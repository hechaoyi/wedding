package com.hedatou.wedding.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hedatou.wedding.domain.User;
import com.hedatou.wedding.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(@CookieValue(value = "l", required = false) String token, Model model) {
        User user = userService.getUser(token);
        if (user == null)
            return "redirect:/";
        model.addAttribute("mobile", user.getMobile());
        model.addAttribute("bless", user.getBless());
        return "user";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse response) {
        userService.logout(response);
        return "redirect:/";
    }

    @RequestMapping("/menu")
    public String menu() {
        return "menu";
    }

    @RequestMapping("/upgrade")
    public String upgrade(@CookieValue(value = "l", required = false) String token, String name, String passcode) {
        if (!"X".equals(passcode))
            return "redirect:/user/";
        userService.toAdmin(token, name);
        return "redirect:/admin/";
    }

}
