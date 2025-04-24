package com.ecommerce.order;

import com.ecommerce.order.domain.TmpOrder;
import com.ecommerce.order.dto.CreateOrderIdRequest;
import com.ecommerce.order.dto.CreateOrderIdResponse;
import com.ecommerce.order.pesistence.TmpOrderRepository;
import com.ecommerce.order.pesistence.entity.TmpOrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final TmpOrderRepository tmpOrderRepository;

    public CreateOrderIdResponse createOrderId(final CreateOrderIdRequest request){
        final TmpOrder tmpOrder = request.toTmpOrder();
        request.getClient().sendMessage(request);
        final TmpOrderEntity tmpOrderEntity = TmpOrderEntity.from(tmpOrder);
        final TmpOrderEntity savedTmpOrderEntity = tmpOrderRepository.save(tmpOrderEntity);
        final TmpOrder savedTmpOrder = savedTmpOrderEntity.toTmpOrder();
        return CreateOrderIdResponse.from(savedTmpOrder);
    }
}
