package com.example.bilal.arksolutions_jomwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bilal.arksolutions_jomwork.adapter.Adapter;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.AwardedJob;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.JobExample;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.PizzaRetrofitInterface;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class AppliedJobs extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTitle;
    TextView credit;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    Context mContext;
    AppController mAppController;
    ListView listView;
    Adapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_jobs);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_my_jobs);
        toolbarTitle = (TextView)toolbar.findViewById(R.id.TitleToolbar);
        toolbarTitle.setText("My Jobs");
        listView = (ListView) findViewById(R.id.listview);

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
        progressDialog = new ProgressDialog(mContext);
        progressDialog.show();
        doPizzaParsing();

    }

    private void doPizzaParsing() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://jomwork.arksols.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        PizzaRetrofitInterface api = retrofit.create(PizzaRetrofitInterface.class);
        Call<JobExample> call = api.listRepos("candidateapplyjobs", "post", mAppController.mUser.getId(), "1", "500");
        call.enqueue(new Callback<JobExample>() {
            @Override
            public void onResponse(retrofit.Response<JobExample> response, Retrofit retrofit) {
                Log.e("ppppppppppppppppppp", "" + response);
                JobExample b = response.body();
                List<AwardedJob> abc = b.getJobs();


                JobExample obj = new JobExample();

                adapter = new Adapter(mContext, abc);
                listView.setAdapter(adapter);
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
}
