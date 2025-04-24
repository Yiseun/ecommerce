package com.ecommerce.coupon;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.Assertions.*;
import com.ecommerce.coupon.dto.CouponRequest;
import com.ecommerce.coupon.dto.InternalCouponValidateRequest;
import com.ecommerce.coupon.exception.application.CouponAndProductRelationshipNotFoundException;
import com.ecommerce.coupon.exception.application.UserCouponNotFoundException;
import com.ecommerce.coupon.persistence.*;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest
public class CouponServiceTests {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private CouponProductRepository couponProductRepository;

    @Test
    @Transactional
    void 일치하는_유저쿠폰을_찾을수없을때_실패한다(){
        final String givenCouponDiscountPercent = "50";
        final String givenProductId = "3";
        final String givenDiscountedPrice = "30";
        final Long theOtherDiscountPercent = 31L;
        final String givenOriginPrice = "60";
        final String givenMemberId = "qqweqq";
        final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
        final CouponEntity givenTheOtherCouponEntity = fixtureMonkey.giveMeBuilder(CouponEntity.class)
                .set(javaGetter(CouponEntity::getCouponId),null)
                .set(javaGetter(CouponEntity::getDiscountPercent),theOtherDiscountPercent)
                .sample();
        final CouponEntity savedCouponEntity = couponRepository.save(givenTheOtherCouponEntity);
        final UserCouponEntity givenUserCouponEntity = fixtureMonkey.giveMeBuilder(UserCouponEntity.class)
                .set(javaGetter(UserCouponEntity::getCouponEntity),savedCouponEntity)
                .set(javaGetter(UserCouponEntity::getUserCouponId),null)
                .set(javaGetter(UserCouponEntity::getMemberId),givenMemberId)
                .sample();
        final Long givenProductPrice = 30L;
        final CouponProductEntity givenCouponProductEntity = fixtureMonkey.giveMeBuilder(CouponProductEntity.class)
                .set(javaGetter(CouponProductEntity::getCouponProductId),null)
                .set(javaGetter(CouponProductEntity::getCouponEntity),savedCouponEntity)
                .set(javaGetter(CouponProductEntity::getProductId),givenProductId)
                .set(javaGetter(CouponProductEntity::getProductPrice),givenProductPrice)
                .sample();
        final UserCouponEntity savedUserCouponEntity = userCouponRepository.save(givenUserCouponEntity);
        couponProductRepository.save(givenCouponProductEntity);
        final CouponRequest coupon = fixtureMonkey.giveMeBuilder(CouponRequest.class)
                .set(javaGetter(CouponRequest::getCouponId),savedCouponEntity.getCouponId().toString())
                .set(javaGetter(CouponRequest::getUserCouponId),savedUserCouponEntity.getUserCouponId().toString())
                .set(javaGetter(CouponRequest::getCouponDiscountPercent),givenCouponDiscountPercent)
                .set(javaGetter(CouponRequest::getProductId),givenProductId)
                .set(javaGetter(CouponRequest::getDiscountedPrice),givenDiscountedPrice)
                .set(javaGetter(CouponRequest::getOriginPrice),givenOriginPrice)
                .sample();
        final List<CouponRequest> coupons = List.of(coupon);
        final InternalCouponValidateRequest request = fixtureMonkey.giveMeBuilder(InternalCouponValidateRequest.class)
                .set(javaGetter(InternalCouponValidateRequest::getMemberId),givenMemberId)
                .set(javaGetter(InternalCouponValidateRequest::getCoupons),coupons)
                .sample();

        assertThatThrownBy(()->couponService.validate(request)).isInstanceOf(UserCouponNotFoundException.class);
    }

    @Test
    @Transactional
    void 쿠폰과_연관관계가_없는상품이_하나라도_존재한다면_실패한다(){
        final String givenCouponDiscountPercent = "50";
        final String givenProductId = "3";
        final String givenDiscountedPrice = "30";
        final String givenOriginPrice = "60";
        final String givenMemberId = "qqweqq";
        final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
        final CouponEntity givenCouponEntity = fixtureMonkey.giveMeBuilder(CouponEntity.class)
                .set(javaGetter(CouponEntity::getCouponId),null)
                .set(javaGetter(CouponEntity::getDiscountPercent),Long.valueOf(givenCouponDiscountPercent))
                .sample();
        final CouponEntity couponEntity = couponRepository.save(givenCouponEntity);
        final UserCouponEntity givenUserCouponEntity = fixtureMonkey.giveMeBuilder(UserCouponEntity.class)
                .set(javaGetter(UserCouponEntity::getCouponEntity),couponEntity)
                .set(javaGetter(UserCouponEntity::getUserCouponId),null)
                .set(javaGetter(UserCouponEntity::getMemberId),givenMemberId)
                .sample();
        final UserCouponEntity userCouponEntity = userCouponRepository.save(givenUserCouponEntity);
        final CouponRequest coupon = fixtureMonkey.giveMeBuilder(CouponRequest.class)
                .set(javaGetter(CouponRequest::getCouponId),couponEntity.getCouponId().toString())
                .set(javaGetter(CouponRequest::getUserCouponId),userCouponEntity.getUserCouponId().toString())
                .set(javaGetter(CouponRequest::getCouponDiscountPercent),givenCouponDiscountPercent)
                .set(javaGetter(CouponRequest::getProductId),givenProductId)
                .set(javaGetter(CouponRequest::getDiscountedPrice),givenDiscountedPrice)
                .set(javaGetter(CouponRequest::getOriginPrice),givenOriginPrice)
                .sample();
        final List<CouponRequest> coupons = List.of(coupon);
        final InternalCouponValidateRequest request = fixtureMonkey.giveMeBuilder(InternalCouponValidateRequest.class)
                .set(javaGetter(InternalCouponValidateRequest::getMemberId),givenMemberId)
                .set(javaGetter(InternalCouponValidateRequest::getCoupons),coupons)
                .sample();

        assertThatThrownBy(()->couponService.validate(request)).isInstanceOf(CouponAndProductRelationshipNotFoundException.class);
    }
}
