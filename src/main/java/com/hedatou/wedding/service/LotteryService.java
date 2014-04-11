package com.hedatou.wedding.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hedatou.wedding.dao.RedisDao;
import com.hedatou.wedding.domain.User;

@Service
public class LotteryService {

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private UserService userService;
    private Random random = new Random();

    // TODO blacklist

    public List<User> roll(int count) {
        Long total = redisDao.zcard("user:list");
        if (total == null)
            return Collections.emptyList();
        List<User> users;
        if (total <= count) {
            users = all();
        } else if (total <= count * 2) {
            users = allAndSelect(count);
        } else {
            users = oneByOne(count, total.intValue());
        }
        if (users.size() > 0)
            while (users.size() < count)
                users.add(users.get(random.nextInt(users.size())));
        Collections.shuffle(users);
        return users;
    }

    private List<User> all() {
        List<User> users = Lists.newArrayList();
        for (String mobile : redisDao.zrange("user:list", 0, -1))
            users.add(userService.getUserByMobile(mobile));
        return users;
    }

    private List<User> allAndSelect(int count) {
        List<String> list = Lists.newArrayList();
        Map<String, User> pool = Maps.newHashMap();
        for (String mobile : redisDao.zrange("user:list", 0, -1)) {
            User user = userService.getUserByMobile(mobile);
            for (int i = 0; i < user.getWeight(); i++)
                list.add(user.getMobile());
            pool.put(mobile, user);
        }
        List<User> users = Lists.newArrayList();
        for (int i = 0; i < count && list.size() > 0; i++) {
            String mobile = list.get(random.nextInt(list.size()));
            int idx;
            while ((idx = list.indexOf(mobile)) != -1)
                list.remove(idx);
            users.add(pool.get(mobile));
        }
        return users;
    }

    private List<User> oneByOne(int count, int total) {
        LoadingCache<String, User> pool = CacheBuilder.newBuilder().build(new CacheLoader<String, User>() {
            @Override
            public User load(String key) throws Exception {
                return userService.getUserByMobile(key);
            }
        });
        List<User> users = Lists.newArrayList();
        Set<String> mobiles = Sets.newHashSet();
        for (int i = 0; i < count; i++) {
            List<String> list = Lists.newArrayList();
            int need = 3;
            while (need > 0) {
                int idx = random.nextInt(total);
                String mobile = redisDao.zrange("user:list", idx, idx).iterator().next();
                if (list.contains(mobile) || mobiles.contains(mobile))
                    continue;
                need--;
                User user = pool.getUnchecked(mobile);
                for (int j = 0; j < user.getWeight(); j++)
                    list.add(user.getMobile());
            }
            String mobile = list.get(random.nextInt(list.size()));
            users.add(pool.getUnchecked(mobile));
            mobiles.add(mobile);
        }
        return users;
    }

}
