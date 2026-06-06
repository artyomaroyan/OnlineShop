package org.spring.basic.util;

import java.security.SecureRandom;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

/**
 * Author: Artyom Aroyan
 * Date: 06.06.26
 * Time: 13:52:48
 */
public class UUIDv7Generator {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static long lastTimestamp = -1L;
    private static long sequence = 0L;

    public static synchronized UUID generateUUIDv7() {
        long timestamp = currentTimeMillis();

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & 0x0FFFL;

            if (sequence == 0) {
                while ((timestamp = currentTimeMillis()) == lastTimestamp) {
                    Thread.onSpinWait();
                }
            }
        } else {
            sequence = SECURE_RANDOM.nextLong() & 0x0FFFL;
        }
        lastTimestamp = timestamp;

        long msb = (timestamp << 16)
                | 0x7000L
                | sequence;

        long lsb = (SECURE_RANDOM.nextLong() & 0x3FFFFFFFFFFFFFFFL)
                | 0x8000000000000000L;
        return new UUID(msb, lsb);
    }
}