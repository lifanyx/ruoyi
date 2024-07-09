package com.ruoyi.common.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class BrowserAutomation {

    public static void main(String[] args) {
        // 设置 ChromeDriver 路径（根据您的实际情况设置）
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");

        // 创建 Chrome 浏览器实例
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-agent=Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Mobile Safari/537.36");
        options.addArguments("--incognito");
//        options.addArguments("--headless"); // 设置无头模式，即不显示浏览器界面
        WebDriver driver = new ChromeDriver(options);

        try {
            // 打开网页
            String url = "https://mobile.yangkeduo.com/fyxmkief.html?feed_id=6417601279605196667&refer_share_uin=73K6XKHXIYQLMANR4YB6HAX5QY_GEXDA&channel=1&page_from=602100&needs_login=1&shared_time=1720322563514&refer_share_channel=message&_x_source_feed_id=6417601279605196667&shared_sign=6dfba7ae9088fc96c0cd0895b26a5cd3&refer_share_id=ISCDIGL0Qyc9xESyEWa0K8q54XiYPQoq&page_id=39494_1720325811911_0t5ktlawoc&is_back=1";
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(url);
            // 打印页面标题
            System.out.println("Page title is: " + driver.getPageSource());
        } finally {
            // 关闭浏览器
            driver.quit();
        }
    }
}