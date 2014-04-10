package com.hedatou.wedding.web;

import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hedatou.wedding.domain.Category;
import com.hedatou.wedding.domain.User;
import com.hedatou.wedding.service.UserService;
import com.hedatou.wedding.web.util.StdJson;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(@CookieValue(value = "l", required = false) String loginUserToken,
            @CookieValue(value = "a", required = false) String availUserTokens, String source,
            HttpServletResponse response) {
        // 记录来源
        userService.rememberSource(source, response);

        // 登录用户跳转到用户首页
        User user = userService.getUser(loginUserToken);
        if (user != null) {
            return "redirect:/user/";
        }

        // 可登录列表不为空，显示列表页
        Set<User> users = userService.getUsers(availUserTokens);
        if (!CollectionUtils.isEmpty(users)) {
            return "list";
        }

        // 首次访问，显示注册页
        return "redirect:/register/step1";
    }

    @RequestMapping("/register/step1")
    public String register1() {
        // 显示手机号注册页
        return "register1";
    }

    @RequestMapping("/sendVCode")
    @ResponseBody
    public StdJson sendVCode(String mobile) {
        userService.sendVCode(mobile);
        return StdJson.ok();
    }

    @RequestMapping("/saveMobile")
    @ResponseBody
    public StdJson saveMobile(String mobile, String vcode, @CookieValue(value = "s", required = false) String source,
            @CookieValue(value = "a", required = false) String availUserTokens, HttpServletResponse response) {
        userService.register(mobile, vcode, source, availUserTokens, response);
        return StdJson.ok("/register/step2");
    }

    @RequestMapping("/register/step2")
    public String register2() {
        // 显示称谓注册页
        return "register2";
    }

    @RequestMapping("/saveName")
    @ResponseBody
    public StdJson saveName(@CookieValue(value = "l", required = false) String token, String category, String name) {
        userService.saveName(token, Category.create(category), name);
        return StdJson.ok("/register/step3");
    }

    @RequestMapping("/register/step3")
    public String register3(String name) {
        // 显示祝词注册页
        return "register3";
    }

    @RequestMapping("/register/step4")
    public String register4(String bless) {
        // 保存用户祝词

        // 重定向到用户首页
        return "redirect:/user/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String key, String value, int age, HttpServletResponse response) {
        Cookie c = new Cookie(key, value);
        c.setMaxAge(age);
        response.addCookie(c);
        return "login";
    }

}
