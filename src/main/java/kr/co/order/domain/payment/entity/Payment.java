package kr.co.order.domain.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import kr.co.order.domain.common.BaseEntity;
import kr.co.order.domain.order.entity.Order;
import kr.co.order.domain.payment.entity.enumtype.PaymentCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "PAYMENT")
@Getter
@NoArgsConstructor
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_uid", length = 50)
    private String paymentUid;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(30)")
    @Enumerated(STRING)
    private PaymentCode paymentCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "payment_price", nullable = false)
    private Integer paymentPrice;

    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @Builder
    public Payment(Long id, String paymentUid, PaymentCode paymentCode, Order order, Integer paymentPrice, Boolean activated) {
        this.id = id;
        this.paymentUid = paymentUid;
        this.paymentCode = paymentCode;
        this.order = order;
        this.paymentPrice = paymentPrice;
        this.activated = activated;
    }

    public static Payment createPayment(String paymentUid, PaymentCode paymentCode, Order order) {
        Payment payment = new Payment(null, paymentUid, paymentCode, order, order.getTotalAmount(), true);
        return payment;
    }
}
