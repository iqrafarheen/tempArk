package com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail;

/**
 * Created by Bilal on 8/13/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("industry_interest")
    @Expose
    private String industryInterest;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("physical_disable")
    @Expose
    private String physicalDisable;
    @SerializedName("non_physical_disable")
    @Expose
    private String nonPhysicalDisable;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustryInterest() {
        return industryInterest;
    }

    public void setIndustryInterest(String industryInterest) {
        this.industryInterest = industryInterest;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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
