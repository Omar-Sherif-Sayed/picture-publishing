package com.picture.publishing.configs.converter;

import com.picture.publishing.enums.PictureStatus;
import org.springframework.core.convert.converter.Converter;

public class PictureStatusConverterToEnum implements Converter<String, PictureStatus> {

    @Override
    public PictureStatus convert(String source) {
        try {
            return PictureStatus.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
