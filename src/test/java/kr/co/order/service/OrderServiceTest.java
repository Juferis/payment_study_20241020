package kr.co.order.service;

import kr.co.order.domain.order.dto.OrderCompleteRequestDTO;
import kr.co.order.domain.order.dto.OrderCreateRequestDTO;
import kr.co.order.domain.order.dto.OrderProductDTO;
import kr.co.order.domain.order.entity.Order;
import kr.co.order.domain.order.entity.OrderProduct;
import kr.co.order.domain.order.entity.enumtype.OrderStatus;
import kr.co.order.domain.order.repository.OrderRepository;
import kr.co.order.domain.order.service.OrderService;
import kr.co.order.domain.order.validation.OrderCompletedValidationService;
import kr.co.order.domain.order.validation.OrderCreateValidationService;
import kr.co.order.domain.payment.entity.Payment;
import kr.co.order.domain.payment.entity.enumtype.PaymentCode;
import kr.co.order.domain.payment.repository.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderCreateValidationService createValidationService;

    @Mock
    private OrderCompletedValidationService completedValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("결제 전 테이블 생성 (orderPrepare) 테스트")
    public void orderPrepareTest() throws Exception {
        // given
        OrderCreateRequestDTO requestDTO = OrderCreateRequestDTO.builder()
                .userId(1L)
                .orderProducts(List.of(
                        OrderProductDTO.createOrderProductDTO(101L, 2000, 1),
                        OrderProductDTO.createOrderProductDTO(102L, 3000, 1)
                ))
                .totalAmount(5000)
                .orderStatus(OrderStatus.PAYMENT_PENDING)
                .build();

        List<OrderProduct> orderProducts = List.of(
                OrderProduct.builder().productId(101L).paymentPrice(2000).quantity(1).build(),
                OrderProduct.builder().productId(102L).paymentPrice(3000).quantity(1).build()
        );

        createValidationService.validateOrderCreation(orderProducts);
        createValidationService.validatePaymentAmount(requestDTO.getOrderProducts(),requestDTO.getTotalAmount());

        Order order = Order.createOrder(1L, 5000, orderProducts);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // when
        Long orderId = orderService.orderPrepare(requestDTO);


        // then
        assertThat(orderId).isNotNull();
        verify(createValidationService, times(1)).validateOrderCreation(orderProducts);
        verify(createValidationService, times(1)).validatePaymentAmount(requestDTO.getOrderProducts(), requestDTO.getTotalAmount());
        verify(orderRepository, times(1)).save(any(Order.class));


        // 저장된 Order 객체 캡처
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(1)).save(orderCaptor.capture());
        Order capturedOrder = orderCaptor.getValue();

        // 캡처된 객체 필드 검증
        assertThat(capturedOrder.getUserId()).isEqualTo(1L);
        assertThat(capturedOrder.getTotalAmount()).isEqualTo(5000);
        assertThat(capturedOrder.getOrderProducts()).hasSize(2);
    }

    @Test
    @DisplayName("결제 완료 테이블 생성 (orderCompleted) 테스트")
    public void orderCompletedTest() throws Exception {
        // given
        OrderCompleteRequestDTO requestDTO = new OrderCompleteRequestDTO(1L, "PAY123456", PaymentCode.CARD);

        Order order = Order.builder()
                .id(1L)
                .totalAmount(5000)
                .orderProducts(List.of(
                        OrderProduct.builder().productId(101L).paymentPrice(2000).quantity(1).build(),
                        OrderProduct.builder().productId(102L).paymentPrice(3000).quantity(1).build()
                ))
                .build();

        Payment payment = Payment.builder()
                .id(1L)
                .paymentUid("PAY123456")
                .paymentCode(PaymentCode.CARD)
                .order(order)
                .build();

        when(orderRepository.getReferenceById(1L)).thenReturn(order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        // when
        Long orderId = orderService.orderCompleted(requestDTO);

        // then
        assertThat(orderId).isNotNull();
        verify(completedValidationService, times(1)).validateNonExistentOrder(requestDTO.getOrderId(), order.getId());
        verify(completedValidationService, times(1)).validateOrderCreation(order.getOrderProducts());
        verify(completedValidationService, times(1)).validatePaymentAmount(order.getOrderProducts(), order.getTotalAmount());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }
}
