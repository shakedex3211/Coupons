package com.jb.dbdao;

import com.jb.beans.Customer;
import com.jb.beans.CustomerCoupon;
import com.jb.dao.CustomerVSCouponsDAO;
import com.jb.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CustomerVSCouponsDBDAO implements CustomerVSCouponsDAO {

    private static final String QUERY_INSERT="INSERT INTO `coupons`.`customers_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?)";
    private static final String QUERY_UPDATE_CUSTOMER="UPDATE `coupons`.`customers_vs_coupons` SET `customer_id` = ?, `coupon_id` = ? WHERE (`customer_id` = ?)";
    private static final String QUERY_UPDATE_COUPON="UPDATE `coupons`.`customers_vs_coupons` SET `customer_id` = ?, `coupon_id` = ? WHERE (`coupon_id` = ?)";
    private static final String QUERY_DELETE_CUSTOMER="DELETE FROM `coupons`.`customers_vs_coupons` WHERE (`customer_id` = ?)";
    private static final String QUERY_DELETE_COUPON="DELETE FROM `coupons`.`customers_vs_coupons` WHERE (`coupon_id` = ?)";
    private static final String QUERY_GET_ALL_CUSTOMERS="SELECT * FROM `coupons`.`customers_vs_coupons WHERE (`customer_id` = ?)`";
    private static final String QUERY_GET_ALL_COUPONS="SELECT * FROM `coupons`.`customers_vs_coupons WHERE (`coupon_id` = ?)`";
    private static final String QUERY_GET_ALL="SELECT * FROM `coupons`.`customers_vs_coupons`";

    @Override
    public void addCustomer(int customerID, int couponID) throws SQLException {

        Map<Integer,Object> map = new HashMap<>();
        map.put(1,customerID);
        map.put(2,couponID);
        DBUtils.runQuery(QUERY_INSERT,map);

    }

    @Override
    public void updateCustomer(int customerID, int couponID) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,customerID);
        map.put(2,couponID);
        map.put(3,customerID);
        DBUtils.runQuery(QUERY_UPDATE_CUSTOMER,map);
    }

    @Override
    public void updateCoupon(int customerID, int couponID) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,customerID);
        map.put(2,couponID);
        map.put(3,couponID);
        DBUtils.runQuery(QUERY_UPDATE_COUPON,map);
    }

    @Override
    public void deleteCustomer(int customerID) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,customerID);
        DBUtils.runQuery(QUERY_DELETE_CUSTOMER,map);
    }

    @Override
    public void deleteCoupon(int couponID) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,couponID);
        DBUtils.runQuery(QUERY_DELETE_COUPON,map);
    }

    @Override
    public ArrayList<CustomerCoupon> getAllCustomers(int customerID) throws SQLException {
        ArrayList<CustomerCoupon> results = new ArrayList<>();
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,customerID);
        ResultSet resultSet =  DBUtils.runQueryWithResults(QUERY_GET_ALL_CUSTOMERS,map);

        if (resultSet == null)
                return results;

        while (resultSet.next()){
            int customerID1 = resultSet.getInt(1);
            int companyID1 = resultSet.getInt(2);
            results.add(new CustomerCoupon(customerID1,companyID1));
        }

        return results;
    }

    @Override
    public ArrayList<CustomerCoupon> getAllCoupons(int couponID) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,couponID);
        ResultSet resultSet =  DBUtils.runQueryWithResults(QUERY_GET_ALL_COUPONS,map);

        ArrayList<CustomerCoupon> results = new ArrayList<>();
        while (resultSet.next()){
            int customerID1 = resultSet.getInt(1);
            int companyID1 = resultSet.getInt(2);
            results.add(new CustomerCoupon(customerID1,companyID1));
        }

        return results;
    }


    public ArrayList<CustomerCoupon> getAllCustomersAndCoupons() throws SQLException {
        ArrayList<CustomerCoupon> results = new ArrayList<>();
        ResultSet resultSet =  DBUtils.runQueryWithResults(QUERY_GET_ALL);
        while (resultSet.next()){
            int customerID1 = resultSet.getInt(1);
            int companyID1 = resultSet.getInt(2);
            results.add(new CustomerCoupon(customerID1,companyID1));
        }

        return results;
    }
}
