package am.online.shop.notification.service;

import am.online.shop.notification.model.EmailType;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static reactor.test.StepVerifier.create;

/**
 * Author: Artyom Aroyan
 * Date: 27.05.26
 * Time: 02:42:17
 */
@ExtendWith(MockitoExtension.class)
class MailFactoryTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private MailFactory mailFactory;

    @BeforeEach
    void setUp() {
        MimeMessage mimeMessage = new MimeMessage((Session) null);
        given(javaMailSender.createMimeMessage())
                .willReturn(mimeMessage);
    }
    @Test
    void send_otpEmail_buildsCorrectEntity() {
        create(mailFactory.send("user1@gmail.com", EmailType.OTP))
                .assertNext(entity -> {
                    assertThat(entity.getId()).isNotNull();
                    assertThat(entity.getMailTo()).isEqualTo("user1@gmail.com");
                    assertThat(entity.getMailFrom()).isEqualTo("online_shop@gmail.com");
                    assertThat(entity.getEmailType()).isEqualTo(EmailType.OTP);
                    assertThat(entity.getSendDate()).isNotNull();
                    assertThat(entity.getVersion()).isNull();
                })
                .verifyComplete();
    }

    @Test
    void send_otpEmail_callsMailSenderExactlyOnce() {
        create(mailFactory.send("user1@gmail.com", EmailType.OTP))
                .expectNextCount(1)
                .verifyComplete();
        then(javaMailSender).should(times(1)).send(any(MimeMessage.class));
    }

    @ParameterizedTest
    @EnumSource(EmailType.class)
    void send_allEmailTypes_completesWithoutError(EmailType type) {
        create(mailFactory.send("user1@gmail.com", type))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void send_whenMailSenderThrows_mapsToRuntimeException() {
        willThrow(new MailSendException("Failed to send email"))
                .given(javaMailSender)
                .send(any(MimeMessage.class));

        create(mailFactory.send("user1@gmail.com", EmailType.OTP))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(RuntimeException.class);
                    assertThat(ex.getMessage()).isEqualTo("Failed to send email");
                })
                .verify();
    }

    @Test
    void send_withNullRecipient_propagatesError() {
        create(mailFactory.send(null, EmailType.OTP))
                .expectError()
                .verify();
    }
}