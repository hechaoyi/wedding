package com.hedatou.wedding.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hedatou.wedding.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "user/index";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse response) {
        userService.logout(response);
        return "redirect:/";
    }

}
