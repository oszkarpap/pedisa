package hu.oszkarpap.dev.android.omsz.omszapp001.main;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.guide.LocationActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.guide.medication.MedActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.guide.memory.MemoryActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.guide.parameters.ParametersActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.login.LoginMainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.navigation.AbcdeActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.navigation.RsiActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.navigation.VeinActivity;

import static com.google.firebase.auth.FirebaseAuth.getInstance;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final String SAS = "SAS";
    public static final String RIGO = "RIGO";
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private Intent intent;
    private FirebaseAuth auth;

    private Button toDev, tutorial;
    private FirebaseUser user;





    /*created by
     * Oszkar Pap
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        createMainActivity();

        final PhoneNumber number = new PhoneNumber();


        toDev = findViewById(R.id.email_to_dev);



        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        toDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.putExtra(Intent.EXTRA_SUBJECT, "OMSZ APP");
                intent.setData(Uri.parse("mailto:pap.oszkar.mt@gmail.com"));
                startActivity(intent);
            }
        });

        tutorial = findViewById(R.id.tutorial);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/open?id=1NaBVLCYm43LENyoyse-u9iY6Ir5tJn4I"));
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            overridePendingTransition(R.anim.fade_out, 0);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_medication) {
            auth.getCurrentUser().reload();
            try {

                intent = new Intent(MainActivity.this, MedActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Elnézést, próbálja újra!", Toast.LENGTH_LONG).show();
            }
        }

        if (id == R.id.menu_parameters) {
            ifDelUser();

            intent = new Intent(MainActivity.this, ParametersActivity.class);
            startActivity(intent);

        }

        if (id == R.id.menu_settings) {
            auth.getCurrentUser().reload();
            intent = new Intent(MainActivity.this, LoginMainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_kany) {
            ifDelUser();
            try {
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0680210022"));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Nincs hívásindító az eszközén!", Toast.LENGTH_LONG).show();
            }
        }
        if (id == R.id.menu_help) {
            auth.getCurrentUser().reload();
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/open?id=1NaBVLCYm43LENyoyse-u9iY6Ir5tJn4I"));
            startActivity(intent);

        }

        if (id == R.id.menu_log_out) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Biztos ki akar jelentkezni?");
            alertDialogBuilder.setPositiveButton("Igen",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(MainActivity.this, "Sikeresen kijelentkezett!", Toast.LENGTH_LONG).show();
                            auth.signOut();
                            finish();
                        }
                    });

            alertDialogBuilder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "Nem jelentkezett ki!", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

        if (id == R.id.menu_note) {
            auth.getCurrentUser().reload();
            intent = new Intent(MainActivity.this, MemoryActivity.class);
            startActivity(intent);
        }

        if (id == R.id.menu_rsi) {
            ifDelUser();
            intent = new Intent(MainActivity.this, RsiActivity.class);
            intent.putExtra(SAS, "01");
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Szabványos eljárásrend", true, true, 1);
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Prehospitális vizsgálat sorrendje", false, false, 11);
        childModelsList.add(childModel);
        childModel = new MenuModel("Vénabiztosítás", false, false, 12);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {

            childList.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Reanimáció", true, true, 2);
        headerList.add(menuModel);
        childModelsList = new ArrayList<>();
        childModel = new MenuModel("MRT ERC ALS", false, false, 99);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {

            childList.put(menuModel, childModelsList);
        }


        menuModel = new MenuModel("Airway", true, true, 3);
        headerList.add(menuModel);
        childModelsList = new ArrayList<>();
        childModel = new MenuModel("Egyszerű Légút", false, false, 0);
        childModelsList.add(childModel);
        childModel = new MenuModel("RSI", false, false, 32);
        childModelsList.add(childModel);
        childModel = new MenuModel("Sürgősségi intubáció", false, false, 0);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {

            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Breathing", true, true, 4);
        headerList.add(menuModel);
        childModel = new MenuModel("Oxigén terápia", false, false, 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Akut asztmás roham", false, false, 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Noninvazív lélegeztetés", false, false, 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("COPD", false, false, 0);
        childModelsList.add(childModel);

        childModel = new MenuModel("Kapnográfia, kapnometria", false, false, 0);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Circulation", true, true, 5);
        headerList.add(menuModel);
        childModel = new MenuModel("Akut Coronária Szindróma", false, false, 0);
        childModelsList.add(childModel);
        childModel = new MenuModel("Akut Balszívfél elégtelenség", false, false, 0);
        childModelsList.add(childModel);
        childModel = new MenuModel("Cardioverzió", false, false, 0);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Disability", true, true, 6);
        headerList.add(menuModel);
        childModel = new MenuModel("Görcsroham", false, false, 0);
        childModelsList.add(childModel);
        childModel = new MenuModel("Sepszis", false, false, 0);
        childModelsList.add(childModel);
        childModel = new MenuModel("Folyadékterápia és keringéstámogatás", false, false, 0);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Environment", true, true, 7);
        headerList.add(menuModel);
        childModel = new MenuModel("Rögzítések", false, false, 0);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


    }

    private void populateExpandableList() {

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

                        case (12):
                            auth.getCurrentUser().reload();
                            intent = new Intent(MainActivity.this, VeinActivity.class);
                            startActivity(intent);
                            break;

                        case (32):
                            auth.getCurrentUser().reload();
                            intent = new Intent(MainActivity.this, RsiActivity.class);
                            intent.putExtra(SAS, "02");
                            startActivity(intent);
                            break;
                        case (999):
                            auth.getCurrentUser().reload();
                            intent = new Intent(MainActivity.this, LocationActivity.class);
                            startActivity(intent);
                            break;
                        case (11):
                            auth.getCurrentUser().reload();
                            intent = new Intent(MainActivity.this, AbcdeActivity.class);
                            startActivity(intent);
                            break;
                        case (0):
                            auth.getCurrentUser().reload();
                            Toast.makeText(MainActivity.this, "Sajnálom, nem implementáltam még a protokollt! Prehospitális vizsgálat sorrendje, Vénabiztosítás és az RSI működik!", Toast.LENGTH_LONG).show();


                    }

                    onBackPressed();
                }

                return false;
            }
        });
    }

    public void createMainActivity() {
        overridePendingTransition(R.anim.bounce, R.anim.fade_in);
        auth = getInstance();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void ifDelUser() {

        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "Profilja törölve lett, Nincs jogosultsága az alkalmazás használatához!", Toast.LENGTH_LONG).show();
            auth.signOut();
            finish();
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
    }
}


