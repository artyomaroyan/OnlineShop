package am.online.shop.user.model;

import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 15:52:09
 */
@Table(name = "", schema = "")
public class UserEntity {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private boolean active;
}