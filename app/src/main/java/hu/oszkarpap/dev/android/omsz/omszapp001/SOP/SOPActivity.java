package hu.oszkarpap.dev.android.omsz.omszapp001.SOP;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.left.RsiActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.ExpandableListAdapter;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MenuModel;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.MedActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.PerfusorActivity;
//import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.MemoryActivity;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is the SOP Activity, connect to Firebase Realtime Database
 */
public class SOPActivity extends MainActivity implements SOPAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener, SOPAdapter.OnItemClickListener {

    public static final int REQUEST_CODE = 999;
    public static final String KEY_SOP_NAME_MODIFY = "NAME_MODIFY";
    public static final String KEY_SOP_DESC_MODIFY = "DESC_MODIFY";
    public static final String KEY_SOP_KEY_MODIFY = "KEY_MODIFY";
    public static int SOPSearching = 0;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private FirebaseAuth auth;
    private List<SOP> sops;
    private SOPAdapter adapter;
    private SOP sopi;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Szabványosított eljárásrendek");
        setContentView(R.layout.activity_sop);
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


        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (RuntimeException e) {
           // Toast.makeText(this, "A Firebase újratöltődik!", Toast.LENGTH_SHORT).show();
        }


        sops = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_sop);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);

        adapter = new SOPAdapter(this, sops, this, this);
        recyclerView.setAdapter(adapter);


        loadSOP();


    }


    /**
     * load medication from Firebase DB and
     * set medication adapter
     */
    private void loadSOP() {


        FirebaseDatabase.getInstance().getReference().child("sop").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SOP sop = dataSnapshot.getValue(SOP.class);
                sops.add(sop);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                SOP med = dataSnapshot.getValue(SOP.class);
                sops.remove(med);
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

    public void saveSOP(final SOP sop) {
        if (sop.getName() == null || sop.getDesc() == "" || sop.getName() == "" || sop.getDesc() == null) {
            Toast.makeText(this, "Mezők kitültése kötelező!", Toast.LENGTH_SHORT).show();
        } else {
            try {
                String key = sop.getName().replace(")", "0").replace("(", "0").replace("/", "0")
                        .replace(".", "0").replace(",", "0").replace("+", "0").replace("-", "0")
                        .replace("*", "0").replace("_", "0").replace("%", "0").replace("{", "0")
                        .replace("}", "0").toLowerCase() + "01" + System.currentTimeMillis();
                sop.setKey(key);
                FirebaseDatabase.getInstance().getReference()
                        .child("sop")
                        .child(key)
                        .setValue(sop)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SOPActivity.this, "Firebase felhőbe mentve!", Toast.LENGTH_SHORT).show();

                            }
                        });
            } catch (Exception e) {
                Toast.makeText(this, R.string.create_medication_name_alert, Toast.LENGTH_LONG).show();
            }

        }
    }


    /**
     * delete medication method
     */

    private void deleteSOP(final SOP sop) {

        FirebaseDatabase.getInstance().getReference().child("sop").child(sop.getKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(SOPActivity.this, "Törlés sikeres!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    /**
     * Set searchView right menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sop, menu);
        //MenuItem menuItem = menu.findItem(R.id.sop_search);

        //SearchView searchView = (SearchView) menuItem.getActionView();
        //searchView.setQueryHint("Keresés ");
        //searchView.setOnQueryTextListener(this);
        //searchView.setOnSearchClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Toast.makeText(SOPActivity.this, "Ne módosítson adatokat keresés közben!!", Toast.LENGTH_SHORT).show();


        //  }
        //});

        return true;

    }

    /**
     * set other item in right menu
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.createSOPMenu) {
            // Toast.makeText(this, "Új gyógyszer felvitele MASTER funkció", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CreateSOPActivity.class);
            //           intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
            startActivityForResult(intent, REQUEST_CODE);
        }
        if (item.getItemId() == R.id.SOPRefresh) {
            Intent intent = new Intent(this, SOPActivity.class);
            startActivity(intent);
            finish();
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
                String name = data.getStringExtra(CreateSOPActivity.KEY_NAME);
                String desc = data.getStringExtra(CreateSOPActivity.KEY_DESC);
                String icon = data.getStringExtra(CreateSOPActivity.KEY_ICON);
                Toast.makeText(this, "" + icon, Toast.LENGTH_SHORT).show();
                if (name == null || name == "" || desc == null || desc == "") {
                    Toast.makeText(this, "Mezők kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {
                    //  Toast.makeText(this, "" + name + desc, Toast.LENGTH_SHORT).show();
                    SOP sop = new SOP(name, desc, icon);
                    saveSOP(sop);
                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * if short click one sop show up alert dialog, and can change duplicate or delete this medication
     */

    @Override
    public void onItemClicked(final int position) {
        sopi = sops.get(position);

        Intent intent = new Intent(SOPActivity.this, GLActivity.class);
        //      intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
        intent.putExtra(KEY_SOP_KEY_MODIFY, sopi.getKey());
        intent.putExtra(KEY_SOP_NAME_MODIFY, sopi.getName());
        startActivity(intent);
    }

    @Override
    public void onItemClicked(SOP sop) {
        sopi = sop;

        Intent intent = new Intent(SOPActivity.this, GLActivity.class);
        //       intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
        intent.putExtra(KEY_SOP_KEY_MODIFY, sopi.getKey());
        intent.putExtra(KEY_SOP_NAME_MODIFY, sopi.getName());
        startActivity(intent);
    }

    /**
     * if long click one medication show up alert dialog, and can change duplicate or delete this medication
     */


    @Override
    public void onItemLongClicked(final int position) {
        // Toast.makeText(this, "Módosítási lehetőség csak MASTER funkció", Toast.LENGTH_SHORT).show();
        sopi = sops.get(position);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Duplikálni vagy törölni szeretné az elemet? ");
        alertDialogBuilder.setPositiveButton("Vissza",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });

        alertDialogBuilder.setNegativeButton("Törlés", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteSOP(sopi);
                finish();
                Intent intent = new Intent(SOPActivity.this, SOPActivity.class);
                startActivity(intent);
            }
        });
        alertDialogBuilder.setNeutralButton("Módosítás", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(SOPActivity.this, CreateSOPActivity.class);
                //             intent.putExtra(MemoryActivity.KEY_MEMORY, "NO");
                intent.putExtra(KEY_SOP_NAME_MODIFY, sopi.getName());
                intent.putExtra(KEY_SOP_DESC_MODIFY, sopi.getDesc());
                intent.putExtra(KEY_SOP_KEY_MODIFY, sopi.getKey());
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
        SOPSearching = 1;
        String MedInput = s.toLowerCase();
        ArrayList<SOP> newList = new ArrayList<>();

        for (SOP sopName : sops) {
            if (sopName.getName().toLowerCase().contains(MedInput) || sopName.getDesc().toLowerCase().contains(MedInput)) {
                newList.add(sopName);

            }
        }

        adapter.updateList(newList);

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        return true;
    }


    @Override
    public void onBackPressed() {
        SOPSearching = 0;
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
/*
        menuModel = new MenuModel("Reanimáció", true, true, 2);
        headerList.add(menuModel);
        childModelsList = new ArrayList<>();
        childModel = new MenuModel("MRT ERC ALS", false, false, 0);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {

            childList.put(menuModel, childModelsList);
        } */


        menuModel = new MenuModel("Gyógyszerek", true, true, 3);
        headerList.add(menuModel);
        childModelsList = new ArrayList<>();
        //      childModel = new MenuModel("Egyszerű Légút", false, false, 0);
        //    childModelsList.add(childModel);
        childModel = new MenuModel("Rendszeresített gyógyszerek", false, false, 31);
        childModelsList.add(childModel);
        childModel = new MenuModel("Gyógyszerpumpa", false, false, 32);
        childModelsList.add(childModel);
        //       childModel = new MenuModel("Sürgősségi intubáció", false, false, 0);
        //       childModelsList.add(childModel);

        if (menuModel.hasChildren) {

            childList.put(menuModel, childModelsList);
        }

     /*   childModelsList = new ArrayList<>();
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
*/
        //      childModelsList = new ArrayList<>();
        // menuModel = new MenuModel("Circulation", true, true, 5);
        // headerList.add(menuModel);
        // childModel = new MenuModel("Akut Coronária Szindróma", false, false, 0);
        // childModelsList.add(childModel);
        // childModel = new MenuModel("Akut Balszívfél elégtelenség", false, false, 0);
        // childModelsList.add(childModel);
        // childModel = new MenuModel("Cardioverzió", false, false, 0);
        // childModelsList.add(childModel);

        // if (menuModel.hasChildren) {
        //     childList.put(menuModel, childModelsList);
        // }

        // childModelsList = new ArrayList<>();
        // menuModel = new MenuModel("Disability", true, true, 6);
        // headerList.add(menuModel);
        // childModel = new MenuModel("Görcsroham", false, false, 0);
        // childModelsList.add(childModel);
        // childModel = new MenuModel("Sepszis", false, false, 0);
        // childModelsList.add(childModel);
        // childModel = new MenuModel("Folyadékterápia és keringéstámogatás", false, false, 0);
        // childModelsList.add(childModel);

        // if (menuModel.hasChildren) {
        //     childList.put(menuModel, childModelsList);
        // }

      /*  childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Environment", true, true, 7);
        headerList.add(menuModel);
        childModel = new MenuModel("Rögzítések", false, false, 0);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
*/

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
                            intent = new Intent(SOPActivity.this, RsiActivity.class);
                            startActivity(intent);
                            break;

                        case (32):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            intent = new Intent(SOPActivity.this, PerfusorActivity.class);
                            intent.putExtra(SAS, "02");
                            startActivity(intent);
                            break;

                        case (31):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            intent = new Intent(SOPActivity.this, MedActivity.class);
                            startActivity(intent);
                            break;
                        case (0):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            Toast.makeText(SOPActivity.this, "Sajnálom, nem implementáltam még a protokollt! Prehospitális vizsgálat sorrendje, Vénabiztosítás és az RSI működik!", Toast.LENGTH_LONG).show();
                        case (12):
                            Objects.requireNonNull(auth.getCurrentUser()).reload();
                            intent = new Intent(SOPActivity.this, SOPActivity.class);
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
