package am.online.shop.user.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 23.04.26
 * Time: 15:28:48
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements ReactiveUserDetailsService {
    private final UserRepository userRepository;

    @NonNull
    @Override
    public Mono<UserDetails> findByUsername(@NonNull String username) {
        return userRepository.findByUsername(username)
                .doOnNext(u -> log.debug("Found entity: {}", u))
                .map(UserIdentity::from)
                .cast(UserDetails.class)
                .switchIfEmpty(Mono.error(() -> new UsernameNotFoundException(
                        "No Account found with " + username + " username")));
    }
}