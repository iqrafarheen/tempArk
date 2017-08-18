package com.example.bilal.arksolutions_jomwork.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.HeaderRequestUrl;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.Job;
import com.example.bilal.arksolutions_jomwork.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by NineSol on 8/1/2017.
 */

public class retrofitAdapter extends RecyclerView.Adapter<retrofitAdapter.MyViewHolder> {

    Context context;
    List<Job> listdata;
    View itemView;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    AppController mAppController;
    int posId;

    List<Job> data = new ArrayList<Job>();


    public retrofitAdapter(Context context, List<Job> listdata) {
        this.context = context;
        this.listdata = listdata;
        mAppController = (AppController) getApplicationContext();


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflayout_job, parent, false);

        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Job obj = listdata.get(position);
        posId = position;
        final String id = listdata.get(position).getJob().getJobId();

//        String photopath = obj.getPicture();
//
//
//        String realPath = photopath.replace("..\\/uploads\\/fooditems\\/","http://lolaspizza.qiriapp.com/uploads/fooditems/");
//        Log.e("PIC_PATH",realPath.replace("../",""));
//        realPath = "http://lolaspizza.qiriapp.com/"+realPath;
//        Log.e("NEW_PIC_PATH",realPath.replace("../",""));


        // String realPath    = "http://services.hanselandpetal.com/photos/"+photopath;


/*
        Picasso.with(context)
                .load(realPath)
                .into((holder.image));*/
        holder.Jobtitle.setText(listdata.get(position).getJob().getJobTitle());
        holder.vacancy.setText(listdata.get(position).getJob().getNoOfCandidates());
        holder.industry.setText(listdata.get(position).getJob().getIndustry());
        holder.location.setText(listdata.get(position).getJob().getJobLocation().toString());
        holder.datetowork.setText(listdata.get(position).getJob().getDateWork());
        holder.duration.setText(listdata.get(position).getJob().getDuration());
        holder.rateperhour.setText(listdata.get(position).getJob().getRatePerHour());
        holder.tiemtostart.setText(listdata.get(position).getJob().getTimeWork());

        holder.bid.setVisibility(View.VISIBLE);
        if(listdata.get(position).getJob().getPhysicalDisableAllow().contentEquals("1"))
        {
            holder.disabilityImage.setVisibility(View.VISIBLE);
        }
        else {
            holder.disabilityImage.setVisibility(View.GONE);
        }
        if (listdata.get(position).getJob().getNonPhysicalDisableAllow().contentEquals("1"))
        {
            holder.nonPhysicalImage.setVisibility(View.VISIBLE);
        }else
        {
            holder.nonPhysicalImage.setVisibility(View.GONE);
        }
      if(listdata.get(position).getJob().getJobType().contentEquals("0") || listdata.get(position).getJob().getJobType().contentEquals("Urgent"))
        {
            holder.urgentImage.setImageResource(R.drawable.planned_job);
        }
        else
        {
            holder.urgentImage.setImageResource(R.drawable.urgent_job_tag);
        }

        holder.bid.setOnClickListener(null);
        holder.bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply(id, (Button) v);
                //  Button b = ((Button)v);
               /* if(statusObj != null && statusObj != "") {
                    holder.bid.setEnabled(false);
                    holder.bid.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    holder.bid.setText("Applied");
                    }
                else {
                    holder.bid.setEnabled(true);
                    holder.bid.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    holder.bid.setText("Place Bid");
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
      /*ImageLoader imageLoader = ImageLoader.getInstance();
        ImageView imageview = (ImageView) v.findViewById(R.id.imageview);
        imageLoader.displayImage(realPath, imageview);*/


    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Jobtitle, vacancy, industry;
        TextView datetowork, location, duration, rateperhour, tiemtostart;
        Button bid;
        ImageView urgentImage,disabilityImage,nonPhysicalImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            Jobtitle = (TextView) itemView.findViewById(R.id.job_heading);
            vacancy = (TextView) itemView
                    .findViewById(R.id.vacancy_value);
            industry = (TextView) itemView
                    .findViewById(R.id.industry_value);
            bid = (Button) itemView.findViewById(R.id.bid);

            location = (TextView) itemView
                    .findViewById(R.id.location_value);
            datetowork = (TextView) itemView
                    .findViewById(R.id.datetowork_value);
            duration = (TextView) itemView
                    .findViewById(R.id.duration_value);
            rateperhour = (TextView) itemView.findViewById(R.id.rm_value);
            tiemtostart = (TextView) itemView.findViewById(R.id.time_start_value);
            disabilityImage=(ImageView)itemView.findViewById(R.id.diability_image);
            nonPhysicalImage=(ImageView)itemView.findViewById(R.id.non_physical_image);
            urgentImage=(ImageView) itemView.findViewById(R.id.urgent_job_tag);

        }
    }

    public void apply(final String jobId, final Button bid) {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                HeaderRequestUrl.base_url + "?function=jobapply&action=post&user_id=" + mAppController.mUser.getId() + "&job_id=" + jobId,
                null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ssf", response.toString());
                        try {
                            String msg = response.getString("message");
                            Toast.makeText(context, response.get("message").toString(), Toast.LENGTH_SHORT).show();

                            statusObj = response.get("status").toString();
                            if (statusObj.contentEquals("success")) {
                                bid.setEnabled(false);
                                bid.setText("Applied");
                                bid.setBackgroundResource(R.drawable.applied_btn_selector);
                            }
                            if (msg.contentEquals("Job applied Successfully")) {
                                if (mAppController.mUser.getPhysicalDisable().contentEquals("0") && mAppController.mUser.getNonPhysicalDisable().contentEquals("0")) {
                                    mAppController.SubtractCredits(1);
                                    mAppController.credits();
                                }
                            }


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
