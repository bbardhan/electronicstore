package com.checkout.service;

import com.checkout.dto.CouponDto;
import com.checkout.error.CouponNotExistException;
import com.checkout.model.Coupon;
import com.checkout.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.checkout.util.AppUtil.modelMapper;
import static java.util.Objects.isNull;


@Service
@Slf4j
public class CouponService {
    private AtomicInteger couponID;
    private Map<Integer, Coupon> couponMap;

    public CouponService() {
        couponID = new AtomicInteger(1);
        couponMap = new ConcurrentHashMap<>();
    }

    public boolean addCoupon(CouponDto couponDto) {
        Coupon coupon = modelMapper().map(couponDto, Coupon.class);
        coupon.setId(couponID.getAndIncrement());
        couponMap.put(coupon.getId(), coupon);
        log.info("New Coupon having id {} has been created ", coupon.getId());
        return true;
    }

    public CouponDto getCoupon(int couponId) {
        Coupon coupon = couponMap.get(couponId);
        if (isNull(coupon)) {
            throw new CouponNotExistException(MessageUtil.COUPON_NOT_FOUND);
        }
        CouponDto couponDto = modelMapper().map(coupon, CouponDto.class);
        return couponDto;
    }

    public List<Coupon> getAllCoupons() {
        return couponMap.values().stream().toList();
    }

    public boolean removeCoupon(int couponId) {
        Coupon coupon = couponMap.remove(couponId);
        if (isNull(coupon)) {
            log.error("Coupon having id {} doesn't exist ", couponId);
            throw new CouponNotExistException(MessageUtil.COUPON_NOT_FOUND);
        }
        log.info("Coupon having id {} has been removed ", couponId);
        return true;
    }
}
