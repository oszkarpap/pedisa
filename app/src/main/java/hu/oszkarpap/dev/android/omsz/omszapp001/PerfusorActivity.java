package hu.oszkarpap.dev.android.omsz.omszapp001;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import hu.oszkarpap.dev.android.omsz.omszapp001.login.LoginMainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.MedActivity;


public class PerfusorActivity extends AppCompatActivity {

    private Intent intent;
    private Spinner perf_spinner;
    private Button perf_submit, perf_calc;
    private EditText perf_patient_weight, perf_dose;
    private TextView perf_scr, perf_result, perf_ill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfusor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        perf_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String spinnerValue = String.valueOf(perf_spinner.getSelectedItem());

                switch (spinnerValue) {
                    case ("Tonogén"):
                        perf_scr.setText("Adrenalin adásának leírása");
                        perf_dose.setVisibility(View.VISIBLE);
                        perf_patient_weight.setVisibility(View.VISIBLE);
                        perf_ill.setVisibility(View.VISIBLE);
                        perf_calc.setVisibility(View.VISIBLE);
                        perf_result.setVisibility(View.VISIBLE);
                        perf_calc.setEnabled(true);
                        break;
                    default:
                        perf_scr.setText("Még nem lett beírva a többi, csak a Adr");
                }

            }


        });


        perf_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double perf_weight_calc = 0;
                double perf_dose_calc = 0;

                try {
                    perf_weight_calc = Double.parseDouble(perf_patient_weight.getText().toString());
                    perf_dose_calc = Double.parseDouble(perf_dose.getText().toString());
                    double result = perf_dose_calc * perf_weight_calc;
                    String tot = new Double(result).toString();
                    perf_result.setText(tot);

                } catch (Exception e) {
                    perf_result.setText("Üres mező!");
                }
            }
        });
    }


    public void addListenerOnSpinnerItemSelection() {
        perf_spinner = (Spinner) findViewById(R.id.perf_spinner);
        perf_spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        perf_spinner = (Spinner) findViewById(R.id.perf_spinner);
        perf_submit = (Button) findViewById(R.id.perf_submit);
        perf_patient_weight = findViewById(R.id.perf_patient_weight);
        perf_dose = findViewById(R.id.perf_dose);
        perf_scr = findViewById(R.id.perf_scr);
        perf_result = findViewById(R.id.perf_result);
        perf_ill = findViewById(R.id.perf_ill);
        perf_calc = findViewById(R.id.perf_calc);


        perf_dose.setVisibility(View.INVISIBLE);
        perf_patient_weight.setVisibility(View.INVISIBLE);
        perf_ill.setVisibility(View.INVISIBLE);
        perf_calc.setVisibility(View.INVISIBLE);
        perf_result.setVisibility(View.INVISIBLE);

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
            intent = new Intent(PerfusorActivity.this, MedActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_parameters) {
            intent = new Intent(PerfusorActivity.this, ParametersActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_perfusor) {
            intent = new Intent(PerfusorActivity.this, PerfusorActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_settings) {
            intent = new Intent(PerfusorActivity.this, LoginMainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_log_out) {
            intent = new Intent(PerfusorActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}



