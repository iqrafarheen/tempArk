package com.example.bilal.arksolutions_jomwork;

import android.app.ProgressDialog;
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
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.GeoCoderVolley;
import com.example.bilal.arksolutions_jomwork.Helper.UserLocation;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.JobExample;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.PizzaRetrofitInterface;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.UserInfo;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.UserInfoExample;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.employerExample;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.employerInfo;
import com.example.bilal.arksolutions_jomwork.adapter.JobsListAdapter;
import com.example.bilal.arksolutions_jomwork.listeners.OnCurrentLocationFoundListner;
import com.example.bilal.arksolutions_jomwork.listeners.OnLocationSetListner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ViewProfile extends AppCompatActivity implements OnLocationSetListner, OnCurrentLocationFoundListner, PopupMenu.OnMenuItemClickListener {
    TextView toolbarTitle;
    Toolbar toolbar;
    ImageView searchIcon;
    MarkerOptions markerOptions;
    String pictureUrl;
    OnCurrentLocationFoundListner foundListner;
    String email, name, birthday, location;

    LatLng latLng;
    Double home_long;
    Double home_lat;
    String addressText;
    GoogleMap map1;
    GeoCoderVolley mGeoCodeHelper;
    Context mContext;
    AutoCompleteTextView searchAuto;
    public static double latd;
    public static double lng;
    public static String[] autoDataList;
    TextView nameEdittext, locationEdittext;
    EditText birthdayEdittext;
    OnCurrentLocationFoundListner listner;
    ImageView dp;
    TextView editProfile;
    ImageView dropdown;
    String gender;
    TextView awardedJobs, appliedJobs;
    TextView viewProfile;
    TextView findJob;
    Location defaultLocation;
    TextView notifications, myJobs;
    TextView editOption;
    AppController mAppController;
    TextView companyName;
    TextView mobNo, industryInterest;
    UserLocation userLocation;
    String city;
    Float ratings=0.0f;
    String reviews;
    RatingBar ratingBar;
    TextView reviewsView;
    TextView ratingView;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        mContext = this;
        listner = this;
        mAppController = (AppController) getApplicationContext();

        defaultLocation = new Location("");//provider name is unecessary
        defaultLocation.setLatitude(0.0d);//your lat long
        defaultLocation.setLongitude(0.0d);
        userLocation = new UserLocation(mContext);
        foundListner = this;
      /*  userLocation.setOnLocationSetListner(this);
        userLocation.checkLocation(false, true);*/
        toolbarTitle = (TextView) findViewById(R.id.TitleToolbar);
        searchIcon = (ImageView) findViewById(R.id.search_add_view);
        searchAuto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        nameEdittext = (TextView) findViewById(R.id.profile_name);
        editProfile = (TextView) findViewById(R.id.edit_profile_option);
        mobNo = (TextView) findViewById(R.id.mob_value);
        companyName = (TextView) findViewById(R.id.comp_value);
        industryInterest = (TextView) findViewById(R.id.mob_value);
        ratingView=(TextView)findViewById(R.id.rate_value);
        reviewsView=(TextView)findViewById(R.id.reviewnums);
        ratingBar=(RatingBar)findViewById(R.id.ratingbar);

        // emailEdittext = (EditText) findViewById(R.id.name_input_profile);
        //birthdayEdittext = (EditText) findViewById(R.id.company_input_profile);
        locationEdittext = (TextView) findViewById(R.id.add_value);
        editOption = (TextView) findViewById(R.id.edit_profile_option);
        dp = (ImageView) findViewById(R.id.dp);

        toolbarTitle.setText("View Profile");
        progressDialog = new ProgressDialog(mContext);
        progressDialog.show();
        doPizzaParsing();


        dropdown = (ImageView) findViewById(R.id.dropdown_click);
        dropdown.setVisibility(View.VISIBLE);

        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(ViewProfile.this);
                popupMenu.inflate(R.menu.menu);
                popupMenu.show();
            }
        });
        if (map1 == null) {
            map1 = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map_view_profile)).getMap();

            // check if map is created successfully or not
            if (map1 == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        findJob = (TextView) findViewById(R.id.menu_1);
        viewProfile = (TextView) findViewById(R.id.menu_2);
        notifications = (TextView) findViewById(R.id.notifications);
        awardedJobs = (TextView) findViewById(R.id.awarded_jobs);
        appliedJobs = (TextView) findViewById(R.id.posted_jobs);

        findJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfile.this, PostJob.class);
                startActivity(intent);
                finish();
            }
        });
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfile.this, ViewProfile.class);
                startActivity(intent);

            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfile.this, Notifications.class);
                startActivity(intent);
                finish();
            }
        });
        awardedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AwardedEmployerJobs.class);
                startActivity(intent);
            }
        });
        appliedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostedJobs.class);
                startActivity(intent);
            }
        });
        editOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditProfile.class);
                startActivity(intent);

            }
        });

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
        // bottomNavigationView.getMenu().getItem(2).setChecked(true);
       /* searchIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String g = searchAuto.getText().toString();
                mGeoCodeHelper = new GeoCoderVolley(mContext);
                mGeoCodeHelper.fetchCoordinatesFromAddress(mContext, g);
                latLng = new LatLng(latd, lng);
                markerOptions = new MarkerOptions();


                markerOptions.position(latLng);
                markerOptions.title(addressText);

                map1.clear();
                map1.addMarker(markerOptions);
                map1.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map1.animateCamera(CameraUpdateFactory.zoomTo(15));
               *//* Geocoder geocoder = new Geocoder(getBaseContext());
                List<Address> addresses = null;

                try {
                    // Getting a maximum of 3 Address that matches the input
                    // text
                    addresses = geocoder.getFromLocationName(g, 3);
                    if (addresses != null && !addresses.equals(""))
                        search(addresses);

                } catch (Exception e) {

                }
*//*
            }
        });
    }

    public void LoadLocationSuggestions(String s) {
        mGeoCodeHelper = new GeoCoderVolley(mContext);
        mGeoCodeHelper.fetchCoordinatesFromAddress(mContext, s);
       *//* ArrayAdapter<String> Adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,autoDataList);
        searchAuto.setThreshold(1);
        searchAuto.setAdapter(Adapter);*//*

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


        markerOptions.position(latLng);
        markerOptions.title(addressText);

        map1.clear();
        map1.addMarker(markerOptions);
        map1.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map1.animateCamera(CameraUpdateFactory.zoomTo(15));
        // locationTv.setText("Latitude:" + address.getLatitude() + ", Longitude:"
        //    + address.getLongitude());


    }*/

        listner = this;
        mContext = this;
        if (map1 == null) {
            map1 = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map_edit_profile)).getMap();

            // check if map is created successfully or not
            if (map1 == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
     /*   mGeoCodeHelper = new GeoCoderVolley(mContext);
        mGeoCodeHelper.setListener(this);
        Intent dataIntent = getIntent();*/

        mobNo.setText(mAppController.mUser.getMobileNumber());
        nameEdittext.setText(mAppController.mUser.getName());
        locationEdittext.setText(mAppController.mUser.getLocation());

        // pictureUrl=mAppController.mUser.getUserAvatar().toString();

        /*name = dataIntent.getStringExtra("Name");
        location = dataIntent.getStringExtra("Location");
        birthday = dataIntent.getStringExtra("Birthday");
        pictureUrl = dataIntent.getStringExtra("Picture");*/
        Glide.with(ViewProfile.this).load(mAppController.mUser.getUserAvatar()).skipMemoryCache(true).into(dp);
        addressText = mAppController.mUser.getLocation();
        search();
        // emailEdittext.setText(email);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfile.this, EditProfile.class);
                intent.putExtra("Name", name);
                intent.putExtra("Birthday", birthday);
                intent.putExtra("Email", email);
                intent.putExtra("Location", location);
                intent.putExtra("Picture", pictureUrl);
                startActivity(intent);
                finish();

            }
        });
        searchIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                addressText = searchAuto.getText().toString();
//                mGeoCodeHelper = new GeoCoderVolley(mContext);
//                mGeoCodeHelper.setListener(foundListner);
//                mGeoCodeHelper.fetchCoordinatesFromAddress(mContext, addressText);
//                // latLng = new LatLng(latd, lng);
//                markerOptions = new MarkerOptions();
//                if (addressText.contains(null)) {
//                    Toast.makeText(mContext, "Please enter city name too!", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    markerOptions.position(latLng);
//                    markerOptions.title(addressText);
//
//                    map1.clear();
//                    map1.addMarker(markerOptions);
//                    map1.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                    map1.animateCamera(CameraUpdateFactory.zoomTo(15));
//                    Geocoder geocoder = new Geocoder(getBaseContext());
                search();

                // }
            }
        });



        // birthdayEdittext.setText(birthday);

/*
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfile.this, EditProfileJob.class);
                startActivity(intent);
            }
        });*/

    }


    @Override
    public void onCurrentLocationFoundListner(String address, String code, double latitude, double longitude, String Timezone) {

        city = address;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:
                Intent intent = new Intent(ViewProfile.this, Login.class);
                startActivity(intent);
                finish();
                return true;
        }
        return true;
    }

    protected void search() {

        Geocoder geocoder = new Geocoder(getBaseContext());
        List<Address> addresses = null;


        // Getting a maximum of 3 Address that matches the input
        // text
        try {
            addresses = geocoder.getFromLocationName(addressText, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && !addresses.equals(""))

        {


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
        } else {
            Toast.makeText(mContext, "Please enter complete address!", Toast.LENGTH_SHORT).show();
        }
        // locationTv.setText("Latitude:" + address.getLatitude() + ", Longitude:"
        //    + address.getLongitude());


    }

    @Override
    public void onLocationSet(String cityName, String Country, double latitude, double longitude, String timeZone) {
        latLng = new LatLng(latitude, longitude);
        markerOptions = new MarkerOptions();
        defaultLocation.setLatitude(latitude);
        defaultLocation.setLongitude(longitude);
        mGeoCodeHelper = new GeoCoderVolley(mContext);
        mGeoCodeHelper.setListener(foundListner);
        mGeoCodeHelper.fetchAddressFromCoordinates(mContext, defaultLocation);
        //markerOptions=null;
        markerOptions.position(latLng);
        markerOptions.title(city);

        map1.clear();
        map1.addMarker(markerOptions);
        map1.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map1.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onNewLocationDetected(String newCityName, String oldCityName, double latitude, double longitude) {

    }
    private void doPizzaParsing() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://jomwork.arksols.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        PizzaRetrofitInterface api = retrofit.create(PizzaRetrofitInterface.class);
        Call<employerExample> call = api.employerInfo("employerinfo", "post", mAppController.mUser.getId());
        call.enqueue(new Callback<employerExample>() {
            @Override
            public void onResponse(retrofit.Response<employerExample> response, Retrofit retrofit) {
                Log.e("ppppppppppppppppppp", "" + response);
                if(response.body().getStatus().contentEquals("success")) {
                    employerExample b = response.body();
                    employerInfo info=b.getInfo();
                    ratings=Float.parseFloat(info.getRating());
                    reviews=info.getReview();
                    ratingBar.setIsIndicator(true);
                    ratingBar.setRating(ratings);
                    reviewsView.setText(reviews+" Reviews");
                    ratingView.setText(ratings.toString());
                    progressDialog.hide();


                }

            }

            @Override
            public void onFailure(Throwable t) {

                Log.e("FAILEDDDDDDDDDDD", "TRUE");
            }
        });


    }

}
