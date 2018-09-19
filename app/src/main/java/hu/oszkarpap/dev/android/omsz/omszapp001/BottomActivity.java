package hu.oszkarpap.dev.android.omsz.omszapp001;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hu.oszkarpap.dev.android.omsz.omszapp001.bottom_fragment.AdultFragment;
import hu.oszkarpap.dev.android.omsz.omszapp001.bottom_fragment.BabyFragment;
import hu.oszkarpap.dev.android.omsz.omszapp001.bottom_fragment.ChildFragment;
import hu.oszkarpap.dev.android.omsz.omszapp001.bottom_fragment.HomeFragment;
import hu.oszkarpap.dev.android.omsz.omszapp001.login.LoginMainActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.medication.MedActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.perfusor.PerfusorActivity;

//implement the interface OnNavigationItemSelectedListener in your activity class
public class BottomActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Intent intent;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();

        //loading the default fragment
        loadFragment(new HomeFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {


            case R.id.bottom_adult:
                fragment = new AdultFragment();
                break;

            case R.id.bottom_child:
                fragment = new ChildFragment();
                break;

            case R.id.bottom_baby:
                fragment = new BabyFragment();
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
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
            intent = new Intent(BottomActivity.this, MedActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_parameters) {
            intent = new Intent(BottomActivity.this, BottomActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_perfusor) {
            intent = new Intent(BottomActivity.this, PerfusorActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_settings) {
            intent = new Intent(BottomActivity.this, LoginMainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_log_out) {
            auth.signOut();
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

}