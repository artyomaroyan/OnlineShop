package am.online.shop.user.security;

import am.online.shop.user.exception.RSAKeyException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Author: Artyom Aroyan
 * Date: 01.05.26
 * Time: 00:17:27
 */
@Component
final class JwtKeyLoader {

    PrivateKey loadPrivateKey(final String privateKyePath, final String algorithm) {
        try {
            final String keyContent = readKeyContent(privateKyePath);
            final String privateKeyPem = keyContent
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(privateKeyPem);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory factory = KeyFactory.getInstance(algorithm);
            return factory.generatePrivate(keySpec);
        }catch (Exception ex) {
            throw new RSAKeyException("Failed to load private key", ex);
        }
    }

    PublicKey loadPublicKey(final String publicKeyPath, final String algorithm) {
        try {
            final String keyContent = readKeyContent(publicKeyPath);
            final String publicKeyPem = keyContent
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(publicKeyPem);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
            KeyFactory factory = KeyFactory.getInstance(algorithm);
            return factory.generatePublic(keySpec);
        } catch (Exception ex) {
            throw new RSAKeyException("Failed to load public key", ex);
        }
    }

    private static String readKeyContent(final String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}