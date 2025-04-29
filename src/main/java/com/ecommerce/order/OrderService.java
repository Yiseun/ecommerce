package com.ecommerce.order;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.TmpOrder;
import com.ecommerce.order.dto.request.CompleteOrderRequest;
import com.ecommerce.order.dto.request.CreateOrderIdRequest;
import com.ecommerce.order.dto.response.CreateOrderIdResponse;
import com.ecommerce.order.exception.application.OrderNotFoundException;
import com.ecommerce.order.persistence.OrderRepository;
import com.ecommerce.order.persistence.TmpOrderRepository;
import com.ecommerce.order.persistence.entity.OrderEntity;
import com.ecommerce.order.persistence.entity.TmpOrderEntity;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final TmpOrderRepository tmpOrderRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public CreateOrderIdResponse createOrderId(final CreateOrderIdRequest request){
        final TmpOrder requestTmpOrder = request.toTmpOrder();
        request.getClient().sendMessage(request);
        final TmpOrderEntity reqeuestTmpOrderEntity = TmpOrderEntity.from(requestTmpOrder);
        final TmpOrderEntity savedTmpOrderEntity = tmpOrderRepository.save(reqeuestTmpOrderEntity);
        final TmpOrder savedTmpOrder = savedTmpOrderEntity.toTmpOrder();
        return CreateOrderIdResponse.from(savedTmpOrder);
    }

    @Transactional
    public void completeOrder(final CompleteOrderRequest request){
        final TmpOrder requestTmpOrder = request.toTmpOrder();
        final TmpOrderEntity requestTmpOrderEntity = TmpOrderEntity.from(requestTmpOrder);
        final TmpOrderEntity serverTmpOrderEntity = tmpOrderRepository.findById(requestTmpOrderEntity.getOrderId()).orElseThrow(()-> new OrderNotFoundException("주문정보를 찾을수 없습니다."));
        final TmpOrder serverTmpOrder = serverTmpOrderEntity.toTmpOrder();
        final Order requestOrder = requestTmpOrder.createOrderWith(serverTmpOrder);
        final OrderEntity requestOrderEntity = OrderEntity.from(requestOrder);
        try {
            orderRepository.save(requestOrderEntity);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateRequestException("이미 생성완료된 주문입니다.");
        }
        request.getClient().sendMessage(request);
    }
}
