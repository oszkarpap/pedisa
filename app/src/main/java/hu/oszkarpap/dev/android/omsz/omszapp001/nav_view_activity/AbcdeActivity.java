package hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;


public class AbcdeActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abcde);
        overridePendingTransition(R.anim.pull_in_left,0);
        collapsingToolbarLayout =  findViewById(R.id.collapsingTollbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_out_left,0);
    }
}
