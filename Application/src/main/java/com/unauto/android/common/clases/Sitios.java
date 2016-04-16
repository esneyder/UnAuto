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

boolean add=true;//retornar si fue guardado el registro
public   boolean GuardarSitio(String username,String
                                                                       nombresitio,String mAddress,String mLatitude,String mLongitude) {

    ParseObject sitios = new ParseObject("MisSitios");
    sitios.put("username", username);
    sitios.put("Nombre", nombresitio);
    sitios.put("Direccion",mAddress);
    sitios.put("Latitude",mLatitude);
    sitios.put("Longitude",mLongitude);
    sitios.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if (e == null)
                add=true;
            else
                add=false;

        }
    });
    return add;
}

public boolean Eliminar(String objectId){
    ParseQuery<ParseObject> query = ParseQuery.getQuery("MisSitios"); query.getInBackground(objectId, new GetCallback<ParseObject>() {

        public void done(ParseObject object, ParseException e) {
            if (e == null) {
                System.out.println("Query success!");
                String test = object.getObjectId().toString();
                System.out.println("The string ID is: " + test);
                // DELETE OBJECT HERE
                add=true;
                try {
                    object.delete();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                // object will be your game score
            } else {
                add=false;
                System.out.println(e.getMessage().toString());
            }


        }
    });
    return add;
}

}
