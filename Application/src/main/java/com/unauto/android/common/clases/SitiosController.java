package com.unauto.android.common.clases;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.unauto.android.common.activities.DetallesActivity;
import com.unauto.android.common.activities.MisSitiosActivity;
import com.unauto.dev.services.UnAuto.R;


/**
 * Created by Developer on 14/04/2016.
 */
public class SitiosController {
Context context;

public SitiosController(Context ctx) {
    this.context = ctx;
}

public void GuardarMiSitio(String mName, final String mAddress, String mLatitude, String mLongitude) {
    //capturar el usuario en session
    ParseUser obtenerUsuario = ParseUser.getCurrentUser();
    final String mUserName = obtenerUsuario.getUsername();// usuario capturado
    final String name = mName;
    final String address = mAddress;
    final String latitude = mLatitude;
    final String longitude = mLongitude;
    LayoutInflater li = LayoutInflater.from(context);
    View filaSitio = li.inflate(R.layout.dialo_nombre_sitio, null);
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    alertDialogBuilder.setView(filaSitio);
    final EditText userInput = (EditText) filaSitio
                                                  .findViewById(R.id.edtNombreSitio);
    userInput.setText(name);
    alertDialogBuilder.setTitle(R.string.nombre_lugar_dialog);
    alertDialogBuilder
            // .setMessage("Desea cambiar el nombre?")
            .setCancelable(false)
            .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String nombresitio = userInput.getText().toString();
                    Sitios sitios = new Sitios();//instancio la clase con el método guardar
                    boolean estado = sitios.GuardarSitio(mUserName, nombresitio, address, latitude, longitude);
                    if (estado) {
                        Toast.makeText(context, "Sitio guardado correctamente!", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, MisSitiosActivity.class));
                    } else
                        Toast.makeText(context, "Sorry!,no se pudo guardar el sitio!", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
}

public void RenobrarSitio(final String objectId, String mNuevoNombre) {
    final String name = mNuevoNombre;
    final String objectoIdUpdate = objectId;
    LayoutInflater li = LayoutInflater.from(context);
    View filaSitio = li.inflate(R.layout.dialo_nombre_sitio, null);
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    alertDialogBuilder.setView(filaSitio);
    final EditText userInput = (EditText) filaSitio
                                                  .findViewById(R.id.edtNombreSitio);
    userInput.setText(name);
    alertDialogBuilder.setTitle(R.string.rename_sitio);
    alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("Renombrar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    final String nombresitio = userInput.getText().toString();
                    Sitios sitios = new Sitios();//instancio la clase con el método renombrar
                    boolean estado = sitios.Renombrar(objectoIdUpdate, nombresitio);
                    if (estado) {
                        Toast.makeText(context, "Sitio renombrado correctamente!", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, MisSitiosActivity.class));
                    } else
                        Toast.makeText(context, "Sorry!,no se pudo renombrar el sitio!", Toast.LENGTH_SHORT).show();

                }

            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
}

public void ActualizarMiSitio(String objectId, String mName, final String mAddress, String mLatitude, String mLongitude) {
    //capturar el usuario en session
    final String objectidUpdate = objectId;
    final String name = mName;
    final String address = mAddress;
    final String latitude = mLatitude;
    final String longitude = mLongitude;
    LayoutInflater li = LayoutInflater.from(context);
    View filaSitio = li.inflate(R.layout.dialo_nombre_sitio, null);
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    alertDialogBuilder.setView(filaSitio);
    final EditText userInput = (EditText) filaSitio
                                                  .findViewById(R.id.edtNombreSitio);
    userInput.setText(name);
    alertDialogBuilder.setTitle(R.string.nombre_lugar_dialog);
    alertDialogBuilder
            // .setMessage("Desea cambiar el nombre?")
            .setCancelable(false)
            .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String nombresitio = userInput.getText().toString();
                    Sitios sitios = new Sitios();//instancio la clase con el método guardar
                    boolean estado = sitios.Actualizar(objectidUpdate, nombresitio, address, latitude, longitude);
                    if (estado) {
                        Toast.makeText(context, "Sitio actualizado correctamente!", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, MisSitiosActivity.class));
                    } else
                        Toast.makeText(context, "Sorry!,no se pudo actualziar el sitio!", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
}
}
