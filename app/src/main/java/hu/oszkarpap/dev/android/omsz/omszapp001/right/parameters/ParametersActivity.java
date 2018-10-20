package hu.oszkarpap.dev.android.omsz.omszapp001.right.parameters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import hu.oszkarpap.dev.android.omsz.omszapp001.right.medication.Medication;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is Paramters Activity
 * set with seekbar the childage 0 - 10 and
 * set with seekbar the medication dose in kilogramm 1-50
 */


public class ParametersActivity extends MainActivity implements SearchView.OnQueryTextListener {


    private SeekBar seekBar, seekBarMed;
    private TextView age, hr, lsz, sys, ts, tm, th, bou, tidal, def;

    private RecyclerView recyclerView;
    private List<Medication> parametersMeds;
    public static MedParametersAdapter adapter;
    private TextView childWeight;
    private SearchView searchView;
    private Medication med;
    public static double progressValue;
    public String value01;
    public String value02;
    public double dose01;
    public double dose02;
    public double dose01D;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parameters);

        createMainActivity();
        overridePendingTransition(0, 0);

        seekBar = findViewById(R.id.par_sb);
        age = findViewById(R.id.par_age);
        hr = findViewById(R.id.par_hr);
        lsz = findViewById(R.id.par_lsz);
        sys = findViewById(R.id.par_sys);
        ts = findViewById(R.id.par_testsuly);
        tm = findViewById(R.id.par_tubusmeret);
        th = findViewById(R.id.par_tubushossz);
        bou = findViewById(R.id.par_bougie);
        tidal = findViewById(R.id.par_tidalvol);
        def = findViewById(R.id.par_def);


        seekBarMed = findViewById(R.id.par_med_sb);
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


        adapter = new MedParametersAdapter(this, parametersMeds);
        recyclerView.setAdapter(adapter);

        childParameters();

        loadMedParameters();

        seekBarMedmethod();


    }


    public void loadMedParameters() {
        FirebaseDatabase.getInstance().getReference().child("med").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                med = dataSnapshot.getValue(Medication.class);

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

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                age.setText(String.valueOf(progress));
                tidal.setText(String.valueOf(progress * 7));
                def.setText(String.valueOf(progress * 4));

                if (progress == 0) {
                    hr.setText("110-160");
                    lsz.setText("30-40");
                    sys.setText("70-90");
                    tidal.setText("24.5 ml újszülött, illetve 49ml féléves");
                    def.setText("14 J újszülött, illetve 28 J féléves");
                } else if (progress == 1 || progress == 2) {
                    hr.setText("100-150");
                    lsz.setText("25-35");
                    sys.setText("80-95");
                } else if (progress > 2 && progress < 6) {
                    hr.setText("90-140");
                    lsz.setText("25-30");
                    sys.setText("80-100");
                } else if (progress > 5 && progress <= 12) {
                    hr.setText("80-130");
                    lsz.setText("20-25");
                    sys.setText("90-110");
                } else if (progress > 12) {
                    hr.setText("60-100");
                    lsz.setText("15-20");
                    sys.setText("100-120");
                }


                if (progress > -1 && progress < 11) {

                    double value = seekBar.getProgress();


                    ts.setText(String.valueOf((value + 4) * 2));
                    tm.setText(String.valueOf((value / 4) + 4));
                    if (value == 0) {
                        ts.setText("3.5kg újszülött, a féléves 7");
                        tm.setText("3.5");

                    } else if (value == 1) {
                        tm.setText("4");
                    } else if (value == 3) {
                        tm.setText("4.5");
                    } else if (value == 5) {
                        tm.setText("5");
                    } else if (value == 7) {
                        tm.setText("6");
                    } else if (value == 9) {
                        tm.setText("6.5");
                    }
                    th.setText(String.valueOf((value / 2) + 12));


                }

                Double tmS = Double.parseDouble(tm.getText().toString());

                if (tmS > 6) {
                    bou.setText("15 Ch");
                }
                if (tmS == 5 || tmS == 6) {
                    bou.setText("10 Ch");
                }
                if (tmS < 5) {
                    bou.setText("ne használj bougie-t!");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    /**
     * this method set the parameters via child weigt
     */

    public void seekBarMedmethod() {


        seekBarMed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
     * this method set searchable rigth menu
     * serach only medication name
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

        return false;
    }


}