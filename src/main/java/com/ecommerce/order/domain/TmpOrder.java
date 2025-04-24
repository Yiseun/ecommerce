package com.ecommerce.order.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TmpOrder {
    private final Order order;

    public static TmpOrder from(final Order order){
        return new TmpOrder(order);
    }
}
