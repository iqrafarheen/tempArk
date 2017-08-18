package com.example.bilal.arksolutions_jomwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bilal.arksolutions_jomwork.Helper.com.example.AwardedJob;
import com.example.bilal.arksolutions_jomwork.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bilal on 7/29/2017.
 */

public class Adapter extends BaseAdapter {
    Context context;
    List<AwardedJob> listdata;
    View itemView;
    LayoutInflater inflater;
    List<AwardedJob> data = new ArrayList<AwardedJob>();

    public Adapter(Context context, List<AwardedJob> jobList) {
        this.context = context;
        this.listdata = jobList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ImageView urgent,diability_image,non_physical_image;
       /* TextView SenderMeaning, RecieverMeaning, SenderTransliteratn, ReceiverTransliteratn;

        LinearLayout SenderBox, ReceiverBox;
    }*/

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Handler handler = new Handler();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.inflayout_job, null);
            handler.Jobtitle = (TextView) convertView
                    .findViewById(R.id.job_heading);
            handler.vacancy = (TextView) convertView
                    .findViewById(R.id.vacancy_value);
            handler.industry = (TextView) convertView
                    .findViewById(R.id.industry_value);
            handler.urgent = (ImageView) convertView.findViewById(R.id.urgent_job_tag);
            handler.location = (TextView) convertView
                    .findViewById(R.id.location_value);
            handler.datetowork = (TextView) convertView
                    .findViewById(R.id.datetowork_value);
            handler.duration = (TextView) convertView
                    .findViewById(R.id.duration_value);
            handler.rateperhour = (TextView) convertView.findViewById(R.id.rm_value);
            handler.tiemtostart = (TextView) convertView.findViewById(R.id.time_start_value);
            handler.bid = (Button) convertView.findViewById(R.id.bid);
            handler.diability_image=(ImageView)convertView.findViewById(R.id.diability_image);
            handler.non_physical_image=(ImageView)convertView.findViewById(R.id.non_physical_image);

            convertView.setTag(handler);

        } else {
            handler = (Handler) convertView.getTag();
         /*   handler.bid.setVisibility(View.VISIBLE);
            handler.bid.setText("");
            handler.bid.setBackgroundResource(R.drawable.urgent_job_tag);
            handler.urgent.setVisibility(View.GONE);*/

            handler.Jobtitle.setText(listdata.get(position).getJobTitle());
            handler.vacancy.setText(listdata.get(position).getNoOfCandidates());
            handler.industry.setText(listdata.get(position).getIndustryInterest());
            handler.location.setText(listdata.get(position).getAddress().toString());
            handler.datetowork.setText(listdata.get(position).getDateWork());
            handler.duration.setText(listdata.get(position).getDuration());
            handler.rateperhour.setText(listdata.get(position).getRatePerHour());
            handler.tiemtostart.setText(listdata.get(position).getTimeWork());
            if(listdata.get(position).getPhysicalDisableAllow().contentEquals("1"))
            {
                handler.diability_image.setVisibility(View.VISIBLE);
            }
            else {
                handler.diability_image.setVisibility(View.GONE);
            }
            if (listdata.get(position).getNonPhysicalDisableAllow().contentEquals("1"))
            {
                handler.non_physical_image.setVisibility(View.VISIBLE);
            }else
            {
                handler.non_physical_image.setVisibility(View.GONE);
            }
            if(listdata.get(position).getJobType().contentEquals("0") || listdata.get(position).getJobType().contentEquals("Urgent"))
            {
                handler.urgent.setImageResource(R.drawable.planned_job);
            }
            else
            {
                handler.urgent.setImageResource(R.drawable.urgent_job_tag);
            }
        }
        return convertView;

    }
}
