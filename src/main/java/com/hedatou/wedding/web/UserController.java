package com.hedatou.wedding.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/chatroom")
    public void chatroom() {

    }

    @RequestMapping("/blessing")
    public void blessing() {

    }

    @RequestMapping("/explore")
    public void explore() {

    }

    @RequestMapping("/aboutUs")
    public void aboutUs() {

    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/user/chatroom";
    }

}
