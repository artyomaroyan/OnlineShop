package am.online.shop.user.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;

/**
 * Author: Artyom Aroyan
 * Date: 01.05.26
 * Time: 01:20:46
 */
record SigningKeyMaterial(
        String keyId,
        PrivateKey privateKey,
        PublicKey publicKey,
        Instant notBefore,
        Instant expiresAt
) {
}