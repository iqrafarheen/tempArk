package com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail;

/**
 * Created by Bilal on 8/13/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class employerExample {

    @SerializedName("info")
    @Expose
    private employerInfo info;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("success")
    @Expose
    private Integer success;

    public employerInfo getInfo() {
        return info;
    }

    public void setInfo(employerInfo info) {
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