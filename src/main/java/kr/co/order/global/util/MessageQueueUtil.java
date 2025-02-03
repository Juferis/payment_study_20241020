package kr.co.order.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageQueueUtil {
    /**
     * object -> string payload
     * @param payload
     * @param mapper
     * @return
     */
    public static String convertPayload(Object payload, ObjectMapper mapper) {
        try {
            return mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("message parsing error");
        }
    }
}
