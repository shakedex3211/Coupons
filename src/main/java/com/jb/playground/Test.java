package com.jb.playground;

import com.jb.beans.Category;
import com.jb.beans.Company;
import com.jb.beans.Coupon;
import com.jb.beans.Customer;
import com.jb.db.DatabaseManager;
import com.jb.facades.*;
import com.jb.job.CouponExperationJob;

import java.sql.Date;
import java.util.Arrays;

public class Test {
    public static Company[] companies = {
            new Company("Osem", "Osem@walla.com", "aaa12345"),
            new Company("ALAM", "alam@gmail.com", "aaa123455"),
            new Company ("Shufersal","shufersal@co.il","aaa1234556"),
            new Company ("maxstock","maxstock@co.il","aaa1234557")
    };
    public static Customer[] customers = {
            new Customer("Yoad", "Cohen","yoad@cohens.com","aaa1234558"),
            new Customer("Shimi", "Tavory","shimi@taviroes.com","aaa1234559"),
            new Customer ("Elkhanann", "Hakohenn","elkhanann@hakohenns.com","aaa12345510"),
            new Customer ("Elmo", "Miki","elmo@miki.com","aaa12345511")
    };

    public static Coupon[] coupons = {
            new Coupon(1, 1, Category.FOOD, "Pasta", "delicious Pasta", new Date(121, 6, 1), new Date(121, 7, 1), 2, 14.99, "pasta.jpg"),
            new Coupon(2, 2, Category.ELECTRICITY, "Lamp", "Lightning Lamp", new Date(121, 6, 2), new Date(121, 7, 3), 1, 144.99, "lamp.jpg"),
            new Coupon(3, 3, Category.VACATION, "Australia", "Australia in Summer", new Date(121, 5, 15), new Date(121, 8, 9), 2, 1449.99, "vacation.jpg"),
            new Coupon(4, 3, Category.RESTAURANT, "Shushi", "nice sushi", new Date(121, 5, 15), new Date(121, 5, 9), 2, 150.99, "sushi.jpg")
    };

    public static void main(String[] args) {
        try {
            System.out.println("START");
            DatabaseManager.dropAndCreate();
            CouponExperationJob cedj = startDailyJob();
            LoginManager lm = LoginManager.getInstance();
            adminFacadeTasks(lm);
            companyFacadeTasks(lm);
            customerFacadeTasks(lm);
            stopDailyJob(cedj);
            System.out.println("FINISH");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static void adminFacadeTasks(LoginManager lm) throws Exception{
        ClientFacade clientFacade = lm.login("admin@admin.com","admin", ClientType.Admin);
        if (clientFacade instanceof AdminFacade) {
            AdminFacade admin = (AdminFacade) clientFacade;
            for (Company c: companies
                 ) {
                try{
                admin.addCompany(c);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("ADD COMPANIES A1 A2 A3 A4: **********");
            System.out.println(admin.getAllCompanies());
            Company a4 = admin.getOneCompany(4);
            a4.setPassword("qwerty454");
            admin.updateCompany(a4);
            System.out.println("UPDATE COMPANY A4: **********");
            System.out.println(admin.getAllCompanies());
            admin.deleteCompany(4);
            System.out.println("DELETE COMPANY A4: **********");
            System.out.println(admin.getAllCompanies());

            for (Customer c: customers
            ) {
                try{
                    admin.addCustomer(c);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("ADD CUSTOMERS B1 B2 B3 B4: **********");
            System.out.println(admin.getAllCustomers());
            Customer b4 = admin.getOneCustomer(4);
            b4.setPassword("abcdfgdfd");
            admin.updateCustomer(b4);
            System.out.println("UPDATE CUSTOMER B4: **********");
            System.out.println(admin.getAllCustomers());
            admin.deleteCustomer(4);
            System.out.println("DELETE CUSTOMER B4: **********");
            System.out.println(admin.getAllCustomers());
        }
    }

    private static void companyFacadeTasks(LoginManager lm) throws Exception {
        CompanyFacade companyFacade ;
        ClientFacade clientFacade = lm.login("Osem@walla.com","aaa12345",ClientType.Company);
        if (clientFacade instanceof CompanyFacade) {
            companyFacade = (CompanyFacade) clientFacade;
            companyFacade.addCoupon(coupons[0]);
            System.out.println("GET A1 COUPONS ************");
            System.out.println(companyFacade.getCompanyCoupons());
            System.out.println(companyFacade.getCompanyCoupons(Category.FOOD));
            System.out.println(companyFacade.getCompanyCoupons(15));
        }
        clientFacade = lm.login("alam@gmail.com", "aaa123455",ClientType.Company);
        if (clientFacade instanceof CompanyFacade) {
            companyFacade = (CompanyFacade) clientFacade;
            companyFacade.addCoupon(coupons[1]);
            System.out.println("GET A2 COUPONS ************");
            System.out.println(companyFacade.getCompanyCoupons());
            System.out.println(companyFacade.getCompanyCoupons(Category.ELECTRICITY));
            System.out.println(companyFacade.getCompanyCoupons(150));
        }
        clientFacade = lm.login("shufersal@co.il","aaa1234556",ClientType.Company);
        if (clientFacade instanceof CompanyFacade) {
            companyFacade = (CompanyFacade) clientFacade;
            companyFacade.addCoupon(coupons[2]);
            companyFacade.addCoupon(coupons[3]);
            companyFacade.getCompanyDetails();
            System.out.println("GET A3 COUPONS ************");
            System.out.println(companyFacade.getCompanyCoupons());
            System.out.println(companyFacade.getCompanyCoupons(Category.VACATION));
            System.out.println(companyFacade.getCompanyCoupons(160));
            Coupon c3 = coupons[2];
            c3.setAmount(3);
            companyFacade.deleteCoupon(c3.getId());
        }
    }

    private static void customerFacadeTasks(LoginManager lm) throws Exception {
        CustomerFacade customerFacade;
        ClientFacade clientFacade = lm.login("yoad@cohens.com", "aaa1234558", ClientType.Customer);
        if (clientFacade instanceof CustomerFacade) {
            customerFacade = (CustomerFacade) clientFacade;
            customerFacade.purchaseCoupon(coupons[0]);
            customerFacade.purchaseCoupon(coupons[1]);
            System.out.println("GET B1 DETAILS ************");
            System.out.println(customerFacade.getCustomerDetails());
            System.out.println("GET B1 COUPON ************");
            System.out.println(customerFacade.getCustomerCoupons());
            System.out.println("GET B1 FOOD COUPON ************");
            System.out.println(customerFacade.getCustomerCoupons(Category.FOOD));
            System.out.println("GET B1 COUPON UNDER 4.00 ************");
            System.out.println(customerFacade.getCustomerCoupons(4.00));
        }
        clientFacade = lm.login("shimi@taviroes.com", "aaa1234559", ClientType.Customer);
        if (clientFacade instanceof CustomerFacade) {
            customerFacade = (CustomerFacade) clientFacade;
            try {
                customerFacade.purchaseCoupon(coupons[1]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("GET B2 DETAILS ************");
                System.out.println(customerFacade.getCustomerDetails());
                System.out.println("GET B2 COUPON ************");
                System.out.println(customerFacade.getCustomerCoupons());
            }
        }
    }

    private static CouponExperationJob startDailyJob() {
        System.out.println("Start daily job");
        CouponExperationJob cedj = new CouponExperationJob();
        Thread t = new Thread(cedj);
        t.start();
        return cedj;
    }

    private static void stopDailyJob(CouponExperationJob cedj) {
        cedj.stop();
        System.out.println("Daily Job Stopped");
    }

}
