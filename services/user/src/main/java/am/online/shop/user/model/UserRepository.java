package am.online.shop.user.model;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 16:50:15
 */
public interface UserRepository extends R2dbcRepository<UserEntity, UUID> {
}