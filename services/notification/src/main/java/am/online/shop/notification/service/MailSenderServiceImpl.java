package am.online.shop.notification.service;

import am.online.shop.notification.model.EmailRepository;
import am.online.shop.notification.model.EmailResponse;
import am.online.shop.notification.model.EmailType;
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
    private final MailFactory mailFactory;
    private final EmailRepository emailRepository;

    @Override
    public Mono<EmailResponse> sendOtpEmail() {
        return null;
    }

    @Override
    public Mono<EmailResponse> sendWelcomeEmail() {
        return null;
    }

    @Override
    public Mono<EmailResponse> sendInformativeEmail() {
        return null;
    }

    @Override
    public Mono<EmailResponse> sendPasswordResetEmail() {
        return null;
    }
}