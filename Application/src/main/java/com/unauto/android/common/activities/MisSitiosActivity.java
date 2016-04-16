package com.unauto.android.common.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.unauto.dev.services.UnAuto.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MisSitiosActivity extends AppCompatActivity {
private Button btn;
@Bind(R.id.fab)
FloatingActionButton floatingActionButton;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mis_sitios);
    ButterKnife.bind(this);

    floatingActionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), SitioActivity.class));
        }
    });

}

}


