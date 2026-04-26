package am.online.shop.user.service;

import am.online.shop.user.exception.UserAlreadyExistsException;
import am.online.shop.user.mapper.UserMapper;
import am.online.shop.user.model.UserRepository;
import am.online.shop.user.model.UserRequest;
import am.online.shop.user.model.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 02:51:02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserFactory userFactory;
    private final UserRepository userRepository;

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
}