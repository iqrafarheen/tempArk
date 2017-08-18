package com.example.bilal.arksolutions_jomwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.AwardedJob;
import com.example.bilal.arksolutions_jomwork.R;
import com.example.bilal.arksolutions_jomwork.listeners.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NineSol on 8/1/2017.
 */

public class JobsListAdapter extends RecyclerView.Adapter<JobsListAdapter.MyViewHolder>  {

    Context context;

    View itemView;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    AppController mAppController;
    private ItemClickListener clickListener;


    LayoutInflater inflater;
    List<AwardedJob> listdata = new ArrayList<AwardedJob>();

    public JobsListAdapter(Context context, List<AwardedJob> jobList) {
        this.context = context;
        this.listdata = jobList;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflayout_job, parent, false);

        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    //    Job obj = listdata.get(position);

     //   final String posId = listdata.get(position).getJob().getJobId();

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
        holder.Jobtitle.setText(listdata.get(position).getJobTitle());
        holder.vacancy.setText(listdata.get(position).getNoOfCandidates());
        holder.industry.setText(listdata.get(position).getIndustry());
        holder.location.setText(listdata.get(position).getJobLocation().toString());
        holder.datetowork.setText(listdata.get(position).getDateWork());
        holder.duration.setText(listdata.get(position).getDuration());
        holder.rateperhour.setText(listdata.get(position).getRatePerHour());
        holder.tiemtostart.setText(listdata.get(position).getTimeWork());

        if(listdata.get(position).getPhysicalDisableAllow().contentEquals("1"))
        {
            holder.disabilityImage.setVisibility(View.VISIBLE);
        }
        else {
            holder.disabilityImage.setVisibility(View.GONE);
        }
        if(listdata.get(position).getNonPhysicalDisableAllow().contentEquals("1"))
        {
            holder.nonPhysicalDisableImage.setVisibility(View.VISIBLE);
        }
        else {
            holder.nonPhysicalDisableImage.setVisibility(View.GONE);
        }
        if(listdata.get(position).getJobType().contentEquals("0")|| listdata.get(position).getJobType().contentEquals("Urgent"))
        {
            holder.jobTypeImage.setImageResource(R.drawable.urgent_job_tag);
        }
        else {
            holder.jobTypeImage.setImageResource(R.drawable.planned_job);
        }


      /*ImageLoader imageLoader = ImageLoader.getInstance();
        ImageView imageview = (ImageView) v.findViewById(R.id.imageview);
        imageLoader.displayImage(realPath, imageview);*/


    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Jobtitle, vacancy, industry;
        ImageView disabilityImage,nonPhysicalDisableImage,jobTypeImage;
        TextView datetowork, location, duration, rateperhour, tiemtostart;
        Button bid;
        TextView nojobPosted;

        public MyViewHolder(View itemView) {
            super(itemView);

            Jobtitle = (TextView) itemView.findViewById(R.id.job_heading);
            vacancy = (TextView) itemView
                    .findViewById(R.id.vacancy_value);
            industry = (TextView) itemView
                    .findViewById(R.id.industry_value);

            location = (TextView) itemView
                    .findViewById(R.id.location_value);
            datetowork = (TextView) itemView
                    .findViewById(R.id.datetowork_value);
            duration = (TextView) itemView
                    .findViewById(R.id.duration_value);
            rateperhour = (TextView) itemView.findViewById(R.id.rm_value);
            tiemtostart = (TextView) itemView.findViewById(R.id.time_start_value);
            disabilityImage=(ImageView)itemView.findViewById(R.id.diability_image);
            nonPhysicalDisableImage=(ImageView)itemView.findViewById(R.id.non_physical_image);
            jobTypeImage=(ImageView)itemView.findViewById(R.id.urgent_job_tag);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view,getPosition());
        }
    }

   /* public void apply(String position, final Button bid) {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                HeaderRequestUrl.base_url + "?function=jobapply&action=post&user_id=" + mAppController.mUser.getId() + "&job_id=" + position,
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
                            bid.setBackgroundResource(R.drawable.applied_btn_selector);


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
*/
}
