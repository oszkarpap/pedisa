package hu.oszkarpap.dev.android.omsz.omszapp001.left;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;


import hu.oszkarpap.dev.android.omsz.omszapp001.R;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the prehospital examination guideline
 */

public class AbcdeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abcde);
        createActivity();

    }

    /**
     * change the open and close animation
     * change collapsed and expanded titlecolor
     */


    public void createActivity() {
        overridePendingTransition(R.anim.pull_in_left, R.anim.fade_out);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingTollbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
