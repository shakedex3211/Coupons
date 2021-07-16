package com.jb.facades;

public class LoginManager {

    private static LoginManager instance = new LoginManager();

    private LoginManager() {

    }

    public static LoginManager getInstance() {
        if (instance == null)
            instance = new LoginManager();

        return instance;
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws Exception {

        ClientFacade facade = null;

        switch (clientType){
            case Admin:
                AdminFacade a = new AdminFacade();
                if (a.login(email,password))
                    facade = a;
                break;
            case Company:
                CompanyFacade co = new CompanyFacade();
                if (co.login(email,password))
                facade = co;
                break;
            case Customer:
                CustomerFacade cu = new CustomerFacade();
                if (cu.login(email,password))
                    facade = cu;
                break;
            default:
                throw new Exception("User Error");
        }

        return facade;
    }
}
