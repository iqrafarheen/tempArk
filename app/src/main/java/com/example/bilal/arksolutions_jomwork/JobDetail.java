package com.example.bilal.arksolutions_jomwork;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.Applyjob;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.Job;
import com.example.bilal.arksolutions_jomwork.adapter.Adapter;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.AwardedJob;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.DetailExample;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.JobExample;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.PizzaRetrofitInterface;
import com.example.bilal.arksolutions_jomwork.adapter.detailAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class JobDetail extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTitle;
    TextView credit;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    Context mContext;
    AppController mAppController;
    RecyclerView listView;
    detailAdapter adapter;
    ProgressDialog progressDialog;
    JobExample obj;
    String joobId;
    Job abc;
    TextView jobtype, duration, industry, vacancy, dateofwork, location, rateperHour, timetostart;
    List<Applyjob> applyjobs;
    public static int noofVacancy;
    public static int jobId;
    TextView noCandidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.TitleToolbar);
        toolbarTitle.setText("Awarded Jobs");
        mAppController = (AppController) getApplicationContext();
        listView = (RecyclerView) findViewById(R.id.joblistview);

        credit = (TextView) findViewById(R.id.credit);
        credit.setVisibility(View.VISIBLE);
        jobtype = (TextView) findViewById(R.id.jobtype_value);
        duration = (TextView) findViewById(R.id.duration_value);
        industry = (TextView) findViewById(R.id.industry_value);
        vacancy = (TextView) findViewById(R.id.vacancy_value);
        dateofwork = (TextView) findViewById(R.id.dateofwork_value);
        location = (TextView) findViewById(R.id.location_value);
        rateperHour = (TextView) findViewById(R.id.rm_value);
        timetostart = (TextView) findViewById(R.id.time_value);
        noCandidate = (TextView) findViewById(R.id.noCandidate);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listView.setLayoutManager(mLayoutManager);
        mContext = this;
        if (mAppController.credits != null) {
            credit.setVisibility(View.VISIBLE);
            credit.setText("Credits : " + mAppController.credits);
        } else {
            credit.setVisibility(View.GONE);
        }
        Intent intent = getIntent();
      /*   obj=(JobExample) intent.getBundleExtra("bundle").getSerializable("object");
        joobId=obj.getJobs().get(0).getJobId().toString();*/
        joobId = intent.getStringExtra("object");

        doPizzaParsing();


    }

    private void doPizzaParsing() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://jomwork.arksols.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        PizzaRetrofitInterface api = retrofit.create(PizzaRetrofitInterface.class);
        Call<DetailExample> call = api.DetailJob("jobdetail", "post", joobId);
        call.enqueue(new Callback<DetailExample>() {
            @Override
            public void onResponse(retrofit.Response<DetailExample> response, Retrofit retrofit) {
                Log.e("pppppppppppppppppp", "" + response);
                if (!((Activity) mContext).isFinishing()) {
                    progressDialog = new ProgressDialog(mContext);
                    progressDialog.show();
                }
                DetailExample b = response.body();
                abc = b.getJob();

                duration.setText(abc.getDuration());
                industry.setText(abc.getIndustry());
                timetostart.setText(abc.getTimeWork());
                if(abc.getJobType().contentEquals("0"))
                {
                    jobtype.setText("Urgent");
                }else {
                    jobtype.setText("Planned");
                }

                rateperHour.setText(abc.getRatePerHour());
                location.setText(abc.getJobLocation());
                dateofwork.setText(abc.getDateWork());
                vacancy.setText(abc.getNoOfCandidates());


                noofVacancy = Integer.parseInt(abc.getNoOfCandidates());
                jobId = Integer.parseInt(abc.getJobId());

                applyjobs = response.body().getApplyjobs();
                abc.getJobId();
                if (applyjobs.size() == 0) {
                    listView.setVisibility(View.GONE);
                    noCandidate.setVisibility(View.VISIBLE);

                } else {
                    listView.setVisibility(View.VISIBLE);
                    noCandidate.setVisibility(View.GONE);
                    adapter = new detailAdapter(mContext, applyjobs);
                    listView.setAdapter(adapter);
                }

                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.hide();

          /*      adapter = new Adapter(mContext, abc);
                listView.setAdapter(adapter);
                progressDialog.hide();*/


             /*   recyclerView.setAdapter(recyclerViewAdapter);
                mLoadingView.stopNestedScroll();
                mLoadingView.setVisibility(View.GONE);*/


                // listview = (ListView) findViewById(R.id.listview);
                //listview.setAdapter(new PizzaListAdapter(MainActivity.this,abc));
                //  progressView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e("FAILEDDDDDDDDDDD", "TRUE");
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAppController.credits != null) {
            credit.setVisibility(View.VISIBLE);
            credit.setText("Credits : " + mAppController.credits);
        } else {
            credit.setVisibility(View.GONE);
        }
    }
}