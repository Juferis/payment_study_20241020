package kr.co.order.domain.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import kr.co.order.domain.common.BaseEntity;
import kr.co.order.domain.order.entity.enumtype.OrderStatus;
import kr.co.order.domain.payment.entity.Payment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "ORDER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(20)")
    @Enumerated(STRING)
    private OrderStatus status;

    @Column(name = "order_at")
    private LocalDateTime orderAt;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

    @Builder
    public Order(Long id, Long userId, OrderStatus status, LocalDateTime orderAt, Integer totalAmount, List<OrderProduct> orderProducts, Payment payment) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.orderAt = orderAt;
        this.totalAmount = totalAmount;
        this.orderProducts = orderProducts;
        this.payment = payment;
    }

    public void changeOrderStatus(OrderStatus orderStatus){
        this.status = orderStatus;
    }

    public void completeOrder(Payment payment) {
        this.payment = payment;
        changeOrderStatus(OrderStatus.PAYMENT_COMPLETED);
    }

    /**
     * 생성 메서드
     */
    public static Order createOrder(Long userId, Integer totalAmount, List<OrderProduct> orderProducts) {
        Order order = new Order(null, userId, OrderStatus.PAYMENT_PENDING, null, totalAmount, orderProducts, null);
        orderProducts.forEach(orderProduct -> orderProduct.setOrder(order));
        return order;
    }
}
