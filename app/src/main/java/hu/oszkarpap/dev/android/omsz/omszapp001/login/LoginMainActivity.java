package hu.oszkarpap.dev.android.omsz.omszapp001.login;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;


/*created by
 * Oszkar Pap
 * */

public class LoginMainActivity extends AppCompatActivity {

    private Button remove, newEmail, newPassword, ok;
    private EditText editText;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);



        auth = FirebaseAuth.getInstance();


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




        remove = findViewById(R.id.login_main_remove_user_button);
        newPassword = findViewById(R.id.login_main_change_password_button);
        newEmail = findViewById(R.id.login_main_change_email_button);
        progressBar = findViewById(R.id.main_login_progressbar);
        editText = findViewById(R.id.main_login_edittext);
        ok = findViewById(R.id.main_login_ok);
        editText.setVisibility(View.INVISIBLE);
        ok.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginMainActivity.this);
                alertDialogBuilder.setMessage("Biztos törli a profilt?");
                alertDialogBuilder.setPositiveButton("Igen",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginMainActivity.this, "Sikeres profiltörlés!", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.INVISIBLE);

                                            finish();
                                            android.os.Process.killProcess(android.os.Process.myPid());
                                            LoginMainActivity.super.onDestroy();

                                            System.exit(0);
                                        } else {
                                            Toast.makeText(LoginMainActivity.this, "Nem sikerült a profiltörlés", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.INVISIBLE);

                                        }
                                    }
                                });

                            }
                        });

                alertDialogBuilder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(LoginMainActivity.this, "Nem törölte a profilját!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        newEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
                ok.setVisibility(View.VISIBLE);
                editText.setHint("új e-mail cím megadása");
                editText.setText(null);
                ok.setText("Új E-mail Küldés");
                ok.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        progressBar.setVisibility(View.VISIBLE);

                        if (!(user == null) && editText.getText().toString().contains("@gmail.com")) {
                            user.updateEmail(editText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginMainActivity.this, "Új email cím beállítva", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                    } else {
                                        Toast.makeText(LoginMainActivity.this, "Nem sikerült az e-mail csere", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });


                        } else {
                            Toast.makeText(LoginMainActivity.this, "gmail-es e-mail címet adjon!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }

                });

            }
        });


        newPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
                editText.setHint("Új jelszó megadása");
                editText.setText(null);

                ok.setVisibility(View.VISIBLE);
                ok.setText("Új jelszó Küldés");
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);

                        if ((editText.getText().toString().trim().length() > 5)) {

                            user.updatePassword(editText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(LoginMainActivity.this, "Sikeres jelszócsere!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginMainActivity.this, "Nem sikerült a jelszócsere", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(LoginMainActivity.this, "Legalább 6 karakter legyen a jelszó!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });
            }
        });

    }
}


