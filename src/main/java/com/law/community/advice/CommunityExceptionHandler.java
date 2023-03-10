package com.law.community.advice;


import com.alibaba.fastjson.JSON;
import com.law.community.dto.ResultDTO;
import com.law.community.exception.CommunityErrorCode;
import com.law.community.exception.CommunityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/*

这是一个控制器通知（ControllerAdvice），用于全局捕获异常并处理。
*/
@Slf4j
@ControllerAdvice
public class CommunityExceptionHandler {
  // 处理所有的异常
  @ExceptionHandler(Exception.class)
  ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
    // 获取请求的Content-Type
    String contentType = request.getContentType();
    if ("application/json".equals(contentType)) {
      ResultDTO resultDTO = null;
      // 如果是JSON请求，则返回JSON响应
      if (e instanceof CommunityException) {
        resultDTO = ResultDTO.errorOf((CommunityException) e);
      } else {
        log.error("handle error", e);
        resultDTO = ResultDTO.errorOf(CommunityErrorCode.SYSTEM_ERROR);
      }

      try {
        response.setContentType("application/json");
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(resultDTO));
        writer.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      return null;
    } else {
      // 如果不是JSON请求，则返回错误页面
      if (e instanceof CommunityException) {
        model.addAttribute("message", e.getMessage());
      } else {
        log.error("handle error", e);
        model.addAttribute("message", CommunityErrorCode.SYSTEM_ERROR.getMessage());
      }
      return new ModelAndView("error");
    }
  }
}
