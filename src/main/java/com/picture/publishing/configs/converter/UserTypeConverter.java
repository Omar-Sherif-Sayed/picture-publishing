package com.picture.publishing.configs.converter;

import com.picture.publishing.enums.UserType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserType attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public UserType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null
                : Stream.of(UserType.values())
                .filter(c -> c.getId().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
