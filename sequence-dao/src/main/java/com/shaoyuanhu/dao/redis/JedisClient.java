package com.shaoyuanhu.dao.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: Jedis客户端
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
public interface JedisClient {

    String set(String key, String value);

    String get(String key);

    Boolean exists(String key);

    Long expire(String key, int seconds);

    Long ttl(String key);

    Long incr(String key);

    Long hset(String key, String field, String value);

    String hget(String key, String field);

    Long hdel(String key, String... field);

    Long del(String key);

    String getSet(String key, String value);

    Set<String> smembers(String key);

    Long setNx(String key, String value);

    Long lpush(String key, String... members);

    List<String> lrange(String key);

    String rpop(String key);

    String hmset(String key, Map<String, String> map);

    Map<String,String> hgetAll(String key);

}

