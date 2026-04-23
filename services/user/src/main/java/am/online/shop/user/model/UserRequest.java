package am.online.shop.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 15:55:53
 */
@Validated
public record UserRequest(
        @Schema(example = "user.1")
        @NotBlank(message = "Username is required")
        @Size(min = 5, max = 16, message = "Username must be 5 - 16 characters length")
        String username,
        @Schema(example = "Password.1")
        @NotBlank(message = "Password is required")
        @Size(min = 5, max = 16, message = "Password must be 5 - 16 characters length")
        String password,
        @Email
        @Schema(example = "user.1@gmail.com")
        @NotBlank(message = "Email is required")
        @Size(max = 254, message = "Email length can not exceed 254 characters")
        String email
) {
}