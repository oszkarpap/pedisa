package hu.oszkarpap.dev.android.omsz.omszapp001;



import android.content.Context;
import android.content.Intent;
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
import android.widget.ExpandableListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.login.LoginMainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.MedActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity.AbcdeActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity.Acticity02;
import hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity.Avtivity03;
import hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity.RsiActivity;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /////
    private Context mContext;
    private String a = "Prehospitális vizsgálat sorrendje";


    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private Intent intent;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.bounce,R.anim.fade_in);
        auth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /////
        mContext = getApplicationContext();



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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_out,0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_medication) {
            intent = new Intent(MainActivity.this,MedActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_parameters) {
            intent = new Intent(MainActivity.this,ParametersActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_perfusor) {
            intent = new Intent(MainActivity.this,PerfusorActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_settings) {
            intent = new Intent(MainActivity.this,LoginMainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_log_out) {
            auth.signOut();
            finish();
        }





        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Protokollok", true, true, 1); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel(a, false, false, 11);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {

            childList.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Reanimáció", true, true, 2); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        childModelsList = new ArrayList<>();
        childModel = new MenuModel("MRT ERC ALS", false, false, 21);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {

            childList.put(menuModel, childModelsList);
        }

        // if (!menuModel.hasChildren) {
        //   childList.put(menuModel, null);
        // }

        menuModel = new MenuModel("Airway", true, true, 3); //Menu of Java Tutorials
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
        menuModel = new MenuModel("Breathing", true, true, 4); //Menu of Python Tutorials
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
        menuModel = new MenuModel("Circulation", true, true, 5); //Menu of Python Tutorials
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
        menuModel = new MenuModel("Disability", true, true, 6); //Menu of Python Tutorials
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
        menuModel = new MenuModel("Exposure", true, true, 7); //Menu of Python Tutorials
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

                //if (headerList.get(groupPosition).isGroup) {
                //  if (!headerList.get(groupPosition).hasChildren) {
                //    WebView webView = findViewById(R.id.webView);
                //  webView.loadUrl(headerList.get(groupPosition).url);
                //onBackPressed();
                //   }
                //  }

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

                        case (44):
                            intent = new Intent(MainActivity.this, RsiActivity.class);
                            startActivity(intent);
                            break;
                        case (32):
                            intent = new Intent(MainActivity.this, RsiActivity.class);




                            startActivity(intent);


//                            ActivityOptions options =
  //                                  ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.unzoom_in,R.anim.unzoom_in);
    //                        MainActivity.this.startActivity(intent, options.toBundle());
                          //  startActivity(intent);


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


}


