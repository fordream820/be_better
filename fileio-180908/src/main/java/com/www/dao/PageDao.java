package com.www.dao;

import com.www.model.Page;
import com.www.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

public class PageDao {

    public int save(Page page){
        Connection connection = DBUtils.getConnection();
        String sql = "INSERT INTO page(name,size,step,createdAt,updatedAt) values(?,?,?,?,?)";
        PreparedStatement pst = null;
        try{
            pst.setString(1,page.getName());
            pst.setLong(2,page.getSize());
            pst.setLong(3,page.getStep());
            pst.setDate(4,new Date(System.currentTimeMillis()));
            pst.setDate(5,new Date(System.currentTimeMillis()));
            return pst.executeUpdate();//执行SQL语句
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
