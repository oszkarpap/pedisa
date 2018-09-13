package hu.oszkarpap.dev.android.omsz.omszapp001.nav_view_activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import hu.oszkarpap.dev.android.omsz.omszapp001.Feed;
import hu.oszkarpap.dev.android.omsz.omszapp001.HeadingView;
import hu.oszkarpap.dev.android.omsz.omszapp001.Info;
import hu.oszkarpap.dev.android.omsz.omszapp001.InfoView;
import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.Utils;

public class Acticity02
        extends AppCompatActivity {

    private ExpandablePlaceHolderView mExpandableView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acticity02);
        mContext = this.getApplicationContext();
        mExpandableView = (ExpandablePlaceHolderView)findViewById(R.id.expandableView);
        for(Feed feed : Utils.loadFeeds(this.getApplicationContext())){
            mExpandableView.addView(new HeadingView(mContext, feed.getHeading()));
            for(Info info : feed.getInfoList()){
                mExpandableView.addView(new InfoView(mContext, info));
            }
        }
    }
}
