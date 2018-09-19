package hu.oszkarpap.dev.android.omsz.omszapp001;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import hu.oszkarpap.dev.android.omsz.omszapp001.login.LoginMainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.MedActivity;


public class ParametersActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(4).setTitle("Főablak");
        return true;



    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_medication) {
            intent = new Intent(ParametersActivity.this, MedActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_parameters) {
            intent = new Intent(ParametersActivity.this, ParametersActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_perfusor) {
            intent = new Intent(ParametersActivity.this, PerfusorActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_settings) {
            intent = new Intent(ParametersActivity.this, LoginMainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_log_out) {
            intent = new Intent(ParametersActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}