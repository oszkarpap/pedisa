package hu.oszkarpap.dev.android.omsz.omszapp001.memory;

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
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.memory.adapter.MemoryAdapter;
import hu.oszkarpap.dev.android.omsz.omszapp001.memory.model.Memory;
import hu.oszkarpap.dev.android.omsz.omszapp001.memory.repository.Repository;
import hu.oszkarpap.dev.android.omsz.omszapp001.memory.repository.sqlite.SQLiteRepository;

public class MemoryActivity extends MainActivity implements MemoryAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener {

    public static final int REQUEST_CODE = 111;
    private List<Memory> memories;
    private MemoryAdapter adapter;
    private Repository repository;
    private Memory memory;


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


        adapter = new MemoryAdapter(this, memories, this);
        recyclerView.setAdapter(adapter);


        loadMemoriesAsync();

    }

    private void loadMemoriesAsync() {
        Thread loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                repository.open(MemoryActivity.this);
                final List<Memory> memoriesLoaded = repository.getAllMemory();
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
    }


    private void saveMemoriesAsync(final Memory memo) {
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


    }

    private void deleteMemoryAsync(final Memory memo) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                repository.open(MemoryActivity.this);
                repository.deleteMemory(memory);
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
    }

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
    }

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
            Intent intent = new Intent(this, CreateMemoryActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra(CreateMemoryActivity.KEY_NAME);
                String agent = data.getStringExtra(CreateMemoryActivity.KEY_AGENT);
                String pack = data.getStringExtra(CreateMemoryActivity.KEY_PACK);
                String ind = data.getStringExtra(CreateMemoryActivity.KEY_IND);
                String contra = data.getStringExtra(CreateMemoryActivity.KEY_CONTRA);
                String adult = data.getStringExtra(CreateMemoryActivity.KEY_ADULT);
                String child = data.getStringExtra(CreateMemoryActivity.KEY_CHILD);
                Memory memory = new Memory(name, agent, pack, ind, contra, adult, child);
                saveMemoriesAsync(memory);


            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemLongClicked(int position) {
        memory = memories.get(position);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Biztos törölni szeretné a jegyzetet?");
        alertDialogBuilder.setPositiveButton("Igen",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MemoryActivity.this, "Törölve!", Toast.LENGTH_LONG).show();

                        deleteMemoryAsync(memory);
                    }
                });

        alertDialogBuilder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        String MedInput = s.toLowerCase();
        List<Memory> newList = new ArrayList<>();

        for (Memory x : memories) {
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
