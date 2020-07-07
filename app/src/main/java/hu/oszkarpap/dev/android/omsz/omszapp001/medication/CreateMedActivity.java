package hu.oszkarpap.dev.android.omsz.omszapp001.medication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.CreateGLActivity;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.SOPActivity;
//import hu.oszkarpap.dev.android.omsz.omszapp001.right.memory.MemoryActivity;

/**
 * @author Oszkar Pap
 * @version 1.0
 * This is created medication activity
 * This Activity used by create Med action, modify Med action, create own Med action, modify own Med action
 */

public class CreateMedActivity extends AppCompatActivity {

    public static final String KEY_NAME = "NAME";
    public static final String KEY_MATER = "MATER";
    public static final String KEY_STORE = "STORE";
    public static final String KEY_AGENT = "AGENT";
    public static final String KEY_PACK = "PACK";
    public static final String KEY_IND = "IND";
    public static final String KEY_CONTRA = "CONTRA";
    public static final String KEY_ADULT = "ADULT";
    public static final String KEY_CHILD = "CHILD";
    public static final String KEY_CHILD01 = "CHILD01";
    public static final String KEY_CHILD02 = "CHILD02";
    public static final String KEY_CHILDUNIT = "CHILDUNIT";
    public static final String KEY_CHILDMAX = "CHILDMAX";
    public static final String KEY_CHILDMAX02 = "CHILDMAX02";
    public static final String KEY_CHILDDESC = "CHILDDESC";
    private EditText createName, createAgent, createPack, createInd, createContra, createAdult, createChild,
            createChD01, createChD02, createChU, createChMaxD, createChMaxD02, createChDesciption, createMater, createStore;
    private TextView createChildParameters;
    private Button createMemoryBTN, deleteBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_medication);
        createName = findViewById(R.id.createNameMedET);
        deleteBTN = findViewById(R.id.createMedDeleteBTN);
        createName.setError(getString(R.string.create_medication_name_alert), null);
        createAgent = findViewById(R.id.createAgentMedET);
        createPack = findViewById(R.id.createPackMedET);
        createInd = findViewById(R.id.createIndMedET);
        createContra = findViewById(R.id.createContraMedET);
        createAdult = findViewById(R.id.createAdultMedET);
        createChild = findViewById(R.id.createChildMedET);
        createChD01 = findViewById(R.id.createChildMedETDose01);
        createChD02 = findViewById(R.id.createChildMedETDose02);
        createChU = findViewById(R.id.createChildMedETUnit);
        createChMaxD = findViewById(R.id.createChildMedETDoseMax);
        createChMaxD02 = findViewById(R.id.createChildMedETDoseMax02);
        createChDesciption = findViewById(R.id.createChildMedETDoseDesc);
        createChildParameters = findViewById(R.id.createMedActivityPrametersTV);
        createStore = findViewById(R.id.createStoreMedET);
        createMater = findViewById(R.id.createMaterMedET);
        createMemoryBTN = findViewById(R.id.createMedBTN);

        setDifferentLayout();

        setEdittextModify();

        clickCreateButton();

        deleteBTN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY)==null){
                    Toast.makeText(CreateMedActivity.this, "Nincs kiválaszott elem!", Toast.LENGTH_SHORT).show();
                }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateMedActivity.this);
                alertDialogBuilder.setMessage("Biztos az elem törlésében?");
                alertDialogBuilder.setPositiveButton("Vissza",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                            }
                        });

                alertDialogBuilder.setNegativeButton("Törlés", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("med").
                                child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY)).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                Toast.makeText(CreateMedActivity.this, "Törlés sikeres!", Toast.LENGTH_SHORT).show();
                                Toast.makeText(CreateMedActivity.this, "Kérem frissítse az oldalt!", Toast.LENGTH_SHORT).show();

                            }
                        });
                        finish();

                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();}
                return false;
            }
        });

        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateMedActivity.this, "Törléshez nyomja hosszan a gombot!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * this method set layout from omsz medication activity and from own medication activity
     */
    public void setDifferentLayout() {
        /**   if ((getIntent().getStringExtra(MemoryActivity.KEY_MEMORY).equals("YES"))) {
         createChD01.setVisibility(View.INVISIBLE);
         createChD02.setVisibility(View.INVISIBLE);
         createChU.setVisibility(View.INVISIBLE);
         createChMaxD.setVisibility(View.INVISIBLE);
         createChMaxD02.setVisibility(View.INVISIBLE);
         createChDesciption.setVisibility(View.INVISIBLE);
         createChildParameters.setVisibility(View.INVISIBLE);
         setTitle("Saját Gyógyszerjegyzet");
         } else {*/
        createChD01.setVisibility(View.VISIBLE);
        createChD02.setVisibility(View.VISIBLE);
        createChU.setVisibility(View.VISIBLE);
        createChMaxD.setVisibility(View.VISIBLE);
        createChMaxD02.setVisibility(View.VISIBLE);
        createChDesciption.setVisibility(View.VISIBLE);
        createChildParameters.setVisibility(View.VISIBLE);

    }

    /**
     * this method set EditText, if i can modify Medication parameters
     */
    public void setEdittextModify() {
        if (!(getIntent().getStringExtra(MedActivity.KEY_NAME_MODIFY) == null)) {
            createName.setText(getIntent().getStringExtra(MedActivity.KEY_NAME_MODIFY));
            createMater.setText(getIntent().getStringExtra(MedActivity.KEY_MATER_MODIFY));
            createStore.setText(getIntent().getStringExtra(MedActivity.KEY_STORE_MODIFY));
            createAgent.setText(getIntent().getStringExtra(MedActivity.KEY_AGENT_MODIFY));
            createPack.setText(getIntent().getStringExtra(MedActivity.KEY_PACK_MODIFY));
            createInd.setText(getIntent().getStringExtra(MedActivity.KEY_IND_MODIFY));
            createContra.setText(getIntent().getStringExtra(MedActivity.KEY_CONTRA_MODIFY));
            createAdult.setText(getIntent().getStringExtra(MedActivity.KEY_ADULT_MODIFY));
            createChild.setText(getIntent().getStringExtra(MedActivity.KEY_CHILD_MODIFY));
            createChU.setText(getIntent().getStringExtra(MedActivity.KEY_UNIT_MODIFY));
            createChD01.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDD01_MODIFY));
            createChD02.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDD02_MODIFY));
            createChMaxD.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDDMAX_MODIFY));
            createChMaxD02.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDDMAX02_MODIFY));
            createChDesciption.setText(getIntent().getStringExtra(MedActivity.KEY_CHILDDDESC_MODIFY));
        }

    }

    /**
     * this is the click create button method
     */
    public void clickCreateButton() {
        createMemoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp = createName.getText().toString();
                if (temp.matches("")) {
                    Toast.makeText(CreateMedActivity.this, "Gyógyszernév megadása kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {

                    if (!(getIntent().getStringExtra(MedActivity.KEY_NAME_MODIFY) == null)) {
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("name").setValue(createName.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("mater").setValue(createMater.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("store").setValue(createStore.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("ind").setValue(createInd.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("agent").setValue(createAgent.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("adult").setValue(createAdult.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("child").setValue(createChild.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("child01").setValue(createChD01.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("child02").setValue(createChD02.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("childDDesc").setValue(createChDesciption.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("childDMax").setValue(createChMaxD.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("childDMax02").setValue(createChMaxD02.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("cont").setValue(createContra.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("int").setValue(createInd.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("pack").setValue(createPack.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("med")
                                .child(getIntent().getStringExtra(MedActivity.KEY_KEY_MODIFY))
                                .child("unit").setValue(createChU.getText().toString());
                        Toast.makeText(CreateMedActivity.this, "Frissítve!", Toast.LENGTH_SHORT).show();
                    } else {
                        String name = createName.getText().toString();
                        String mater = createMater.getText().toString();
                        String store = createStore.getText().toString();
                        String agent = createAgent.getText().toString();
                        String pack = createPack.getText().toString();
                        String ind = createInd.getText().toString();
                        String contra = createContra.getText().toString();
                        String adult = createAdult.getText().toString();
                        String child = createChild.getText().toString();
                        String unit = createChU.getText().toString();
                        String dose01 = createChD01.getText().toString();
                        String dose02 = createChD02.getText().toString();
                        String doseMax = createChMaxD.getText().toString();
                        String doseMax02 = createChMaxD02.getText().toString();
                        String doseDesc = createChDesciption.getText().toString();


                        Intent intent = new Intent();

                        intent.putExtra(KEY_NAME, name);
                        intent.putExtra(KEY_AGENT, agent);
                        intent.putExtra(KEY_PACK, pack);
                        intent.putExtra(KEY_IND, ind);
                        intent.putExtra(KEY_CONTRA, contra);
                        intent.putExtra(KEY_ADULT, adult);
                        intent.putExtra(KEY_CHILD, child);
                        intent.putExtra(KEY_CHILDUNIT, unit);
                        intent.putExtra(KEY_CHILD01, dose01);
                        intent.putExtra(KEY_CHILD02, dose02);
                        intent.putExtra(KEY_CHILDMAX, doseMax);
                        intent.putExtra(KEY_CHILDMAX02, doseMax02);
                        intent.putExtra(KEY_CHILDDESC, doseDesc);
                        intent.putExtra(KEY_MATER, mater);
                        intent.putExtra(KEY_STORE, store);

                        setResult(RESULT_OK, intent);

                    }
                    finish();
                }
            }
        });

    }
}
