package com.ecommerce.order.port;

import com.ecommerce.coupon.CouponReceiver;
import com.ecommerce.coupon.dto.CouponRequest;
import com.ecommerce.coupon.dto.InternalCouponValidateRequest;
import com.ecommerce.order.dto.request.CreateOrderIdRequest;
import com.ecommerce.product.ProductReceiver;
import com.ecommerce.product.dto.InternalProductValidateRequest;
import com.ecommerce.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCheckClient {

    private final CouponReceiver couponReceiver;
    private final ProductReceiver productReceiver;

    public void sendMessage(final CreateOrderIdRequest request){
        final List<CouponRequest> couponRequests = request.getBody().getOrderItemDtos().stream()
                .map(i->CouponRequest.builder()
                                .userCouponId(i.getUserCouponId())
                                .couponId(i.getCouponId())
                                .productId(i.getProductId())
                                .originPrice(i.getPrice())
                                .discountedPrice(i.getDiscountPrice())
                                .couponDiscountPercent(i.getCouponDiscountPercent())
                                .build())
                .toList();
        couponReceiver.validate(InternalCouponValidateRequest.from(request.getMemberId(), couponRequests));
        final List<ProductDto> productDtos = request.getBody().getOrderItemDtos().stream()
                .map(i-> ProductDto.builder()
                        .productId(i.getProductId())
                        .productName(i.getProductName())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .build())
                .toList();
        productReceiver.validate(InternalProductValidateRequest.from(productDtos));
    }

}
