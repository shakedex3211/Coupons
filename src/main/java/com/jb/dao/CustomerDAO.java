package com.jb.dao;

import com.jb.beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;


public interface CustomerDAO {

    void addCustomer(Customer customer) throws SQLException;
    void updateCustomer(Customer customer) throws SQLException;
    void deleteCustomer(int customerID) throws SQLException;

    Customer getOneCustomer(int customerID) throws SQLException;

    ArrayList<Customer> getAllCustomers() throws SQLException;
    boolean isCustomerExists(String email, String password) throws SQLException;

}
