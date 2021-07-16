package com.jb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kobis on 07 Jun, 2021
 */
public class DateUtils {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static Date dateBuilder(int dd, int MM, int yyyy){
        try {
            return format.parse ( String.format("%d-%d-%d",yyyy,MM,(dd+1)) );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
