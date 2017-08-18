package com.example.bilal.arksolutions_jomwork.Helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.AwardedJob;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.Job;
import com.example.bilal.arksolutions_jomwork.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Bilal on 7/30/2017.
 */

public class SearchJobsAdapter extends BaseAdapter {
    Context context;
    ArrayList<Job> listdata;
    View itemView;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    AppController mAppController;
    LayoutInflater inflater;
    List<AwardedJob> data = new ArrayList<AwardedJob>();
    int positionJob;

    Handler handler = new Handler();

    public SearchJobsAdapter(Context context, ArrayList<Job> jobList) {
        this.context = context;
        this.listdata = jobList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAppController = (AppController) getApplicationContext();
    }

    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return
                position;
    }

    public class Handler {
        TextView Jobtitle, vacancy, industry;
        TextView datetowork, location, duration, rateperhour, tiemtostart;
        Button bid;
       /* TextView SenderMeaning, RecieverMeaning, SenderTransliteratn, ReceiverTransliteratn;

        LinearLayout SenderBox, ReceiverBox;
    }*/

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        positionJob = position;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.inflayout_job, null);
            handler.Jobtitle = (TextView) convertView
                    .findViewById(R.id.job_heading);
            handler.vacancy = (TextView) convertView
                    .findViewById(R.id.vacancy_value);
            handler.industry = (TextView) convertView
                    .findViewById(R.id.industry_value);
            handler.bid = (Button) convertView.findViewById(R.id.bid);

            handler.location = (TextView) convertView
                    .findViewById(R.id.location_value);
            handler.datetowork = (TextView) convertView
                    .findViewById(R.id.datetowork_value);
            handler.duration = (TextView) convertView
                    .findViewById(R.id.duration_value);
            handler.rateperhour = (TextView) convertView.findViewById(R.id.rm_value);
            handler.tiemtostart = (TextView) convertView.findViewById(R.id.time_start_value);
            convertView.setTag(handler);

        } else {
            handler = (Handler) convertView.getTag();
        }
        handler.bid.setVisibility(View.VISIBLE);

        handler.bid.setOnClickListener(null);
        handler.bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply(position, (Button) v);
                //  Button b = ((Button)v);
               /* if(statusObj != null && statusObj != "") {
                    handler.bid.setEnabled(false);
                    handler.bid.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    handler.bid.setText("Applied");
                    }
                else {
                    handler.bid.setEnabled(true);
                    handler.bid.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    handler.bid.setText("Place Bid");
                    }*/


/*
                if(statusObj != null && statusObj != "")
                    if (statusObj.contentEquals("success")) {
                        //Toast.makeText(context, errorObj, Toast.LENGTH_SHORT).show();
                        handler.bid.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        handler.bid.setText("Applied");

                    }else {
                        handler.bid.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                        handler.bid.setText("Apply");
                    }*/
            }
        });

        handler.Jobtitle.setText(listdata.get(position).getJob().getJobTitle());
        handler.vacancy.setText(listdata.get(position).getJob().getNoOfCandidates());
        handler.industry.setText(listdata.get(position).getJob().getIndustry());
        handler.location.setText(listdata.get(position).getJob().getJobLocation().toString());
        handler.datetowork.setText(listdata.get(position).getJob().getDateWork());
        handler.duration.setText(listdata.get(position).getJob().getDuration());
        handler.rateperhour.setText(listdata.get(position).getJob().getRatePerHour());
        handler.tiemtostart.setText(listdata.get(position).getJob().getTimeWork());

        return convertView;
    }

    public void apply(int position, final Button bid) {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                HeaderRequestUrl.base_url + "?function=jobapply&action=post&user_id=" + mAppController.mUser.getId() + "&job_id=" + listdata.get(position).getJob().getJobId(),
                null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ssf", response.toString());
                        try {
                            String msg = response.getString("message");
                            Toast.makeText(context, response.get("message").toString(), Toast.LENGTH_SHORT).show();

                            statusObj = response.get("status").toString();
                            bid.setEnabled(false);
                            bid.setText("Applied");


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
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });


        //  Volley.newRequestQueue(mContext).add(jsonObjReq);
        mAppController.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

}
