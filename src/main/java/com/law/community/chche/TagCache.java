package com.law.community.chche;

import com.law.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
public class TagCache  {
  private static List<TagDTO> tagDTOS = new ArrayList<>();
  private static List<String> customTags = new ArrayList<>(); // 新添加的自定义标签

  static {
    // 添加默认标签
    TagDTO program = new TagDTO();
    program.setCategoryName("校园热门");
    program.setTags(Arrays.asList("约车", "出游","二手", "广科约拍", "音乐节", "英语四级", "考研", "恋爱", "广科新闻", "运动会", "篮球", "羽毛球", "瑜伽", "动漫", "二次元"));
    tagDTOS.add(program);
  }

  public static List<TagDTO> get() {
    return tagDTOS;
  }

  public static void addCustomTag(String tag) {
    customTags.add(tag);
  }

  public static String filterInvalid(String tags) {
    String[] split = StringUtils.split(tags, ",");
    List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
    tagList.addAll(customTags); // 将自定义标签加入到列表中
    String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
    return invalid;
  }
}

//public class TagCache  {
//  public static List<TagDTO> get() {
//    List<TagDTO> tagDTOS = new ArrayList<>();
//    TagDTO program = new TagDTO();
//    program.setCategoryName("校园热门");
//    program.setTags(Arrays.asList("约车", "出游","二手", "广科约拍", "音乐节", "英语四级", "考研", "恋爱", "广科新闻", "运动会", "篮球", "羽毛球", "瑜伽", "动漫", "二次元"));
//    tagDTOS.add(program);
//
//
//    return tagDTOS;
//  }
//  public static String filterInvalid(String tags) {
//    String[] split = StringUtils.split(tags, ",");
//    List<TagDTO> tagDTOS = get();
//
//    List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
//    String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
//    return invalid;
//  }
//}
