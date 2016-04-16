package com.unauto.android.common.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.unauto.android.common.logger.Log;
import com.unauto.dev.playservices.UnAuto.R;

public class DetallesActivity extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detalles);
    /*
    Intent in = getIntent();
    String nombreSitio  = in.getStringExtra("nombreSitio");
    setTitle(nombreSitio);
    android.support.v7.app.ActionBar actionBar=getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
*/

}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
}
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {

        case R.id.menu_save:
            Log.i("ActionBar", "Guardar!");;

        default:
            return super.onOptionsItemSelected(item);
    }
}
}
