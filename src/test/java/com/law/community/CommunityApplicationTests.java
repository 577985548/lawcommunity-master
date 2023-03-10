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
     * �����ַ����е����дʻ�
     * @param content   �ı�
     * @param sensitiveWord   ���дʻ�
     * @return
     */
    public String filterSensitiveWords(String content, String sensitiveWord) {

        if (content == null || sensitiveWord == null) {
            return content;
        }

        //��ȡ�����дʻ���ͬ�������Ǻ�
        String starChar = getStarChar(sensitiveWord.length());

        //�滻���дʻ�
        return content.replace(sensitiveWord, starChar);
    }

    //�󲿷����дʻ���10�����ڣ�ֱ�ӷ��ػ�����ַ���
    public static String[] starArr={"*","**","***","****","*****","******","*******","********","*********","**********"};

    /**
     * ����n���Ǻŵ��ַ���
     * @param length
     * @return
     */
    private static String getStarChar(int length) {
        if (length <= 0) {
            return "";
        }
        //�󲿷����дʻ���10�����ڣ�ֱ�ӷ��ػ�����ַ���
        if (length <= 10) {
            return starArr[length - 1];
        }

        //����n���Ǻŵ��ַ���
        char[] arr = new char[length];
        for (int i = 0; i < length; i++) {
            arr[i] = '*';
        }
        return new String(arr);
    }


    public String FilterSensitiveWords(String content){
        //�����ؼ��ֺ�г����
        String[] strings = {"��", "��", "��","������"};
        //�������飬�ж��û������Ƿ�����ؼ���
        for (int i = 0; i < strings.length; i++) {
            //��������ؼ��֣������Ǻ��滻��
            if (content.contains(strings[i])) {
                content = content.replace(strings[i], "**");
            }
        }
        return content;
    }


    private static final String REPLACEMENT = "**";
    private static final Pattern PATTERN = Pattern.compile("\\s*|\t|\r|\n"); // ƥ��ո�ͻ��з�
    private List<String> sensitiveWords = Arrays.asList("��", "��", "��","������");

    /**
     * �������д�
     * @param text �����˵��ı�
     * @return ���˺���ı�
     */
    public String filter(String text) {
        if (text == null || text.trim().length() == 0) {
            return text;
        }
        text = PATTERN.matcher(text).replaceAll(""); // ȥ���ո�ͻ��з�
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
        System.out.println("��");
        System.out.println("��Ʒ");
        System.out.println(filter("��Ʒdsafdsafsda�ư����ȸ����˵�   ����"));
    }




}
