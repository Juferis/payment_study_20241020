package kr.co.order.domain.order.util;

import kr.co.order.domain.order.entity.OrderProduct;
import kr.co.order.domain.product.entity.Product;
import kr.co.order.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationUtils {
    private final ProductService productService;

    // 재고 검증
    public void validateProductStock(List<OrderProduct> orderProducts) {
        for (OrderProduct orderProduct : orderProducts) {
            Product product = productService.findProductById(orderProduct.getProductId());
            if (product.getQuantity() < orderProduct.getQuantity()) {
                throw new IllegalStateException("상품 ID " + orderProduct.getProductId() + "의 재고가 부족합니다.");
            }
        }
    }

    // 상품 존재 여부 검증
    public void validateProductExistence(List<OrderProduct> orderProducts) {
        for (OrderProduct orderProduct : orderProducts) {
            productService.findProductById(orderProduct.getProductId());
        }
    }
}
