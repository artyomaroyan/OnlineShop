package am.online.shop.user.service;

import am.online.shop.user.mapper.UserMapper;
import am.online.shop.user.model.UserEntity;
import am.online.shop.user.model.UserRequest;
import am.online.shop.user.model.UserResponse;
import am.online.shop.user.security.PasswordHashService;
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

    static Mono<UserResponse> createUser(UserRequest request) {
        return create(request);
    }

    private static Mono<UserResponse> create(UserRequest request) {
        var validPass = PasswordValidator.getInstance().isValid(request.password());
        final UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID())
                .username(UsernameValidator.getInstance().isValid(request.username()))
                .password(PasswordHashService.getInstance().encode(validPass).toCharArray())
                .email(EmailValidator.getInstance().isValid(request.email()))
                .role(USER)
                .active(false)
                .build();

        return UserMapper.getInstance().fromEntityToResponse(entity);
    }

    // todo: implement logic for user active status, something like -> if user activate OTP or verify email then account is active...

    // todo: also after app pre-ready status I can implement logic to check user ID uniqueness,
    //  need to have existing users cache, and check if there is any user with generated ID,
    //  if yes generate another otherwise use generated.
}