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

import com.example.bilal.arksolutions_jomwork.Helper.AlarmHelper;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.DetailExample;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.PizzaRetrofitInterface;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.Job;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.detailAwardedJob;
import com.example.bilal.arksolutions_jomwork.adapter.detailAwardedAdapter;
import com.example.bilal.arksolutions_jomwork.listeners.ItemClickListener;

import java.util.Calendar;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class EmployerAwardedDetailList extends AppCompatActivity implements ItemClickListener {
    RecyclerView recyclerView;
    Context mContext;
    List<detailAwardedJob> awardedlist;
    detailAwardedAdapter adapter;
    ProgressDialog progressDialog;
    String joobId;
    Toolbar toolbar;
    ItemClickListener listener;
    TextView title;
    Job job; String[] timesplitted;
    String am_pm,min;
    String jobTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_awarded_detail_list);
        recyclerView = (RecyclerView) findViewById(R.id.awardedcandidatelist);
        toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        title = (TextView) toolbar.findViewById(R.id.TitleToolbar);
        mContext = this;
        listener = this;

        Intent intent = getIntent();
        joobId = intent.getStringExtra("object");
        jobTitle= intent.getStringExtra("jobTitle");
        title.setText(jobTitle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
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
                Log.e("ppppppppppppppppppp", "" + response);
                progressDialog = new ProgressDialog(mContext);
                progressDialog.show();
                DetailExample b = response.body();
                job = response.body().getJob();

                awardedlist = response.body().getAwardedjobs();
                if (awardedlist.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    //  noCandidate.setVisibility(View.VISIBLE);

                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    //   noCandidate.setVisibility(View.GONE);
                    adapter = new detailAwardedAdapter(mContext, awardedlist);
                    adapter.setClickListener(listener);
                    recyclerView.setAdapter(adapter);
                }


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
    public void onClick(View v, int position) {
        Intent jobDetail = new Intent(EmployerAwardedDetailList.this, JObProgress.class);
        //  Bundle bundle=new Bundle();
        //bundle.putSerializable("object",abc.get(position).getJobId());

        String timeWork = job.getTimeWork();
        String[] splitted = timeWork.split(":");
        if(splitted[1].contains("am"))
        {
          timesplitted =splitted[1].split(" ");
            am_pm=timesplitted[1];
            min=timesplitted[0];

        }
        else {
            am_pm="am";
            min=splitted[1];
        }

        String dateofWork=job.getDateWork();

        String[] splittedDate=dateofWork.split("-");
        String year=splittedDate[0];
        String month=splittedDate[1];
        String date=splittedDate[2];
        AlarmHelper alarmHelper = new AlarmHelper(mContext);

        Calendar c1 = alarmHelper.setAlarmTime(Integer.parseInt(splitted[0]),Integer.parseInt(min), am_pm,Integer.parseInt(splittedDate[0]),Integer.parseInt(splittedDate[1]),Integer.parseInt(splittedDate[2]));
        alarmHelper.setAlarmEveryDay(c1, 1, false);

        Bundle bundle = new Bundle();
        bundle.putSerializable("jobModel", job);
        bundle.putString("candidate_id",awardedlist.get(position).getUserId());
        bundle.putString("applyId",awardedlist.get(position).getApplyId());
        jobDetail.putExtra("jobBundle", bundle);

        startActivity(jobDetail);
    }
}
