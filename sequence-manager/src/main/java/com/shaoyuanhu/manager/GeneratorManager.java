package com.shaoyuanhu.manager;

import com.shaoyuanhu.domain.Generator;

import java.util.List;

/**
 * @Description:
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
public interface GeneratorManager {

    Long insert(Generator record);

    Generator getGeneratorByOwnerKey(String ownerKey);

    Long updateGenerator(String ownerKey);

    List<Generator> getGeneratorList();

}
