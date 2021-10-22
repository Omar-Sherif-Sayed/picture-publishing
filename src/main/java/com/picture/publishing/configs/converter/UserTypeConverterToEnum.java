package com.picture.publishing.configs.converter;

import com.picture.publishing.enums.UserType;
import org.springframework.core.convert.converter.Converter;

public class UserTypeConverterToEnum implements Converter<String, UserType> {

    @Override
    public UserType convert(String source) {
        try {
            return UserType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
