package com.jb.facades;

import com.jb.beans.Category;
import com.jb.beans.Coupon;
import com.jb.beans.Customer;
import com.jb.beans.CustomerCoupon;
import com.jb.utils.Validators;

import java.sql.Date;
import java.util.ArrayList;

public class CustomerFacade extends ClientFacade {

    private int customerID = 0;

    @Override
    public boolean login(String email, String password) throws Exception {
        if (!Validators.PasswordValidator(password))
            throw new Exception("Password can containd only numbers and letters and 8 charters at least");
        if (!Validators.EmailValidator(email))
            throw new Exception("not An Email adress, Check Again");

        this.customerID = customerDAO.getAllCustomers().stream().filter(customer -> customer.getEmail().equals(email) && customer.getPassword().equals(password)).findFirst().get().getId();
        return (this.customerID != 0);
    }

    public void purchaseCoupon(Coupon coupon) throws Exception {
        ArrayList<Coupon> coupons = couponsDAO.getAllCoupons();
        if (!coupons.stream().filter(coupon1 -> coupon1.getId() == coupon.getId()).findFirst().isPresent())
            throw new Exception("Coupon Does not Exists");
        if (coupons.stream().filter(coupon1 -> coupon1.getId() == coupon.getId() && coupon1.getAmount() == 0).findFirst().isPresent())
            throw new Exception("Out of Coupons");

        Date currentDate = new java.sql.Date(System.currentTimeMillis());
        if (coupons.stream().filter(coupon1 -> coupon1.getEndDate().before(currentDate) && coupon1.getId() == coupon.getId()).findFirst().isPresent())
            throw new Exception("Coupon Expired!");

        if (customerVSCouponsDAO.getAllCustomers(customerID).stream().filter(customerCoupon -> customerCoupon.getCouponID() == coupon.getId()).findFirst().isPresent())
            throw new Exception("Cannot Parchase more then 1 from that Coupon");

        customerVSCouponsDAO.addCustomer(customerID, coupon.getId());
        Coupon purchasedCoupon = coupon;
        purchasedCoupon.setAmount(coupon.getAmount() - 1);
        couponsDAO.updateCoupon(purchasedCoupon);

    }

    public ArrayList<Coupon> getCustomerCoupons() throws Exception {
        ArrayList<Coupon> results = new ArrayList<Coupon>();
        ArrayList<CustomerCoupon> ccList = customerVSCouponsDAO.getAllCustomersAndCoupons();
        if (!ccList.stream().filter(customerCoupon -> customerCoupon.getCustomerID() == customerID).findFirst().isPresent())
            throw new Exception("Customer Do not have Coupons");
        for (CustomerCoupon c : ccList
        ) {
            results.add(couponsDAO.getOneCoupon(c.getCouponID()));
        }

        return results;
    }

    public ArrayList<Coupon> getCustomerCoupons(Category category) throws Exception {
        ArrayList<Coupon> results = new ArrayList<Coupon>();
        ArrayList<CustomerCoupon> ccList = customerVSCouponsDAO.getAllCustomersAndCoupons();
        if (!ccList.stream().filter(customerCoupon -> customerCoupon.getCustomerID() == customerID).findFirst().isPresent())
            throw new Exception("Customer Do not have Coupons");
        for (CustomerCoupon c : ccList
        ) {
            Coupon coupon = couponsDAO.getOneCoupon(c.getCouponID());
            if (coupon.getCategory() == category)
                results.add(coupon);
        }

        if(results.isEmpty())
            return null;

        return results;
    }

    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws Exception {
        ArrayList<Coupon> results = new ArrayList<Coupon>();
        ArrayList<CustomerCoupon> ccList = customerVSCouponsDAO.getAllCustomersAndCoupons();
        if (!ccList.stream().filter(customerCoupon -> customerCoupon.getCustomerID() == customerID).findFirst().isPresent())
            throw new Exception("Customer Do not have Coupons");
        for (CustomerCoupon c : ccList
        ) {
            Coupon coupon = couponsDAO.getOneCoupon(c.getCouponID());
            if (coupon.getPrice() < maxPrice)
                results.add(coupon);
        }

        if(results.isEmpty())
            return null;

        return results;
    }

    public Customer getCustomerDetails() throws Exception {
        return customerDAO.getOneCustomer(customerID);
    }
}
