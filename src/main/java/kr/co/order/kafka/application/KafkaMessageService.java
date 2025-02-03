package kr.co.order.kafka.application;

import kr.co.order.kafka.event.KafkaMessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 동기 방식
     *
     * @param event
     */
    public void sendKafkaMessage(KafkaMessageEvent event) {
        ProducerRecord<String, String> producerRecord = createProducerRecord(event.getTopic(), event.getKey(), event.getPayload());
        try {
            kafkaTemplate.send(producerRecord)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Message success : {}", event.getTopic());
                        } else {
                            log.error("error : {}", ex.getMessage());
                        }
                    });
        } catch (KafkaException ex) {
            log.error(ex.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private ProducerRecord<String, String> createProducerRecord(String topic, String key, String value) {
        return new ProducerRecord<>(topic, null, key, value, null);
    }
}
