package com.law.community.controller;


import com.law.community.dto.CommentCreateDTO;
import com.law.community.dto.CommentDTO;
import com.law.community.dto.ResultDTO;
import com.law.community.enums.CommentTypeEnum;
import com.law.community.model.Comment;
import com.law.community.model.User;
import com.law.community.service.CommentService;
import com.law.community.exception.CommunityErrorCode;
import com.law.community.utils.KeywordFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {


  @Autowired
  private CommentService commentService;

  @ResponseBody
  @RequestMapping(value = "/comment", method = RequestMethod.POST)
  // RequestBody,自动反序列化
  public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                     HttpServletRequest request) {

    User user = (User) request.getSession().getAttribute("user");

    if (user == null) {
      return ResultDTO.errorOf(CommunityErrorCode.NO_LOGIN);
    }
    // commentCreateDTO.getContent() == null || commentCreateDTO.getContent().equals("") ======StringUtils.isBlank(commentCreateDTO.getContent())
    if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
      return ResultDTO.errorOf(CommunityErrorCode.CONTENT_IS_EMPTY);
    }

    Comment comment = new Comment();
    comment.setParentId(commentCreateDTO.getParentId());
    comment.setContent(new KeywordFilter().filter(commentCreateDTO.getContent()));
    comment.setType(commentCreateDTO.getType());
    comment.setGmtCreate(System.currentTimeMillis());
    comment.setGmtModified(System.currentTimeMillis());
    comment.setLikeCount(0L);
    comment.setCommentator(user.getId());
    commentService.insert(comment, user);
    return ResultDTO.okOf();
  }

  @ResponseBody
  @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
  public ResultDTO<List> comments(@PathVariable(name = "id") Long id) {
    List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
    return ResultDTO.okOf(commentDTOS);
  }
}
