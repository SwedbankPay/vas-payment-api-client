package com.payex.vas.demo.domain.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum PaymentInstrumentType {
    PREPAID_CARD("Prepaid card"),
    CREDIT_CARD("Credit card"),
    VALUE_CODE("Value code");

    private String value;

    PaymentInstrumentType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static PaymentInstrumentType fromValue(String text) {
        for (PaymentInstrumentType b : PaymentInstrumentType.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
