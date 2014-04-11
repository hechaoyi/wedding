package com.hedatou.wedding.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hedatou.wedding.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "redirect:/admin/dashboard";
    }

    @RequestMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

}
