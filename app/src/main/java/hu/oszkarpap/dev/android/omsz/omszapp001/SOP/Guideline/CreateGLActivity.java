package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is created sop activity
 * This Activity used by create SOP ... action, modify Med action, create own Med action, modify own Med action
 */

public class CreateGLActivity extends AppCompatActivity {

    public static final String KEY_NAME = "NAME";
    public static final String KEY_DESC = "DESC";
    public static final String KEY_ASC = "ASC";
    public static final String KEY_TITLE = "TITLE";
    private EditText createName, createDesc;
    private Button createMemoryBTN;
    private String asc, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sop);
        createName = findViewById(R.id.createNameSopET);
        createName.setError(getString(R.string.create_medication_name_alert), null);
        createDesc = findViewById(R.id.createDescSopET);
        createMemoryBTN = findViewById(R.id.createSopBTN);
        asc = getIntent().getStringExtra(GLActivity.KEY_GL_ASC_MODIFY);
        title = getIntent().getStringExtra(GLActivity.KEY_GL_TITLE_MODIFY);
        //Toast.makeText(this, ""+asc, Toast.LENGTH_SHORT).show();


        setEdittextModify();

        clickCreateButton();

    }


    /**
     * this method set EditText, if i can modify Medication parameters
     */
    public void setEdittextModify() {
        if (!(getIntent().getStringExtra(GLActivity.KEY_GL_NAME_MODIFY) == null)) {
            createName.setText(getIntent().getStringExtra(GLActivity.KEY_GL_NAME_MODIFY));
            createDesc.setText(getIntent().getStringExtra(GLActivity.KEY_GL_DESC_MODIFY));
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
                intent.putExtra(KEY_ASC,asc);
                intent.putExtra(KEY_TITLE, title);

                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }
}
