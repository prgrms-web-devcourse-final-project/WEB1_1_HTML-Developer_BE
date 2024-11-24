package com.backend.allreva.member.infra.converter;

import com.backend.allreva.member.command.domain.value.RefundAccountInfo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RefundAccountConverter implements AttributeConverter<RefundAccountInfo, String> {

    private final String DELEMETER = ",";

    @Override
    public String convertToDatabaseColumn(RefundAccountInfo attribute) {
        return String.join(DELEMETER, attribute.getBank(), attribute.getNumber());
    }

    @Override
    public RefundAccountInfo convertToEntityAttribute(String dbData) {
        String[] split = dbData.split(",");
        return RefundAccountInfo.builder()
                .bank(split[0])
                .number(split[1])
                .build();
    }
}
