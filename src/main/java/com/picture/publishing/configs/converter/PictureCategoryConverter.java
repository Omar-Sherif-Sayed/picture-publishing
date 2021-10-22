package com.picture.publishing.configs.converter;

import com.picture.publishing.enums.PictureCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PictureCategoryConverter implements AttributeConverter<PictureCategory, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PictureCategory attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public PictureCategory convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null
                : Stream.of(PictureCategory.values())
                .filter(c -> c.getId().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
