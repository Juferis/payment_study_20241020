package kr.co.order.domain.order.repository.custom;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.order.domain.order.dto.OrderSearchDTO;
import kr.co.order.domain.order.dto.OrderSearchDetailResponseDTO;
import kr.co.order.domain.order.dto.OrderSearchListResponseDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.co.order.domain.order.entity.QOrder.order;
import static kr.co.order.domain.order.repository.RepositoryUtils.*;
import static kr.co.order.domain.product.entity.QProduct.product;
import static kr.co.order.domain.user.entity.QUser.user;
import static kr.co.order.domain.payment.entity.QPayment.payment;
import static kr.co.order.domain.order.entity.QOrderProduct.orderProduct;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderSearchListResponseDTO> searchOrderList(Long userId) {
        return queryFactory
                .select(Projections.constructor(OrderSearchListResponseDTO.class,
                        order.id,
                        order.status,
                        order.orderAt,
                        order.totalAmount
                ))
                .from(order)
                .where(order.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<OrderSearchListResponseDTO> searchOrderListV2(OrderSearchDTO orderSearchDTO) {
        return queryFactory
                .select(Projections.constructor(OrderSearchListResponseDTO.class,
                        order.id.as("orderId"),
                        order.status,
                        payment.paymentCode,
                        user.name.as("userName"),
                        user.address,
                        order.totalAmount,
                        order.createdAt
                ))
                .from(order)
                .join(payment)
                .on(order.payment.eq(payment))
                .join(user)
                .on(order.userId.eq(user.id))
                .join(orderProduct)
                .on(order.id.eq(orderProduct.order.id))
                .join(product)
                .on(orderProduct.productId.eq(product.id))
                .where(
                        eqNumber(order.id, orderSearchDTO.getOrderId()),
                        containsString(user.name, orderSearchDTO.getUserName()),
                        containsString(product.name, orderSearchDTO.getProductName())
                )
                .offset(orderSearchDTO.getPageNumber())
                .limit(orderSearchDTO.getPageSize())
                .groupBy(order.id)
                .orderBy(order.id.desc())
                .fetch();
    }

    @Override
    public int searchOrderListV2TotalCount(OrderSearchDTO orderSearchDTO) {
        return Math.toIntExact(queryFactory
                .select(order.id.count())
                .from(order)
                .join(payment).on(order.payment.eq(payment))
                .join(user).on(order.userId.eq(user.id))
                .join(orderProduct).on(order.id.eq(orderProduct.order.id))
                .join(product).on(orderProduct.productId.eq(product.id))
                .where(
                        eqNumber(order.id, orderSearchDTO.getOrderId()),
                        containsString(user.name, orderSearchDTO.getUserName()),
                        containsString(product.name, orderSearchDTO.getProductName())
                )
                .fetchOne()
        );
    }

    @Override
    public OrderSearchDetailResponseDTO searchOrderDetail(Long userId, Long orderId) {
        return queryFactory
                .select(Projections.constructor(OrderSearchDetailResponseDTO.class,
                        order.id,
                        user.id,
                        order.status,
                        order.orderAt,
                        order.totalAmount,
                        payment.paymentCode,
                        user.name,
                        orderProduct.productId,
                        payment.paymentPrice,
                        orderProduct.quantity
                ))
                .from(order)
                .where(order.userId.eq(userId).and(order.id.eq(orderId)))
                .join(user).on(user.id.eq(order.userId))
                .join(payment).on(payment.order.eq(order))
                .join(orderProduct).on(orderProduct.order.eq(order))
                .fetchOne();
    }
}
