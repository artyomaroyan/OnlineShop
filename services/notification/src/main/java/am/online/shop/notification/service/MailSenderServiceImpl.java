package am.online.shop.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 13.05.26
 * Time: 16:09:04
 */
@Slf4j
@Service
class MailSenderServiceImpl implements MailSenderService {

    @Override
    public Mono<String> sendOtpEmail() {
        return null;
    }

    @Override
    public Mono<String> sendWelcomeEmail() {
        return null;
    }

    @Override
    public Mono<String> sendInformativeEmail() {
        return null;
    }

    @Override
    public Mono<String> sendPasswordResetEmail() {
        return null;
    }
}