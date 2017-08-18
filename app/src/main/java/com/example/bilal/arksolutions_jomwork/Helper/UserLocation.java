package com.example.bilal.arksolutions_jomwork.Helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;


import com.example.bilal.arksolutions_jomwork.R;
import com.example.bilal.arksolutions_jomwork.listeners.OnCurrentLocationFoundListner;
import com.example.bilal.arksolutions_jomwork.listeners.OnLocationSetListner;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;


import java.util.HashMap;

public class UserLocation implements OnCurrentLocationFoundListner, OnLocationSetListner, ConnectionCallbacks, OnConnectionFailedListener {

    public static final int LOCATION_SETTINGS_DELAY = 4000;

    Context mContext;
    //ManualLocationDialog manualLocDialog;
    AlertDialog alertProvider = null;
    private boolean isShown = false, isGPSEnabled = false, isNetworkEnabled = false;
    String timeZone;
    double latitude, longitude;
    OnLocationSetListner mOnLocationSetListner;
  //  LocationPref locationPref;
    Location mCurrentLocation;
    GoogleApiClient mGoogleApiClient;

    private boolean isLocationSettingsShown = false;
    private boolean isLocationSet = false;

    public UserLocation(Context cntxt) {
        mContext = cntxt;
        isLocationSettingsShown = false;
        isLocationSet = false;
      //  locationPref = new LocationPref(cntxt);
       // manualLocDialog = new ManualLocationDialog(cntxt, this);
        setOnLocationSetListner(mOnLocationSetListner);
        buildGoogleApiClient();
    }

    public void setOnLocationSetListner(OnLocationSetListner lister) {
        this.mOnLocationSetListner = lister;
    }

    public void checkLocation(boolean isShown, boolean isShowLocationSettings) {
        this.isShown = isShown;
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        handleFirstTimeLocation();
       /* if (locationPref.isFirstLaunch()) {
            handleFirstTimeLocation();
        } else {
            handleLocationAccess(isShowLocationSettings);
        }*/
    }

    private void handleFirstTimeLocation() {
        if (isNetworkConnected()) {
            if (!isGPSEnabled && !isNetworkEnabled && !isLocationSettingsShown) {
                if (!isShown) {
                    isLocationSettingsShown = true;
                    providerAlertMessage();
                }
            } else {
                if (!mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                } else {
                    findUserLocation();
                }
            }
        } else {
            dismissDialog();

          /*  if (!isShown && locationPref.isFirstLaunch()) {
                dismissDialog();
                manualLocDialog.showDialog();
                locationPref.setFirstLaunch();
            }*/
        }
    }

    private void handleLocationAccess(boolean isShowLocationSettings) {
        if (isNetworkConnected()) {
            if (!isGPSEnabled && !isNetworkEnabled && !isLocationSettingsShown) {
                if (!isShown && isShowLocationSettings) {
                    isLocationSettingsShown = true;
                    providerAlertMessage();
                } else {
                    //useLastSavedLocation();
                }
            } else {
                if (!mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                } else {
                    findUserLocation();
                }
            }
        } else {
           // useLastSavedLocation();
        }
    }

   /* private void useLastSavedLocation() {
        Log.i("Location", "Manual");
        HashMap<String, String> alarm = locationPref.getLocation();
        String Address = alarm.get(LocationPref.CITY_NAME);
        if (!Address.equals("")) {
            latitude = Double.parseDouble(alarm.get(LocationPref.LATITUDE));
            longitude = Double.parseDouble(alarm.get(LocationPref.LONGITUDE));
            String Country=alarm.get(LocationPref.COUNTRY_NAME);

         //   sendAnalyticsData("Location Manual");
            isLocationSet = true;
            mOnLocationSetListner.onLocationSet(Address,Country, latitude, longitude,alarm.get(LocationPref.TIMEZONE));
        } else {
            if (!isShown && locationPref.isFirstLaunch()) {
                dismissDialog();
                manualLocDialog.showDialog();
                locationPref.setFirstLaunch();
            } else {
                mOnLocationSetListner.onLocationSet("", "",0, 0,"");
            }
        }
    }*/

    public void findUserLocation() {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mCurrentLocation != null && !isLocationSet) {
            latitude = mCurrentLocation.getLatitude();
            longitude = mCurrentLocation.getLongitude();
            long time = mCurrentLocation.getTime();
            timeZone = String.valueOf(time);
           /* locationPref.setLatitudeCurrent(latitude + "");
            locationPref.setLongitudeCurrent(longitude + "");*/

            if (isNetworkConnected()) {
                GeoCoderVolley gh = new GeoCoderVolley(mContext);
                gh.setListener(this);
                gh.fetchAddressFromCoordinates(mContext, mCurrentLocation);
            } else {
               // useLastSavedLocation();
            }
        } else {
            //useLastSavedLocation();
        }
    }


    @Override
    public void onLocationSet(String cityName, String Country, double latitude, double longitude, String timeZone) {
        // TODO Auto-generated method stub
        /*
         * if(cityName == null || latitude == 0 || longitude == 0 || latitude == -2 || longitude == -2) { useLastSavedLocation(); } else
		 */
        if (cityName != null) {
            if (cityName.equals("")) {
               // useLastSavedLocation();
            } else if (!cityName.equals("")) {
                // sendAnalyticsData("Location Auto");
               // locationPref.setFirstLaunch();
                isLocationSet = true;
                mOnLocationSetListner.onLocationSet(cityName,Country, latitude, longitude,timeZone);
            }
        }
    }

    @Override
    public void onNewLocationDetected(String newCityName, String oldCityName, double latitude, double longitude) {


    }

    public boolean isNetworkConnected() {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnected() && netInfo.isAvailable());
    }

    private void providerAlertMessage() {

        dismissDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getResources().getString(R.string.unable_to_find_location));
        builder.setMessage(mContext.getResources().getString(R.string.enable_provider));
        builder.setCancelable(false);

        builder.setPositiveButton(mContext.getResources().getString(R.string.settings), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(settingsIntent);
                //CompassDialMenuFragment.LOCATION_REQUEST_DELAY = LOCATION_SETTINGS_DELAY;
            }
        });

        builder.setNegativeButton(mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               // CompassDialMenuFragment.LOCATION_REQUEST_DELAY = 0;
                if (!isShown) {
                    dismissDialog();
                    /*if (locationPref.getCityName().equals("") && locationPref.isFirstLaunch()) {

                        dismissDialog();
                        manualLocDialog.showDialog();
                    } else {
                        useLastSavedLocation();
                    }*/
                }
            }
        });

        builder.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
              /*  if (!isShown) {
                    if (locationPref.getCityName().equals("") && locationPref.isFirstLaunch()) {

                        dismissDialog();
                        manualLocDialog.showDialog();
                    } else {
                        useLastSavedLocation();
                    }*/
                }

        });

        builder.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    return true;
                }
                return false;
            }
        });

        alertProvider = builder.create();
        alertProvider.show();
    }

    public boolean isShowingDialog() {
        if (alertProvider != null)
            return alertProvider.isShowing();
        return false;
    }

    private void dismissDialog() {
        if (alertProvider != null)
            alertProvider.dismiss();

    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        findUserLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

   /* private void sendAnalyticsData(String name) {
        AnalyticSingaltonClass.getInstance(mContext).sendScreenAnalytics(name);
    }*/

    @Override
    public void onCurrentLocationFoundListner(String address, String code, double latitude, double longitude, String Timezone) {
        // TODO Auto-generated method stub

   if (address == null || address.equals("") || latitude == 0 || longitude == 0 || latitude == -2 || longitude == -2) {
            //useLastSavedLocation();
        } else if (!address.equals("")) {
            // sendAnalyticsData("Location Auto");
       /*     locationPref.setFirstLaunch();
            isLocationSet = true;
            HashMap<String, String> alarm = locationPref.getLocation();
            String oldCity = alarm.get(LocationPref.CITY_NAME);
            String country=alarm.get(LocationPref.COUNTRY_NAME);
            if (!oldCity.isEmpty() && !address.equals(oldCity)) {
                mOnLocationSetListner.onNewLocationDetected(address, oldCity, latitude, longitude);
            } else {*/
                mOnLocationSetListner.onLocationSet(address,code, latitude, longitude,Timezone);
            }
        }
  /*      String cityName;
        String countryName;
        if (address == null) {
            address = "";
        }
        locationPref.setFirstLaunch();
        isLocationSet = true;

        if (address.equals("") || latitude == 0 || longitude == 0 || latitude == -2 || longitude == -2) {

            cityName = address;
            countryName = code;


        } else if (!address.equals("")) {

            countryName = code;
            cityName = address;
            this.latitude = latitude;
            this.longitude = longitude;
            locationPref.setLocation(cityName,countryName,String.valueOf(latitude),String.valueOf(longitude),timeZone);
            // this.timeZone=timezone;
        }*/
    }

