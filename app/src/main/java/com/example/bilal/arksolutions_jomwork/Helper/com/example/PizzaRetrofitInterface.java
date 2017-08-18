package com.example.bilal.arksolutions_jomwork.Helper.com.example;

import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.UserInfoExample;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.employerExample;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.employerInfo;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Ninesol on 4/6/2017.
 */

public interface PizzaRetrofitInterface {


    @GET("/webservices/service.php?")
   /* Call<List<PizzaModelClass>> call= getPizzaJsonData();
    Call<PizzaModelClass> example(@Query("tableName") String finder) call = getPizzaJsonData();*/
    Call<JobExample> listRepos(@Query("function") String function,
                               @Query("action") String action,
                               @Query("user_id") String id,
                               @Query("page") String page,
                               @Query("per_page") String perpage);

    @GET("/webservices/service.php?")
    Call<Job> listJobSearch(@Query("function") String function,
                                   @Query("action") String action,
                                   @Query("user_id") String id,
                                   @Query("page") String page,
                                   @Query("per_page") String perpage,
                                   @Query("search_term") String keyword,
                                   @Query("location") String location,
                                   @Query("industry") String industry
    );
    @GET("/webservices/service.php?")
    Call<JobExample> AppliedJobSearch(@Query("function") String function,
                            @Query("action") String action,
                            @Query("user_id") String id,
                            @Query("page") String page,
                            @Query("per_page") String perpage

    );
    @GET("/webservices/service.php?")
    Call<DetailExample> DetailJob(@Query("function") String function,
                                      @Query("action") String action,
                                      @Query("job_id") String id


    );
    @GET("/webservices/service.php?")
    Call<UserInfoExample> userInfo(@Query("function") String function,
                                    @Query("action") String action,
                                    @Query("user_id") String id


    );
    @GET("/webservices/service.php?")
    Call<employerExample> employerInfo(@Query("function") String function,
                                       @Query("action") String action,
                                       @Query("user_id") String id


    );
}