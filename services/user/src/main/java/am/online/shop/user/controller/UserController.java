package am.online.shop.user.controller;

import am.online.shop.user.model.UserRequest;
import am.online.shop.user.model.UserResponse;
import am.online.shop.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 21.04.26
 * Time: 23:51:18
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    Mono<ResponseEntity<UserResponse>> createUser(@Valid @RequestBody UserRequest request) {
        return userService.create(request)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/get/by/{userId}")
    Mono<ResponseEntity<UserResponse>> getUserById(@PathVariable UUID userId) {
        return userService.findUserById(userId)
                .map(ResponseEntity::ok);
    }
}