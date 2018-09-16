package hu.oszkarpap.dev.android.omsz.omszapp001.medication;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import hu.oszkarpap.dev.android.omsz.omszapp001.MainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.R;

public class MedActivity extends AppCompatActivity
        {

        private RecyclerView recyclerView;
        private MedicationAdapter adapter;
        private ArrayList<Medication> employeeArrayList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_med);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);



            employeeArrayList = new ArrayList<>();
            employeeArrayList.add(new Medication("Employee1", "emp1@example.com", "123456789"));
            employeeArrayList.add(new Medication("Employee2", "emp2@example.com", "987654321"));
            employeeArrayList.add(new Medication("Employee3", "emp3@example.com", "789456123"));
            employeeArrayList.add(new Medication("Employee4", "emp4@example.com", "321654987"));

            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            adapter = new MedicationAdapter(employeeArrayList);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MedActivity.this);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_search:

        }
        return true;
    }
}

