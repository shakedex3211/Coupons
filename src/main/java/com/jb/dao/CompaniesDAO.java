package com.jb.dao;

import com.jb.beans.Company;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CompaniesDAO {/*DAO = DATA ACCESS OBJECT*/

   boolean isCompanyExists(String email, String password)throws SQLException;
   void addCompany(Company Company) throws SQLException;
   void updateCompany(Company Company) throws SQLException;
   void deleteCompany(int CompanyID) throws SQLException;

   ArrayList<Company> getAllCompanies() throws SQLException;
   Company getOneCompany(int CompanyID) throws SQLException;

}
