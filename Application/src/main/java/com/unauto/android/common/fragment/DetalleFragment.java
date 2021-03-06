package com.unauto.android.common.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import com.unauto.R;
import com.unauto.android.common.clases.MisSitios;
import com.unauto.android.common.clases.SitiosController;
import com.unauto.android.common.logger.Log;
import com.unauto.android.common.util.Direccion;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleFragment extends Fragment {
private ListView mSitios;
private List<MisSitios> mLIstaSitios;
private double mLatitude;
private double mLongitude;
private String mNombreSitio;
private String mObjectId;
ParseUser obtenerUsuario = ParseUser.getCurrentUser();
private GoogleMap mMap;
AdaptadorSitios adaptadorMisSitios;
SitiosController miSitio;
Direccion doInBackground;
Intent in;
public DetalleFragment() {
    // Required empty public constructor
}


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    View viewFragmento = inflater.inflate(R.layout.fragment_detalle, container, false);
    mSitios = (ListView) viewFragmento.findViewById(R.id.list_detalle_sitio);
    mLIstaSitios = new ArrayList<>();
    this.mSitios.setItemsCanFocus(false);
    adaptadorMisSitios = new AdaptadorSitios();
    miSitio = new SitiosController(getActivity());
    doInBackground = new Direccion(getActivity());

    setUpMapIfNeeded();
    in = getActivity().getIntent();
    mObjectId = in.getStringExtra("objectId");
    mNombreSitio=in.getStringExtra("nombreSitio");
    //capturar el usuario en session
    String userName = obtenerUsuario.getUsername();// usuario capturado
    ParseQuery<ParseObject> query = ParseQuery.getQuery("MisSitios");
    query.whereEqualTo("objectId", mObjectId);
    query.findInBackground(new FindCallback<ParseObject>() {
        public void done(List<ParseObject> favoritoList, ParseException e) {
            if (e == null) {
                Log.d("Mis sitios", "Sitios " + favoritoList.size() + " Sitios");

                for (ParseObject object : favoritoList) {
                    String idSitio = (String) object.getObjectId();
                    String usename = (String) object.get("username");
                    String nombre = (String) object.get("Nombre");
                    mNombreSitio = nombre;
                    String direccion = (String) object.get("Direccion");
                    String Lat = (String) object.get("Latitude");
                    String Lng = (String) object.get("Longitude");
                    MisSitios misSitios = new MisSitios(idSitio, usename, nombre, direccion, Lat, Lng);
                    LatLng latLng = new LatLng(Double.parseDouble(Lat), Double.parseDouble(Lng));
                    mMap.addMarker(new MarkerOptions().position(latLng).title(nombre));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                                                            .target(latLng)      // Sets the center of the map to Mountain View
                                                            .zoom(17)                   // Sets the zoom
                                                            .bearing(90)                // Sets the orientation of the camera to east
                                                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    mLIstaSitios.add(misSitios);
                }
                mSitios.setAdapter(adaptadorMisSitios);
                adaptadorMisSitios.notifyDataSetChanged();
            } else {
                Log.d("Mis sitios", "Sitios: " + e.getMessage());
            }
        }
    });


    return viewFragmento;
}

public void setText(String text,String lat,String lng) {
    LinearLayout llupdate=(LinearLayout) getView().findViewById(R.id.LLDatosUpdate);
    llupdate.setVisibility(View.VISIBLE);
    final TextView textView = (TextView) getView().findViewById(R.id.txtdireccionupdate);
    final TextView textLat=(TextView) getView().findViewById(R.id.txtLat);
    final TextView textLng=(TextView)getView().findViewById(R.id.txtLng);
    textView.setText(text);
    textLat.setText(lat);
    textLng.setText(lng);
    llupdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            miSitio.ActualizarMiSitio(mObjectId,mNombreSitio,
                    textView.getText().toString(),
                    textLat.getText().toString(),
                    textLng.getText().toString());

        }
    });

}

@Override
public void onResume() {
    super.onResume();
    setUpMapIfNeeded();
}

private void setUpMapIfNeeded() {

    if (mMap == null) {
        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.isMyLocationEnabled();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                mMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(arg0);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));
                Marker marker = mMap.addMarker(markerOptions);

                marker.showInfoWindow();
                String direcion = doInBackground.getAddress(arg0);
                setText(direcion,String.valueOf(arg0.latitude),String.valueOf(arg0.longitude));

            }
        });
        if (mMap != null) {
        }
    }
}

private class AdaptadorSitios extends BaseAdapter {

    @Override
    public int getCount() {
        return mLIstaSitios.size();
    }

    @Override
    public Object getItem(int position) {
        return mLIstaSitios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View filaView = getActivity().getLayoutInflater().inflate(R.layout.item_detalle_sitios, null);
        MisSitios misSitios = mLIstaSitios.get(position);
        TextView txtNombreMiSitio = (TextView) filaView.findViewById(R.id.txtNombreSitio);
        TextView txtdirecion = (TextView) filaView.findViewById(R.id.txtdireccion);
        txtNombreMiSitio.setText(misSitios.getNombre());
        String st=new String(misSitios.getDireccion());
        txtdirecion.setText(st.substring(0,21));
        return filaView;
    }

}

}

