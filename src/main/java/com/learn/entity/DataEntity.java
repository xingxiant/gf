package com.learn.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 文本信息
 *
 */
public class DataEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;

    //文本内容
    private String content;

    //上传时间
    private Date gmttime = new Date();

    private double fs;

    public double getFs() {
        return fs;
    }

    public void setFs(double fs) {
        this.fs = fs;
    }

    /**
     * 设置：
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：文本内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：文本内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置：上传时间
     */
    public void setGmttime(Date gmttime) {
        this.gmttime = gmttime;
    }

    /**
     * 获取：上传时间
     */
    public Date getGmttime() {
        return gmttime;
    }
}
