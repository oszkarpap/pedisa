package hu.oszkarpap.dev.android.omsz.omszapp001.right.medication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

public class CheckMedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_med);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -100;
        params.height = 500;
        params.width = 500;
        params.y = -50;

        this.getWindow().setAttributes(params);
    }
}