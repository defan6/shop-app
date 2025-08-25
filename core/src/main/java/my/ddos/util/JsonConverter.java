package my.ddos.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JsonConverter {


    private final ObjectMapper objectMapper;


    public<T> T toObject(String message, Class<T> clazz){
        try {
            return objectMapper.readValue(message, clazz);
        } catch (JsonProcessingException e) {
            log.error("Json deserializing exception: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Json serializing exception: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
