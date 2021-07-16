package com.jb.beans;

public class CustomerCoupon {

    int couponID;
    int customerID;

    public CustomerCoupon(int couponID, int customerID) {
        this.customerID = customerID;
        this.couponID = couponID;
    }

    public CustomerCoupon() {
        this.couponID = 0;
        this.customerID = 0;
    }


    public int getCouponID() {
        return this.couponID;
    }

    public void setCouponID(int couponID) {
        this.couponID = couponID;
    }

    public int getCustomerID() { return this.customerID; }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "CompanyCoupon{" +
                "couponID=" + couponID +
                ", customerID=" + customerID +
                '}';
    }
    // TODO: Run Tests ont he class + GETTER + SETTER + CTORs
}
