package com.hedatou.wedding.common;

import java.io.IOException;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            if (obj == null)
                return null;
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            if (StringUtils.isEmpty(json))
                return null;
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
