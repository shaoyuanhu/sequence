package com.shaoyuanhu.common.enums;

/**
 * @Description: 接口返回值信息统一定义
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
public enum ResultMessageEnum {

    SUCCESS("00000","成功"),
    ERROR_SYSTEM_BUSY("99999","系统繁忙"),
    ERROR_PARAMS("10000","参数错误"),
    ERROR_OWNERKEY_NOTEXIST("10001","未配置分配规则的所有者");

    private String code;
    private String desc;

    ResultMessageEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
