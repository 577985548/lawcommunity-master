package com.law.community.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class KeywordFilter {

    private static final String REPLACEMENT = "⬛";
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
}