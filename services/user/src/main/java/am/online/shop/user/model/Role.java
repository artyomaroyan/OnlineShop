package am.online.shop.user.model;

import lombok.Getter;

import java.util.*;

import static am.online.shop.user.model.Permission.*;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 16:02:53
 */
public enum Role {
    GUEST,
    USER(SELF_READ, SELF_UPDATE),
    ADMIN(READ, CREATE, UPDATE, MANAGE, DELETE);

    @Getter
    private final Set<Permission> permissions;

    Role(Permission... permission) {
        this.permissions = Collections.unmodifiableSet(EnumSet.copyOf(Arrays.asList(permission)));
    }

    // GUEST edge case — empty EnumSet needs special handling
    Role() {
        this.permissions = Collections.emptySet();
    }
}