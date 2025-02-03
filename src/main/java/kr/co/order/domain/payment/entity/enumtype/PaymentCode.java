package kr.co.order.domain.payment.entity.enumtype;

import kr.co.order.domain.common.enumtype.InterfaceEnumType;

public enum PaymentCode implements InterfaceEnumType {
    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    TRANSFER("계좌이체"),
    MOBILE("휴대폰소액결제");

    private final String value;

    PaymentCode(String value) {
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
