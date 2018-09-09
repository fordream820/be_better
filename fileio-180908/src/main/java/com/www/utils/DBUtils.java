package com.www.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtils {

    private static String jdbcName="com.mysql.jdbc.Driver";
    private static String dbUrl="jdbc:mysql://localhost:3306/fileio";
    private static String dbUserName="fileio";
    private static String dbPassword="fileio";

    public static Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName(jdbcName);//加载驱动
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);//连接
        }catch (Exception e){
            e.fillInStackTrace();
            throw new NullPointerException();
        }

        return connection;
    }


    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet){
        try{
            if(resultSet != null){
                resultSet.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            if(statement != null){
                statement.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            if(connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
