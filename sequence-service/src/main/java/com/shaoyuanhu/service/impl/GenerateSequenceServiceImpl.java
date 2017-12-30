package com.shaoyuanhu.service.impl;

import com.shaoyuanhu.api.GenerateSequenceService;
import com.shaoyuanhu.common.enums.ResultMessageEnum;
import com.shaoyuanhu.common.util.CacheConstant;
import com.shaoyuanhu.common.util.RemoteResult;
import com.shaoyuanhu.common.util.StringUtil;
import com.shaoyuanhu.dao.redis.JedisClient;
import com.shaoyuanhu.domain.Generator;
import com.shaoyuanhu.domain.InitSequenceResult;
import com.shaoyuanhu.manager.GeneratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description:
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
@Service("generateSequenceService")
public class GenerateSequenceServiceImpl implements GenerateSequenceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateSequenceServiceImpl.class);

    //定义锁超时时间为15秒
    private static final long LOCKEXPIRES = 15 * 1000;
    @Autowired
    private GeneratorManager generatorManager;
    @Autowired
    private JedisClient jedisClient;

    /**
     * 获取序列号方法
     * @param ownerKey 序列号标识
     * @return
     */
    public RemoteResult<String> getSequenceKey(String ownerKey) {
        RemoteResult<String> remoteResult = new RemoteResult<String>();
        String sequence = null;
        String cacheKey = CacheConstant.CACHE_PREFIX_OWNERKEY + ownerKey;
        String cacheLockKey = CacheConstant.CACHE_PREFIX_LOCKKEY + ownerKey;
        String cacheSetting = CacheConstant.CACHE_PREFIX_SETTING;


        Map<String, String> mapSetting = jedisClient.hgetAll(cacheSetting);
        Generator generator = null;
        if (mapSetting != null && mapSetting.size() > 0) {
            String val = mapSetting.get(ownerKey);
            if (StringUtil.isEmpty(val)) {
                //初始化配置信息
                generator = generatorManager.getGeneratorByOwnerKey(ownerKey);
                if (generator == null) {
                    remoteResult.setResultCode(ResultMessageEnum.ERROR_OWNERKEY_NOTEXIST.getCode());
                    remoteResult.setResultMsg(ResultMessageEnum.ERROR_OWNERKEY_NOTEXIST.getDesc());
                    return remoteResult;
                } else {
                    jedisClient.hset(cacheSetting, generator.getOwnerkey(), generator.getOwnername());
                }
            }
        } else {
            List<Generator> list = generatorManager.getGeneratorList();
            Map<String, String> map =new HashMap<String, String>();
            for (Generator gen : list) {
                map.put(gen.getOwnerkey(), gen.getOwnername());
            }
            jedisClient.hmset(cacheSetting, map);
        }


        sequence = jedisClient.rpop(cacheKey);
        if (StringUtil.isEmpty(sequence)) {
            try {
                while (!tryLock(cacheLockKey)) {
                    Thread.sleep(1);
                    sequence = jedisClient.rpop(cacheKey);
                    if (!StringUtil.isEmpty(sequence)) {
                        remoteResult.setSuccess(true);
                        remoteResult.setT(sequence);
                        remoteResult.setResultCode(ResultMessageEnum.SUCCESS.getCode());
                        remoteResult.setResultMsg(ResultMessageEnum.SUCCESS.getDesc());
                        return remoteResult;
                    }
                }
            }catch (Exception e){
                LOGGER.error(e.getMessage(),e);
            }
            try {
                Long rows = generatorManager.updateGenerator(ownerKey);
                if (rows > 0) {
                    //初始化数据
                    generator = generatorManager.getGeneratorByOwnerKey(ownerKey);
                    Integer isOrder = generator.getIsorder();
                    InitSequenceResult initSequenceResult = null;
                    switch (isOrder) {
                        case 0://0:随机
                            initSequenceResult = initSequence(generator.getStartindex(), generator.getStep(), true);
                            break;
                        case 1://1：顺序
                            initSequenceResult = initSequence(generator.getStartindex(), generator.getStep(), false);
                            break;
                    }
                    rows = jedisClient.lpush(cacheKey, initSequenceResult.getMembers());
                    if (rows > 0) {
                        unlock(cacheLockKey); //解锁

                        remoteResult.setSuccess(true);
                        remoteResult.setT(initSequenceResult.getNumber());
                        remoteResult.setResultCode(ResultMessageEnum.SUCCESS.getCode());
                        remoteResult.setResultMsg(ResultMessageEnum.SUCCESS.getDesc());
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        } else {
            remoteResult.setSuccess(true);
            remoteResult.setT(sequence);
            remoteResult.setResultCode(ResultMessageEnum.SUCCESS.getCode());
            remoteResult.setResultMsg(ResultMessageEnum.SUCCESS.getDesc());
        }
        return remoteResult;
    }

    private static InitSequenceResult initSequence(Long startindex, Integer step, boolean isOrder) {
        List<String> list = new ArrayList<String>();
        InitSequenceResult initSequenceResult = new InitSequenceResult();
        initSequenceResult.setNumber(String.valueOf(startindex));
        for (long i = startindex+1; i < (startindex + step); i++) {
            list.add(String.valueOf(i));
        }
        if (isOrder) {
            Collections.shuffle(list);
        }
        initSequenceResult.setMembers(list.toArray(new String[list.size()]));
        return initSequenceResult;
    }

    private boolean tryLock(String lockKey) {
        long lockExpireTime = System.currentTimeMillis() + LOCKEXPIRES + 1;//锁超时时间
        String lockExpireTimeStr = String.valueOf(lockExpireTime);

        if (jedisClient.setNx(lockKey, lockExpireTimeStr) == 1) { // 获取到锁
            LOGGER.error(Thread.currentThread().getName()+":setNx1");
            return true;
        }

        String value = jedisClient.get(lockKey);
        if (value != null && isTimeExpired(value)) {
            //锁失效了
            String oldValue = jedisClient.getSet(lockKey, lockExpireTimeStr);
            if (oldValue != null && isTimeExpired(oldValue)) {
                //获取到锁
                LOGGER.error(Thread.currentThread().getName()+":setNx2");
                return true;
            }
        }
        return false;
    }

    private void unlock(String key) {
        String value = jedisClient.get(key);
        if (value != null && !isTimeExpired(value)) {
            LOGGER.error(Thread.currentThread().getName()+":unlock");
            jedisClient.del(key);
        }
    }

    private boolean isTimeExpired(String value) {
        return Long.parseLong(value) < System.currentTimeMillis();
    }

}
