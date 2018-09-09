package com.www.dao;

import com.www.utils.DBUtils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ImageDao {

    public int save(InputStream inputStream){
//        Connection connection = DBUtils.getConnection();
//        String sql = "insert into image(picture) values(?)";
//        PreparedStatement pst = null;
//        try{
//            pst.setAsciiStream(1,inputStream);//填第一个坑
//            return pst.executeUpdate();//执行SQL语句
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        return 0;
    }

}
