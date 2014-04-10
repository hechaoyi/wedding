package com.hedatou.wedding.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

@Repository
public class RedisDao {

    @Autowired
    private JedisPool pool;

    public String get(String key) {
        Jedis jedis = pool.getResource();
        try {
            String result = jedis.get(key);
            pool.returnResource(jedis);
            return result;
        } catch (JedisException e) {
            pool.returnBrokenResource(jedis);
            throw e;
        }
    }

    public void set(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            jedis.set(key, value);
            pool.returnResource(jedis);
        } catch (JedisException e) {
            pool.returnBrokenResource(jedis);
            throw e;
        }
    }

}
