package com.ecommerce.order;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.TmpOrder;
import com.ecommerce.order.dto.request.CreateInitOrderRequest;
import com.ecommerce.order.dto.request.CreateOrderIdRequest;
import com.ecommerce.order.dto.request.ReadOrderRequest;
import com.ecommerce.order.dto.request.UpdateOrderRequest;
import com.ecommerce.order.dto.response.CreateOrderIdResponse;
import com.ecommerce.order.dto.response.ReadOrderResponse;
import com.ecommerce.order.exception.application.OrderNotFoundException;
import com.ecommerce.order.persistence.OrderRepository;
import com.ecommerce.order.persistence.TmpOrderRepository;
import com.ecommerce.order.domain.constraint.Constraint;
import com.ecommerce.order.persistence.constraint.PageRequest;
import com.ecommerce.order.persistence.constraint.Specifications;
import com.ecommerce.order.persistence.entity.OrderEntity;
import com.ecommerce.order.persistence.entity.TmpOrderEntity;
import com.ecommerce.order.port.OrderClientRouter;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderClientRouter router;
    private final TmpOrderRepository tmpOrderRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public CreateOrderIdResponse createOrderId(final CreateOrderIdRequest request){
        final TmpOrder requestTmpOrder = request.toTmpOrder();
        router.createOrderId(request);
        final TmpOrderEntity reqeuestTmpOrderEntity = TmpOrderEntity.from(requestTmpOrder);
        final TmpOrderEntity savedTmpOrderEntity = tmpOrderRepository.save(reqeuestTmpOrderEntity);
        final TmpOrder savedTmpOrder = savedTmpOrderEntity.toTmpOrder();
        return CreateOrderIdResponse.from(savedTmpOrder);
    }

    @Transactional
    public void createInitOrder(final CreateInitOrderRequest request){
        final TmpOrder requestTmpOrder = request.toTmpOrder();
        final TmpOrderEntity requestTmpOrderEntity = TmpOrderEntity.from(requestTmpOrder);
        final TmpOrderEntity serverTmpOrderEntity = tmpOrderRepository.findById(requestTmpOrderEntity.getOrderId()).orElseThrow(()-> new OrderNotFoundException("주문정보를 찾을수 없습니다."));
        final TmpOrder serverTmpOrder = serverTmpOrderEntity.toTmpOrder();
        final Order requestOrder = requestTmpOrder.createOrderWith(serverTmpOrder);
        final OrderEntity requestOrderEntity = OrderEntity.from(requestOrder);
        try {
            final OrderEntity resultOrderEntity = orderRepository.save(requestOrderEntity);
            final Order resultOrder = resultOrderEntity.toOrder();
            router.createInitOrder(request);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateRequestException("이미 생성완료된 주문입니다.");
        }
    }

    @Transactional
    public void updateOrder(final UpdateOrderRequest request){
        final Order requestOrder = request.toOrder();
        final OrderEntity requestOrderEntity = OrderEntity.from(requestOrder);
        final OrderEntity serverOrderEntity = orderRepository.findByOrderId(requestOrderEntity.getOrderId()).orElseThrow(()->new OrderNotFoundException("주문정보를 찾을수 없습니다."));
        final Order serverOrder = serverOrderEntity.toOrder();
        final Order resultOrder = serverOrder.update(requestOrder);
        final OrderEntity resultOrderEntity = OrderEntity.from(resultOrder);
        orderRepository.save(resultOrderEntity);
        router.updateOrder(request);
    }

    @Transactional(readOnly = true)
    public ReadOrderResponse findOrder(final ReadOrderRequest request){
        final Constraint constraint = request.toConstraint();
        final Slice<OrderEntity> orderEntities = orderRepository.findAll(Specifications.from(constraint), PageRequest.from(constraint));
        final List<Order> orders = orderEntities.stream().map(orderEntity -> orderEntity.toOrder()).toList();
        return ReadOrderResponse.from(orders);
    }
}
