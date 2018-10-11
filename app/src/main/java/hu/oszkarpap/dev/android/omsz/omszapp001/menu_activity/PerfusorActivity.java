package hu.oszkarpap.dev.android.omsz.omszapp001.menu_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MainActivity;

public class PerfusorActivity extends MainActivity {

    private Spinner perf_spinner;
    private Button perf_chose, perf_calc;
    private EditText perf_patient_weight, perf_dose, perf_db, perf_pack01, perf_pack02, perf_dilution;
    private TextView perf_scr, perf_mg, perf_ml, perf_result, perf_db_label, perf_kg, perf_per;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfusor);
        createMainActivity();
        overridePendingTransition(0, 0);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        perf_chose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String spinnerValue = String.valueOf(perf_spinner.getSelectedItem());

                switch (spinnerValue) {
                    case ("Tonogén"):
                        perf_scr.setText("Adrenalin adásának leírása");
                        setVisibilityOther();
                        break;
                    case ("Noradrenalin"):
                        perf_scr.setText("Noradrenalin adásának leírása");
                        setVisibilityOther();
                        break;
                    case ("Dopamin"):
                        perf_scr.setText("Dopamin adásának leírása");
                        setVisibilityOther();
                        break;
                    case ("NitroPohl"):
                        perf_scr.setText("Nitropohl adásának leírása");
                        setVisibilityOther();
                        break;
                    case ("Propofol"):
                        perf_scr.setText("Propofol adásának leírása");
                        setVisibilityOther();
                        break;
                    case ("Cordarone"):
                        perf_scr.setText("Cordarone adásának leírása");
                        setVisibilityOther();
                        break;
                    default:
                        perf_scr.setText("Készítmény kiválasztása kötelező");
                }

            }


        });




        /*created by
         * Oszkar Pap
         * */


        perf_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double weight = 0;
                double dose = 0;
                double pack01 = 0;
                double pack02 = 0;
                double dilution = 0;

                try {
                    weight = Double.parseDouble(perf_patient_weight.getText().toString());
                    dose = Double.parseDouble(perf_dose.getText().toString());
                    pack01 = Double.parseDouble(perf_pack01.getText().toString());
                    pack02 = Double.parseDouble(perf_pack02.getText().toString());
                    dilution = Double.parseDouble(perf_dilution.getText().toString());


                    perf_result.setText("Még nem írtam be a logikát!");

                } catch (Exception e) {
                    perf_result.setText("Üres mező!");
                }
            }
        });
    }


    public void addListenerOnSpinnerItemSelection() {
        perf_spinner =  findViewById(R.id.perf_spinner);
        perf_spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public void addListenerOnButton() {

        perf_spinner =  findViewById(R.id.perf_spinner);
        perf_chose =  findViewById(R.id.perf_submit);
        perf_patient_weight = findViewById(R.id.perf_patient_weight);
        perf_dose = findViewById(R.id.perf_dose);
        perf_scr = findViewById(R.id.perf_scr);
        perf_result = findViewById(R.id.perf_result);
        perf_pack01 = findViewById(R.id.perf_pack01);
        perf_pack02 = findViewById(R.id.perf_pack02);
        perf_dilution = findViewById(R.id.perf_dilution);
        perf_calc = findViewById(R.id.perf_calc);
        perf_mg = findViewById(R.id.perf_mg);
        perf_ml = findViewById(R.id.perf_ml);
        perf_db = findViewById(R.id.perf_db);
        perf_per = findViewById(R.id.perf_per);
        perf_kg = findViewById(R.id.perf_kg);
        perf_db_label = findViewById(R.id.perf_db_label);


        perf_dose.setVisibility(View.INVISIBLE);
        perf_patient_weight.setVisibility(View.INVISIBLE);
        perf_calc.setVisibility(View.INVISIBLE);
        perf_result.setVisibility(View.INVISIBLE);
        perf_pack02.setVisibility(View.INVISIBLE);
        perf_pack01.setVisibility(View.INVISIBLE);
        perf_dilution.setVisibility(View.INVISIBLE);
        perf_scr.setVisibility(View.INVISIBLE);
        perf_mg.setVisibility(View.INVISIBLE);
        perf_ml.setVisibility(View.INVISIBLE);
        perf_db.setVisibility(View.INVISIBLE);
        perf_per.setVisibility(View.INVISIBLE);
        perf_kg.setVisibility(View.INVISIBLE);
        perf_db_label.setVisibility(View.INVISIBLE);


    }

    public void setVisibilityOther(){
        perf_dose.setVisibility(View.VISIBLE);
        perf_patient_weight.setVisibility(View.VISIBLE);
        perf_calc.setVisibility(View.VISIBLE);
        perf_result.setVisibility(View.VISIBLE);
        perf_pack01.setVisibility(View.VISIBLE);
        perf_pack02.setVisibility(View.VISIBLE);
        perf_dilution.setVisibility(View.VISIBLE);
        perf_mg.setVisibility(View.VISIBLE);
        perf_ml.setVisibility(View.VISIBLE);
        perf_scr.setVisibility(View.VISIBLE);
        perf_db.setVisibility(View.VISIBLE);
        perf_per.setVisibility(View.VISIBLE);
        perf_kg.setVisibility(View.VISIBLE);
        perf_db_label.setVisibility(View.VISIBLE);

        perf_calc.setEnabled(true);
    }



}



