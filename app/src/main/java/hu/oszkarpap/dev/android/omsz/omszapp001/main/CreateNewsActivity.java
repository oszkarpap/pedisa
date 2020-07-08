package hu.oszkarpap.dev.android.omsz.omszapp001.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GL;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is created sop activity
 * This Activity used by create SOP ... action, modify Med action, create own Med action, modify own Med action
 */

public class CreateNewsActivity extends AppCompatActivity {

    public static final String KEY_NAME = "NAME";
    public static final String KEY_TEXT = "TEXT";
    public static final String KEY_DATE = "DATE";
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseAuth auth;
    private EditText createName, createText;
    private Button createBTN, choeserBtn, deleteImageBtn;
    private PhotoView imageView;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        setContentView(R.layout.activity_create_news);
        createText = findViewById(R.id.createNewsText);
        createName = findViewById(R.id.createNewsName);
        createBTN = findViewById(R.id.createNewsBTN);
        createBTN.setText("Elem mentése");
        choeserBtn = findViewById(R.id.createNewsChooseImage);
        deleteImageBtn = findViewById(R.id.createNewsDeleteImage);
        imageView = findViewById(R.id.createNewsImage);
        choeserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        deleteImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateNewsActivity.this);
                alertDialogBuilder.setMessage("Biztos, hogy törölni akarja a felhőből a képet?");
                alertDialogBuilder.setPositiveButton("Vissza",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                            }
                        });

                alertDialogBuilder.setNegativeButton("Törlés", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StorageReference ref = storageReference.child("images/" + getIntent().getStringExtra(MainActivity.KEY_NEWS_KEY_MODIFY));
                        ref.delete();
                        Toast.makeText(CreateNewsActivity.this, "Kép törölve a Firebase felhőből!", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        setTitle("Hír készítése");

        setEdittextModify();

        clickCreateButton();
        //Toast.makeText(this, ""+getIntent().getStringExtra(GLActivity.KEY_GL_IMAGE_MODIFY), Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(CreateNewsActivity.this, "Feltöltve", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateNewsActivity.this, "Sikertelen " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Picasso.get().load(filePath).resize(1200, 1200).centerInside().onlyScaleDown().into(imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * this method set EditText, if i can modify Medication parameters
     */
    public void setEdittextModify() {
        if (!(getIntent().getStringExtra(MainActivity.KEY_NEWS_KEY_MODIFY) == null)) {
            createName.setText(getIntent().getStringExtra(MainActivity.KEY_NEWS_NAME_MODIFY));
            createText.setText(getIntent().getStringExtra(MainActivity.KEY_NEWS_TEXT_MODIFY));
            try {
                storageReference.child("images/" + getIntent().getStringExtra(MainActivity.KEY_NEWS_KEY_MODIFY)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override

                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).resize(800, 800).centerInside().onlyScaleDown().into(imageView);

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


        }

    }

    /**
     * this is the click create button method
     */
    public void clickCreateButton() {
        createBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (
                        createName.getText().toString() == null || createName.getText().toString() == "" ||
                                createText.getText().toString() == null || createText.getText().toString() == " ") {
                    Toast.makeText(CreateNewsActivity.this, "A mezők kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {

                    News news = new News();
                    news.setName(createName.getText().toString());
                    news.setText(createText.getText().toString());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd, HH:mm");
                    Date resultdate = new Date((System.currentTimeMillis()));
                    news.setDate(String.valueOf(sdf.format(resultdate)));


                    if (!(getIntent().getStringExtra(MainActivity.KEY_NEWS_KEY_MODIFY) == null)) {
                        FirebaseDatabase.getInstance().getReference().child("news")
                                .child(getIntent().getStringExtra(MainActivity.KEY_NEWS_KEY_MODIFY))
                                .child("name")
                                .setValue(createName.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("news")
                                .child(getIntent().getStringExtra(MainActivity.KEY_NEWS_KEY_MODIFY))
                                .child("text")
                                .setValue(createText.getText().toString());

                        uploadImage(getIntent().getStringExtra(MainActivity.KEY_NEWS_KEY_MODIFY));
                    } else {
                        saveNews(news);
                    }


                }
                //Toast.makeText(CreateGLActivity.this, ""+attr, Toast.LENGTH_SHORT).show();
                if (filePath == null) {
                    finish();
                }

            }
        });

    }


    public void saveNews(final News news) {

        try {
            String x = news.getName() + System.currentTimeMillis();
            uploadImage(x);
            news.setKey(x);
            FirebaseDatabase.getInstance().getReference()
                    .child("news")
                    .child(x)
                    .setValue(news)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(CreateNewsActivity.this, "Firebase felhőbe mentve!", Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, R.string.create_medication_name_alert, Toast.LENGTH_LONG).show();
        }


    }


}



