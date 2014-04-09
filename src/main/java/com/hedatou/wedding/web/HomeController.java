package com.hedatou.wedding.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        // 首次访问，跳转到注册页
        // 登录状态，跳转到用户首页
        // 非登录状态，显示可登录列表
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login() {

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String userName, HttpServletResponse response) {
        Cookie c = new Cookie("u", userName);
        c.setMaxAge(3600 * 24);
        c.setHttpOnly(true);
        response.addCookie(c);
        return "redirect:/user/chatroom";
    }

    @RequestMapping("/register")
    public void register() {

    }

}
