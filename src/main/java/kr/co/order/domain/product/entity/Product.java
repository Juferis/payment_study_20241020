package kr.co.order.domain.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GenerationType;
import kr.co.order.domain.common.BaseEntity;
import kr.co.order.domain.product.entity.enumtype.ProductStatus;
import kr.co.order.exception.NotEnoughStockException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;


@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@Getter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(20)")
    @Enumerated(STRING)
    private ProductStatus status;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Builder
    public Product(Long id, ProductStatus status, String name, Integer price, Integer quantity) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void downQuantity(int quantity) {
        int inStock = this.quantity - quantity;
        if(inStock < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.quantity = inStock;
    }
}
