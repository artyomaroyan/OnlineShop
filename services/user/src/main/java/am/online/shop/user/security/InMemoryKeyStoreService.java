package am.online.shop.user.security;

import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Artyom Aroyan
 * Date: 01.05.26
 * Time: 01:23:15
 */
@Component
final class InMemoryKeyStoreService implements KeyStoreService {
    private final Map<String, SigningKeyMaterial> keys = new ConcurrentHashMap<>();
    private volatile String activeKeyId;

    @Override
    public SigningKeyMaterial getActiveSigningKey() {
        if (activeKeyId == null) throw new IllegalStateException("No active signing key configured");
        SigningKeyMaterial material = keys.get(activeKeyId);
        if (material == null) throw new IllegalStateException("Active key ID not found in store:" + activeKeyId);
        return material;
    }

    @Override
    public PublicKey getActiveSigningKey(String keyId) {
        SigningKeyMaterial material = keys.get(keyId);
        if (material == null) {
            throw new IllegalArgumentException("Unknown KeyId: " + keyId);
        }
        return material.publicKey();
    }

    @Override
    public Collection<SigningKeyMaterial> getAllKeys() {
        return keys.values();
    }

    @Override
    public void addKey(SigningKeyMaterial key) {
        keys.put(key.keyId(), key);
    }

    @Override
    public void activateKey(String keyId) {
        if (!keys.containsKey(keyId)) {
            throw new IllegalArgumentException("Key not found: " + keyId);
        }
        this.activeKeyId = keyId;
    }

    @Override
    public void removeKey(String keyId) {
        if (keyId.equals(activeKeyId)) {
            throw new IllegalStateException("Cannot remove the currently active key");
        }
        keys.remove(keyId);
    }
}