package com.hedatou.wedding.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hedatou.wedding.common.JsonUtils;
import com.hedatou.wedding.dao.RedisDao;
import com.hedatou.wedding.domain.Chat;
import com.hedatou.wedding.domain.User;

@Service
public class ChatService {

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private UserService userService;

    public Chat chat(String token, String message) {
        User user = userService.getUser(token);
        if (user == null)
            throw new BusinessException("未登录，请刷新页面~");
        Date now = new Date();
        Chat chat = new Chat();
        chat.setName(user.getDisplayName());
        chat.setMobi(user.getMobile());
        chat.setMsg(message);
        chat.setTime(now);
        chat.setAdmin(user.isAdmin());
        redisDao.zadd("chat:list", JsonUtils.toJson(chat), now.getTime());
        // 聊天计数，发言活跃加幸运值
        Long count = redisDao.incr(String.format("chat:mobile:%:count", user.getMobile()));
        if (count != null && (count == 1 || count == 10 || count == 30 || count == 60 || count == 100))
            userService.updateWeight(user, user.getWeight() + 1, String.format("发言%d次", count), true);
        return chat;
    }

}
