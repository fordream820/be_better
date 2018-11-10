package com.www.dao;

import com.www.model.Page;
import com.www.utils.DBUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;

public class PageDao {

    public long save(Page page){
        Connection connection = DBUtils.getConnection();
        String sql = "INSERT INTO page(name,size,step,createdAt,updatedAt,url) values(?,?,?,?,?,?)";
        PreparedStatement pst = null;
        try{
            pst = connection.prepareStatement(sql);
            pst.setString(1,page.getName());
            pst.setLong(2,page.getSize());
            pst.setLong(3,page.getStep());
            pst.setDate(4,new Date(System.currentTimeMillis()));
            pst.setDate(5,new Date(System.currentTimeMillis()));
            pst.setString(6,page.getUrl());
            pst.executeUpdate();//执行SQL语句

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtils.closeAll(connection,pst,null);
        }
        return 0;
    }
}
