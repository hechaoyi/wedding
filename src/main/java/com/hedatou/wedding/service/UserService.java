package com.hedatou.wedding.service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hedatou.wedding.common.JsonUtils;
import com.hedatou.wedding.dao.RedisDao;
import com.hedatou.wedding.domain.User;

@Service
public class UserService {

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SmsService smsService;

    public User getUser(String token) {
        if (StringUtils.isEmpty(token))
            return null;
        String mobile = redisDao.get(String.format("user:token:%s:mobile", token));
        if (StringUtils.isEmpty(mobile))
            return null;
        String userJson = redisDao.get(String.format("user:mobile:%s:json", mobile));
        return JsonUtils.fromJson(userJson, User.class);
    }

    public List<User> getUsers(String tokens) {
        if (StringUtils.isEmpty(tokens))
            return Collections.emptyList();
        List<User> users = Lists.newArrayList();
        for (String token : Splitter.on(",").trimResults().omitEmptyStrings().split(tokens)) {
            User user = this.getUser(token);
            if (user != null)
                users.add(user);
        }
        return users;
    }

    public void rememberSource(String source, HttpServletResponse response) {
        if (StringUtils.isEmpty(source))
            source = "";
        Cookie cookie = new Cookie("s", source);
        cookie.setMaxAge(3600 * 24);
        response.addCookie(cookie);
    }

    public void sendVCode(String mobile) {
        String vcode = String.format("%04d", new Random().nextInt(10000));
        redisDao.set(String.format("vcode:mobile:%s", mobile), vcode);
        String message = String.format("感谢您参与互动，您的验证码是%s。", vcode);
        smsService.send(mobile, message);
    }

}
