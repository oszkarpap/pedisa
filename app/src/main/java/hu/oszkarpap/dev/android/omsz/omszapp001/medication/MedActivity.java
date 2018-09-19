package hu.oszkarpap.dev.android.omsz.omszapp001.medication;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import hu.oszkarpap.dev.android.omsz.omszapp001.BottomActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.MainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.login.LoginMainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.perfusor.PerfusorActivity;

public class MedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MedicationAdapter adapter;
    private ArrayList<Medication> employeeArrayList;
    private Intent intent;
    private FirebaseAuth auth;

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(4).setTitle("Főablak");
        return true;



    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_medication) {
            intent = new Intent(MedActivity.this, MedActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_parameters) {
            intent = new Intent(MedActivity.this, BottomActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_perfusor) {
            intent = new Intent(MedActivity.this, PerfusorActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_settings) {
            intent = new Intent(MedActivity.this, LoginMainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_log_out) {
            intent = new Intent(MedActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}