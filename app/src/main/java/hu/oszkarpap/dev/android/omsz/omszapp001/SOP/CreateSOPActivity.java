package hu.oszkarpap.dev.android.omsz.omszapp001.SOP;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
//import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.MemoryActivity;

import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity.SOPSearching;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is created sop activity
 * This Activity used by create SOP ... action, modify Med action, create own Med action, modify own Med action
 */

public class CreateSOPActivity extends AppCompatActivity {

    public static final String KEY_NAME = "NAME";
    public static final String KEY_DESC = "DESC";
    public static final String KEY_ICON = "ICON";
    private EditText createName, createDesc;
    private Button createMemoryBTN;
    private RadioButton sr, brady, stemi, vt, bag, asthma, vent, vent02, tox, ballon, bed, cpr, cprHand, crossFour, crossSix, defi, drop, foni, foni02, heart, heart02, heart03, infusion, injection, injection02, lung, monitor, patch, pill, scalpel, temp, wound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sop);
        createName = findViewById(R.id.createNameSopET);
        // createName.setError(getString(R.string.create_medication_name_alert), null);
        createDesc = findViewById(R.id.createDescSopET);
        createMemoryBTN = findViewById(R.id.createSopBTN);
        createMemoryBTN.setText("Mentés");
        sr = findViewById(R.id.icon_sinus);
        stemi = findViewById(R.id.icon_stemi);
        vt = findViewById(R.id.icon_vt);
        brady = findViewById(R.id.icon_brady);
        asthma = findViewById(R.id.icon_asthma);
        vent = findViewById(R.id.icon_vent);
        vent02 = findViewById(R.id.icon_vent02);
        tox = findViewById(R.id.icon_tox);
        bag = findViewById(R.id.icon_bag);
        ballon = findViewById(R.id.icon_ballon);
        bed = findViewById(R.id.icon_bed);
        cpr = findViewById(R.id.icon_cpr);
        cprHand = findViewById(R.id.icon_cpr_hand);
        crossFour = findViewById(R.id.icon_cross_four);
        crossSix = findViewById(R.id.icon_cross_six);
        defi = findViewById(R.id.icon_defi);
        drop = findViewById(R.id.icon_drop);
        foni = findViewById(R.id.icon_foni_01);
        foni02 = findViewById(R.id.icon_foni_02);
        heart = findViewById(R.id.icon_heart_01);
        heart02 = findViewById(R.id.icon_heart_02);
        heart03 = findViewById(R.id.icon_heart_03);
        infusion = findViewById(R.id.icon_infusion);
        injection = findViewById(R.id.icon_inj_01);
        injection02 = findViewById(R.id.icon_inj_02);
        lung = findViewById(R.id.icon_lung);
        monitor = findViewById(R.id.icon_monitor);
        patch = findViewById(R.id.icon_patch);
        pill = findViewById(R.id.icon_pill);
        scalpel = findViewById(R.id.icon_scalpel);
        temp = findViewById(R.id.icon_temp);
        wound = findViewById(R.id.icon_wound);
        setTitle(getTitle() + " - Protokoll név és leírás");


        setEdittextModify();

        clickCreateButton();

    }


    /**
     * this method set EditText, if i can modify Medication parameters
     */
    public void setEdittextModify() {
        if (!(getIntent().getStringExtra(SOPActivity.KEY_SOP_NAME_MODIFY) == null)) {
            createName.setText(getIntent().getStringExtra(SOPActivity.KEY_SOP_NAME_MODIFY));
            createDesc.setText(getIntent().getStringExtra(SOPActivity.KEY_SOP_DESC_MODIFY));
        }

    }

    /**
     * this is the click create button method
     */
    public void clickCreateButton() {
        createMemoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (createDesc.getText().toString() == null || createDesc.getText().toString() == " " ||
                        createName.getText().toString() == null || createName.getText().toString() == " ") {
                    Toast.makeText(CreateSOPActivity.this, "A mezők kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {

                    //Toast.makeText(CreateSOPActivity.this, ""+createName.getText().toString(), Toast.LENGTH_SHORT).show();
                    if (!(getIntent().getStringExtra(SOPActivity.KEY_SOP_KEY_MODIFY) == null)) {
                        FirebaseDatabase.getInstance().getReference().child("sop")
                                .child(getIntent().getStringExtra(SOPActivity.KEY_SOP_KEY_MODIFY))
                                .child("name").setValue(createName.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("sop")
                                .child(getIntent().getStringExtra(SOPActivity.KEY_SOP_KEY_MODIFY))
                                .child("desc").setValue(createDesc.getText().toString());

                        String icon = "";

                        if (bag.isChecked()) {
                            icon = "bag";
                        }
                        if (sr.isChecked()) {
                            icon = "sinus";
                        }
                        if (stemi.isChecked()) {
                            icon = "stemi";
                        }
                        if (vt.isChecked()) {
                            icon = "vt";
                        }
                        if (brady.isChecked()) {
                            icon = "brady";
                        }
                        if (asthma.isChecked()) {
                            icon = "asthma";
                        }
                        if (ballon.isChecked()) {
                            icon = "ballon";
                        }
                        if (tox.isChecked()) {
                            icon = "tox";
                        }
                        if (vent.isChecked()) {
                            icon = "vent";
                        }
                        if (vent02.isChecked()) {
                            icon = "vent02";
                        }
                        if (bed.isChecked()) {
                            icon = "bed";
                        }
                        if (cpr.isChecked()) {
                            icon = "cpr";
                        }
                        if (cprHand.isChecked()) {
                            icon = "cprhand";
                        }
                        if (crossFour.isChecked()) {
                            icon = "crossfour";
                        }
                        if (crossSix.isChecked()) {
                            icon = "crossix";
                        }
                        if (defi.isChecked()) {
                            icon = "defi";
                        }
                        if (drop.isChecked()) {
                            icon = "drop";
                        }
                        if (foni.isChecked()) {
                            icon = "foni";
                        }
                        if (foni02.isChecked()) {
                            icon = "foni02";
                        }
                        if (heart.isChecked()) {
                            icon = "heart";
                        }
                        if (heart02.isChecked()) {
                            icon = "heart02";
                        }
                        if (heart03.isChecked()) {
                            icon = "heart03";
                        }
                        if (infusion.isChecked()) {
                            icon = "inf";
                        }
                        if (injection.isChecked()) {
                            icon = "inj";
                        }
                        if (injection02.isChecked()) {
                            icon = "inj02";
                        }
                        if (lung.isChecked()) {
                            icon = "lung";
                        }
                        if (monitor.isChecked()) {
                            icon = "monitor";
                        }
                        if (patch.isChecked()) {
                            icon = "patch";
                        }
                        if (pill.isChecked()) {
                            icon = "pill";
                        }
                        if (scalpel.isChecked()) {
                            icon = "scal";
                        }
                        if (temp.isChecked()) {
                            icon = "temp";
                        }
                        if (wound.isChecked()) {
                            icon = "wound";
                        }

                        FirebaseDatabase.getInstance().getReference().child("sop")
                                .child(getIntent().getStringExtra(SOPActivity.KEY_SOP_KEY_MODIFY))
                                .child("icon").setValue(icon);
                    } else {
                        String name = createName.getText().toString();
                        String desc = createDesc.getText().toString();
                        String icon = "";
                        if (sr.isChecked()) {
                            icon = "sinus";
                        }
                        if (stemi.isChecked()) {
                            icon = "stemi";
                        }
                        if (vt.isChecked()) {
                            icon = "vt";
                        }
                        if (brady.isChecked()) {
                            icon = "brady";
                        }

                        if (bag.isChecked()) {
                            icon = "bag";
                        }
                        if (asthma.isChecked()) {
                            icon = "asthma";
                        }
                        if (ballon.isChecked()) {
                            icon = "ballon";
                        }
                        if (tox.isChecked()) {
                            icon = "tox";
                        }
                        if (vent.isChecked()) {
                            icon = "vent";
                        }
                        if (vent02.isChecked()) {
                            icon = "vent02";
                        }
                        if (ballon.isChecked()) {
                            icon = "ballon";
                        }
                        if (bed.isChecked()) {
                            icon = "bed";
                        }
                        if (cpr.isChecked()) {
                            icon = "cpr";
                        }
                        if (cprHand.isChecked()) {
                            icon = "cprhand";
                        }
                        if (crossFour.isChecked()) {
                            icon = "crossfour";
                        }
                        if (crossSix.isChecked()) {
                            icon = "crossix";
                        }
                        if (defi.isChecked()) {
                            icon = "defi";
                        }
                        if (drop.isChecked()) {
                            icon = "drop";
                        }
                        if (foni.isChecked()) {
                            icon = "foni";
                        }
                        if (foni02.isChecked()) {
                            icon = "foni02";
                        }
                        if (heart.isChecked()) {
                            icon = "heart";
                        }
                        if (heart02.isChecked()) {
                            icon = "heart02";
                        }
                        if (heart03.isChecked()) {
                            icon = "heart03";
                        }
                        if (infusion.isChecked()) {
                            icon = "inf";
                        }
                        if (injection.isChecked()) {
                            icon = "inj";
                        }
                        if (injection02.isChecked()) {
                            icon = "inj02";
                        }
                        if (lung.isChecked()) {
                            icon = "lung";
                        }
                        if (monitor.isChecked()) {
                            icon = "monitor";
                        }
                        if (patch.isChecked()) {
                            icon = "patch";
                        }
                        if (pill.isChecked()) {
                            icon = "pill";
                        }
                        if (scalpel.isChecked()) {
                            icon = "scal";
                        }
                        if (temp.isChecked()) {
                            icon = "temp";
                        }
                        if (wound.isChecked()) {
                            icon = "wound";
                        }

                        Intent intent = new Intent();

                        intent.putExtra(KEY_NAME, name);
                        intent.putExtra(KEY_DESC, desc);
                        intent.putExtra(KEY_ICON, icon);
                        setResult(RESULT_OK, intent);

                    }


                    SOPSearching = 0;
                    finish();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        SOPSearching = 0;
        finish();
        super.onBackPressed();
    }
}
