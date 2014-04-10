package com.hedatou.wedding.web;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hedatou.wedding.domain.User;
import com.hedatou.wedding.service.AuthService;

@Controller
public class HomeController {

    @Autowired
    private AuthService authService;

    @RequestMapping("/")
    public String index(@CookieValue(value = "l", required = false) String loginUserToken,
            @CookieValue(value = "a", required = false) String availUserTokens) {
        // 登录用户跳转到用户首页
        User user = authService.getUser(loginUserToken);
        if (user != null) {
            return "redirect:/user/";
        }

        // 可登录列表不为空，显示列表页
        List<User> users = authService.getUsers(availUserTokens);
        if (!CollectionUtils.isEmpty(users)) {
            return "list";
        }

        // 首次访问，显示注册页
        return "register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login() {

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String key, String value, int age, HttpServletResponse response) {
        Cookie c = new Cookie(key, value);
        c.setMaxAge(age);
        response.addCookie(c);
        return "login";
    }

}
