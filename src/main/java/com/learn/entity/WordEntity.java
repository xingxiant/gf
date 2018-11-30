package com.learn.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 情感词语
 *
 */
public class WordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;

    //词语
    private String value;

    //情感类别
    private String type;
    private String type1;

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
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
     * 设置：词语
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取：词语
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置：情感类别
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取：情感类别
     */
    public String getType() {
        return type;
    }
}
