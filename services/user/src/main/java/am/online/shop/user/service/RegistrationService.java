package am.online.shop.user.service;

import am.online.shop.user.model.UserRequest;
import am.online.shop.user.model.UserResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 02:50:33
 */
public interface RegistrationService {
    Mono<UserResponse> create(UserRequest request);
}