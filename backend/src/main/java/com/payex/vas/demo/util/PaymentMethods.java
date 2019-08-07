package com.payex.vas.demo.util;


import lombok.Getter;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

@Getter
    public enum PaymentMethods { //TODO:: Denne hører hjemme under "com.payex.vas.demo.domain.payex.request" package (om det i det hele tatt trenger å være en enum).. Formatter klassen!
        ALL("ALL"),
        INVOICE("INVOICE"),
        ONLINE("ONLINE");

        private String value;

        PaymentMethods(String value){this.value = value;}

        @Override
        @JsonValue
    public String toString() {return String.valueOf(value);}

    @JsonCreator
    public static PaymentMethods fromValue(String text){
            for (PaymentMethods dt : PaymentMethods.values()){
                if(String.valueOf(dt.value).equalsIgnoreCase(text)){
                    return dt;
                }
            }
            return null;
        }
    }

