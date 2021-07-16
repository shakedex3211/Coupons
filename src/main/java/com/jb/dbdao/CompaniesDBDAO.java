package com.jb.dbdao;

import com.jb.beans.Company;
import com.jb.dao.CompaniesDAO;
import com.jb.utils.ArtUtils;
import com.jb.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompaniesDBDAO implements CompaniesDAO {

    private static final String QUERY_INSERT = "INSERT INTO `coupons`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?);";
    private static final String QUERY_UPDATE = "UPDATE `coupons`.`companies` SET `name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `coupons`.`companies` WHERE (`id` = ?);";
    private static final String QUERY_GET_ONE = "SELECT * FROM `coupons`.`companies` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `coupons`.`companies`";
    private static final String QUERY_CHECK_EXISTS="SELECT * FROM `coupons`.`companies` WHERE (email = ? and password = ?)";


    @Override
    public boolean isCompanyExists(String email, String password) throws SQLException  {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,email);
        map.put(2,password);
        ResultSet resultSet =  DBUtils.runQueryWithResults(QUERY_CHECK_EXISTS,map);

        if (resultSet.next())
            return true;

        return false;
    }

    @Override
    public void addCompany(Company company) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());
        DBUtils.runQuery(QUERY_INSERT, map);

//      ForDebug - and beuty
        System.out.println(ArtUtils.INSERT);
    }

    @Override
    public void updateCompany(Company company) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());
        map.put(4, company.getId());
        DBUtils.runQuery(QUERY_UPDATE, map);

//      ForDebug - and beuty
        System.out.println(ArtUtils.UPDATE);
    }

    @Override
    public void deleteCompany(int id) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        DBUtils.runQuery(QUERY_DELETE, map);

//      ForDebug - and beuty
        System.out.println(ArtUtils.DELETE);
    }

    @Override
    public Company getOneCompany(int id) throws SQLException {
        Company company = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ONE, map);
        resultSet.next();

        //int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        String email = resultSet.getString(3);
        String password = resultSet.getString(4);

        return new Company(id, name, email, password);
    }

    @Override
    public ArrayList<Company> getAllCompanies() throws SQLException {
        ArrayList<Company> companies = new ArrayList<Company>();

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String password = resultSet.getString(4);
            Company company = new Company(id, name, email, password);
            companies.add(company);
        }

        return companies;
    }

}
