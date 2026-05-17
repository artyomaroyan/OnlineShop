package am.online.shop.user.mapper;

import am.online.shop.user.model.UserEntity;
import am.online.shop.user.model.UserResponse;
import org.spring.basic.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 17:33:49
 */
@Component
public class UserMapper extends BaseMapper<UserEntity, UserResponse> {

//    @Override
//    protected UserEntity mapToEntity(UserRequest request) {
//        return UserEntity.builder()
//                .id(null)
//                .username(request.username())
//                .password(request.password())
//                .email(request.email())
//                .roles(Set.of(USER))
//                .active(false)
//                .build();
//    }

    @Override
    protected UserResponse mapToResponse(UserEntity entity) {
        return new UserResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRoles(),
                entity.isActive()
        );
    }
}