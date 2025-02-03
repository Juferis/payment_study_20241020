package kr.co.order.domain.order.validation;

import kr.co.order.domain.order.dto.OrderProductDTO;
import kr.co.order.domain.order.entity.OrderProduct;
import kr.co.order.domain.order.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCreateValidationService {
    private final ValidationUtils validationUtils;

    public void validateOrderCreation(List<OrderProduct> orderProducts) {
        validationUtils.validateProductStock(orderProducts);
        validationUtils.validateProductExistence(orderProducts);
    }

    // 결제 금액 검증
    public void validatePaymentAmount(List<OrderProductDTO> orderProducts, Integer totalAmount) {
        int calculatedTotal = orderProducts.stream()
                .mapToInt(product -> product.getPaymentPrice() * product.getQuantity())
                .sum();

        if (calculatedTotal != totalAmount) {
            throw new IllegalStateException("요청된 총 결제 금액과 상품 총 금액이 일치하지 않습니다.");
        }
    }
}