package hu.oszkarpap.dev.android.omsz.omszapp001.left;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the prehospital examination guideline
 */

public class AbcdeActivity extends AppCompatActivity {

    /**
     * Here is not still implemented the full picture of protocol Activity, therefore this intent shows to itself
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abcde);
        createActivity();
        Button guideline = findViewById(R.id.btn_abcde_guideline);
        guideline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbcdeActivity.this, AbcdeActivity.class);
                startActivity(intent);
            }
        });

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
}
