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

import javax.servlet.http.HttpServletResponse;

@Controller
public class RegisterController {

    @Autowired
    private UserplusMapper userplusMapper;

    @Autowired
    private UserplusService userplusService;

    @GetMapping("/register")
    public String login() {
        return "register";
    }

    @PostMapping("/register")
    public String doLogin(@RequestParam(value = "accountId", required = false) String accountId,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "password", required = false) String password,
                          @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
                          HttpServletResponse response,
                          Model model
    ) {
        model.addAttribute("accountId", accountId);
        model.addAttribute("name", name);
        model.addAttribute("password", password);
        model.addAttribute("confirmPassword", confirmPassword);
        if (accountId == null || accountId == "") {
            model.addAttribute("error", "用户名不能为空");
            return "register";
        }

        if (name == null || name == "") {
            model.addAttribute("error", "昵称不能为空");
            return "register";
        }

        if (password == null || password == "") {
            model.addAttribute("error", "密码不能为空");
            return "register";
        }

        if (confirmPassword == null || confirmPassword == "") {
            model.addAttribute("error", "确认密码不能为空");
            return "register";
        }

        User user = userplusMapper.selectOne(new QueryWrapper<User>().eq("account_id", accountId));

        if (user == null) {
            if (password.equals(confirmPassword)) {
                User userinfo = new User();
                userinfo.setAccountId(accountId);
                userinfo.setPassword(password);
                userinfo.setName(name);
                userinfo.setGmtCreate(System.currentTimeMillis());
                userinfo.setGmtModified(userinfo.getGmtCreate());
                userplusMapper.insert(userinfo);
                model.addAttribute("success", "注册成功！");
                return "register";
            } else {
                model.addAttribute("error", "两次密码不一致！");
                return "register";
            }

        }else {
            model.addAttribute("error", "该用户名已存在！");
            return "register";
        }
    }
}
