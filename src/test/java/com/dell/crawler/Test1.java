package com.dell.crawler;

import com.dell.crawler.Utils.STools;
import com.dell.crawler.Utils.Tools;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Test1 {
    public static void main(String[] args) throws InterruptedException, IOException {
       /**INSERT INTO `javawebreview`.`book`(`name`, `author`, `price`, `sales`, `stock`) VALUES ('数据结构', '李军', 50, 15, 50)
        * */
        List<String> list = Arrays.asList("李白", "陶渊明", "苏轼", "杜甫", "王维", "李清照", "辛弃疾", "李煜", "纳兰容若", "李商隐", "杜牧", "范仲淹", "欧阳修", "柳宗元", "刘长卿", "刘禹锡", "屈原", "王昌龄", "白居易", "陆游", "韦应物", "王勃", "张若虚", "柳永", "周邦彦", "韦庄", "杜荀鹤", "岑参", "吴文英", "杨万里", "庾信", "贺知章", "孟浩然", "王安石", "李贺", "王之涣");
        List<String> books = Arrays.asList("年华是无效信","长相思","倾世皇妃","人生若只如初见","最后一只猫","盛开的青春","草样年华","欢喜城","私.时间的玫瑰","畅销的秘密","比时间更短 比爱情更长","深海夜未眠","南极姑娘","夏梦狂诗曲","我与世界只差一个你","我的黑色小礼服","配婚令","你和梦想必须一起活下去","格格不入","时间的女儿","跪求一腔热血","四月间事","跪求一腔热血","陪安东尼度过漫长岁月","无梦之境","灵魂舞会之征服女王心","暖暖","许你来生","晨昏","深夜食堂","东霓","全世界只有我看得见你","少女病","站住！我们恋爱吧","双生","当时明月在");
        for (int i = 0; i < books.size(); i++) {
            System.out.println("INSERT INTO `book`(`name`, `author`, `price`, `sales`, `stock`) " +
                    "VALUES ('"+books.get(i)+"', '"+list.get(i)
                    +"', "+((int) (Math.random()*90)+10)+", "+((int) (Math.random()*20))+", "+((int) (Math.random()*80)+20)+");");
        }
    }
}
