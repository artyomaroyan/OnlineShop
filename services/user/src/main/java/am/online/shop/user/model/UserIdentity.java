package am.online.shop.user.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Author: Artyom Aroyan
 * Date: 03.05.26
 * Time: 22:03:42
 */
@Getter
public final class UserIdentity implements UserDetails {
    private final UUID id;
    private final String username;
    private final String password;
    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final Collection<? extends GrantedAuthority> authorities;

    private UserIdentity(UUID id, String username, String password,
                         boolean enabled, boolean accountNonExpired, boolean accountNonLocked,
                         boolean credentialsNonExpired, Collection<? extends GrantedAuthority> authorities) {
        this.id = Objects.requireNonNull(id);
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.authorities = List.copyOf(authorities);
    }

    public static UserIdentity from(UserEntity user) {
        return new UserIdentity(
                user.getId(),
                user.getUsername(),
                user.getPassword(), // todo: check that this MUST return password hash not plain password.
                user.isActive(),
                true,
                true,
                true,
                mapAuthorities(user.getRoles())
        );
    }

    private static Collection<GrantedAuthority> mapAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect(Collectors.toUnmodifiableList());
    }

    public UUID userId() {
        return null;
    }
}