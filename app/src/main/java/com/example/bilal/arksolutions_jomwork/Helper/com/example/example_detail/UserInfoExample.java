package com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoExample {

    @SerializedName("info")
    @Expose
    private UserInfo info;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("success")
    @Expose
    private Integer success;

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
