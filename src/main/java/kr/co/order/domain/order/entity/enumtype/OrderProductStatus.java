package kr.co.order.domain.order.entity.enumtype;

import kr.co.order.domain.common.enumtype.InterfaceEnumType;

public enum OrderProductStatus implements InterfaceEnumType {
    PAYMENT_PENDING("결제대기"),
    PAYMENT_COMPLETED("결제완료"),
    RETURN_IN_PROGRESS("반품진행중"),
    RETURN_COMPLETED("반품완료"),
    REFUND_IN_PROGRESS("환불진행중"),
    REFUND_COMPLETED("환불완료");

    private final String value;

    OrderProductStatus(String value) {
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
