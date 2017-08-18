package com.example.bilal.arksolutions_jomwork.Helper.com.example;

/*import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

public class User {

  /*  @SerializedName("id")
    @Expose*/
    private String id;
   /* @SerializedName("username")
    @Expose*/
    private String username;
   /* @SerializedName("email")
    @Expose*/
    private String email;
//    @SerializedName("name")
//    @Expose
    private String name;
   /* @SerializedName("contact_name")
    @Expose*/
    private Object contactName;
  /*  @SerializedName("dob")
    @Expose*/
    private String dob;
    /*@SerializedName("mobile_number")
    @Expose*/
    private String mobileNumber;
//    @SerializedName("gender")
//    @Expose
    private String gender;
    /*@SerializedName("industry_interest")
    @Expose*/
    private String industryInterest;
   /* @SerializedName("physical_disable")
    @Expose*/
    private String physicalDisable;
  /*  @SerializedName("user_avatar")
    @Expose*/
    private Object userAvatar;
    /*@SerializedName("address")
    @Expose*/
    private String address;
  /*  @SerializedName("location")
    @Expose*/
    private String location;
  /*  @SerializedName("non_physical_disable")
    @Expose*/
    private String nonPhysicalDisable;
  /*  @SerializedName("role_id")
    @Expose*/
    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Object getContactName() {
        return contactName;
    }

    public void setContactName(Object contactName) {
        this.contactName = contactName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIndustryInterest() {
        return industryInterest;
    }

    public void setIndustryInterest(String industryInterest) {
        this.industryInterest = industryInterest;
    }

    public String getPhysicalDisable() {
        return physicalDisable;
    }

    public void setPhysicalDisable(String physicalDisable) {
        this.physicalDisable = physicalDisable;
    }

    public Object getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(Object userAvatar) {
        this.userAvatar = userAvatar;
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

    public String getNonPhysicalDisable() {
        return nonPhysicalDisable;
    }

    public void setNonPhysicalDisable(String nonPhysicalDisable) {
        this.nonPhysicalDisable = nonPhysicalDisable;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
