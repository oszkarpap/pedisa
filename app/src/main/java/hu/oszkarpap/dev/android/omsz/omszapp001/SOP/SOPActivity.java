package hu.oszkarpap.dev.android.omsz.omszapp001.SOP;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.MemoryActivity;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is the SOP Activity, connect to Firebase Realtime Database
 */
public class SOPActivity extends MainActivity implements SOPAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener, SOPAdapter.OnItemClickListener {

    public static final int REQUEST_CODE = 999;
    public static final String KEY_SOP_NAME_MODIFY = "NAME_MODIFY";
    public static final String KEY_SOP_DESC_MODIFY = "DESC_MODIFY";
    public static final String KEY_SOP_KEY_MODIFY = "KEY_MODIFY";

    private List<SOP> sops;
    private SOPAdapter adapter;
    private SOP sopi;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_sop);
            createMainActivity();

        overridePendingTransition(0, 0);
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (RuntimeException e) {
            Toast.makeText(this, "A Firebase újratöltődik!", Toast.LENGTH_SHORT).show();
        }


        sops = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_sop);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);

        adapter = new SOPAdapter(this, sops, this, this);
        recyclerView.setAdapter(adapter);


        loadSOP();


    }


    /**
     * load medication from Firebase DB and
     * set medication adapter
     */
    private void loadSOP() {


        FirebaseDatabase.getInstance().getReference().child("sop").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SOP sop = dataSnapshot.getValue(SOP.class);
                sops.add(sop);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                SOP med = dataSnapshot.getValue(SOP.class);
                sops.remove(med);
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

    public void saveSOP(final SOP sop) {

        try {
            String key = sop.getName().replace(")", "0").replace("(", "0").replace("/", "0")
                    .replace(".", "0").replace(",", "0").replace("+", "0").replace("-", "0")
                    .replace("*", "0").replace("_", "0").replace("%", "0").replace("{", "0")
                    .replace("}", "0").toLowerCase() + "01" + System.currentTimeMillis();
            sop.setKey(key);
            FirebaseDatabase.getInstance().getReference()
                    .child("sop")
                    .child(key)
                    .setValue(sop)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(SOPActivity.this, "Firebase felhőbe mentve!", Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, R.string.create_medication_name_alert, Toast.LENGTH_LONG).show();
        }


    }


    /**
     * delete medication method
     */

    private void deleteSOP(final SOP sop) {

        FirebaseDatabase.getInstance().getReference().child("sop").child(sop.getKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(SOPActivity.this, "Törlés sikeres!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    /**
     * Set searchView right menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sop, menu);
        MenuItem menuItem = menu.findItem(R.id.sop_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Keresés ");
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SOPActivity.this, "Ne módosítson adatokat keresés közben!!", Toast.LENGTH_SHORT).show();


            }
        });

        return true;

    }

    /**
     * set other item in right menu
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.createSOPMenu) {
           // Toast.makeText(this, "Új gyógyszer felvitele MASTER funkció", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CreateSOPActivity.class);
            intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
            startActivityForResult(intent, REQUEST_CODE);
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
                String name = data.getStringExtra(CreateSOPActivity.KEY_NAME);
                String desc = data.getStringExtra(CreateSOPActivity.KEY_DESC);

                SOP sop = new SOP(name, desc);
                saveSOP(sop);


            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * if short click one sop show up alert dialog, and can change duplicate or delete this medication
     *
     * */

    @Override
    public void onItemClicked(final int position) {
        sopi = sops.get(position);

        Intent intent = new Intent(SOPActivity.this, GLActivity.class);
        intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
        intent.putExtra(KEY_SOP_KEY_MODIFY, sopi.getKey());
        intent.putExtra(KEY_SOP_NAME_MODIFY,sopi.getName());
        startActivity(intent);
    }

    /**
     * if long click one medication show up alert dialog, and can change duplicate or delete this medication
     */



    @Override
    public void onItemLongClicked(final int position) {
         // Toast.makeText(this, "Módosítási lehetőség csak MASTER funkció", Toast.LENGTH_SHORT).show();
           sopi = sops.get(position);

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
                deleteSOP(sopi);
                finish();
                Intent intent = new Intent(SOPActivity.this, SOPActivity.class);
                startActivity(intent);
            }
        });
        alertDialogBuilder.setNeutralButton("Duplikáció", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(SOPActivity.this, CreateSOPActivity.class);
                intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
                intent.putExtra(KEY_SOP_NAME_MODIFY, sopi.getName());
                intent.putExtra(KEY_SOP_DESC_MODIFY, sopi.getDesc());

                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
        ArrayList<SOP> newList = new ArrayList<>();

        for (SOP sopName : sops) {
            if (sopName.getName().toLowerCase().contains(MedInput) || sopName.getDesc().toLowerCase().contains(MedInput)) {
                newList.add(sopName);

            }
        }

        adapter.updateList(newList);

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        return true;
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
