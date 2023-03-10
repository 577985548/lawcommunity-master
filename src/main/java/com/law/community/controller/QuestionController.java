package com.law.community.controller;


import com.law.community.dto.CommentDTO;
import com.law.community.dto.QuestionDTO;
import com.law.community.enums.CommentTypeEnum;
import com.law.community.exception.CommunityErrorCode;
import com.law.community.exception.CommunityException;
import com.law.community.service.CommentService;
import com.law.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

  @Autowired
  private QuestionService questionService;

  @Autowired
  private CommentService commentService;

  @GetMapping("/question/{id}")
  public String question(@PathVariable(name="id") String id, Model model) {
    Long questionId = null;
    try {
      questionId = Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new CommunityException(CommunityErrorCode.INVALID_INPUT);
    }
    QuestionDTO questionDTO = questionService.getById(questionId);

    List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);

    List<CommentDTO> comments = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);
    // 累加阅读数
    questionService.incView(questionId);
    model.addAttribute("question", questionDTO);
    model.addAttribute("comments", comments);
    model.addAttribute("relatedQuestions", relatedQuestions);
    return "question";
  }
}
