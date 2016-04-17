package com.unauto.android.common.clases;

/**
 * Created by Developer on 14/04/2016.
 */
public class MisSitios {

private String IdSitio;
private String username;
private String Nombre;
private String Direccion;
private String Latitude;
private String Longitude;

public MisSitios(String idSitio, String username, String nombre,
                 String direccion, String latitude, String longitude) {

    IdSitio = idSitio;
    this.username = username;
    this.Nombre = nombre;
    Direccion = direccion;
    this.Latitude=latitude;
    this.Longitude=longitude;
}

    public String getIdSitio() {
        return IdSitio;
    }

    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }
}

