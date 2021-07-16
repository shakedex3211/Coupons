package com.jb.dao;

import com.jb.beans.CustomerCoupon;

import java.sql.SQLException;
import java.util.ArrayList;


public interface CustomerVSCouponsDAO {

    void addCustomer(int customerID, int couponID) throws SQLException;

    void updateCustomer(int customerID, int couponID) throws SQLException;

    void updateCoupon(int customerID, int couponID) throws SQLException;

    void deleteCustomer(int customerID) throws SQLException ;

    void deleteCoupon(int couponID) throws SQLException ;

    ArrayList<CustomerCoupon> getAllCustomers(int customerID) throws SQLException;

    ArrayList<CustomerCoupon> getAllCoupons(int couponID) throws SQLException;

    ArrayList<CustomerCoupon> getAllCustomersAndCoupons() throws SQLException;
}
