package am.online.shop.user.security;

import java.security.PublicKey;
import java.util.Collection;

/**
 * Author: Artyom Aroyan
 * Date: 01.05.26
 * Time: 01:19:42
 */
public interface KeyStoreService {
    SigningKeyMaterial getActiveSigningKey();
    PublicKey getActiveSigningKey(String keyId);
    Collection<SigningKeyMaterial> getAllKeys();
    void addKey(SigningKeyMaterial key);
    void activateKey(String keyId);
    void removeKey(String keyId);
}