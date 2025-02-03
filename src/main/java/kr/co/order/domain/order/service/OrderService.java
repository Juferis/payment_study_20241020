package kr.co.order.domain.order.service;

import kr.co.order.domain.order.dto.*;
import kr.co.order.domain.order.entity.Order;
import kr.co.order.domain.order.entity.OrderProduct;
import kr.co.order.domain.order.entity.enumtype.OrderProductStatus;
import kr.co.order.domain.order.repository.OrderRepository;
import kr.co.order.domain.order.validation.OrderCompletedValidationService;
import kr.co.order.domain.order.validation.OrderCreateValidationService;
import kr.co.order.domain.payment.entity.Payment;
import kr.co.order.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderCreateValidationService createValidationService;
    private final OrderCompletedValidationService completedValidationService;

    /**
     * 결제 전 테이블 생성
     */
    public Long orderPrepare(OrderCreateRequestDTO orderCreateRequestDTO) {
        List<OrderProduct> orderProducts = convertToOrderProductList(orderCreateRequestDTO.getOrderProducts());

        // 주문 전 검증
        createValidationService.validateOrderCreation(orderProducts);
        createValidationService.validatePaymentAmount(orderCreateRequestDTO.getOrderProducts(), orderCreateRequestDTO.getTotalAmount());

        Order order = Order.createOrder(orderCreateRequestDTO.getUserId(), orderCreateRequestDTO.getTotalAmount(), orderProducts);

        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 결제 완료
     */
    public Long orderCompleted(OrderCompleteRequestDTO orderCompleteRequestDTO) {
        Order order = orderRepository.getReferenceById(orderCompleteRequestDTO.getOrderId());

        // 주문 전 검증
        completedValidationService.validateNonExistentOrder(orderCompleteRequestDTO.getOrderId(), order.getId());
        completedValidationService.validateOrderCreation(order.getOrderProducts());
        completedValidationService.validatePaymentAmount(order.getOrderProducts(), order.getTotalAmount());

        Payment payment = Payment.createPayment(orderCompleteRequestDTO.getPaymentUid(), orderCompleteRequestDTO.getPaymentCode(), order);
        paymentRepository.save(payment);

        order.completeOrder(payment);
        return order.getId();
    }

    /**
     * 주문 조회
     */
    public List<OrderSearchListResponseDTO> searchOrderList(Long userId) {
        return orderRepository.searchOrderList(userId);
    }

    public OrderSearchListResponse searchOrderListV2(OrderSearchDTO orderSearchDTO) {
        List<OrderSearchListResponseDTO> list = orderRepository.searchOrderListV2(orderSearchDTO);
        int totalCount = orderRepository.searchOrderListV2TotalCount(orderSearchDTO);
        return new OrderSearchListResponse(list, totalCount);
    }

    /**
     * 주문 상세 조회
     */
    public OrderSearchDetailResponseDTO searchOrderDetail(Long userId, Long orderId) {
        return orderRepository.searchOrderDetail(userId, orderId);
    }

    private List<OrderProduct> convertToOrderProductList(List<OrderProductDTO> orderProductDTOs) {
        return orderProductDTOs.stream()
                .map(dto -> OrderProduct.builder()
                        .productId(dto.getProductId())
                        .paymentPrice(dto.getPaymentPrice())
                        .quantity(dto.getQuantity())
                        .status(OrderProductStatus.PAYMENT_PENDING) // 기본 상태 설정
                        .build())
                .collect(Collectors.toList());
    }
}
