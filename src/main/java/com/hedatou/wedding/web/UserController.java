package com.hedatou.wedding.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/chat")
    public void chat() {

    }

    @RequestMapping("/blessing")
    public void blessing() {

    }

    @RequestMapping("/logout")
    public void logout() {

    }

}
