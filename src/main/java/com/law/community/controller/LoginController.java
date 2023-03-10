package com.law.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.law.community.mapper.UserplusMapper;
import com.law.community.model.User;
import com.law.community.service.UserplusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserplusMapper userplusMapper;

    @Autowired
    private UserplusService userplusService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam(value = "accountId", required = false) String accountId,
                          @RequestParam(value = "password", required = false) String password,
                          HttpServletResponse response,
                          HttpServletRequest request,
                          Model model
                          ){
        model.addAttribute("accountId", accountId);
        model.addAttribute("password", password);
        System.out.println("login");
        if (accountId == null || accountId == "") {
            model.addAttribute("error", "用户名不能为空");
            return "login";
        }

        if (password == null || password == "") {
            model.addAttribute("error", "密码不能为空");
            return "login";
        }


        User user = userplusMapper.selectOne(new QueryWrapper<User>().eq("account_id", accountId));

        if (user == null) {
            model.addAttribute("error", "用户不存在");
            return "login";
        }

        String dbPassword = user.getPassword();

        if(dbPassword.equals(password)){
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            userplusService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        }else {
            model.addAttribute("error", "用戶名或密码错误请重试");
            return "login";
        }
    }
}
