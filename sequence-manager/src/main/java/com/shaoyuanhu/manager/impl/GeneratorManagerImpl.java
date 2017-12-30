package com.shaoyuanhu.manager.impl;

import com.shaoyuanhu.dao.mapper.GeneratorMapper;
import com.shaoyuanhu.domain.Generator;
import com.shaoyuanhu.manager.GeneratorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
@Service("generatorManager")
@Transactional
public class GeneratorManagerImpl implements GeneratorManager {

    @Autowired
    private GeneratorMapper generatorMapper;

    public Long insert(Generator record) {
        return generatorMapper.insert(record);
    }

    public Generator getGeneratorByOwnerKey(String ownerKey) {
        return generatorMapper.getGeneratorByOwnerKey(ownerKey);
    }

    public Long updateGenerator(String ownerKey) {
        return generatorMapper.updateGenerator(ownerKey);
    }

    public List<Generator> getGeneratorList() {
        return generatorMapper.getGeneratorList();
    }
}
