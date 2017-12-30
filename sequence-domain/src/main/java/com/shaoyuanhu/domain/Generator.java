package com.shaoyuanhu.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 序列号生成对象
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
public class Generator implements Serializable{

    //主键id
    private Long id;
    //所有者key
    private String ownerkey;
    //所有者name
    private String ownername;
    //开始索引
    private Long startindex;
    //步长
    private Integer step;
    //是否有序
    private Integer isorder;
    //修改时间
    private Date updatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerkey() {
        return ownerkey;
    }

    public void setOwnerkey(String ownerkey) {
        this.ownerkey = ownerkey;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public Long getStartindex() {
        return startindex;
    }

    public void setStartindex(Long startindex) {
        this.startindex = startindex;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getIsorder() {
        return isorder;
    }

    public void setIsorder(Integer isorder) {
        this.isorder = isorder;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
