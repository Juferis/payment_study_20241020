package kr.co.order.domain.order.dto;

public enum OrderSearchCondition {
    ORDER_ID("주문 번호"),
    USER_NAME("유저 이름"),
    PRODUCT_NAME("상품 이름");

    private String value;

    OrderSearchCondition(String value) {
        this.value = value;
    }
}
