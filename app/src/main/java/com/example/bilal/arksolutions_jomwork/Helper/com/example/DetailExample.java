
package com.example.bilal.arksolutions_jomwork.Helper.com.example;

import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailExample {

    @SerializedName("job")
    @Expose
    private com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.Job job;
    @SerializedName("applyjobs")
    @Expose
    private List<Applyjob> applyjobs = null;
    @SerializedName("awardedjobs")
    @Expose
    private List<detailAwardedJob> awardedjobs = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("success")
    @Expose
    private Integer success;

    public com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.Job getJob() {
        return job;
    }

    public void setJob(com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.Job job) {
        this.job = job;
    }

    public List<Applyjob> getApplyjobs() {
        return applyjobs;
    }

    public void setApplyjobs(List<Applyjob> applyjobs) {
        this.applyjobs = applyjobs;
    }

    public List<detailAwardedJob> getAwardedjobs() {
        return awardedjobs;
    }

    public void setAwardedjobs(List<detailAwardedJob> awardedjobs) {
        this.awardedjobs = awardedjobs;
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


