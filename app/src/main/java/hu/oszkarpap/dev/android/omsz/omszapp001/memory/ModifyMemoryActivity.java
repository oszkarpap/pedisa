package hu.oszkarpap.dev.android.omsz.omszapp001.memory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.memory.model.Memory;

public class ModifyMemoryActivity extends MemoryActivity {

    public static final String KEY_NAME = "NAME";
    public static final String KEY_AGENT = "AGENT";
    public static final String KEY_PACK = "PACK";
    public static final String KEY_IND = "IND";
    public static final String KEY_CONTRA = "CONTRA";
    public static final String KEY_ADULT = "ADULT";
    public static final String KEY_CHILD = "CHILD";
    private EditText createName, createAgent, createPack, createInd, createContra, createAdult, createChild;
    private Button createMemoryBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_memory);
        createName = findViewById(R.id.createNameET);
        createAgent = findViewById(R.id.createAgentET);
        createPack = findViewById(R.id.createPackET);
        createInd = findViewById(R.id.createIndET);
        createContra = findViewById(R.id.createContraET);
        createAdult = findViewById(R.id.createAdultET);
        createChild = findViewById(R.id.createChildET);
        createMemoryBTN = findViewById(R.id.createMemoryBTN);

        createName.setText(getIntent().getStringExtra(MemoryActivity.KEY_NAME_MODIFY_MEMORY));
        createAgent.setText(getIntent().getStringExtra(MemoryActivity.KEY_AGENT_MODIFY_MEMORY));
        createPack.setText(getIntent().getStringExtra(MemoryActivity.KEY_PACK_MODIFY_MEMORY));
        createInd.setText(getIntent().getStringExtra(MemoryActivity.KEY_IND_MODIFY_MEMORY));
        createContra.setText(getIntent().getStringExtra(MemoryActivity.KEY_CONTRA_MODIFY_MEMORY));
        createAdult.setText(getIntent().getStringExtra(MemoryActivity.KEY_ADULT_MODIFY_MEMORY));
        createChild.setText(getIntent().getStringExtra(MemoryActivity.KEY_CHILD_MODIFY_MEMORY));




        createMemoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = createName.getText().toString();
                String agent = createAgent.getText().toString();
                String pack = createPack.getText().toString();
                String ind = createInd.getText().toString();
                String contra = createContra.getText().toString();
                String adult = createAdult.getText().toString();
                String child = createChild.getText().toString();
                Memory memory = new Memory(name,agent,pack,ind,contra,adult,child);
                saveMemoriesAsync(memory);



            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
