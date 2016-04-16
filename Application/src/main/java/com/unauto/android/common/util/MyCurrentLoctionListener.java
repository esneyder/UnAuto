package com.unauto.android.common.util;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Developer on 15/04/2016.
 */
public class MyCurrentLoctionListener implements android.location.LocationListener {

@Override
public void onLocationChanged(Location location) {
    location.getLatitude();
    location.getLongitude();

    String myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

    //I make a log to see the results
    Log.e("MY CURRENT LOCATION", myLocation);

}


public void onStatusChanged(String s, int i, Bundle bundle) {

}


public void onProviderEnabled(String s) {

}

public void onProviderDisabled(String s) {

}
}