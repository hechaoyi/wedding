package com.hedatou.wedding.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hedatou.wedding.common.JsonUtils;
import com.hedatou.wedding.dao.RedisDao;
import com.hedatou.wedding.domain.User;

@Service
public class AuthService {

    @Autowired
    private RedisDao redisDao;

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

}
