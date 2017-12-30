package com.shaoyuanhu.dao.redis.impl;

import com.shaoyuanhu.dao.redis.RedisObjectManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: redis操作类
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
//@Component("redisObjectManager")
public class RedisObjectManagerImpl implements RedisObjectManager {

    @Autowired
    private ShardedJedisSentinelPool shardedJedisPool;

    @Override
    public String hmset(final String key,final Map<String,String> map) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                return jedis.hmset(key,map);
            }
        }.getResult();
    }

    @Override
    public String get(final String key) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                return jedis.get(key);
            }
        }.getResult();
    }

    @Override
    public Long hset(final String key,final String field,final String value) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.hset(key, field, value);
            }
        }.getResult();
    }

    @Override
    public Map<String,String> hgetAll(final String key) {
        return new Executor<Map<String,String>>(shardedJedisPool) {

            @Override
            Map<String,String> execute() {
                return jedis.hgetAll(key);
            }
        }.getResult();
    }

    @Override
    public Long lpush(final String key,final String... members) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.lpush(key, members);
            }
        }.getResult();
    }

    @Override
    public String rpop(final String key) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                return jedis.rpop(key);
            }
        }.getResult();
    }

    @Override
    public Long setNx(final String key,final String value) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.setnx(key, value);
            }
        }.getResult();
    }
    @Override
    public Long expire(final String key,final int second) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.expire(key, second);
            }
        }.getResult();
    }


    @Override
    public Set<String> smembers(final String key) {
        return new Executor<Set<String>>(shardedJedisPool) {

            @Override
            Set<String> execute() {
                return jedis.smembers(key);
            }
        }.getResult();
    }

    @Override
    public List<String> lrange(final String key) {
        return new Executor<List<String>>(shardedJedisPool) {

            @Override
            List<String> execute() {
                return jedis.lrange(key, 0, -1);
            }
        }.getResult();
    }

    @Override
    public Long del(final String key) {
        return new Executor<Long>(shardedJedisPool) {

            @Override
            Long execute() {
                return jedis.del(key);
            }
        }.getResult();
    }

    @Override
    public String getSet(final String key,final String value) {
        return new Executor<String>(shardedJedisPool) {

            @Override
            String execute() {
                return jedis.getSet(key, value);
            }
        }.getResult();
    }

    abstract class Executor<T> {

        ShardedJedis jedis;
        ShardedJedisSentinelPool shardedJedisPool;

        public Executor(ShardedJedisSentinelPool shardedJedisPool) {
            this.shardedJedisPool = shardedJedisPool;
            jedis = this.shardedJedisPool.getResource();
        }

        abstract T execute();

        public T getResult() {
            T result = null;
            try {
                result = execute();
            } catch (Throwable e) {
                throw new RuntimeException("Redis execute exception", e);
            } finally {
                if (jedis != null) {
                    shardedJedisPool.returnResource(jedis);
                }
            }
            return result;
        }
    }
}
