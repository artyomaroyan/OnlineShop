package am.online.shop.user.service;

import am.online.shop.user.model.UserEntity;
import am.online.shop.user.model.UserResponse;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 16:31:57
 */
public record UserFactory() {
    private static UserEntity entity;

    static UserResponse createUser(String username, String password, String email) {

        return new UserResponse();
    }
}