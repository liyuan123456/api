package com.li.api.service;

import com.li.api.enums.CouponStatus;
import com.li.api.exception.NotFoundException;
import com.li.api.exception.ParamterException;
import com.li.api.pojo.model.Activity;
import com.li.api.pojo.model.Coupon;
import com.li.api.pojo.model.UserCoupon;
import com.li.api.repository.ActivityRepository;
import com.li.api.repository.CouponRepository;
import com.li.api.repository.UserCouponRepository;
import com.li.api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 黎源
 * @date 2020/12/31 15:04
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository repository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    public List<Coupon> getCouponByCategoryId(long cid) {
        Optional<List<Coupon>> couponList = repository.findByCategoryId(cid, new Date());
        return couponList.orElseThrow(() -> new NotFoundException(30006));
    }

    public List<Coupon> getCouponByWholeStore() {
        Optional<List<Coupon>> listOptional = repository.findByWholeStore(true, new Date());
        return listOptional.orElseThrow(() -> new NotFoundException(30006));
    }

    public UserCoupon collectCoupon(Long uid, Long cid) {
        // 2021/1/6 优惠券是否存在
        repository.findById(cid).orElseThrow(() -> new NotFoundException(30006));
        //  2021/1/6 领取活动是否过期
        Activity activity = activityRepository
                .findByCouponListId(cid)
                .orElseThrow(() -> new ParamterException(40001));
        if (!DateUtil.isExpired(new Date(), activity.getStartTime(), activity.getEndTime())) {
            throw new ParamterException(40001);
        }
        //  2021/1/6 是否重复领取
        Optional<UserCoupon> userCouponOptional = userCouponRepository
                .findFirstByUserIdAndCouponId(uid, cid);
        if(userCouponOptional.isPresent()){
            throw new ParamterException(40002);
        }
        //  2021/1/6 写入记录
        UserCoupon userCoupon = UserCoupon.builder()
                .userId(uid)
                .couponId(cid)
                .createTime(new Date())
                .status(CouponStatus.USABLE.getValue())
                .build();
        return userCouponRepository.save(userCoupon);
    }

    public List<Coupon> getMyCouponByUsable(Long uid) {
        Optional<List<Coupon>> listOptional = repository.findMineByUsableStatus(uid, new Date());
        return listOptional.orElseThrow(()->new NotFoundException(30006));
    }

    public List<Coupon> getMyCouponByUsed(Long uid) {
        Optional<List<Coupon>> listOptional = repository.findMineByUsedStatus(uid, new Date());
        return listOptional.orElseThrow(()->new NotFoundException(30006));
    }

    public List<Coupon> getMyCouponByOverdue(Long uid) {
        Optional<List<Coupon>> listOptional = repository.findMineByOverdueStatus(uid, new Date());
        return listOptional.orElseThrow(()->new NotFoundException(30006));
    }
}
