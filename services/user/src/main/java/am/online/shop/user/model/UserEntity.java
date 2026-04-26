package am.online.shop.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 15:52:09
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "users")
public class UserEntity {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
    private boolean active;
}