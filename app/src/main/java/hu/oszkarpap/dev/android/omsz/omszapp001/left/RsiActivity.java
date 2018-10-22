package hu.oszkarpap.dev.android.omsz.omszapp001.left;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MainActivity;

import static hu.oszkarpap.dev.android.omsz.omszapp001.main.MainActivity.KONZULENS_SZAMA;

/**
 * @author Oszkar Pap
 * @version 1.0
 * @see AbcdeActivity
 * <p>
 * This is the rapid sequence intubation giudeline
 */

public class RsiActivity extends AbcdeActivity {

    /**
     * create variable
     */

    private Button CalculatorBtn, AdultBtn, ChildBtn, CallKonzulensBtn, changeRokuBtn01;

    private TextView etSizeTv, secEtSizeTv, larSizeTv, secLarSizeTv, patientTv,
            childAgeTv, rokuDose01Tv, transportMed01Tv, transportMed02Tv,
            ketaminTv, etomidatetv, atropintv, szukcinilkolinTv, etChecklistTv,
            secEtChecklistTv, bougieTv, larChecklistTv, secLarChecklistTv, lmaTv;

    private Spinner EtSizeSpnr, SecEtSizeSpnr, LarSizeSpnr, SecLarSizeSpnr;

    private EditText AdultweightEt;

    private RadioButton ketaminRb, halfKetaminRb, etomidateRb, szukcinilkolinRb, nullRb;

    private SeekBar childSb;

    private double childWeightD, etChildD, etChildLengthD, atropinD, patientWeightD, counter;


    private Intent intent;

    private int observerI;

    private String konzulensPhoneNumberS;

    /**
     * MainActivity.SAS set the layout. The RSI activity start in left menu created full guideline,
     * in right menu the RSI is decrease protocol
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getStringExtra(MainActivity.SAS).equals("01")) {
            setContentView(R.layout.activity_rsi_menu);
        } else if (getIntent().getStringExtra(MainActivity.SAS).equals("02")) {

            setContentView(R.layout.activity_rsi);
        }


        createActivity();

        /*
         * Data panel
         * */
        AdultBtn = findViewById(R.id.rsi_adult_btn);
        ChildBtn = findViewById(R.id.rsi_child_btn);

        etSizeTv = findViewById(R.id.rsi_et_label);
        secEtSizeTv = findViewById(R.id.rsi_et_sec_label);
        larSizeTv = findViewById(R.id.rsi_lar_label);
        secLarSizeTv = findViewById(R.id.rsi_lar_sec_label);
        patientTv = findViewById(R.id.rsi_pat_label);

        EtSizeSpnr = findViewById(R.id.rsi_et_size);
        SecEtSizeSpnr = findViewById(R.id.rsi_et_sec_size);
        LarSizeSpnr = findViewById(R.id.rsi_lar_size);
        SecLarSizeSpnr = findViewById(R.id.rsi_lar_sec_size);

        AdultweightEt = findViewById(R.id.rsi_patient_weight);

        childSb = findViewById(R.id.rsi_child_sb);

        childAgeTv = findViewById(R.id.rsi_age_child);

        ketaminRb = findViewById(R.id.rsi_KET);
        halfKetaminRb = findViewById(R.id.rsi_KET_felezett);
        etomidateRb = findViewById(R.id.rsi_ETO);

        szukcinilkolinRb = findViewById(R.id.rsi_SZUK);
        nullRb = findViewById(R.id.rsi_Null);


        CalculatorBtn = findViewById(R.id.rsi_calc);

        /*
         * Konzulens calling Button
         * */
        CallKonzulensBtn = findViewById(R.id.call_to_konzulens);

        /*
         * Checklist panel
         * */
        ketaminTv = findViewById(R.id.rsi_check_ketamin);
        etomidatetv = findViewById(R.id.rsi_check_etomidate);
        szukcinilkolinTv = findViewById(R.id.rsi_check_szukci);
        atropintv = findViewById(R.id.rsi_check_atropin);
        etChecklistTv = findViewById(R.id.rsi_check_etiSize);
        secEtChecklistTv = findViewById(R.id.rsi_check_alternativETSize);
        bougieTv = findViewById(R.id.rsi_check_bougieSize);
        larChecklistTv = findViewById(R.id.rsi_check_laringSize);
        secLarChecklistTv = findViewById(R.id.rsi_check_alternativLaringSize);
        lmaTv = findViewById(R.id.rsi_check_lmaSize);

        /*
         * Set Rokuronium Dose After ETI Panel
         * */
        changeRokuBtn01 = findViewById(R.id.rsi_rocu_button);
        rokuDose01Tv = findViewById(R.id.rsi_rocu_adag);


        /*
         * Set Transzport Medication Dose Panel
         * */

        transportMed01Tv = findViewById(R.id.rsi_gysz_alt);
        transportMed02Tv = findViewById(R.id.rsi_gysz_alt2);
        observerI = 0;

        setStartVisible();
        childButtonMethod();
        adultButtonMethod();
        setKonzulensNumber();
        callKonzulens();
        rsicalcMethod();
        rokuButtonMethod();


    }

    /**
     * Set Invisible the Child and after RSI rokuronium dose
     */

    public void setStartVisible() {

        childSb.setVisibility(View.INVISIBLE);
        childAgeTv.setVisibility(View.INVISIBLE);
        rokuDose01Tv.setVisibility(View.INVISIBLE);
        changeRokuBtn01.setVisibility(View.INVISIBLE);

    }

    /**
     * load konzulens phone number from firebase realtime database
     */

    public void setKonzulensNumber() {


        FirebaseDatabase.getInstance().getReference().child(KONZULENS_SZAMA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                konzulensPhoneNumberS = Objects.requireNonNull(dataSnapshot.getValue()).toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Konzulens calling method
     */

    public void callKonzulens() {

        CallKonzulensBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel: " + konzulensPhoneNumberS));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(RsiActivity.this, "Nincs hívásindító az eszközén!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Calculate dose and parameters into RSI checklist
     */

    public void rsicalcMethod() {
        CalculatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    try {

                        setEtsAndAtropin();

                        setBougieSize();

                        setLmaSize();

                        setLaringSize();

                        medDoseForEti();

                        afterEtiDose();

                    } catch (Exception e) {


                        Toast.makeText(RsiActivity.this, "Adja meg a beteg ttkg-ját!", Toast.LENGTH_LONG).show();


                    }

                Toast.makeText(RsiActivity.this, "Checklista kitöltve!", Toast.LENGTH_LONG).show();

            }


        });

    }

    /**
     * After ETI rokuronium dose changer
     */
    public void rokuButtonMethod() {
        changeRokuBtn01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (counter % 2 == 0) {
                    String t1 = getString(R.string.roku_dose_rsi_activity) + String.valueOf(patientWeightD * 0.5) + " mg";
                    rokuDose01Tv.setText(t1);
                    String t2 = getString(R.string.question_half_dose_rsi_activity);
                    changeRokuBtn01.setText(t2);
                    counter++;
                } else {
                    String t3 = getString(R.string.roku_half_dose_rsi_activity) + String.valueOf(patientWeightD * 0.25) + " mg";
                    rokuDose01Tv.setText(t3);
                    String t4 = getString(R.string.question_original_dose_rsi_activity);
                    changeRokuBtn01.setText(t4);
                    counter++;
                }

            }
        });


    }

    /**
     * if click on the child button
     */

    public void childButtonMethod() {

        ChildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observerI = 1;
                childSb.setVisibility(View.VISIBLE);
                childAgeTv.setVisibility(View.VISIBLE);
                atropintv.setVisibility(View.VISIBLE);


                etSizeTv.setVisibility(View.INVISIBLE);
                secEtSizeTv.setVisibility(View.INVISIBLE);
                larSizeTv.setVisibility(View.INVISIBLE);
                secLarSizeTv.setVisibility(View.INVISIBLE);
                patientTv.setVisibility(View.INVISIBLE);
                EtSizeSpnr.setVisibility(View.INVISIBLE);
                SecEtSizeSpnr.setVisibility(View.INVISIBLE);
                LarSizeSpnr.setVisibility(View.INVISIBLE);
                SecLarSizeSpnr.setVisibility(View.INVISIBLE);
                AdultweightEt.setVisibility(View.INVISIBLE);
                changeRokuBtn01.setVisibility(View.INVISIBLE);
                rokuDose01Tv.setVisibility(View.INVISIBLE);


                childSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        double age = progress;

                        if (progress == 1) {
                            age = 0.5;
                        } else if (progress > 1) {
                            age = progress - 1;
                        }

                        childWeightD = (age + 4) * 2;
                        String t5 = getString(R.string.the_childs_age_rsi_activity) + age + getString(R.string.yaer_weight) + childWeightD + " kg";
                        childAgeTv.setText(t5);


                        etChildD = (age / 4.0) + 4.0;
                        if (age == 0) {
                            etChildD = 3.5;
                        } else if (age == 0.5) {
                            etChildD = 3.5;
                        } else if (age == 1) {
                            etChildD = 4;
                        } else if (age == 3) {
                            etChildD = 4.5;
                        } else if (age == 5) {
                            etChildD = 5;
                        } else if (age == 7) {
                            etChildD = 6;
                        } else if (age == 9) {
                            etChildD = 6.5;
                        }
                        etChildLengthD = (age / 2) + 12;
                        atropinD = 0.02 * childWeightD;

                        if (age == 0) {
                            childAgeTv.setText(R.string.newlyborn_and_weight);
                            childWeightD = 3.5;
                        } else if (age == 0.5) {
                            String t6 = getString(R.string.child_age_rsi_activity) + age + getString(R.string.year_weight_seven_kg_rsi_activity);
                            childAgeTv.setText(t6);
                            childWeightD = 7;
                            etChildLengthD = 12;
                        }


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

    /**
     * if click on the adult button
     */

    public void adultButtonMethod() {
        AdultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observerI = 0;
                childSb.setVisibility(View.INVISIBLE);
                childAgeTv.setVisibility(View.INVISIBLE);
                atropintv.setVisibility(View.INVISIBLE);


                etSizeTv.setVisibility(View.VISIBLE);
                secEtSizeTv.setVisibility(View.VISIBLE);
                larSizeTv.setVisibility(View.VISIBLE);
                secLarSizeTv.setVisibility(View.VISIBLE);
                patientTv.setVisibility(View.VISIBLE);
                EtSizeSpnr.setVisibility(View.VISIBLE);
                SecEtSizeSpnr.setVisibility(View.VISIBLE);
                LarSizeSpnr.setVisibility(View.VISIBLE);
                SecLarSizeSpnr.setVisibility(View.VISIBLE);
                AdultweightEt.setVisibility(View.VISIBLE);
                changeRokuBtn01.setVisibility(View.VISIBLE);
                rokuDose01Tv.setVisibility(View.VISIBLE);

                rsicalcMethod();

            }
        });

    }


    /**
     * set ET and secET size and atropin
     */

    public void setEtsAndAtropin() {

        String EtMeretString = EtSizeSpnr.getSelectedItem().toString();
        String EtAltMeretString = SecEtSizeSpnr.getSelectedItem().toString();
        etChecklistTv.setTypeface(null, Typeface.BOLD);
        secEtChecklistTv.setTypeface(null, Typeface.BOLD);
        if (observerI == 1) {
            String t6 = "ET mérete: " + String.valueOf(etChildD) + " mm, a fogsornál " + String.valueOf(etChildLengthD) + " cm kell lennie";
            etChecklistTv.setText(t6);
            String t7 = "Cuffos ET mérete: " + String.valueOf(etChildD + 0.5) + " mm";
            secEtChecklistTv.setText(t7);
            String t8 = "Atropin adagja: " + atropinD + " mg";
            atropintv.setText(t8);
            atropintv.setTypeface(null, Typeface.BOLD);
        } else {
            String t9 = "ET tubus mérete: " + EtMeretString;
            etChecklistTv.setText(t9);
            String t10 = "Alternativ ET mérete: " + EtAltMeretString;
            secEtChecklistTv.setText(t10);
        }
    }

    /**
     * set bougie size
     */

    public void setBougieSize() {

        double EtMeretDouble;
        if (observerI == 1) {
            EtMeretDouble = etChildD;
        } else {
            EtMeretDouble = Double.parseDouble(EtSizeSpnr.getSelectedItem().toString());
        }
        if (EtMeretDouble > 6) {
            String t10 = "Bougie mérete: 15 Ch";
            bougieTv.setText(t10);
        } else if (EtMeretDouble <= 6 && EtMeretDouble >= 5) {
            String t11 = "Bougie mérete: 10Ch";
            bougieTv.setText(t11);
        } else {
            String t12 = "Ne használjon Bougie-t";
            bougieTv.setText(t12);
        }
        bougieTv.setTypeface(null, Typeface.BOLD);

    }

    /**
     * set LMA size
     */

    public void setLmaSize() {

        if (observerI == 1) {
            patientWeightD = childWeightD;
        } else {
            patientWeightD = Double.parseDouble(AdultweightEt.getText().toString());
        }
        if (patientWeightD >= 100) {
            lmaTv.setText(R.string.lma_6_rsi_a);
        } else if (patientWeightD >= 70) {
            lmaTv.setText(R.string.lma_5_rsi_a);
        } else if (patientWeightD >= 50) {
            lmaTv.setText(R.string.lma_4_rsi_a);
        } else if (patientWeightD >= 30) {
            lmaTv.setText(R.string.lma_3_rsi_a);
        } else if (patientWeightD >= 20) {
            lmaTv.setText(R.string.lma_2_5_rsi_a);
        } else if (patientWeightD >= 10) {
            lmaTv.setText(R.string.lma_2_rsi_a);
        } else if (patientWeightD >= 5) {
            lmaTv.setText(R.string.lma_1_5_rsi_a);
        } else lmaTv.setText(R.string.lma_1_rsi_a);

        lmaTv.setTypeface(null, Typeface.BOLD);

    }

    /**
     * Set laringoscopes size
     */

    public void setLaringSize() {

        larChecklistTv.setTypeface(null, Typeface.BOLD);
        secLarChecklistTv.setTypeface(null, Typeface.BOLD);
        if (observerI == 1) {
            larChecklistTv.setText(R.string.lar_3_rsi_a);
            secLarChecklistTv.setText(R.string.lar_2_rsi_a);
        } else {
            String t19 = getString(R.string.lar_size_rsi_a) + LarSizeSpnr.getSelectedItem().toString();
            larChecklistTv.setText(t19);
            String t20 = getString(R.string.lar_sec_size_rsi_a) + SecLarSizeSpnr.getSelectedItem().toString();
            secLarChecklistTv.setText(t20);
        }
    }

    /**
     * Set induction medication dose
     */

    public void medDoseForEti() {

        if (ketaminRb.isChecked()) {
            double ind;
            ind = 2 * patientWeightD;
            if (ind > 200) {
                ketaminTv.setText(R.string.ket_max_dose_rsi_a);
                etomidatetv.setText(R.string.eto_dose_rsi_a);
                etomidatetv.setTypeface(null, Typeface.NORMAL);
                ketaminTv.setTypeface(null, Typeface.BOLD);
            } else {
                String t21 = getString(R.string.ket_dose_rsi_a) + String.valueOf(ind) + " mg";
                ketaminTv.setText(t21);
                etomidatetv.setText(R.string.eto_dose_rsi_a);
                etomidatetv.setTypeface(null, Typeface.NORMAL);
                ketaminTv.setTypeface(null, Typeface.BOLD);
            }
        } else if (halfKetaminRb.isChecked()) {
            double indhalf;
            indhalf = patientWeightD;
            if (indhalf > 200) {
                ketaminTv.setText(R.string.ket_max_dose_rsi_a);
                etomidatetv.setText(R.string.eto_dose_rsi_a2);
                etomidatetv.setTypeface(null, Typeface.NORMAL);
                ketaminTv.setTypeface(null, Typeface.BOLD);
            } else {
                String t22 = "Ketamin dózisa: " + String.valueOf(indhalf) + " mg";
                ketaminTv.setText(t22);
                etomidatetv.setText(R.string.eto_dose_rsi_a);
                etomidatetv.setTypeface(null, Typeface.NORMAL);
                ketaminTv.setTypeface(null, Typeface.BOLD);
            }
        } else if (etomidateRb.isChecked()) {
            double etodouble;
            etodouble = 3 * patientWeightD / 10;

            if (etodouble > 20) {
                etomidatetv.setText(R.string.eto_max_dose_rsi_a);
                ketaminTv.setText(R.string.ket_dose_rsi_a2);
                ketaminTv.setTypeface(null, Typeface.NORMAL);
                etomidatetv.setTypeface(null, Typeface.BOLD);
            } else {
                String t23 = getString(R.string.eto_dose_rsi_a3) + String.valueOf(etodouble) + " mg";
                etomidatetv.setText(t23);
                ketaminTv.setText(R.string.ket_dose_rsi_a2);
                ketaminTv.setTypeface(null, Typeface.NORMAL);
                etomidatetv.setTypeface(null, Typeface.BOLD);
            }
        } else if (nullRb.isChecked()) {
            etomidatetv.setText(R.string.eto_dose_rsi_a2);
            etomidatetv.setTypeface(null, Typeface.NORMAL);
            ketaminTv.setText(R.string.keto_dose_rsi_a2);
            ketaminTv.setTypeface(null, Typeface.NORMAL);
        }

        double izomr;
        if (szukcinilkolinRb.isChecked()) {
            izomr = 15 * patientWeightD / 10;
            if (izomr > 200) {
                szukcinilkolinTv.setText(R.string.suk_max_dose_rsi_a);
                szukcinilkolinTv.setTypeface(null, Typeface.BOLD);
            } else {
                String t24 = getString(R.string.szuk_dose_rsi_a) + izomr + " mg";
                szukcinilkolinTv.setText(t24);
                szukcinilkolinTv.setTypeface(null, Typeface.BOLD);
            }
        } else {
            izomr = patientWeightD;
            String t25 = getString(R.string.roku_dose_rsi_a) + izomr + " mg";
            szukcinilkolinTv.setText(t25);
            szukcinilkolinTv.setTypeface(null, Typeface.BOLD);

        }

    }

    /**
     * Set rokuronium dose after ETI and other medication dose to transport
     */

    public void afterEtiDose() {


        double rocuDouble = patientWeightD / 2;
        String t26 = getString(R.string.roku_dose_rsi_a2) + String.valueOf(rocuDouble) + " mg";
        rokuDose01Tv.setText(t26);
        rokuDose01Tv.setVisibility(View.VISIBLE);
        changeRokuBtn01.setVisibility(View.VISIBLE);
        String t27 = "100 Hgmm alatt: Ketamin " + 5 * patientWeightD / 10 + " - " + patientWeightD + " mg bólusokban 20 percenként ismételve";
        transportMed01Tv.setText(t27);
        String t28 = "100 Hgmm felett: Propofol " + patientWeightD + " mg/ óra induló dózissal perfuzorban (ennek" +
                " hiányában " + 3 * patientWeightD / 10 + " mg bólusok 5-10 percenként), valamint Fentanyl " + patientWeightD + "ug ( 20 percenként ismételve ) bólusokban";
        transportMed02Tv.setText(t28);
    }
}
