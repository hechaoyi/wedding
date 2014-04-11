package com.hedatou.wedding.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hedatou.wedding.domain.User;
import com.hedatou.wedding.service.LotteryService;
import com.hedatou.wedding.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private LotteryService lotteryService;

    @RequestMapping("/")
    public String index() {
        return "redirect:/admin/dashboard";
    }

    @RequestMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @RequestMapping("/lottery")
    public String lottery() {
        return "lottery";
    }

    @RequestMapping("/roll")
    @ResponseBody
    public List<User> roll(int count) {
        return lotteryService.roll(count);
    }

}
