package com.backend.allreva.member.infra.converter;

import com.backend.allreva.member.command.domain.value.RefundAccount;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// TODO: 리팩토링
@Converter
public class RefundAccountConverter implements AttributeConverter<RefundAccount, String> {

    private final String DELEMETER = ",";

    @Override
    public String convertToDatabaseColumn(RefundAccount attribute) {
        if (attribute == null) {
            return DELEMETER;
        }
        String bank = "";
        String number = "";
        if (attribute.getBank() != null) {
            bank = attribute.getBank();
        }
        if (attribute.getNumber() != null) {
            number = attribute.getNumber();
        }
        return String.join(DELEMETER, bank, number);
    }

    @Override
    public RefundAccount convertToEntityAttribute(String dbData) {
        if (dbData.equals(DELEMETER)) {
            return RefundAccount.builder()
                    .build();
        }
        String[] split = dbData.split(",");
        return RefundAccount.builder()
                .bank(split[0])
                .number(split[1])
                .build();
    }
}
