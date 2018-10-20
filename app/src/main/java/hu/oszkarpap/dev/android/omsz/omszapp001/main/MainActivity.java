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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.left.AbcdeActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.left.RsiActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.left.VeinActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.login.LoginMainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.medication.MedActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.MemoryActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.parameters.ParametersActivity;

import static com.google.firebase.auth.FirebaseAuth.getInstance;


/**
 * @author Oszkar Pap
 * @version 1.0
 * This is main screen in activity
 * The String SAS set the layout of RSI activity
 */


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final String SAS = "SAS";
    public static final String KONZULENS_SZAMA = "konzulensSzama";
    public static final String KANY_SZAMA = "kanySzama";
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private Intent intent;
    private FirebaseAuth auth;

    private TextView konzTv, kanyTv;
    private Button konzBtn, kanyBtn;
    private EditText konzEt, kanyEt;
    private String kanySzama;


    /**
     * in this method checked Firebase Realtime Database
     * and set tutorial and send email to developer action
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        createMainActivity();

        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (RuntimeException e) {
            Toast.makeText(this, "A Firebase újratöltődik!", Toast.LENGTH_SHORT).show();
        }

        Button toDev = findViewById(R.id.email_to_dev);

        konzEt = findViewById(R.id.main_konz_ET);
        konzBtn = findViewById(R.id.main_konz_BTN);
        konzTv = findViewById(R.id.main_konz_TV);


        kanyEt = findViewById(R.id.main_kany_ET);
        kanyBtn = findViewById(R.id.main_kany_BTN);
        kanyTv = findViewById(R.id.main_kany_TV);

        setPhoneNumbers();

        setKonzToFirebase();

        setKanyToFirebase();

        auth = FirebaseAuth.getInstance();

        toDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.putExtra(Intent.EXTRA_SUBJECT, "OMSZ APP");
                intent.setData(Uri.parse("mailto:pap.oszkar.mt@gmail.com"));
                startActivity(intent);
            }
        });

        Button tutorial = findViewById(R.id.tutorial);
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

    /**
     * Set right which menu item start which action
     * and check secret the user parameters, and if user delete, profil sign out
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_medication) {
            Objects.requireNonNull(auth.getCurrentUser()).reload();
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
            Objects.requireNonNull(auth.getCurrentUser()).reload();
            intent = new Intent(MainActivity.this, LoginMainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_kany) {
            ifDelUser();
            try {
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + kanySzama));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Nincs hívásindító az eszközén!", Toast.LENGTH_LONG).show();
            }
        }
        if (id == R.id.menu_help) {
            Objects.requireNonNull(auth.getCurrentUser()).reload();
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
            Objects.requireNonNull(auth.getCurrentUser()).reload();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Set the Navigatin drawer - left menu - omsz protocol menu item
     */

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
        childModel = new MenuModel("MRT ERC ALS", false, false, 0);
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

    /**
     * ckeck the navigation drawer menu - left menu - protokoll menu, which element start to which activity,
     * and reload secret user parameters
     */
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
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            intent = new Intent(MainActivity.this, VeinActivity.class);
                            startActivity(intent);
                            break;

                        case (32):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            intent = new Intent(MainActivity.this, RsiActivity.class);
                            intent.putExtra(SAS, "02");
                            startActivity(intent);
                            break;

                        case (11):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            intent = new Intent(MainActivity.this, AbcdeActivity.class);
                            startActivity(intent);
                            break;
                        case (0):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            Toast.makeText(MainActivity.this, "Sajnálom, nem implementáltam még a protokollt! Prehospitális vizsgálat sorrendje, Vénabiztosítás és az RSI működik!", Toast.LENGTH_LONG).show();


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

    /**
     * This method checked the firebase RTDB for phone number
     */

    public void setPhoneNumbers() {

        FirebaseDatabase.getInstance().getReference().child(KONZULENS_SZAMA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String t01 = "(" + Objects.requireNonNull(dataSnapshot.getValue()).toString() + ")";
                konzTv.setText(t01);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child(KANY_SZAMA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String t02 = "(" + Objects.requireNonNull(dataSnapshot.getValue()).toString() + ")";
                kanyTv.setText(t02);
                kanySzama = Objects.requireNonNull(dataSnapshot.getValue()).toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

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

    /**
     * This method change to konzulens phone number in firebase realtime database
     */
    public void setKonzToFirebase() {
        konzBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (konzEt.getText().toString().length() < 8) {
                    Toast.makeText(MainActivity.this, "Biztos telefonszámot adott meg?", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance().getReference()
                            .child(KONZULENS_SZAMA)
                            .setValue(konzEt.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(MainActivity.this, "Firebase felhőbe mentve!", Toast.LENGTH_SHORT).show();

                                }
                            });
                }
                FirebaseDatabase.getInstance().getReference().child(KONZULENS_SZAMA).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String t01 = "(" + Objects.requireNonNull(dataSnapshot.getValue()).toString() + ")";
                        konzTv.setText(t01);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    /**
     * This method change Kany phone Number in Firebase realtime database
     */
    public void setKanyToFirebase() {

        kanyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kanyEt.getText().toString().length() < 8) {
                    Toast.makeText(MainActivity.this, "Biztos telefonszámot adott meg?", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance().getReference()
                            .child(KANY_SZAMA)
                            .setValue(kanyEt.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(MainActivity.this, "Firebase felhőbe mentve!", Toast.LENGTH_SHORT).show();

                                }
                            });
                }
                FirebaseDatabase.getInstance().getReference().child(KANY_SZAMA).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String t01 = "(" + Objects.requireNonNull(dataSnapshot.getValue()).toString() + ")";
                        kanyTv.setText(t01);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    protected void onStop() {

        super.onStop();
    }
}


