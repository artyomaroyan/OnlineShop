package am.online.shop.user.service;

import am.online.shop.user.model.UserEntity;
import am.online.shop.user.model.UserRequest;
import am.online.shop.user.security.PasswordHashService;
import am.online.shop.user.validation.*;
import lombok.RequiredArgsConstructor;
import org.spring.basic.exception.ValidationException;
import org.spring.basic.util.UUIDv7Generator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.Set;

import static am.online.shop.user.model.Role.USER;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 16:31:57
 */
@Component
@RequiredArgsConstructor
final class UserFactory {
    private final EmailValidator emailValidator;
    private final UsernameValidator usernameValidator;
    private final PasswordValidator passwordValidator;
    private final PasswordHashService passwordHashService;

    public Mono<UserEntity> createUser(UserRequest request) {
        return Mono.zip(
                        validateFields(usernameValidator, request.username()),
                        validateFields(emailValidator, request.email()),
                        validateAndEncodePassword(request.password())
                )
                .map(tuple -> UserEntity.builder()
                        .id(UUIDv7Generator.generateUUIDv7())
                        .username(request.username())
                        .password(tuple.getT3())
                        .email(request.email())
                        .roles(Set.of(USER))
                        .createdAt(Instant.now())
                        .active(false)
                        .build());
    }

//    private Mono<ValidationResult> validateUsername(String username) {
//        return Mono.fromCallable(() -> fieldValidator.validate(username))
//                .filter(ValidationResult::isValid)
//                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid username")));
//    }
//    private Mono<Boolean> validateUsername(String username) {
//        return Mono.fromCallable(() -> usernameValidator.isValid(username))
//                .filter(valid -> valid)
//                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid username")));
//    }

//    private Mono<ValidationResult> validateEmail(String email) {
//        return Mono.fromCallable(() -> fieldValidator.validate(email))
//                .filter(ValidationResult::isValid)
//                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid Email")));
//    }
//    private Mono<Boolean> validateEmail(String email) {
//        return Mono.fromCallable(() -> emailValidator.isValid(email))
//                .filter(valid -> valid)
//                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid Email")));
//    }

    private Mono<ValidationResult> validateFields(FieldValidator<String> validator, String value) {
        return Mono.fromCallable(() -> validator.validate(value))
                .flatMap(result -> result.isValid()
                        ? Mono.just(result)
                        : Mono.error(new ValidationException(result.reason())));
    }

    private Mono<String> validateAndEncodePassword(String password) {
        return Mono.fromCallable(() -> passwordValidator.validate(password))
                .flatMap(result -> result.isValid()
                        ? Mono.fromCallable(() -> passwordHashService.encode(password))
                        .subscribeOn(Schedulers.boundedElastic())
                        : Mono.error(new ValidationException(result.reason())));
    }
//    private Mono<String> validateAndEncodePassword(String password) {
//        return Mono.fromCallable(() -> {
//            if (!passwordValidator.validate(password).isValid()) {
//                throw new IllegalArgumentException("Invalid password format");
//            }
//            return passwordHashService.encode(password);
//        });
//    }

//    private Mono<String> validateAndEncodePassword(String password) {
//        return Mono.fromCallable(() -> {
//            if (!passwordValidator.isValid(password)) {
//                throw new IllegalArgumentException("Invalid password format");
//            }
//            return passwordHashService.encode(password);
//        });
//    }

    // todo: implement logic for user active status, something like -> if user activate OTP or verify email then account is active...

    // todo: also after app pre-ready status I can implement logic to check user ID uniqueness,
    //  need to have existing users cache, and check if there is any user with generated ID,
    //  if yes generate another otherwise use generated.
}