package com.unauto.android.common.util;

import android.app.Activity;
import android.content.Context;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Developer on 15/04/2016.
 */
public class Direccion {
Activity context;

public Direccion(Activity ctx) {
    context = ctx;
}

public String getAddress(LatLng... params) {
    Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
    double latitude = params[0].latitude;
    double longitude = params[0].longitude;

    List<android.location.Address> addresses = null;
    String addressText = "";

    try {
        addresses = geocoder.getFromLocation(latitude, longitude, 1);
    } catch (IOException e) {
        e.printStackTrace();
    }

    if (addresses != null && addresses.size() > 0) {
        android.location.Address address = addresses.get(0);

        addressText = String.format("%s, %s %s, %s", address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                address.getLocality() != null ? address.getLocality() : "", address.getPostalCode() != null ? address.getPostalCode() : "", address.getCountryName());
    }

    return addressText;
}

}
