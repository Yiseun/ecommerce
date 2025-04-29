package com.ecommerce.coupon;

import com.ecommerce.coupon.domain.CouponProduct;
import com.ecommerce.coupon.domain.UserCoupon;
import com.ecommerce.coupon.dto.InternalCouponUseRequest;
import com.ecommerce.coupon.dto.InternalCouponValidateRequest;
import com.ecommerce.coupon.exception.application.CouponAndProductRelationshipNotFoundException;
import com.ecommerce.coupon.exception.application.UserCouponNotFoundException;
import com.ecommerce.coupon.persistence.*;
import com.ecommerce.grobal.util.NonDuplicatedList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponProductRepository couponProductRepository;

    @Transactional(readOnly = true)
    public void validate(final InternalCouponValidateRequest request){
        final NonDuplicatedList<UserCoupon> requestUserCoupons = request.toUserCouponList();
        final List<Long> requestUserCouponEntityIds = requestUserCoupons.getStream().map(i->UserCouponEntity.from(i).getUserCouponId()).toList();
        final List<UserCouponEntity> serverUserCouponEntities = userCouponRepository.findAllById(requestUserCouponEntityIds);
        if(serverUserCouponEntities.isEmpty()){
            throw new UserCouponNotFoundException("서버에 쿠폰이 존재하지 않습니다.");
        }
        final List<UserCoupon> serverUserCoupons = serverUserCouponEntities.stream().map(i->i.toUserCoupon()).toList();
        final Map<UserCoupon,UserCoupon> serverUserCouponMap = serverUserCoupons.stream().collect(Collectors.toMap(i->i, i->i));
        requestUserCoupons.getStream().forEach(requestUserCoupon->{
            final UserCoupon serverUserCoupon = serverUserCouponMap.get(requestUserCoupon);
            if(serverUserCoupon==null){
                throw new UserCouponNotFoundException("서버에 존재하지않는 쿠폰이 있습니다.");
            }
        });

        final List<CouponProduct> requestCouponProducts = request.toCouponProducts();
        final List<CouponProductEntity> requestCouponProductEntities = requestCouponProducts.stream().map(i->CouponProductEntity.from(i)).toList();
        requestCouponProductEntities.forEach(requestCouponProductEntity->{
            final CouponEntity requestCouponEntity = requestCouponProductEntity.getCouponEntity();
            final String requestProductId = requestCouponProductEntity.getProductId();
            couponProductRepository.findByCouponEntityAndProductId(requestCouponEntity,requestProductId).orElseThrow(()->new CouponAndProductRelationshipNotFoundException("쿠폰과 연관된 상품을 찾을수 없습니다."));
        });
    }

    @Transactional
    public void useUserCoupon(final InternalCouponUseRequest request){
        final NonDuplicatedList<UserCoupon> requestUserCoupons = request.toUserCoupons();
        final List<Long> requestUserCouponEntityIds = requestUserCoupons.getStream().map(requestUserCoupon->{
            final UserCouponEntity requestUserCouponEntity = UserCouponEntity.from(requestUserCoupon);
            return requestUserCouponEntity.getUserCouponId();
        }).toList();
        final List<UserCouponEntity> serverUserCouponEntities = userCouponRepository.findAllById(requestUserCouponEntityIds);
        if(serverUserCouponEntities.isEmpty()){
            throw new UserCouponNotFoundException("서버에 쿠폰이 존재하지않습니다.");
        }
        final List<UserCoupon> serverUserCoupons = serverUserCouponEntities.stream().map(i->i.toUserCoupon()).toList();
        final Map<UserCoupon,UserCoupon> serverUserCouponMap = serverUserCoupons.stream().collect(Collectors.toMap(i->i,i->i));
        final List<UserCoupon> resultUserCoupons = requestUserCoupons.getStream().map(requestUserCoupon->{
            final UserCoupon serverCoupon = serverUserCouponMap.get(requestUserCoupon);
            if(serverCoupon==null){
                throw new UserCouponNotFoundException("서버에 존재하지않는 쿠폰이 있습니다.");
            }
            return serverCoupon;
        }).toList();
        final List<Long> resultUserCouponEntityIds = resultUserCoupons.stream().map(i->{
            final UserCouponEntity resultUserCoupon = UserCouponEntity.from(i);
            return resultUserCoupon.getUserCouponId();
        }).toList();

        final List<CouponProduct> requestCouponProducts = request.toCouponProducts();
        final List<CouponProductEntity> requestCouponProductEntities = requestCouponProducts.stream().map(i->CouponProductEntity.from(i)).toList();
        requestCouponProductEntities.forEach(requestCouponProductEntity->{
            final CouponEntity requestCouponEntity = requestCouponProductEntity.getCouponEntity();
            final String requestProductId = requestCouponProductEntity.getProductId();
            couponProductRepository.findByCouponEntityAndProductId(requestCouponEntity,requestProductId).orElseThrow(()->new CouponAndProductRelationshipNotFoundException("쿠폰과 연관된 상품을 찾을수 없습니다."));
        });

        userCouponRepository.deleteAllById(resultUserCouponEntityIds);
    }
}

