package kr.co.order.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderProductDTO {
    @NotNull
    private Long productId;
    @NotNull
    private Integer paymentPrice;
    @NotNull
    private Integer quantity;

    @Builder
    private OrderProductDTO(long productId, Integer paymentPrice, Integer quantity) {
        this.productId = productId;
        this.paymentPrice = paymentPrice;
        this.quantity = quantity;
    }

    public static OrderProductDTO createOrderProductDTO(long productId, Integer paymentPrice, Integer quantity) {
        OrderProductDTO orderProductDTO = new OrderProductDTO(productId, paymentPrice, quantity);
        return orderProductDTO;
    }
}
