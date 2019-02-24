package com.learn.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class PathEntity implements Serializable {
    private static final long serialVersionUID = 100L;

    private Long id;

    private String name;

    private String companyKey;

    private String appId;

    private String appName;

    private String callBackPath;

    private String callBackApp;

    private Timestamp createTime;

    private String appHost;

    private int weight;

    private String userIds;

    @Override
    public String toString() {
        return "PathEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyKey='" + companyKey + '\'' +
                ", appId='" + appId + '\'' +
                ", appName='" + appName + '\'' +
                ", callBackPath='" + callBackPath + '\'' +
                ", callBackApp='" + callBackApp + '\'' +
                ", createTime=" + createTime +
                ", appHost='" + appHost + '\'' +
                ", weight=" + weight +
                ", userIds='" + userIds + '\'' +
                '}';
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getAppHost() {
        return appHost;
    }

    public void setAppHost(String appHost) {
        this.appHost = appHost;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(String compamyKey) {
        this.companyKey = compamyKey;
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

    public String getCallBackPath() {
        return callBackPath;
    }

    public void setCallBackPath(String callBackPath) {
        this.callBackPath = callBackPath;
    }

    public String getCallBackApp() {
        return callBackApp;
    }

    public void setCallBackApp(String callBackApp) {
        this.callBackApp = callBackApp;
    }
}
