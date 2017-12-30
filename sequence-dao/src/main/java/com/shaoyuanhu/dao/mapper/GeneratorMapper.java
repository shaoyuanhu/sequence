package com.shaoyuanhu.dao.mapper;

import com.shaoyuanhu.domain.Generator;

import java.util.List;

/**
 * @Description:
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
public interface GeneratorMapper {

    Long insert(Generator record);

    Generator getGeneratorByOwnerKey(String ownerKey);

    Long updateGenerator(String ownerKey);

    List<Generator> getGeneratorList();

}
