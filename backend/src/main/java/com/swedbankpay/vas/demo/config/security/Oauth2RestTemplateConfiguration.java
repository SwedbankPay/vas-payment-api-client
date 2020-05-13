package com.swedbankpay.vas.demo.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.swedbankpay.vas.demo.config.ApplicationProperties;
import com.swedbankpay.vas.demo.config.ExternalRequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;

@Slf4j
@EnableOAuth2Client
@Configuration
@RequiredArgsConstructor
public class Oauth2RestTemplateConfiguration {

    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    @Bean
    @ConfigurationProperties("vas-payment-api.oauth2.client")
    protected ClientCredentialsResourceDetails oAuthDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    protected RestTemplate restTemplate() {
        var restTemplate = new OAuth2RestTemplate(oAuthDetails());
        restTemplate.setInterceptors(ImmutableList.of(externalRequestInterceptor()));
        restTemplate.setRequestFactory(httpRequestFactory());
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        var requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setReadTimeout(applicationProperties.getVasPaymentServerApi().getApiTimeout());
        requestFactory.setConnectTimeout(applicationProperties.getVasPaymentServerApi().getApiTimeout());
        return new BufferingClientHttpRequestFactory(requestFactory);
    }

    @Bean
    public ClientHttpRequestInterceptor externalRequestInterceptor() {
        return new ExternalRequestInterceptor(applicationProperties);
    }

    @Bean
    public HttpMessageConverter messageConverter() {
        return mappingJackson2HttpMessageConverter();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        var jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }
}
