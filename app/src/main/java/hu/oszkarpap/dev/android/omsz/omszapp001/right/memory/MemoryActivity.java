package hu.oszkarpap.dev.android.omsz.omszapp001.right.memory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.medication.CreateMedActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.medication.MedActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.medication.MedAdapter;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.medication.Medication;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.repository.Repository;
import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.repository.sqlite.SQLiteRepository;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the Medication Memory Activity, this is own Medications, there are only own device! do not connect the firebase
 */
public class MemoryActivity extends AppCompatActivity implements MedAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener {

    public static final int REQUEST_CODE = 111;

    private List<Medication> memories;
    public MedAdapter adapter;
    private Repository repository;
    private Medication memory;
    public static final String KEY_MEMORY = "MEMORY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        overridePendingTransition(0, 0);

        repository = new SQLiteRepository();

        memories = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_memory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new MedAdapter(this, memories, this);
        recyclerView.setAdapter(adapter);


        loadMemoriesAsync();

    }

    /**
     * load saved medication memory
     */

    private void loadMemoriesAsync() {
        Thread loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                repository.open(MemoryActivity.this);
                final List<Medication> memoriesLoaded = repository.getAllMemory();
                repository.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        memories.clear();
                        memories.addAll(memoriesLoaded);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        loadThread.start();
        adapter.notifyDataSetChanged();
    }

    /**
     * save new medication memory
     */

    public void saveMemoriesAsync(final Medication memo) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                repository.open(MemoryActivity.this);
                repository.saveMemory(memo);
                repository.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadMemoriesAsync();
                    }
                });
            }
        });
        thread.start();
        adapter.notifyDataSetChanged();


    }

    /**
     * delete selected own medication memory
     */
    private void deleteMemoryAsync(final Medication memo) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                repository.open(MemoryActivity.this);
                repository.deleteMemory(memo);
                repository.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadMemoriesAsync();
                    }
                });
            }
        });
        thread.start();
        adapter.notifyDataSetChanged();
    }

    /**
     * delete all medication memory in own DB
     */
    private void deleteAllAsync() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                repository.open(MemoryActivity.this);
                repository.deleteAllMemory();
                repository.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadMemoriesAsync();
                    }
                });
            }
        });
        thread.start();
        adapter.notifyDataSetChanged();
    }

    /**
     * create right menu with serchview
     * search for medication name and agent
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_memory, menu);

        MenuItem menuItem = menu.findItem(R.id.memo_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Keresés gyógyszernév vagy hatóanyag alapján ");
        searchView.setOnQueryTextListener(this);
        return true;

    }

    /**
     * set up other item in right menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.removeAllMemory) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Biztos törölni szeretné az ÖSSZES jegyzetet?");
            alertDialogBuilder.setPositiveButton("Igen",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(MemoryActivity.this, "Törölve!", Toast.LENGTH_LONG).show();
                            deleteAllAsync();
                            adapter.notifyDataSetChanged();
                            finish();

                        }
                    });

            alertDialogBuilder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        } else if (item.getItemId() == R.id.createMemoryMenu) {
            Intent intent = new Intent(this, CreateMedActivity.class);
            intent.putExtra(KEY_MEMORY, "YES");
            startActivityForResult(intent, REQUEST_CODE);
        } else if (item.getItemId() == R.id.memory_refresh) {
            finish();
            Intent intent = new Intent(MemoryActivity.this, MemoryActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * this method get new medication memory parameters and save in own DB
     */
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
                Medication memory = new Medication(name, agent, pack, ind, contra, adult, child);
                saveMemoriesAsync(memory);


            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * if long clicked one medication memory, show new alertdialoge, and can change duplicate or delete this medication memory
     */
    @Override
    public void onItemLongClicked(int position) {
        memory = memories.get(position);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Duplikálni vagy törölni szeretné az elemet?");
        alertDialogBuilder.setPositiveButton("Vissza",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });

        alertDialogBuilder.setNegativeButton("Törlés", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteMemoryAsync(memory);
            }
        });
        alertDialogBuilder.setNeutralButton("Duplikáció", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MemoryActivity.this, CreateMedActivity.class);
                intent.putExtra(KEY_MEMORY, "YES");
                intent.putExtra(MedActivity.KEY_NAME_MODIFY, memory.getName());
                intent.putExtra(MedActivity.KEY_AGENT_MODIFY, memory.getAgent());
                intent.putExtra(MedActivity.KEY_PACK_MODIFY, memory.getPack());
                intent.putExtra(MedActivity.KEY_IND_MODIFY, memory.getInd());
                intent.putExtra(MedActivity.KEY_CONTRA_MODIFY, memory.getCont());
                intent.putExtra(MedActivity.KEY_ADULT_MODIFY, memory.getAdult());
                intent.putExtra(MedActivity.KEY_CHILD_MODIFY, memory.getChild());


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
     * this method set up the adapter via searchview parameters
     */
    @Override
    public boolean onQueryTextChange(String s) {

        String MedInput = s.toLowerCase();
        List<Medication> newList = new ArrayList<>();

        for (Medication x : memories) {
            if (x.getName().toLowerCase().contains(MedInput) || x.getAgent().toLowerCase().contains(MedInput)
                    || x.getPack().toLowerCase().contains(MedInput) || x.getInd().toLowerCase().contains(MedInput)
                    || x.getCont().toLowerCase().contains(MedInput) || x.getAdult().toLowerCase().contains(MedInput)
                    || x.getChild().toLowerCase().contains(MedInput)) {
                newList.add(x);
            }
        }

        adapter.updateList(newList);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
