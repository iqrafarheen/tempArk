package com.example.bilal.arksolutions_jomwork.Helper.com.example;

import com.example.bilal.arksolutions_jomwork.Helper.com.example.User;
/*import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;*/

public class Example {

    /*@SerializedName("message")
    @Expose*/
    private String message;
    /*@SerializedName("status")
    @Expose*/
    private String status;
   /* @SerializedName("user")
    @Expose*/
    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
