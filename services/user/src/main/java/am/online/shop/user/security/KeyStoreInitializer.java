package am.online.shop.user.security;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

/**
 * Author: Artyom Aroyan
 * Date: 01.05.26
 * Time: 01:37:53
 */
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtTokenProperties.class)
public class KeyStoreInitializer {
    private final JwtKeyLoader keyLoader;
    private final KeyStoreService keyStore;
    private final JwtTokenProperties properties;


    @PostConstruct
    public void init() {
        final PrivateKey privateKey = keyLoader.loadPrivateKey(properties.privateKeyPath(), properties.algorithm());
        final PublicKey publicKey = keyLoader.loadPublicKey(properties.publicKeyPath(), properties.algorithm());
        final String keyId = KeyIdGenerator.fromRsaPublicKey((RSAPublicKey) publicKey);

        SigningKeyMaterial keyMaterial = new SigningKeyMaterial(
                keyId,
                privateKey,
                publicKey,
                Instant.now(),
                null
        );

        keyStore.addKey(keyMaterial);
        keyStore.activateKey(keyId);
    }
}