package com.ecommerce.order.persistence.constraint;

import com.ecommerce.order.domain.constraint.Constraint;
import com.ecommerce.order.persistence.entity.OrderEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class Specifications {

    public static Specification<OrderEntity> from(final Constraint constraint){
        return (root, query, criteriaBuilder) -> {
            final Long orderId = constraint.getOrderBase().getOrderId().getValue();
            final String memberId = constraint.getOrderBase().getMemberId();
            final LocalDate startDate = constraint.getDateRange().getStartDate().getLocalDate();
            final LocalDate endDate = constraint.getDateRange().getEndDate().getLocalDate();
            final Predicate cursor = criteriaBuilder.greaterThanOrEqualTo(root.get("orderId"),orderId);
            final Predicate member = criteriaBuilder.equal(root.get("memberId"),memberId);
            final Predicate dateRange = criteriaBuilder.between(root.get("createdTime"),startDate,endDate);
            return criteriaBuilder.and(cursor,member,dateRange);
        };
    }
}
