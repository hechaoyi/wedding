package com.hedatou.wedding.service;

import java.util.Collections;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.hedatou.wedding.common.JsonUtils;
import com.hedatou.wedding.dao.RedisDao;
import com.hedatou.wedding.domain.User;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
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

    public Set<User> getUsers(String tokens) {
        if (StringUtils.isEmpty(tokens))
            return Collections.emptySet();
        Set<User> users = Sets.newHashSet();
        for (String token : Splitter.on(",").trimResults().omitEmptyStrings().split(tokens)) {
            User user = this.getUser(token);
            if (user != null)
                users.add(user);
        }
        return users;
    }

    public void rememberSource(String source, HttpServletResponse response) {
        if (StringUtils.isEmpty(source))
            source = "default";
        Cookie cookie = new Cookie("s", source);
        cookie.setMaxAge(3600 * 24);
        response.addCookie(cookie);
    }

    public void sendVCode(String mobile) {
        // 检查是否已经注册过了
        if (redisDao.get(String.format("user:mobile:%s:json", mobile)) != null)
            throw new BusinessException("该手机号码已经注册过了，不能重复注册~");
        // 检查最近发送时间
        Double timestamp = redisDao.zscore("vcode:last-send-time", mobile);
        if (timestamp != null && System.currentTimeMillis() - timestamp.longValue() < 30000)
            throw new BusinessException("您点击的太频繁了~请稍后再试");
        // 生成验证码
        String vcode = String.format("%04d", new Random().nextInt(10000));
        redisDao.setex(String.format("vcode:mobile:%s", mobile), vcode, 60 * 5);
        // 发送短信
        String message = String.format("感谢您参与互动，您的验证码是%s，5分钟内有效。", vcode);
        smsService.send(mobile, message);
        // 记录发送时间
        redisDao.zadd("vcode:last-send-time", mobile, System.currentTimeMillis());
        logger.info("send to {}, vcode:{}", mobile, vcode);
    }

    public void register(String mobile, String clientVCode, String source, String availUserTokens,
            HttpServletResponse response) {
        // 检查是否已经注册过了
        if (redisDao.get(String.format("user:mobile:%s:json", mobile)) != null)
            throw new BusinessException("该手机号码已经注册过了，不能重复注册~");
        // 检查验证码
        String serverVCode = redisDao.get(String.format("vcode:mobile:%s", mobile));
        if (StringUtils.isEmpty(serverVCode) || !serverVCode.equals(clientVCode))
            throw new BusinessException("您输入的短信验证码不正确~");
        // 创建用户
        User user = new User();
        user.setMobile(mobile);
        user.setSource(StringUtils.isEmpty(source) ? "default" : source);
        redisDao.set(String.format("user:mobile:%s:json", mobile), JsonUtils.toJson(user));
        // 生成token
        String temp = String.format("token-%s-%d", mobile, System.currentTimeMillis());
        String token = DigestUtils.md5DigestAsHex(temp.getBytes());
        redisDao.set(String.format("user:token:%s:mobile", token), mobile);
        // 种Cookie
        Cookie loginCookie = new Cookie("l", token);
        loginCookie.setMaxAge(3600 * 24);
        response.addCookie(loginCookie);
        availUserTokens = StringUtils.isEmpty(availUserTokens) ? token : (availUserTokens + "," + token);
        Cookie availCookie = new Cookie("a", availUserTokens);
        availCookie.setMaxAge(3600 * 24);
        response.addCookie(availCookie);
        logger.info("new user {}, source:{}, token:{}", mobile, source, token);
    }

}
