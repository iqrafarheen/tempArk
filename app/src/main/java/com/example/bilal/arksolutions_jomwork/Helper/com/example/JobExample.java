
package com.example.bilal.arksolutions_jomwork.Helper.com.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobExample {

    @SerializedName("jobs")
    @Expose
    private List<AwardedJob> jobs = null;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<AwardedJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<AwardedJob> jobs) {
        this.jobs = jobs;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
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
