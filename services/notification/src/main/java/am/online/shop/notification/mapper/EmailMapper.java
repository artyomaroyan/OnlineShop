package am.online.shop.notification.mapper;

import am.online.shop.notification.model.EmailEntity;
import am.online.shop.notification.model.EmailResponse;
import org.spring.basic.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * Author: Artyom Aroyan
 * Date: 18.05.26
 * Time: 00:07:37
 */
@Component
public class EmailMapper extends BaseMapper<EmailEntity, EmailResponse> {

    @Override
    protected EmailResponse mapToResponse(EmailEntity entity) {
        return null;
    }
}