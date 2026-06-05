package am.online.shop.notification.controller;

import am.online.shop.notification.model.EmailResponse;
import am.online.shop.notification.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 04.06.26
 * Time: 00:37:09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final MailSenderService mailSenderService;

    @PostMapping("/send/verification/{recipient}")
    Mono<ResponseEntity<EmailResponse>> sendVerificationMail(@PathVariable String recipient) {
        return mailSenderService.sendOtpEmail(recipient)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/send/welcome/{recipient}")
    Mono<ResponseEntity<EmailResponse>> sendWelcomeMail(@PathVariable String recipient) {
        return mailSenderService.sendWelcomeEmail(recipient)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/send/informative/{recipient}")
    Mono<ResponseEntity<EmailResponse>> sendInfoMail(@PathVariable String recipient) {
        return mailSenderService.sendInformativeEmail(recipient)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/send/password-reset/{recipient}")
    Mono<ResponseEntity<EmailResponse>> sendPasswordResetMail(@PathVariable String recipient) {
        return mailSenderService.sendPasswordResetEmail(recipient)
                .map(ResponseEntity::ok);
    }
}