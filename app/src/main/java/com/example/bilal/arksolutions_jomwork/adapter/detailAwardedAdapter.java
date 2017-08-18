package com.example.bilal.arksolutions_jomwork.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.bilal.arksolutions_jomwork.Helper.com.example.AwardedJob;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.Job;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.detailAwardedJob;
import com.example.bilal.arksolutions_jomwork.R;
import com.example.bilal.arksolutions_jomwork.listeners.ItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NineSol on 8/9/2017.
 */

public class detailAwardedAdapter extends RecyclerView.Adapter<detailAwardedAdapter.MyViewHolder> {
    Context context;
    List<detailAwardedJob> listdata;
    View itemView;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    AppController mAppController;
    String applyId;
    private ItemClickListener clickListener;
    List<Job> data = new ArrayList<Job>();


    public detailAwardedAdapter(Context context, List<detailAwardedJob> listdata) {
        this.context = context;
        this.listdata = listdata;
        mAppController = (AppController) context.getApplicationContext();


    }

    @Override
    public detailAwardedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_detail_inflayout, parent, false);

        return new detailAwardedAdapter.MyViewHolder(itemView);

    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    @Override
    public void onBindViewHolder(detailAwardedAdapter.MyViewHolder holder, int position) {
        detailAwardedJob obj = listdata.get(position);

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
        holder.rating.setText(listdata.get(position).getRating());
        holder.ratingBar.setRating(Float.parseFloat(listdata.get(position).getRating()));
        holder.ratingBar.setEnabled(false);
        holder.ratingBar.setIsIndicator(true);
        holder.mobNum.setText(listdata.get(position).getMobileNumber().toString());

        if(listdata.get(position).getPhysicalDisable().contentEquals("1"))
        {
            holder.disabilityLogo.setVisibility(View.VISIBLE);
        }
        else {
            holder.disabilityLogo.setVisibility(View.GONE);
        }
        if(listdata.get(position).getNonPhysicalDisable().contentEquals("1"))
        {
            holder.nonPhysicalDisable.setVisibility(View.VISIBLE);
        }
        else {
            holder.nonPhysicalDisable.setVisibility(View.GONE);
        }
        holder.hire.setVisibility(View.GONE);
        holder.mSeparator.setVisibility(View.GONE);

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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView companyLogo;

        TextView name, reviews,rating;
        TextView hire;
        LinearLayout mSeparator;
        RatingBar ratingBar;
        TextView mobNum;
        ImageView disabilityLogo,nonPhysicalDisable;
        ArrayList<ImageView> stars = new ArrayList<>();


        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.comp_name);
            reviews = (TextView) itemView
                    .findViewById(R.id.review);
            rating=(TextView)itemView.findViewById(R.id.rate_value);
            companyLogo = (ImageView) itemView
                    .findViewById(R.id.logo);
            hire = (TextView) itemView.findViewById(R.id.hire_btn);
            ratingBar=(RatingBar)itemView.findViewById(R.id.ratingbar);
            disabilityLogo = (ImageView) itemView.findViewById(R.id.disablity);
            mSeparator = (LinearLayout) itemView.findViewById(R.id.separator);
            mobNum=(TextView)itemView.findViewById(R.id.mob_no);
            nonPhysicalDisable=(ImageView)itemView.findViewById(R.id.nonphysical_disablity);
           // disabilityLogo=(ImageView)itemView.findViewById(R.id.diability_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v,getPosition());
        }
    }


    public void apply(String applyId, final TextView bid) {
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
                            bid.setEnabled(false);
                            bid.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                            bid.setText("Awarded");


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

