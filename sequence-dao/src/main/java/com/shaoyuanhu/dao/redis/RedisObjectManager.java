package com.shaoyuanhu.dao.redis;


import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: redis操作类
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
public interface RedisObjectManager {

     Long del(String key);

     String getSet(String key, String value);

     String get(String key);

     Set<String> smembers(String key);

     Long setNx(String key, String value);

     Long expire(String key, int second);

     Long lpush(String key, String... members);

     List<String> lrange(String key);

     String rpop(String key);

     String hmset(String key, Map<String, String> map);

     Map<String,String> hgetAll(String key);

     Long hset(String key, String field, String value);

}
