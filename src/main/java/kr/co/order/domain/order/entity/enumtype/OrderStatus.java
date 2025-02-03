package kr.co.order.domain.order.entity.enumtype;

import kr.co.order.domain.common.enumtype.InterfaceEnumType;

public enum OrderStatus implements InterfaceEnumType {
    PAYMENT_PENDING("결제대기"),
    PAYMENT_COMPLETED("결제완료"),
    DELIVERY_READY("배송준비중"),
    DELIVERY_PROGRESS("배송중"),
    DELIVERY_COMPLETED("배송완료"),
    CANCEL("주문취소"),
    COMPLETED("거래완료");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
