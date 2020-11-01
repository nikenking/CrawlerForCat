package com.dell.crawler;

import com.dell.crawler.Utils.STools;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        String kw = "https://www.a520z6oekgzc.com/home/pic/1045974_7.html";//得到url
        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get(kw);//链接
        Thread.sleep(2000);
        js.executeScript("window.stop()");
        STools.ThreadDownload(driver,"porn1");
    }
}
