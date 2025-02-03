package kr.co.order.domain.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderSearchListResponse {
    private List<OrderSearchListResponseDTO> data;
    private int totalCount;

    @Builder
    public OrderSearchListResponse(List<OrderSearchListResponseDTO> data, int totalCount) {
        this.data = data;
        this.totalCount = totalCount;
    }
}
