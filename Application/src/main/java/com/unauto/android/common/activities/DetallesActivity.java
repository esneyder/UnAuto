package com.unauto.android.common.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.unauto.android.common.logger.Log;
import com.unauto.dev.services.UnAuto.R;


public class DetallesActivity extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detalles);

    Intent in = getIntent();
    String nombreSitio  = in.getStringExtra("nombreSitio");
    setTitle(nombreSitio);
    android.support.v7.app.ActionBar actionBar=getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);


}
}
