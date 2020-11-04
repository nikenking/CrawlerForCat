package com.dell.crawler;

import com.dell.crawler.Utils.STools;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class Threads {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.a520z6oekgzc.com/home/piclist/7/832-" + 1 + ".html");
        STools.PageDownload(driver);
    }
}

