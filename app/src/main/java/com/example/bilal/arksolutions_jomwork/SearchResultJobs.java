package com.example.bilal.arksolutions_jomwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.HeaderRequestUrl;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.Employer;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.Job;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.Job_;
import com.example.bilal.arksolutions_jomwork.adapter.retrofitAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultJobs extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarTitle;

    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    Context mContext;
    AppController mAppController;
    RecyclerView recyclerView;
    retrofitAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<Job> jobList = new ArrayList<>();
    String keyword,location,industry;
    TextView nojob;
    TextView credit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_jobs);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_my_jobs);
        toolbarTitle = (TextView)toolbar.findViewById(R.id.TitleToolbar);
        nojob=(TextView)findViewById(R.id.nojob);
        toolbarTitle.setText("Available Jobs");
        recyclerView = (RecyclerView) findViewById(R.id.listview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        credit = (TextView) findViewById(R.id.credit);

        mContext = this;
        mAppController = (AppController) getApplicationContext();
        if(mAppController.credits != null)
        {
            credit.setVisibility(View.VISIBLE);
            credit.setText("Credits : "+mAppController.credits);
        }
        else {
            credit.setVisibility(View.GONE);
        }

        Intent intent=getIntent();

        keyword=intent.getStringExtra("keyword");

        industry=intent.getStringExtra("industry");
        location=intent.getStringExtra("location");
        if(keyword == null && TextUtils.isEmpty(keyword))
        {
            keyword="Others";
        }
        if(industry == null && TextUtils.isEmpty(industry))
        {
            industry="";
        }
        if(location == null && TextUtils.isEmpty(location))
        {
            location="";
        }


        jsonHandling();
        //doPizzaParsing();

    }

  /*  private void doPizzaParsing() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://jomwork.arksols.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        PizzaRetrofitInterface api = retrofit.create(PizzaRetrofitInterface.class);
        Call<Job> call = api.listJobSearch("joblisting", "post", "75", "1", "5",keyword,location,industry);
        call.enqueue(new Callback<Job>() {
            @Override
            public void onResponse(retrofit.Response<Job> response, Retrofit retrofit) {
                Log.e("ppppppppppppppppppp", "" + response);

              *//*  JobExample b = response.body();
                List<AwardedJob> abc = b.getJobs();


                JobExample obj = new JobExample();

                adapter = new Adapter(mContext, abc);*//*
                listView.setAdapter(adapter);
                progressDialog.hide();


             *//*   recyclerView.setAdapter(recyclerViewAdapter);
                mLoadingView.stopNestedScroll();
                mLoadingView.setVisibility(View.GONE);*//*


                // listview = (ListView) findViewById(R.id.listview);
                //listview.setAdapter(new PizzaListAdapter(MainActivity.this,abc));
                //  progressView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e("FAILEDDDDDDDDDDD", "TRUE");
            }
        });



    }*/

    @Override
    protected void onResume() {
        super.onResume();
        if(mAppController.credits != null)
        {
            credit.setVisibility(View.VISIBLE);
            credit.setText("Credits : "+mAppController.credits);
        }
        else {
            credit.setVisibility(View.GONE);
        }

    }

    public void jsonHandling() {
      final ProgressDialog pDialog = new ProgressDialog(mContext);
      pDialog.setMessage("Loading...");
      pDialog.show();
      jsonObjReq = new JsonObjectRequest(Request.Method.GET,
              HeaderRequestUrl.base_url + "?function=joblisting&action=post&user_id="+mAppController.mUser.getId()+"&page=1&per_page=500&search_term=" + keyword + "&location=" + location + "&industry=" + industry,
              null,
              new Response.Listener<JSONObject>() {


                  @Override
                  public void onResponse(JSONObject response) {
                      Log.e("ssf", response.toString());
                      try {
                          int num;
                          JSONArray noofJob = response.getJSONArray("jobs");
                          num = noofJob.length();
                          int i = 0;
                          jobList.clear();
                          if (num == 0) {
                              Toast.makeText(mContext, "No job found!", Toast.LENGTH_SHORT).show();
                              nojob.setVisibility(View.VISIBLE);

                          } else {
                              nojob.setVisibility(View.GONE);
                              for (i = 0; i < noofJob.length(); i++)

                              {
                                  Employer employer = new Employer();
                                  Job_ jobModel = new Job_();
                                  Job jobListModel = new Job();
                                  JSONObject obj = response.getJSONArray("jobs").getJSONObject(i).getJSONObject("employer");
                                  employer.setCompanyName(obj.getString("company_name"));
                                  employer.setContactName(obj.getString("contact_name"));
                                  employer.setContactNumber(obj.getString("contact_number"));
                                  employer.setIndustry(obj.getString("industry"));
                                  employer.setCompanyLogo(obj.getString("company_logo"));
                                  employer.setAddress(obj.getString("address"));
                                  employer.setLocation(obj.getString("location"));
                                  JSONObject jobObj = response.getJSONArray("jobs").getJSONObject(i).getJSONObject("job");
                                  jobModel.setJobId(jobObj.getString("job_id"));
                                  jobModel.setJobTitle(jobObj.getString("job_title"));
                                  jobModel.setDescription(jobObj.getString("description"));
                                  jobModel.setIndustry(jobObj.getString("industry"));
                                  jobModel.setNoOfCandidates(jobObj.getString("no_of_candidates"));
                                  jobModel.setDateWork(jobObj.getString("date_work"));
                                  jobModel.setTimeWork(jobObj.getString("time_work"));
                                  jobModel.setDuration(jobObj.getString("duration"));
                                  jobModel.setRatePerHour(jobObj.getString("rate_per_hour"));
                                  jobModel.setJobLocation(jobObj.getString("job_location"));
                                  jobModel.setJobPostingDate(jobObj.getString("job_posting_date"));
                                  jobModel.setJobListingExpDate(jobObj.getString("job_listing_exp_date"));
                                  jobModel.setPhysicalDisableAllow(jobObj.getString("physical_disable_allow"));
                                  jobModel.setNonPhysicalDisableAllow(jobObj.getString("non_physical_disable_allow"));
                                  jobModel.setIsApply(jobObj.getString("isApply"));
                                  jobModel.setJobType(jobObj.getString("job_type"));
                                  jobListModel.setEmployer(employer);
                                  jobListModel.setJob(jobModel);
                                  jobList.add(jobListModel);
                              }
                          }

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                        /* if (statusObj.contentEquals("failed")) {
                            Toast.makeText(mContext, errorObj, Toast.LENGTH_SHORT).show();
                        }*/

                      Log.d(tag_json_obj, response.toString());
                      pDialog.hide();
                      adapter=new retrofitAdapter(mContext,jobList);
                      recyclerView.setAdapter(adapter);


                  }
              }, new Response.ErrorListener() {

          @Override
          public void onErrorResponse(VolleyError error) {
              VolleyLog.d(tag_json_obj, "Error: " + error.getMessage());
              Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
              pDialog.hide();
          }
      });


      //  Volley.newRequestQueue(mContext).add(jsonObjReq);
      mAppController.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

  }


}
