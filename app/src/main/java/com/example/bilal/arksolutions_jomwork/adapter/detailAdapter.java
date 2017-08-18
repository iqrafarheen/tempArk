package com.example.bilal.arksolutions_jomwork.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.HeaderRequestUrl;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.Applyjob;

import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.Job;
import com.example.bilal.arksolutions_jomwork.JobDetail;
import com.example.bilal.arksolutions_jomwork.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Bilal on 8/3/2017.
 */

public class detailAdapter extends RecyclerView.Adapter<detailAdapter.MyViewHolder> {
    Context context;
    List<Applyjob> listdata;
    View itemView;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    AppController mAppController;
    String applyId;
    List<Job> data = new ArrayList<Job>();
    int noOfAwarded;


    public detailAdapter(Context context, List<Applyjob> listdata) {
        this.context = context;
        this.listdata = listdata;
        mAppController = (AppController) context.getApplicationContext();


    }

    @Override
    public detailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_detail_inflayout, parent, false);

        return new detailAdapter.MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(detailAdapter.MyViewHolder holder, int position) {
        Applyjob obj = listdata.get(position);

        applyId = listdata.get(position).getApplyId();

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
        holder.name.setText(listdata.get(position).getName());
        holder.reviews.setText(listdata.get(position).getReview()+" Reviews");

        holder.ratingBar.setRating(Float.parseFloat(listdata.get(position).getRating().toString()));
        holder.ratingValue.setText(listdata.get(position).getRating());
        holder.ratingBar.setIsIndicator(true);

        holder.hire.setVisibility(View.VISIBLE);

        holder.hire.setOnClickListener(null);
        holder.hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apply(applyId, (TextView) view);
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
        ImageView companyLogo;

        TextView name, reviews;
        TextView hire;
        ImageView disabilityLogo;
        TextView ratingValue;
        RatingBar ratingBar;
        ArrayList<ImageView> stars = new ArrayList<>();


        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.comp_name);
            reviews = (TextView) itemView
                    .findViewById(R.id.review);
            companyLogo = (ImageView) itemView
                    .findViewById(R.id.logo);
            hire = (TextView) itemView.findViewById(R.id.hire_btn);
            disabilityLogo = (ImageView) itemView.findViewById(R.id.disablity);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbar);
            ratingValue=(TextView)itemView.findViewById(R.id.rate_value);



        }
    }

    public void apply(String applyId, final TextView bid) {
        awardeddlistSize();
        if (noOfAwarded <= JobDetail.noofVacancy) {
            final ProgressDialog pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading...");
            pDialog.show();

            jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    HeaderRequestUrl.base_url + "?function=jobaward&action=post&employer_id=" + mAppController.mUser.getId() + "&apply_id=" + applyId,
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
                                    bid.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                                    bid.setText("Awarded");
                                }
                                if (msg.contentEquals("Job awarded Successfully")) {
                                    if (Integer.parseInt(mAppController.credits) > 0) {
                                        mAppController.SubtractCredits(1);
                                        mAppController.credits();
                                    }
                                    else {
                                        Toast.makeText(context, "No sufficient credit !", Toast.LENGTH_LONG).show();
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
        } else {
            Toast.makeText(context, "You have already hired required number of candidates !", Toast.LENGTH_LONG).show();
        }

    }

    public void awardeddlistSize() {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                HeaderRequestUrl.base_url + "?function=awardedlist&action=post&job_id=" + JobDetail.jobId,
                null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ssf", response.toString());
                        try {
                            JSONArray noofJob = response.getJSONArray("awardedlist");
                            noOfAwarded = noofJob.length();

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
