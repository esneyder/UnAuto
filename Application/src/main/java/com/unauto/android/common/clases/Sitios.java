package com.unauto.android.common.clases;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

/**
 * Created by Developer on 14/04/2016.
 */
public class Sitios {

boolean bandera = true;

public boolean GuardarSitio(String username, String
                                                     nombresitio, String mAddress, String mLatitude, String mLongitude) {

    ParseObject sitios = new ParseObject("MisSitios");
    sitios.put("username", username);
    sitios.put("Nombre", nombresitio);
    sitios.put("Direccion", mAddress);
    sitios.put("Latitude", mLatitude);
    sitios.put("Longitude", mLongitude);
    sitios.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if (e == null)
                bandera = true;
            else
                bandera = false;

        }
    });
    return bandera;
}

public boolean Eliminar(String objectId) {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("MisSitios");
    query.getInBackground(objectId, new GetCallback<ParseObject>() {

        public void done(ParseObject object, ParseException e) {
            if (e == null) {
                bandera = true;
                try {
                    object.delete();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            } else {
                bandera = false;
                System.out.println(e.getMessage().toString());
            }
        }
    });
    return bandera;
}

public boolean Renombrar(String objectId, final String nuevoNombre) {
    final String updateNombre = nuevoNombre;
    ParseQuery<ParseObject> query = ParseQuery.getQuery("MisSitios");
    query.getInBackground(objectId, new GetCallback<ParseObject>() {
        public void done(ParseObject gameScore, ParseException e) {
            if (e == null) {
                gameScore.put("Nombre", updateNombre);
                gameScore.saveInBackground();
                bandera = true;
            }
        }
    });
    return bandera;

}

public boolean Actualizar(String objectId,
                          String nombresitio,
                          String mAddress,
                          String mLatitude,
                          String mLongitude) {
    final String objectIdUpdate = objectId;
    final String updateNombre = nombresitio;
    final String direccionUpdate = mAddress;
    final String latUpdate = mLatitude;
    final String lngUpdate = mLongitude;
    ParseQuery<ParseObject> query = ParseQuery.getQuery("MisSitios");
    query.getInBackground(objectIdUpdate, new GetCallback<ParseObject>() {
        public void done(ParseObject sitios, ParseException e) {
            if (e == null) {
                sitios.put("Nombre", updateNombre);
                sitios.put("Direccion", direccionUpdate);
                sitios.put("Latitude", latUpdate);
                sitios.put("Longitude", lngUpdate);
                sitios.saveInBackground();
                bandera = true;
            }
        }
    });
    return bandera;

}

}
