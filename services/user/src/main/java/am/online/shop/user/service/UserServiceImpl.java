package am.online.shop.user.service;

import am.online.shop.user.model.UserRequest;
import am.online.shop.user.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 02:51:02
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public Mono<UserResponse> create(UserRequest request) {
        return UserFactory.createUser(request);
    }
}