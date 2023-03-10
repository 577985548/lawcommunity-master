package com.law.community.interceptor;

import com.law.community.mapper.UserMapper;
import com.law.community.model.User;
import com.law.community.model.UserExample;
import com.law.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterception implements HandlerInterceptor {

  @Resource
  private UserMapper userMapper;

  @Autowired
  private NotificationService notificationService;

  @Value("${github.redirect.uri}")
  private String githubRedirectUri;

  @Value("${gitee.redirect.uri}")
  private String giteeRedirectUri;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    response.addCookie(new Cookie("token","test"));

    //设置 context 级别的属性
    //navigation模板使用
    request.getServletContext().setAttribute("githubRedirectUri", githubRedirectUri);
    request.getServletContext().setAttribute("giteeRedirectUri", giteeRedirectUri);
//    System.out.println("githubRedirectUri = " + githubRedirectUri);
//    System.out.println("giteeRedirectUri = " + giteeRedirectUri);

    Cookie[] cookies = request.getCookies();
    if(cookies!=null&&cookies.length!=0){
      for (Cookie cookie :cookies) {
        if(cookie.getName().equals("token")){
          String token = cookie.getValue();
          UserExample userExample = new UserExample();
          userExample.createCriteria().andTokenEqualTo(token);
          List<User> users = userMapper.selectByExample(userExample);
          if(users.size()!=0){
            // 写到session
            request.getSession().setAttribute("user",users.get(0));
            // 消息未读数
            Long unreadCount = notificationService.unreadCount(users.get(0).getId());
            request.getSession().setAttribute("unreadCount", unreadCount);
          }
          break;
        }
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

  }


}
