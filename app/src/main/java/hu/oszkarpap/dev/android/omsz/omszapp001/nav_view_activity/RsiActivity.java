package hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;


public class RsiActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private CheckBox checkBox;
    private EditText midazolamEt, rsi_lar, rsi_altlar, rsi_bou, rsi_et, rsi_altet, rsi_lma;
    private TextView clet, claltet, clbou, cll, clal, cllma, clketa, cleto, clszu;
    private TextView midazolamTv, midazolamMg, midazolam, ketamin, etomidate, szukci, ketaminMg, etomidateMg, szukciMg, ketoD, etoD, szukciD;
    private Button midazolamBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsi);
        overridePendingTransition(R.anim.pull_in_left, R.anim.fade_out);
        collapsingToolbarLayout = findViewById(R.id.collapsingTollbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));

        checkBox = findViewById(R.id.rsi_checkbox);

        midazolamEt = findViewById(R.id.rsi_midazolam_et);
        midazolamTv = findViewById(R.id.rsi_midazolam_tv);
        rsi_lar = findViewById(R.id.rsi_laring);
        rsi_altlar = findViewById(R.id.rsi_altLaring);
        rsi_bou = findViewById(R.id.rsi_bugie);
        rsi_et = findViewById(R.id.rsi_et);
        rsi_altet = findViewById(R.id.rsi_altet);
        rsi_lma = findViewById(R.id.rsi_lma);


        midazolam = findViewById(R.id.rsi_midazolam);
        ketamin = findViewById(R.id.rsi_ketamin);
        etomidate = findViewById(R.id.rsi_etomidate);
        szukci = findViewById(R.id.rsi_szukc);

        ketoD = findViewById(R.id.rsi_ket_dose);
        etoD = findViewById(R.id.rsi_eto_dose);
        szukciD = findViewById(R.id.rsi_szukci_dose);


        midazolamBtn = findViewById(R.id.rsi_midazolam_btn);
        midazolamMg = findViewById(R.id.rsi_midazolam_mg);

        ketaminMg = findViewById(R.id.rsi_ketamin_mg);
        etomidateMg = findViewById(R.id.rsi_etomidate_mg);
        szukciMg = findViewById(R.id.rsi_szukc_mg);

        clet = findViewById(R.id.rsi_l_et);
        claltet = findViewById(R.id.rsi_l_altet);
        clbou = findViewById(R.id.rsi_l_bou);
        cll = findViewById(R.id.rsi_l_lar);
        clal = findViewById(R.id.rsi_l_altl);
        cllma = findViewById(R.id.rsi_l_lma);
        clketa = findViewById(R.id.rsi_l_ket);
        cleto = findViewById(R.id.rsi_l_eto);
        clszu = findViewById(R.id.rsi_l_szu);

        midazolamMg.setVisibility(View.INVISIBLE);
        ketaminMg.setVisibility(View.INVISIBLE);
        etomidateMg.setVisibility(View.INVISIBLE);
        szukciMg.setVisibility(View.INVISIBLE);
        midazolam.setVisibility(View.INVISIBLE);
        ketamin.setVisibility(View.INVISIBLE);
        etomidate.setVisibility(View.INVISIBLE);
        szukci.setVisibility(View.INVISIBLE);
        ketoD.setVisibility(View.INVISIBLE);
        etoD.setVisibility(View.INVISIBLE);
        szukciD.setVisibility(View.INVISIBLE);


        midazolamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    double midDose = Double.parseDouble(midazolamEt.getText().toString());
                    double midKeta = Double.parseDouble(midazolamEt.getText().toString());
                    double midEto = Double.parseDouble(midazolamEt.getText().toString());
                    double midSzukci = Double.parseDouble(midazolamEt.getText().toString());


                        String midDoseS = Double.toString(midDose *= 0.02);

                        String etoDoseS = Double.toString(midEto *= 0.3);
                        String szukciDoseS = Double.toString(midSzukci *= 1.5);
                    if(checkBox.isChecked()){
                        midKeta/=2;
                    }
                    String ketaDoseS = Double.toString(midKeta *= 2);

                    if(midKeta>200.0) {ketaDoseS = "200.0";
                    }
                    if(midEto>20.0) {etoDoseS = "20.0";
                    }
                    if(midSzukci>200.0) {szukciDoseS = "200.0";
                    }

                    midazolamTv.setText(midDoseS);
                    ketamin.setText(ketaDoseS);
                    etomidate.setText(etoDoseS);
                    szukci.setText(szukciDoseS);


                    midazolam.setVisibility(View.VISIBLE);
                    midazolamMg.setVisibility(View.VISIBLE);
                    midazolamMg.setVisibility(View.VISIBLE);
                    ketaminMg.setVisibility(View.VISIBLE);
                    etomidateMg.setVisibility(View.VISIBLE);
                    szukciMg.setVisibility(View.VISIBLE);
                    midazolam.setVisibility(View.VISIBLE);
                    ketamin.setVisibility(View.VISIBLE);
                    etomidate.setVisibility(View.VISIBLE);
                    szukci.setVisibility(View.VISIBLE);
                    ketoD.setVisibility(View.VISIBLE);
                    etoD.setVisibility(View.VISIBLE);
                    szukciD.setVisibility(View.VISIBLE);
//////////////////////////////////////////////////////////////////

                    String editTextValue01 = rsi_et.getText().toString();
                    clet.setText(editTextValue01);
                    String editTextValue02 = rsi_altet.getText().toString();
                    claltet.setText(editTextValue02);
                    String editTextValue03 = rsi_lar.getText().toString();
                    cll.setText(editTextValue03);
                    String editTextValue04 = rsi_altlar.getText().toString();
                    clal.setText(editTextValue04);
                    String editTextValue05 = rsi_lma.getText().toString();
                    cllma.setText(editTextValue05);
                    String editTextValue06 = ketamin.getText().toString();
                    clketa.setText(editTextValue06);
                    String editTextValue07 = etomidate.getText().toString();
                    cleto.setText(editTextValue07);
                    String editTextValue08 = szukci.getText().toString();
                    clszu.setText(editTextValue08);
                    String editTextValue09 = rsi_bou.getText().toString();
                    clbou.setText(editTextValue09);


                } catch (Exception e) {
                    midazolamTv.setText("Üres mező!");
                    midazolamMg.setVisibility(View.INVISIBLE);
                    ketaminMg.setVisibility(View.INVISIBLE);
                    etomidateMg.setVisibility(View.INVISIBLE);
                    szukciMg.setVisibility(View.INVISIBLE);
                    midazolam.setVisibility(View.INVISIBLE);
                    ketamin.setVisibility(View.INVISIBLE);
                    etomidate.setVisibility(View.INVISIBLE);
                    szukci.setVisibility(View.INVISIBLE);
                    ketoD.setVisibility(View.INVISIBLE);
                    etoD.setVisibility(View.INVISIBLE);
                    szukciD.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
}
