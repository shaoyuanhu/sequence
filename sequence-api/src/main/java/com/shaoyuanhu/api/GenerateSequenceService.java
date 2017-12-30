package com.shaoyuanhu.api;

import com.shaoyuanhu.common.util.RemoteResult;

/**
 * @Description: 序列号服务
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
public interface GenerateSequenceService {

    RemoteResult<String> getSequenceKey(String ownerKey);

}
