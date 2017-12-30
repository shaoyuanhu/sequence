package com.shaoyuanhu.domain;

import java.io.Serializable;

/**
 * @Description:
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
public class InitSequenceResult implements Serializable {

    //需要添加到缓存的数据数组
    private String[] members;
    //本次初始化起始值
    private String number;

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
