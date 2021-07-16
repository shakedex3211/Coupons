package com.jb.job;

import com.jb.beans.Coupon;
import com.jb.dao.CouponsDAO;
import com.jb.dao.CustomerVSCouponsDAO;
import com.jb.dbdao.CouponsDBDAO;
import com.jb.dbdao.CustomerVSCouponsDBDAO;

import java.util.Date;

public class CouponExperationJob implements Runnable{
    private CouponsDAO couponsDAO = new CouponsDBDAO();
    private CustomerVSCouponsDAO customerVSCouponsDAO = new CustomerVSCouponsDBDAO();
    boolean quit;
    final Long ONE_DAY = Integer.toUnsignedLong(1000*60*60*24);
    final Long TEN_SEC = Integer.toUnsignedLong(1000*10);

    public CouponExperationJob() {
        this.quit = false;
    }

    @Override
    public void run() {
        try {
            while (!quit) {
                java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                for (Coupon c: couponsDAO.getAllCoupons()
                     ) {
                    if (c.getEndDate().before(currentDate)){
                        if (!customerVSCouponsDAO.getAllCoupons(c.getId()).isEmpty())
                            customerVSCouponsDAO.deleteCoupon(c.getId());
                    }
                    couponsDAO.deleteCoupon(c.getId());
                }
                Thread.sleep(ONE_DAY);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void stop() {
        this.quit = true;
    }
}
