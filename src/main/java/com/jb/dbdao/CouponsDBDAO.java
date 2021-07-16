package com.jb.dbdao;

import com.jb.beans.Category;
import com.jb.beans.Coupon;
import com.jb.beans.CustomerCoupon;
import com.jb.dao.CouponsDAO;
import com.jb.utils.DBUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CouponsDBDAO implements CouponsDAO {

    private static final String QUERY_INSERT="INSERT INTO `coupons`.`coupons` (`company_id`, `category_id`, `title`, `description`,`start_date`,`end_date`,`amount`,`price`,`image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String QUERY_UPDATE="UPDATE `coupons`.`coupons` SET `company_id` = ?, `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?)";
    private static final String QUERY_DELETE="DELETE FROM `coupons`.`coupons` WHERE (`id` = ?)";
    private static final String QUERY_GET_ONE="SELECT * FROM `coupons`.`coupons` WHERE (`id` = ?)";
    private static final String QUERY_GET_COMPANY_COUPONS="SELECT * FROM `coupons`.`coupons` WHERE (`company_id` = ?)";
    private static final String QUERY_GET_ALL="SELECT * FROM `coupons`.`coupons`";
    private static final String QUERY_INSERT_COUPON_PURCHASE ="INSERT INTO `coupons`.`customers_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?)";
    private static final String QUERY_DELETE_COUPON_PURCHASE ="DELETE FROM `coupons`.`customers_vs_coupons` WHERE `customer_id` = ? AND `coupon_id` = ?";

    @Override
    public void addCoupon(Coupon coupon) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,coupon.getCompanyID());
        map.put(2,coupon.getCategory().ordinal());
        map.put(3,coupon.getTitle());
        map.put(4,coupon.getDescription());
        map.put(5,coupon.getStartDate());
        map.put(6,coupon.getEndDate());
        map.put(7,coupon.getAmount());
        map.put(8,coupon.getPrice());
        map.put(9,coupon.getImage());
        DBUtils.runQuery(QUERY_INSERT,map);

    }

    @Override
    public void updateCoupon(Coupon coupon) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,coupon.getCompanyID());
        map.put(2,coupon.getCategory().ordinal());
        map.put(3,coupon.getTitle());
        map.put(4,coupon.getDescription());
        map.put(5,coupon.getStartDate());
        map.put(6,coupon.getEndDate());
        map.put(7,coupon.getAmount());
        map.put(8,coupon.getPrice());
        map.put(9,coupon.getImage());
        map.put(10,coupon.getId());
        DBUtils.runQuery(QUERY_UPDATE,map);
    }

    @Override
    public void deleteCoupon(int couponID) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,couponID);
        DBUtils.runQuery(QUERY_DELETE,map);
    }

    @Override
    public void addCouponPurchase(int customerID, int couponID) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,customerID);
        map.put(2,couponID);
        DBUtils.runQuery(QUERY_INSERT_COUPON_PURCHASE,map);
    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,customerID);
        map.put(2,couponID);
        DBUtils.runQuery(QUERY_DELETE_COUPON_PURCHASE,map);
    }

    @Override
    public Coupon getOneCoupon(int couponID) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,couponID);
        ResultSet resultSet =  DBUtils.runQueryWithResults(QUERY_GET_ONE,map);
        resultSet.next();

//        int      id          = Integer.parseInt(resultSet.getString(1));
        int      companiID   = Integer.parseInt(resultSet.getString(2));

        Category category = Category.ZERO;
        for (Category c: Category.values()
             ) {
                if (c.ordinal() == resultSet.getInt(3))
                    category = c;
        }

        String   title       = resultSet.getString(4);
        String   description = resultSet.getString(5);
        Date     startDate   = Date.valueOf(resultSet.getString(6));
        Date     endDate     = Date.valueOf(resultSet.getString(7));
        int      amount      = Integer.parseInt(resultSet.getString(8));
        double   price       = Double.parseDouble(resultSet.getString(9));
        String   image       = resultSet.getString(10);

        return new Coupon(couponID,companiID,category,title,description,startDate,endDate,amount,price,image);
    }

    public ArrayList<Coupon> getAllCompanyCoupons(int companyID) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,companyID);
        ResultSet resultSet =  DBUtils.runQueryWithResults(QUERY_GET_COMPANY_COUPONS,map);

        ArrayList<Coupon> results = new ArrayList<>();

        while (resultSet.next()){
            int      couponID    = Integer.parseInt(resultSet.getString(1));
            int      companiID   = Integer.parseInt(resultSet.getString(2));
            int      nCategory   = resultSet.getInt(3);
            Category category    = Arrays.stream(Category.values()).filter(category1 -> category1.ordinal() == nCategory).findFirst().get();
            String   title       = resultSet.getString(4);
            String   description = resultSet.getString(5);
            Date     startDate   = Date.valueOf(resultSet.getString(6));
            Date     endDate     = Date.valueOf(resultSet.getString(7));
            int      amount      = Integer.parseInt(resultSet.getString(8));
            double   price       = Double.parseDouble(resultSet.getString(9));
            String   image       = resultSet.getString(10);

            results.add(new Coupon(couponID,companiID,category,title,description,startDate,endDate,amount,price,image));
        }

        return results;
    }

    @Override
    public ArrayList<Coupon> getAllCoupons() throws SQLException {
        ArrayList<Coupon>results = new ArrayList<>();
        ResultSet resultSet =  DBUtils.runQueryWithResults(QUERY_GET_ALL);
        while (resultSet.next()){
            int      couponID    = Integer.parseInt(resultSet.getString(1));
            int      companiID   = Integer.parseInt(resultSet.getString(2));
            int      nCategory   = resultSet.getInt(3);
            Category category    = Arrays.stream(Category.values()).filter(category1 -> category1.ordinal() == nCategory).findFirst().get();
            String   title       = resultSet.getString(4);
            String   description = resultSet.getString(5);
            Date     startDate   = Date.valueOf(resultSet.getString(6));
            Date     endDate     = Date.valueOf(resultSet.getString(7));
            int      amount      = Integer.parseInt(resultSet.getString(8));
            double   price       = Double.parseDouble(resultSet.getString(9));
            String   image       = resultSet.getString(10);

            results.add(new Coupon(couponID,companiID,category,title,description,startDate,endDate,amount,price,image));
        }

        return results;
    }
}
