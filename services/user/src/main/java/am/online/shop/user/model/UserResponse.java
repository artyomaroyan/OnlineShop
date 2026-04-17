package am.online.shop.user.model;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 16:02:04
 */
public record UserResponse(
        UUID id,
        String username,
        String password,
        String email,
        Role role,
        boolean active
) {
}