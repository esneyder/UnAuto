package com.unauto.android.common.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.unauto.android.common.activities.MisSitiosActivity;
import com.unauto.android.common.clases.Sitios;
import com.unauto.android.common.fragment.MisSitiosFragment;
import com.unauto.dev.services.UnAuto.R;


/**
 * Created by Developer on 14/04/2016.
 */
public class DialogFactory {
public static Dialog createSimpleOkErrorDialog(Context context, String title, String message) {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                                              .setTitle(title)
                                              .setMessage(message)
                                              .setNeutralButton(R.string.dialog_action_ok, null);
    return alertDialog.create();
}

public static Dialog createSimpleYesNoErrorDialog(Context context, String title, String message, DialogInterface.OnClickListener positiveListener) {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                                              .setTitle(title)
                                              .setMessage(message)
                                              .setPositiveButton(R.string.dialog_action_yes, positiveListener)
                                              .setNegativeButton(R.string.dialog_action_cancel, null);
    return alertDialog.create();
}

public static Dialog createSimpleErrorDialog(Context context) {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                                              .setTitle(context.getString(R.string.dialog_error_title))
                                              .setMessage(context.getString(R.string.dialog_general_error_Message))
                                              .setNeutralButton(R.string.dialog_action_ok, null);
    return alertDialog.create();
}

public static ProgressDialog createProgressDialog(Context context, String message) {
    ProgressDialog progressDialog = new ProgressDialog(context);
    progressDialog.setMessage(message);
    return progressDialog;
}

public static ProgressDialog createProgressDialog(Context context, @StringRes int messageResoruce) {
    return createProgressDialog(context, context.getString(messageResoruce));
}
public static void OptionPlaces(final Activity context, String objecId) {
    final String idSitio=objecId;
    String[] opc = new String[]{"Eliminar"};
    android.support.v7.app.AlertDialog opciones =
            new android.support.v7.app.AlertDialog.Builder(context)
                    .setTitle("Opciones")
                    .setItems(opc,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int selected) {
                                    if (selected == 0) {
                                       Sitios sitio=new Sitios();
                                        if(sitio.Eliminar(idSitio)) {
                                            Toast.makeText(context, "Sitio eliminado", Toast.LENGTH_SHORT).show();
                                            RecargarActitidad.reiniciarActivity(context);
                                            MisSitiosFragment fragment = new MisSitiosFragment();
                                            FragmentTransaction ft = context.getFragmentManager().beginTransaction();
                                            ft.commit();

                                            }else
                                            Toast.makeText(context, "No fue posible eliminar el sitio!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).create();
    opciones.show();
}


}

