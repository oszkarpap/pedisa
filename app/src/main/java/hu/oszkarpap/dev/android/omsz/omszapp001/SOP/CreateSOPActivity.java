package hu.oszkarpap.dev.android.omsz.omszapp001.SOP;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.MemoryActivity;

import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity.SOPSearching;
import static hu.oszkarpap.dev.android.omsz.omszapp001.right.medication.MedActivity.Searching;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is created sop activity
 * This Activity used by create SOP ... action, modify Med action, create own Med action, modify own Med action
 */

public class CreateSOPActivity extends AppCompatActivity {

    public static final String KEY_NAME = "NAME";
    public static final String KEY_DESC = "DESC";
    private EditText createName, createDesc;
    private Button createMemoryBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sop);
        createName = findViewById(R.id.createNameSopET);
        createName.setError(getString(R.string.create_medication_name_alert), null);
        createDesc = findViewById(R.id.createDescSopET);
        createMemoryBTN = findViewById(R.id.createSopBTN);
        setTitle(getTitle()+" - Protokoll név és leírás");



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

                String name = createName.getText().toString();
                String desc = createDesc.getText().toString();

                Intent intent = new Intent();

                intent.putExtra(KEY_NAME, name);
                intent.putExtra(KEY_DESC, desc);

                setResult(RESULT_OK, intent);
                SOPSearching =0;
                finish();

            }
        });



    }
    @Override
    public void onBackPressed() {
        SOPSearching =0;
        finish();
        super.onBackPressed();
    }
}
