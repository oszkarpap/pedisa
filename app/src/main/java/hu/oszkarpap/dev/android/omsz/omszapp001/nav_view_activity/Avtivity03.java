package hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import hu.oszkarpap.dev.android.omsz.omszapp001.MainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.R;

public class Avtivity03 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avtivity03);
        overridePendingTransition(R.anim.fade_in,0);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
overridePendingTransition(R.anim.fade_out, 0);

    }
}
