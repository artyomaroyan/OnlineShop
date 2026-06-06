package am.online.shop.user.controller;

import am.online.shop.user.exception.UserAlreadyExistsException;
import am.online.shop.user.exception.UserNotFoundException;
import am.online.shop.user.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.spring.basic.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 06.06.26
 * Time: 12:52:38
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    Mono<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        log.warn("Conflict: {}", ex.getMessage());
        return Mono.just(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Mono<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        log.warn("Not Found: {}", ex.getMessage());
        return Mono.just(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Mono<ErrorResponse> handleValidation(ValidationException ex) {
        log.warn("Validation Failed: {}", ex.getMessage());
        return Mono.just(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Mono<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        log.debug(ex.getMessage());
        return Mono.just(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid Username or Password"));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Mono<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Unexpected error", ex);
        return Mono.just(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred"));
    }
}