package am.online.shop.user.security;

import am.online.shop.user.model.UserIdentity;
import io.jsonwebtoken.Claims;

/**
 * Author: Artyom Aroyan
 * Date: 03.05.26
 * Time: 22:03:11
 */
public interface JwtTokenService {
    String generateToken(UserIdentity identity);
    Claims validateToken(String token);
}