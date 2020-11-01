package com.dell.crawler;

import com.dell.crawler.Utils.STools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Download implements Runnable {
    private List<String> links;
    private Integer num;
    private String name;
    public Download(List<String> list,String name) {
        links = list;
        num = links.size();
        this.name = name;
    }
    Object flag = new Object();
    @Override
    public void run() {
        int target = 0;
        while (num > 0) {
            synchronized (flag) {
                if (num > 0) {
                    num--;
                    target = num;
                }
            }
            try {
                System.out.println("link target is " + links.get(target));
                STools.download(links.get(target),name + "-" + target);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}