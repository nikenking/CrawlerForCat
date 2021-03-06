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
import java.util.regex.Pattern;

public class STools {
    /**
     * 获得当页所有的图片链接,return type map,只针对porn卡通单独页
     */
    public static Map<String, Integer> getLinks(WebDriver driver, int time) throws InterruptedException {
        Map<String, Integer> map = new HashMap<>();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < time; i++) {
            List<WebElement> img = driver.findElements(By.tagName("img"));
            for (WebElement item : img) {
                String src = item.getAttribute("src");
                /*https://pic1.hmpicimage.com/katong/2020/10/31/2773dda6-cae2-474b-b86c-93992763515d/001.jpg*/
                if (!map.containsKey(src) && src != null && Pattern.matches("https://.*\\.com/katong/.*\\.jpg", src)) {
                    map.put(src, map.size() + 1);
                    System.out.println(src);
                }
            }
            js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
            Thread.sleep(1000);
        }
        return map;
    }

    /**
     * 获得当前界面可用的图片链接,return type list,只针对porn卡通单独页
     */
    public static List<String> getLinklist(WebDriver driver) throws InterruptedException {
        List<String> list = new ArrayList<>();
        System.out.println("start get link");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        List<WebElement> img = driver.findElements(By.tagName("img"));
        for (WebElement item : img) {
            String src = item.getAttribute("src");
            /*https://pic1.hmpicimage.com/katong/2020/10/31/2773dda6-cae2-474b-b86c-93992763515d/001.jpg*/
            if (src != null && !list.contains(src) && Pattern.matches("https://.*\\.com/katong/.*\\.jpg", src)) {
                list.add(src);
                System.out.println("get one :" + src);
            }
        }
        return list;
    }

    /**
     * 下载指定链接到C:/static/img/name下,全名称:name-1.jpg
     */
    public static void download(String url, String name) throws IOException {
        File file = new File("C:\\static\\img\\" + name.split("-")[0]);
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

    /**
     * 获取当前页面所有的图片显示页可用跳转链接,只针对porn卡通某一页
     */
    public static List<String> getPageLinks(WebDriver driver) {
        List<String> list = new ArrayList<>();
        List<WebElement> pages = driver.findElements(By.tagName("a"));
        for (WebElement page : pages) {
            String href = page.getAttribute("href");
            if (href != null && !list.contains(href) && Pattern.matches("https://www.*com/home/pic/.*?\\d+.*?.html", href)) {
                list.add(href);
                System.out.println("get one href :" + href);
            }
        }
        return list;
    }

    /**
     * 多线程下载所有通过getlinklist得到的当前所有可用图片，到指定name文件夹下
     */
    public static void ThreadDownload(WebDriver driver, String name) throws InterruptedException {
        System.out.println("start img getlinks");
        List<String> linklist = STools.getLinklist(driver);
        Download download = new Download(linklist, name);
        for (int i = 0; i < 10; i++) {
            new Thread(download).start();
        }
    }

    /**
     * 得到当前页面所有的链接标题，主要用于替换porn-name*/

    public static List<String> getPagetitles(WebDriver driver){
        List<String> list = new ArrayList<>();
        List<WebElement> pages = driver.findElements(By.tagName("div"));
        for (WebElement page : pages) {
            String title = page.getAttribute("class");
            if (Pattern.matches(".*?vodname.*?",title)){
                list.add(page.getAttribute("innerHTML"));
            }
        }
        return list;
    }

    /**
     * 整合，获取当前页所有可显示getPageLinks,打开所有界面
     * 循环跳转，每一页调用一次getLinklist,再多线程下载*/
    public static void PageDownload(WebDriver driver) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String mother = driver.getWindowHandle();
        List<String> pageLinks = STools.getPageLinks(driver);//获取页面所有可跳转链接
        List<String> pagetitles = STools.getPagetitles(driver);//获取页面所有可跳转链接对应标题
        List<String> handlers = new ArrayList<>();
        for (String pageLink : pageLinks) {
            js.executeScript("window.open('"+pageLink+"')");
            for (String handle : driver.getWindowHandles()) {
                if (!mother.equals(handle)&&!handlers.contains(handle)){
                    handlers.add(handle);
                }
            }
        }
        Thread.sleep(3000);
        for (int i = 0; i < handlers.size(); i++) {
            driver.switchTo().window(handlers.get(i));
            System.out.println("i am ready");
            js.executeScript("window.stop()");
            System.out.println("i am ready2");
            STools.ThreadDownload(driver,pagetitles.get(i));
            driver.close();
        }
    }
}
