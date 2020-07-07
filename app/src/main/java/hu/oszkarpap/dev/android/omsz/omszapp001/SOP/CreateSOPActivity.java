package hu.oszkarpap.dev.android.omsz.omszapp001.SOP;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
//import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.MemoryActivity;

import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity.SOPSearching;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is created sop activity
 * This Activity used by create SOP ... action, modify Med action, create own Med action, modify own Med action
 */

public class CreateSOPActivity extends AppCompatActivity implements SOPIconAdapter.OnItemClickListener, SOPIconAdapter.OnItemLongClickListener {

    public static final String KEY_NAME = "NAME";
    public static final String KEY_DESC = "DESC";
    public static final String KEY_ICON = "ICON";
    private EditText createName, createDesc;
    private Button createMemoryBTN, brBtn, brBtn2;
    private List<SOPIcon> sopIcons;
    private SOPIconAdapter sopIconAdapter;
    private String sopIconTemp;
    private RecyclerView recyclerView;
    private SOPIcon sopIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sop);
        createName = findViewById(R.id.createNameSopET);
        // createName.setError(getString(R.string.create_medication_name_alert), null);
        createDesc = findViewById(R.id.createDescSopET);
        createMemoryBTN = findViewById(R.id.createSopBTN);
        createMemoryBTN.setText("Mentés");
        brBtn = findViewById(R.id.createSopBrBTN);
        brBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //createName.setText(createName.getText().toString() + " ");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<br/>";
                createName.getText().insert(start, s);
            }
        });
        brBtn2 = findViewById(R.id.createSopBrBTN2);
        brBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //createName.setText(createName.getText().toString() + " ");
                int start = createDesc.getSelectionStart(); //this is to get the the cursor position
                String s = "<br/>";
                createDesc.getText().insert(start, s);
            }
        });
        sopIcons = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_create_icon_sop);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);

        sopIconAdapter = new SOPIconAdapter(this, sopIcons, this, this, sopIcon);
        recyclerView.setAdapter(sopIconAdapter);
        SOPIcon sopIcon01 = new SOPIcon("sr", R.drawable.icon_sr);
        sopIcons.add(sopIcon01);
        SOPIcon sopIcon02 = new SOPIcon("stemi", R.drawable.icon_stemi);
        sopIcons.add(sopIcon02);
        SOPIcon sopIcon03 = new SOPIcon("vt", R.drawable.icon_vt);
        sopIcons.add(sopIcon03);
        SOPIcon sopIcon04 = new SOPIcon("brady", R.drawable.icon_brady);
        sopIcons.add(sopIcon04);
        SOPIcon sopIcon05 = new SOPIcon("asthma", R.drawable.icon_asthma);
        sopIcons.add(sopIcon05);
        SOPIcon sopIcon06 = new SOPIcon("vent", R.drawable.icon_ventolin);
        sopIcons.add(sopIcon06);
        SOPIcon sopIcon07 = new SOPIcon("vent02", R.drawable.icon_ventolin02);
        sopIcons.add(sopIcon07);
        SOPIcon sopIcon08 = new SOPIcon("tox", R.drawable.icon_tox);
        sopIcons.add(sopIcon08);
        SOPIcon sopIcon09 = new SOPIcon("bag", R.drawable.icon_bag);
        sopIcons.add(sopIcon09);
        SOPIcon sopIcon10 = new SOPIcon("ballon", R.drawable.icon_ballon);
        sopIcons.add(sopIcon10);
        SOPIcon sopIcon11 = new SOPIcon("bed", R.drawable.icon_bed);
        sopIcons.add(sopIcon11);
        SOPIcon sopIcon12 = new SOPIcon("cpr", R.drawable.icon_cpr);
        sopIcons.add(sopIcon12);
        SOPIcon sopIcon13 = new SOPIcon("cprHand", R.drawable.icon_cpr_hand);
        sopIcons.add(sopIcon13);
        SOPIcon sopIcon14 = new SOPIcon("crossFour", R.drawable.icon_cross_four);
        sopIcons.add(sopIcon14);
        SOPIcon sopIcon15 = new SOPIcon("crossSix", R.drawable.icon_cross_six);
        sopIcons.add(sopIcon15);
        SOPIcon sopIcon16 = new SOPIcon("defi", R.drawable.icon_defi);
        sopIcons.add(sopIcon16);
        SOPIcon sopIcon17 = new SOPIcon("foni", R.drawable.icon_fonendoscope);
        sopIcons.add(sopIcon17);
        SOPIcon sopIcon18 = new SOPIcon("foni02", R.drawable.icon_fonendoscope02);
        sopIcons.add(sopIcon18);
        SOPIcon sopIcon19 = new SOPIcon("heart", R.drawable.icon_heart);
        sopIcons.add(sopIcon19);
        SOPIcon sopIcon20 = new SOPIcon("heart02", R.drawable.icon_heart02);
        sopIcons.add(sopIcon20);
        SOPIcon sopIcon21 = new SOPIcon("heart03", R.drawable.icon_heart03);
        sopIcons.add(sopIcon21);
        SOPIcon sopIcon22 = new SOPIcon("infusion", R.drawable.icon_infusion);
        sopIcons.add(sopIcon22);
        SOPIcon sopIcon23 = new SOPIcon("injection", R.drawable.icon_injection);
        sopIcons.add(sopIcon23);
        SOPIcon sopIcon24 = new SOPIcon("injection02", R.drawable.icon_injection02);
        sopIcons.add(sopIcon24);
        SOPIcon sopIcon25 = new SOPIcon("lung", R.drawable.icon_lung);
        sopIcons.add(sopIcon25);
        SOPIcon sopIcon26 = new SOPIcon("monitor", R.drawable.icon_monitor);
        sopIcons.add(sopIcon26);
        SOPIcon sopIcon27 = new SOPIcon("patch", R.drawable.icon_patch);
        sopIcons.add(sopIcon27);
        SOPIcon sopIcon28 = new SOPIcon("pill", R.drawable.icon_pill);
        sopIconAdapter.notifyDataSetChanged();
        sopIcons.add(sopIcon28);
        SOPIcon sopIcon29 = new SOPIcon("scalpel", R.drawable.icon_scalpel);
        sopIcons.add(sopIcon29);
        SOPIcon sopIcon30 = new SOPIcon("temp", R.drawable.icon_temp);
        sopIcons.add(sopIcon30);
        SOPIcon sopIcon31 = new SOPIcon("wound", R.drawable.icon_wound);
        sopIcons.add(sopIcon31);
        // Toast.makeText(this, ""+sopIcons.get(6).getName(), Toast.LENGTH_SHORT).show();

        setTitle("Protokoll név és leírás");


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
                String t1, t2;
                        t1 = createName.getText().toString();
                t2 = createDesc.getText().toString();
                if (t1.matches("") ||
                        t2.matches("")) {
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
                        icon = sopIconTemp;

                        FirebaseDatabase.getInstance().getReference().child("sop")
                                .child(getIntent().getStringExtra(SOPActivity.KEY_SOP_KEY_MODIFY))
                                .child("icon").setValue(icon);
                    } else {
                        String name = createName.getText().toString();
                        String desc = createDesc.getText().toString();
                        String icon = "";
                        icon = sopIconTemp;
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


    @Override
    public void onItemLongClicked(int position) {

    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, "választott: " + sopIcons.get(position).getName(), Toast.LENGTH_SHORT).show();
        sopIconTemp = sopIcons.get(position).getName();

    }
}
