package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.CreateSOPActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem.CreateGLListItemActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.CreateMedActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.MedActivity;

import static com.google.firebase.auth.FirebaseAuth.getInstance;
import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity.KEY_GL_ASC_MODIFY;

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
    private CheckBox colored, colored2;
    private Button createBTN, choeserBtn, deleteImageBtn, deleteBtn,
            boldBtn1, italicBtn1, insertedBtn1, bigBtn1, superBtn1, subBtn1,
            boldBtn2, italicBtn2, insertedBtn2, bigBtn2, superBtn2, subBtn2;
    private String asc, title, color = "34495E", color2 = "34495E", getAttr;
    private ColorPickerView colorPickerView, colorPickerView2;
    private String attr;
    private PhotoView imageView;

    //StorageReference storageRef = storage.getReference();

    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        setContentView(R.layout.activity_create_gl);
        getAttr = getIntent().getStringExtra(GLActivity.KEY_GL_ATTR);
        deleteBtn = findViewById(R.id.createGlDeleteBTN);

        deleteBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (getIntent().getStringExtra(GLActivity.KEY_GL_NAME_MODIFY) == null) {
                    Toast.makeText(CreateGLActivity.this, "Nincs törlendő elem!", Toast.LENGTH_SHORT).show();
                } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateGLActivity.this);
                alertDialogBuilder.setMessage("Biztos az elem törlésében?");
                alertDialogBuilder.setPositiveButton("Vissza",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                            }
                        });

                alertDialogBuilder.setNegativeButton("Törlés", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("gl")
                                .child(getIntent().getStringExtra(GLActivity.KEY_GL_KEY))
                                .child(getIntent().getStringExtra(GLActivity.KEY_GL_ASC))
                                .removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        Toast.makeText(CreateGLActivity.this, "Törlés sikeres!", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(CreateGLActivity.this, "Kérem frissítse az oldalt!", Toast.LENGTH_SHORT).show();
                                        finish();

                                    }
                                });

                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
                    return false;
                }

        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateGLActivity.this, "Törléshez nyomja hosszan a gombot!", Toast.LENGTH_SHORT).show();
            }
        });

        createName = findViewById(R.id.createNameGlET);
        createName.setError(getString(R.string.create_medication_name_alert), null);
        createDesc = findViewById(R.id.createDescGlET);
        createBTN = findViewById(R.id.createGlBTN);
        createBTN.setText("Elem mentése");
        createNumber = findViewById(R.id.createNumberGlET);
        choeserBtn = findViewById(R.id.createGlChooseImage);
        deleteImageBtn = findViewById(R.id.createGlDeleteImage);
        imageView = findViewById(R.id.createGlimage);

        colored = findViewById(R.id.CreateGlColor);

        colored2 = findViewById(R.id.CreateGlColor2);

        getAttrFunction();
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
        deleteImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateGLActivity.this);
                alertDialogBuilder.setMessage("Biztos, hogy törölni akarja a felhőből a képet?");
                alertDialogBuilder.setPositiveButton("Vissza",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                            }
                        });

                alertDialogBuilder.setNegativeButton("Törlés", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StorageReference ref = storageReference.child("images/" + getIntent().getStringExtra(GLActivity.KEY_GL_ASC));
                        ref.delete();
                        Toast.makeText(CreateGLActivity.this, "Kép törölve a Firebase felhőből!", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
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
        setTitle("Protokoll részlet név és kifejtés");


        setEdittextModify();

        clickCreateButton();
        //Toast.makeText(this, ""+getIntent().getStringExtra(GLActivity.KEY_GL_IMAGE_MODIFY), Toast.LENGTH_SHORT).show();


        boldBtn1 = findViewById(R.id.GlBTNBold1);
        italicBtn1 = findViewById(R.id.GlBTNItalic1);
        insertedBtn1 = findViewById(R.id.GlBTNInserted1);
        bigBtn1 = findViewById(R.id.GlBTNBig1);
        superBtn1 = findViewById(R.id.GlBTNSup1);
        subBtn1 = findViewById(R.id.GlBTNSub1);
        boldBtn2 = findViewById(R.id.GlBTNBold2);
        italicBtn2 = findViewById(R.id.GlBTNItalic2);
        insertedBtn2 = findViewById(R.id.GlBTNInserted2);
        bigBtn2 = findViewById(R.id.GlBTNBig2);
        superBtn2 = findViewById(R.id.GlBTNSup2);
        subBtn2 = findViewById(R.id.GlBTNSub2);
        boldBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + "<b></b>");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<b></b>";
                createName.getText().insert(start, s);
            }
        });

        italicBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<i></i>";
                createName.getText().insert(start, s);
            }
        });

        insertedBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<div></div>";
                createName.getText().insert(start, s);
            }
        });

        bigBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<big></big>";
                createName.getText().insert(start, s);
            }
        });
        superBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<sup></sup>";
                createName.getText().insert(start, s);
            }
        });

        subBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<sub></sub>";
                createName.getText().insert(start, s);
            }
        });
        boldBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int start = createDesc.getSelectionStart(); //this is to get the the cursor position
                String s = "<b></b>";
                createDesc.getText().insert(start, s);
            }
        });

        italicBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createDesc.getSelectionStart(); //this is to get the the cursor position
                String s = "<i></i>";
                createDesc.getText().insert(start, s);
            }
        });

        insertedBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createDesc.getSelectionStart(); //this is to get the the cursor position
                String s = "<div></div>";
                createDesc.getText().insert(start, s);
            }
        });

        bigBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createDesc.getSelectionStart(); //this is to get the the cursor position
                String s = "<big></big>";
                createDesc.getText().insert(start, s);
            }
        });
        superBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createDesc.getSelectionStart(); //this is to get the the cursor position
                String s = "<sup></sup>";
                createDesc.getText().insert(start, s);
            }
        });

        subBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createDesc.getSelectionStart(); //this is to get the the cursor position
                String s = "<sub></sub>";
                createDesc.getText().insert(start, s);
            }
        });

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
                Picasso.get().load(filePath).resize(800, 800).centerInside().onlyScaleDown().into(imageView);
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
            try {
                storageReference.child("images/" + getIntent().getStringExtra(GLActivity.KEY_GL_ASC)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override

                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).resize(1000, 1000).centerInside().onlyScaleDown().into(imageView);

                        // Toast.makeText(context, "Sikeres "+uri, Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //  Toast.makeText(context, "Sikertelen "+exception.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });
            } catch (Exception e) {
                Toast.makeText(this, "X", Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(this, ""+getIntent().getStringExtra(GLActivity.KEY_GL_COUNT_MODIFY), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * this is the click create button method
     */
    public void clickCreateButton() {
        createBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t1, t2, t3;
                t1 = createNumber.getText().toString();
                t2 = createName.getText().toString();
                t3 = createDesc.getText().toString();
                if (t1.matches("") ||
                        t2.matches("") ||
                        t3.matches("")) {
                    Toast.makeText(CreateGLActivity.this, "A mezők kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {

                    generateAttr();

                    Intent intent = new Intent();
                    //Toast.makeText(CreateGLActivity.this, ""+attr, Toast.LENGTH_SHORT).show();
                    intent.putExtra(KEY_ASC, asc);
                    intent.putExtra(KEY_TITLE, title);
                    intent.putExtra(KEY_ATTR, attr);
                    GL gl = new GL();
                    String tempCount = "0";
                    gl.setName(createName.getText().toString());
                    gl.setDesc(createDesc.getText().toString());
                    gl.setCount(tempCount);
                    gl.setKey(asc);
                    gl.setAttr(attr);
                    try {
                        if (createNumber.getText().toString() == null || createNumber.getText().toString() == "") {
                            tempCount = createNumber.getText().toString();
                        }

                        gl.setCount(tempCount);
                    } catch (Exception e) {

                    }
                    if (!(getIntent().getStringExtra(GLActivity.KEY_GL_KEY) == null)) {
                        FirebaseDatabase.getInstance().getReference().child("gl")
                                .child(getIntent().getStringExtra(GLActivity.KEY_GL_KEY))
                                .child(getIntent().getStringExtra(GLActivity.KEY_GL_ASC)).
                                child("name")
                                .setValue(createName.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("gl")
                                .child(getIntent().getStringExtra(GLActivity.KEY_GL_KEY))
                                .child(getIntent().getStringExtra(GLActivity.KEY_GL_ASC)).
                                child("desc")
                                .setValue(createDesc.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("gl")
                                .child(getIntent().getStringExtra(GLActivity.KEY_GL_KEY))
                                .child(getIntent().getStringExtra(GLActivity.KEY_GL_ASC)).
                                child("count")
                                .setValue(createNumber.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("gl")
                                .child(getIntent().getStringExtra(GLActivity.KEY_GL_KEY))
                                .child(getIntent().getStringExtra(GLActivity.KEY_GL_ASC)).
                                child("attr")
                                .setValue(attr);
                        uploadImage(getIntent().getStringExtra(GLActivity.KEY_GL_ASC));
                    } else {
                        saveGL(gl);
                    }

                    setResult(RESULT_OK, intent);
                    //Toast.makeText(CreateGLActivity.this, ""+attr, Toast.LENGTH_SHORT).show();
                    if (filePath == null) {
                        finish();
                    }
                }
            }
        });

    }

    public void generateAttr() {
        attr = "f";


        attr += "0";

        attr += "0";
        attr += "0";

        attr += "s";
        attr += "0";
        attr += "0";

        attr += "0";

        attr += "X";
        if (colored.isChecked()) {
            attr += 1;
        } else {
            attr += 1;
            color = "34495E";
        }
        attr += color;
        attr += "Y";
        if (colored2.isChecked()) {
            attr += 1;
        } else {
            attr += 1;
            color2 = "34495E";
        }

        attr += color2;
        attr += "W";



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

    public void getAttrFunction() {
      /*  if (!(getAttr == null)) {
            if (getAttr.contains("f10")) {
                bold.setChecked(true);
            } else if (getAttr.contains("f01")) {
                italian.setChecked(true);
            } else if (getAttr.contains("f11")) {
                bold.setChecked(true);
                italian.setChecked(true);
            } else {

            }


            if (getAttr.contains("s10")) {
                bold2.setChecked(true);
            } else if (getAttr.contains("s01")) {
                italian2.setChecked(true);
            } else if (getAttr.contains("s11")) {
                bold2.setChecked(true);
                italian2.setChecked(true);
            } else {

            }

            if (getAttr.contains("1s")) {
                underline.setChecked(true);
            }

            if (getAttr.contains("1X")) {
                underline2.setChecked(true);
            }
        } */
    }


}



