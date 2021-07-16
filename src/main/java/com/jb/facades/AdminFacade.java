package com.jb.facades;

import com.jb.beans.Company;
import com.jb.beans.Coupon;
import com.jb.beans.Customer;
import com.jb.beans.CustomerCoupon;
import com.jb.utils.Validators;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminFacade extends ClientFacade {

    public AdminFacade() {
        super();
    }

    public boolean login(String email, String password) {
        return email.toLowerCase().equals("admin@admin.com") && password.equals("admin");
    }

    //  Company Methods
    public void addCompany(Company company) throws Exception {
        ArrayList<Company> companiesList = companiesDAO.getAllCompanies();
        if (!Validators.EmailValidator(company.getEmail()) || !Validators.PasswordValidator(company.getPassword()))
            throw new Exception("Email or Password is not validated");
        if (companiesList.stream().filter(company1 -> company1.getName().equals(company.getName())).findAny().isPresent())
            throw new Exception("Company name already Exists - Choose Another!");
        if (companiesList.stream().filter(company1 -> company1.getEmail().equals(company.getEmail())).findAny().isPresent())
            throw new Exception("Company Email already exists - Choose Another!");


        companiesDAO.addCompany(company);
    }

    public void updateCompany(Company company) throws Exception {
        ArrayList<Company> companiesList = companiesDAO.getAllCompanies();
        if (!companiesList.stream().filter(company1 -> company1.getId() == company.getId()).findAny().isPresent())
            throw new Exception("No company with that ID..");
        if (!Validators.EmailValidator(company.getEmail()))
            throw new Exception("Wrong pattern of Email");
        if (!Validators.PasswordValidator(company.getPassword()))
            throw new Exception("Password canot contain whitespace charters and be 8 charcters long");
        if (!companiesList.stream().filter(company1 -> company1.getName().equals(company.getName())).findAny().isPresent())
            throw new Exception("Company Name already Exists!");

        companiesDAO.updateCompany(company);
    }

    public void deleteCompany(int companyID) throws Exception {
        ArrayList<Company> companiesList = companiesDAO.getAllCompanies();
        if (!companiesList.stream().filter(company -> company.getId() == companyID).findAny().isPresent())
            throw new Exception("No company with that ID..");

        List<Coupon> couponList = couponsDAO.getAllCoupons().stream().filter(coupon1 -> coupon1.getCompanyID() == companyID).collect(Collectors.toList());

        if (!couponList.isEmpty()) {

            ArrayList<CustomerCoupon> customerCoupons = customerVSCouponsDAO.getAllCustomersAndCoupons();

            for (Coupon c : couponList
            ) {
                if (customerCoupons.stream().filter(customerCoupon -> customerCoupon.getCouponID() == c.getId()).findFirst().isPresent())
                    customerVSCouponsDAO.deleteCoupon(c.getId());

                couponsDAO.deleteCoupon(c.getId());

            }
        }

        companiesDAO.deleteCompany(companyID);
    }

    public ArrayList<Company> getAllCompanies() throws SQLException {
        return companiesDAO.getAllCompanies();
    }

    public Company getOneCompany(int companyID) throws Exception {
        if (!companiesDAO.getAllCompanies().stream().filter(company -> company.getId() == companyID).findFirst().isPresent())
            throw new Exception("Company Does not exists");

        return companiesDAO.getAllCompanies().stream().filter(company -> company.getId() == companyID).findFirst().get();
    }

    // Customer Methods
    public void addCustomer(Customer customer) throws Exception {

        if (!Validators.EmailValidator(customer.getEmail()) || !Validators.PasswordValidator(customer.getPassword()))
            throw new Exception("Email or Password is not validated");

        ArrayList<Customer> customersList = customerDAO.getAllCustomers();
        if (customersList.stream().filter(customer1 -> customer1.getEmail().equals(customer.getEmail())).findAny().isPresent())
            throw new Exception("Company Email already exists - Choose Another!");

        customerDAO.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) throws Exception {
        if (!customerDAO.getAllCustomers().stream().filter(customer1 -> customer1.getId() == customer.getId()).findFirst().isPresent())
            throw new Exception("Customer Does not Exists!");
        if (!Validators.PasswordValidator(customer.getPassword()))
            throw new Exception("Wrong Password template");
        if (!Validators.EmailValidator(customer.getEmail()))
            throw new Exception("Wrong Email Template");

        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(int customerID) throws Exception {
        if (!customerDAO.getAllCustomers().stream().filter(customer1 -> customer1.getId() == customerID).findFirst().isPresent())
            throw new Exception("Customer Does not Exists!");

        if (customerVSCouponsDAO.getAllCustomersAndCoupons().stream().filter(cac -> cac.getCustomerID() == customerID).findAny().isPresent())
                customerVSCouponsDAO.deleteCustomer(customerID);

        customerDAO.deleteCustomer(customerID);
    }

    public ArrayList<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    public Customer getOneCustomer(int customerID) throws Exception {

        if (!customerDAO.getAllCustomers().stream().filter(customer -> customer.getId() == customerID).findFirst().isPresent())
            throw new Exception("Customer Does not exists");

        return customerDAO.getOneCustomer(customerID);
    }

}
