package am.online.shop.user.security;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * Author: Artyom Aroyan
 * Date: 04.05.26
 * Time: 12:59:20
 */
final class KeyIdGenerator {

    private KeyIdGenerator(){
    }

    public static String fromRsaPublicKey(RSAPublicKey publicKey) {
        try {
            String n = base64Url(publicKey.getModulus());
            String e = base64Url(publicKey.getPublicExponent());
            String jwkJson = "{\"e\":\"" + e + "\",\"kty\":\"RSA\",\"n\":\"" + n + "\"}";
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(jwkJson.getBytes(StandardCharsets.UTF_8));

            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception ex) {
            throw new IllegalArgumentException();
        }
    }

    private static String base64Url(BigInteger value) {
        byte[] bytes = value.toByteArray();

        // Remove leading zero if present
        if (bytes[0] == 0) {
            byte[] temp = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, temp, 0, temp.length);
            bytes = temp;
        }
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}