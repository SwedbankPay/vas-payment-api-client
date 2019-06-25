package com.payex.vas.demo.rest.util;

import com.google.common.base.Stopwatch;
import com.payex.vas.demo.config.security.SecurityUtils;
import com.payex.vas.demo.util.JsonUtil;
import com.payex.vas.demo.util.error.InternalServerErrorException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Supplier;

public class ControllerExecutorHelper {

    public static <S> ResponseEntity<S> executeAndLogRequest(Logger logger, String method, Supplier<ResponseEntity<S>> operation) {
        return executeAndLogRequest(logger, method, null, operation);
    }

    public static <S> ResponseEntity<S> executeAndLogRequest(Logger logger, String method, Object payload, Supplier<ResponseEntity<S>> operation) {
        var stopwatch = Stopwatch.createStarted();
        var status = "";
        try {
            var payloadStr = payload == null ? null : JsonUtil.mapToString(payload);
            var client = SecurityUtils.getCurrentUserLogin().orElse(null);
            logger.info("# invoked {} ({}) by user: '{}', payload: '{}'",
                method,
                getCurrentRequestUri(),
                client,
                payloadStr);

            var responseEntity = operation.get();
            status = getStatusText(responseEntity.getStatusCode());
            return responseEntity;
        } catch (ResponseStatusException ex) {
            status = getStatusText(ex.getStatus());
            throw ex;
        } catch (Exception ex) {
            logger.error("Something unexpected happened {}", ex.getMessage(), ex);
            throw new InternalServerErrorException("Something happened: " + ex.getMessage());
        } finally {
            logger.info("# finished [{}] {}, executeTime: {}", method, status, stopwatch.stop());
        }
    }

    private static String getStatusText(HttpStatus httpStatus) {
        return "(status: '" + httpStatus.value() + " - " + httpStatus.getReasonPhrase() + "')";
    }

    private static String getCurrentRequestUri() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        if (!StringUtils.isEmpty(request.getQueryString()))
            uri = uri + "?" + request.getQueryString();

        return uri;
    }
}
