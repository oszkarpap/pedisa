package hu.oszkarpap.dev.android.omsz.omszapp001.main;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.CreateSOPActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOP;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPAdapter;
import hu.oszkarpap.dev.android.omsz.omszapp001.left.RsiActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.login.LoginMainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.MedActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.PerfusorActivity;

import static com.google.firebase.auth.FirebaseAuth.getInstance;


/**
 * @author Oszkar Pap
 * @version 1.0
 * This is main screen in activity
 * The String SAS set the layout of RSI activity
 */


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NewsAdapter.OnItemLongClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String KEY_NEWS_NAME_MODIFY = "NEWS_NAME_MODIFY";
    public static final String KEY_NEWS_TEXT_MODIFY = "NEWS_TEXT_MODIFY";
    public static final String KEY_NEWS_KEY_MODIFY = "NEWS_KEY_MODIFY";

    public static final int REQUEST_CODE = 111;
    public static final String SAS = "SAS";
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    private FirebaseAuth auth;
    private Button tut, dev;
    private Intent intent;
    private List<News> newsList;
    private NewsAdapter adapter;
    private News newsi;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;


    /**
     * in this method checked Firebase Realtime Database
     * and set tutorial and send email to developer action
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.news_swipe);
        swipeLayout.setOnRefreshListener(this);
        newsList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_news);
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NewsAdapter(this, newsList, this);
        recyclerView.setAdapter(adapter);

        loadNews();

        createMainActivity();
        tut = findViewById(R.id.tutorial);
        tut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Nincs feltöltve még fájl!", Toast.LENGTH_SHORT).show();
            }
        });

        dev = findViewById(R.id.email_to_dev);
        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.putExtra(Intent.EXTRA_SUBJECT, "OMSZ_APP; " +
                        user.getEmail() + ";\n" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

                intent.setData(Uri.parse("mailto:pap.oszkar.mt@gmail.com"));
                startActivity(intent);
            }
        });
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (RuntimeException e) {
            // Toast.makeText(this, "A Firebase újratöltődik!", Toast.LENGTH_SHORT).show();
        }
        auth = FirebaseAuth.getInstance();
    }

    private void loadNews() {


        FirebaseDatabase.getInstance().getReference().child("news").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                News news = dataSnapshot.getValue(News.class);
                newsList.add(news);
                Collections.sort(newsList);
                adapter.notifyDataSetChanged();
                //SOP sop = dataSnapshot.getValue(SOP.class);
                //sops.add(sop);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                News news = dataSnapshot.getValue(News.class);
                newsList.remove(news);
                Collections.sort(newsList);
                adapter.notifyDataSetChanged();
                //SOP med = dataSnapshot.getValue(SOP.class);
                //sops.remove(med);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            //overridePendingTransition(R.anim.fade_out, 0);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    /**
     * Set right menu item, where which item start which action
     * and check the user parameters, and if user delete, profil sign out
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.menu_settings) {
            ifDelUser();
            Objects.requireNonNull(auth.getCurrentUser()).reload();
            intent = new Intent(MainActivity.this, LoginMainActivity.class);
            startActivity(intent);
        }

        if (id == R.id.menu_news_add) {
            ifDelUser();
            Objects.requireNonNull(auth.getCurrentUser()).reload();
            intent = new Intent(MainActivity.this, CreateNewsActivity.class);
            startActivity(intent);
        }

        if (id == R.id.menu_log_out) {
            ifDelUser();
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


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Set the Navigatin drawer - left menu - omsz protocol menu item
     */

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
        childModel = new MenuModel("Rendszeresített gyógyszerek", false, false, 31);
        childModelsList.add(childModel);
        childModel = new MenuModel("Gyógyszerpumpa", false, false, 32);
        childModelsList.add(childModel);
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
                            intent = new Intent(MainActivity.this, RsiActivity.class);
                            startActivity(intent);
                            break;

                        case (32):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            ifDelUser();
                            intent = new Intent(MainActivity.this, PerfusorActivity.class);

                            startActivity(intent);
                            break;

                        case (31):
                            ifDelUser();
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            intent = new Intent(MainActivity.this, MedActivity.class);
                            startActivity(intent);
                            break;
                        case (0):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            Toast.makeText(MainActivity.this, "Sajnálom, nem implementáltam még a protokollt! Prehospitális vizsgálat sorrendje, Vénabiztosítás és az RSI működik!", Toast.LENGTH_LONG).show();
                        case (12):
                            ifDelUser();
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            intent = new Intent(MainActivity.this, SOPActivity.class);
                            startActivity(intent);
                            break;


                    }

                    onBackPressed();
                }

                return false;
            }
        });
    }

    /**
     * This method generate the look of main screen
     */

    public void createMainActivity() {
        //overridePendingTransition(R.anim.bounce, R.anim.fade_in);
        auth = getInstance();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
    }

    /**
     * This method checked the firebase RTDB for phone number
     */


    /**
     * This method check to profile, and sign out, if the admin del it
     */

    public void ifDelUser() {

        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, R.string.del_your_profile_main_a, Toast.LENGTH_LONG).show();
            auth.signOut();
            finish();
        }
    }

    private void deleteNews(final News news) {

        FirebaseDatabase.getInstance().getReference().child("news").child(news.getKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(MainActivity.this, "Törlés sikeres!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onItemLongClicked(int position) {
        newsi = newsList.get(position);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Módosítani vagy törölni szeretné az elemet? ");
        alertDialogBuilder.setPositiveButton("Vissza",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });

        alertDialogBuilder.setNegativeButton("Törlés", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteNews(newsi);
                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alertDialogBuilder.setNeutralButton("Módosítás", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, CreateNewsActivity.class);
                //             intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
                intent.putExtra(KEY_NEWS_KEY_MODIFY, newsi.getKey());
                intent.putExtra(KEY_NEWS_NAME_MODIFY, newsi.getName());
                intent.putExtra(KEY_NEWS_TEXT_MODIFY, newsi.getText());
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    @Override public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                ifDelUser();
                Objects.requireNonNull(auth.getCurrentUser()).reload();
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}


