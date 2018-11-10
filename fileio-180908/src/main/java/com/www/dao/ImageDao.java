package com.www.dao;

import com.www.model.Image;
import com.www.utils.DBUtils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImageDao {

    public long save(Image image){
        Connection connection = DBUtils.getConnection();
        String sql = "INSERT INTO page(pageId,title,index,data,seq) values(?,?,?,?,?)";
        PreparedStatement pst = null;
        try{
            pst = connection.prepareStatement(sql);
            pst.setLong(1,image.getPageId());
            pst.setString(2,image.getTitle());
            pst.setLong(3,image.getIndex());
            pst.setString(4,image.getData());
            pst.setLong(5,image.getSeq());
            pst.executeUpdate();//执行SQL语句

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtils.closeAll(connection,pst,null);
        }
        return 0;
    }

}
