package am.online.shop.user.model;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 16:50:15
 */
@Repository("r2dbcRepository")
public interface UserRepository extends R2dbcRepository<UserEntity, UUID> {
    Mono<Boolean> existsByUsername(String username);
    Mono<Boolean> existsByEmail(String email);
    Mono<UserDetails> findByUsername(String username);
}