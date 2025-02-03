package kr.co.order.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import kr.co.order.domain.order.entity.enumtype.OrderStatus;
import kr.co.order.domain.payment.entity.enumtype.PaymentCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderSearchDetailResponseDTO {

    @NotNull
    private Long orderId;

    @NotNull
    private Long userId;

    @NotNull
    private OrderStatus status;

    @NotNull
    private LocalDateTime orderAt;

    @NotNull
    private Integer totalAmount;

    @NotNull
    private PaymentCode paymentCode;

    @NotNull
    private String userName;

    @NotNull
    private Long productId;

    @NotNull
    private Integer paymentPrice;

    @NotNull
    private Integer productQuantity;

}
