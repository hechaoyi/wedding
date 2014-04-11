package com.hedatou.wedding.dao;

import java.util.Set;

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
            String value = jedis.get(key);
            pool.returnResource(jedis);
            return value;
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

    public void setex(String key, String value, int seconds) {
        Jedis jedis = pool.getResource();
        try {
            jedis.setex(key, seconds, value);
            pool.returnResource(jedis);
        } catch (JedisException e) {
            pool.returnBrokenResource(jedis);
            throw e;
        }
    }

    public Long incr(String key) {
        Jedis jedis = pool.getResource();
        try {
            Long num = jedis.incr(key);
            pool.returnResource(jedis);
            return num;
        } catch (JedisException e) {
            pool.returnBrokenResource(jedis);
            throw e;
        }
    }

    public Long zcard(String key) {
        Jedis jedis = pool.getResource();
        try {
            Long count = jedis.zcard(key);
            pool.returnResource(jedis);
            return count;
        } catch (JedisException e) {
            pool.returnBrokenResource(jedis);
            throw e;
        }
    }

    public Set<String> zrange(String key, long start, long end) {
        Jedis jedis = pool.getResource();
        try {
            Set<String> members = jedis.zrange(key, start, end);
            pool.returnResource(jedis);
            return members;
        } catch (JedisException e) {
            pool.returnBrokenResource(jedis);
            throw e;
        }
    }

    public Double zscore(String key, String member) {
        Jedis jedis = pool.getResource();
        try {
            Double score = jedis.zscore(key, member);
            pool.returnResource(jedis);
            return score;
        } catch (JedisException e) {
            pool.returnBrokenResource(jedis);
            throw e;
        }
    }

    public void zadd(String key, String member, double score) {
        Jedis jedis = pool.getResource();
        try {
            jedis.zadd(key, score, member);
            pool.returnResource(jedis);
        } catch (JedisException e) {
            pool.returnBrokenResource(jedis);
            throw e;
        }
    }

}
