package com.shaoyuanhu.web.controller;

import com.shaoyuanhu.api.GenerateSequenceService;
import com.shaoyuanhu.common.util.JacksonUtil;
import com.shaoyuanhu.common.util.RemoteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 生成序列
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
@Controller
@RequestMapping("/sequence")
public class GeneratorSequenceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorSequenceController.class);

    @Autowired
    private GenerateSequenceService generateSequenceService;

    @RequestMapping(value = "/getSequenceKey" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getSequenceKey(String ownerKey){
        RemoteResult<String> remoteResult = generateSequenceService.getSequenceKey(ownerKey);
        return JacksonUtil.toJson(remoteResult);
    }

}
