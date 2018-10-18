package hu.oszkarpap.dev.android.omsz.omszapp001.navigation;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import hu.oszkarpap.dev.android.omsz.omszapp001.R;

public class AbcdeActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Button guideline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abcde);

        createActivity();
        guideline = findViewById(R.id.btn_abcde_guideline);
        guideline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbcdeActivity.this,AbcdeActivity.class );
                startActivity(intent);
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();



    }

    public void createActivity(){
        overridePendingTransition(R.anim.pull_in_left, R.anim.fade_out);
        collapsingToolbarLayout =  findViewById(R.id.collapsingTollbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));

    }
}
