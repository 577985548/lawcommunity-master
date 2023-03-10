package com.law.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class indexoneController {
    @GetMapping("/index1")
    public String index1(){
        return "index1";
    }
}
