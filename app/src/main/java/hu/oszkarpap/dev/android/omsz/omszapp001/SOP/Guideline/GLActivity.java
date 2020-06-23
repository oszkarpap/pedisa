package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem.CreateGLListItemActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.SingleChoiceDialogFragment;
//import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.MemoryActivity;

import static com.google.firebase.auth.FirebaseAuth.getInstance;
import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLAdapter.savedImage;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is the GL Activity, connect to Firebase Realtime Database
 */
public class GLActivity extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener, GLAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener {

    public static final int REQUEST_CODE = 998;
    public static final String KEY_GL_NAME_MODIFY = "NAME_MODIFY";
    public static final String KEY_GL_DESC_MODIFY = "DESC_MODIFY";
    public static final String KEY_GL_COUNT_MODIFY = "COUNT_MODIFY";
    public static final String KEY_GL_KEY_MODIFY = "KEY_MODIFY";
    public static final String KEY_GL_ASC_MODIFY = "KEY_MODIFY";
    public static final String KEY_GL_TITLE_MODIFY = "TITLE_MODIFY";
    public static final String KEY_GL_IMAGE_MODIFY = "IMAGE_MODIFY";

    public static final String KEY_GL_KEY = "KGK";
    public static final String KEY_GL_ASC = "KGA";
    public static final String KEY_GL_ATTR = "KGATTR";

    public static int TEXTSIZE = 20;

    RecyclerView recyclerView;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private String title, attr;
    private List<GL> gls;
    private GLAdapter adapter;
    private GL gli;
    private String SOPKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gl);


        overridePendingTransition(R.anim.bounce, R.anim.fade_in);

        Toolbar toolbar = findViewById(R.id.toolbar_gl);
        setSupportActionBar(toolbar);
        overridePendingTransition(0, 0);
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (RuntimeException e) {
            Toast.makeText(this, "A Firebase újratöltődik!", Toast.LENGTH_SHORT).show();

        }
        try {
            //TODO:
            storageRef.child("images/" + savedImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).resize(800, 800).centerInside().onlyScaleDown();
                    //saveImage(uri);
                    // Toast.makeText(context, "Sikeres "+uri, Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //  Toast.makeText(context, "Sikertelen "+exception.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });
        } catch (Exception e) {
        }

        SOPKey = getIntent().getStringExtra(SOPActivity.KEY_SOP_KEY_MODIFY);
        //Toast.makeText(this, ""+ SOPKey, Toast.LENGTH_SHORT).show();
        title = getIntent().getStringExtra(SOPActivity.KEY_SOP_NAME_MODIFY);
        setTitle(title);


        gls = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_gl);

        //recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GLAdapter(this, gls, this);
        recyclerView.setAdapter(adapter);


        loadGL();
        expandCW();


/**

 storageRef.child("images/1").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
@Override public void onSuccess(Uri uri) {
Glide.with(GLActivity.this).load(uri).into(imageView);
Toast.makeText(GLActivity.this, "Sikeres letöltés", Toast.LENGTH_SHORT).show();
}
}).addOnFailureListener(new OnFailureListener() {
@Override public void onFailure(@NonNull Exception exception) {
Toast.makeText(GLActivity.this, "Sikertelen letöltés", Toast.LENGTH_SHORT).show();
}
});
 */
    }


    /**
     * load medication from Firebase DB and
     * set medication adapter
     */
    private void loadGL() {
        /** databaseReference = FirebaseDatabase.getInstance().getReference().child("gl").child(SOPKey);
         databaseReference.addValueEventListener(new ValueEventListener() {
        @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        final GL gl = new GL();
        gl.setName(dataSnapshot.child("name").getValue().toString());
        gl.setKey(dataSnapshot.child("key").getValue().toString());
        gl.setDesc(dataSnapshot.child("desc").getValue().toString());
        gl.setAsc(dataSnapshot.child("asc").getValue().toString());
        gls.add(gl);
        adapter.notifyDataSetChanged();

        }

        @Override public void onCancelled(@NonNull DatabaseError databaseError) {

        }
        }); */

        FirebaseDatabase.getInstance().getReference().child("gl").child(SOPKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                GL gl = dataSnapshot.getValue(GL.class);


                //if(gl.getAsc().equals(SOPKey)){
                gls.add(gl);
                Collections.sort(gls);

                //}
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                GL gl = dataSnapshot.getValue(GL.class);
                gls.remove(gl);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method save the new medication to FB DB
     * some character is false as key in database, in the future  I will change this method
     */

    public void saveGL(final GL gl) {

        try {
            String key = gl.getKey();
            String x = key + System.currentTimeMillis();
            gl.setKey(key);

            gl.setAsc(x);
            FirebaseDatabase.getInstance().getReference()
                    .child("gl")
                    .child(key).child(x)
                    .setValue(gl)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(GLActivity.this, "Firebase felhőbe mentve!", Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, R.string.create_medication_name_alert, Toast.LENGTH_LONG).show();
        }


    }


    /**
     * delete medication method
     */

    private void deleteGL(final GL gl) {

        FirebaseDatabase.getInstance().getReference().child("gl").child(gl.getKey()).child(gl.getAsc()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(GLActivity.this, "Törlés sikeres!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GLActivity.this, GLActivity.class);
                intent.putExtra(KEY_GL_ASC_MODIFY, SOPKey);
                intent.putExtra(KEY_GL_TITLE_MODIFY, title);
                startActivityForResult(intent, REQUEST_CODE);
                finish();

            }
        });
    }


    /**
     * Set searchView right menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_gl, menu);
        MenuItem menuItem = menu.findItem(R.id.gl_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Keresés ");
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GLActivity.this, "Ne módosítson adatokat keresés közben!!", Toast.LENGTH_SHORT).show();


            }
        });


        return true;

    }

    /**
     * set other item in right menu
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.createglMenu) {
            // Toast.makeText(this, "Új gyógyszer felvitele MASTER funkció", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CreateGLActivity.class);
            //         intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
            intent.putExtra(KEY_GL_ASC_MODIFY, SOPKey);
            intent.putExtra(KEY_GL_TITLE_MODIFY, title);

            //intent.putExtra(KEY_GL_ASC_MODIFY,getIntent().getStringExtra(SOPActivity.KEY_SOP_KEY_MODIFY));
            startActivityForResult(intent, REQUEST_CODE);
        } else if (item.getItemId() == R.id.gl_refresh) {

            Intent intent = new Intent(GLActivity.this, GLActivity.class);
            intent.putExtra(KEY_GL_ASC_MODIFY, SOPKey);
            intent.putExtra(KEY_GL_TITLE_MODIFY, title);
            startActivityForResult(intent, REQUEST_CODE);
            finish();

        } else if (item.getItemId() == R.id.createglLow) {
            TEXTSIZE += 3;
            if (TEXTSIZE >= 40) {
                TEXTSIZE = 40;
            }
            // Toast.makeText(this, ""+TEXTSIZE, Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.createglHigh) {
            TEXTSIZE -= 3;
            if (TEXTSIZE <= 15) {
                TEXTSIZE = 15;
            }

            // Toast.makeText(this, ""+TEXTSIZE, Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * this method get data from create and modify medication menu and save new medication
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                title = data.getStringExtra(CreateGLActivity.KEY_TITLE);

                setTitle(title);
                String name = data.getStringExtra(CreateGLActivity.KEY_NAME);
                String desc = data.getStringExtra(CreateGLActivity.KEY_DESC);
                String asc = data.getStringExtra(CreateGLActivity.KEY_ASC);
                attr = data.getStringExtra(CreateGLActivity.KEY_ATTR);
                // Toast.makeText(this, "" + attr, Toast.LENGTH_SHORT).show();
                if (SOPKey == null) {
                    SOPKey = data.getStringExtra(CreateGLActivity.KEY_ASC);
                }//Toast.makeText(this, ""+desc, Toast.LENGTH_SHORT).show();
                GL gl = new GL();
                gl.setName(name);
                gl.setDesc(desc);
                gl.setKey(asc);
                gl.setAttr(attr);
                //  saveGL(gl);


            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * if short click one sop show up alert dialog, and can change duplicate or delete this medication
     *
     *

     @Override public void onItemClicked(final int position) {
     gli = gls.get(position);

     Intent intent = new Intent(GLActivity.this, GLActivity.class);
     intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
     intent.putExtra(KEY_GL_KEY_MODIFY, gli.getKey());
     startActivity(intent);
     } */

    /**
     * if long click one medication show up alert dialog, and can change duplicate or delete this medication
     */


    @Override
    public void onItemLongClicked(final int position) {
        // Toast.makeText(this, "Módosítási lehetőség csak MASTER funkció", Toast.LENGTH_SHORT).show();
        gli = gls.get(position);

        DialogFragment singleChoiceDialog = new SingleChoiceDialogFragment();
        singleChoiceDialog.setCancelable(false);
        singleChoiceDialog.show(getSupportFragmentManager(), "Single Choice Dialog");

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }


    /**
     * This method checked medication adapter after searchview parameters
     */
    @Override
    public boolean onQueryTextChange(String s) {

        String MedInput = s.toLowerCase();
        ArrayList<GL> newList = new ArrayList<>();

        for (GL glName : gls) {
            if (glName.getName().toLowerCase().contains(MedInput) || glName.getDesc().toLowerCase().contains(MedInput)) {
                newList.add(glName);
            }
        }

        adapter.updateList(newList);
        return true;
    }

    public void expandCW() {


    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        switch (position) {
            case 0:

                Intent intent = new Intent(GLActivity.this, CreateGLActivity.class);
                //              intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
                intent.putExtra(KEY_GL_NAME_MODIFY, gli.getName());
                intent.putExtra(KEY_GL_DESC_MODIFY, gli.getDesc());
                String x = String.valueOf(gli.getCount());
               // intent.putExtra(KEY_GL_IMAGE_MODIFY, savedImage);
                intent.putExtra(KEY_GL_COUNT_MODIFY, x);
                // Toast.makeText(GLActivity.this, "" + gli.getCount(), Toast.LENGTH_SHORT).show();
                intent.putExtra(KEY_GL_ASC_MODIFY, SOPKey);
                intent.putExtra(KEY_GL_TITLE_MODIFY, title);
                //intent.putExtra(KEY_GL_ASC_MODIFY,getIntent().getStringExtra(SOPActivity.KEY_SOP_KEY_MODIFY));
                intent.putExtra(KEY_GL_KEY, gli.getKey());
                intent.putExtra(KEY_GL_ASC, gli.getAsc());
                intent.putExtra(KEY_GL_ATTR, gli.getAttr());
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case 1:
                // Toast.makeText(GLActivity.this, "TODO", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(GLActivity.this, CreateGLListItemActivity.class);
                //              intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
                intent2.putExtra(KEY_GL_NAME_MODIFY, gli.getName());
                intent2.putExtra(KEY_GL_DESC_MODIFY, gli.getDesc());
                String x2 = String.valueOf(gli.getCount());
                intent2.putExtra(KEY_GL_COUNT_MODIFY, x2);
                //  Toast.makeText(GLActivity.this, "" + gli.getCount(), Toast.LENGTH_SHORT).show();
                intent2.putExtra(KEY_GL_ASC_MODIFY, gli.getAsc());
                intent2.putExtra(KEY_GL_TITLE_MODIFY, title);
                //intent.putExtra(KEY_GL_ASC_MODIFY,getIntent().getStringExtra(SOPActivity.KEY_SOP_KEY_MODIFY));

                //  startActivityForResult(intent2, REQUEST_CODE);
                startActivity(intent2);
                break;
            case 2:
                Toast.makeText(this, "Nem implementált funkció!", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                deleteGL(gli);
                break;

        }

    }


/*    public void saveImage(Uri downloadUrlOfImage) {
        String filename = "filename.jpg";
        String DIR_NAME = "PEDISA";
        File direct =
                new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + "/" + DIR_NAME + "/");


        if (!direct.exists()) {
            direct.mkdir();
            Toast.makeText(this, "Mappa létrehozva!", Toast.LENGTH_SHORT).show();
        }

        DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //Uri downloadUri = Uri.parse(downloadUrlOfImage);
        DownloadManager.Request request = new DownloadManager.Request(downloadUrlOfImage);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                        File.separator + DIR_NAME + File.separator + filename);

        dm.enqueue(request);
    } */


    @Override
    public void onNegativeButtonClicked() {

    }
}
