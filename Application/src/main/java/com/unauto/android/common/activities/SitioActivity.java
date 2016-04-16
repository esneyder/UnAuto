package com.unauto.android.common.activities;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unauto.android.common.clases.SitiosController;
import com.unauto.android.common.logger.Log;
import com.unauto.android.common.util.MyCurrentLoctionListener;
import com.unauto.dev.playservices.UnAuto.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SitioActivity extends UnAutoActivityBase implements PlaceSelectionListener, GoogleMap.OnMapClickListener,
                                                                         GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
@Bind(R.id.place_details)
TextView mPlaceDetailsText;
@Bind(R.id.place_attribution)
TextView mPlaceAttribution;
@Bind(R.id.place_Lng)
TextView place_Lng;
@Bind(R.id.LLDatos)
LinearLayout LLDatosSeleccionados;
private GoogleMap googleMap;
SitiosController miSitio;
private Location mLastLocation;
public LocationManager mLocationManager;


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sitio);
    ButterKnife.bind(this);
    // Retrieve the PlaceAutocompleteFragment.
    PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                                                             getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

    // Register a listener to receive callbacks when a place has been selected or an error has
    // occurred.
    autocompleteFragment.setOnPlaceSelectedListener(this);
    miSitio = new SitiosController(this);
    LLDatosSeleccionados.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = "Casa";
            String address = mPlaceDetailsText.getText().toString();
            String lat = mPlaceAttribution.getText().toString();
            String lng = place_Lng.getText().toString();
            miSitio.GuardarMiSitio(name, address, lat, lng);
        }
    });

    int LOCATION_REFRESH_TIME = 1000;
    int LOCATION_REFRESH_DISTANCE = 5;

    mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
            LOCATION_REFRESH_DISTANCE, new MyCurrentLoctionListener());


    Log.d("datos ", mPlaceDetailsText.getText().toString());

}

private void lanzarmapa(double lt, double ln) {
    try {

        LatLng UnAuto = new LatLng(lt, ln);
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().
                                                                   findFragmentById(R.id.map)).getMap();
        }
        String direccion = doInBackground(UnAuto);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        final Marker TP = googleMap.addMarker(new MarkerOptions().
                                                                         position(UnAuto).title(direccion));
        googleMap.isMyLocationEnabled();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(UnAuto, 16f));
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng arg0) {
                googleMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(arg0);
                String direcion = doInBackground(arg0);
                mPlaceDetailsText.setText(direcion);
                mPlaceAttribution.setText(String.valueOf(arg0.latitude));
                place_Lng.setText(String.valueOf(arg0.longitude));
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));
                Marker marker = googleMap.addMarker(markerOptions);
                marker.showInfoWindow();

            }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@Override
public void onPlaceSelected(Place place) {
    LatLng latLng = place.getLatLng();
    CharSequence direcion = place.getAddress();
    mPlaceDetailsText.setText(direcion.toString());
    mPlaceAttribution.setText(String.valueOf(latLng.latitude));
    place_Lng.setText(String.valueOf(latLng.longitude));
    onMapClick(latLng);
}

@Override
public void onError(Status status) {
    Log.e(TAG, "onError: Status = " + status.toString());

    Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
            Toast.LENGTH_SHORT).show();
}

@Override
public void onMapClick(LatLng latLng) {
    googleMap.clear();
    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.position(latLng);
    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    Marker marker = googleMap.addMarker(markerOptions);
    marker.showInfoWindow();
}

protected String doInBackground(LatLng... params) {
    Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
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


@Override
public void onConnected(@Nullable Bundle bundle) {

}

@Override
public void onConnectionSuspended(int i) {

}

@Override
public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

}

private class MyCurrentLoctionListener implements android.location.LocationListener {

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();

        String myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

        //I make a log to see the results
        android.util.Log.e("MY CURRENT LOCATION", myLocation);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        String direccion = doInBackground(latLng);
        mPlaceDetailsText.setText(direccion);
        mPlaceAttribution.setText(String.valueOf(latLng.latitude));
        place_Lng.setText(String.valueOf(latLng.longitude));
        lanzarmapa(location.getLatitude(), location.getLongitude());

    }


    public void onStatusChanged(String s, int i, Bundle bundle) {

    }


    public void onProviderEnabled(String s) {

    }

    public void onProviderDisabled(String s) {

    }
}


}
