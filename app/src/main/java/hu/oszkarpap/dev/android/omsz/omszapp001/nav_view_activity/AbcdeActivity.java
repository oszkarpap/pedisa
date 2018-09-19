package hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alespero.expandablecardview.ExpandableCardView;

import hu.oszkarpap.dev.android.omsz.omszapp001.ExpandableListAdapter;
import hu.oszkarpap.dev.android.omsz.omszapp001.R;


public class AbcdeActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Button guideline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abcde);
        overridePendingTransition(R.anim.pull_in_left,0);
        collapsingToolbarLayout =  findViewById(R.id.collapsingTollbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        guideline = findViewById(R.id.btn_abcde_guideline);
        guideline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbcdeActivity.this,AbcdeProtokollActivity.class );
                startActivity(intent);
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_out_left,0);


    }
}
