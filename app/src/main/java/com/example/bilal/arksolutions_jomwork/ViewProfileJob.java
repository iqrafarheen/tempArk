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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.GeoCoderVolley;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.PizzaRetrofitInterface;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.UserInfo;
import com.example.bilal.arksolutions_jomwork.Helper.com.example.example_detail.UserInfoExample;
import com.example.bilal.arksolutions_jomwork.listeners.OnCurrentLocationFoundListner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ViewProfileJob extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    TextView toolbarTitle;
    Toolbar toolbar;
    ImageView searchIcon;
    MarkerOptions markerOptions;
    String pictureUrl;
    String email, name, birthday, location;
ProgressDialog progressDialog;
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
    EditText birthdayEdittext, emailEdittext;
    OnCurrentLocationFoundListner listner;
    Float ratings=0.0f;
    String reviews;
    RatingBar ratingBar;
    TextView reviewsView;
    TextView ratingView;
    ImageView dropdown;
    TextView gender, dob, mobno, industry;
    AppController mAppController;
    ImageView dp;
    TextView profileName;
    TextView viewProfile;
    TextView findJob;
    TextView notifications, myJobs;
    TextView editProfile;
    TextView awardedJobs, appliedJobs;
    CheckBox phsicalValue, nonPhysicalValue;
    private ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_job);
        toolbarTitle = (TextView) findViewById(R.id.TitleToolbar);
        toolbarTitle = (TextView) findViewById(R.id.TitleToolbar);
        searchIcon = (ImageView) findViewById(R.id.search_add_view);
        profileName = (TextView) findViewById(R.id.profile_name);
        searchAuto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        gender = (TextView) findViewById(R.id.comp_value);
        dp = (ImageView) findViewById(R.id.dp);
        dob = (TextView) findViewById(R.id.add_value);
        editProfile = (TextView) findViewById(R.id.edit_profile_option);
        mobno = (TextView) findViewById(R.id.mob_value);
        industry = (TextView) findViewById(R.id.interest_value);
        phsicalValue = (CheckBox) findViewById(R.id.chkdisable_view);
        nonPhysicalValue = (CheckBox) findViewById(R.id.chknondisable_view);
        mContext = this;
        toolbarTitle.setText("View Profile");

        findJob = (TextView) findViewById(R.id.menu_1);
        viewProfile = (TextView) findViewById(R.id.menu_2);
        notifications = (TextView) findViewById(R.id.notifications);
        ratingView=(TextView)findViewById(R.id.rate_value);
        reviewsView=(TextView)findViewById(R.id.reviewnums);
        ratingBar=(RatingBar)findViewById(R.id.ratingbar);


        mAppController = (AppController) getApplicationContext();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.show();
        doPizzaParsing();
        dropdown = (ImageView) findViewById(R.id.dropdown_click);
        awardedJobs = (TextView) findViewById(R.id.awarded_jobs);
        appliedJobs = (TextView) findViewById(R.id.applied_jobs);
        dropdown.setVisibility(View.VISIBLE);
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(ViewProfileJob.this);
                popupMenu.inflate(R.menu.menu);
                popupMenu.show();
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login.isSignup=false;
                Intent intent = new Intent(ViewProfileJob.this, EditProfileJob.class);
                startActivity(intent);
                finish();
            }
        });
        findJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileJob.this, FindAJob.class);
                startActivity(intent);
                finish();
            }
        });
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileJob.this, ViewProfileJob.class);
                startActivity(intent);
                finish();
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileJob.this, Notifications.class);
                startActivity(intent);
                finish();
            }
        });
        awardedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileJob.this, MyJobs.class);
                startActivity(intent);
                finish();
            }
        });
        appliedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileJob.this, MyJobs.class);
                startActivity(intent);
                finish();
            }
        });
        gender.setText(mAppController.mUser.getGender());
        dob.setText(mAppController.mUser.getDob());
        mobno.setText(mAppController.mUser.getMobileNumber());
        industry.setText(mAppController.mUser.getIndustryInterest());
      //  Glide.with(mContext).load(mAppController.mUser.getUserAvatar()).skipMemoryCache(true).into(dp);

        profileName.setText(mAppController.mUser.getName());
        //Initializing ImageLoader
     /*   imageLoader = mAppController.getmInstance().getImageLoader();
        imageLoader.get(mAppController.mUser.getUserAvatar().toString(), ImageLoader.getImageListener(dp, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        dp.setImageUrl(mAppController.mUser.getUserAvatar().toString(),imageLoader);*/
        Glide.with(ViewProfileJob.this).load(mAppController.mUser.getUserAvatar()).skipMemoryCache(true).into(dp);
        String physcialCheck = mAppController.mUser.getPhysicalDisable();
        if (physcialCheck.contentEquals("1")) {
            phsicalValue.setChecked(true);
        } else {
            phsicalValue.setChecked(false);
        }
        if (mAppController.mUser.getNonPhysicalDisable().contentEquals("1")) {
            nonPhysicalValue.setChecked(true);
        } else {
            nonPhysicalValue.setChecked(false);
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
        }
        else {
            Toast.makeText(mContext,"Please enter complete address!",Toast.LENGTH_SHORT).show();
        }
        // locationTv.setText("Latitude:" + address.getLatitude() + ", Longitude:"
        //    + address.getLongitude());


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
           /* case R.id.credit:
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

    private void doPizzaParsing() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://jomwork.arksols.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        PizzaRetrofitInterface api = retrofit.create(PizzaRetrofitInterface.class);
        Call<UserInfoExample> call = api.userInfo("candidateinfo", "post", mAppController.mUser.getId());
        call.enqueue(new Callback<UserInfoExample>() {
            @Override
            public void onResponse(retrofit.Response<UserInfoExample> response, Retrofit retrofit) {
                Log.e("ppppppppppppppppppp", "" + response);
                if(response.body().getStatus().contentEquals("success")) {
                    UserInfoExample b = response.body();
                    UserInfo info=b.getInfo();
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
