package com.backend.allreva.member.infra.converter;

import com.backend.allreva.member.command.domain.value.RefundAccount;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RefundAccountConverter implements AttributeConverter<RefundAccount, String> {

    private final String DELEMETER = ",";

    @Override
    public String convertToDatabaseColumn(RefundAccount attribute) {
        return String.join(DELEMETER, attribute.getBank(), attribute.getNumber());
    }

    @Override
    public RefundAccount convertToEntityAttribute(String dbData) {
        String[] split = dbData.split(",");
        return RefundAccount.builder()
                .bank(split[0])
                .number(split[1])
                .build();
    }
}
