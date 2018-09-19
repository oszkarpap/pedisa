package hu.oszkarpap.dev.android.omsz.omszapp001.medication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import hu.oszkarpap.dev.android.omsz.omszapp001.MainActivity;

import hu.oszkarpap.dev.android.omsz.omszapp001.ParametersActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.PerfusorActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.login.LoginMainActivity;


public class MedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MedicationAdapter adapter;
    private ArrayList<Medication> MedArrayList;
    private Intent intent;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        MedArrayList = new ArrayList<>();
        MedArrayList.add(new Medication("Adenocor","(adenozin)","6mg/2ml",
                "PSVT, dd SVT/VT ","allergia, II. III. AVB, SSS, asthma bronchiale,COPD",
                "6mg iv. bolus, sz.e. 2 min 12mg, sz.e. ism","0,05-0,3mg/ttkg iv (2ml 6m-re higítva)"));
        MedArrayList.add(new Medication("Atropin","(atropin-szulfát)","1mg/1ml",
                "kQRS bradycardia, alkilfoszfát-mérgezés, ETI:vagusizgalom csökkentése,REA(nem rutinszerűen)","Sürgősségi esetben nincs, egyébként glaucoma, hyperthyreosis",
                "0,5mg - 1 - 3mg, AD:0,05-0,3mg/ttkg iv","0,1-0,5mg (1 ml 10ml higítva)"));
        MedArrayList.add(new Medication("Betaloc","(metoprolol-tartarát)","5mg/5ml",
                " stressz arritmia, HT, angina, kQRS tachycardia","allergia, card. decomp., cor pulmonale, card. shock, COPD, SSS, WPW, terhesség,DM",
                "2,5 mg - 5mg (-10 - 15mg) (max 1mg/min) iv ","0.05 - 0,07 mg/ttkg"));
        MedArrayList.add(new Medication("Cordarone","(amiodaron-hidroklorid)","150mg/3ml",
                "AF, VES, PSVT, VT, VF/pnVT","bradycardia, SA- AV block, pajzsmirigy-betegség, jódérzékenység, súlyos hypotónia, SSS, szoptatás",
                "300mg/20min, ill. ALS alg. szerint","5mg/ttkg ill. ALS alg, szerint"));
        MedArrayList.add(new Medication("Magnesium","(magnesium-szulfát)","1000m/10ml",
                "hypoMg, fenyegető koraszülés, terh. eclampsia, preeclampsia, TdP, VF, VT","bradycardia, AVB, súlyos VE",
                "ALS 2gr, TdP 1-2gr, koraszülés/pre- ill. ecplampsia 4-6gr (500ml glükózban 30 min alatt)","20mg /ttkg"));
        MedArrayList.add(new Medication("Verapamil","(verapamil)","5mg/2ml",
                "PSVT, SVES, AF, PF, kQRS tachycardia, angina, hipertónia, HOCMP","SSS, SZE, card. shock, AV-betegség, WPW, I. trimeszter, beta-blokkoló adása",
                "0,075 - 0,15mg/ttkg (2.5-5mg) iv. 2 min alatt, 30 min ism."," 1 éves korig: 0,1-0,2 mg6ttkg iv, 1-15 év között: 0,1-0,3 mg/ttkg, max 5mg "));
        MedArrayList.add(new Medication("Suprastin","(cloropyraminium-chloratum)","20mg/1ml",
                "allergia, anaphylaxia,urticaria, dermatitis, pruritis, rovarcsípés "," újszülött, koraszülött, akut asthmás roham, I. trimeszter, szoptatás",
                "20-40 mg iv. ","0,5-1mg6ttkg max (2mg/ttkg)(1 év alatt: 1/4 ampulla, 1-6 éves: 1/2 ampulla, 6-14 kor: 1/2-1 ampulla)"));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new MedicationAdapter(MedArrayList);

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
            intent = new Intent(MedActivity.this, ParametersActivity.class);
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