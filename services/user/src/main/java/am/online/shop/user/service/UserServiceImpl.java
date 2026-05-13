package am.online.shop.user.service;

import am.online.shop.user.exception.UserNotFoundException;
import am.online.shop.user.mapper.UserMapper;
import am.online.shop.user.model.UserRepository;
import am.online.shop.user.model.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 08.05.26
 * Time: 19:58:40
 */
@Slf4j
@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private static final Duration USER_CACHE_TTL = Duration.ofMinutes(10L);

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ReactiveRedisTemplate<String, UserResponse> redisTemplate;

    @Override
    public Mono<UserResponse> findUserById(UUID userId) {
        var cacheKey = buildCacheKey(userId);
        return redisTemplate.opsForValue()
                .get(cacheKey)
                .doOnNext(_ -> log.info("Cache HIT for user: {}", userId))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("Cache MISS for user: {}. Fetching from DB...", userId);
                    return userRepository.findById(userId)
                            .switchIfEmpty(Mono.error(new UserNotFoundException("User with Id " + userId + " not found in DB")))
                            .flatMap(userMapper::fromEntityToResponse)
                            .switchIfEmpty(Mono.error(new RuntimeException("conversation error here !")))
                            .flatMap(user -> redisTemplate.opsForValue()
                                    .set(cacheKey, user, USER_CACHE_TTL)
                                    .thenReturn(user));
                }));
    }

    private String buildCacheKey(UUID userId) {
        return String.format("user:cache:%s", userId);
    }
}