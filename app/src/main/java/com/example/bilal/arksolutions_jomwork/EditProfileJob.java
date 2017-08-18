package com.example.bilal.arksolutions_jomwork;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
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

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class EditProfileJob extends AppCompatActivity implements OnLocationSetListner, OnCurrentLocationFoundListner, PopupMenu.OnMenuItemClickListener {
    EditText dob;
    TextView toolbarTitle;
    Toolbar toolbar;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button nextbtn;
    String name, email, pasword, cpasword, gender, mobNo, dobFull, industryInterest;
    int yearFull, monthFull, dayofmonthFull;
    EditText nameEdit, genderEdit, mobEdit;
    Spinner genderSpinner;
    Spinner industrySpinner;
    int physicalDisable, nonPhysicalDisable;
    CheckBox physicalBox, nonPhysicalBox;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    StringRequest stringRequest;
    AppController mAppController;
    Context mContext;
    ImageView dp;
    public String url = "http://jomwork.arksols.com/webservices/service.php";
    private static final int SELECT_PICTURE = 100;
    Button picUpload;
    GoogleMap map1;
    GeoCoderVolley mGeoCodeHelper;

    MarkerOptions markerOptions;
    Location defaultLocation;
    UserLocation userLocation;
    OnLocationSetListner listner;
    OnCurrentLocationFoundListner foundListner;
    String city;
    ImageView dropdown;
    String function;
    AutoCompleteTextView searchAuto;
    ImageView searchIcon;
    String nameUrl, addressUrl;
    LatLng latLng;
    Double home_long;
    String image;
    Double home_lat;
    String addressText;
    Bitmap bitmap;

    public static Uri selectedImageUri;
    String str_facebookname, str_facebookemail, str_facebookimage, genderFb, str_location, str_birthda;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_job);
        mContext = this;
        mAppController = (AppController) mContext.getApplicationContext();
        foundListner = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar_profile_job);
        dob = (EditText) findViewById(R.id.dob_input_profile_job);
        nameEdit = (EditText) findViewById(R.id.name_input_profile_job);
        nextbtn = (Button) findViewById(R.id.btnnext_profile_jobloye);
        mobEdit = (EditText) findViewById(R.id.contact_input_profile_job);

        physicalBox = (CheckBox) findViewById(R.id.chkdisable);
        nonPhysicalBox = (CheckBox) findViewById(R.id.chknondisable);
        genderSpinner = (Spinner) findViewById(R.id.gender_spinner);
        industrySpinner = (Spinner) findViewById(R.id.industrySpinnerEmoloyee);
        toolbarTitle = (TextView) findViewById(R.id.TitleToolbar);
        searchIcon = (ImageView) findViewById(R.id.search_add_view);
        searchAuto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);


        dp = (ImageView) findViewById(R.id.iv_profile_job);

        setSupportActionBar(toolbar);
        if (Login.isSignup == true) {

            toolbarTitle.setText("Complete Profile");
        } else {
            toolbarTitle.setText("Edit Profile");


        }
        dropdown = (ImageView) findViewById(R.id.dropdown_click);
        dropdown.setVisibility(View.VISIBLE);

        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(EditProfileJob.this, v);
                popupMenu.setOnMenuItemClickListener(EditProfileJob.this);
                popupMenu.inflate(R.menu.menu);
                popupMenu.getMenu().getItem(0).setTitle("Credits : " + mAppController.credits);
                popupMenu.show();
            }
        });
//        toolbar.inflateMenu(R.menu.menu);
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
        picUpload = (Button) findViewById(R.id.upload_job);

        picUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
        Intent intent = getIntent();
        if (intent.getBundleExtra("signup_bundle") != null) {
            name = intent.getBundleExtra("signup_bundle").getString("name");
            email = intent.getBundleExtra("signup_bundle").getString("email");
            pasword = intent.getBundleExtra("signup_bundle").getString("pasword");
            cpasword = intent.getBundleExtra("signup_bundle").getString("cpasword");
            nameEdit.setText(name);

            userLocation = new UserLocation(mContext);
            userLocation.setOnLocationSetListner(this);
            userLocation.checkLocation(false, true);

        } else if (intent.getBundleExtra("fb_bundle") != null) {
            name = intent.getBundleExtra("fb_bundle").getString("Name");
            dobFull = intent.getBundleExtra("fb_bundle").getString("Birthday");
            email = (String) intent.getBundleExtra("fb_bundle").get("Email");
            addressText = intent.getBundleExtra("fb_bundle").getString("Location");
            str_facebookimage = intent.getBundleExtra("fb_bundle").getString("Picture");
            gender = intent.getBundleExtra("fb_bundle").getString("gender");
            // genderFb=intent.getStringExtra("Gender");
            nameEdit.setText(name);
            //genderEdit.setText(genderFb);
            dob.setText(dobFull);
            if (gender.contentEquals("Male"))

            {
                genderSpinner.setSelection(0);
            } else {
                genderSpinner.setSelection(1);
            }

            if (addressText != null && addressText != "") {
              //  searchAuto.setText(addressText);
                search();
            }
            Glide.with(EditProfileJob.this).load(str_facebookimage).skipMemoryCache(true).into(dp);

        } else {
            nameEdit.setText(mAppController.mUser.getName());
            dob.setText(mAppController.mUser.getDob());
            // genderEdit.setText(mAppController.mUser.getGender());
            mobEdit.setText(mAppController.mUser.getMobileNumber());
            gender = mAppController.mUser.getGender();
            if (gender.contentEquals("Male"))

            {
                genderSpinner.setSelection(0);
            } else {
                genderSpinner.setSelection(1);
            }
            if (mAppController.mUser.getPhysicalDisable().contentEquals("1")) {
                physicalBox.setChecked(true);
                physicalDisable = 1;
            } else {
                physicalBox.setChecked(false);
                physicalDisable = 0;
            }
            if (mAppController.mUser.getNonPhysicalDisable().contentEquals("1")) {
                nonPhysicalBox.setChecked(true);
                nonPhysicalDisable = 1;
            } else {
                nonPhysicalBox.setChecked(false);
                nonPhysicalDisable = 0;
            }
            Glide.with(EditProfileJob.this).load(mAppController.mUser.getUserAvatar()).skipMemoryCache(true).into(dp);
            addressText = mAppController.mUser.getAddress();

            if (addressText != null && addressText != "") {

              ///  searchAuto.setText(addressText);
                search();
            }


        }


        industrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                industryInterest = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



      /*  mGeoCodeHelper = new GeoCoderVolley(mContext);
        mGeoCodeHelper.setListener(this);*/
        physicalBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    physicalDisable = 1;
                } else {
                    physicalDisable = 0;
                }
            }
        });
        nonPhysicalBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nonPhysicalDisable = 1;
                } else {
                    nonPhysicalDisable = 0;
                }
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileJob.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                yearFull = year;
                                dayofmonthFull = dayOfMonth;
                                monthFull = monthOfYear;
                                dobFull = yearFull + "-" + monthFull + "-" + dayofmonthFull;

                                dob.setText(dobFull);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


// Adding request to request queue
        //  AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
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


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobNo = mobEdit.getText().toString();
                name = nameEdit.getText().toString();
                gender = genderSpinner.getSelectedItem().toString();
                dobFull = dob.getText().toString();
              //  addressText = searchAuto.getText().toString();
               /* if(dobFull.length()==0)
                {
                    Toast.makeText(mContext,"Please enter date of birth !",Toast.LENGTH_LONG).show();
                }*/

                try {
                    nameUrl = URLEncoder.encode(name, "UTF-8");
                    addressUrl = URLEncoder.encode(addressText, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                final ProgressDialog pDialog = new ProgressDialog(EditProfileJob.this);
                pDialog.setMessage("Loading...");
                if (Login.isSignup == true) {
                    function = "usersignup";

                    pDialog.show();
                    imageUploading();
                    /*
                    jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                            url + "?function=usersignup&action=post&email=" + email + "&password=" + pasword + "&cpassword=" + cpasword + "&name=" + nameUrl + "&dob=22-03-1994"  *//*+dobFull*//* + "&gender=" + gender + "&mobile_number=" + mobNo + "&industry_interest=" + industryInterest + "&uploadimg=123.png"  + "&physical_disable=" + String.valueOf(physicalDisable) + "&non_physical_disable=" + String.valueOf(nonPhysicalDisable)+"&address="+addressUrl,
                            null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {


                                    Toast.makeText(EditProfileJob.this, response.toString(), Toast.LENGTH_LONG).show();
                                    try {
                                        statusObj = (String) response.get("status");
                                        errorObj = response.get("error").toString();
                                        resObj = response.get("res").toString();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (statusObj.contentEquals("failed")) {
                                        Intent backIntent = new Intent(EditProfileJob.this, SignUp.class);
                                        startActivity(backIntent);
                                        finish();
                                    } else {*/
                    mAppController.mUser.setId("");
                    mAppController.mUser.setUsername(name);
                    mAppController.mUser.setEmail(email);
                    mAppController.mUser.setName(name);
                    mAppController.mUser.setContactName(name);
                    mAppController.mUser.setDob(dobFull);
                    mAppController.mUser.setMobileNumber(mobNo);
                    mAppController.mUser.setGender(gender);
                    mAppController.mUser.setIndustryInterest(industryInterest);
                    mAppController.mUser.setPhysicalDisable(physicalDisable + "");
                    mAppController.mUser.setUserAvatar(selectedImageUri);
                    mAppController.mUser.setAddress(addressText);
                    mAppController.mUser.setLocation(addressText);
                    mAppController.mUser.setNonPhysicalDisable(nonPhysicalDisable + "");
                    mAppController.mUser.setRoleId("2");


                    Intent backIntent = new Intent(EditProfileJob.this, FindAJob.class);
                    startActivity(backIntent);
                    finish();
                } else {
                    toolbarTitle.setText("Edit Profile");

                    pDialog.setMessage("Loading...");

                    pDialog.show();
                    function = "updateprofile";
                    if (mAppController.mUser.getUserAvatar() != null) {
                        UpdatingProfile();
                    }
                    mAppController.mUser.setUsername(name);
                    mAppController.mUser.setName(name);
                    mAppController.mUser.setContactName(name);
                    mAppController.mUser.setDob(dobFull);
                    mAppController.mUser.setMobileNumber(mobNo);
                    mAppController.mUser.setGender(gender);
                    mAppController.mUser.setIndustryInterest(industryInterest);
                    mAppController.mUser.setPhysicalDisable(physicalDisable + "");
                    if(selectedImageUri != null) {
                        mAppController.mUser.setUserAvatar(selectedImageUri);
                    }
                    mAppController.mUser.setAddress(addressText);
                    mAppController.mUser.setLocation(addressText);
                    mAppController.mUser.setNonPhysicalDisable(nonPhysicalDisable + "");
                    mAppController.mUser.setRoleId("2");

                    Intent backIntent = new Intent(EditProfileJob.this, FindAJob.class);
                    startActivity(backIntent);
                    finish();
                }
/*}
                                    }
                                    Log.d(tag_json_obj, response.toString());
                                    pDialog.hide();
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(tag_json_obj, "Error: " + error.getMessage());
                            Toast.makeText(EditProfileJob.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            pDialog.hide();
                        }
                    });
                    mAppController.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
                } /**/
                  /*  else {
                    toolbarTitle.setText("Edit Profile");
                    final ProgressDialog pDialog = new ProgressDialog(EditProfileJob.this);
                    pDialog.setMessage("Loading...");

                    pDialog.show();
                    function=   "updateprofile";
                    jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                            url + "?function=updateprofile&action=post&user_id=" + mAppController.mUser.getId() + "&name=" + nameUrl + "&dob=2013-03-03"  + "&gender=" + gender + "&mobile_number=" + mobNo + "&industry_interest=" + industryInterest + "&uploadimg=123.png"  + "&physical_disable=" + String.valueOf(physicalDisable) + "&non_physical_disable=" + String.valueOf(nonPhysicalDisable) +"&address="+addressUrl,
                            null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {


                                    Toast.makeText(EditProfileJob.this, response.toString(), Toast.LENGTH_SHORT).show();
                                    try {
                                        statusObj = (String) response.get("status");
                                        errorObj = response.get("error").toString();
                                        resObj = response.get("res").toString();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (statusObj.contentEquals("failed")) {
                                        *//**//*Intent backIntent = new Intent(EditProfileJob.this, ViewProfileJob.class);
                                        startActivity(backIntent);
                                        finish();*//**//*
                                    } else {
                                        mAppController.mUser.setUsername(name);
                                        mAppController.mUser.setName(name);
                                        mAppController.mUser.setContactName(name);
                                        mAppController.mUser.setDob(dobFull);
                                        mAppController.mUser.setMobileNumber(mobNo);
                                        mAppController.mUser.setGender(gender);
                                        mAppController.mUser.setIndustryInterest(industryInterest);
                                        mAppController.mUser.setPhysicalDisable(physicalDisable + "");
                                        mAppController.mUser.setUserAvatar(selectedImageUri);
                                        mAppController.mUser.setAddress(addressText);
                                        mAppController.mUser.setLocation(addressText);
                                        mAppController.mUser.setNonPhysicalDisable(nonPhysicalDisable + "");
                                        mAppController.mUser.setRoleId("2");
                                       *//**//* Intent backIntent = new Intent(EditProfileJob.this, FindAJob.class);
                                        startActivity(backIntent);
                                        finish();*//**//*
                                    }
                                    Log.d(tag_json_obj, response.toString());
                                    pDialog.hide();
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(tag_json_obj, "Error: " + error.getMessage());
                            Toast.makeText(EditProfileJob.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            pDialog.hide();
                        }
                    });
                    mAppController.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
*/

                /*    mAppController.mUser.setIndustryInterest(industryInterest);
                    mAppController.mUser.setPhysicalDisable(physicalDisable + "");
                    mAppController.mUser.setNonPhysicalDisable(nonPhysicalDisable + "");
                    mAppController.mUser.setMobileNumber(mobNo);
                    Intent intent = new Intent(EditProfileJob.this, ViewProfileJob.class);
                    startActivity(intent);
                    finish();*/

            }

        });
    }

    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                selectedImageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    image = getStringImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("jobseeker", "Image Path : " + path);
                    Glide.with(mContext).load(selectedImageUri).skipMemoryCache(true).into(dp);
                    mAppController.mUser.setUserAvatar(selectedImageUri);
                }
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
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
        } /*else {
            Toast.makeText(mContext, "Please enter complete address!", Toast.LENGTH_SHORT).show();
        }*/
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
        addressText = cityName + "," + Country;

        map1.clear();
        map1.addMarker(markerOptions);
        map1.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map1.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onNewLocationDetected(String newCityName, String oldCityName, double latitude, double longitude) {

    }

    @Override
    public void onCurrentLocationFoundListner(String address, String code, double latitude, double longitude, String Timezone) {
        addressText = address;
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem settingsMenuItem =  toolbar.getMenu().findItem(R.id.sign_in);
       // MenuItem settingsMenuItem = menu.findItem(R.id.sign_in);
        SpannableString s = new SpannableString(settingsMenuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, s.length(), 0);
        return true;
    }*/

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.sign_in) {
            return true;
        }
        if (id == R.id.sign_up) {
            return true;
        }
        if (id == R.id.logout) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.credit:
                Toast.makeText(this, "Credit Clicked", Toast.LENGTH_SHORT).show();
                return true;*/
            case R.id.logout:
                Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfileJob.this, Login.class);
                startActivity(intent);
                finish();
                return true;

        }
        return true;
    }

    public void imageUploading() {

        //getting the actual path of the image
        if(selectedImageUri!=null) {
            path = getPath(selectedImageUri);
        }
        else {
            path=mAppController.mUser.getUserAvatar().toString();
        }

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, HeaderRequestUrl.base_url)
                    //Adding file
                    .addParameter("function", function)//Adding text parameter to the request
                    .addParameter("action", "post")
                    .addParameter("email", email)
                    .addParameter("password", pasword)
                    .addParameter("cpassword", cpasword)
                    .addParameter("name", name)
                    .addParameter("dob", dobFull)
                    .addParameter("gender", gender)
                    .addParameter("mobile_number", mobNo)
                    .addParameter("industry_interest", industryInterest)

                    .addParameter("physical_disable", String.valueOf(physicalDisable))
                    .addParameter("non_physical_disable", String.valueOf(nonPhysicalDisable))
                    .addParameter("address", addressText)
                    .addFileToUpload(path, "uploadimg")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, net.gotev.uploadservice.UploadInfo uploadInfo) {
                            Log.e("progress", "p");
                        }

                        @Override
                        public void onError(Context context, net.gotev.uploadservice.UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            Log.e("progress", "error");
                        }

                        @Override
                        public void onCompleted(Context context, net.gotev.uploadservice.UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Log.e("progress", "complete");
                            Toast.makeText(mContext, "Completed", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(Context context, net.gotev.uploadservice.UploadInfo uploadInfo) {
                            Log.e("progress", "Cancelled");
                            Toast.makeText(mContext, "Cancelled", Toast.LENGTH_LONG).show();
                        }


                    })
                    .startUpload();
        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }


    }

 /*   private UploadServiceBroadcastReceiver broadcastReceiver = new UploadServiceBroadcastReceiver() {
        @Override
        public void onProgress(Context context, UploadInfo uploadInfo) {
            // your implementation
        }

        @Override
        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
            // your implementation
        }

        @Override
        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
            // your implementation
        }

        @Override
        public void onCancelled(Context context, UploadInfo uploadInfo) {
            // your implementation
        }
    };*/

    public void UpdatingProfile() {

        //getting the actual path of the image
        if(selectedImageUri!=null) {
            path = getPath(selectedImageUri);
        }
        else {
            path=mAppController.mUser.getUserAvatar().toString();
        }


        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, HeaderRequestUrl.base_url)
                    //Adding file
                    .addParameter("function", function)//Adding text parameter to the request
                    .addParameter("action", "post")
                    .addParameter("user_id", mAppController.mUser.getId())
                    .addParameter("name", name)
                    .addParameter("gender", gender)
                    .addParameter("dob", dobFull)

                    .addParameter("mobile_number", mobNo)
                    .addParameter("industry_interest", industryInterest)

                    .addParameter("physical_disable", String.valueOf(physicalDisable))
                    .addParameter("non_physical_disable", String.valueOf(nonPhysicalDisable))
                    .addParameter("address", addressText)
                    .addFileToUpload(path, "uploadimg")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, net.gotev.uploadservice.UploadInfo uploadInfo) {
                            Log.e("progress", "p");
                        }

                        @Override
                        public void onError(Context context, net.gotev.uploadservice.UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            Log.e("progress", "error");
                        }

                        @Override
                        public void onCompleted(Context context, net.gotev.uploadservice.UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Log.e("progress", "complete");
                            Toast.makeText(mContext, "Completed", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(Context context, net.gotev.uploadservice.UploadInfo uploadInfo) {
                            Log.e("progress", "Cancelled");
                            Toast.makeText(mContext, "Cancelled", Toast.LENGTH_LONG).show();
                        }


                    })
                    .startUpload();
        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }


    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


}