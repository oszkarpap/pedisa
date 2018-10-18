package hu.oszkarpap.dev.android.omsz.omszapp001.guide;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

public class LocationActivity extends AppCompatActivity {

    private TextView loc;
    private Button gen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        loc = findViewById(R.id.loc);
        gen = findViewById(R.id.locbtn);
        final Location loc1 = new Location("");
        loc1.getLatitude();
        loc1.getLongitude();

        Location loc2 = new Location("21.0000,19.000");


        float distanceInMeters = loc1.distanceTo(loc2);
        loc.setText(String.valueOf(distanceInMeters));
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc.setText(String.valueOf(loc1));
            }
        });
    }
}
