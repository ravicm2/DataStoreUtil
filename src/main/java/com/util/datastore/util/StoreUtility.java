package com.util.datastore.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;

public class StoreUtility {

    private static ObjectMapper mapper = new ObjectMapper();

    public static final String FILE_NAME;

    public static final String DEFAULT_DIRECTORY_PATH;

    public static final int MAX_CHAR_LENGTH_KEY = 32;
    /**
     * Units in bytes 16 Kb
     */
    public static final int MAX_SIZE_VALUE = 16000;
    /**
     * Units in bytes 1 Gb
     */
    public static final int MAX_SIZE_FILE = 1000000000;

    static {
        FILE_NAME = "%s/local_data_store.json";
        DEFAULT_DIRECTORY_PATH = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
    }

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static boolean isBlank(String value) {
        return (value == null || value.trim().length() <= 0);
    }

    /**
     * Gets map from given json string.
     *
     * @param json the string
     * @return the map
     */
    public static Map<String, Object> getMapFromJson(String json) throws JsonProcessingException {
        try {
            return mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Gets json.
     *
     * @param object the object
     * @return the json
     */
    public static String getJson(Object object) throws Exception {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new Exception("Error occurred while converting object", e);
        }
    }

    private static void checkErrorCase(boolean isFailed, String errorMsg) {
        if (isFailed) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static void checkKey(String key) {
        checkErrorCase(isBlank(key), "Key must not be null/empty");
        checkErrorCase(key.length() > MAX_CHAR_LENGTH_KEY, "Key length must not exceed 32 characters");
    }

    public static void checkValue(Object value) {
        checkErrorCase(value != null && !(value instanceof JSONObject), "Value must be a JSON Object");
        checkErrorCase(value != null && value.toString().getBytes().length > MAX_SIZE_VALUE, "Value length must not exceed 16 kb");
    }
}
