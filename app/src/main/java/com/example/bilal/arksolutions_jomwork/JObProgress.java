package com.example.bilal.arksolutions_jomwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bilal.arksolutions_jomwork.Helper.AlarmHelper;
import com.example.bilal.arksolutions_jomwork.Helper.AlarmReceiverPrayers;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.HeaderRequestUrl;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.AwardedJob;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.Job;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class JObProgress extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    TextView jobtype, duration, industry, vacancy, dateofwork, location, rateperHour, timetostart;
    EditText editFeedback;
    Calendar sysCalendar;
    Calendar timerCalndar;
    Context mContext;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    AppController mAppController;
    String comments;
    String coments;
    Button submitbtn;
    String finalUrl;
    AwardedJob model;
    TextView hours, mins, secs;
    long miliTimesCurrent, miliTimesTarget, miliTimesDuration, miliTimesDifference;
    String durationHours, leftHours, leftMins, leftSecs, timeLeft;
    Timer timer;
    public String status;
    MyTime mt;
    TextView statusToolbar;
    RatingBar ratingBar;
    String ratingValue;
    TextView statusValue;
    String applyId;
    Job jobModel;
    Calendar currentCalendar;
    LinearLayout feedback;
    String candidate_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_progress);
        toolbar = (Toolbar) findViewById(R.id.toolbar_job_progress);
        title = (TextView) toolbar.findViewById(R.id.TitleToolbar);
        statusToolbar = (TextView) findViewById(R.id.credit);
        jobtype = (TextView) findViewById(R.id.jobtype_value);
        duration = (TextView) findViewById(R.id.duration_value);
        industry = (TextView) findViewById(R.id.industry_value);
        vacancy = (TextView) findViewById(R.id.vacancy_value);
        dateofwork = (TextView) findViewById(R.id.dateofwork_value);
        location = (TextView) findViewById(R.id.location_value);
        rateperHour = (TextView) findViewById(R.id.rm_value);
        timetostart = (TextView) findViewById(R.id.time_value);
        editFeedback = (EditText) findViewById(R.id.editcoment);
        submitbtn = (Button) findViewById(R.id.btn_submit_coments);
        statusValue = (TextView) findViewById(R.id.status_value);
        feedback=(LinearLayout)findViewById(R.id.feedback_box);
        hours = (TextView) findViewById(R.id.hrs);
        mins = (TextView) findViewById(R.id.mins);
        secs = (TextView) findViewById(R.id.secs);
        title.setText("Job Progress");
        mContext = this;
        Intent intent = getIntent();
        if (intent.getBundleExtra("bundle") != null) {
            model = (AwardedJob) intent.getBundleExtra("bundle").getSerializable("model");

            mAppController = (AppController) mContext.getApplicationContext();
            statusToolbar.setVisibility(View.VISIBLE);
            duration.setText(model.getDuration());
            industry.setText(model.getIndustry());
            timetostart.setText(model.getTimeWork());
            jobtype.setText(model.getJobType());
            rateperHour.setText(model.getRatePerHour());
            location.setText(model.getJobLocation());
            dateofwork.setText(model.getDateWork());
            vacancy.setText(model.getNoOfCandidates());
            durationHours = model.getDuration();
        } else {
            jobModel = (Job) intent.getBundleExtra("jobBundle").getSerializable("jobModel");
            applyId = (String) intent.getBundleExtra("jobBundle").get("applyId");
            candidate_Id=(String)intent.getBundleExtra("jobBundle") .get("candidate_id");
            mAppController = (AppController) mContext.getApplicationContext();
            statusToolbar.setVisibility(View.VISIBLE);
            duration.setText(jobModel.getDuration());
            industry.setText(jobModel.getIndustry());
            timetostart.setText(jobModel.getTimeWork());
            if(jobModel.getJobType().contentEquals("0"))
            {
                jobtype.setText("Urgent");
            }
            else {
                jobtype.setText("Planned");
            }

            rateperHour.setText(jobModel.getRatePerHour());
            location.setText(jobModel.getJobLocation());
            dateofwork.setText(jobModel.getDateWork());
            vacancy.setText(jobModel.getNoOfCandidates());
            durationHours = jobModel.getDuration();
        }


        currentCalendar = Calendar.getInstance();
        currentCalendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH) - 1);

        addListenerOnRatingBar();

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"You are not able to give feedback before completion of your job!",Toast.LENGTH_LONG).show();
            }
        });
        if (AlarmReceiverPrayers.isWorkStarted == true) {
            updateTime();
            status = "2" + "";
            status();
            statusToolbar.setText("In Progress");
            statusValue.setText("(In Progress)");
            editFeedback.setFocusable(false);
            editFeedback.setEnabled(false);
            submitbtn.setEnabled(false);
            ratingBar.setEnabled(false);
            ratingBar.setFocusable(false);
            feedback.setFocusable(true);
            feedback.setEnabled(true);


        } else {
            if (currentCalendar.before(AlarmHelper.jobCalendar)) {
                statusToolbar.setText("Not Started");
                statusValue.setText("(Not Started)");
                editFeedback.setFocusable(false);
                editFeedback.setEnabled(false);
                submitbtn.setEnabled(false);
                ratingBar.setEnabled(false);
                ratingBar.setFocusable(false);
                feedback.setFocusable(true);
                feedback.setEnabled(true);

            } else {
                status = "3" + "";
                status();
                statusToolbar.setText("Completed");
                statusValue.setText("(Completed)");
                editFeedback.setFocusable(true);
                editFeedback.setEnabled(true);
                submitbtn.setEnabled(true);
                ratingBar.setEnabled(true);
                ratingBar.setFocusable(true);
                feedback.setFocusable(false);
                feedback.setEnabled(false);
            }
        }
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coments = editFeedback.getText().toString();
                try {
                    if (coments != null && ratingValue != null)
                        doPizzaParsing();
                    else {
                        Toast.makeText(mContext, "Please enter your feedback", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "Please enter your feedback", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void updateTime() {
        // TODO Auto-generated method stub
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        mt = new MyTime(mContext, false);
        timer.schedule(mt, 1, 500);
    }

    public class MyTime extends TimerTask {
        String tz;
        boolean isEnd;

        public MyTime(Context context, boolean isEnd) {

            this.isEnd = isEnd;

        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {

                        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

                        handler.sendEmptyMessage(0);
                        timeLeftCalculations();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                // dateTime.setText(result.toLocaleString());
                //System.out.println(result.getHours()+" "+result.getMinutes());
                hours.setText(leftHours);
                mins.setText(leftMins);
                secs.setText(leftSecs);
              /*  if(t.contentEquals("00:00:00"))
                {
                   updateTime(locPref.getTimezone());
                    Toast.makeText(context,"dfgg",Toast.LENGTH_SHORT).show();
                }*/

                // fl1.addView(new CustomClock(context, lp1.height / 2, lp1.width / 2, result));
            }
        };
    }

    public void timeLeftCalculations() {

        //miliTimesCurrent = currentCalendar.getTimeInMillis();
        miliTimesCurrent= currentCalendar.getTimeInMillis();

        miliTimesDuration = Integer.parseInt(durationHours) * 60 * 60 * 1000;
        miliTimesTarget = AlarmHelper.targetTimeMiliseconds + miliTimesDuration;
        if (miliTimesCurrent < miliTimesTarget)

        {
            miliTimesDifference = miliTimesTarget - miliTimesCurrent;
            //long second = Math.abs(result.getSeconds() - 60);
            leftSecs = String.valueOf((miliTimesDifference / 1000) % 60);
            leftMins = String.valueOf((miliTimesDifference / (1000 * 60)) % 60);
            leftHours = String.valueOf((miliTimesDifference / (1000 * 60 * 60)) % 24);
            Log.e("aa", "" + leftHours);

            //timeLeft = String.format("%02d:%02d:%02d", leftHours, leftMins, leftSecs);
            editFeedback.setEnabled(false);
            submitbtn.setEnabled(false);
            editFeedback.setFocusable(false);
            ratingBar.setEnabled(false);
            ratingBar.setFocusable(false);
        } else {

            Toast.makeText(mContext, "Finished", Toast.LENGTH_LONG).show();
            AlarmReceiverPrayers.isWorkStarted = false;
            editFeedback.setEnabled(true);
            submitbtn.setEnabled(true);
            status = "3";
            status();
            statusToolbar.setText("Completed");
            editFeedback.setFocusable(true);
            editFeedback.setEnabled(true);
            submitbtn.setEnabled(true);
            ratingBar.setEnabled(true);
            ratingBar.setFocusable(true);
            feedback.setFocusable(false);
            feedback.setEnabled(false);

        }

    }

    private void doPizzaParsing() {
        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");


        pDialog.show();
        if (mAppController.mUser.getRoleId().contentEquals("2")) {
            finalUrl = HeaderRequestUrl.base_url + "?function=candidatecomments&action=post&job_id=" +model.getJobId()+ "&comments=" + coments + "&ratting=" + ratingValue + "&user_id="+mAppController.mUser.getId();
        } else {
            finalUrl = HeaderRequestUrl.base_url + "?function=employercomments&action=post&job_id=" + jobModel.getJobId() + "&comments=" + coments + "&ratting=" + ratingValue + "&user_id="+candidate_Id;
        }
        jsonObjReq = new JsonObjectRequest(Request.Method.POST,

                finalUrl,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        Toast.makeText(mContext, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            statusObj = (String) response.get("status");
                            errorObj = response.get("error").toString();
                            resObj = response.get("res").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d(tag_json_obj, response.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(tag_json_obj, "Error: " + error.getMessage());
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });
        mAppController.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    private void status() {
        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");


        pDialog.show();

        finalUrl = HeaderRequestUrl.base_url + "?function=changestatus&action=post&apply_id=" + "1" + "&status=" + status;

        jsonObjReq = new JsonObjectRequest(Request.Method.POST,

                finalUrl,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                       // Toast.makeText(mContext, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            statusObj = (String) response.get("status");
                            errorObj = response.get("error").toString();
                            resObj = response.get("res").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d(tag_json_obj, response.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(tag_json_obj, "Error: " + error.getMessage());
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });
        mAppController.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        // txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                ratingValue = String.valueOf(rating);
            }
        });


    }
}