package hu.oszkarpap.dev.android.omsz.omszapp001.menu_activity.medication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import hu.oszkarpap.dev.android.omsz.omszapp001.memory.CreateMemoryActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.memory.adapter.MemoryAdapter;
import hu.oszkarpap.dev.android.omsz.omszapp001.memory.model.Memory;

public class MedActivity extends AppCompatActivity implements MedAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener {

    public static final int REQUEST_CODE = 999;
    private List<Medication> medications;
    private MedAdapter adapter;

    private Medication medi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med);
        overridePendingTransition(0, 0);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        medications = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_med);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new MedAdapter(this, medications, this);
        recyclerView.setAdapter(adapter);


        loadMed();

    }

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


    private void saveMed(final Medication med) {

        String key = FirebaseDatabase.getInstance().getReference().child("med").push().getKey();
        med.setKey(key);
        FirebaseDatabase.getInstance().getReference()
                .child("med")
                .child(key)
                .setValue(med)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MedActivity.this, "Firebase felhőbe mentve!!", Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private void deleteMed(final Medication med) {

        FirebaseDatabase.getInstance().getReference().child("med").child(med.getKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(MedActivity.this, "Törlés sikeres!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteAllMed() {
        FirebaseDatabase.getInstance().getReference().child("med").removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(MedActivity.this, "Összes elem törölve!", Toast.LENGTH_SHORT).show();
            }
        });
    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.removeAllMedication) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Biztos törölni szeretné az elemet?");
            alertDialogBuilder.setPositiveButton("Igen",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(MedActivity.this, "Törölve!", Toast.LENGTH_LONG).show();

                            deleteAllMed();
                            adapter.notifyDataSetChanged();
                        }
                    });

            alertDialogBuilder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();



        } else if (item.getItemId() == R.id.createMedMenu) {
            Intent intent = new Intent(this, CreateMedActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

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
                Medication med = new Medication(name, agent, pack, ind, contra, adult, child);
                saveMed(med);


            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemLongClicked(int position) {
        medi = medications.get(position);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Biztos törölni szeretné az elemet?");
        alertDialogBuilder.setPositiveButton("Igen",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MedActivity.this, "Törölve!", Toast.LENGTH_LONG).show();

                        deleteMed(medi);
                    }
                });

        alertDialogBuilder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

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
        return true;
    }
}
