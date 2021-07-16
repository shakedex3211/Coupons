package com.jb.utils;

import com.jb.db.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * Created by kobis on 31 May, 2021
 */
public class DBUtils {

    public static void runQuery(String sql) throws SQLException {
        Connection connection = null;
        try{
            //STEP 2 - Open Connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            //STEP 3 - Run your SQL instruction
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            //STEP 5 - Close Connection to DB
            ConnectionPool.getInstance().restoreConnection(connection);
        }

    }

    public static void runQuery(String sql, Map<Integer,Object> map) throws SQLException {
        Connection connection = null;
        try{
            //STEP 2 - Open Connection to DB
            connection = ConnectionPool.getInstance().getConnection();

            //STEP 3 - Run your SQL instruction
            PreparedStatement statement = connection.prepareStatement(sql);

            for (Map.Entry<Integer,Object> entry: map.entrySet()) {
                int key = entry.getKey();
                Object value = entry.getValue();
                if(value instanceof String){
                    statement.setString(key,String.valueOf(value));
                }
                else if (value instanceof Integer){
                    statement.setInt(key,(int)value);
                }else if(value instanceof Float){
                    statement.setFloat(key,(float) value);
                }else if(value instanceof Double){
                    statement.setDouble(key,(double) value);
                }else if(value instanceof Date){
                    statement.setObject(key,value);
                }
            }
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            //STEP 5 - Close Connection to DB
            ConnectionPool.getInstance().restoreConnection(connection);
        }

    }

    public static ResultSet runQueryWithResults(String sql) throws SQLException {
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement stm = connection.prepareStatement(sql);
            resultSet = stm.executeQuery();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
        return resultSet;
    }

    public static ResultSet runQueryWithResults(String sql,Map<Integer,Object> map) throws SQLException {
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            for (Map.Entry<Integer,Object> entry: map.entrySet()) {
                int key = entry.getKey();
                Object value = entry.getValue();
                if(value instanceof String){
                    statement.setString(key,String.valueOf(value));
                }
                else if (value instanceof Integer){
                    statement.setInt(key,(int)value);
                }else if(value instanceof Float){
                    statement.setFloat(key,(float) value);
                }
            }
            resultSet = statement.executeQuery();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
        return resultSet;
    }
}
