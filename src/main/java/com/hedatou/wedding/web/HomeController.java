package com.hedatou.wedding.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        // 首次访问，跳转到注册页
        // 登录状态，跳转到用户首页
        // 非登录状态，显示可登录列表
        return "index";
    }

    @RequestMapping("/register")
    public void register() {

    }

}
