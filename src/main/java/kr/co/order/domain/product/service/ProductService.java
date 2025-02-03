package kr.co.order.domain.product.service;

import jakarta.annotation.PostConstruct;
import kr.co.order.domain.product.entity.Product;
import kr.co.order.domain.product.entity.enumtype.ProductStatus;
import kr.co.order.domain.product.repository.ProductRepository;
import kr.co.order.exception.NotEnoughStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 모든 상품 조회
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // 특정 상품 조회
    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + id));
    }

    // 장바구니에 담기
    public void addToCart(Long productId, int quantity) {
        Product product = findProductById(productId);
        if (product.getQuantity() < quantity) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        product.downQuantity(quantity);
    }

//    @PostConstruct
//    public void initData() {
//        productRepository.saveAll(List.of(
//                new Product(null, ProductStatus.ON_SALE, "상품 A", 1000, 10),
//                new Product(null, ProductStatus.ON_SALE, "상품 B", 2000, 5),
//                new Product(null, ProductStatus.ON_SALE, "상품 C", 3000, 2)
//        ));
//    }
}
