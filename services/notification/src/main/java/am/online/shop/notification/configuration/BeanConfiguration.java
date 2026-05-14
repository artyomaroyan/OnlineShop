package am.online.shop.notification.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Author: Artyom Aroyan
 * Date: 13.05.26
 * Time: 15:49:41
 */
@Configuration
@EnableConfigurationProperties(MailConfiguration.class)
public class BeanConfiguration {

    @Bean
    protected JavaMailSender mailSender(MailConfiguration mailConfiguration) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setPort(mailConfiguration.port());
        mailSender.setHost(mailConfiguration.host());
        mailSender.setUsername(mailConfiguration.username());
        mailSender.setPassword(mailConfiguration.password());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", mailConfiguration.auth());
        properties.put("mail.smtp.starttls.enable", mailConfiguration.enable());
        properties.put("mail.smtp.starttls.require", mailConfiguration.require());
        properties.put("mail.transport.protocol", mailConfiguration.protocol());

        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}