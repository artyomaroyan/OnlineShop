package am.online.shop.user.service;

import am.online.shop.user.model.UserResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 08.05.26
 * Time: 19:58:12
 */
public interface UserService {
    Mono<UserResponse> findUserById(UUID userId);
    // Here will be added more methods soon
}