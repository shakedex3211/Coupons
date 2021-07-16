package com.jb.facades;

import java.util.List;
import java.util.stream.*;

import com.jb.beans.Category;
import com.jb.beans.Company;
import com.jb.beans.Coupon;
import com.jb.utils.Validators;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CompanyFacade extends ClientFacade {

    private int companyID = 0;

    public CompanyFacade() {
        super();
    }

    @Override
    public boolean login(String email, String password) throws Exception {
        if (!Validators.PasswordValidator(password))
            throw new Exception("Password can containd only numbers and letters and 8 charters at least");
        if (!Validators.EmailValidator(email))
            throw new Exception("not An Email adress, Check Again");

        companyID = companiesDAO.getAllCompanies().stream().filter(company -> company.getEmail().equals(email) && company.getPassword().equals(password)).findFirst().get().getId();
        return (companyID != 0);
    }

    public void addCoupon(Coupon coupon) throws Exception{
        if (!companiesDAO.getAllCompanies().stream().filter(company -> company.getId() == coupon.getCompanyID()).findFirst().isPresent())
            throw new Exception("Company NotFound");

        if (couponsDAO.getAllCoupons().stream().filter(coupon1 -> coupon1.getTitle().equals(coupon.getTitle()) && coupon1.getCompanyID() == coupon.getCompanyID()).findFirst().isPresent())
            throw new Exception("Title of coupon already exists for this company");

        couponsDAO.addCoupon(coupon);
    }

    public void updateCoupon(Coupon coupon) throws Exception{
        ArrayList<Coupon> couponsList = couponsDAO.getAllCoupons();
        if (!couponsList.stream().filter(coupon1 -> coupon1.getId()==coupon.getId()).findFirst().isPresent())
            throw new Exception("Coupon Doesnot Exists");
        if (!companiesDAO.getAllCompanies().stream().filter(company -> company.getId() == coupon.getCompanyID()).findFirst().isPresent())
            throw new Exception("Company Doesnot Exists");
        if (couponsList.stream().filter(coupon1 -> coupon1.getId() == coupon.getId() && coupon1.getCompanyID() != coupon.getCompanyID()).findFirst().isPresent())
            throw new Exception("Cannot Change company ID");

        couponsDAO.updateCoupon(coupon);

    }

    public void deleteCoupon(int couponID) throws Exception{
        if (!checkCouponsForCompany())
            throw new Exception("Coupon not Exists");
        if (customerVSCouponsDAO.getAllCustomersAndCoupons().stream().filter(customerCoupon -> customerCoupon.getCouponID() == couponID).findFirst().isPresent())
            customerVSCouponsDAO.deleteCoupon(couponID);

        couponsDAO.deleteCoupon(couponID);
    }

    public ArrayList<Coupon> getCompanyCoupons() throws Exception{
        if (!checkCouponsForCompany())
            throw new Exception("Company Do not have coupons");

        return couponsDAO.getAllCompanyCoupons(companyID);
    }

    public ArrayList<Coupon> getCompanyCoupons(Category category) throws Exception {

        if (!checkCouponsForCompany())
           throw new Exception("Company Does not have Coupons");

        ArrayList<Coupon> coupons = couponsDAO.getAllCompanyCoupons(companyID);
        if (!coupons.stream().filter(coupon -> coupon.getCategory() == category).findFirst().isPresent())
            return null;

        List<Coupon> couponList = coupons.stream().filter(coupon -> coupon.getCategory() == category).collect(Collectors.toList());
        return new ArrayList<Coupon>(couponList);
    }

    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws Exception{
        if (!checkCouponsForCompany())
            throw new Exception("Company Does not have Coupons");

        ArrayList<Coupon> coupons = couponsDAO.getAllCompanyCoupons(companyID);
        if (!coupons.stream().filter(coupon -> coupon.getPrice() < maxPrice).findFirst().isPresent())
            return null;

        List<Coupon> couponList = coupons.stream().filter(coupon -> coupon.getPrice() < maxPrice).collect(Collectors.toList());
        return new ArrayList<Coupon>(couponList);
    }

    public Company getCompanyDetails() throws Exception{
        if (companyID == 0)
            throw new Exception("Company Error");
        return companiesDAO.getOneCompany(companyID);
    }

    private Boolean checkCouponsForCompany() throws Exception{
        return couponsDAO.getAllCoupons().stream().filter(coupon -> coupon.getCompanyID() == companyID).findFirst().isPresent();
    }

    private Boolean checkCouponsForCompany(int _companyID) throws Exception{
        return couponsDAO.getAllCoupons().stream().filter(coupon -> coupon.getCompanyID() == _companyID).findFirst().isPresent();
    }
}
