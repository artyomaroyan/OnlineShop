package am.online.shop.user.service;

import am.online.shop.user.exception.UserAlreadyExistsException;
import am.online.shop.user.exception.UserNotFoundException;
import am.online.shop.user.mapper.UserMapper;
import am.online.shop.user.model.UserRepository;
import am.online.shop.user.model.UserRequest;
import am.online.shop.user.model.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 02:51:02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Duration USER_CACHE_TTL = Duration.ofMinutes(10L);

    private final UserMapper userMapper;
    private final UserFactory userFactory;
    private final UserRepository userRepository;
    private final ReactiveRedisTemplate<String, UserResponse> redisTemplate;

    @Override
    public Mono<UserResponse> create(UserRequest request) {
        return Mono.just(request)
                .flatMap(this::validateUniqueness)
                .flatMap(userFactory::createUser)
                .flatMap(userRepository::save)
                .flatMap(userMapper::fromEntityToResponse)
                .onErrorResume(DuplicateKeyException.class, _ ->
                    Mono.error(new UserAlreadyExistsException("Username or email already exists")))
                .doOnSuccess(_ -> log.info("User created successfully: {}", request.username()))
                .doOnError(error -> log.error("Failed to create user: {}", error.getMessage()));
    }

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
                            .flatMap(user -> redisTemplate.opsForValue()
                                    .set(cacheKey, user, USER_CACHE_TTL)
                                    .thenReturn(user));
                }));
    }

    private Mono<UserRequest> validateUniqueness(UserRequest request) {
        return Mono.zip(
                        userRepository.existsByUsername(request.username()),
                        userRepository.existsByEmail(request.email())
                )
                .flatMap(tuple -> {
                    if (tuple.getT1()) {
                        return Mono.error(new UserAlreadyExistsException("User with " + request.username() + " already exists"));
                    }
                    if (tuple.getT2()) {
                        return Mono.error(new UserAlreadyExistsException("User with " + request.email() + " already exists"));
                    }
                    return Mono.just(request);
                });
    }

    private String buildCacheKey(UUID userId) {
        return String.format("user:cache:%s", userId);
    }
}