package com.payex.vas.demo.domain.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CurrencyIso {
    NOK("NOK"),
    SEK("SEK"),
    DKK("DKK"),
    EUR("EUR"),
    USD("USD");

    private String value;

    CurrencyIso(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static CurrencyIso fromValue(String text) {
        for (CurrencyIso b : CurrencyIso.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
