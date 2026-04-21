package am.online.shop.user.service;

import am.online.shop.user.model.UserRequest;
import am.online.shop.user.model.UserResponse;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 02:50:33
 */
public interface UserService {
    Mono<UserResponse> create(UserRequest request);
}