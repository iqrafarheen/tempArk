package com.example.bilal.arksolutions_jomwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.AwardedJob;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.JobExample;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.PizzaRetrofitInterface;
import com.example.bilal.arksolutions_jomwork.adapter.JobsListAdapter;
import com.example.bilal.arksolutions_jomwork.listeners.ItemClickListener;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class PostedJobs extends AppCompatActivity implements ItemClickListener {

    Toolbar toolbar;
    TextView toolbarTitle;
    TextView credit;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    Context mContext;
    AppController mAppController;
    RecyclerView listView;
    JobsListAdapter adapter;
    ProgressDialog progressDialog;
    List<AwardedJob> abc;
    ItemClickListener listener;
    TextView nojobPosted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_my_jobs);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.TitleToolbar);
        toolbarTitle.setText("My Jobs");
        listView = (RecyclerView) findViewById(R.id.listview);
        nojobPosted=(TextView) findViewById(R.id.no_job_posted);

        credit = (TextView) findViewById(R.id.credit);
        credit.setVisibility(View.VISIBLE);
        mContext = this;
        listener = this;
        mAppController = (AppController) getApplicationContext();
        if (mAppController.credits != null) {
            credit.setVisibility(View.VISIBLE);
            credit.setText("Credits : " + mAppController.credits);
        } else {
            credit.setVisibility(View.GONE);
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listView.setLayoutManager(mLayoutManager);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("Loading... ");
        progressDialog.show();
        doPizzaParsing();
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent jobDetail=new Intent(PostedJobs.this,JobDetail.class);
              //  Bundle bundle=new Bundle();
                //bundle.putSerializable("object",abc.get(position).getJobId());
                jobDetail.putExtra("object",abc.get(position).getJobId());

                startActivity(jobDetail);
            }
        });*/
    }

    private void doPizzaParsing() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://jomwork.arksols.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        PizzaRetrofitInterface api = retrofit.create(PizzaRetrofitInterface.class);
        Call<JobExample> call = api.listRepos("employerjob", "post", mAppController.mUser.getId(), "1", "500");
        call.enqueue(new Callback<JobExample>() {
            @Override
            public void onResponse(retrofit.Response<JobExample> response, Retrofit retrofit) {
                Log.e("ppppppppppppppppppp", "" + response);
                JobExample b = response.body();
                abc = b.getJobs();
                if (abc.size() > 0)

                {
                    listView.setVisibility(View.VISIBLE);
                    nojobPosted.setVisibility(View.GONE);
                    JobExample obj = new JobExample();

                    adapter = new JobsListAdapter(mContext, abc);
                    adapter.setClickListener(listener);
                    listView.setAdapter(adapter);
                }else {
                    listView.setVisibility(View.GONE);
                    nojobPosted.setVisibility(View.VISIBLE);
                }
                progressDialog.hide();


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
    public void onClick(View v, int position) {
        Intent jobDetail = new Intent(PostedJobs.this, JobDetail.class);
        //  Bundle bundle=new Bundle();
        //bundle.putSerializable("object",abc.get(position).getJobId());
        jobDetail.putExtra("object", abc.get(position).getJobId());

        startActivity(jobDetail);
    }


}
