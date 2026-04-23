package am.online.shop.user.mapper;

import am.online.shop.user.model.Role;
import am.online.shop.user.model.UserEntity;
import am.online.shop.user.model.UserRequest;
import am.online.shop.user.model.UserResponse;
import org.spring.basic.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * Author: Artyom Aroyan
 * Date: 17.04.26
 * Time: 17:33:49
 */
@Component
public class UserMapper extends BaseMapper<UserEntity, UserRequest, UserResponse> {
    private static final UserMapper USER_MAPPER = new UserMapper();

    @Override
    protected UserEntity mapToEntity(UserRequest request) {
        return UserEntity.builder()
                .id(null)
                .username(request.username())
                .password(request.password().toCharArray())
                .email(request.email())
                .role(Role.USER)
                .active(false)
                .build();
    }

    @Override
    protected UserResponse mapToResponse(UserEntity entity) {
        return new UserResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRole(),
                entity.isActive()
        );
    }

    public static UserMapper getInstance() {
        return USER_MAPPER;
    }
}