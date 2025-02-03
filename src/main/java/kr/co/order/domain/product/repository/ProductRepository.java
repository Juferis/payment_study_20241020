package kr.co.order.domain.product.repository;

import kr.co.order.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface  ProductRepository extends JpaRepository<Product, Long> {

}

