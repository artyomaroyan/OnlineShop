package am.online.shop.notification.service;

import am.online.shop.notification.model.EmailResponse;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 13.05.26
 * Time: 16:08:53
 */
public interface MailSenderService {
    Mono<EmailResponse> sendOtpEmail();
    Mono<EmailResponse> sendWelcomeEmail();
    Mono<EmailResponse> sendInformativeEmail();
    Mono<EmailResponse> sendPasswordResetEmail();
}