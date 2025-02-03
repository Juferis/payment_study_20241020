package kr.co.order.domain.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import kr.co.order.domain.common.BaseEntity;
import kr.co.order.domain.order.entity.enumtype.OrderProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "ORDER_PRODUCT")
@Getter
@NoArgsConstructor
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(20)")
    @Enumerated(STRING)
    private OrderProductStatus status;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "payment_price", nullable = false)
    private Integer paymentPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Builder
    public OrderProduct(Long id, Order order, OrderProductStatus status, Long productId, Integer paymentPrice, Integer quantity) {
        this.id = id;
        this.order = order;
        this.status = status;
        this.productId = productId;
        this.paymentPrice = paymentPrice;
        this.quantity = quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
