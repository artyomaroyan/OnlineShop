package am.online.shop.user.converter;

import am.online.shop.user.model.Role;
import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: Artyom Aroyan
 * Date: 07.05.26
 * Time: 00:22:55
 */
public final class RoleSetConverters {
    private RoleSetConverters() {
    }

    @WritingConverter
    public static class RoleSetToStringArray implements Converter<Set<Role>, String> {
        @Override
        public String convert(Set<Role> source) {
            return source.stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
        }
    }
//    @WritingConverter
//    public static class RoleSetToStringArray implements GenericConverter {
//        @Override
//        public @Nullable Set<ConvertiblePair> getConvertibleTypes() {
//            return Set.of(new ConvertiblePair(Set.class, String.class));
//        }
//
//        @Override
//        public @Nullable Object convert(@Nullable Object source, @NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
//            if (source == null) throw new IllegalArgumentException("Source can not be null");
//            @SuppressWarnings("unchecked")
//            Set<Role> roles = (Set<Role>) source;
//            return roles.stream()
//                    .map(Role::name)
//                    .collect(Collectors.joining(","));
//        }
//    }

    @ReadingConverter
    public static class StringArrayToRoleSet implements Converter<String, Set<Role>> {
        @Override
        public Set<Role> convert(@NonNull String source) {
            if (source.isBlank()) return Set.of();
            return Arrays.stream(source.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Role::valueOf)
                    .collect(Collectors.toUnmodifiableSet());
        }
    }
}