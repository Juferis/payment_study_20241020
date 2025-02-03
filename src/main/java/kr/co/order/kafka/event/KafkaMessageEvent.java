package kr.co.order.kafka.event;

public interface KafkaMessageEvent {
    String getTopic();
    String getKey();
    String getPayload();
}
