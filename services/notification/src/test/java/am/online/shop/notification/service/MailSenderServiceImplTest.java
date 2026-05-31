package am.online.shop.notification.service;

import am.online.shop.notification.mapper.EmailMapper;
import am.online.shop.notification.model.EmailEntity;
import am.online.shop.notification.model.EmailRepository;
import am.online.shop.notification.model.EmailResponse;
import am.online.shop.notification.model.EmailType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static am.online.shop.notification.model.EmailType.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static reactor.test.StepVerifier.create;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author: Artyom Aroyan
 * Date: 27.05.26
 * Time: 02:16:17
 */
@ExtendWith(MockitoExtension.class)
class MailSenderServiceImplTest {
    @Mock
    private EmailMapper emailMapper;
    @Mock
    private MailFactory mailFactory;
    @Mock
    private EmailRepository emailRepository;
    @InjectMocks
    private MailSenderServiceImpl mailSenderService;

    private EmailEntity unsaved;
    private EmailEntity saved;
    private EmailResponse emailResponse;

    @BeforeEach
    void setUp() {
        unsaved = EmailEntity.builder()
                .id(UUID.randomUUID())
                .mailTo("user1@gmail.com")
                .mailFrom("online_shop@gmail.com")
                .sendDate(LocalDateTime.now())
                .build();

        saved = EmailEntity.builder()
                .id(unsaved.getId())
                .mailTo(unsaved.getMailTo())
                .mailFrom(unsaved.getMailFrom())
                .sendDate(unsaved.getSendDate())
                .version(0L)
                .build();

        emailResponse = new EmailResponse(saved.getId(), saved.getMailTo(), saved.getMailFrom(), saved.getSendDate());
    }

    @Test
    void sendOtpEmail_happyPath_returnsMappedResponse() {
        givenFactoryReturns(OTP);

        create(mailSenderService.sendOtpEmail("user1@gmailcom"))
                .assertNext(response -> assertThat(response).isEqualTo(emailResponse))
                .verifyComplete();

        verifyFullPipelineInvoked(OTP);
    }

    @Test
    void sendWelcomeEmail_happyPath_returnsMappedResponse() {
        givenFactoryReturns(VALIDATED);

        create(mailSenderService.sendWelcomeEmail("user1@gmail.com"))
                .assertNext(response -> assertThat(response).isEqualTo(emailResponse))
                .verifyComplete();

        verifyFullPipelineInvoked(VALIDATED);
    }

    @Test
    void sendInformativeEmail_happyPath_returnsMappedResponse() {
        givenFactoryReturns(INFORMATIVE);

        create(mailSenderService.sendInformativeEmail("user1@gmail.com"))
                .assertNext(response -> assertThat(response).isEqualTo(emailResponse))
                .verifyComplete();

        verifyFullPipelineInvoked(INFORMATIVE);
    }

    @Test
    void sendPasswordResetEmail_happyPath_returnsMappedResponse() {
        givenFactoryReturns(PASSWORD_RESET);

        create(mailSenderService.sendPasswordResetEmail("user1@gmail.com"))
                .assertNext(response -> assertThat(response).isEqualTo(emailResponse))
                .verifyComplete();

        verifyFullPipelineInvoked(PASSWORD_RESET);
    }

    @Test
    void sendOtpEmail_whenFactoryFails_propagatesError() {
        given(mailFactory.send("user1@gmail.com", OTP))
                .willReturn(Mono.error(new RuntimeException("SMTP error")));

        create(mailSenderService.sendOtpEmail("user1@gmail.com"))
                .expectErrorMessage("SMTP error")
                .verify();

        then(emailRepository).shouldHaveNoInteractions();
        then(emailMapper).shouldHaveNoInteractions();
    }

    private void givenFactoryReturns(EmailType type) {
        given(mailFactory.send("user1@gmailcom", type))
                .willReturn(Mono.just(unsaved));
        given(emailRepository.save(unsaved))
                .willReturn(Mono.just(saved));
        given(emailMapper.fromEntityToResponse(saved))
                .willReturn(Mono.just(emailResponse));
    }

    private void verifyFullPipelineInvoked(EmailType type) {
        then(mailFactory).should().send("user1@gmail.com", type);
        then(emailRepository).should().save(unsaved);
        then(emailMapper).should().fromEntityToResponse(saved);
    }
}