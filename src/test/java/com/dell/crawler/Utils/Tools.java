package com.dell.crawler.Utils;

import com.dell.crawler.Download;
import netscape.security.UserTarget;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    /**
     * 获得当前界面可用的图片链接,return type list,只针对porn卡通单独页
     */
    public static List<String> getLinklist(WebDriver driver, Integer size) throws InterruptedException {
        List<String> list = new ArrayList<>();
        System.out.println("start get link");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        while (list.size() < size) {
            List<WebElement> img = driver.findElements(By.tagName("img"));
            for (WebElement item : img) {
                String src = item.getAttribute("src");
                /**src="https://tse2-mm.cn.bing.net/th/id/OIP.HBxFjW8u_ecDYkSOOX-0YAHaE8?w=282&h=188&c=7&o=5&dpr=1.24&pid=1.7"*/
                if (src != null && !list.contains(src) && Pattern.matches("https://.*?bing\\.net/.*?w=\\d+&h=\\d+.*?", src) && list.size()<size) {
                    list.add(src);
                    System.out.println("get one :" + src);
                }
            }
            js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
            Thread.sleep(200);
        }
        return list;
    }

    /**
     * 下载指定链接到C:/static/img/name下,全名称:name-1.jpg
     */

    public static void download(String url, String name) throws IOException {
        File file = new File("C:\\static\\img\\" + name.substring(0,name.lastIndexOf("-")));
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
        }
        BufferedOutputStream bs = new BufferedOutputStream(
                new FileOutputStream(file + "\\" + name + ".png"));
        HttpURLConnection httpcon = (HttpURLConnection) new URL(url).openConnection();
        httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
        DataInputStream di = new DataInputStream(httpcon.getInputStream());
        byte[] bytes = new byte[1024];
        int len = 0;
        System.out.println("downloading:" + name + ".png");
        while ((len = di.read(bytes)) > 0) {
            bs.write(bytes, 0, len);
        }
        bs.close();
        di.close();
    }

    /**将必应图片尺寸最大化*/
    public static void changeBiyingPicSize(List<String> list,Integer width,Integer height){
        for (int i = 0; i < list.size(); i++) {
            Matcher m = Pattern.compile("(.*w=)\\d+(&h=)\\d+(.*)").matcher(list.get(i));
            while (m.find()) {
                list.set(i,m.group(1)+"8000"+m.group(2)+"6000"+m.group(3));
            }
        }
    }

    /**
     * 多线程下载所有通过getlinklist得到的当前所有可用图片，到指定name文件夹下
     */
    public static void ThreadDownload(WebDriver driver, String name, Integer size) throws InterruptedException {
        System.out.println("start img getLinks");
        List<String> linklist = Tools.getLinklist(driver, size);
        Tools.changeBiyingPicSize(linklist,8000,6000);
        Download download = new Download(linklist, name);
        for (int i = 0; i < 10; i++) {
            new Thread(download).start();
        }
    }

    /**
     * 整合，获取当前页所有可显示getPageLinks,打开所有界面
     * 循环跳转，每一页调用一次getLinklist,再多线程下载
     */
    public static void PageDownload(WebDriver driver) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String mother = driver.getWindowHandle();
    }
}
