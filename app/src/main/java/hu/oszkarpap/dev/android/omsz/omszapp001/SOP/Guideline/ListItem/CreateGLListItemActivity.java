package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity;

import static com.google.firebase.auth.FirebaseAuth.getInstance;
import static hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GLActivity.KEY_GL_ASC_MODIFY;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This is created sop activity
 * This Activity used by create SOP ... action, modify Med action, create own Med action, modify own Med action
 */

public class CreateGLListItemActivity extends AppCompatActivity {


    public static final String KEY_ASC = "ASC";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_ATTR = "ATTR";

    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseAuth auth;
    private EditText createName, createNumber;
    private CheckBox  underline, colored;
    private Button createMemoryBTN, deleteBTN;
    private String asc, title, color = "FFFFFF", color2 = "FFFFFF";
    private ColorPickerView colorPickerView;
    private String attr;
    private int update = 0;
    private String second, x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        setContentView(R.layout.activity_create_glli);
        deleteBTN = findViewById(R.id.deleteGlliBTN);
        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateGLListItemActivity.this);
                alertDialogBuilder.setMessage("Biztos törölni szeretné az elemet? ");
                alertDialogBuilder.setPositiveButton("Vissza",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                            }
                        });

                alertDialogBuilder.setNegativeButton("Törlés", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try{FirebaseDatabase.getInstance().getReference().child("glli").child(getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_PARENT))
                        .child(getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_SEC_KEY)).
                                removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                Toast.makeText(CreateGLListItemActivity.this, "Törlés sikeres!", Toast.LENGTH_SHORT).show();
                                finish();
                                //Intent intent = new Intent(CreateGLListItemActivity.this, GLActivity.class);
                                //startActivity(intent);
                            }
                        }); }catch (Exception e){
                            Toast.makeText(CreateGLListItemActivity.this, "Nincs választott elem!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        createName = findViewById(R.id.createNameGlliET);
        createName.setError(getString(R.string.create_medication_name_alert), null);
        createMemoryBTN = findViewById(R.id.createGlliBTN);
        createNumber = findViewById(R.id.createNumberGlliET);

        underline = findViewById(R.id.CreateGlliunderline);
        colored = findViewById(R.id.CreateGlliColor);
        underline.setChecked(false);


        colorPickerView = findViewById(R.id.CreateGlcolorPickerViewli);
        colorPickerView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                LinearLayout linearLayout = findViewById(R.id.LinearLayoutColorPickerViewli);
                linearLayout.setBackgroundColor(colorEnvelope.getColor());
                color = colorEnvelope.getColorHtml();
                //Toast.makeText(CreateGLActivity.this, ""+colorEnvelope.getColorHtml(), Toast.LENGTH_SHORT).show();
            }
        });




        asc = getIntent().getStringExtra(KEY_GL_ASC_MODIFY);
        title = getIntent().getStringExtra(GLActivity.KEY_GL_TITLE_MODIFY);
        //Toast.makeText(this, ""+asc, Toast.LENGTH_SHORT).show();
        setTitle("Protokoll részlet listája");


        if (!(getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_NAME) == null)) {
            update = 1;
            createName.setText(getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_NAME));
            createNumber.setText(getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_COUNT));
            // Toast.makeText(this, "" + update, Toast.LENGTH_SHORT).show();
        }


        clickCreateButton();

    }


    /**
     * this method set EditText, if i can modify Medication parameters
     */


    /**
     * this is the click create button method
     */
    public void clickCreateButton() {
        createMemoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                generateAttr();

                Intent intent = new Intent();
                //Toast.makeText(CreateGLActivity.this, ""+attr, Toast.LENGTH_SHORT).show();
                intent.putExtra(KEY_ASC, asc);
                intent.putExtra(KEY_TITLE, title);
                intent.putExtra(KEY_ATTR, attr);
                GuideLineListItem guideLineListItem = new GuideLineListItem();
                guideLineListItem.setItem(createName.getText().toString());
                guideLineListItem.setKey(asc);
                guideLineListItem.setAttributum(attr);
                try {
                    guideLineListItem.setCount(Integer.parseInt(createNumber.getText().toString()));
                } catch (Exception e) {

                }
                try {
                    guideLineListItem.setParent(asc);
                } catch (Exception e) {

                }
                saveGLLI(guideLineListItem);
                setResult(RESULT_OK, intent);
                //Toast.makeText(CreateGLActivity.this, ""+attr, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void generateAttr() {
        attr = "f";

            attr += "0";

            attr += "0";
        if (underline.isChecked()) {
            attr += "1";
        } else {
            attr += "0";
        }
        attr += "s";

        attr += "X";
        if (colored.isChecked()) {
            attr += 1;
        } else {
            attr += 0;
        }
        attr += color;
        attr += "Y";

        attr += color2;
        attr += "W";


    }


    public void saveGLLI(final GuideLineListItem guideLineListItem) {

        try {
            String key;
             second = "unkonwn";
            if (update == 0) {
                second = getIntent().getStringExtra(GLActivity.KEY_GL_ASC_MODIFY);
            } else if (update == 1) {
                second = getIntent().getStringExtra(GuideLineListItemAdapter.LIST_ITEM_PARENT);
                guideLineListItem.setParent(second);
                guideLineListItem.setKey(second);
            }

            key = guideLineListItem.getKey();
             x = key + System.currentTimeMillis();
             guideLineListItem.setSecondKey(x);

            guideLineListItem.setKey(key);
            // guideLineListItem.setAsc(x);
            FirebaseDatabase.getInstance().getReference()
                    .child("glli")
                    .child(second).child(x)
                    .setValue(guideLineListItem)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(CreateGLListItemActivity.this, "Firebase felhőbe mentve!", Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, R.string.create_medication_name_alert, Toast.LENGTH_LONG).show();
        }


    }
}



