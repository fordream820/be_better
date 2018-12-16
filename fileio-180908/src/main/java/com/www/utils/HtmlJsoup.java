package com.www.utils;

import com.www.model.HtmlModel;
import net.iharder.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HtmlJsoup {

    public static String encoding = "utf-8";
    public static String baseUrl = "http://w3.afulyu.pw/pw/";
    public static String baseFilePath = "F:/www/fileio";
    public static int timeout = 100000;

    /**
     * 第一步：获取页面的源代码；
     * 第二步：解析源代码，含有图片的标签，再找到图片标签里面的src；
     * 第三步：利用Java里面的net包，网络编程
     * */


    /**
     * 根据网页和编码获取网页内容和源代码
     * @param url
     * @param encoding
     */
    public static String getHtmlResourceByUrl(String url,String encoding){
        StringBuffer buffer   = new StringBuffer();
        URL urlObj            = null;
        HttpURLConnection connection      = null;
        InputStreamReader in  = null;
        BufferedReader reader = null;

        try {
            // 建立网络连接
            urlObj = new URL(url);
            // 打开网络连接
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 创建输入流
            in     = new InputStreamReader(connection.getInputStream(),encoding);
            // 创建一个缓冲写入流
            reader = new BufferedReader(in);

            String line = null;
            while ((line = reader.readLine()) != null) {
                // 一行一行追加
                buffer.append(line+"\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }

    /**
     * 根据图片的URL下载的图片到本地的filePath
     * @param filePath 文件夹
     * @param imageUrl 图片的网址
     */
    public static void downImages(String filePath,String imageUrl){
        // 截取图片的名称
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/"));
        if(fileName.indexOf(".") >= 0){
            fileName = fileName.substring(0,fileName.indexOf("."));
        }

        File file = new File(filePath+fileName);
        if(file.exists()){
            return;
        }

        //创建文件的目录结构
        File files = new File(filePath);
        if(!files.exists()){// 判断文件夹是否存在，如果不存在就创建一个文件夹
            files.mkdirs();
        }
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream is = connection.getInputStream();
            // 创建文件

            FileOutputStream out = new FileOutputStream(file);
            int i = 0;
            byte[] b = new byte[1024];
            while((i = is.read(b)) != -1){
                out.write(b,0,i);
            }
            is.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<HtmlModel> getHtml(String url){
        List<HtmlModel> list = new ArrayList<HtmlModel>();
//        String htmlResource = getHtmlResourceByUrl(url, encoding);
//        // 解析网页源代码
//
//        Document document = Jsoup.parse(htmlResource);

        Document document = null;
        try {
            document = Jsoup.parse(new URL(url),timeout);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<HtmlModel>();
        }
        // 获取所有图片的地址
        Elements elements = document.getElementsByTag("a");

        for(Element element : elements){
            String href = element.attr("href");
            Node node = null;
            List<Node> child = element.childNodes();
            if(child.size() > 0){
                node = child.get(0);
            }
            if(href.startsWith("htm_data") && node != null && node.toString().charAt(0) == '['){
                String title = node.toString();
                HtmlModel htmlModel = new HtmlModel(title,baseUrl + href);
                list.add(htmlModel);
            }
        }
        return list;
    }

    public static void getImg(HtmlModel htmlModel){
        String url = htmlModel.getUrl();
        String title = htmlModel.getTitle();
        String title1 = "other";
        String title2 = title;
        if(title.indexOf(']')  >= 0 ){
            title1 = title.substring(0,title.indexOf(']') + 1);
            title2 = title.substring(title.indexOf(']') + 1);
        }

        title2 = Base64.encodeBytes(title2.getBytes());

        String filePath = baseFilePath + File.separator +  title1+ File.separator +  title2;
        new File(filePath).mkdirs();
//        String htmlResource = getHtmlResourceByUrl(url, encoding);

        // 解析网页源代码
        //Document document = Jsoup.parse(htmlResource);
        Document document = null;
        try {
            document = Jsoup.parse(new URL(url),timeout);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        // 获取所有图片的地址
        Elements elements = document.getElementsByTag("img");

        //System.out.println("-------下载" + htmlModel.getTitle() + "开始！-------");
        for(Element element : elements){
            String imgSrc = element.attr("src");
            if (!"".equals(imgSrc) && (imgSrc.startsWith("http://") || imgSrc.startsWith("https://"))) {
                // 判断imgSrc是否为空且是否以"http://"开头
                //System.out.println("正在下载的图片的地址：" + imgSrc);
                System.out.println(filePath);
                System.out.println(imgSrc);
                downImages(filePath, imgSrc);
            }
        }

        File file = new File(filePath + File.separator + "OK");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(1);
        } catch (Exception e) {

        }finally {
            if(out != null){
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {

                }
            }
        }

        //System.out.println("-------下载" + htmlModel.getTitle() + "完毕！-------");
    }

    //执行测试程序代码
    public static void main(String[] args) {
        start1();

    }

    public static void start2(){
        HtmlModel model = new HtmlModel();
//        model.setUrl("http://w3.afulyu.pw/pw/htm_data/14/1811/1379638.html");
        model.setUrl("http://93.cao1024lui99.com/pw/htm_data/14/1811/1379644.html");
        model.setTitle("[11.11] XXX[50P]");
        getImg(model);
    }
    public static void start1(){
        //http://93.cao1024lui99.com/pw/htm_data/14/1811/1379644.html
        //http://p7.urlpic.club/pic1893/upload/image/20181111/11110850619.jpg
        //String url = "http://93.cao1024lui99.com/pw/thread-htm-fid-14-page-2.html";
        int page = 1;
        while(page < 2){
            String url = url = "http://93.cao1024lui99.com/pw/thread-htm-fid-14-page-" + page + ".html";
            final List<HtmlModel> list = getHtml(url);

            System.out.println(list.size());
//            System.out.println(list.get(0).getUrl());
//            System.out.println(list.get(0).getTitle());
            //http://93.cao1024lui99.com/pw/htm_data/14/1811/1378454.html
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            for(final HtmlModel htmlModel : list){
                getImg(htmlModel);
            }

//            ExecutorService fixedThreadPool = null;
//            try{
//                fixedThreadPool = Executors.newFixedThreadPool(20);
//                for(final HtmlModel htmlModel : list){
//                    fixedThreadPool.execute(new Runnable() {
//                        public void run() {
//                            getImg(htmlModel);
//                        }
//                    });
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }finally {
//                if(fixedThreadPool != null){
//                    try{
//                        fixedThreadPool.shutdown();
//                        fixedThreadPool.awaitTermination(1,TimeUnit.MINUTES);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }
            page ++;
        }
    }
}
