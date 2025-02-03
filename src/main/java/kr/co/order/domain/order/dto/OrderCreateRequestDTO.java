package kr.co.order.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import kr.co.order.domain.order.entity.enumtype.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDTO {
    @NotNull
    private Long userId;
    @NotNull
    private List<OrderProductDTO> orderProducts;
    @NotNull
    private Integer totalAmount;
    @NotNull
    private OrderStatus orderStatus;

    @Builder
    public OrderCreateRequestDTO(Long userId, List<OrderProductDTO> orderProducts, Integer totalAmount, OrderStatus orderStatus) {
        this.userId = userId;
        this.orderProducts = orderProducts;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }
}
