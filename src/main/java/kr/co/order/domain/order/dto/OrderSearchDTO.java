package kr.co.order.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor
@ToString
public class OrderSearchDTO {

    private String searchContent;
    private OrderSearchCondition searchCondition;
    private LocalDate startOrderDate;
    private LocalDate endOrderDate;
    private int pageSize;
    private int pageNumber;

    @Builder
    public OrderSearchDTO(String searchContent, OrderSearchCondition searchCondition, LocalDate startOrderDate, LocalDate endOrderDate, int pageSize, int pageNumber) {
        this.searchContent = searchContent;
        this.searchCondition = searchCondition;
        this.startOrderDate = startOrderDate;
        this.endOrderDate = endOrderDate;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    @JsonIgnore
    public Long getOrderId() {
        if(searchCondition != OrderSearchCondition.ORDER_ID) {
            return null;
        }
        Objects.requireNonNull(searchContent);
        return Long.valueOf(searchContent);
    }

    @JsonIgnore
    public String getUserName() {
        if(searchCondition != OrderSearchCondition.USER_NAME) {
            return null;
        }
        return searchContent;
    }

    @JsonIgnore
    public String getProductName() {
        if(searchCondition != OrderSearchCondition.PRODUCT_NAME) {
            return null;
        }
        return searchContent;
    }
}
