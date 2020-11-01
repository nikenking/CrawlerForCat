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
        String kw = "https://www.a520z6oekgzc.com/home/piclist/7/832-1.html";
        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get(kw);//链接
        List<String> pagetitles = STools.getPagetitles(driver);
        for (String pagetitle : pagetitles) {
            System.out.println(pagetitle);
        }
    }
}
