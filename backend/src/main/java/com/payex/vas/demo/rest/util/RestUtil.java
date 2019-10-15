package com.payex.vas.demo.rest.util;

import com.google.common.base.Stopwatch;
import com.payex.vas.demo.config.ApplicationProperties;
import com.payex.vas.demo.util.JsonUtil;
import com.payex.vas.demo.util.error.BadRequestException;
import com.payex.vas.demo.util.error.InternalServerErrorException;
import com.payex.vas.demo.util.error.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static com.payex.vas.demo.util.Constants.ApiHeaders.AGREEMENT_MERCHANT_ID;

@Slf4j
@AllArgsConstructor
@Component
public class RestUtil {
    private final ApplicationProperties applicationProperties;
    private final RestTemplate restTemplate;

    public <T> T executeForEntity(String url, HttpMethod httpMethod, HttpEntity payload, Class<T> entityClass) {
        var stopwatch = Stopwatch.createStarted();
        try {
            log.debug("About to make a {} REST call to {}", httpMethod, url);

            var response = restTemplate.exchange(url, httpMethod, payload, entityClass);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            return handleHttpStatusCodeException(url, ex);
        } catch (Exception ex) {
            log.error("Got exception while calling '{}'", url, ex);
            throw new InternalServerErrorException("Failed to call API");
        } finally {
            log.debug("Finished {} for {} in {}.", httpMethod, entityClass.getSimpleName(), stopwatch.stop());
        }
    }

    private <T> T handleHttpStatusCodeException(String url, HttpStatusCodeException ex) {
        if (ex.getStatusCode().is4xxClientError()) { //Don't log stacktrace
            var responseBody = ex.getResponseBodyAsString();
            log.warn("Failed while invoking '{}'. Got status: '{} - {}', with body: {}",
                url,
                ex.getStatusCode().value(),
                ex.getStatusCode().getReasonPhrase(),
                responseBody);
            var backendMsg = JsonUtil.getValueFromJsonString("message", responseBody);
            if (StringUtils.isEmpty(backendMsg))
                backendMsg = JsonUtil.getValueFromJsonString("title", responseBody);

            var exceptionMsg = !StringUtils.isEmpty(backendMsg) ? backendMsg : responseBody;
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new NotFoundException(exceptionMsg);
            else
                throw new BadRequestException(exceptionMsg);
        } else {
            log.error("Got exception while calling '{}'", url, ex);
            throw new InternalServerErrorException(ex.getResponseBodyAsString(), ex);
        }
    }

    public String getUrl(String endpoint) {
        return applicationProperties.getVasPaymentServerApi().getBaseUrl() + endpoint;
    }

    public HttpEntity createPayload(Object payload, String agreementMerchantId) {
        var headers = new HttpHeaders();
        headers.add(AGREEMENT_MERCHANT_ID, agreementMerchantId);
        return new HttpEntity<>(payload, headers);
    }
}
