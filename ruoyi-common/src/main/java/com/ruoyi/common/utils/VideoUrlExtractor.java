package com.ruoyi.common.utils;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoUrlExtractor {

    private static final Logger log = LoggerFactory.getLogger(VideoUrlExtractor.class);

    private static final String PDD_VEDIO_HOST = "https://video4.pddpic.com";

    public static JSONObject extractor(String url) {
        JSONObject jsonObject = new JSONObject();
        String videoUrl = "";
        try {
            // 使用Jsoup请求页面内容
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Mobile Safari/537.36")
                    .get();

            // 查找包含视频链接的<script>标签
            Elements scripts = document.getElementsByTag("script");
            for (Element script : scripts) {
                String scriptContent = script.html();
                videoUrl = extractVideoUrlWithPattern(scriptContent, "topicListLabel", "feedsCount");
                if (!videoUrl.isEmpty()) {
                    // 替换字符串并解析为JSONObject
                    videoUrl = videoUrl.replace("topicListLabel\":", "")
                            .replace(",\"feedsCount", "}");
                    jsonObject = new JSONObject(videoUrl);

                    // 解析视频链接
                    String decodedLinkUrl = URLDecoder.decode(jsonObject.getString("linkUrl"), StandardCharsets.UTF_8.toString());
                    videoUrl = extractVideoUrlWithPattern(decodedLinkUrl, "http:", ".mp4");
                    if (!videoUrl.isEmpty()) {
                        jsonObject.put("linkUrl",hostConverter(videoUrl));
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static String extractVideoUrlWithPattern(String scriptContent, String start, String end) {
        String videoUrl = "";
        try {
            // 使用正则表达式提取linkUrl
            String patternString =  Pattern.quote(start) + ".*" + Pattern.quote(end);
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(scriptContent);

            if (matcher.find()) {
                videoUrl = matcher.group(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoUrl;
    }

    public static String hostConverter (String url) {
        String host = "";
        // 构造正则表达式
        String regex = "(http://[^/]+)";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        // 查找匹配项
        if (matcher.find()) {
            host = matcher.group(1);
        }
        return url.replace(host,PDD_VEDIO_HOST);
    }

    public static void main(String[] args) {
        JSONObject jsonObject = extractor("https://mobile.yangkeduo.com/fyxmkief.html?feed_id=6417601279605196667&refer_share_uin=73K6XKHXIYQLMANR4YB6HAX5QY_GEXDA&channel=1&page_from=602100&needs_login=1&shared_time=1720322563514&refer_share_channel=message&_x_source_feed_id=6417601279605196667&shared_sign=6dfba7ae9088fc96c0cd0895b26a5cd3&refer_share_id=ISCDIGL0Qyc9xESyEWa0K8q54XiYPQoq&page_id=39494_1720325811911_0t5ktlawoc&is_back=1");
    }
}