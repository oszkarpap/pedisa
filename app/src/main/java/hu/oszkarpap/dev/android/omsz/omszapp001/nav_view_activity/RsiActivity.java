package hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;


public class RsiActivity extends AppCompatActivity {


    /*created by
     * Oszkar Pap
     * */

    //adatok cardview
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Button rsiCalc;
    private TextView rsiETSizeLabel, rsiETSecSizeLabel, rsiLarSizeLabel, rsiLarSecSizeLabel, rsiPatientLabel, rsiAgeChild;
    private Spinner rsiETSize, rsiETSecSize, rsiLarSize, rsiLarSecSize;
    private EditText rsiPatientWeight;
    private RadioButton KETO, K2, ETO, SZUK, ROKU;
    private SeekBar rsiSeekBar;
    private double childweight, ETchild, ETchildLength, atropin, patientWeightDouble;
    private Button rsiAdultCheck, rsiChildCheck;
    private TextView rsiRocuAdag, rsiRocuAdag2;
    private Button rsiRocuButton, rsiRocuButton2, call_to_konzulens;
    private TextView rsigyszalt, rsigyszalt2;
    private Intent intent;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button rsiOtherKonzBtn, rsiNumberReset;
    private EditText rsiOtherKonzEt;
    private String konzPhoneNumber;

    private int Oraculum;


    //checklista
    private TextView ket, eto, szuk, et, sEt, bougie, lar, slar, lma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsi);
        overridePendingTransition(R.anim.pull_in_left, R.anim.fade_out);
        collapsingToolbarLayout = findViewById(R.id.collapsingTollbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));

        //adatok

        rsiAdultCheck = findViewById(R.id.rsi_adult_btn);
        rsiChildCheck = findViewById(R.id.rsi_child_btn);
        rsiETSizeLabel = findViewById(R.id.rsi_et_label);
        rsiETSecSizeLabel = findViewById(R.id.rsi_et_sec_label);
        rsiLarSizeLabel = findViewById(R.id.rsi_lar_label);
        rsiLarSecSizeLabel = findViewById(R.id.rsi_lar_sec_label);
        rsiPatientLabel = findViewById(R.id.rsi_pat_label);
        rsiETSize = findViewById(R.id.rsi_et_size);
        rsiETSecSize = findViewById(R.id.rsi_et_sec_size);
        rsiLarSize = findViewById(R.id.rsi_lar_size);
        rsiLarSecSize = findViewById(R.id.rsi_lar_sec_size);
        rsiPatientWeight = findViewById(R.id.rsi_patient_weight);
        rsiSeekBar = findViewById(R.id.rsi_child_sb);
        rsiAgeChild = findViewById(R.id.rsi_age_child);
        rsiRocuAdag = findViewById(R.id.rsi_rocu_adag);
        rsiRocuAdag2 = findViewById(R.id.rsi_rocu_adag2);
        rsiRocuButton = findViewById(R.id.rsi_rocu_button);
        rsiRocuButton2 = findViewById(R.id.rsi_rocu_button2);
        rsigyszalt = findViewById(R.id.rsi_gysz_alt);
        rsigyszalt2 = findViewById(R.id.rsi_gysz_alt2);

        rsiCalc = findViewById(R.id.rsi_calc);
        KETO = findViewById(R.id.rsi_KET);
        K2 = findViewById(R.id.rsi_KET_felezett);
        ETO = findViewById(R.id.rsi_ETO);
        SZUK = findViewById(R.id.rsi_SZUK);
        ROKU = findViewById(R.id.rsi_ROKU);
        call_to_konzulens = findViewById(R.id.call_to_konzulens);


        rsiSeekBar.setVisibility(View.INVISIBLE);
        rsiAgeChild.setVisibility(View.INVISIBLE);
        rsiSeekBar.setEnabled(false);
        rsiAgeChild.setEnabled(false);
        rsiRocuButton.setVisibility(View.INVISIBLE);
        rsiRocuButton2.setVisibility(View.INVISIBLE);
        rsiRocuAdag.setVisibility(View.INVISIBLE);
        rsiRocuAdag2.setVisibility(View.INVISIBLE);


        //checklista
        ket = findViewById(R.id.rsi_check_ketamin);
        eto = findViewById(R.id.rsi_check_etomidate);
        szuk = findViewById(R.id.rsi_check_szukci);
        et = findViewById(R.id.rsi_check_etiSize);
        sEt = findViewById(R.id.rsi_check_alternativETSize);
        bougie = findViewById(R.id.rsi_check_bougieSize);
        lar = findViewById(R.id.rsi_check_laringSize);
        slar = findViewById(R.id.rsi_check_alternativLaringSize);
        lma = findViewById(R.id.rsi_check_lmaSize);


        rsiETSize.setVisibility(View.VISIBLE);
        rsiETSecSize.setVisibility(View.VISIBLE);
        rsiLarSize.setVisibility(View.VISIBLE);
        rsiLarSecSize.setVisibility(View.VISIBLE);
        rsiPatientWeight.setVisibility(View.VISIBLE);
        rsiETSizeLabel.setVisibility(View.VISIBLE);
        rsiETSecSizeLabel.setVisibility(View.VISIBLE);
        rsiLarSizeLabel.setVisibility(View.VISIBLE);
        rsiLarSecSizeLabel.setVisibility(View.VISIBLE);
        rsiPatientLabel.setVisibility(View.VISIBLE);


        rsiOtherKonzBtn = findViewById(R.id.rsi_other_konz_btn);
        rsiOtherKonzEt = findViewById(R.id.rsi_other_konz_et);
        sharedPreferences = getSharedPreferences(null, Context.MODE_PRIVATE);
        rsiNumberReset = findViewById(R.id.rsi_number_reset);






        konzulensCallMethod();

        Oraculum = 0;
        rsicalcMethod();

        rsiRokuButtonMethod();
        rsiChildButtonMethod();
        rsiAdultButtonMethod();







    }

    public void rsicalcMethod() {
        rsiCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    //checklista et  es altEt beállítása

                    String EtMeretString = rsiETSize.getSelectedItem().toString();
                    String EtAltMeretString = rsiETSecSize.getSelectedItem().toString();
                    if (Oraculum == 1) {
                        et.setText("ET mérete: " + String.valueOf(ETchild) + "mm, a fogsornál " + String.valueOf(ETchildLength) + " cm kell lennie");
                        sEt.setText("Cuffos ET mérete: " + String.valueOf(ETchild + 0.5) + " mm, Az Atropin adagja " + atropin + " mg");
                        et.setTypeface(null, Typeface.BOLD);
                        sEt.setTypeface(null, Typeface.BOLD);
                    } else {
                        et.setText("ET tubus mérete: " + EtMeretString);
                        et.setTypeface(null, Typeface.BOLD);
                        sEt.setText("Alternativ ET mérete: " + EtAltMeretString);
                        sEt.setTypeface(null, Typeface.BOLD);
                    }

                    //checklista bougie beállítása

                    double EtMeretDouble;
                    if (Oraculum == 1) {
                        EtMeretDouble = ETchild;
                    } else {
                        EtMeretDouble = Double.parseDouble(rsiETSize.getSelectedItem().toString());
                    }
                    if (EtMeretDouble > 6) {
                        bougie.setText("Bougie mérete: 15 Ch");
                    } else if (EtMeretDouble <= 6 && EtMeretDouble >= 5) {
                        bougie.setText("Bougie mérete: 10Ch");
                    } else {
                        bougie.setText("Bougie mérete: ne használjon Bougie-t");
                    }
                    bougie.setTypeface(null, Typeface.BOLD);


                    //checklista LMA beállítása

                    if (Oraculum == 1) {
                        patientWeightDouble = childweight;
                    } else {
                        patientWeightDouble = Double.parseDouble(rsiPatientWeight.getText().toString());
                    }
                    if (patientWeightDouble >= 100) {
                        lma.setText("LMA, mérete: 6");
                    } else if (patientWeightDouble >= 70) {
                        lma.setText("LMA, mérete: 5");
                    } else if (patientWeightDouble >= 50) {
                        lma.setText("LMA mérete: 4");
                    } else if (patientWeightDouble >= 30) {
                        lma.setText("LMA, mérete: 3");
                    } else if (patientWeightDouble >= 20) {
                        lma.setText("LMA, mérete: 2.5");
                    } else if (patientWeightDouble >= 10) {
                        lma.setText("LMA, mérete: 2.0");
                    } else if (patientWeightDouble >= 5) {
                        lma.setText("LMA, mérete: 1.5");
                    } else lma.setText("LMA, mérete: 1.0");

                    lma.setTypeface(null, Typeface.BOLD);

                    //checklista lar és altlar beállítása
                    if (Oraculum == 1) {
                        lar.setText("Laringoskóp, mérete: 3");
                        lar.setTypeface(null, Typeface.BOLD);
                        slar.setText("Alternativ lapoc, mérete: 2");
                        slar.setTypeface(null, Typeface.BOLD);
                    } else {
                        lar.setText("Laringoskóp, mérete: " + rsiLarSize.getSelectedItem().toString());
                        lar.setTypeface(null, Typeface.BOLD);
                        slar.setText("Alternativ lapoc, mérete: " + rsiLarSecSize.getSelectedItem().toString());
                        slar.setTypeface(null, Typeface.BOLD);
                    }
                    //chechlista indukciószer beálllítása


                    if (KETO.isChecked()) {
                        double ind;
                        ind = 2 * patientWeightDouble;
                        if (ind > 200) {
                            ket.setText("Ketamin dózisa: 200mg (maximum dózis)");
                            eto.setText("Etomidate: ");
                            eto.setTypeface(null, Typeface.NORMAL);
                            ket.setTypeface(null, Typeface.BOLD);
                        } else {
                            ket.setText("Ketamin dózisa: " + String.valueOf(ind) + " mg");
                            eto.setText("Etomidate: ");
                            eto.setTypeface(null, Typeface.NORMAL);
                            ket.setTypeface(null, Typeface.BOLD);
                        }
                    } else if (K2.isChecked()) {
                        double indhalf;
                        indhalf = patientWeightDouble;
                        if (indhalf > 200) {
                            ket.setText("Ketamin dózisa: 200mg (maximum dózis)");
                            eto.setText("Etomidate: ");
                            eto.setTypeface(null, Typeface.NORMAL);
                            ket.setTypeface(null, Typeface.BOLD);
                        } else {
                            ket.setText("Ketamin dózisa: " + String.valueOf(indhalf) + " mg");
                            eto.setText("Etomidate: ");
                            eto.setTypeface(null, Typeface.NORMAL);
                            ket.setTypeface(null, Typeface.BOLD);
                        }
                    } else if (ETO.isChecked()) {
                        double etodouble;
                        etodouble = 3 * patientWeightDouble / 10;
                        //  etodouble = (Math.round(etodouble*100))/100;
                        if (etodouble > 20) {
                            eto.setText("Etomidate adagja 20 mg (maximum dózis)");
                            ket.setText("Ketomidate: ");
                            ket.setTypeface(null, Typeface.NORMAL);
                            eto.setTypeface(null, Typeface.BOLD);
                        } else {
                            eto.setText("Etomidate dózisa: " + String.valueOf(etodouble) + " mg");
                            ket.setText("Ketomidate: ");
                            ket.setTypeface(null, Typeface.NORMAL);
                            eto.setTypeface(null, Typeface.BOLD);
                        }
                    }

                    double izomr;
                    if (SZUK.isChecked()) {
                        izomr = 15 * patientWeightDouble / 10;
                        if (izomr > 200) {
                            szuk.setText("Szukcinilkolin adagja: 200mg (maximum dózis)");
                            szuk.setTypeface(null, Typeface.BOLD);
                        } else {
                            szuk.setText("Szukcinilkolin adagja: " + izomr + " mg");
                            szuk.setTypeface(null, Typeface.BOLD);
                        }
                    } else {
                        izomr = patientWeightDouble;
                        szuk.setText("Rokuronium adagja: " + izomr + " mg");
                        szuk.setTypeface(null, Typeface.BOLD);

                    }

                    //roku beállítása

                    double rocuDouble = 5 * patientWeightDouble / 10;
                    rsiRocuAdag.setText("Rocuronium adagja: " + String.valueOf(rocuDouble) + " mg");
                    rsiRocuAdag.setVisibility(View.VISIBLE);
                    rsiRocuButton.setVisibility(View.VISIBLE);

                    rsigyszalt.setText("100 Hgmm alatt: Ketamin " + 5 * patientWeightDouble / 10 + " - " + patientWeightDouble + " mg bólusokban 20 percenként ismételve");
                    rsigyszalt2.setText("100 Hgmm felett: Propofol " + patientWeightDouble + " mg/ óra induló dózissal perfuzorban (ennek" +
                            " hiányában " + 3 * patientWeightDouble / 10 + " mg bólusok 5-10 percenként), valamint Fentanyl " + patientWeightDouble + "ug ( 20 percenként ismételve ) bólusokban");

                } catch (Exception e) {


                    Toast.makeText(RsiActivity.this, "Adja meg a beteg ttkg-ját!", Toast.LENGTH_LONG).show();


                }

            }


        });

    }





    public void rsiRokuButtonMethod(){
        rsiRocuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rsiRocuAdag.setVisibility(View.INVISIBLE);
                rsiRocuButton.setVisibility(View.INVISIBLE);
                rsiRocuAdag2.setText("Rocuronium felezett adagja: " + String.valueOf(patientWeightDouble * 0.25) + " mg");
                rsiRocuAdag2.setVisibility(View.VISIBLE);
                rsiRocuButton2.setVisibility(View.VISIBLE);
            }
        });

        rsiRocuButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rsiRocuButton2.setVisibility(View.INVISIBLE);
                rsiRocuAdag2.setVisibility(View.INVISIBLE);
                rsiRocuAdag.setVisibility(View.VISIBLE);
                rsiRocuButton.setVisibility(View.VISIBLE);
                rsiRocuAdag.setText("Rocuronium adagja: " + String.valueOf(patientWeightDouble * 0.5) + " mg");
            }
        });

    }

    public void rsiChildButtonMethod(){

        rsiChildCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Oraculum = 1;
                rsiSeekBar.setVisibility(View.VISIBLE);
                rsiSeekBar.setEnabled(true);
                rsiAgeChild.setVisibility(View.VISIBLE);
                rsiAgeChild.setEnabled(true);
                rsiETSize.setVisibility(View.INVISIBLE);
                rsiETSize.setEnabled(false);
                rsiETSecSize.setVisibility(View.INVISIBLE);
                rsiETSecSize.setEnabled(false);
                rsiLarSize.setVisibility(View.INVISIBLE);
                rsiLarSize.setEnabled(false);
                rsiLarSecSize.setVisibility(View.INVISIBLE);
                rsiLarSecSize.setEnabled(false);
                rsiPatientWeight.setVisibility(View.INVISIBLE);
                rsiPatientWeight.setEnabled(false);
                rsiETSizeLabel.setVisibility(View.INVISIBLE);
                rsiETSecSizeLabel.setEnabled(false);
                rsiETSecSizeLabel.setVisibility(View.INVISIBLE);
                rsiETSizeLabel.setEnabled(false);
                rsiLarSizeLabel.setVisibility(View.INVISIBLE);
                rsiLarSizeLabel.setEnabled(false);
                rsiLarSecSizeLabel.setVisibility(View.INVISIBLE);
                rsiLarSecSizeLabel.setEnabled(false);
                rsiPatientLabel.setVisibility(View.INVISIBLE);
                rsiPatientLabel.setEnabled(false);
                rsiRocuAdag2.setVisibility(View.INVISIBLE);

                rsiRocuButton2.setVisibility(View.INVISIBLE);


                rsiSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        double age = progress + 1;
                        childweight = (age + 4) * 2;
                        rsiAgeChild.setText("A gyermek életkora: " + age + " év, testsúlya: " + childweight + " kg");
                        ETchild = (age + 4.0) / 4.0;
                        ETchildLength = (age / 2) + 12;
                        atropin = 0.02 * childweight;


                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                rsicalcMethod();

            }
        });
    }

    public void rsiAdultButtonMethod(){
        rsiAdultCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Oraculum = 0;
                rsiSeekBar.setVisibility(View.INVISIBLE);
                rsiSeekBar.setEnabled(false);
                rsiAgeChild.setVisibility(View.INVISIBLE);
                rsiAgeChild.setEnabled(false);
                rsiETSize.setVisibility(View.VISIBLE);
                rsiETSize.setEnabled(true);
                rsiETSecSize.setVisibility(View.VISIBLE);
                rsiETSecSize.setEnabled(true);
                rsiLarSize.setVisibility(View.VISIBLE);
                rsiLarSize.setEnabled(true);
                rsiLarSecSize.setVisibility(View.VISIBLE);
                rsiLarSecSize.setEnabled(true);
                rsiPatientWeight.setVisibility(View.VISIBLE);
                rsiPatientWeight.setEnabled(true);
                rsiETSizeLabel.setVisibility(View.VISIBLE);
                rsiETSizeLabel.setEnabled(true);
                rsiETSecSizeLabel.setVisibility(View.VISIBLE);
                rsiETSecSizeLabel.setEnabled(true);
                rsiLarSizeLabel.setVisibility(View.VISIBLE);
                rsiLarSizeLabel.setEnabled(true);
                rsiLarSecSizeLabel.setVisibility(View.VISIBLE);
                rsiLarSecSizeLabel.setEnabled(true);
                rsiPatientLabel.setVisibility(View.VISIBLE);
                rsiPatientLabel.setEnabled(true);

                rsicalcMethod();

            }
        });

    }

    public void konzulensCallMethod(){

        konzPhoneNumber = "0680205025";
        editor = sharedPreferences.edit();
        editor.commit();


        call_to_konzulens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_DIAL);
                if ( sharedPreferences.getString("NewPhoneNumber","1") == "1") {
                    intent.setData(Uri.parse("tel:" + konzPhoneNumber));
                } else {
                    intent.setData(Uri.parse("tel:"+sharedPreferences.getString("NewPhoneNumber","1")));
                }
                startActivity(intent);
            }
        });


        rsiOtherKonzBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!rsiOtherKonzEt.getText().toString().matches("")){
                    try {


                        editor.putString("NewPhoneNumber", rsiOtherKonzEt.getText().toString());
                        editor.commit();
                        rsiOtherKonzEt.setText(null);
                        Toast.makeText(RsiActivity.this, "Az RSI konzulens száma megváltozott", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(RsiActivity.this, "Nem adott meg új számot!", Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(RsiActivity.this, "Nem adott meg új telefonszámot!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        rsiNumberReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    editor.clear();
                    editor.commit();
                    Toast.makeText(RsiActivity.this, "Eredeti Konzulens telefonszám visszaállítva!", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(RsiActivity.this, "Eredeti a Konzulens telefonszáma!", Toast.LENGTH_SHORT).show();
                }}
        });

    }
}
