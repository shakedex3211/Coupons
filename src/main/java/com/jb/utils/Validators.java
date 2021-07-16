package com.jb.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

    public static Boolean EmailValidator(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        return pattern.matcher(email).matches();
    }

    public static Boolean PasswordValidator(String password) {
        Pattern pattern = Pattern.compile("^(?=\\S+$).{8,}$");

        return pattern.matcher(password).matches();
    }

}
