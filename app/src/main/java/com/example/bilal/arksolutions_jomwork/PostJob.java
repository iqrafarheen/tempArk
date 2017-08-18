package com.example.bilal.arksolutions_jomwork;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.GeoCoderVolley;
import com.example.bilal.arksolutions_jomwork.Helper.HeaderRequestUrl;
import com.example.bilal.arksolutions_jomwork.Helper.UserLocation;
import com.example.bilal.arksolutions_jomwork.listeners.OnCurrentLocationFoundListner;
import com.example.bilal.arksolutions_jomwork.listeners.OnLocationSetListner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PostJob extends AppCompatActivity implements OnCurrentLocationFoundListner, OnLocationSetListner, PopupMenu.OnMenuItemClickListener {
    EditText datefworkEdit;
    TextView toolbarTitle;
    Toolbar toolbar;
    Button nextbtn;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String industryInterest, vacancy, dateofWork, duration, rmHr;
    EditText duratnEdittext, rmHourEdittext;
    Spinner industryChoices, vacancySpinner, jobtypeSpinner;
    String jobType;
    String minutesCal;
    EditText jobDescriptionEdit;
    String jobDescription;
    String time, timeUrl;
    EditText timeEdit;
    String jobTitleUrl, jobDescriptionUrl;

    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    Context mContext;
    String location, keyword, indusrty;
    AppController mAppController;

    GoogleMap map1;
    GeoCoderVolley mGeoCodeHelper;

    MarkerOptions markerOptions;
    Location defaultLocation;
    UserLocation userLocation;
    OnLocationSetListner listner;
    OnCurrentLocationFoundListner foundListner;

    ImageView dropdown;
    int hour, sec, min, amorpm;
    AutoCompleteTextView searchAuto;
    ImageView searchIcon;
    LatLng latLng;
    Double home_long;
    Double home_lat;
    String addressText;
    String city;
    TextView viewProfile;
    TextView findJob;
    TextView notifications, myJobs;
    EditText postingDate, expiryDate;
    CheckBox physicalDisable, nonPhysical;
    String phsical = "0";
    String nonphysical = "0";
    EditText titleEdittext;
    TextView awardedJobs, postedJobs;
    String title;
    String joblistingExpiry, jobPosting, jobtype;
    String cityUrl;
    Calendar plannedExpiryCalendar;
    Calendar urgentExpiryCalendar;
    TimePickerDialog timePickerDialog;
    Calendar tempCal;
    TextView expiryTitle;
    String getJoblistingExpiryUrl;
    EditText vacancyEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        toolbar = (Toolbar) findViewById(R.id.toolbar_postjob);
        toolbarTitle = (TextView) findViewById(R.id.TitleToolbar);
        toolbarTitle.setText("Post A Job");
        datefworkEdit = (EditText) findViewById(R.id.date_input_postjob);
        postingDate = (EditText) findViewById(R.id.posting_input_postjob);
        expiryDate = (EditText) findViewById(R.id.expiry_input_postjob);
        industryChoices = (Spinner) findViewById(R.id.industry_post);
        physicalDisable = (CheckBox) findViewById(R.id.chkdisable_view);
        titleEdittext = (EditText) findViewById(R.id.title_input_postjob);
        expiryTitle = (TextView) findViewById(R.id.expiryTitle);
        nonPhysical = (CheckBox) findViewById(R.id.chknondisable_view);
        vacancyEdit=(EditText)findViewById(R.id.vacancy_input);
       // vacancySpinner = (Spinner) findViewById(R.id.vacancy_spinner_emoloyee);
        jobtypeSpinner = (Spinner) findViewById(R.id.jobtype_spinner_post);
        jobDescriptionEdit = (EditText) findViewById(R.id.description_input_postjob);
        duratnEdittext = (EditText) findViewById(R.id.duration_input_postjob);
        rmHourEdittext = (EditText) findViewById(R.id.rate_input_postjob);
        searchAuto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        searchIcon = (ImageView) findViewById(R.id.search_add_view);
        findJob = (TextView) findViewById(R.id.menu_1);
        viewProfile = (TextView) findViewById(R.id.menu_2);
        notifications = (TextView) findViewById(R.id.notifications);
        timeEdit = (EditText) findViewById(R.id.time_input_postjob);

        awardedJobs = (TextView) findViewById(R.id.awarded_jobs);
        postedJobs = (TextView) findViewById(R.id.posted_jobs);
        nextbtn = (Button) findViewById(R.id.submit_postjob);
        mContext = this;
        foundListner = this;
        mAppController = (AppController) mContext.getApplicationContext();
        expiryDate.setVisibility(View.GONE);
        expiryTitle.setVisibility(View.GONE);


        dropdown = (ImageView) findViewById(R.id.dropdown_click);
        dropdown.setVisibility(View.VISIBLE);
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(PostJob.this);
                popupMenu.inflate(R.menu.menu);
                popupMenu.getMenu().getItem(0).setTitle("Credits : " + mAppController.credits);
                popupMenu.show();
            }
        });
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
                Intent intent = new Intent(mContext, ViewProfile.class);
                startActivity(intent);

            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Notifications.class);
                startActivity(intent);

            }
        });
        awardedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AwardedEmployerJobs.class);
                startActivity(intent);

            }
        });
        postedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostedJobs.class);
                startActivity(intent);

            }
        });
        userLocation = new UserLocation(mContext);
        userLocation.setOnLocationSetListner(this);
        userLocation.checkLocation(false, true);
        defaultLocation = new Location("");//provider name is unecessary
        defaultLocation.setLatitude(0.0d);//your lat long
        defaultLocation.setLongitude(0.0d);
        physicalDisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    phsical = 1 + "";
                } else {
                    phsical = 0 + "";
                }
            }
        });
        nonPhysical.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nonphysical = 1 + "";
                } else {
                    nonphysical = 0 + "";
                }
            }
        });

        if (map1 == null) {
            map1 = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map_post)).getMap();

            // check if map is created successfully or not
            if (map1 == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        searchAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                // LoadLocationSuggestions(s.toString());
            }
        });
        searchIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                addressText = searchAuto.getText().toString();
                city = searchAuto.getText().toString();
       /*         mGeoCodeHelper = new GeoCoderVolley(mContext);
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
                    e.printStackTrace();

                }

            }

        });


      /*  vacancySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vacancy = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vacancy = parent.getItemAtPosition(0).toString();
            }
        });*/
        industryChoices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                industryInterest = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                industryInterest = parent.getItemAtPosition(0).toString();
            }
        });
        jobtypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jobtype = parent.getItemAtPosition(position).toString();
               /* if (jobtype.contentEquals("Urgent")) {
                    expiryDate.setVisibility(View.GONE);
                    expiryTitle.setVisibility(View.GONE);
                } else {
                    expiryDate.setVisibility(View.VISIBLE);
                    expiryTitle.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                jobtype = parent.getItemAtPosition(0).toString();
            }
        });
        datefworkEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PostJob.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateofWork = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                datefworkEdit.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        expiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PostJob.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                joblistingExpiry = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                expiryDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        postingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PostJob.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                jobPosting = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                postingDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        timeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                hour = c.get(Calendar.HOUR);
                min = c.get(Calendar.MINUTE);
                sec = c.get(Calendar.SECOND);
                amorpm = c.get(Calendar.AM_PM);


                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (String.valueOf(minute).length() == 1) {
                            minutesCal = "0" + String.valueOf(minute);
                        } else {
                            minutesCal = String.valueOf(minute);
                        }
                        if (hourOfDay > 12) {
                            time = String.valueOf(hourOfDay - 12) + ":" + minutesCal + " pm";
                            timeEdit.setText(time);
                        }
                        if (hourOfDay == 12) {
                            time = "12" + ":" + minutesCal + " pm";

                            timeEdit.setText(time);
                        }
                        if (hourOfDay < 12) {
                            if (hourOfDay == 0) {
                                hourOfDay = 00;
                                time = "00" + ":" + minutesCal + " am";
                            } else {
                                time = String.valueOf(hourOfDay) + ":" + minutesCal + " am";
                            }

                            timeEdit.setText(time);
                        }

                    }
                }, hour, min, false);

                timePickerDialog.show();
            }
        });


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleEdittext.getText().toString();
                rmHr = rmHourEdittext.getText().toString();
                duration = duratnEdittext.getText().toString();
                jobDescription = jobDescriptionEdit.getText().toString();
                vacancy=vacancyEdit.getText().toString();
                /*if(!city.isEmpty()) {
                    city = searchAuto.getText().toString();
                }else {
                    city=addressText;
                }*/
                city = searchAuto.getText().toString();
                try {
                    if (jobtype != null && vacancy != null && title != null && rmHr != null && duration != null && jobDescription != null && jobPosting != null && dateofWork != null) {
                      /*  mAppController.credits();
                        if(Integer.parseInt(mAppController.credits )>= Integer.parseInt( vacancy)) {
                            jsonHandling();
                        }else {
                            Toast.makeText(mContext,"No sufficient credits ! ",Toast.LENGTH_LONG).show();
                        }*/
                        if (jobtype.contentEquals("Planned")) {
                            plannedExpiryCalendar = Calendar.getInstance();
                          /*  Date expDate = ConvertToDate(dateofWork);
                            plannedExpiryCalendar.setTime(expDate);*/
                            tempCal = Calendar.getInstance();
                            Date datetowork = ConvertToDateTime(dateofWork,time);
                            tempCal.setTime(datetowork);
                            tempCal.add(Calendar.DAY_OF_YEAR, -3);
                            ConvertDatetoString();

                            jsonHandling();
                          /*  if (plannedExpiryCalendar.getTime().equals(tempCal.getTime())) {
                                mAppController.AddCredits(Integer.parseInt(vacancy));
                                jsonHandling();
                            } else {
                                Toast.makeText(mContext, "Enter valid expiry date !", Toast.LENGTH_LONG).show();
                            }*/
                        } else
                            {
                            urgentExpiryCalendar = Calendar.getInstance();
                            Date newDate = ConvertToDateTime(dateofWork, time);
                            tempCal = Calendar.getInstance();
                            tempCal.setTime(newDate);
                            tempCal.add(Calendar.HOUR, -3);
                                ConvertDatetoString();
                            mAppController.AddCredits(Integer.parseInt(vacancy));

                            jsonHandling();
                        }


                    }
                } catch (NullPointerException e) {
                    Toast.makeText(mContext, "Please Enter All  Fields !!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Date ConvertToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy ");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
            joblistingExpiry=dateFormat.format(convertedDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }
public  void ConvertDatetoString(){
if(jobtype.contentEquals("Planned")) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy ");
    Date newDate = tempCal.getTime();
    joblistingExpiry = dateFormat.format(newDate);
}else {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    Date newDate = tempCal.getTime();
    joblistingExpiry = dateFormat.format(newDate);
}

}
    private Date ConvertToDateTime(String dateString, String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        String dateTime = dateString + " " + time;

        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateTime);
            joblistingExpiry = dateFormat.format(convertedDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }


    public void jsonHandling() {
      /*  mAppController.AddCredits(20);
        */

        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.show();

        try {
            cityUrl = URLEncoder.encode(city, "UTF-8");
            timeUrl = URLEncoder.encode(time, "UTF-8");
            jobTitleUrl = URLEncoder.encode(title, "UTF-8");
            getJoblistingExpiryUrl = URLEncoder.encode(joblistingExpiry, "UTF-8");

            jobDescriptionUrl = URLEncoder.encode(jobDescription, "UTF-8");
            Log.d("TEST", cityUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = " http://jomwork.arksols.com/webservices/service.php?function=employeraddjob&action=post&job_title=" + jobTitleUrl + "&description=" + jobDescriptionUrl + "&industry=" + industryInterest + "&no_of_candidates=" + vacancy + "&date_work=" + dateofWork + "&time_work=" + timeUrl + "&duration=" + duration + "&rate_per_hour=" + rmHr + "&job_location=" + cityUrl + "&job_posting_date=" + jobPosting + "&job_listing_exp_date=" + getJoblistingExpiryUrl + "&physical_disable_allow=" + phsical + "&non_physical_disable_allow=" + nonphysical + "&user_id=" + mAppController.mUser.getId() + "&job_status=1" + "&job_type=" + jobtype;
        jsonObjReq = new JsonObjectRequest(Request.Method.GET, url
               /* HeaderRequestUrl.base_url + "?function=employeraddjob&action=post&job_title=" + title + "&description=test&industry=" + industryInterest + "&no_of_candidates=" + vacancy + "&date_work=" + "2017-09-01" + "&time_work=09:00&duration=" + duration + "&rate_per_hour=" + rmHr + "&job_location=" + city + "&job_posting_date=" + jobPosting+ "&job_listing_exp_date=" + joblistingExpiry + "&physical_disable_allow=" + phsical + "&non_physical_disable_allow=" + nonphysical + "&user_id=75&job_status=1&job_type=1"*/,
                null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ssf", response.toString());
                        //Toast.makeText(mContext, response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            statusObj = (String) response.get("status");
                            errorObj = response.get("message").toString();
                            //resObj = response.get("res").toString();
                            // Toast.makeText(mContext, errorObj.toString(), Toast.LENGTH_LONG).show();
                           /* mAppController.SubtractCredits(Integer.parseInt(vacancy));
                            mAppController.credits();*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (statusObj.contentEquals("failed")) {
                         /*   Intent backIntent = new Intent(PostJob.this, SignUp.class);
                            startActivity(backIntent);*/
                        } else {

                            //  mAppController.credits();
                            Toast.makeText(mContext, "Job Added !", Toast.LENGTH_LONG).show();
                        }

                        Log.d(tag_json_obj, response.toString());
                        pDialog.hide();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(tag_json_obj, "Error: " + error.getMessage());
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.hide();
            }
        });


        //  Volley.newRequestQueue(mContext).add(jsonObjReq);
        mAppController.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    protected void search(List<Address> addresses) {

        Address address = (Address) addresses.get(0);
        home_long = address.getLongitude();
        home_lat = address.getLatitude();
        latLng = new LatLng(address.getLatitude(), address.getLongitude());

        addressText = String.format(
                "%s, %s",
                address.getMaxAddressLineIndex() > 0 ? address
                        .getAddressLine(0) : address.getLocality(), address.getCountryName());

        markerOptions = new MarkerOptions();

        // markerOptions=null;
        markerOptions.position(latLng);
        markerOptions.title(addressText);

        map1.clear();

        map1.addMarker(markerOptions);
        map1.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map1.animateCamera(CameraUpdateFactory.zoomTo(15));
        // locationTv.setText("Latitude:" + address.getLatitude() + ", Longitude:"
        //    + address.getLongitude());


    }


    @Override
    public void onCurrentLocationFoundListner(String address, String code, double latitude, double longitude, String Timezone) {
        addressText = address;
    }

    @Override
    public void onLocationSet(String cityName, String Country, double latitude, double longitude, String timeZone) {
        latLng = new LatLng(latitude, longitude);
        addressText = cityName;
        markerOptions = new MarkerOptions();
        defaultLocation.setLatitude(latitude);
        defaultLocation.setLongitude(longitude);
        mGeoCodeHelper = new GeoCoderVolley(mContext);
        mGeoCodeHelper.setListener(foundListner);
        mGeoCodeHelper.fetchAddressFromCoordinates(mContext, defaultLocation);
        //markerOptions=null;
        markerOptions.position(latLng);
        markerOptions.title(cityName);

        map1.clear();
        map1.addMarker(markerOptions);
        map1.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map1.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onNewLocationDetected(String newCityName, String oldCityName, double latitude, double longitude) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.credit:
                Toast.makeText(this, "Credit Clicked", Toast.LENGTH_SHORT).show();
                return true;
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

