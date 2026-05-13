package am.online.shop.user.service;

import am.online.shop.user.model.AuthRequest;
import am.online.shop.user.model.CustomUserDetailsService;
import am.online.shop.user.model.UserIdentity;
import am.online.shop.user.security.JwtTokenService;
import am.online.shop.user.security.PasswordHashService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 07.05.26
 * Time: 16:03:57
 */
@Slf4j
@Service
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {
    private final JwtTokenService tokenService;
    private final PasswordHashService passwordHashService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public Mono<String> login(AuthRequest request) {
        return userDetailsService.findByUsername(request.username())
                .cast(UserIdentity.class)
                .doOnNext(u -> {
                    log.debug("Found user: {}", u.getUsername());
                    log.debug("Stored password hash: {}", u.getPassword());
                    log.debug("Password matches: {}", passwordHashService.matches(request.password(), u.getPassword()));
                })
                .filter(u -> passwordHashService.matches(request.password(), u.getPassword()))
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")))
                .map(UserIdentity::withoutPassword)
                .map(tokenService::generateToken)
                .doOnSuccess(_ -> log.debug("Generated JWT token for: {}", request.username()))
                .doOnError(error -> log.error("Failed to log in: {}", error.getMessage()))
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid credentials")));
    }
}