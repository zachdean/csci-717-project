package com.zachdean.lambda;

import java.text.SimpleDateFormat;
import java.util.Date;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class DateAttributeConverter implements AttributeConverter<Date> {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final SimpleDateFormat FORMATER = new SimpleDateFormat(DATE_PATTERN);

    @Override
    public AttributeValue transformFrom(Date input) {

        return AttributeValue.builder()
                .s(FORMATER.format(input))
                .build();
    }

    @Override
    public Date transformTo(AttributeValue input) {
        try {
            return FORMATER.parse(input.s());

        } catch (Exception e) {
            return new Date();
        }
    }

    @Override
    public EnhancedType<Date> type() {
        return EnhancedType.of(Date.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }

}
