package am.online.shop.user.security;

import am.online.shop.user.model.UserIdentity;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Author: Artyom Aroyan
 * Date: 03.05.26
 * Time: 22:04:54
 */
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {
    private final JwtTokenProperties properties;
    private final KeyStoreService keyStoreService;

    @Override
    public String generateToken(UserIdentity identity) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + properties.expiration());
        SigningKeyMaterial keyMaterial = keyStoreService.getActiveSigningKey();

        return Jwts.builder()
                .header()
                .type("JWT")
                .keyId(keyMaterial.keyId())
                .and()
                .id(UUID.randomUUID().toString())
                .subject(identity.getUsername())
                .issuer(properties.issuer())
                .issuedAt(now)
                .expiration(exp)
                .claim("uid", identity.userId())
                .claim("auth", identity.getAuthorities())
                .compact();
    }
}