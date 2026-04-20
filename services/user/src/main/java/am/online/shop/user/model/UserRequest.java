package am.online.shop.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 15:55:53
 */
@Validated
public record UserRequest(
        @NotBlank(message = "Username is required")
        @Size(min = 5, max = 16, message = "Username must be 5 - 16 characters length")
        String username,
        @NotBlank(message = "Password is required")
        @Size(min = 5, max = 16, message = "Password must be 5 - 16 characters length")
        String password,
        @Email
        @NotBlank(message = "Email is required")
        String email,
        @NotNull(message = "Role is required")
        Role role,
        boolean active
) {
}