package hu.oszkarpap.dev.android.omsz.omszapp001.right.medication;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import hu.oszkarpap.dev.android.omsz.omszapp001.R;
/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the Modify Medication Activity
 */

public class ModifyMedActivity extends MedActivity {

    private EditText createName, createAgent, createPack, createInd, createContra, createAdult,
            createChild, createChD01, createChD02, createChMaxD, createChDesciption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_medication);
        createName = findViewById(R.id.createNameMedET);
        createName.setError("Alfanumerkius karaktereket, ill. (/) (.) (,) használjon! ", null);
        createAgent = findViewById(R.id.createAgentMedET);
        createPack = findViewById(R.id.createPackMedET);
        createInd = findViewById(R.id.createIndMedET);
        createContra = findViewById(R.id.createContraMedET);
        createAdult = findViewById(R.id.createAdultMedET);
        createChild = findViewById(R.id.createChildMedET);
        createChild = findViewById(R.id.createChildMedET);
        createChD01 = findViewById(R.id.createChildMedETDose01);
        createChD02 = findViewById(R.id.createChildMedETDose02);
        createChMaxD = findViewById(R.id.createChildMedETDoseMax);
        createChDesciption = findViewById(R.id.createChildMedETDoseDesc);


        Button createMemoryBTN = findViewById(R.id.createMedBTN);
        createName.setText(getIntent().getStringExtra(MedActivity.KEY_NAME_MODIFY));
        createAgent.setText(getIntent().getStringExtra(MedActivity.KEY_AGENT_MODIFY));
        createPack.setText(getIntent().getStringExtra(MedActivity.KEY_PACK_MODIFY));
        createInd.setText(getIntent().getStringExtra(MedActivity.KEY_IND_MODIFY));
        createContra.setText(getIntent().getStringExtra(MedActivity.KEY_CONTRA_MODIFY));
        createAdult.setText(getIntent().getStringExtra(MedActivity.KEY_ADULT_MODIFY));
        createChild.setText(getIntent().getStringExtra(MedActivity.KEY_CHILD_MODIFY));
        createChD01.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDD01_MODIFY));
        createChD02.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDD02_MODIFY));
        createChMaxD.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDDMAX_MODIFY));
        createChDesciption.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDDDESC_MODIFY));

        createMemoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Medication  med = new Medication(createName.getText().toString(),
                        createAgent.getText().toString(),createPack.getText().toString(),
                        createInd.getText().toString(),createContra.getText().toString(),
                        createAdult.getText().toString(),createChild.getText().toString(),
                        createChD01.getText().toString(),createChD02.getText().toString(),
                        createChMaxD.getText().toString(),createChDesciption.getText().toString());

                saveMed(med);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
