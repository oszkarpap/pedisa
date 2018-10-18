package hu.oszkarpap.dev.android.omsz.omszapp001.guide.parameters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MainActivity;


public class ParametersActivity extends MainActivity implements OnQueryTextListener {


    private SeekBar seekBar, seekBarMed;
    private TextView age, hr, lsz, sys, ts, tm, th, bou, tidal, def;

    private RecyclerView recyclerView;
    private ArrayList<ParametersMed> parametersMeds;
    private ParameterMedAdapter adapter;
    private TextView childWeight;



    private String adenozin;
    private String atropin;
    private String betaloc;
    private String cordarone;
    private String magnesium;
    private String verapamil;
    private String fenatyl;
    private String morfin;
    private String naloxon;
    private String ketamin;
    private String algopyrin;
    private String nospa;
    private String nurofen;
    private String nitropohl;
    private String berodual;
    private String rectodelt;
    private String metilprednisolon;
    private String ventolin;
    private String bricanyl;
    private String suprastin;
    private String calcimusc;
    private String dezitin;
    private String seduxen;
    private String midazolam;
    private String epanutin;
    private String etomidate;
    private String propofol;
    private String ebrantil;
    private String furosemid;
    private String tensiomin;
    private String cerucal;
    private String tonogen;
    private String dopamin;
    private String heparin;
    private String glukagen;
    private String suxa;
    private String rocuronium;
    private String arterenol;
    private String esmeron;
    private String lysthenon;
    private String exacyl;
    private String ceftriaxon;
    private SearchView searchView;
    private int progressValue;
    private String glukoz;
    private String tidalvol;
    private String defibrillation;
    private String folyadék;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parameters);

        createMainActivity();
        overridePendingTransition(0, 0);

        seekBar = findViewById(R.id.par_sb);
        age = findViewById(R.id.par_age);
        hr = findViewById(R.id.par_hr);
        lsz = findViewById(R.id.par_lsz);
        sys = findViewById(R.id.par_sys);
        ts = findViewById(R.id.par_testsuly);
        tm = findViewById(R.id.par_tubusmeret);
        th = findViewById(R.id.par_tubushossz);
        bou = findViewById(R.id.par_bougie);
        tidal = findViewById(R.id.par_tidalvol);
        def = findViewById(R.id.par_def);


        seekBarMed = findViewById(R.id.par_med_sb);
        childWeight = findViewById(R.id.par_weight);


        childParameters();
        childMedDose();




    }

    private void childParameters() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                age.setText(String.valueOf(progress));
                tidal.setText(String.valueOf(progress*7));
                def.setText(String.valueOf(progress*4));

                if (progress == 0) {
                    hr.setText("110-160");
                    lsz.setText("30-40");
                    sys.setText("70-90");
                } else if (progress == 1 || progress == 2) {
                    hr.setText("100-150");
                    lsz.setText("25-35");
                    sys.setText("80-95");
                } else if (progress > 2 && progress < 6) {
                    hr.setText("90-140");
                    lsz.setText("25-30");
                    sys.setText("80-100");
                } else if (progress > 5 && progress <= 12) {
                    hr.setText("80-130");
                    lsz.setText("20-25");
                    sys.setText("90-110");
                } else if (progress > 12) {
                    hr.setText("60-100");
                    lsz.setText("15-20");
                    sys.setText("100-120");
                }


                if (progress > -1 && progress < 11) {

                    double value = seekBar.getProgress();


                    ts.setText(String.valueOf((value + 4) * 2));
                    tm.setText(String.valueOf((value / 4) + 4));
                    if(value == 0){
                        ts.setText("3.5kg újszülött, a féléves 7");
                        tm.setText("3.5");
                    }else if(value == 1) {
                        tm.setText("4");
                    }else if(value == 3){
                        tm.setText("4.5");
                    }else if(value == 5){
                        tm.setText("5");
                    }else if (value == 7){
                        tm.setText("6");
                    }else if (value == 9){
                        tm.setText("6.5");
                    }
                    th.setText(String.valueOf((value / 2) + 12));


                }

                Double tmS = Double.parseDouble(tm.getText().toString());

                if (tmS > 6) {
                    bou.setText("15 Ch");
                }
                if (tmS == 5 || tmS == 6) {
                    bou.setText("10 Ch");
                }
                if (tmS < 5) {
                    bou.setText("ne használj bougie-t!");
                }



                /*created by
                 * Oszkar Pap
                 * */


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void childMedDose() {


        parametersMeds = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_par_med);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new ParameterMedAdapter(parametersMeds);
        fillParMed(parametersMeds);

        recyclerView.setAdapter(adapter);

        seekBarMedmethod();

    }


    private void seekBarMedmethod() {


        seekBarMed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                childWeight.setText("Gyermek tömege: " + String.valueOf(progress) + " kg");
                progressValue = progress;

                medString(progressValue);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                parametersMeds.clear();
                fillParMed(parametersMeds);
                adapter.notifyDataSetChanged();


            }
        });
    }

    private void fillParMed(ArrayList<ParametersMed> x) {
        x.add(new ParametersMed("Tidal Volumen: ", tidalvol));
        x.add(new ParametersMed("Defibriláció: ", defibrillation));
        x.add(new ParametersMed("Folyadékpótlás: ", folyadék));
        x.add(new ParametersMed("Adenozin: ", adenozin));
        x.add(new ParametersMed("Algopyrin: ", algopyrin));
        x.add(new ParametersMed("Arterenlol: ", arterenol));
        x.add(new ParametersMed("Atropin: ", atropin));
        x.add(new ParametersMed("Berodual: ", berodual));
        x.add(new ParametersMed("Betaloc: ", betaloc));
        x.add(new ParametersMed("Bricanyl: ", bricanyl));
        x.add(new ParametersMed("Calcimusc: ", calcimusc));
        x.add(new ParametersMed("Calypsol: ", ketamin));
        x.add(new ParametersMed("Ceftriaxon: ", ceftriaxon));
        x.add(new ParametersMed("Cerucal: ", cerucal));
        x.add(new ParametersMed("Cordarone: ", cordarone));
        x.add(new ParametersMed("Diazepan Dezitin: ", dezitin));
        x.add(new ParametersMed("Ebrantil: ", ebrantil));
        x.add(new ParametersMed("Epanutin: ", epanutin));

        x.add(new ParametersMed("Etomidate: ", etomidate));
        x.add(new ParametersMed("Exacyl: ", exacyl));
        x.add(new ParametersMed("Fentanyl: ", fenatyl));
        x.add(new ParametersMed("Furosemid: ", furosemid));
        x.add(new ParametersMed("Glukagen: ", glukagen));
        x.add(new ParametersMed("Glükóz: ", glukoz));
        x.add(new ParametersMed("Heparin: ", heparin));

        x.add(new ParametersMed("Magnézium: ", magnesium));
        x.add(new ParametersMed("Metilprednisolon: ", metilprednisolon));
        x.add(new ParametersMed("Midazolam: ", midazolam));
        x.add(new ParametersMed("Morfin: ", morfin));
        x.add(new ParametersMed("Naloxon: ", naloxon));

        x.add(new ParametersMed("NoSpa: ", nospa));
        x.add(new ParametersMed("Nurofen: ", nurofen));
        x.add(new ParametersMed("Propofol: ", propofol));
        x.add(new ParametersMed("Rectodelt: ", rectodelt));
        x.add(new ParametersMed("Rocuronium: ",rocuronium));
        x.add(new ParametersMed("Seduxen: ", seduxen));
        x.add(new ParametersMed("Suprastin: ", suprastin));
        x.add(new ParametersMed("Suxamethonium: ",suxa));
        x.add(new ParametersMed("Tensiomin: ", tensiomin));
        x.add(new ParametersMed("Tonogén: ", tonogen));
        x.add(new ParametersMed("Ventolin: ", ventolin));
        x.add(new ParametersMed("Verapamil: ", verapamil));
    }

    private void medString(int income) {

        double progress = income;

        adenozin = progress / 10 + " mg iv., sz.e. ism " + 2 * progress / 10 + " mg iv. 2x (0.1mg/ttkg max 6mg ill. 0.2mg/ttkg max 12mg)";
        atropin = 2 * progress / 10 + " mg iv. (0.02mg/ttkg, min 0.1mg)";
        betaloc = 5 * progress / 100 + " - " +   progress / 10 + " mg iv. (0.05-0.1 mg/ttkg)";
        cordarone = 5 * progress + " mg iv (5mg/ttkg, max. aktuális felnőtt dózis)";
        magnesium = 25 * progress + " - " + 50 * progress + " mg iv (25-50mg/ttkg)";
        verapamil = progress / 10 + " - " + 3 * progress / 10 + " mg max 5mg (0.1-0.3 mg/ttkg)";
        fenatyl = progress + " - " + 3 * progress + " ug iv, titrálva (1-3 ug/ttkg)";
        morfin = progress / 100 + " mg iv., im. (0.01 mg/ttkg)";
        naloxon = progress / 100 + " mg iv. (0.01mg/ttkg) sz.e. ism 2-3 min múlva;";
        ketamin = progress / 4 + " - " + progress / 2 + " mg analgesia, " + progress + " - " + 2 * progress + " mg iv. RSI";
        algopyrin = 10 * progress + " mg iv., im. (10mg/ttkg)";
        nospa = progress / 2 + " - " + progress + " mg iv., im., (0.5-1 mg/ttkg) egyes ajánlások szerint kerülendő!";
        nurofen = "1 kúp 9 hónapos kor alatt, 2 kúp 9 hónapos kor felett";

        berodual = "0.5-1-2 ml";
        rectodelt = "30mg 6 éves kor alatt, 60mg felette";
        metilprednisolon = progress + " - " + 2 * progress + " mg iv.(1-2mg/ttkg)";
        ventolin = "0.1 mg ( 1 expozíció)";
        bricanyl = progress * 5 + " - " + progress * 10 + " ug sc.(5-10ug/ttkg)";
        suprastin = progress / 2 + " - " + progress + " iv. (0.5-1 mg/ttkg), max " + 2 * progress + " mg (2mg/ttkg)";
        calcimusc = 10 * progress + " - " + 20 * progress + " mg iv.(10-20mg/ttkg)";
        dezitin = "10-15 kg között 5mg, 15 kg felett 10mg";
        seduxen = progress / 5 + " - " + progress / 2 + " mg iv (0.2-0.5 mg/ttkg), max " + progress + " mg-ig (1mg/ttkg)";
        midazolam = 2 * progress / 100 + " mg iv (0.02mg/ttkg)";
        epanutin = 15 * progress + " mg iv., max " + progress + "mg min";
        etomidate = 15 * progress / 100 + " - " + 30 * progress / 100 + " mg iv.";
        propofol = progress+"mg/óra kezdő dózis (1mg/ttkg/h), perfuzor hiányában "+3*progress/10+" mg bólus (0.3mg/ttkg) 5-10 percenként";
        ebrantil = "később implementálom";
        furosemid = progress / 2 + " - " + progress + "mg iv (0.5-1mg/ttkg,a felnőtt dózist ne haladjuk meg, figyelembe kell venni az indikációt!)";
        tensiomin = progress / 2 + " - " + progress + " mg per os (0.5-1 mg/ttkg po)";
        cerucal = progress / 10 + " mg iv, 14 éves kor alatt megfontolandó, 2 éves kor alatt ne! (0.1 mg/ttkg)";
        tonogen = "CPR: "+10 * progress + " ug iv. (10ug/ttkg); Anaphylaxia: " + progress/1000 + " ug iv (0.001 mg/ttkg iv.), musculáris adagja 6 év alatt 0.15mg im, 6-12 év között 0.3mg im";
        dopamin = progress + " - " + progress * 3 + " - " + progress * 10 + " ug/min vese - béta1 - alfa receptor stimuláló";
        heparin = 50 * progress + " NE iv";
        glukagen = 10 * progress + " - " + 20 * progress + " ug sc., im., iv., BB intox: " + progress * 5 + " - " + progress * 150 + " ug (hypoglikémiában max 0.5-1mg, BB intox 5-150 ug/ttkg(5-10mg iv. a felnőtt dózis))";

        arterenol = 15 * progress / 100 + " - " + 3*progress / 2 + "ml/h perfuzorban (1 apm/50ml -> 20ug/ml(0.05-0.5 ug6ttkg/min))";


        exacyl = 20 * progress + " mg iv (20mg/ttkg)";
        ceftriaxon = 50 * progress + " - " + 100 * progress + " mg iv lassan, max 2gr";
        rocuronium = progress/4+" mg "+progress/2+" mg RSI után,"+progress+" mg iv.RSI-ben alt. izomrelaxánsként";
        suxa = 1.5*progress+" mg iv. RSI";
        glukoz = 2.5*progress+"ml 10%-os (2.5 ml/ttkg)";
        folyadék = 10*progress+" - "+20*progress+"ml (10-20ml/ttkg)";
        defibrillation = 4*progress+" J (4J/ttkg)";
        tidalvol = 7*progress+"ml (7ml/ttkg)";


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_par, menu);
        MenuItem menuItem = menu.findItem(R.id.par_search);

        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Gyógyszer neve:");
        searchView.setOnQueryTextListener(this);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.par_refresh) {
            finish();
            Intent intent = new Intent(ParametersActivity.this, ParametersActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        String Input = s.toLowerCase();

        ArrayList<ParametersMed> newList = new ArrayList<>();
        for (ParametersMed x : parametersMeds) {
            if (x.getMedLabel().toLowerCase().contains(Input)) {
                newList.add(x);
            }
        }

        adapter.updateList(newList);
        seekBarMed.setVisibility(View.INVISIBLE);
        return true;
    }


}