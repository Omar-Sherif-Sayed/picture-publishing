package com.picture.publishing.configs.converter;

import com.picture.publishing.enums.PictureStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PictureStatusConverter implements AttributeConverter<PictureStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PictureStatus attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public PictureStatus convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null
                : Stream.of(PictureStatus.values())
                .filter(c -> c.getId().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
