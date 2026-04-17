package am.online.shop.user.model;

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

    Role(Permission... permission) {
    }
}