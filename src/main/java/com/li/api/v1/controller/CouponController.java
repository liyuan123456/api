package com.li.api.v1.controller;

import com.li.api.core.UnifyResponse;
import com.li.api.core.UserLocal;
import com.li.api.core.interceptors.ScopeLevel;
import com.li.api.enums.CouponStatus;
import com.li.api.exception.ParamterException;
import com.li.api.pojo.model.Coupon;
import com.li.api.pojo.model.UserCoupon;
import com.li.api.pojo.vo.CouponCategoryVo;
import com.li.api.pojo.vo.CouponSimpleVo;
import com.li.api.service.CouponService;
import com.li.api.service.CouponServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 黎源
 * @date 2020/12/31 15:01
 */
@RestController
@RequestMapping("/v1/coupon")
public class CouponController {

    @Autowired
    private CouponServiceImpl service;

    @GetMapping("/by/category/{cid}")
    public List<CouponSimpleVo> getCouponByCategory(@PathVariable(name = "cid") Long cid) {
        List<Coupon> couponList = service.getCouponByCategoryId(cid);
        return couponList.stream().map(CouponSimpleVo::new).collect(Collectors.toList());
    }

    @GetMapping("/whole_store")
    public List<CouponSimpleVo> getCouponByWholeStore() {
        List<Coupon> coupons = service.getCouponByWholeStore();
        return coupons.stream().map(CouponSimpleVo::new).collect(Collectors.toList());
    }

    @PostMapping("/collect/{id}")
    @ScopeLevel
    public UnifyResponse collectCoupon(@PathVariable(value = "id") Long cid, HttpServletRequest request) {
        UserCoupon userCoupon = service.collectCoupon(UserLocal.getUser().getId(), cid);
        if (userCoupon == null) {
            throw new ParamterException(10005);
        }
        return UnifyResponse.OK(request);
    }

    @GetMapping("/myself/by/status/{status}")
    @ScopeLevel
    public List<CouponSimpleVo> getMyCouponByStatus(@PathVariable(value = "status") Integer status) {
        Long uid = UserLocal.getUser().getId();
        List<Coupon> coupons;
        switch (CouponStatus.toType(status)) {
            case USABLE:
                coupons = service.getMyCouponByUsable(uid);
                break;
            case USED:
                coupons = service.getMyCouponByUsed(uid);
                break;
            case OVERDUE:
                coupons = service.getMyCouponByOverdue(uid);
                break;
            default:
                throw new ParamterException(10001);
        }
        return coupons.stream().map(CouponSimpleVo::new).collect(Collectors.toList());
    }

    @ScopeLevel
    @GetMapping("/myselft/available/with_category")
    public List<CouponCategoryVo> getMyCouponByUsableAndCategory() {
        Long uid = UserLocal.getUser().getId();
        List<Coupon> coupons = service.getMyCouponByUsable(uid);
        return coupons.stream()
                .map(CouponCategoryVo::new)
                .collect(Collectors.toList());
    }
}
