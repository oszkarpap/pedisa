/** package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOP;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.MemoryActivity;

public class GuidelineActivity_template extends AppCompatActivity implements SearchView.OnQueryTextListener, GuidelineAdapter.OnItemLongClickListener{

  private TextView name, desc;
    private DatabaseReference databaseReference;
    private Toolbar Toolbar;
    public static final int REQUEST_CODE = 2;
    public static final String KEY_NAME_MODIFY = "NAME_MODIFY";
    public static final String KEY_DESC_MODIFY = "DESC_MODIFY";
    public static final String KEY_KEY_MODIFY = "KEY_MODIFY";
    private List<GuidelineComponent> glcs;
    private GuidelineAdapter adapter;
    private GuidelineComponent guidelineComponenti;
    private String ascending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideline);
        Toolbar =  findViewById(R.id.guideline_toolbar);

        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (RuntimeException e) {
            Toast.makeText(this, "A Firebase újratöltődik!", Toast.LENGTH_SHORT).show();
        }


        glcs = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_guideline);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GuidelineAdapter(this, glcs);
        recyclerView.setAdapter(adapter);



        setSupportActionBar(Toolbar);
        name = findViewById(R.id.GuidellineNameSopET);
        desc = findViewById(R.id.GuidelineDescSopET);
        try {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("sop").child(getIntent().getStringExtra(SOPActivity.KEY_KEY_MODIFY));
        }catch (Exception e) {
            Toast.makeText(this, " "+e, Toast.LENGTH_SHORT).show();
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue().toString());
                desc.setText(dataSnapshot.child("desc").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        loadGL();
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_guideline, menu);
        MenuItem menuItem = menu.findItem(R.id.guideline_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Keresés gyógyszernév vagy hatóanyag alapján ");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.createGuidelineMenu) {
            Intent intent = new Intent(this, CreateGuidelineComponentActivity.class);
            intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
            startActivityForResult(intent, REQUEST_CODE);
        } else if (item.getItemId() == R.id.sop_refresh) {
            finish();
            Intent intent = new Intent(GuidelineActivity_template.this, GuidelineActivity_template.class);
            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }



    /**
     * if long click one medication show up alert dialog, and can change duplicate or delete this medication




    @Override
    public void onItemLongClicked(final int position) {
        // Toast.makeText(this, "Módosítási lehetőség csak MASTER funkció", Toast.LENGTH_SHORT).show();
        guidelineComponenti = glcs.get(position);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Duplikálni vagy törölni szeretné az elemet?");
        alertDialogBuilder.setPositiveButton("Vissza",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });

        alertDialogBuilder.setNegativeButton("Törlés", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
             //   deleteGL(guidelineComponenti);
            }
        });
        alertDialogBuilder.setNeutralButton("Duplikáció", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GuidelineActivity_template.this, CreateGuidelineComponentActivity.class);
                intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
                intent.putExtra(KEY_NAME_MODIFY, guidelineComponenti.getName());
                intent.putExtra(KEY_DESC_MODIFY, guidelineComponenti.getDesc());

                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra(CreateGuidelineComponentActivity.KEY_NAME);
                String desc = data.getStringExtra(CreateGuidelineComponentActivity.KEY_DESC);
                ascending = data.getStringExtra(CreateGuidelineComponentActivity.KEY_KEY);
                GuidelineComponent guidelineComponent = new GuidelineComponent(name, desc, ascending);
                saveGL(guidelineComponent);


            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * load medication from Firebase DB and
     * set medication adapter

    private void loadGL() {


        FirebaseDatabase.getInstance().getReference().child("guideline").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                GuidelineComponent guidelineComponent = dataSnapshot.getValue(GuidelineComponent.class);
               // if(guidelineComponent.getAscend().contains(getIntent().getStringExtra(SOPActivity.KEY_KEY_MODIFY)) ){
                glcs.add(guidelineComponent);
            //}
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                GuidelineComponent guidelineComponent = dataSnapshot.getValue(GuidelineComponent.class);
                glcs.remove(guidelineComponent);
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


    public void saveGL(final GuidelineComponent guidelineComponent) {

        try {
            String key = guidelineComponent.getAscend()+ System.currentTimeMillis();
            guidelineComponent.setKey(key);
            FirebaseDatabase.getInstance().getReference()
                    .child("guideline")
                    .child(key)
                    .setValue(guidelineComponent)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(GuidelineActivity_template.this, "Firebase felhőbe mentve!", Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, R.string.create_medication_name_alert, Toast.LENGTH_LONG).show();
        }


    }


    /**
     * delete medication method


    private void deleteGL(final SOP sop) {

        FirebaseDatabase.getInstance().getReference().child("sop").child(sop.getKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(GuidelineActivity_template.this, "Törlés sikeres!", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
*/

