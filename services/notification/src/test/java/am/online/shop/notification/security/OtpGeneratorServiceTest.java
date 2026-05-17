package am.online.shop.notification.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Author: Artyom Aroyan
 * Date: 13.05.26
 * Time: 16:48:48
 */
@SpringBootTest
class OtpGeneratorServiceTest {

    @Test
    void randomOtp() {
        IO.println(OtpGeneratorService.randomOtp());
    }
}