package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

public class GLEmptyActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_l_empty);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        imageView = findViewById(R.id.glEmptyImageView);

        /**  File localFile = null;
         try {
         localFile = File.createTempFile("images", "jpg");
         Toast.makeText(this, ""+localFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
         } catch (IOException e) {
         e.printStackTrace();
         }*/


        storageRef.child("images/1").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(GLEmptyActivity.this).load(uri).into(imageView);
                Toast.makeText(GLEmptyActivity.this, "Sikeres letöltés", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(GLEmptyActivity.this, "Sikertelen letöltés", Toast.LENGTH_SHORT).show();
            }
        });

    }

}


