package hu.oszkarpap.dev.android.omsz.omszapp001;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import hu.oszkarpap.dev.android.omsz.omszapp001.login.LoginMainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.MedActivity;


public class ParametersActivity extends AppCompatActivity {

    private Intent intent;
    private SeekBar seekBar;
    private TextView age, hr, lsz, sys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        seekBar = (SeekBar) findViewById(R.id.par_sb);
        age = (TextView) findViewById(R.id.par_age);
        hr = (TextView) findViewById(R.id.par_hr);
        lsz = (TextView) findViewById(R.id.par_lsz);
        sys = (TextView) findViewById(R.id.par_sys);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                age.setText(String.valueOf(progress));

                    if(progress==0) {
                        hr.setText("110-160");
                        lsz.setText("30-40");
                        sys.setText("70-90");
                    }else if(progress==1 || progress==2){
                        hr.setText("100-150");
                        lsz.setText("25-35");
                        sys.setText("80-95");
                    } else if (progress>2 && progress<6){
                        hr.setText("90-140");
                        lsz.setText("25-30");
                        sys.setText("80-100");
                    }else if (progress>5 && progress<=12){
                        hr.setText("80-130");
                        lsz.setText("20-25");
                        sys.setText("90-110");
                    }else if (progress>12){
                        hr.setText("60-100");
                        lsz.setText("15-20");
                        sys.setText("100-120");
                    }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(4).setTitle("Főablak");
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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