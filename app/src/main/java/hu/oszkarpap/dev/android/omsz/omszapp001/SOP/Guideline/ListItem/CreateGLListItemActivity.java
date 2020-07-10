package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.google.gson.internal.$Gson$Preconditions;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.CreateSOPActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.CreateGLActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity;

import static com.google.firebase.auth.FirebaseAuth.getInstance;
import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity.KEY_GL_ASC_MODIFY;

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
    private final int PICK_IMAGE_REQUEST = 72;
    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseAuth auth;
    private EditText createName, createNumber;
    private CheckBox colored;
    private Button createBTN, deleteBTN, boldBtn, italicBtn, insertedBtn, bigBtn, superBtn, subBtn, choeserBtn, deleteImageBtn;
    private String asc, title, color = "34495E", color2 = "34495E";
    private ColorPickerView colorPickerView;
    private String attr;
    private int update = 0;
    private String second, x;
    private PhotoView imageView;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = getInstance();
        storage = FirebaseStorage.getInstance();

        storageReference = storage.getReference();
        setContentView(R.layout.activity_create_glli);
        deleteBTN = findViewById(R.id.deleteGlliBTN);
        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateGLListItemActivity.this, "Törléshez nyomja hosszan a gombot!", Toast.LENGTH_SHORT).show();
            }
        });
        deleteBTN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateGLListItemActivity.this);
                    alertDialogBuilder.setMessage("Biztos törölni szeretné az elemet? ");
                    alertDialogBuilder.setPositiveButton("Vissza",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {


                                }
                            });

                    alertDialogBuilder.setNegativeButton("Törlés", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                FirebaseDatabase.getInstance().getReference().child("glli").child(getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_PARENT))
                                        .child(getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_SEC_KEY)).
                                        removeValue(new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                                Toast.makeText(CreateGLListItemActivity.this, "Törlés sikeres!", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(CreateGLListItemActivity.this, "Frissítse az oldalt!!", Toast.LENGTH_SHORT).show();
                                                finish();
                                                //Intent intent = new Intent(CreateGLListItemActivity.this, GLActivity.class);
                                                //startActivity(intent);
                                            }
                                        });
                            } catch (Exception e) {
                                Toast.makeText(CreateGLListItemActivity.this, "Nincs választott elem!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                return false;
            }
        });
        createName = findViewById(R.id.createNameGlliET);
        createName.setError(getString(R.string.create_medication_name_alert), null);
        createBTN = findViewById(R.id.createGlliBTN);
        createBTN.setText("Elem mentése");
        createNumber = findViewById(R.id.createNumberGlliET);
        choeserBtn = findViewById(R.id.createGlliChooseImage);
        deleteImageBtn = findViewById(R.id.createGlliDeleteImage);
        imageView = findViewById(R.id.createGlliimage);
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

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateGLListItemActivity.this);
                alertDialogBuilder.setMessage("Biztos, hogy törölni akarja a felhőből a képet?");
                alertDialogBuilder.setPositiveButton("Vissza",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                            }
                        });

                alertDialogBuilder.setNegativeButton("Törlés", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StorageReference ref = storageReference.child("images/" + getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_SEC_KEY));
                        ref.delete();
                        Toast.makeText(CreateGLListItemActivity.this, "Kép törölve a felhőből!", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
        colored = findViewById(R.id.CreateGlliColor);


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


        asc = getIntent().getStringExtra(KEY_GL_ASC_MODIFY);
        title = getIntent().getStringExtra(GLActivity.KEY_GL_TITLE_MODIFY);

        //Toast.makeText(this, ""+asc, Toast.LENGTH_SHORT).show();
        setTitle("Protokoll részlet listája");


        if (!(getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_NAME) == null)) {
            update = 1;
            createName.setText((getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_NAME)));
            createNumber.setText(getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_COUNT));
            try {
                storageReference.child("images/" + getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_SEC_KEY)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
            // Toast.makeText(this, "" + update, Toast.LENGTH_SHORT).show();
        }


        clickCreateButton();

        boldBtn = findViewById(R.id.GlliBTNBold);
        italicBtn = findViewById(R.id.GlliBTNItalic);
        insertedBtn = findViewById(R.id.GlliBTNInserted);
        bigBtn = findViewById(R.id.GlliBTNBig);
        superBtn = findViewById(R.id.GlliBTNSup);
        subBtn = findViewById(R.id.GlliBTNSub);

        boldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + "<b></b>");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<b></b>";
                createName.getText().insert(start, s);
            }
        });

        italicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<i></i>";
                createName.getText().insert(start, s);
            }
        });

        insertedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<div></div>";
                createName.getText().insert(start, s);
            }
        });

        bigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<big></big>";
                createName.getText().insert(start, s);
            }
        });
        superBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<sup></sup>";
                createName.getText().insert(start, s);
            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createName.setText(createName.getText().toString() + " ");
                int start = createName.getSelectionStart(); //this is to get the the cursor position
                String s = "<sub></sub>";
                createName.getText().insert(start, s);
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
            progressDialog.setTitle("Kép feltöltése a felhőbe ...");
            progressDialog.show();

            //StorageReference ref = storageReference.child("images/"+ asc);
            StorageReference ref = storageReference.child("images/" + imageName);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateGLListItemActivity.this, "Feltöltve", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateGLListItemActivity.this, "Sikertelen " + e.getMessage(), Toast.LENGTH_SHORT).show();
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


    /**
     * this is the click create button method
     */
    public void clickCreateButton() {
        createBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp1, temp2;
                temp1 = createName.getText().toString();
                temp2 = createNumber.getText().toString();

                if (temp1.matches("") || temp2.matches("")) {
                    Toast.makeText(CreateGLListItemActivity.this, "A mezők kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {
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
                        guideLineListItem.setCount(createNumber.getText().toString());
                    } catch (Exception e) {

                    }
                    try {
                        guideLineListItem.setParent(asc);
                    } catch (Exception e) {

                    }
                    saveGLLI(guideLineListItem);
                    setResult(RESULT_OK, intent);
                    if (filePath == null) {
                        finish();
                    }
                    //Toast.makeText(CreateGLActivity.this, ""+attr, Toast.LENGTH_SHORT).show();

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

        attr += "X";
        if (colored.isChecked()) {
            attr += 1;
        } else {
            attr += 1;
            color = "34495E";
        }
        attr += color;
        attr += "Y";

        attr += color2;
        attr += "W";



    }


    public void saveGLLI(final GuideLineListItem guideLineListItem) {

        try {
            String key;
            key = guideLineListItem.getKey();
            second = "unkonwn";
            if (update == 0) {
                second = getIntent().getStringExtra(GLActivity.KEY_GL_ASC_MODIFY);
                x = key + System.currentTimeMillis();
                guideLineListItem.setSecondKey(x);
                guideLineListItem.setKey(key);
                uploadImage(getIntent().getStringExtra(guideLineListItem.getSecondKey()));
            } else if (update == 1) {
                second = getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_PARENT);
                guideLineListItem.setParent(second);
                guideLineListItem.setKey(second);

                guideLineListItem.setCount(createNumber.getText().toString());
                x = getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_SEC_KEY);
                guideLineListItem.setSecondKey(x);
            }


            // guideLineListItem.setAsc(x);
            FirebaseDatabase.getInstance().getReference()
                    .child("glli")
                    .child(second).child(x)
                    .setValue(guideLineListItem)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(CreateGLListItemActivity.this, "Felhőbe mentve!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(CreateGLListItemActivity.this, "Kérem frissítse az oldat!", Toast.LENGTH_SHORT).show();
                            uploadImage(getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_SEC_KEY));

                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, R.string.create_medication_name_alert, Toast.LENGTH_LONG).show();
        }

    }
}



