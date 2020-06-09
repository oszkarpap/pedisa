package hu.oszkarpap.dev.android.omsz.omszapp001.right.medication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPAdapter;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.MemoryActivity;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the Medication Activity, connect to Firebase Realtime Database
 */
public class MedActivity extends MainActivity implements MedAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener, MedAdapter.OnItemClickListener {

    public static final int REQUEST_CODE = 999;
    public static final String KEY_NAME_MODIFY = "NAME_MODIFY";
    public static final String KEY_AGENT_MODIFY = "AGENT_MODIFY";
    public static final String KEY_PACK_MODIFY = "PACK_MODIFY";
    public static final String KEY_IND_MODIFY = "IND_MODIFY";
    public static final String KEY_CONTRA_MODIFY = "CONTRA_MODIFY";
    public static final String KEY_ADULT_MODIFY = "ADULT_MODIFY";
    public static final String KEY_CHILD_MODIFY = "CHILD_MODIFY";
    public static final String KEY_UNIT_MODIFY = "UNIT_MODIFY";
    public static final String KEY_CHILDD01_MODIFY = "CHILDD01_MODIFY";
    public static final String KEY_CHILDD02_MODIFY = "CHILDD02_MODIFY";
    public static final String KEY_CHILDDMAX_MODIFY = "CHILDMAX_MODIFY";
    public static final String KEY_CHILDDMAX02_MODIFY = "CHILDMAX02_MODIFY";
    public static final String KEY_CHILDDDESC_MODIFY = "CHILDDESC_MODIFY";
    private List<Medication> medications;
    private MedAdapter adapter;
    private TextView name, agent, pack, ind, contra, adult, child;
    private CardView cardView;
    private Medication medi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_med);
        createMainActivity();

        overridePendingTransition(0, 0);
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (RuntimeException e) {
            Toast.makeText(this, "A Firebase újratöltődik!", Toast.LENGTH_SHORT).show();
        }


        medications = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_med);
        recyclerView = findViewById(R.id.recycler_view_med);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));




        adapter = new MedAdapter(this, medications, this,this);
        recyclerView.setAdapter(adapter);


        loadMed();



        name = findViewById(R.id.med_cardview_name);
        agent = findViewById(R.id.med_cardview_agent);
        pack = findViewById(R.id.med_cardview_pack);
        ind = findViewById(R.id.med_cardview_ind);
        contra = findViewById(R.id.med_cardview_contra);
        adult = findViewById(R.id.med_cardview_adult);
        child = findViewById(R.id.med_cardview_child);
        cardView = findViewById(R.id.activitiy_med_cardview);
    }


    /**
     * load medication from Firebase DB and
     * set medication adapter
     */
    private void loadMed() {


        FirebaseDatabase.getInstance().getReference().child("med").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Medication med = dataSnapshot.getValue(Medication.class);
                medications.add(med);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Medication med = dataSnapshot.getValue(Medication.class);
                medications.remove(med);
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

    public void saveMed(final Medication med) {

        try {
            String key = med.getName().replace(")", "0").replace("(", "0").replace("/", "0")
                    .replace(".", "0").replace(",", "0").replace("+", "0").replace("-", "0")
                    .replace("*", "0").replace("_", "0").replace("%", "0").replace("{", "0")
                    .replace("}", "0").toLowerCase() + "01" + System.currentTimeMillis();
            med.setKey(key);
            FirebaseDatabase.getInstance().getReference()
                    .child("med")
                    .child(key)
                    .setValue(med)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MedActivity.this, "Firebase felhőbe mentve!", Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, R.string.create_medication_name_alert, Toast.LENGTH_LONG).show();
        }


    }


    /**
     * delete medication method
     */

    private void deleteMed(final Medication med) {

        FirebaseDatabase.getInstance().getReference().child("med").child(med.getKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(MedActivity.this, "Törlés sikeres!", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(MedActivity.this, MedActivity.class);
                startActivity(intent);

            }
        });
    }


    /**
     * Set searchView right menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_med, menu);
        MenuItem menuItem = menu.findItem(R.id.med_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Keresés gyógyszernév vagy hatóanyag alapján ");
        searchView.setOnQueryTextListener(this);
        return true;

    }

    /**
     * set other item in right menu
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.createMedMenu) {
            // Toast.makeText(this, "Új gyógyszer felvitele MASTER funkció", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CreateMedActivity.class);
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
                String name = data.getStringExtra(CreateMedActivity.KEY_NAME);
                String agent = data.getStringExtra(CreateMedActivity.KEY_AGENT);
                String pack = data.getStringExtra(CreateMedActivity.KEY_PACK);
                String ind = data.getStringExtra(CreateMedActivity.KEY_IND);
                String contra = data.getStringExtra(CreateMedActivity.KEY_CONTRA);
                String adult = data.getStringExtra(CreateMedActivity.KEY_ADULT);
                String child = data.getStringExtra(CreateMedActivity.KEY_CHILD);
                String childD01 = data.getStringExtra(CreateMedActivity.KEY_CHILD01);
                String childD02 = data.getStringExtra(CreateMedActivity.KEY_CHILD02);
                String unit = data.getStringExtra(CreateMedActivity.KEY_CHILDUNIT);
                String childDMax = data.getStringExtra(CreateMedActivity.KEY_CHILDMAX);
                String childDMax02 = data.getStringExtra(CreateMedActivity.KEY_CHILDMAX02);
                String childDdesc = data.getStringExtra(CreateMedActivity.KEY_CHILDDESC);
                Medication med = new Medication(name, agent, pack, ind, contra, adult,
                        child, childD01, childD02, unit, childDMax, childDMax02, childDdesc);
                saveMed(med);
                finish();
                Intent intent = new Intent(MedActivity.this, MedActivity.class);
                startActivity(intent);


            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * if long click one medication show up alert dialog, and can change duplicate or delete this medication
     */

    @Override
    public void onItemLongClicked(final int position) {
        // Toast.makeText(this, "Módosítási lehetőség csak MASTER funkció", Toast.LENGTH_SHORT).show();
        medi = medications.get(position);

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
                deleteMed(medi);
            }
        });
        alertDialogBuilder.setNeutralButton("Duplikáció", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MedActivity.this, CreateMedActivity.class);
                intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
                intent.putExtra(KEY_NAME_MODIFY, medi.getName());
                intent.putExtra(KEY_AGENT_MODIFY, medi.getAgent());
                intent.putExtra(KEY_PACK_MODIFY, medi.getPack());
                intent.putExtra(KEY_IND_MODIFY, medi.getInd());
                intent.putExtra(KEY_CONTRA_MODIFY, medi.getCont());
                intent.putExtra(KEY_ADULT_MODIFY, medi.getAdult());
                intent.putExtra(KEY_CHILD_MODIFY, medi.getChild());
                intent.putExtra(KEY_CHILDD01_MODIFY, medi.getChild01());
                intent.putExtra(KEY_CHILDD02_MODIFY, medi.getChild02());
                intent.putExtra(KEY_UNIT_MODIFY, medi.getUnit());
                intent.putExtra(KEY_CHILDDMAX_MODIFY, medi.getChildDMax());
                intent.putExtra(KEY_CHILDDMAX02_MODIFY, medi.getChildDMax02());
                intent.putExtra(KEY_CHILDDDESC_MODIFY, medi.getChildDDesc());


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
        ArrayList<Medication> newList = new ArrayList<>();

        for (Medication medName : medications) {
            if (medName.getName().toLowerCase().contains(MedInput) || medName.getAgent().toLowerCase().contains(MedInput)) {
                newList.add(medName);
            }
        }

        adapter.updateList(newList);
        adapter.notifyDataSetChanged();
        return true;

    }





    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onItemClicked(int position) {
        medi = medications.get(position);
        name.setText(medi.getName());
        agent.setText(medi.getAgent());
        pack.setText(medi.getPack());
        ind.setText(medi.getInd());
        contra.setText(medi.getCont());
        adult.setText(medi.getAdult());
        child.setText(medi.getChild());
        cardView.setVisibility(View.VISIBLE);



    }





}








