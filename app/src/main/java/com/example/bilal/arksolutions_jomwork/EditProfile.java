package com.example.bilal.arksolutions_jomwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.bilal.arksolutions_jomwork.Helper.AppController;
import com.example.bilal.arksolutions_jomwork.Helper.GeoCoderVolley;
import com.example.bilal.arksolutions_jomwork.Helper.HeaderRequestUrl;
import com.example.bilal.arksolutions_jomwork.listeners.OnCurrentLocationFoundListner;
import com.example.bilal.arksolutions_jomwork.listeners.OnLocationSetListner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class EditProfile extends AppCompatActivity implements OnCurrentLocationFoundListner, OnLocationSetListner, PopupMenu.OnMenuItemClickListener {
    TextView toolbarTitle;
    Toolbar toolbar;
    Button nextbtn;
    String email, name, birthday, location;

    EditText nameEdittext, companyEdittext, mobNo, emailEdittext;
    OnCurrentLocationFoundListner listner;
    MarkerOptions markerOptions;
    GoogleMap map1;
    Button picUpload;
    Bitmap myBitmap;
    Location defaultLocation;
    Context mContext;
    GeoCoderVolley mGeoCodeHelper;
    ImageView dp;
    String city;
    ImageView dropdown;
    AppController mAppController;
    LatLng latLng;
    Double home_long;
    Double home_lat;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "EditProfile";
    Spinner industrySpinner;
    Uri selectedImageUri;
    private String str_facebookname, str_birthda, str_location, str_facebookemail, str_facebookimage;
    String addressText, mobile;
    Bitmap bitmap;
    private String industryInterest;
    JsonObjectRequest jsonObjReq;
    String statusObj, errorObj, resObj;
    String tag_json_obj = "json_obj_req";
    AutoCompleteTextView searchAuto;
    ImageView searchIcon;
    String path;
    String image;
    String nameUrl,addressUrl,companyUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        toolbarTitle = (TextView) findViewById(R.id.TitleToolbar);
        nextbtn = (Button) findViewById(R.id.btn_edit_profile);
        nameEdittext = (EditText) findViewById(R.id.name_input_profile);
        emailEdittext = (EditText) findViewById(R.id.email_input_profile);
        companyEdittext = (EditText) findViewById(R.id.company_input_profile);
        mobNo = (EditText) findViewById(R.id.contact_input_profile);
        industrySpinner = (Spinner) findViewById(R.id.industry_spinner);
        searchIcon = (ImageView) findViewById(R.id.search_add_view);
        searchAuto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        listner = this;
        mContext = this;
        mAppController = (AppController) mContext.getApplicationContext();
        dp = (ImageView) findViewById(R.id.iv_profile);
        picUpload = (Button) findViewById(R.id.upload);

        dropdown = (ImageView) findViewById(R.id.dropdown_click);
        dropdown.setVisibility(View.VISIBLE);

        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(EditProfile.this);
                popupMenu.getMenu().getItem(0).setTitle("Credits : " + mAppController.credits);
                popupMenu.inflate(R.menu.menu);
                popupMenu.show();
            }
        });


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

        picUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

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
        mGeoCodeHelper = new GeoCoderVolley(mContext);
        mGeoCodeHelper.setListener(this);
        Intent intent = getIntent();
        if (intent.getBundleExtra("fb_bundle") != null) {
            str_facebookname = intent.getBundleExtra("fb_bundle").getString("Name");
            str_birthda = intent.getBundleExtra("fb_bundle").getString("Birthday");
            str_facebookemail = (String) intent.getBundleExtra("fb_bundle").get("Email");
            str_location = intent.getBundleExtra("fb_bundle").getString("Location");
            str_facebookimage = intent.getBundleExtra("fb_bundle").getString("Picture");
            // genderFb=intent.getStringExtra("Gender");
            nameEdittext.setText(str_facebookname);
            //genderEdit.setText(genderFb);
            emailEdittext.setText(str_facebookemail);
            Glide.with(EditProfile.this).load(str_facebookimage).skipMemoryCache(true).into(dp);
            if (str_location != null) {
                addressText = str_location;
                search();
         /*       locationEdittext.setText(str_location);
                mGeoCodeHelper.fetchCoordinatesFromAddress(mContext, locationEdittext.getText().toString());*/

            }

        } else {
            nameEdittext.setText(mAppController.mUser.getName());
            emailEdittext.setText(mAppController.mUser.getEmail());
            mobNo.setText(mAppController.mUser.getMobileNumber());
            // genderEdit.setText(mAppController.mUser.getGender());

            addressText = mAppController.mUser.getAddress();
            search();


        }

    /*    nameEdittext.setText(name);
        emailEdittext.setText(email);*/

/*
        birthdayEdittext.setText(birthday);
        Glide.with(EditProfile.this).load(pictureUrl).skipMemoryCache(true).into(dp);*/
        industrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                industryInterest = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                mobile = mobNo.getText().toString();
                name = nameEdittext.getText().toString();
                String company = companyEdittext.getText().toString();

                try {
                    nameUrl= URLEncoder.encode(name, "UTF-8");
                    addressUrl=URLEncoder.encode(addressText, "UTF-8");
                    companyUrl=URLEncoder.encode(addressText, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(selectedImageUri!=null)
                {
                    path=getPathFromURI(selectedImageUri);
                }
               /* mAppController.mUser.setIndustryInterest(industryInterest);
                mAppController.mUser.setMobileNumber(mobile);*/
               /* if(selectedImageUri==null){
                    selectedImageUri=(Uri) Uri.parse( "123.png");
                }*/

               /* if(!mobNo.getText().toString().isEmpty()) {
                    Intent intent = new Intent(EditProfile.this, PostJob.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(mContext,"Please enter contact number !",Toast.LENGTH_LONG).show();
                }*/
                final ProgressDialog pDialog = new ProgressDialog(EditProfile.this);
                pDialog.setMessage("Loading...");


                pDialog.show();

                jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        HeaderRequestUrl.base_url + "?function=updateemployerprofile&action=post&user_id=" + mAppController.mUser.getId() + "&contact_name=" + nameUrl + "&company_name=" + companyUrl + "&contact_number=" + mobile + "&industry=" + industryInterest + "&uploadimg="+path + "&address=" + addressUrl,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {


                                Toast.makeText(EditProfile.this, response.toString(), Toast.LENGTH_SHORT).show();
                                try {
                                    statusObj = (String) response.get("status");
                                    errorObj = response.get("error").toString();
                                    resObj = response.get("res").toString();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (statusObj.contentEquals("failed")) {

                                } else {
                                    mAppController.mUser.setId("");
                                    mAppController.mUser.setUsername(name);
                                    mAppController.mUser.setEmail(email);
                                    mAppController.mUser.setName(name);
                                    mAppController.mUser.setContactName(name);

                                    mAppController.mUser.setMobileNumber(mobile);

                                    mAppController.mUser.setIndustryInterest(industryInterest);

                                    mAppController.mUser.setUserAvatar(path);
                                    mAppController.mUser.setAddress(addressText);
                                    mAppController.mUser.setLocation(addressText);

                                    mAppController.mUser.setRoleId("");
                                    Intent backIntent = new Intent(EditProfile.this, PostJob.class);
                                    startActivity(backIntent);
                                    finish();
                                }
                                Log.d(tag_json_obj, response.toString());
                                pDialog.hide();
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(tag_json_obj, "Error: " + error.getMessage());
                        Toast.makeText(EditProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                });
                mAppController.getmInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

            }
        });
        toolbarTitle.setText("Edit Profile");
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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

        // locationTv.setText("Latitude:" + address.getLatitude() + ", Longitude:"
        //    + address.getLongitude());


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
                    Log.i(TAG, "Image Path : " + path);
                    Glide.with(EditProfile.this).load(selectedImageUri).skipMemoryCache(true).into(dp);
/*
                    // S

                        paret the image in ImageView
                    File imgFile=new File(selectedImageUri.getPath());
                    //Bitmap myBitmap = getBitmap(imgFile.getAbsolutePath());
                    try {
                        ParcelFileDescriptor parcelFileDescriptor =
                                getContentResolver().openFileDescriptor(selectedImageUri, "r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                       myBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
celFileDescriptor.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        ExifInterface exif = new ExifInterface(imgFile.getAbsolutePath());
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        Log.d("EXIF", "Exif: " + orientation);
                        Matrix matrix = new Matrix();
                        if (orientation == 6) {
                            matrix.postRotate(90);
                        }
                        else if (orientation == 3) {
                            matrix.postRotate(180);
                        }
                        else if (orientation == 8) {
                            matrix.postRotate(270);
                        }
                        myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true); // rotating bitmap
                    }
                    catch (Exception e) {

                    }

                    dp.setImageBitmap(myBitmap);*/
                    //dp.setImageURI(selectedImageUri);
                }
            }
        }
    }

    /* Get the real path from the URI */
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

    @Override
    public void onLocationSet(String cityName, String Country, double latitude, double longitude, String timeZone) {
        latLng = new LatLng(latitude, longitude);
        markerOptions = new MarkerOptions();
        defaultLocation.setLatitude(latitude);
        defaultLocation.setLongitude(longitude);
        mGeoCodeHelper = new GeoCoderVolley(mContext);
        mGeoCodeHelper.fetchAddressFromCoordinates(mContext, defaultLocation);
        addressText = cityName + "," + Country;
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

    @Override
    public void onCurrentLocationFoundListner(String address, String code, double latitude, double longitude, String Timezone) {
        addressText = address;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(EditProfile.this, Login.class);
                startActivity(intent);
                finish();

                return true;

        }
        return true;
    }
}
