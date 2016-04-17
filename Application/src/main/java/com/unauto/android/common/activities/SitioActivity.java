package com.unauto.android.common.activities;

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
import com.unauto.android.common.util.Direccion;
import com.unauto.android.common.util.GPSTracker;
import com.unauto.dev.services.UnAuto.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SitioActivity extends UnAutoActivityBase implements PlaceSelectionListener, GoogleMap.OnMapClickListener {
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
Direccion doInBackground;
GPSTracker gps;
double latitude;
double longitude;
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
    doInBackground=new Direccion(this);



    Log.d("datos ", mPlaceDetailsText.getText().toString());
    gps = new GPSTracker(getApplication());
    if (gps.canGetLocation()) {
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
    } else {
        gps.showSettingsAlert();
    }
    lanzarmapa(latitude,longitude);
}

private void lanzarmapa(final double lt, final double ln) {
    try {

        final LatLng UnAuto = new LatLng(lt, ln);
        String direccion = doInBackground.getAddress(UnAuto);
        mPlaceDetailsText.setText(direccion);
        mPlaceAttribution.setText(String.valueOf(UnAuto.latitude));
        place_Lng.setText(String.valueOf(UnAuto.longitude));
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().
                                                                   findFragmentById(R.id.map)).getMap();
        }
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
                String direcion = doInBackground.getAddress(arg0);
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

}
