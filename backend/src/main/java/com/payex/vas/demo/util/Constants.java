package com.payex.vas.demo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ApiHeaders {
        public static final String AGREEMENT_MERCHANT_ID = "Agreement-Merchant-Id";
        public static final String SESSION_ID = "Session-Id";
        public static final String TRANSMISSION_TIME = "Transmission-Time";
        public static final String HMAC = "Hmac";
        public static final String USERNAME = "Username";
    }

    public static final String CREATE_ORDER_URL = "/create";
    public static final String GET_ORDER_URL = "/%d";
    public static final String CANCEL_ORDER_URL = "/%d/cancel";
}
