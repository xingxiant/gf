package com.learn.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserPathEntity implements Serializable {
    private static final long serialVersionUID = 100L;

    private Long id;

    private String pathName;

    private String companyKey;

    private String appId;

    private String appName;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "UserPathEntity{" +
                "id=" + id +
                ", pathName='" + pathName + '\'' +
                ", companyKey='" + companyKey + '\'' +
                ", appId='" + appId + '\'' +
                ", appName='" + appName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
