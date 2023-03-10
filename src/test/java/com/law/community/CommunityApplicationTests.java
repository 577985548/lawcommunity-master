package com.law.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

    /**
     * 过滤字符串中的敏感词汇
     * @param content   文本
     * @param sensitiveWord   敏感词汇
     * @return
     */
    public String filterSensitiveWords(String content, String sensitiveWord) {

        if (content == null || sensitiveWord == null) {
            return content;
        }

        //获取和敏感词汇相同数量的星号
        String starChar = getStarChar(sensitiveWord.length());

        //替换敏感词汇
        return content.replace(sensitiveWord, starChar);
    }

    //大部分敏感词汇在10个以内，直接返回缓存的字符串
    public static String[] starArr={"*","**","***","****","*****","******","*******","********","*********","**********"};

    /**
     * 生成n个星号的字符串
     * @param length
     * @return
     */
    private static String getStarChar(int length) {
        if (length <= 0) {
            return "";
        }
        //大部分敏感词汇在10个以内，直接返回缓存的字符串
        if (length <= 10) {
            return starArr[length - 1];
        }

        //生成n个星号的字符串
        char[] arr = new char[length];
        for (int i = 0; i < length; i++) {
            arr[i] = '*';
        }
        return new String(arr);
    }


    public String FilterSensitiveWords(String content){
        //创建关键字和谐数组
        String[] strings = {"烟", "酒", "毒","电子烟"};
        //遍历数组，判断用户输入是否包含关键字
        for (int i = 0; i < strings.length; i++) {
            //如果包含关键字，就用星号替换掉
            if (content.contains(strings[i])) {
                content = content.replace(strings[i], "**");
            }
        }
        return content;
    }


    private static final String REPLACEMENT = "**";
    private static final Pattern PATTERN = Pattern.compile("\\s*|\t|\r|\n"); // 匹配空格和换行符
    private List<String> sensitiveWords = Arrays.asList("烟", "酒", "毒","电子烟");

    /**
     * 过滤敏感词
     * @param text 待过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text) {
        if (text == null || text.trim().length() == 0) {
            return text;
        }
        text = PATTERN.matcher(text).replaceAll(""); // 去除空格和换行符
        StringBuilder sb = new StringBuilder(text);
        for (String word : sensitiveWords) {
            int index = sb.indexOf(word);
            while (index != -1) {
                sb.replace(index, index + word.length(), REPLACEMENT);
                index = sb.indexOf(word, index + REPLACEMENT.length());
            }
        }
        return sb.toString();
    }



    @Test
    public void contextLoads() {
        System.out.println("烟");
        System.out.println("毒品");
        System.out.println(filter("毒品dsafdsafsda酒按进度付款了电   子烟"));
    }




}
