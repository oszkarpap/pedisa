package hu.oszkarpap.dev.android.omsz.omszapp001.right.parameters;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.Medication;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is Paramters Activity
 * set with seekbar the childage 0 - 10 and
 * set with seekbar the medication dose in kilogramm 1-50
 */


public class ParametersActivity extends MainActivity implements ParametersAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener {


    private SeekBar seekBarChildAge, seekBarMedicationParameters;
    private TextView ageChild, hr, lsz, sys, ttkg, etSize, etLength, bougie, tidalVol, def;
    private RecyclerView recyclerView;
    private List<Medication> parametersMeds;
    public static ParametersAdapter adapter;
    private TextView childWeight;
    private SearchView searchView;
    public static int progressValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parameters);

        createMainActivity();
        overridePendingTransition(0, 0);

        seekBarChildAge = findViewById(R.id.par_sb);
        ageChild = findViewById(R.id.par_age);
        hr = findViewById(R.id.par_hr);
        lsz = findViewById(R.id.par_lsz);
        sys = findViewById(R.id.par_sys);
        ttkg = findViewById(R.id.par_testsuly);
        etSize = findViewById(R.id.par_tubusmeret);
        etLength = findViewById(R.id.par_tubushossz);
        bougie = findViewById(R.id.par_bougie);
        tidalVol = findViewById(R.id.par_tidalvol);
        def = findViewById(R.id.par_def);


        seekBarMedicationParameters = findViewById(R.id.par_med_sb);
        childWeight = findViewById(R.id.par_weight);


        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (RuntimeException e) {
            Toast.makeText(this, "A Firebase újratöltődik!", Toast.LENGTH_SHORT).show();
        }


        parametersMeds = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_par_med);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new ParametersAdapter(this, parametersMeds, this);
        recyclerView.setAdapter(adapter);

        childParameters();

        loadMedParameters();

        seekBarMedmethod();


    }


    public void loadMedParameters() {
        FirebaseDatabase.getInstance().getReference().child("med").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Medication med = dataSnapshot.getValue(Medication.class);

                parametersMeds.add(med);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Medication med = dataSnapshot.getValue(Medication.class);
                parametersMeds.remove(med);
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
     * this method set the parameters via child age
     */

    private void childParameters() {

        seekBarChildAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                double age = progress;

                if (progress == 1) {
                    age = 0.5;
                } else if (progress > 1) {
                    age = progress - 1;
                }

                ageChild.setText(String.valueOf(age)+" év");
                ttkg.setText(String.valueOf((age + 4) * 2));
                etSize.setText(String.valueOf((age / 4) + 4));
                etLength.setText(String.valueOf((age / 2) + 12));
                def.setText(String.valueOf((age + 4) * 2 * 4)+" J");
                tidalVol.setText(String.valueOf((age + 4) * 2 * 7));


                if (age == 0) {
                    ttkg.setText("3.5");
                    etSize.setText("3.5");
                    hr.setText("110-160");
                    lsz.setText("30-40");
                    sys.setText("70-90");
                    tidalVol.setText("24.5 ");
                    def.setText("14 J ");
                } else if (age == 0.5) {
                    etSize.setText("3.5");
                    ttkg.setText("7");
                    etLength.setText("12");
                    hr.setText("110-160");
                    lsz.setText("30-40");
                    sys.setText("70-90");
                    tidalVol.setText("49 ");
                    def.setText("28 J");
                } else if (age == 1) {
                    etSize.setText("4");
                    hr.setText("100-150");
                    lsz.setText("25-35");
                    sys.setText("80-95");
                } else if (age == 2) {
                    hr.setText("100-150");
                    lsz.setText("25-35");
                    sys.setText("80-95");
                } else if (age == 3) {
                    etSize.setText("4.5");
                } else if (age > 2 && age < 6) {
                    hr.setText("90-140");
                    lsz.setText("25-30");
                    sys.setText("80-100");
                } else if (age > 5 && age <= 12) {
                    hr.setText("80-130");
                    lsz.setText("20-25");
                    sys.setText("90-110");
                } else if (age == 5) {
                    etSize.setText("5");
                } else if (age == 7) {
                    etSize.setText("6");
                } else if (age == 9) {
                    etSize.setText("6.5");
                }


                Double tmS = Double.parseDouble(etSize.getText().toString());

                if (tmS > 6) {
                    bougie.setText("15 Ch");
                }
                if (tmS == 5 || tmS == 6) {
                    bougie.setText("10 Ch");
                }
                if (tmS < 5) {
                    bougie.setText("ne használjon!");
                }


        }

        @Override
        public void onStartTrackingTouch (SeekBar seekBar){
        }

        @Override
        public void onStopTrackingTouch (SeekBar seekBar){
        }
    });
}


    /**
     * this method set the parameters via child weight
     */

    public void seekBarMedmethod() {


        seekBarMedicationParameters.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                childWeight.setText("Gyermek tömege: " + String.valueOf(progress) + " kg");
                progressValue = progress;
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                adapter.notifyDataSetChanged();


            }
        });
    }


    /**
     * this method set searchable right menu
     * because the parameters are together in one TextView, can search all child Medication parameters, but only medication!
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_par, menu);
        MenuItem menuItem = menu.findItem(R.id.par_search);

        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Gyógyszer neve:");
        searchView.setOnQueryTextListener(this);
        return true;

    }


    /**
     * this method create the refresh menu item, because
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.par_refresh) {
            finish();
            Intent intent = new Intent(ParametersActivity.this, ParametersActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }


    /**
     * this method set up the adapter via serachview parameters
     */
    @Override
    public boolean onQueryTextChange(String s) {
        String Input = s.toLowerCase();

        ArrayList<Medication> newList = new ArrayList<>();
        for (Medication x : parametersMeds) {
            if (x.getName().toLowerCase().contains(Input)) {
                newList.add(x);
            }
        }

        adapter.updateList(newList);
        seekBarMedicationParameters.setVisibility(View.INVISIBLE);
        return true;
    }

    /**
     * if long clicked in one medication, show the agent in alert dialog
     * */
    @Override
    public void onItemLongClicked(final int position) {
        final Medication med = parametersMeds.get(position);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Hatóanyag: " + med.getAgent());

        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
