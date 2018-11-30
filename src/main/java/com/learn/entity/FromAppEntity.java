package com.learn.entity;

import java.util.Date;

public class FromAppEntity {
    private Long id;
    private String companyKey;
    private String appId;
    private Date time = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(String companyKey) {
        this.companyKey = companyKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ToPathEntity{" +
                "id=" + id +
                ", companyKey='" + companyKey + '\'' +
                ", appId='" + appId + '\'' +
                ", time=" + time +
                '}';
    }
}
