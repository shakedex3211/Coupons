package com.jb.facades;

import com.jb.dao.CompaniesDAO;
import com.jb.dao.CouponsDAO;
import com.jb.dao.CustomerDAO;
import com.jb.dao.CustomerVSCouponsDAO;
import com.jb.dbdao.CompaniesDBDAO;
import com.jb.dbdao.CouponsDBDAO;
import com.jb.dbdao.CustomerDBDAO;
import com.jb.dbdao.CustomerVSCouponsDBDAO;

public abstract class ClientFacade {

    protected CompaniesDAO companiesDAO;
    protected CouponsDAO couponsDAO;
    protected CustomerDAO customerDAO;
    protected CustomerVSCouponsDAO customerVSCouponsDAO;

    protected ClientFacade() {
        companiesDAO = new CompaniesDBDAO();
        couponsDAO = new CouponsDBDAO();
        customerDAO = new CustomerDBDAO();
        customerVSCouponsDAO = new CustomerVSCouponsDBDAO();
    }

    public abstract boolean login(String email, String password) throws Exception;
}
