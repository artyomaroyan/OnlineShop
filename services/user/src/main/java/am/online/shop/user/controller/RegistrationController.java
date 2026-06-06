package am.online.shop.user.controller;

import am.online.shop.user.model.UserRequest;
import am.online.shop.user.model.UserResponse;
import am.online.shop.user.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 23:51:18
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        return registrationService.create(request);
    }
}