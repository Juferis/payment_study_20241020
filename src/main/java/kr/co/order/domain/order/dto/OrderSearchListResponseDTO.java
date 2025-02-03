package kr.co.order.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import kr.co.order.domain.order.entity.enumtype.OrderStatus;
import kr.co.order.domain.payment.entity.enumtype.PaymentCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderSearchListResponseDTO {

    private Long orderId;

    private OrderStatus status;

    private PaymentCode paymentCode;

    private String userName;

    private String address;

    private Integer totalAmount;

    private LocalDateTime createdAt;

    @Builder
    public OrderSearchListResponseDTO(Long orderId, OrderStatus status, PaymentCode paymentCode, String userName, String address, Integer totalAmount, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.status = status;
        this.paymentCode = paymentCode;
        this.userName = userName;
        this.address = address;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }
}
