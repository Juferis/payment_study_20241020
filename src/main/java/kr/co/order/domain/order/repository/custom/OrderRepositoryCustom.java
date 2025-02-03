package kr.co.order.domain.order.repository.custom;

import kr.co.order.domain.order.dto.OrderSearchDTO;
import kr.co.order.domain.order.dto.OrderSearchDetailResponseDTO;
import kr.co.order.domain.order.dto.OrderSearchListResponseDTO;

import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderSearchListResponseDTO> searchOrderList(Long userId);
    List<OrderSearchListResponseDTO> searchOrderListV2(OrderSearchDTO orderSearchDTO);
    int searchOrderListV2TotalCount(OrderSearchDTO orderSearchDTO);
    OrderSearchDetailResponseDTO searchOrderDetail(Long userId, Long orderId);
}
