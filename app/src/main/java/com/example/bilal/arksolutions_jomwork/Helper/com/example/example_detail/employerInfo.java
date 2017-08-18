package com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail;

/**
 * Created by Bilal on 8/13/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class employerInfo {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("contact_name")
    @Expose
    private Object contactName;
    @SerializedName("industry")
    @Expose
    private String industry;
    @SerializedName("company_logo")
    @Expose
    private String companyLogo;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("location")
    @Expose
    private Object location;
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
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


