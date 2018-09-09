package com.www.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownload {

    //http://w3.afulyu.pw/pw/thread.php?fid=14&page=2
    public static String path = "/Users/wudawei/Documents/www/fileio";
    public static void main(String[] args) {
        String url = "http://p3.urlpic.club/picturespace/upload/image/20180822/82206518995.jpg";
        downloadPicture(url);
    }
    //链接url下载图片
    public static void downloadPicture(String urlList) {
        URL url = null;
        int imageNumber = 0;

        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            String imageName =  path + "/82206518995.jpg";

            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context = output.toByteArray();
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
