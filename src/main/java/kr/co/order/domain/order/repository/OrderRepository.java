package kr.co.order.domain.order.repository;

import kr.co.order.domain.order.entity.Order;
import kr.co.order.domain.order.repository.custom.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
