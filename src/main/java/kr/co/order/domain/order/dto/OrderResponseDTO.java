package kr.co.order.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderResponseDTO {
    @NotNull
    private Long orderId;
    private String resultMessage;

    public OrderResponseDTO(Long orderId, String resultMessage) {
        this.orderId = orderId;
        this.resultMessage = resultMessage;
    }
}
