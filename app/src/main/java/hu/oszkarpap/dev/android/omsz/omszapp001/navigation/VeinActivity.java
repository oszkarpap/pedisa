package hu.oszkarpap.dev.android.omsz.omszapp001.navigation;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

public class VeinActivity extends AbcdeActivity {

    private Spinner veinSpinner;
    private EditText veinDose, veinTime;
    private Button veinDoseCalc, veinTimeClac;
    private TextView veinResult;
    private String spinnerValue;
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

        veinTimeClac.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    spinnerValue();
                    double time = Double.parseDouble(veinTime.getText().toString());
                    double result = Math.round(time * kanul);
                    veinResult.setText(Double.toString(result) + " ml adható be " + time + " perc alatt");
                    veinResult.setTypeface(null,Typeface.BOLD);
                } catch (Exception e) {
                    veinResult.setText("Legalább egy mező kitöltése kötelező, és a mellette lévő gomb megnyomandó");
                }
            }
        });

        veinDoseCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    spinnerValue();
                    double dose = Double.parseDouble(veinDose.getText().toString());
                    double result = Math.round(dose / kanul);
                    veinResult.setText(Double.toString(result) + " perc alatt adható be " + dose + " ml folyadék");
                    veinResult.setTypeface(null, Typeface.BOLD);
                } catch (Exception e) {
                    veinResult.setText("Legalább egy mező kitöltése kötelező, és a mellette lévő gomb megnyomandó");
                }

            }
        });

    }

    public void spinnerValue() {
        spinnerValue = String.valueOf(veinSpinner.getSelectedItem());
        kanul = 0;
        if (spinnerValue.contains("24G Sárga")) {
            kanul = 20;
        } else if (spinnerValue.contains("22G Kék")) {
            kanul = 30;
        } else if (spinnerValue.contains("20G Rózsaszín")) {
            kanul = 55;
        } else if (spinnerValue.contains("18G Zöld")) {
            kanul = 100;
        } else if (spinnerValue.contains("16G Szürke")) {
            kanul = 180;
        } else {
            kanul = 270;
        }
    }
}
