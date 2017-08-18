package com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail;

/**
 * Created by NineSol on 8/9/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class detailAwardedJob implements Serializable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("industry_interest")
    @Expose
    private String industryInterest;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("physical_disable")
    @Expose
    private String physicalDisable;
    @SerializedName("non_physical_disable")
    @Expose
    private String nonPhysicalDisable;
    @SerializedName("apply_date")
    @Expose
    private String applyDate;
    @SerializedName("apply_id")
    @Expose
    private String applyId;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("review")
    @Expose
    private String review;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhysicalDisable() {
        return physicalDisable;
    }

    public void setPhysicalDisable(String physicalDisable) {
        this.physicalDisable = physicalDisable;
    }

    public String getNonPhysicalDisable() {
        return nonPhysicalDisable;
    }

    public void setNonPhysicalDisable(String nonPhysicalDisable) {
        this.nonPhysicalDisable = nonPhysicalDisable;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}