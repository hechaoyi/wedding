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
        redisDao.zadd("chat:list", JsonUtils.toJson(chat), now.getTime());
        return chat;
    }

}
