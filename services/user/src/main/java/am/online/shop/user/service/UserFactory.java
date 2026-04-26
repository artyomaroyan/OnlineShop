package am.online.shop.user.service;

import am.online.shop.user.model.Role;
import am.online.shop.user.model.UserEntity;
import am.online.shop.user.model.UserRequest;
import am.online.shop.user.security.PasswordHashService;
import am.online.shop.user.validation.EmailValidator;
import am.online.shop.user.validation.PasswordValidator;
import am.online.shop.user.validation.UsernameValidator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 16:31:57
 */
@Component
public record UserFactory(
        EmailValidator emailValidator, UsernameValidator usernameValidator,
        PasswordValidator passwordValidator, PasswordHashService passwordHashService
) {

    public Mono<UserEntity> createUser(UserRequest request) {
        return Mono.zip(
                validateUsername(request.username()),
                validateEmail(request.email()),
                validateAndEncodePassword(request.password())
        )
                .map(tuple -> UserEntity.builder()
                        .id(UUID.randomUUID())
                        .username(request.username())
                        .password(tuple.getT3())
                        .email(request.email())
                        .role(Role.USER)
                        .createdAt(LocalDateTime.now())
                        .active(false)
                        .build())
                ;
    }

    private Mono<Boolean> validateUsername(String username) {
        return Mono.fromCallable(() -> usernameValidator.isValid(username))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid username")));
    }

    private Mono<Boolean> validateEmail(String email) {
        return Mono.fromCallable(() -> emailValidator.isValid(email))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid Email")));
    }

    private Mono<String> validateAndEncodePassword(String password) {
        return Mono.fromCallable(() -> {
            if (!passwordValidator.isValid(password)) {
                throw new IllegalArgumentException("Invalid password format");
            }
            return passwordHashService.encode(password);
        });
    }

    // todo: implement logic for user active status, something like -> if user activate OTP or verify email then account is active...

    // todo: also after app pre-ready status I can implement logic to check user ID uniqueness,
    //  need to have existing users cache, and check if there is any user with generated ID,
    //  if yes generate another otherwise use generated.
}