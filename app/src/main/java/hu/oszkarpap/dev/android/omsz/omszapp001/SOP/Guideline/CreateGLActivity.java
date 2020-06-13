package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;

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
    public static final String KEY_ATTR = "ATTR";
    private EditText createName, createDesc;
    private CheckBox bold, italian, underline, colored, bold2, italian2, underline2, colored2;
    private Button createMemoryBTN;
    private String asc, title, color = "FFFFFF", color2 = "FFFFFF";
    private ColorPickerView colorPickerView, colorPickerView2;
    private String attr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_gl);
         createName = findViewById(R.id.createNameGlET);
        createName.setError(getString(R.string.create_medication_name_alert), null);
        createDesc = findViewById(R.id.createDescGlET);
        createMemoryBTN = findViewById(R.id.createGlBTN);

        bold = findViewById(R.id.CreateGlbold);
        italian = findViewById(R.id.CreateGlitalic);
        underline = findViewById(R.id.CreateGlunderline);
        colored = findViewById(R.id.CreateGlColor);
        bold2 = findViewById(R.id.CreateGlbold2);
        italian2 = findViewById(R.id.CreateGlitalic2);
        underline2 = findViewById(R.id.CreateGlunderline2);
        colored2 = findViewById(R.id.CreateGlColor2);
        bold.setChecked(false);
        italian.setChecked(false);
        underline.setChecked(false);
        bold2.setChecked(false);
        italian.setChecked(false);
        underline2.setChecked(false);

       colorPickerView = findViewById(R.id.CreateGlcolorPickerView);
        colorPickerView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                LinearLayout linearLayout = findViewById(R.id.LinearLayoutColorPickerView);
                linearLayout.setBackgroundColor(colorEnvelope.getColor());
                color = colorEnvelope.getColorHtml();
                //Toast.makeText(CreateGLActivity.this, ""+colorEnvelope.getColorHtml(), Toast.LENGTH_SHORT).show();
            }
        });


        colorPickerView2 = findViewById(R.id.CreateGlcolorPickerView2);
        colorPickerView2.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                LinearLayout linearLayout = findViewById(R.id.LinearLayoutColorPickerView2);
                linearLayout.setBackgroundColor(colorEnvelope.getColor());
                color2 = colorEnvelope.getColorHtml();
                //Toast.makeText(CreateGLActivity.this, ""+colorEnvelope.getColorHtml(), Toast.LENGTH_SHORT).show();
            }
        });
        asc = getIntent().getStringExtra(GLActivity.KEY_GL_ASC_MODIFY);
        title = getIntent().getStringExtra(GLActivity.KEY_GL_TITLE_MODIFY);
        //Toast.makeText(this, ""+asc, Toast.LENGTH_SHORT).show();
        setTitle(getTitle() + " - Protokoll részlet név és kifejtés");


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
                generateAttr();

                Intent intent = new Intent();
                //Toast.makeText(CreateGLActivity.this, ""+attr, Toast.LENGTH_SHORT).show();
                intent.putExtra(KEY_NAME, name);
                intent.putExtra(KEY_DESC, desc);
                intent.putExtra(KEY_ASC, asc);
                intent.putExtra(KEY_TITLE, title);
                intent.putExtra(KEY_ATTR, attr);

                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }

    public void generateAttr() {
        attr = "f";

        if (bold.isChecked()) {
            attr += "1";
        } else {
            attr += "0";
        }
        if (italian.isChecked()) {
            attr += "1";
        } else {
            attr += "0";
        }
        if (underline.isChecked()) {
            attr += "1";
        } else {
            attr += "0";
        }
        attr += "s";
        if (bold2.isChecked()) {
            attr += "1";
        } else {
            attr += "0";
        }
        if (italian2.isChecked()) {
            attr += "1";
        } else {
            attr += "0";
        }
        if (underline2.isChecked()) {
            attr += "1";
        } else {
            attr += "0";
        }

        attr += "X";
        if (colored.isChecked()) {
            attr += 1;
        } else {
            attr += 0;
        }
        attr += color;
        attr += "Y";
        if (colored2.isChecked()) {
            attr += 1;
        } else {
            attr += 0;
        }

        attr += color2;

    }
}



