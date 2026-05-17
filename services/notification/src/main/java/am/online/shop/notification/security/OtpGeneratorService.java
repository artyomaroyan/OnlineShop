package am.online.shop.notification.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Author: Artyom Aroyan
 * Date: 13.05.26
 * Time: 15:14:24
 */
@Slf4j
@Service
public record OtpGeneratorService() {
    private static final int SIZE = 5;

    public static String randomOtp() {
        StringBuilder generatedOtp = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < SIZE; i++) {
                generatedOtp.append(number.nextInt(SIZE));
            }
        } catch (NoSuchAlgorithmException ex) {
            log.error("can not find algorithm: {}", ex.getMessage());
        }
        return generatedOtp.toString();
    }
}