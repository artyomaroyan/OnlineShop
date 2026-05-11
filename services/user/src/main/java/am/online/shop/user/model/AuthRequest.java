package am.online.shop.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/**
 * Author: Artyom Aroyan
 * Date: 07.05.26
 * Time: 16:01:06
 */
@Validated
public record AuthRequest(
        @Schema(example = "user1")
        @NotBlank(message = "username is required")
        @Size(min = 5, max = 16, message = "Username must be 5 - 16 characters length")
        String username,
        @Schema(example = "Password.1")
        @NotBlank(message = "password is required")
        @Size(min = 5, max = 16, message = "Password must be 5 - 16 characters length")
        String password) {
}