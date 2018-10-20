
package hu.oszkarpap.dev.android.omsz.omszapp001.left;


import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

/**
 * @author Oszkar Pap
 * @version 1.0
 * @see AbcdeActivity
 *
 * This is the vein punction giudeline
 */

public class VeinActivity extends AbcdeActivity {

    private Spinner veinSpinner;
    private EditText veinDose, veinTime;
    private Button veinDoseCalc, veinTimeClac;
    private TextView veinResult;
    private double kanul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vein);
        createActivity();

        veinSpinner = findViewById(R.id.vein_calc_spinner);
        veinDose = findViewById(R.id.vein_dose);
        veinTime = findViewById(R.id.vein_time);
        veinDoseCalc = findViewById(R.id.vein_dose_calc);
        veinTimeClac = findViewById(R.id.vein_time_calc);
        veinResult = findViewById(R.id.vein_result);

        time();

        dose();



    }

    /**
     * this method calculate how many ml infusion give with selected branule in time
     *
     * */
    public void time(){

        veinTimeClac.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    spinnerValue();
                    double time = Double.parseDouble(veinTime.getText().toString());
                    double result = Math.round(time * kanul);
                    String t1 = Double.toString(result) + getString(R.string.give_ml_into_vein_activity) + time + getString(R.string.in_minutes_vein_activity);
                    veinResult.setText(t1);
                    veinResult.setTypeface(null,Typeface.BOLD);
                } catch (Exception e) {
                    String t2 =getString(R.string.exception_vein_activity);
                    veinResult.setText(t2);
                }
            }
        });
    }

    /**
     * Select type of branule
     * */
    public void spinnerValue() {
        String spinnerValue = String.valueOf(veinSpinner.getSelectedItem());
        kanul = 0;
        if (spinnerValue.contains(getString(R.string.G24))) {
            kanul = 20;
        } else if (spinnerValue.contains(getString(R.string.G22))) {
            kanul = 30;
        } else if (spinnerValue.contains(getString(R.string.G20))) {
            kanul = 55;
        } else if (spinnerValue.contains(getString(R.string.G18))) {
            kanul = 100;
        } else if (spinnerValue.contains(getString(R.string.G16))) {
            kanul = 180;
        } else {
            kanul = 270;
        }
    }

    /**
     * this method calculate how many minutes give insert ml infusion with selected branule
     * */

    public void dose(){

        veinDoseCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    spinnerValue();
                    double dose = Double.parseDouble(veinDose.getText().toString());
                    double result = Math.round(dose / kanul);
                    String t3=Double.toString(result) + getString(R.string.min_give_into_ml_vein_activity) + dose + getString(R.string.ml_infusion_vein_activity);
                    veinResult.setText(t3);
                    veinResult.setTypeface(null, Typeface.BOLD);
                } catch (Exception e) {
                    veinResult.setText(R.string.exception_vein_activity);
                }

            }
        });
    }
}
