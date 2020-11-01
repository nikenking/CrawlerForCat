package com.dell.crawler;

import com.dell.crawler.Utils.STools;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Set;

public class GetPages {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        String kw = "https://www.a520z6oekgzc.com/home/piclist/7/832-1.html";
        WebDriver driver = new ChromeDriver();
        driver.get(kw);
        List<String> pageLinks = STools.getPageLinks(driver);
        String home = driver.getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (String pageLink : pageLinks) {
            js.executeScript("window.open('"+pageLink+"')");
        }
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            driver.switchTo().window(windowHandle);
            System.out.println(windowHandle);
            Thread.sleep(500);
        }
        System.out.println("home handler is "+home);
    }
}
