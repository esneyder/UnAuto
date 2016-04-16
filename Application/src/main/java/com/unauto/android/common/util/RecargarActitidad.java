package com.unauto.android.common.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Developer on 16/04/2016.
 */
public class RecargarActitidad {

//reinicia una Activity
public  static void reiniciarActivity(Activity actividad){
    Intent intent=new Intent();
    intent.setClass(actividad, actividad.getClass());
    //llamamos a la actividad
    actividad.startActivity(intent);
    //finalizamos la actividad actual
    actividad.finish();
}

}
