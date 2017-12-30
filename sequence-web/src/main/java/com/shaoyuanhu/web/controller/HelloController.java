package com.shaoyuanhu.web.controller;

import com.shaoyuanhu.common.util.JacksonUtil;
import com.shaoyuanhu.common.util.RemoteResult;
import com.shaoyuanhu.common.enums.ResultMessageEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Description:
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value="/test",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String test(HttpServletResponse response,String name){
        System.out.println(name);
        RemoteResult<Date> remoteResult = new RemoteResult<Date>();
        remoteResult.setResultCode(ResultMessageEnum.SUCCESS.getCode());
        remoteResult.setResultMsg(ResultMessageEnum.SUCCESS.getDesc());
        remoteResult.setSuccess(true);
        remoteResult.setT(new Date());
        return JacksonUtil.toJson(remoteResult);
    }


}
