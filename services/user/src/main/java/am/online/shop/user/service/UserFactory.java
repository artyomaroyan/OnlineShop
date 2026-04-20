package am.online.shop.user.service;

import am.online.shop.user.mapper.UserMapper;
import am.online.shop.user.model.UserEntity;
import am.online.shop.user.model.UserRequest;
import am.online.shop.user.model.UserResponse;
import am.online.shop.user.validation.EmailValidator;
import am.online.shop.user.validation.PasswordValidator;
import am.online.shop.user.validation.UsernameValidator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static am.online.shop.user.model.Role.USER;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 16:31:57
 */
@Component
public record UserFactory() {
    private static UserMapper mapper;
    private static UsernameValidator usernameValidator;
    private static PasswordValidator passwordValidator;
    private static EmailValidator emailValidator;

    static Mono<UserResponse> createUser(UserRequest request) {
        return create(request);
    }

    private static Mono<UserResponse> create(UserRequest request) {
        final UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username(usernameValidator.isValid(request.username()))
                .password(passwordValidator.isValid(request.password()))
                .email(emailValidator.isValid(request.email()))
                .role(USER)
                .active(true)
                .build();

        return mapper.fromEntityToResponse(entity);
    }

    // todo: implement logic for user active status, something like -> if user activate OTP or verify email then account is active...

    // todo: also after app pre-ready status I can implement logic to check user ID uniqueness,
    //  need to have existing users cache, and check if there is any user with generated ID,
    //  if yes generate another otherwise use generated.
}