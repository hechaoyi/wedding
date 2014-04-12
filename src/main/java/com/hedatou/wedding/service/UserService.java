package com.hedatou.wedding.service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hedatou.wedding.common.JsonUtils;
import com.hedatou.wedding.dao.RedisDao;
import com.hedatou.wedding.domain.Category;
import com.hedatou.wedding.domain.User;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SmsService smsService;
    @Autowired
    private NotifyService notifyService;

    public User getUser(String token) {
        if (StringUtils.isEmpty(token))
            return null;
        String mobile = redisDao.get(String.format("user:token:%s:mobile", token));
        return getUserByMobile(mobile);
    }

    public User getUserByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile))
            return null;
        String userJson = redisDao.get(String.format("user:mobile:%s:json", mobile));
        User user = JsonUtils.fromJson(userJson, User.class);
        String adminName = redisDao.get(String.format("user:mobile:%s:admin", mobile));
        if (!StringUtils.isEmpty(adminName)) {
            user.setDisplayName(adminName);
            user.setAdmin(true);
            if (user.getWeight() > 0) {
                this.updateWeight(user, 0, "管理员不能抽奖", false);
                logger.info("admin {} weight degraded", mobile);
            }
        }
        return user;
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

    public List<User> findUsersBySource(String source) {
        List<User> users = Lists.newArrayList();
        for (String mobile : redisDao.zrange("user:list", 0, -1)) {
            User user = this.getUserByMobile(mobile);
            if (user.getSource().equals(source))
                users.add(user);
        }
        return users;
    }

    public void rememberSource(String token, String source, HttpServletResponse response) {
        if (StringUtils.isEmpty(source))
            source = "default";
        Cookie cookie = new Cookie("s", source);
        cookie.setMaxAge(3600 * 24);
        response.addCookie(cookie);
        // 记录来源
        if (!source.equals("default")) {
            User user = this.getUser(token);
            if (user != null) {
                user.setSource(source);
                redisDao.set(String.format("user:mobile:%s:json", user.getMobile()), JsonUtils.toJson(user));
                logger.info("user {} update source:{}", user.getMobile(), source);
            }
        }
        // 通知
        notifyService.access(source);
    }

    public void sendVCode(String mobile) {
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

    public boolean register(String mobile, String clientVCode, String source, String availUserTokens,
            HttpServletResponse response) {
        // 检查验证码
        String serverVCode = redisDao.get(String.format("vcode:mobile:%s", mobile));
        if (StringUtils.isEmpty(serverVCode) || !serverVCode.equals(clientVCode))
            throw new BusinessException("您输入的短信验证码不正确~");
        // 检查是否已经注册过了
        boolean newUser = false;
        if (redisDao.get(String.format("user:mobile:%s:json", mobile)) == null) {
            newUser = true;
            // 创建用户
            User user = new User();
            user.setMobile(mobile);
            user.setSource(StringUtils.isEmpty(source) ? "default" : source);
            user.setCategory(Category.OTHER);
            user.setName("现场来宾");
            user.setDisplayName("现场来宾");
            user.setBless("祝百年好合！");
            redisDao.set(String.format("user:mobile:%s:json", mobile), JsonUtils.toJson(user));
            redisDao.zadd("user:list", mobile, System.currentTimeMillis());
            // 通知
            notifyService.mobile(mobile);
        }
        String token = this.login(mobile, availUserTokens, response);
        logger.info("{} user {}, source:{}, token:{}", newUser ? "new" : "old", mobile, source, token);
        return newUser;
    }

    public String login(String mobile, String availUserTokens, HttpServletResponse response) {
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
        return token;
    }

    public void saveName(String token, Category category, String name) {
        User user = this.getUser(token);
        if (user == null || category == null)
            throw new BusinessException("注册信息不完整");
        user.setCategory(category);
        user.setName(name);
        boolean family = Pattern.compile("父|母|爷|奶|姥|舅|姑|姨|婶|爸|妈|伯|叔|嫂|哥|姐|弟|妹|媳|婿|侄|甥").matcher(name).find();
        String displayName;
        switch (category) {
        case MALE_FAMILY:
            displayName = family ? ("新郎的" + name) : ("新郎家人" + name);
            break;
        case FEMALE_FAMILY:
            displayName = family ? ("新娘的" + name) : ("新娘家人" + name);
            break;
        case NEU_CLASSMATE:
            displayName = "东大同学" + name;
            break;
        case MALE_CLASSMATE:
            displayName = "新郎同学" + name;
            break;
        case FEMALT_WORKMATE:
            displayName = "新娘同事" + name;
            break;
        case MALE_FATHER_FRIEND:
            displayName = "新郎父亲好友" + name;
            break;
        case MALE_MOTHER_FRIEND:
            displayName = "新郎母亲好友" + name;
            break;
        default:
            displayName = "现场来宾" + name;
            break;
        }
        boolean newName = user.getDisplayName().equals("现场来宾");
        user.setDisplayName(displayName);
        redisDao.set(String.format("user:mobile:%s:json", user.getMobile()), JsonUtils.toJson(user));
        if (newName) {
            // 发送短信
            String message = String.format("您已经注册成功，后续的祝词和发言，将会以[%s]作为昵称显示在大屏幕上，祝您今天玩得开心。", displayName);
            smsService.send(user.getMobile(), message);
            // 通知
            notifyService.register(displayName);
        }
        logger.info("user {} save name:{}", user.getMobile(), displayName);
    }

    public void saveBless(String token, String bless) {
        User user = this.getUser(token);
        if (user == null)
            throw new BusinessException("注册信息不完整");
        if (StringUtils.isEmpty(bless))
            bless = "祝百年好合！";
        user.setBless(bless);
        redisDao.set(String.format("user:mobile:%s:json", user.getMobile()), JsonUtils.toJson(user));
        // 通知
        notifyService.bless(user.getDisplayName(), bless);
        logger.info("user {} save bless:{}", user.getMobile(), bless);
        if (bless.length() > 30)
            this.updateWeight(user, user.getWeight() + 1, "祝词很用心", true);
    }

    public void updateWeight(User user, int weight, String reason, boolean notify) {
        if (weight < 0 || weight > 7)
            return;
        if (user.isAdmin() && weight > 0)
            return;
        user.setWeight(weight);
        redisDao.set(String.format("user:mobile:%s:json", user.getMobile()), JsonUtils.toJson(user));
        logger.info("user {} update weight to {}, cause:{}", user.getMobile(), weight, reason);
        if (notify) {
            // 通知
            notifyService.upgrade(user.getDisplayName(), weight, reason);
            // 发送短信
            String message = String.format("由于【%s】，您的幸运值已经提升到%d，抽奖环节会有更高的几率中奖，祝您今天玩得开心。", reason, weight);
            smsService.send(user.getMobile(), message);
        }
    }

    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("l", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void toAdmin(String token, String name) {
        User user = this.getUser(token);
        if (user == null)
            throw new BusinessException("未登录");
        redisDao.set(String.format("user:mobile:%s:admin", user.getMobile()), name);
    }

}
