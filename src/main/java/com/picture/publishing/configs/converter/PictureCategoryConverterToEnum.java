package com.picture.publishing.configs.converter;

import com.picture.publishing.enums.PictureCategory;
import org.springframework.core.convert.converter.Converter;

public class PictureCategoryConverterToEnum  implements Converter<String, PictureCategory> {

    @Override
    public PictureCategory convert(String source) {
        try {
            return PictureCategory.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
