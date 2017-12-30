package com.shaoyuanhu.dao.redis.impl;

import com.shaoyuanhu.dao.redis.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: jedis单机版实现
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
//@Component("jedisClientPool")
public class JedisClientPool implements JedisClient {

    //@Autowired
    private JedisPool jedisPool;

    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }

    public String getSet(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.getSet(key, value);
        jedis.close();
        return result;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.set(key, value);
        jedis.close();
        return result;
    }

    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    @Override
    public Boolean exists(String key) {
        Jedis jedis = jedisPool.getResource();
        Boolean result = jedis.exists(key);
        jedis.close();
        return result;
    }

    public Set<String> smembers(String key) {
        Jedis jedis = jedisPool.getResource();
        Set<String> result = jedis.smembers(key);
        jedis.close();
        return result;
    }

    public Long setNx(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.setnx(key, value);
        jedis.close();
        return result;
    }

    public Long expire(String key, int second) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key, second);
        jedis.close();
        return result;
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.incr(key);
        jedis.close();
        return result;
    }

    public Long lpush(String key, String... members) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.lpush(key, members);
        jedis.close();
        return result;
    }

    public List<String> lrange(String key) {
        Jedis jedis = jedisPool.getResource();
        List<String> result = jedis.lrange(key, 0, -1);
        jedis.close();
        return result;
    }

    public String rpop(String key) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.rpop(key);
        jedis.close();
        return result;
    }

    public String hmset(String key, Map<String, String> map) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.hmset(key, map);
        jedis.close();
        return result;
    }

    public Map<String, String> hgetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> result = jedis.hgetAll(key);
        jedis.close();
        return result;
    }

    public Long hset(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(key,field,value);
        jedis.close();
        return result;
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.hget(key, field);
        jedis.close();
        return result;
    }

    @Override
    public Long hdel(String key, String... field) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hdel(key, field);
        jedis.close();
        return result;
    }
}
