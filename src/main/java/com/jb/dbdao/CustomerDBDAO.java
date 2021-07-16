package com.jb.dbdao;

import com.jb.beans.Customer;
import com.jb.dao.CustomerDAO;
import com.jb.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class CustomerDBDAO implements CustomerDAO {

    private static final String QUERY_INSERT="INSERT INTO `coupons`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?)";
    private static final String QUERY_UPDATE="UPDATE `coupons`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?)";
    private static final String QUERY_DELETE="DELETE FROM `coupons`.`customers` WHERE (`id` = ?)";
    private static final String QUERY_GET_ONE="SELECT * FROM `coupons`.`customers` WHERE (`id` = ?)";
    private static final String QUERY_GET_ALL="SELECT `id`, `first_name`, `last_name`, `email`, `password` FROM `coupons`.`customers`";
    private static final String QUERY_CHECK_EXISTS="SELECT * FROM `coupons`.`customers` WHERE (email = ? and password = ?)";

    @Override
    public void addCustomer(Customer customer) throws SQLException {

        Map<Integer,Object> map = new HashMap<>();
        map.put(1,customer.getFirstName());
        map.put(2,customer.getLastName());
        map.put(3,customer.getEmail());
        map.put(4,customer.getPassword());
        DBUtils.runQuery(QUERY_INSERT,map);

    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,customer.getFirstName());
        map.put(2,customer.getLastName());
        map.put(3,customer.getEmail());
        map.put(4,customer.getPassword());
        map.put(5,customer.getId());
        DBUtils.runQuery(QUERY_UPDATE,map);
    }

    @Override
    public void deleteCustomer(int id) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,id);
        DBUtils.runQuery(QUERY_DELETE,map);
    }

    @Override
    public Customer getOneCustomer(int id) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,id);
        ResultSet resultSet =  DBUtils.runQueryWithResults(QUERY_GET_ONE,map);
        resultSet.next();
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        String email = resultSet.getString(4);
        String password = resultSet.getString(5);

        return new Customer(id,firstName,lastName,email,password);
    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException {
        ArrayList<Customer> results = new ArrayList<>();
        ResultSet resultSet =  DBUtils.runQueryWithResults(QUERY_GET_ALL);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String fisrtName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);
            results.add(new Customer(id,fisrtName,lastName,email,password));
        }

        return results;
    }

    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException {
        return getAllCustomers().stream().filter(customer -> (customer.getEmail().equals(email) && customer.getPassword().equals(password))).findFirst().isPresent();
    }
}
