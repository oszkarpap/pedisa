package hu.oszkarpap.dev.android.omsz.omszapp001.menu_activity.medication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.menu_activity.ParametersActivity;


public class MedActivity extends AppCompatActivity implements MedAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener {

    public static final int REQUEST_CODE = 999;
    public static final String KEY_NAME_MODIFY = "NAME_MODIFY";
    public static final String KEY_AGENT_MODIFY = "AGENT_MODIFY";
    public static final String KEY_PACK_MODIFY = "PACK_MODIFY";
    public static final String KEY_IND_MODIFY = "IND_MODIFY";
    public static final String KEY_CONTRA_MODIFY = "CONTRA_MODIFY";
    public static final String KEY_ADULT_MODIFY = "ADULT_MODIFY";
    public static final String KEY_CHILD_MODIFY = "CHILD_MODIFY";
    private List<Medication> medications;
    private MedAdapter adapter;


    private Medication medi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med);
        overridePendingTransition(0, 0);
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (RuntimeException e) {
            Toast.makeText(this, "A Firebase újratöltődik!", Toast.LENGTH_SHORT).show();
        }


        calc();


        medications = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_med);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new MedAdapter(this, medications, this);
        recyclerView.setAdapter(adapter);


        loadMed();

    }

    private void loadMed() {


        FirebaseDatabase.getInstance().getReference().child("med").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Medication med = dataSnapshot.getValue(Medication.class);
                medications.add(med);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Medication med = dataSnapshot.getValue(Medication.class);
                medications.remove(med);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void saveMed(final Medication med) {

        String key = med.getName() + System.currentTimeMillis();
        med.setKey(key);
        FirebaseDatabase.getInstance().getReference()
                .child("med")
                .child(key)
                .setValue(med)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MedActivity.this, "Firebase felhőbe mentve!!", Toast.LENGTH_SHORT).show();

                    }
                });
        adapter.notifyDataSetChanged();


    }

    private void deleteMed(final Medication med) {

        FirebaseDatabase.getInstance().getReference().child("med").child(med.getKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(MedActivity.this, "Törlés sikeres!", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void deleteAllMed() {
        FirebaseDatabase.getInstance().getReference().child("med").removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(MedActivity.this, "Összes elem törölve!", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_med, menu);
        MenuItem menuItem = menu.findItem(R.id.med_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Keresés gyógyszernév vagy hatóanyag alapján ");
        searchView.setOnQueryTextListener(this);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.removeAllMedication) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Biztos törölni szeretné az elemet?");
            alertDialogBuilder.setPositiveButton("Igen",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(MedActivity.this, "Törölve!", Toast.LENGTH_LONG).show();

                            deleteAllMed();
                            adapter.notifyDataSetChanged();
                        }
                    });

            alertDialogBuilder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        } else if (item.getItemId() == R.id.createMedMenu) {
            Intent intent = new Intent(this, CreateMedActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }else if (item.getItemId() == R.id.med_refresh) {
            finish();
            Intent intent = new Intent(MedActivity.this, MedActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra(CreateMedActivity.KEY_NAME);
                String agent = data.getStringExtra(CreateMedActivity.KEY_AGENT);
                String pack = data.getStringExtra(CreateMedActivity.KEY_PACK);
                String ind = data.getStringExtra(CreateMedActivity.KEY_IND);
                String contra = data.getStringExtra(CreateMedActivity.KEY_CONTRA);
                String adult = data.getStringExtra(CreateMedActivity.KEY_ADULT);
                String child = data.getStringExtra(CreateMedActivity.KEY_CHILD);
                Medication med = new Medication(name, agent, pack, ind, contra, adult, child);
                saveMed(med);


            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemLongClicked(final int position) {
        medi = medications.get(position);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Duplikálni vagy törölni szeretné az elemet?");
        alertDialogBuilder.setPositiveButton("Vissza",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });

        alertDialogBuilder.setNegativeButton("Törlés", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteMed(medi);
            }
        });
        alertDialogBuilder.setNeutralButton("Duplikáció", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MedActivity.this, ModifyMedActivity.class);
                intent.putExtra(KEY_NAME_MODIFY, medi.getName());
                intent.putExtra(KEY_AGENT_MODIFY, medi.getAgent());
                intent.putExtra(KEY_PACK_MODIFY, medi.getPack());
                intent.putExtra(KEY_IND_MODIFY, medi.getInd());
                intent.putExtra(KEY_CONTRA_MODIFY, medi.getCont());
                intent.putExtra(KEY_ADULT_MODIFY, medi.getAdult());
                intent.putExtra(KEY_CHILD_MODIFY, medi.getChild());


                startActivity(intent);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        String MedInput = s.toLowerCase();
        ArrayList<Medication> newList = new ArrayList<>();

        for (Medication medName : medications) {
            if (medName.getName().toLowerCase().contains(MedInput) || medName.getAgent().toLowerCase().contains(MedInput)) {
                newList.add(medName);
            }
        }

        adapter.updateList(newList);
        return true;
    }


    Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, buttonAdd, buttonSub, buttonDivision,
            buttonMul, button10, buttonC, buttonEqual;

    EditText edt1;

    float mValueOne, mValueTwo;

    boolean mAddition, mSubtract, mMultiplication, mDivision;

    public void calc() {
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        buttonAdd = (Button) findViewById(R.id.buttonadd);
        buttonSub = (Button) findViewById(R.id.buttonsub);
        buttonMul = (Button) findViewById(R.id.buttonmul);
        buttonDivision = (Button) findViewById(R.id.buttondiv);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonEqual = (Button) findViewById(R.id.buttoneql);
        edt1 = (EditText) findViewById(R.id.calc_edittext);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText() + "1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText() + "2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText() + "3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText() + "4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText() + "5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText() + "6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText() + "7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText() + "8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText() + "9");
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText() + "0");
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt1 == null) {
                    edt1.setText("");
                } else {
                    mValueOne = Float.parseFloat(edt1.getText() + "");
                    mAddition = true;
                    edt1.setText(null);
                }
            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueOne = Float.parseFloat(edt1.getText() + "");
                mSubtract = true;
                edt1.setText(null);
            }
        });

        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueOne = Float.parseFloat(edt1.getText() + "");
                mMultiplication = true;
                edt1.setText(null);
            }
        });

        buttonDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueOne = Float.parseFloat(edt1.getText() + "");
                mDivision = true;
                edt1.setText(null);
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mValueTwo = Float.parseFloat(edt1.getText() + "");

                    if (mAddition == true) {

                        edt1.setText(mValueOne + mValueTwo + "");
                        mAddition = false;
                    }


                    if (mSubtract == true) {
                        edt1.setText(mValueOne - mValueTwo + "");
                        mSubtract = false;
                    }

                    if (mMultiplication == true) {
                        edt1.setText(mValueOne * mValueTwo + "");
                        mMultiplication = false;
                    }

                    if (mDivision == true) {
                        edt1.setText(mValueOne / mValueTwo + "");
                        mDivision = false;
                    }

                } catch (Exception e) {
                    Toast.makeText(MedActivity.this, "Megfelelő értéket adjon meg!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText("");
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText(edt1.getText() + ".");
            }
        });
    }


}
