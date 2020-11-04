package com.dell.crawler;

import com.dell.crawler.Utils.STools;
import com.dell.crawler.Utils.Tools;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class BiYingPic {
    public static void main(String[] args) throws Exception {
        System.out.println("请输入词条及数量");
        String[] inp = new Scanner(System.in).nextLine().split(",");
        if (inp.length==1)inp= new String[]{inp[0], "100"};
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        for (String s : Arrays.asList("东方不败", "长虹剑", "兰博基尼", "金刚狼", "外星人", "大海", "壮丽风景")) {
            inp = new String[]{s,"100"};
            driver.manage().window().setPosition(new Point(1910,1070));
            driver.get("https://cn.bing.com/images/search?q="+inp[0]);
            Tools.ThreadDownload(driver,inp[0],Integer.parseInt(inp[1]));
        }
//        driver.close();
//        java.awt.Desktop.getDesktop().open(new File("C:\\static\\img\\"+inp[0]));
    }
}
