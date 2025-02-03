package kr.co.order.domain.product.entity.enumtype;

import kr.co.order.domain.common.enumtype.InterfaceEnumType;

public enum ProductStatus implements InterfaceEnumType {
    PENDING("판매대기"),
    ON_SALE("판매중"),
    CLOSED("판매종료");

    private final String value;

    ProductStatus(String value) {
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
