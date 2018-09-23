package hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;


public class RsiActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;

    //adatok almenu
    private TextView rsi_midazolam, rsi_ketamin, rsi_etomidate, rsi_szukci, rsi_rocueti;
    private EditText laringoscopSize, alternativLaringoscopSize,etSize, alternativEtSize,bougieSize, lmaSize, patientWeight;
    private CheckBox KetaminHalfDoseCB;

    //checklista almenu
    private TextView CLKetaDose,CLEtoDose, CLSzukciDose, CLLaringSize, CLAltLarSize, CLETSize, CLBougieSize, CLAltEtSize, CLLmaSize;


    private TextView rsiRocuDose, rsiGyszAlt, rsiGyszAlt02;
    private Button rsi_calculator, call_to_konzulens, rsiRocuBtn;
    private Intent intent;

    String midDoseS, ketaDoseS, etoDoseS, szukciDoseS, rocuDoseEtiS, text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsi);
        overridePendingTransition(R.anim.pull_in_left, R.anim.fade_out);
        collapsingToolbarLayout = findViewById(R.id.collapsingTollbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));



        initRsi();

        initCheckList();

        initRocoMenu();




        callKonzulens();

        rsiCalcBtn();





    }

    public void initRsi(){

        //Az adatok almenűben lévő elemek implementálása

        laringoscopSize = findViewById(R.id.rsi_laringoscopSize);
        alternativLaringoscopSize = findViewById(R.id.rsi_alternativLaringoscopSize);
        etSize = findViewById(R.id.rsi_etSize);
        alternativEtSize = findViewById(R.id.rsi_alternativEtSize);
        bougieSize = findViewById(R.id.rsi_bougieSize);
        lmaSize = findViewById(R.id.rsi_lmaSize);
        patientWeight = findViewById(R.id.rsi_patientWeight);

        KetaminHalfDoseCB = findViewById(R.id.rsi_KetaminHalfDoseCB);

        rsi_calculator = findViewById(R.id.rsi_calculator);

        rsi_midazolam = findViewById(R.id.rsi_midazolam);
        rsi_ketamin = findViewById(R.id.rsi_ketamin);
        rsi_etomidate = findViewById(R.id.rsi_etomidate);
        rsi_szukci = findViewById(R.id.rsi_szukc);
        rsi_rocueti = findViewById(R.id.rsi_rocoeti);
        rsiRocuDose = findViewById(R.id.rsi_rocu_adag);
        rsiRocuBtn = findViewById(R.id.rsi_rocu_button);

        rsi_midazolam.setVisibility(View.INVISIBLE);
        rsi_ketamin.setVisibility(View.INVISIBLE);
        rsi_etomidate.setVisibility(View.INVISIBLE);
        rsi_szukci.setVisibility(View.INVISIBLE);
        rsi_rocueti.setVisibility(View.INVISIBLE);
        rsiRocuDose.setVisibility(View.INVISIBLE);
        rsiRocuBtn.setVisibility(View.INVISIBLE);

    }

    public void etiDoseCalcAndVisible(){

        double minD = Double.parseDouble(patientWeight.getText().toString());
        double ketD = Double.parseDouble(patientWeight.getText().toString());
        double etoD = Double.parseDouble(patientWeight.getText().toString());
        double szukD = Double.parseDouble(patientWeight.getText().toString());
        double rocuD = Double.parseDouble(patientWeight.getText().toString());


        midDoseS = Double.toString(minD *= 0.02);
        ketaDoseS = Double.toString(ketD *= 2.0);
        etoDoseS = Double.toString(etoD *= 0.3);
        szukciDoseS = Double.toString(szukD *= 1.5);
        rocuDoseEtiS = Double.toString(rocuD *= 1);

        if(KetaminHalfDoseCB.isChecked()){
            double temp = Double.parseDouble(ketaDoseS);
            temp/=2;
            ketaDoseS = Double.toString(temp);
        }

        if (ketD > 200.0) {
            ketaDoseS = "200.0";
        }
        if (etoD > 20.0) {
            etoDoseS = "20.0";
        }
        if (szukD > 200.0) {
            szukciDoseS = "200.0";
        }

        rsi_midazolam.setText("A midazolam egyszeri adagja: "+midDoseS+" mg");
        rsi_ketamin.setText("A ketamin adagja az ETI-hoz: "+ketaDoseS+" mg");
        rsi_etomidate.setText("Az etomidate adagja az ETI-hoz: "+etoDoseS+" mg");
        rsi_szukci.setText("A szukcinilkolin adagja az ETI-hoz: "+szukciDoseS+" mg");
        rsi_rocueti.setText("A rocoronium adagja ETI-hoz: "+rocuDoseEtiS+" mg");


        rsi_midazolam.setVisibility(View.VISIBLE);
        rsi_ketamin.setVisibility(View.VISIBLE);
        rsi_etomidate.setVisibility(View.VISIBLE);
        rsi_szukci.setVisibility(View.VISIBLE);
        rsi_rocueti.setVisibility(View.VISIBLE);
        rsiRocuBtn.setVisibility(View.VISIBLE);
        rsiRocuDose.setVisibility(View.VISIBLE);



        rocuD/=2;
         text = String.valueOf(rocuD);
        rsiRocuDose.setText("A Rokuronium 0.5mg/ttkg adagja :"+text+" mg");
        rsiRocuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double temp = Double.parseDouble(text);
                temp/=2;
                rsiRocuDose.setText("A Rokuronium 0.25mg/ttkg adagja: "+Double.toString(temp)+" mg");
                rsiRocuBtn.setEnabled(false);


            }
        });

    }

    public void anesthMed(){

        rsiGyszAlt = findViewById(R.id.rsi_gysz_alt);
        rsiGyszAlt02 = findViewById(R.id.rsi_gysz_alt2);

        //altatáshoz gyógyszerek

        double anesthKeta = Double.parseDouble(patientWeight.getText().toString());
        double anesthProp = Double.parseDouble(patientWeight.getText().toString());
        double anesthFenta = Double.parseDouble(patientWeight.getText().toString());
        double anesthKetahalfDose = anesthKeta * 0.5;
        double anesthPropoPerfusor = anesthProp * 0.3;

        String anesthketaString = Double.toString(anesthKeta);
        String anesthPropoString = Double.toString(anesthProp);
        String anesthKetaHalfDoseString = Double.toString(anesthKetahalfDose);
        String anesthPropoPerfusorString = Double.toString(anesthPropoPerfusor);
        String anesthFentaString = Double.toString(anesthFenta);

        String belowhundredsys = "100 Hgmm alatt: " + anesthKetaHalfDoseString + " - " + anesthketaString + "mg Ketamin bólusban 20 percenként ismétlve; ";
        String abovehundredsys = "100 Hgmm fölött: " + anesthPropoString + "mg/óra Propofol induló dózis perfúzorral (hiányában " +anesthPropoPerfusorString  + "mg bólus " +
                " 5-10 percenként), Fentanyl " + anesthFentaString + " mg 20 percenként ismételve";

        rsiGyszAlt.setText(belowhundredsys);
        rsiGyszAlt02.setText(abovehundredsys);
    }

public void callKonzulens(){

    call_to_konzulens = findViewById(R.id.call_to_konzulens);
    call_to_konzulens.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0680205025"));
            startActivity(intent);
        }
    });
}

public void initCheckList(){

    CLKetaDose = findViewById(R.id.rsi_check_ketamin);
    CLEtoDose = findViewById(R.id.rsi_check_etomidate);
    CLSzukciDose = findViewById(R.id.rsi_check_szukci);
    CLLaringSize = findViewById(R.id.rsi_check_laringSize);
    CLAltLarSize = findViewById(R.id.rsi_check_alternativLaringSize);
    CLETSize = findViewById(R.id.rsi_check_etiSize);
    CLBougieSize = findViewById(R.id.rsi_check_bougieSize);
    CLAltEtSize = findViewById(R.id.rsi_check_alternativETSize);
    CLLmaSize = findViewById(R.id.rsi_check_lmaSize);






}

public void createCheckList(){
    CLKetaDose.setText("Ketamin dózia: "+ketaDoseS+" mg");
    CLKetaDose.setTypeface(null, Typeface.BOLD);
    CLEtoDose.setText("Etomidate dózisa: "+etoDoseS+" mg");
    CLEtoDose.setTypeface(null, Typeface.BOLD);
    CLSzukciDose.setText("Szukcinilkolin dózisa: "+szukciDoseS+" mg");
    CLSzukciDose.setTypeface(null, Typeface.BOLD);
    CLLaringSize.setText("Laringoszkóp lapoc, mérete: "+laringoscopSize.getText().toString());
    CLLaringSize.setTypeface(null, Typeface.BOLD);
    CLAltLarSize.setText("Alternativ lapoc, mérete: "+alternativLaringoscopSize.getText().toString());
    CLAltLarSize.setTypeface(null, Typeface.BOLD);
    CLETSize.setText("ET tubus mérete: "+etSize.getText().toString()+" mm");
    CLETSize.setTypeface(null, Typeface.BOLD);
    CLBougieSize.setText("Bougie mérete: "+bougieSize.getText().toString()+" Ch");
    CLBougieSize.setTypeface(null, Typeface.BOLD);
    CLAltEtSize.setText("Alternatív tubus mérete: "+alternativEtSize.getText().toString()+" mm");
    CLAltEtSize.setTypeface(null, Typeface.BOLD);
    CLLmaSize.setText("LMA, mérete: "+lmaSize.getText().toString());
    CLLmaSize.setTypeface(null, Typeface.BOLD);
}

public void rsiCalcBtn(){

    rsi_calculator.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {

                // ETI-hez való gyógyszerek

                etiDoseCalcAndVisible();

                anesthMed();

                createCheckList();


            } catch (Exception e) {
                rsi_midazolam.setText("Üres mező!");
                rsi_midazolam.setVisibility(View.VISIBLE);
                rsiRocuBtn.setVisibility(View.INVISIBLE);
                rsiRocuDose.setVisibility(View.INVISIBLE);
                rsi_szukci.setVisibility(View.INVISIBLE);
                rsi_etomidate.setVisibility(View.INVISIBLE);
                rsi_ketamin.setVisibility(View.INVISIBLE);
                rsi_rocueti.setVisibility(View.INVISIBLE);
            }
        }
    });
}

public void initRocoMenu(){







}
}
