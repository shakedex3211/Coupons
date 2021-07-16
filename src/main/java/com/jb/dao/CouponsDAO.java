package com.jb.dao;

import com.jb.beans.Coupon;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponsDAO {

//    CRUD Coupon
    void addCoupon(Coupon coupon) throws SQLException;
    void updateCoupon(Coupon coupon) throws SQLException;
    void deleteCoupon(int couponID) throws SQLException;

//  Coupon Purchase
    void addCouponPurchase(int customerID, int couponID) throws SQLException;
    void deleteCouponPurchase(int customerID, int couponID) throws SQLException;

    Coupon getOneCoupon(int couponID) throws SQLException;
    ArrayList<Coupon> getAllCoupons() throws SQLException;
    ArrayList<Coupon> getAllCompanyCoupons(int companyID) throws SQLException;

}
