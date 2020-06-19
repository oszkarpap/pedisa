package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;

import java.io.IOException;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem.GuideLineListItem;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is created sop activity
 * This Activity used by create SOP ... action, modify Med action, create own Med action, modify own Med action
 */

public class CreateGLListItemActivity extends AppCompatActivity {


    public static final String KEY_ASC = "ASC";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_ATTR = "ATTR";

    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseAuth auth;
    private EditText createName, createNumber;
    private CheckBox bold, italian, underline, colored;
    private Button createMemoryBTN;
    private String asc, title, color = "FFFFFF", color2 = "FFFFFF";
    private ColorPickerView colorPickerView;
    private String attr;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        setContentView(R.layout.activity_create_glli);

        createName = findViewById(R.id.createNameGlliET);
        createName.setError(getString(R.string.create_medication_name_alert), null);
        createMemoryBTN = findViewById(R.id.createGlliBTN);
        createNumber = findViewById(R.id.createNumberGlliET);
        bold = findViewById(R.id.CreateGllibold);
        italian = findViewById(R.id.CreateGlliitalic);
        underline = findViewById(R.id.CreateGlliunderline);
        colored = findViewById(R.id.CreateGlliColor);
        bold.setChecked(false);
        italian.setChecked(false);
        underline.setChecked(false);




        colorPickerView = findViewById(R.id.CreateGlcolorPickerViewli);
        colorPickerView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                LinearLayout linearLayout = findViewById(R.id.LinearLayoutColorPickerViewli);
                linearLayout.setBackgroundColor(colorEnvelope.getColor());
                color = colorEnvelope.getColorHtml();
                //Toast.makeText(CreateGLActivity.this, ""+colorEnvelope.getColorHtml(), Toast.LENGTH_SHORT).show();
            }
        });


        asc = getIntent().getStringExtra(GLActivity.KEY_GL_ASC_MODIFY);
        title = getIntent().getStringExtra(GLActivity.KEY_GL_TITLE_MODIFY);
        //Toast.makeText(this, ""+asc, Toast.LENGTH_SHORT).show();
        setTitle(getTitle() + " - Protokoll Lista");


        setEdittextModify();

        clickCreateButton();

    }



    /**
     * this method set EditText, if i can modify Medication parameters
     */
    public void setEdittextModify() {
        if (!(getIntent().getStringExtra(GLActivity.KEY_GL_NAME_MODIFY) == null)) {
            createName.setText(getIntent().getStringExtra(GLActivity.KEY_GL_NAME_MODIFY));
            createNumber.setText(getIntent().getStringExtra(GLActivity.KEY_GL_COUNT_MODIFY));
            //Toast.makeText(this, ""+getIntent().getStringExtra(GLActivity.KEY_GL_COUNT_MODIFY), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * this is the click create button method
     */
    public void clickCreateButton() {
        createMemoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                generateAttr();

                Intent intent = new Intent();
                //Toast.makeText(CreateGLActivity.this, ""+attr, Toast.LENGTH_SHORT).show();
                intent.putExtra(KEY_ASC, asc);
                intent.putExtra(KEY_TITLE, title);
                intent.putExtra(KEY_ATTR, attr);
                GuideLineListItem guideLineListItem = new GuideLineListItem();
                guideLineListItem.setItem(createName.getText().toString());
                guideLineListItem.setKey(asc);
                guideLineListItem.setAttributum(attr);
                try {
                    guideLineListItem.setCount(Integer.parseInt(createNumber.getText().toString()));
                } catch (Exception e) {

                }
                try {
                    guideLineListItem.setParent(asc);
                }catch (Exception e){

                }saveGLLI(guideLineListItem);
                setResult(RESULT_OK, intent);
                //Toast.makeText(CreateGLActivity.this, ""+attr, Toast.LENGTH_SHORT).show();
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

        attr += "X";
        if (colored.isChecked()) {
            attr += 1;
        } else {
            attr += 0;
        }
        attr += color;
        attr += "Y";

        attr += color2;
        attr+="W";


    }


    public void saveGLLI(final GuideLineListItem guideLineListItem) {

        try {
            String key;

            key = guideLineListItem.getKey();
            String x = key + System.currentTimeMillis();

            guideLineListItem.setKey(key);

           // guideLineListItem.setAsc(x);
            FirebaseDatabase.getInstance().getReference()
                    .child("glli")
                    .child(key).child(x)
                    .setValue(guideLineListItem)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(CreateGLListItemActivity.this, "Firebase felhőbe mentve!", Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, R.string.create_medication_name_alert, Toast.LENGTH_LONG).show();
        }


    }
}



