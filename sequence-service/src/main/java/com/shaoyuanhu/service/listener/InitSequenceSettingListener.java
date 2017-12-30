package com.shaoyuanhu.service.listener;

import com.shaoyuanhu.common.util.CacheConstant;
import com.shaoyuanhu.dao.redis.JedisClient;
import com.shaoyuanhu.dao.redis.RedisObjectManager;
import com.shaoyuanhu.domain.Generator;
import com.shaoyuanhu.manager.GeneratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 初始化数据库配置
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
public class InitSequenceSettingListener implements ApplicationListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitSequenceSettingListener.class);

    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private GeneratorManager generatorManager;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("InitSequenceSettingListener执行了==========================");
        String cacheKey= CacheConstant.CACHE_PREFIX_SETTING;
        try {
            List<Generator> list = generatorManager.getGeneratorList();
            Map<String,String> map= new HashMap<String, String>();
            for (Generator generator : list) {
                map.put(generator.getOwnerkey(), generator.getOwnername());
            }
            jedisClient.hmset(cacheKey,map);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
