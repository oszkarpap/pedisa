package hu.oszkarpap.dev.android.omsz.omszapp001.medication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.left.RsiActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.ExpandableListAdapter;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MenuModel;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

//import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.MemoryActivity;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the Medication Activity, connect to Firebase Realtime Database
 */
public class PerfusorActivity extends MainActivity implements
        AdapterView.OnItemSelectedListener {

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private FirebaseAuth auth;
    private Spinner spinner;
    private EditText dose, weight, totalDose, volumen;
    private TextView mg_units, result;
    private Button calc, clear;
    private int number = 0;
    private double doseN, weightN, totalDoseN, volumenN;
    private String[] listPerf = {"Mg/Hr", "Mg/min", "Mcg/Min", "Mcg/Hr", "Mcg/Kg/Hr", "Mcg/Kg/Min", "Mg/Kg/Hr",
            "Mg/Kg/Min", "Units/Hr", "Units/Min", "Units/Kg/Hr", "Units/kg/min"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_perfusor);

        auth = getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Gyógyszerpumpa számológép");
        expandableListView = findViewById(R.id.expandableListView);
        leftMenuData();
        leftMenuIntent();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        overridePendingTransition(0, 0);


        spinner = findViewById(R.id.med_perf_spinner);
        dose = findViewById(R.id.med_perf_dose);
        weight = findViewById(R.id.med_perf_weight);
        totalDose = findViewById(R.id.med_perf_total_dose);
        volumen = findViewById(R.id.med_perf_volume);
        calc = findViewById(R.id.med_perf_calc);
        clear = findViewById(R.id.med_perf_clear);
        mg_units = findViewById(R.id.med_perf_mg_units);
        result = findViewById(R.id.med_perf_result);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listPerf);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(this);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dose.setText("");
                weight.setText("");
                totalDose.setText("");
                volumen.setText("");
            }
        });
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(PerfusorActivity.this, ""+weight.getText().toString(), Toast.LENGTH_SHORT).show();
                double perfusor = 1;

                try {
                    doseN = Double.parseDouble(dose.getText().toString());
                    weightN = Double.parseDouble(weight.getText().toString());
                    totalDoseN = Double.parseDouble(totalDose.getText().toString());
                    volumenN = Double.parseDouble(volumen.getText().toString());
                    perfusor = totalDoseN / volumenN;
                    //Toast.makeText(PerfusorActivity.this, ""+perfusor, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(PerfusorActivity.this, "A mezők kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                }

                if (number == 0) {
                    //Mg/Hr*
                    result.setText((doseN / perfusor) + " mL/Hr");
                }

                if (number == 1) {
                    //Mg/min*
                    result.setText((doseN / perfusor) * 60 + " mL/Hr");
                }

                if (number == 2) {
                    //Mcg/Min*
                    result.setText((doseN / perfusor / 1000) * 60 + " mL/Hr");
                }

                if (number == 3) {
                    //Mcg/Hr*
                    double x = doseN / 1000;
                    result.setText((x / perfusor) + " mL/Hr");
                }

                if (number == 4) {
                    //Mcg/Kg/Hr*
                    result.setText((doseN * weightN) / 1000 / perfusor + " mL/Hr");
                }

                if (number == 5) {
                    //Mcg/kG/min*
                    result.setText((doseN * weightN * 60) / 1000 / perfusor + " mL/Hr");
                }

                if (number == 6) {
                    //Mg/Kg/Hr*
                    result.setText((doseN / perfusor) * weightN + " mL/Hr");
                }

                if (number == 7) {
                    //Mg/Kg/Min*
                    result.setText((doseN / perfusor) * 60 * weightN + " mL/Hr");
                }
                if (number == 8) {
                    //U/Hr*
                    result.setText((doseN / perfusor) + " mL/Hr");
                }
                if (number == 9) {
                    //U/min*
                    result.setText((doseN / perfusor) * 60 + " mL/Hr");
                }
                if (number == 10) {
                    //U/Kg/Hr
                    result.setText((doseN / perfusor) * weightN + " mL/Hr");

                }
                if (number == 11) {
                    //U/kg/min*
                    result.setText((doseN / perfusor) * 60 * weightN + " mL/Hr");
                }

                DecimalFormat df = new DecimalFormat("#.##");

                result.setText(df.format(Double.parseDouble(result.getText().toString().replace(" mL/Hr","")))+" mL/Hr");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_perfusor, menu);
        return true;

    }

    @Override
    public void onBackPressed() {

        finish();
        super.onBackPressed();
    }


    private void leftMenuData() {

        MenuModel menuModel = new MenuModel("Eljárásrendek", true, true, 1);
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("", false, false, 11);
        childModel = new MenuModel("Rapid Sequence Intubation", false, false, 11);
        childModelsList.add(childModel);
        childModel = new MenuModel("Guideline", false, false, 12);
        childModelsList.add(childModel);


        if (!childList.containsValue(menuModel.hasChildren)) {

            childList.put(menuModel, childModelsList);
        }
        menuModel = new MenuModel("Gyógyszerek", true, true, 3);
        headerList.add(menuModel);
        childModelsList = new ArrayList<>();
        //      childModel = new MenuModel("Egyszerű Légút", false, false, 0);
        //    childModelsList.add(childModel);
        childModel = new MenuModel("Rendszeresített gyógyszerek", false, false, 31);
        childModelsList.add(childModel);
        childModel = new MenuModel("Gyógyszerpumpa számológép", false, false, 32);
        childModelsList.add(childModel);
        //       childModel = new MenuModel("Sürgősségi intubáció", false, false, 0);
        //       childModelsList.add(childModel);

        if (menuModel.hasChildren) {

            childList.put(menuModel, childModelsList);
        }

    }

    /**
     * ckeck the navigation drawer menu - left menu - protokoll menu, which element start to which activity,
     * and reload user parameters
     */
    private void leftMenuIntent() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {


                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    int a = model.modelId;
                    Intent intent;
                    switch (a) {

                        case (11):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            intent = new Intent(PerfusorActivity.this, RsiActivity.class);
                            startActivity(intent);
                            break;

                        case (32):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            intent = new Intent(PerfusorActivity.this, PerfusorActivity.class);
                            intent.putExtra(SAS, "02");
                            startActivity(intent);
                            break;

                        case (31):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            intent = new Intent(PerfusorActivity.this, MedActivity.class);
                            startActivity(intent);
                            break;
                        case (0):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            Toast.makeText(PerfusorActivity.this, "Sajnálom, nem implementáltam még a protokollt! Prehospitális vizsgálat sorrendje, Vénabiztosítás és az RSI működik!", Toast.LENGTH_LONG).show();
                        case (12):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            intent = new Intent(PerfusorActivity.this, SOPActivity.class);
                            startActivity(intent);
                            break;


                    }

                    onBackPressed();
                }

                return false;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        number = position;
        //Toast.makeText(this, "" + number, Toast.LENGTH_SHORT).show();
        if (position == 0 || position == 1 || position == 2 || position == 3 | position == 4 || position == 5 || position == 6 || position == 7) {
            mg_units.setText("mg");
        } else {
            mg_units.setText("Units");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This method generate the look of main screen
     */


}








