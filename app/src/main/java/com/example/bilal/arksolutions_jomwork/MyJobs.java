package com.example.bilal.arksolutions_jomwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bilal.arksolutions_jomwork.Helper.AlarmHelper;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.AwardedJob;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.JobExample;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.PizzaRetrofitInterface;
import com.example.bilal.arksolutions_jomwork.adapter.JobsListAdapter;
import com.example.bilal.arksolutions_jomwork.listeners.ItemClickListener;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class MyJobs extends AppCompatActivity implements Serializable, ItemClickListener {
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
    List<AwardedJob> abc;
    ProgressDialog progressDialog;
    ItemClickListener listener;
    String am_pm, min;
    int vcbvbvbcvbcbvcvbcbvyugigtuycvbc;
int asjdsjkfhdsfhd;
    int a = 4;
    String[] timesplitted;
    int asdsd=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_my_jobs);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.TitleToolbar);
        toolbarTitle.setText("Awarded Jobs");
        listView = (RecyclerView) findViewById(R.id.listview);
        listener = this;
        credit = (TextView) findViewById(R.id.credit);


        mContext = this;
        mAppController = (AppController) getApplicationContext();

        if (mAppController.credits != null) {
            credit.setVisibility(View.VISIBLE);
            credit.setText("Credits : " + mAppController.credits);
        } else {
            credit.setVisibility(View.GONE);
        }

        progressDialog = new ProgressDialog(mContext);
        progressDialog.show();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listView.setLayoutManager(mLayoutManager);
        doPizzaParsing();
       /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                AwardedJob model = new AwardedJob();
                                                model = abc.get(position);
                                                String timeWork = model.getTimeWork();
                                                String[] splitted = timeWork.split(":");
                                                String dateofWork=model.getDateWork();
                                                String[] splittedDate=dateofWork.split("-");
                                                String year=splittedDate[0];
                                                String month=splittedDate[1];
                                                String date=splittedDate[2];
                                                AlarmHelper alarmHelper = new AlarmHelper(mContext);
                                                Calendar c1 = alarmHelper.setAlarmTime(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]), "am",Integer.parseInt(splittedDate[0]),Integer.parseInt(splittedDate[1]),Integer.parseInt(splittedDate[2]));
                                                alarmHelper.setAlarmEveryDay(c1, 1, false);
                                               // alarmHelper.SetAlarm(mContext);
                                                Intent intent = new Intent(MyJobs.this, JObProgress.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("model", model);
                                                intent.putExtra("bundle", bundle);
                                                startActivity(intent);
                                            }

                                        }
        );*/
    }

    public class LongOperation extends AsyncTask<Void, Void, Void> {


        protected Void doInBackground(Void... arg0) {

            //do code here
            mAppController.credits();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            credit.setVisibility(View.VISIBLE);
            credit.setText("Credits : " + mAppController.credits);
        }

    }

    private void doPizzaParsing() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://jomwork.arksols.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        PizzaRetrofitInterface api = retrofit.create(PizzaRetrofitInterface.class);
        Call<JobExample> call = api.listRepos("candidateawardjobs", "post", mAppController.mUser.getId(), "1", "500");
        call.enqueue(new Callback<JobExample>() {
            @Override
            public void onResponse(retrofit.Response<JobExample> response, Retrofit retrofit) {
                Log.e("ppppppppppppppppppp", "" + response);
                JobExample b = response.body();
                abc = b.getJobs();


                adapter = new JobsListAdapter(mContext, abc);
                adapter.setClickListener(listener);

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

    @Override
    public void onClick(View v, int position) {
        AwardedJob model = new AwardedJob();
        model = abc.get(position);
        String timeWork = model.getTimeWork();
        String[] splitted = timeWork.split(":");
        String dateofWork = model.getDateWork();
        if (splitted[1].contains("am")) {
            timesplitted = splitted[1].split(" ");
            am_pm = timesplitted[1];
            min = timesplitted[0];

        } else {
            am_pm = "am";
            min = splitted[1];
        }
        String[] splittedDate = dateofWork.split("-");
        String year = splittedDate[0];
        String month = splittedDate[1];
        String date = splittedDate[2];
        AlarmHelper alarmHelper = new AlarmHelper(mContext);
        Calendar c1 = alarmHelper.setAlarmTime(Integer.parseInt(splitted[0]), Integer.parseInt(min), am_pm, Integer.parseInt(splittedDate[0]), Integer.parseInt(splittedDate[1]), Integer.parseInt(splittedDate[2]));
        alarmHelper.setAlarmEveryDay(c1, 1, false);
        // alarmHelper.SetAlarm(mContext);
        Intent intent = new Intent(MyJobs.this, JObProgress.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", model);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }
}
