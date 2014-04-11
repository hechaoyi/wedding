package com.hedatou.wedding.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hedatou.wedding.service.LotteryService;
import com.hedatou.wedding.service.UserService;
import com.hedatou.wedding.web.util.StdJson;

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
    public StdJson roll(int count) {
        return StdJson.ok(lotteryService.roll(count));
    }

}
