package com.payex.vas.demo.config;

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
    private final VasMultiPayServerApi vasMultiPayServerApi = new VasMultiPayServerApi();

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class VasPaymentServerApi {
        private int apiTimeout;
        private String baseUrl;
        private String apiKey;
        private String apiSecret;
    }

    @Getter
    @Setter@NoArgsConstructor(access = AccessLevel.PRIVATE) //TODO:: Linjeskift
    public static class VasMultiPayServerApi {
        private int apiTimeout;
        private String baseUrl;
        private String baseOrderUrl; //TODO:: Denne trengs vel egntlig ikke, kan slås sammen med URL over?
        private String apiKey;
        private String apiSecret;
    }
}
