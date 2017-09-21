package example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import example.exception.ValidationException;

import java.io.IOException;
import java.util.List;

public class JsonHelper {
    private static ObjectMapper mapper = init();

    private static final String JSON_PARSE_ERROR = "Erro no parse do json: ";

    private static ObjectMapper init() {
        mapper = new ObjectMapper();

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    public static String objectToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new ValidationException(JSON_PARSE_ERROR + e.getMessage(), e);
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ValidationException(JSON_PARSE_ERROR + e.getMessage(), e);
        }
    }

    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz) );
        } catch (IOException e) {
            throw new ValidationException(JSON_PARSE_ERROR + e.getMessage(), e);
        }
    }
}
