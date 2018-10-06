package hu.oszkarpap.dev.android.omsz.omszapp001.main;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.login.LoginMainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.menu_activity.HelpActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.menu_activity.ParametersActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.menu_activity.PerfusorActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.menu_activity.medication.MedActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity.AbcdeActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity.RsiActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity.VeinActivity;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private Intent intent;
    private FirebaseAuth auth;
    private Button toDev;




    /*created by
     * Oszkar Pap
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        createMainActivity();


        toDev = findViewById(R.id.email_to_dev);

        toDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.putExtra(Intent.EXTRA_SUBJECT, "OMSZ APP");
                intent.setData(Uri.parse("mailto:pap.oszkar.mt@gmail.com"));
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
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_out, 0);
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
            intent = new Intent(MainActivity.this, MedActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_parameters) {
            intent = new Intent(MainActivity.this, ParametersActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_perfusor) {
            intent = new Intent(MainActivity.this, PerfusorActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_settings) {
            intent = new Intent(MainActivity.this, LoginMainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_kany) {
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0680210022"));
            startActivity(intent);
        }

        if (id == R.id.menu_log_out) {
            auth.signOut();
            finish();
        }
        if (id == R.id.menu_help) {
            intent = new Intent(MainActivity.this, HelpActivity.class);
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

        MenuModel menuModel = new MenuModel("Protokollok", true, true, 1);
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
        childModel = new MenuModel("MRT ERC ALS", false, false, 21);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {

            childList.put(menuModel, childModelsList);
        }


        menuModel = new MenuModel("Airway", true, true, 3);
        headerList.add(menuModel);
        childModelsList = new ArrayList<>();
        childModel = new MenuModel("Egyszerű Légút", false, false, 31);
        childModelsList.add(childModel);
        childModel = new MenuModel("RSI", false, false, 32);
        childModelsList.add(childModel);
        childModel = new MenuModel("Sürgősségi intubáció", false, false, 33);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {

            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Breathing", true, true, 4);
        headerList.add(menuModel);
        childModel = new MenuModel("Oxigén terápia", false, false, 41);
        childModelsList.add(childModel);

        childModel = new MenuModel("Akut asztmás roham", false, false, 42);
        childModelsList.add(childModel);

        childModel = new MenuModel("Noninvazív lélegeztetés", false, false, 43);
        childModelsList.add(childModel);

        childModel = new MenuModel("COPD", false, false, 44);
        childModelsList.add(childModel);

        childModel = new MenuModel("Kapnográfia, kapnometria", false, false, 45);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Circulation", true, true, 5);
        headerList.add(menuModel);
        childModel = new MenuModel("Akut Coronária Szindróma", false, false, 51);
        childModelsList.add(childModel);
        childModel = new MenuModel("Akut Balszívfél elégtelenség", false, false, 52);
        childModelsList.add(childModel);
        childModel = new MenuModel("Cardioverzió", false, false, 53);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Disability", true, true, 6);
        headerList.add(menuModel);
        childModel = new MenuModel("Görcsroham", false, false, 61);
        childModelsList.add(childModel);
        childModel = new MenuModel("Sepszis", false, false, 62);
        childModelsList.add(childModel);
        childModel = new MenuModel("Folyadékterápia és keringéstámogatás", false, false, 63);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Environment", true, true, 7);
        headerList.add(menuModel);
        childModel = new MenuModel("Rögzítések", false, false, 71);
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
                            intent = new Intent(MainActivity.this, VeinActivity.class);
                            startActivity(intent);
                            break;
                        case (44):
                            intent = new Intent(MainActivity.this, RsiActivity.class);
                            startActivity(intent);
                            break;
                        case (32):
                            intent = new Intent(MainActivity.this, RsiActivity.class);
                           startActivity(intent);
                           break;
                        case (41):
                            intent = new Intent(MainActivity.this, AbcdeActivity.class);
                            startActivity(intent);
                            break;
                        case (11):
                            intent = new Intent(MainActivity.this, AbcdeActivity.class);
                            startActivity(intent);
                            break;
                        case (21):
                            intent = new Intent(MainActivity.this, MedActivity.class);
                            startActivity(intent);
                            break;



                    }

                    onBackPressed();
                }

                return false;
            }
        });
    }

    public void createMainActivity() {
        overridePendingTransition(R.anim.bounce, R.anim.fade_in);
        auth = FirebaseAuth.getInstance();


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

}


