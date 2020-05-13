package com.swedbankpay.vas.demo.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final VasPaymentServerApi vasPaymentServerApi = new VasPaymentServerApi();

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class VasPaymentServerApi {
        private int apiTimeout;
        private String baseUrl;
        private String apiKey;
        private String apiSecret;
    }
}
