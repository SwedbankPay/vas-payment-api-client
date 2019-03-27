package com.payex.vas.demo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JsonUtil {

    private static ObjectMapper mapper;

    @Autowired
    public JsonUtil(ObjectMapper objectMapper) {
        JsonUtil.mapper = objectMapper;
    }


    public static String mapToString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception ex) {
            log.error("Failed to map Class to json string", ex);
            return null;
        }
    }

    public static <T> T mapToObject(String source, Class<T> clazz) {
        try {
            return mapper.readValue(source, clazz);
        } catch (Exception ex) {
            log.error("Failed to map json string to Class", ex);
            return null;
        }
    }

    public static String getValueFromJsonString(String key, String jsonString) {
        try {
            JsonNode jNode = mapper.readTree(jsonString);
            JsonNode result = jNode.findValue(key);
            return !isNull(result)
                ? getStringValue(result)
                : null;
        } catch (Exception e) {
            return null;
        }
    }

    private static String getStringValue(JsonNode result) {
        return result.isTextual()
            ? result.textValue()
            : result.toString();
    }

    private static boolean isNull(JsonNode result) {
        return result == null || result.isNull();
    }
}
