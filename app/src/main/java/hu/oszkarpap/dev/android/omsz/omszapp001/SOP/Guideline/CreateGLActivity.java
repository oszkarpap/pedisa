package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

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
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseAuth auth;
    private EditText createName, createDesc, createNumber;
    private CheckBox bold, italian, underline, colored, bold2, italian2, underline2, colored2;
    private Button createMemoryBTN, choeserBtn, uploadBtn;
    private String asc, title, color = "FFFFFF", color2 = "FFFFFF";
    private ColorPickerView colorPickerView, colorPickerView2;
    private String attr;
    private ImageView imageView;
    private RadioButton n10, n12, n14, n16, l10, l12, l14, l16;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        setContentView(R.layout.activity_create_gl);

        n10 = findViewById(R.id.radioGLTextSize10Name);
        n12 = findViewById(R.id.radioGLTextSize12Name);
        n14 = findViewById(R.id.radioGLTextSize14Name);
        n16 = findViewById(R.id.radioGLTextSize16Name);
        l10 = findViewById(R.id.radioGLTextSize10Legend);
        l12 = findViewById(R.id.radioGLTextSize12Legend);
        l14 = findViewById(R.id.radioGLTextSize14Legend);
        l16 = findViewById(R.id.radioGLTextSize16Legend);
        n10.setChecked(true);
        l10.setChecked(true);
        createName = findViewById(R.id.createNameGlET);
        createName.setError(getString(R.string.create_medication_name_alert), null);
        createDesc = findViewById(R.id.createDescGlET);
        createMemoryBTN = findViewById(R.id.createGlBTN);
        createNumber = findViewById(R.id.createNumberGlET);
        choeserBtn = findViewById(R.id.createGlChooseImage);
        //uploadBtn = findViewById(R.id.createGlUploadImage);
        imageView = findViewById(R.id.createGlimage);
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

        choeserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        /**   uploadBtn.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        uploadImage();
        }
        });*/

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

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Kép kiválasztása"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage(String imageName) {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Kép feltöltése a Firebase-be ...");
            progressDialog.show();

            //StorageReference ref = storageReference.child("images/"+ asc);
            StorageReference ref = storageReference.child("images/" + imageName);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateGLActivity.this, "Feltöltve", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateGLActivity.this, "Sikertelen " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Feltöltés... " + (int) progress + "%");

                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * this method set EditText, if i can modify Medication parameters
     */
    public void setEdittextModify() {
        if (!(getIntent().getStringExtra(GLActivity.KEY_GL_NAME_MODIFY) == null)) {
            createName.setText(getIntent().getStringExtra(GLActivity.KEY_GL_NAME_MODIFY));
            createDesc.setText(getIntent().getStringExtra(GLActivity.KEY_GL_DESC_MODIFY));
            createNumber.setText(getIntent().getStringExtra(GLActivity.KEY_GL_COUNT_MODIFY));
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
                GL gl = new GL();
                gl.setName(createName.getText().toString());
                gl.setDesc(createDesc.getText().toString());
                gl.setKey(asc);
                gl.setAttr(attr);
                try {
                    gl.setCount(Integer.parseInt(createNumber.getText().toString()));
                } catch (Exception e) {

                }
                saveGL(gl);
                setResult(RESULT_OK, intent);
                //Toast.makeText(CreateGLActivity.this, ""+attr, Toast.LENGTH_SHORT).show();
                if (filePath==null){
                    finish();
                }

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
        attr+="W";
        if(n10.isChecked()) {
        attr+="10";
        }
        if(n12.isChecked()) {
            attr+="12";
        }
        if(n14.isChecked()) {
            attr+="14";
        }
        if(n16.isChecked()) {
            attr+="16";
        }
        attr+="Z";
        if(l10.isChecked()) {
            attr+="10";
        }
        if(l12.isChecked()) {
            attr+="12";
        }
        if(l14.isChecked()) {
            attr+="14";
        }
        if(l16.isChecked()) {
            attr+="16";
        }


    }


    public void saveGL(final GL gl) {

        try {
            String key = gl.getKey();
            String x = key + System.currentTimeMillis();
            uploadImage(x);
            gl.setKey(key);

            gl.setAsc(x);
            FirebaseDatabase.getInstance().getReference()
                    .child("gl")
                    .child(key).child(x)
                    .setValue(gl)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(CreateGLActivity.this, "Firebase felhőbe mentve!", Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, R.string.create_medication_name_alert, Toast.LENGTH_LONG).show();
        }


    }
}



