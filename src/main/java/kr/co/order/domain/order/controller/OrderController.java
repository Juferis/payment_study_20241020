package kr.co.order.domain.order.controller;

import kr.co.order.domain.order.dto.*;
import kr.co.order.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 정보 저장
     */
    @PostMapping("/acceptance")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Validated OrderCreateRequestDTO orderCreateRequestDTO) {
        Long orderId = orderService.orderPrepare(orderCreateRequestDTO); // 주문 생성
        OrderResponseDTO response = new OrderResponseDTO(orderId,"주문이 성공적으로 생성되었습니다.");
        return ResponseEntity.ok(response); // 생성된 주문 정보 반환
    }

    /**
     * 주문 상태 결제 상태로 변경
     */
    @PostMapping("/request")
    public ResponseEntity<OrderResponseDTO> requestOrder(@RequestBody @Validated OrderCompleteRequestDTO orderCompleteRequestDTO) {
        orderService.orderCompleted(orderCompleteRequestDTO);
        return ResponseEntity.ok(new OrderResponseDTO(orderCompleteRequestDTO.getOrderId(), "주문이 완료되었습니다."));
    }

    @GetMapping("/search-list")
    public ResponseEntity<List<OrderSearchListResponseDTO>> searchOrderList(@RequestParam(name = "userId") Long userId) {
        List<OrderSearchListResponseDTO> responseDTOList = orderService.searchOrderList(userId);
        log.trace("result : " + responseDTOList);
        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping("/v2/search-list")
    public ResponseEntity<?> searchOrderListV2(OrderSearchDTO orderSearchDTO) {
        return ResponseEntity.ok(orderService.searchOrderListV2(orderSearchDTO));
    }


    @GetMapping("/detail")
    public ResponseEntity<OrderSearchDetailResponseDTO> searchOrderDetail(
            @RequestParam Long userId,
            @RequestParam Long orderId) {
        OrderSearchDetailResponseDTO responseDTO = orderService.searchOrderDetail(userId, orderId);
        return ResponseEntity.ok(responseDTO);
    }


}
