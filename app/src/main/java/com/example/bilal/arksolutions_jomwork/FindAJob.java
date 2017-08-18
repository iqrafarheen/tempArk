package com.example.bilal.arksolutions_jomwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.GeoCoderVolley;
import com.example.bilal.arksolutions_jomwork.Helper.HeaderRequestUrl;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.Employer;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.Job;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.Job_;
import com.example.bilal.arksolutions_jomwork.listeners.OnCurrentLocationFoundListner;
import com.example.bilal.arksolutions_jomwork.listeners.OnLocationSetListner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FindAJob extends AppCompatActivity implements OnLocationSetListner, OnCurrentLocationFoundListner, PopupMenu.OnMenuItemClickListener {
    TextView toolbarTitle;
    Toolbar toolbar;
    Button btnSubmit;
    EditText keywordEdit;
    EditText locationEdit;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    Context mContext;
    String location, keyword, indusrty;
    AppController mAppController;
    ArrayList<Job> jobList = new ArrayList<>();
    GoogleMap map1;
    GeoCoderVolley mGeoCodeHelper;
    MarkerOptions markerOptions;
    OnCurrentLocationFoundListner foundListner;
    LatLng latLng;

    String addressText;
    RadioGroup radioGroup;
    TextView viewProfile;
    TextView findJob;
    TextView notifications, myJobs;
    ImageView dropdown;
    RadioGroup grp2;
    boolean isChecking = true;
    int mCheckedId;
    TextView awardedJobs, appliedJobs;
    RadioButton retailBtn, fbBtn, fmcgBtn, othersBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ajob);
        toolbar = (Toolbar) findViewById(R.id.toolbar_findjob);
        locationEdit = (EditText) findViewById(R.id.location_input);
        keywordEdit = (EditText) findViewById(R.id.keyword_input);
        toolbarTitle = (TextView) findViewById(R.id.TitleToolbar);
        toolbarTitle.setText("Find a Job");
        radioGroup = (RadioGroup) findViewById(R.id.industry_choices_find_grp1);
        btnSubmit = (Button) findViewById(R.id.submit_findjob);
        findJob = (TextView) findViewById(R.id.menu_1);
        viewProfile = (TextView) findViewById(R.id.menu_2);
        notifications = (TextView) findViewById(R.id.notifications);
        awardedJobs = (TextView) findViewById(R.id.awarded_jobs);
        appliedJobs = (TextView) findViewById(R.id.applied_jobs);
        grp2 = (RadioGroup) findViewById(R.id.industry_choices_find_grp2);
        retailBtn = (RadioButton) findViewById(R.id.retail);
        fbBtn = (RadioButton) findViewById(R.id.fb);
        fmcgBtn = (RadioButton) findViewById(R.id.fmcg);
        othersBtn = (RadioButton) findViewById(R.id.other);

        mContext = this;
        foundListner = this;
        mAppController = (AppController) mContext.getApplicationContext();

        dropdown = (ImageView) findViewById(R.id.dropdown_click);
        dropdown.setVisibility(View.VISIBLE);
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(FindAJob.this);
                popupMenu.inflate(R.menu.menu);
                popupMenu.getMenu().getItem(0).setTitle("Credits : "+mAppController.credits);
                popupMenu.show();
            }
        });
        indusrty="Others";
        keyword="";
        location="";
        jsonHandling();
        findJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =new Intent(FindAJob.this,PostJob.class);
//                startActivity(intent);
            }
        });
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindAJob.this, ViewProfileJob.class);
                startActivity(intent);
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindAJob.this, Notifications.class);
                startActivity(intent);
            }
        });
        awardedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindAJob.this, MyJobs.class);
                startActivity(intent);
            }
        });
        appliedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindAJob.this, AppliedJobs.class);
                startActivity(intent);
            }
        });
       /* radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
             *//*   if (mCheckedId != -1 && isChecking) {
                    isChecking = false;
                    grp2.clearCheck();
                    mCheckedId = checkedId;
                  *//**//*  if(mCheckedId!= -1) {
                        RadioButton b = (RadioButton) group.findViewById(mCheckedId);
                        b.setTextColor(getResources().getColor(R.color.black));

                    }*//**//*
                }*//*
                mCheckedId=0;
                mCheckedId=checkedId;
                isChecking = true;
              show();

            }
        });
        grp2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
             mCheckedId=0;
                mCheckedId=checkedId;
                show();
            }
        });*/
        if (map1 == null) {
            map1 = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map_find_job)).getMap();

            // check if map is created successfully or not
            if (map1 == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = locationEdit.getText().toString();
                keyword = keywordEdit.getText().toString();
                Intent intent=new Intent(FindAJob.this,SearchResultJobs.class);
                intent.putExtra("keyword",keyword);
                intent.putExtra("location",location);
                intent.putExtra("industry",indusrty);
                startActivity(intent);
                //jsonHandling();
               /* if (!location.isEmpty() && !keyword.isEmpty() && !indusrty.isEmpty()) {
                    jsonHandling();
                } else {
                    Toast.makeText(mContext, "Please fill the feilds!", Toast.LENGTH_SHORT).show();
                }*/

            }
        });
    }

    public void show(View v) {

        mCheckedId = v.getId();
        if (mCheckedId == retailBtn.getId()) {
            retailBtn.setChecked(true);
            fmcgBtn.setChecked(false);
            fbBtn.setChecked(false);
            othersBtn.setChecked(false);

            retailBtn.setTextColor(getResources().getColor(R.color.black));
            fmcgBtn.setTextColor(getResources().getColor(R.color.darker_gray_box));
            othersBtn.setTextColor(getResources().getColor(R.color.darker_gray_box));
            fbBtn.setTextColor(getResources().getColor(R.color.darker_gray_box));
            indusrty = "Retail";
        } else if (mCheckedId == fbBtn.getId()) {
            fbBtn.setChecked(true);
            fmcgBtn.setChecked(false);
            retailBtn.setChecked(false);
            othersBtn.setChecked(false);

            fbBtn.setTextColor(getResources().getColor(R.color.black));
            fmcgBtn.setTextColor(getResources().getColor(R.color.darker_gray_box));
            othersBtn.setTextColor(getResources().getColor(R.color.darker_gray_box));
            retailBtn.setTextColor(getResources().getColor(R.color.darker_gray_box));
            indusrty = "F&B";

        } else if (mCheckedId == R.id.fmcg) {
            indusrty = "FMCG";
            fmcgBtn.setChecked(true);

            retailBtn.setChecked(false);
            fbBtn.setChecked(false);
            othersBtn.setChecked(false);

            fmcgBtn.setTextColor(getResources().getColor(R.color.black));
            fbBtn.setTextColor(getResources().getColor(R.color.darker_gray_box));
            othersBtn.setTextColor(getResources().getColor(R.color.darker_gray_box));
            retailBtn.setTextColor(getResources().getColor(R.color.darker_gray_box));

        } else {
            othersBtn.setChecked(true);

            fmcgBtn.setChecked(false);
            fbBtn.setChecked(false);
            retailBtn.setChecked(false);

            othersBtn.setTextColor(getResources().getColor(R.color.black));
            fmcgBtn.setTextColor(getResources().getColor(R.color.darker_gray_box));
            fbBtn.setTextColor(getResources().getColor(R.color.darker_gray_box));
            retailBtn.setTextColor(getResources().getColor(R.color.darker_gray_box));

            indusrty = "Others";
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    public void jsonHandling() {
        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.show();
        jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                HeaderRequestUrl.base_url + "?function=joblisting&action=post&user_id=75&page=1&per_page=5&search_term=" + keyword + "&location=" + location + "&industry=" + indusrty,
                null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ssf", response.toString());
                        try {
                            int num;
                            JSONArray noofJob = response.getJSONArray("jobs");
                            num = noofJob.length();
                            int i = 0;
                            map1.clear();
                            jobList.clear();
                            if (num == 0) {
                                Toast.makeText(mContext, "No job found!", Toast.LENGTH_SHORT).show();
                            } else {
                                for (i = 0; i < noofJob.length(); i++)

                                {
                                    Employer employer = new Employer();
                                    Job_ jobModel = new Job_();
                                    Job jobListModel = new Job();
                                    JSONObject obj = response.getJSONArray("jobs").getJSONObject(i).getJSONObject("employer");
                                    employer.setCompanyName(obj.getString("company_name"));
                                    employer.setContactName(obj.getString("contact_name"));
                                    employer.setContactNumber(obj.getString("contact_number"));
                                    employer.setIndustry(obj.getString("industry"));
                                    employer.setCompanyLogo(obj.getString("company_logo"));
                                    employer.setAddress(obj.getString("address"));
                                    employer.setLocation(obj.getString("location"));
                                    JSONObject jobObj = response.getJSONArray("jobs").getJSONObject(i).getJSONObject("job");
                                    jobModel.setJobId(jobObj.getString("job_id"));
                                    jobModel.setJobTitle(jobObj.getString("job_title"));
                                    jobModel.setDescription(jobObj.getString("description"));
                                    jobModel.setIndustry(jobObj.getString("industry"));
                                    jobModel.setNoOfCandidates(jobObj.getString("no_of_candidates"));
                                    jobModel.setDateWork(jobObj.getString("date_work"));
                                    jobModel.setTimeWork(jobObj.getString("time_work"));
                                    jobModel.setDuration(jobObj.getString("duration"));
                                    jobModel.setRatePerHour(jobObj.getString("rate_per_hour"));
                                    jobModel.setJobLocation(jobObj.getString("job_location"));
                                    jobModel.setJobPostingDate(jobObj.getString("job_posting_date"));
                                    jobModel.setJobListingExpDate(jobObj.getString("job_listing_exp_date"));
                                    jobModel.setPhysicalDisableAllow(jobObj.getString("physical_disable_allow"));
                                    jobModel.setNonPhysicalDisableAllow(jobObj.getString("non_physical_disable_allow"));
                                    jobModel.setIsApply(jobObj.getString("isApply"));
                                    jobListModel.setEmployer(employer);
                                    jobListModel.setJob(jobModel);
                                    jobList.add(jobListModel);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /* if (statusObj.contentEquals("failed")) {
                            Toast.makeText(mContext, errorObj, Toast.LENGTH_SHORT).show();
                        }*/

                        Log.d(tag_json_obj, response.toString());
                        pDialog.hide();

                        MappingJobs();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(tag_json_obj, "Error: " + error.getMessage());
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });


        //  Volley.newRequestQueue(mContext).add(jsonObjReq);
        mAppController.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    public void MappingJobs() {
        for (int i = 0; i < jobList.size(); i++) {
            addressText = jobList.get(i).getJob().getJobLocation();
        /*    mGeoCodeHelper = new GeoCoderVolley(mContext);
            mGeoCodeHelper.setListener(foundListner);
            mGeoCodeHelper.fetchCoordinatesFromAddress(mContext, g);
            // latLng = new LatLng(latd, lng);
            markerOptions = new MarkerOptions();
            if (addressText.contains(null)) {
                Toast.makeText(mContext, "Please enter city name too!", Toast.LENGTH_SHORT).show();
            } else {

                markerOptions.position(latLng);
                markerOptions.title(addressText);

                map1.clear();
                map1.addMarker(markerOptions);
                map1.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map1.animateCamera(CameraUpdateFactory.zoomTo(15));*/
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input
                // text
                addresses = geocoder.getFromLocationName(addressText, 3);
                if (addresses != null && !addresses.equals(""))
                    search(addresses);

            } catch (Exception e) {

            }

        }
    }

    protected void search(List<Address> addresses) {

        Address address = (Address) addresses.get(0);

        latLng = new LatLng(address.getLatitude(), address.getLongitude());

        addressText = String.format(
                "%s, %s",
                address.getMaxAddressLineIndex() > 0 ? address
                        .getAddressLine(0) : address.getLocality(), address.getCountryName());

        markerOptions = new MarkerOptions();

        // markerOptions=null;
        markerOptions.position(latLng);
        markerOptions.title(addressText);


        map1.addMarker(markerOptions);
        map1.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map1.animateCamera(CameraUpdateFactory.zoomTo(5));
        // locationTv.setText("Latitude:" + address.getLatitude() + ", Longitude:"
        //    + address.getLongitude());


    }

    @Override
    public void onCurrentLocationFoundListner(String address, String code, double latitude, double longitude, String Timezone) {
        addressText = address;
        latLng = new LatLng(latitude, longitude);
    }

    @Override
    public void onLocationSet(String cityName, String Country, double latitude, double longitude, String timeZone) {

    }

    @Override
    public void onNewLocationDetected(String newCityName, String oldCityName, double latitude, double longitude) {

    }


    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            /*    case R.id.credit:
                    Toast.makeText(this, "Credit Clicked", Toast.LENGTH_SHORT).show();
                    return true;*/
            case R.id.logout:
                Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, Login.class);
                startActivity(intent);
                finish();
                return true;

        }
        return true;
    }
}

