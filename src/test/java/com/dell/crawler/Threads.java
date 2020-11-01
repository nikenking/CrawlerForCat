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
        String kw = "https://www.a520z6oekgzc.com/home/piclist/7/832-1.html";
        WebDriver driver = new ChromeDriver();
        driver.get(kw);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String mother = driver.getWindowHandle();
        List<String> pageLinks = STools.getPageLinks(driver);
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
            Thread.sleep(200);
            js.executeScript("window.stop()");
            STools.ThreadDownload(driver,"porn"+i);
        }
    }
}

