
package com.example.bilal.arksolutions_jomwork.Helper.com.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AwardedJob implements Serializable {

    @SerializedName("job_id")
    @Expose
    private String jobId;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("industry")
    @Expose
    private String industry;
    @SerializedName("no_of_candidates")
    @Expose
    private String noOfCandidates;
    @SerializedName("date_work")
    @Expose
    private String dateWork;
    @SerializedName("time_work")
    @Expose
    private String timeWork;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("rate_per_hour")
    @Expose
    private String ratePerHour;
    @SerializedName("job_location")
    @Expose
    private String jobLocation;
    @SerializedName("job_posting_date")
    @Expose
    private String jobPostingDate;
    @SerializedName("job_listing_exp_date")
    @Expose
    private String jobListingExpDate;
    @SerializedName("physical_disable_allow")
    @Expose
    private String physicalDisableAllow;
    @SerializedName("non_physical_disable_allow")
    @Expose
    private String nonPhysicalDisableAllow;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("job_status")
    @Expose
    private String jobStatus;
    @SerializedName("job_approve_by")
    @Expose
    private Object jobApproveBy;
    @SerializedName("job_approve_date")
    @Expose
    private Object jobApproveDate;
    @SerializedName("job_type")
    @Expose
    private String jobType;
    @SerializedName("posted_date")
    @Expose
    private String postedDate;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("contact_name")
    @Expose
    private Object contactName;
    @SerializedName("company_logo")
    @Expose
    private Object companyLogo;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("industry_interest")
    @Expose
    private String industryInterest;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("apply_date")
    @Expose
    private String applyDate;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getNoOfCandidates() {
        return noOfCandidates;
    }

    public void setNoOfCandidates(String noOfCandidates) {
        this.noOfCandidates = noOfCandidates;
    }

    public String getDateWork() {
        return dateWork;
    }

    public void setDateWork(String dateWork) {
        this.dateWork = dateWork;
    }

    public String getTimeWork() {
        return timeWork;
    }

    public void setTimeWork(String timeWork) {
        this.timeWork = timeWork;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(String ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobPostingDate() {
        return jobPostingDate;
    }

    public void setJobPostingDate(String jobPostingDate) {
        this.jobPostingDate = jobPostingDate;
    }

    public String getJobListingExpDate() {
        return jobListingExpDate;
    }

    public void setJobListingExpDate(String jobListingExpDate) {
        this.jobListingExpDate = jobListingExpDate;
    }

    public String getPhysicalDisableAllow() {
        return physicalDisableAllow;
    }

    public void setPhysicalDisableAllow(String physicalDisableAllow) {
        this.physicalDisableAllow = physicalDisableAllow;
    }

    public String getNonPhysicalDisableAllow() {
        return nonPhysicalDisableAllow;
    }

    public void setNonPhysicalDisableAllow(String nonPhysicalDisableAllow) {
        this.nonPhysicalDisableAllow = nonPhysicalDisableAllow;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Object getJobApproveBy() {
        return jobApproveBy;
    }

    public void setJobApproveBy(Object jobApproveBy) {
        this.jobApproveBy = jobApproveBy;
    }

    public Object getJobApproveDate() {
        return jobApproveDate;
    }

    public void setJobApproveDate(Object jobApproveDate) {
        this.jobApproveDate = jobApproveDate;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Object getContactName() {
        return contactName;
    }

    public void setContactName(Object contactName) {
        this.contactName = contactName;
    }

    public Object getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(Object companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getIndustryInterest() {
        return industryInterest;
    }

    public void setIndustryInterest(String industryInterest) {
        this.industryInterest = industryInterest;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

}
