package kr.co.order.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.order.domain.payment.entity.enumtype.PaymentCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCompleteRequestDTO {
    @NotNull
    private Long orderId;
    @NotNull
    @Size(max = 50)
    private String paymentUid;
    @NotNull
    private PaymentCode paymentCode;

    @Builder
    public OrderCompleteRequestDTO(Long orderId, String paymentUid, PaymentCode paymentCode) {
         this.orderId = orderId;
         this.paymentUid = paymentUid;
         this.paymentCode = paymentCode;
    }
}
