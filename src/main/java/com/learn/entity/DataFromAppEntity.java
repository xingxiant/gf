package com.learn.entity;

import java.io.Serializable;

public class DataFromAppEntity implements Serializable {
    private static final long serialVersionUID = 2L;

    private Long id;

    //appId
    private String appId;
    //渠道Key
    private String companyKey;
    //app名称
    private String appName;
    //渠道名称
    private String pathName;
    //数据日期
    private Long time;
    //idfa
    private String idfa;
    //是否上报成功 0失败 1成功
    private Integer isReportSuccess;
    //上报返回值
    private String reportResult;

    public String getReportResult() {
        return reportResult;
    }

    public void setReportResult(String reportResult) {
        this.reportResult = reportResult;
    }

    public Integer getIsReportSuccess() {
        return isReportSuccess;
    }

    public void setIsReportSuccess(Integer isReportSuccess) {
        this.isReportSuccess = isReportSuccess;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(String companyKey) {
        this.companyKey = companyKey;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    @Override
    public String toString() {
        return "DataFromAppEntity{" +
                "id=" + id +
                ", appId='" + appId + '\'' +
                ", companyKey='" + companyKey + '\'' +
                ", appName='" + appName + '\'' +
                ", pathName='" + pathName + '\'' +
                ", time=" + time +
                ", idfa='" + idfa + '\'' +
                ", isReportSuccess=" + isReportSuccess +
                ", reportResult='" + reportResult + '\'' +
                '}';
    }
}
