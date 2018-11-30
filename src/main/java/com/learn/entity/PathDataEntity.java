package com.learn.entity;

import java.io.Serializable;
import java.util.Date;

public class PathDataEntity implements Serializable {
    private static final long serialVersionUID = 2L;

    private Long id;

    //渠道
    private String path;
    //渠道Key
    String companyKey;
    //app名称
    private String app;
    //appId
    private String appId;
    //渠道访问量
    private Long fromPathCount;
    //app回调量
    private Long fromAppCount;
    //app去重回调量
    private Long fromAppTrueCount;
    //回调渠道量
    private Long toPathCount;
    //回调去重渠道量
    private Long toPathTrueCount;

    //数据日期
    private Date daytime;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public Long getFromPathCount() {
        return fromPathCount;
    }

    public void setFromPathCount(Long fromPathCount) {
        this.fromPathCount = fromPathCount;
    }

    public Long getFromAppCount() {
        return fromAppCount;
    }

    public void setFromAppCount(Long fromAppCount) {
        this.fromAppCount = fromAppCount;
    }

    public Long getFromAppTrueCount() {
        return fromAppTrueCount;
    }

    public void setFromAppTrueCount(Long fromAppTrueCount) {
        this.fromAppTrueCount = fromAppTrueCount;
    }

    public Long getToPathCount() {
        return toPathCount;
    }

    public void setToPathCount(Long toPAthCount) {
        this.toPathCount = toPAthCount;
    }

    public Date getDaytime() {
        return daytime;
    }

    public void setDaytime(Date daytime) {
        this.daytime = daytime;
    }

    public Long getToPathTrueCount() {
        return toPathTrueCount;
    }

    public void setToPathTrueCount(Long toPathTrueCount) {
        this.toPathTrueCount = toPathTrueCount;
    }

    @Override
    public String toString() {
        return "PathDataEntity{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", companyKey='" + companyKey + '\'' +
                ", app='" + app + '\'' +
                ", appId='" + appId + '\'' +
                ", fromPathCount=" + fromPathCount +
                ", fromAppCount=" + fromAppCount +
                ", fromAppTrueCount=" + fromAppTrueCount +
                ", toPathCount=" + toPathCount +
                ", toPathTrueCount=" + toPathTrueCount +
                ", daytime=" + daytime +
                '}';
    }
}
