package am.online.shop.notification.service;

import am.online.shop.notification.mapper.EmailMapper;
import am.online.shop.notification.model.EmailRepository;
import am.online.shop.notification.model.EmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static am.online.shop.notification.model.EmailType.*;

/**
 * Author: Artyom Aroyan
 * Date: 13.05.26
 * Time: 16:09:04
 */
@Slf4j
@Service
@RequiredArgsConstructor
class MailSenderServiceImpl implements MailSenderService {
    private final EmailMapper emailMapper;
    private final MailFactory mailFactory;
    private final EmailRepository emailRepository;

    @Override
    public Mono<EmailResponse> sendOtpEmail(String recipient) {
        return mailFactory.send(recipient, OTP)
                .flatMap(emailRepository::save)
                .flatMap(emailMapper::fromEntityToResponse);
    }

        @Override
        public Mono<EmailResponse> sendWelcomeEmail(String recipient) {
            return mailFactory.send(recipient, VALIDATED)
                    .flatMap(emailRepository::save)
                    .flatMap(emailMapper::fromEntityToResponse);
            }

    @Override
    public Mono<EmailResponse> sendInformativeEmail(String recipient) {
        return mailFactory.send(recipient, INFORMATIVE)
                .flatMap(emailRepository::save)
                .flatMap(emailMapper::fromEntityToResponse);
    }

    @Override
    public Mono<EmailResponse> sendPasswordResetEmail(String recipient) {
        return mailFactory.send(recipient, PASSWORD_RESET)
                .flatMap(emailRepository::save)
                .flatMap(emailMapper::fromEntityToResponse);
    }
}