
package com.example.bilal.arksolutions_jomwork.listeners;

import java.util.TimeZone;

public interface OnLocationSetListner {

    void onLocationSet(String cityName, String Country, double latitude, double longitude, String timeZone);

    void onNewLocationDetected(String newCityName, String oldCityName, double latitude, double longitude);
}
