package hu.oszkarpap.dev.android.omsz.omszapp001.medication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
//import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.MemoryActivity;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is created medication activity
 * This Activity used by create Med action, modify Med action, create own Med action, modify own Med action
 */

public class CreateMedActivity extends AppCompatActivity {

    public static final String KEY_NAME = "NAME";
    public static final String KEY_AGENT = "AGENT";
    public static final String KEY_PACK = "PACK";
    public static final String KEY_IND = "IND";
    public static final String KEY_CONTRA = "CONTRA";
    public static final String KEY_ADULT = "ADULT";
    public static final String KEY_CHILD = "CHILD";
    public static final String KEY_CHILD01 = "CHILD01";
    public static final String KEY_CHILD02 = "CHILD02";
    public static final String KEY_CHILDUNIT = "CHILDUNIT";
    public static final String KEY_CHILDMAX = "CHILDMAX";
    public static final String KEY_CHILDMAX02 = "CHILDMAX02";
    public static final String KEY_CHILDDESC = "CHILDDESC";
    private EditText createName, createAgent, createPack, createInd, createContra, createAdult, createChild,
            createChD01, createChD02, createChU, createChMaxD, createChMaxD02, createChDesciption;
    private TextView createChildParameters;
    private Button createMemoryBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_medication);
        createName = findViewById(R.id.createNameMedET);
        createName.setError(getString(R.string.create_medication_name_alert), null);
        createAgent = findViewById(R.id.createAgentMedET);
        createPack = findViewById(R.id.createPackMedET);
        createInd = findViewById(R.id.createIndMedET);
        createContra = findViewById(R.id.createContraMedET);
        createAdult = findViewById(R.id.createAdultMedET);
        createChild = findViewById(R.id.createChildMedET);
        createChD01 = findViewById(R.id.createChildMedETDose01);
        createChD02 = findViewById(R.id.createChildMedETDose02);
        createChU = findViewById(R.id.createChildMedETUnit);
        createChMaxD = findViewById(R.id.createChildMedETDoseMax);
        createChMaxD02 = findViewById(R.id.createChildMedETDoseMax02);
        createChDesciption = findViewById(R.id.createChildMedETDoseDesc);
        createChildParameters = findViewById(R.id.createMedActivityPrametersTV);
        createMemoryBTN = findViewById(R.id.createMedBTN);

        setDifferentLayout();

        setEdittextModify();

        clickCreateButton();

    }

    /**
     * this method set layout from omsz medication activity and from own medication activity
     */
    public void setDifferentLayout() {
     /**   if ((getIntent().getStringExtra(MemoryActivity.KEY_MEMORY).equals("YES"))) {
            createChD01.setVisibility(View.INVISIBLE);
            createChD02.setVisibility(View.INVISIBLE);
            createChU.setVisibility(View.INVISIBLE);
            createChMaxD.setVisibility(View.INVISIBLE);
            createChMaxD02.setVisibility(View.INVISIBLE);
            createChDesciption.setVisibility(View.INVISIBLE);
            createChildParameters.setVisibility(View.INVISIBLE);
            setTitle("Saját Gyógyszerjegyzet");
        } else {*/
            createChD01.setVisibility(View.VISIBLE);
            createChD02.setVisibility(View.VISIBLE);
            createChU.setVisibility(View.VISIBLE);
            createChMaxD.setVisibility(View.VISIBLE);
            createChMaxD02.setVisibility(View.VISIBLE);
            createChDesciption.setVisibility(View.VISIBLE);
            createChildParameters.setVisibility(View.VISIBLE);

    }

    /**
     * this method set EditText, if i can modify Medication parameters
     */
    public void setEdittextModify() {
        if (!(getIntent().getStringExtra(MedActivity.KEY_NAME_MODIFY) == null)) {
            createName.setText(getIntent().getStringExtra(MedActivity.KEY_NAME_MODIFY));
            createAgent.setText(getIntent().getStringExtra(MedActivity.KEY_AGENT_MODIFY));
            createPack.setText(getIntent().getStringExtra(MedActivity.KEY_PACK_MODIFY));
            createInd.setText(getIntent().getStringExtra(MedActivity.KEY_IND_MODIFY));
            createContra.setText(getIntent().getStringExtra(MedActivity.KEY_CONTRA_MODIFY));
            createAdult.setText(getIntent().getStringExtra(MedActivity.KEY_ADULT_MODIFY));
            createChild.setText(getIntent().getStringExtra(MedActivity.KEY_CHILD_MODIFY));
            createChU.setText(getIntent().getStringExtra(MedActivity.KEY_UNIT_MODIFY));
            createChD01.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDD01_MODIFY));
            createChD02.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDD02_MODIFY));
            createChMaxD.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDDMAX_MODIFY));
            createChMaxD02.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDDMAX02_MODIFY));
            createChDesciption.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDDDESC_MODIFY));
        }

    }

    /**
     * this is the click create button method
     */
    public void clickCreateButton() {
        createMemoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = createName.getText().toString();
                String agent = createAgent.getText().toString();
                String pack = createPack.getText().toString();
                String ind = createInd.getText().toString();
                String contra = createContra.getText().toString();
                String adult = createAdult.getText().toString();
                String child = createChild.getText().toString();
                String unit = createChU.getText().toString();
                String dose01 = createChD01.getText().toString();
                String dose02 = createChD02.getText().toString();
                String doseMax = createChMaxD.getText().toString();
                String doseMax02 = createChMaxD02.getText().toString();
                String doseDesc = createChDesciption.getText().toString();


                Intent intent = new Intent();

                intent.putExtra(KEY_NAME, name);
                intent.putExtra(KEY_AGENT, agent);
                intent.putExtra(KEY_PACK, pack);
                intent.putExtra(KEY_IND, ind);
                intent.putExtra(KEY_CONTRA, contra);
                intent.putExtra(KEY_ADULT, adult);
                intent.putExtra(KEY_CHILD, child);
                intent.putExtra(KEY_CHILDUNIT, unit);
                intent.putExtra(KEY_CHILD01, dose01);
                intent.putExtra(KEY_CHILD02, dose02);
                intent.putExtra(KEY_CHILDMAX, doseMax);
                intent.putExtra(KEY_CHILDMAX02, doseMax02);
                intent.putExtra(KEY_CHILDDESC, doseDesc);

                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }
}
