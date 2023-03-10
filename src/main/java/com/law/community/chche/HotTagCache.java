package com.law.community.chche;


import com.law.community.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Component
@Data
public class HotTagCache {
  private List<String> hots = new ArrayList<>();// 存储热门标签的列表
  // 更新热门标签的方法，传入一个 Map<String, Integer> 类型的标签集合
  public void updateTags(Map<String, Integer> tags) {
    int max = 10;
    PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max); // 存10个 创建一个最大容量为 max 的优先队列

    tags.forEach((name, priority) -> {
      HotTagDTO hotTagDTO = new HotTagDTO();
      hotTagDTO.setName(name);
      hotTagDTO.setPriority(priority);
      if (priorityQueue.size() < max) {// 如果队列未满，则直接添加
        priorityQueue.add(hotTagDTO);
      } else {// 如果队列已满，判断该标签的热度是否比当前队列中热度最小的标签更高，若是，则将该标签加入队列并移除热度最小的标签
        HotTagDTO minHot = priorityQueue.peek();
        if (hotTagDTO.compareTo(minHot) > 0) {
          priorityQueue.poll(); // 去掉最小的
          priorityQueue.add(hotTagDTO);
        }
      }
    });
//    System.out.println(priorityQueue);

    List<String> sortedTags = new ArrayList<>();// 创建一个存储排序后标签的列表

    HotTagDTO poll = priorityQueue.poll();
    while (poll != null) {
      sortedTags.add(0, poll.getName());
      poll = priorityQueue.poll();
    }
    hots = sortedTags;// 将排序后的标签列表赋值给 hots
//    System.out.println(hots);
  }
}
