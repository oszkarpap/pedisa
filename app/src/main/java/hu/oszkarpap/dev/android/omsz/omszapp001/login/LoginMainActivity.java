package hu.oszkarpap.dev.android.omsz.omszapp001.login;


import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;


/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the main login Activity
 */
public class LoginMainActivity extends SignupActivity {

    private Button ok, newPassword, newEmail, remove;
    private EditText editText;
    private TextView textView;
    private ImageView imageView;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        textView = findViewById(R.id.loginMainTextview);
        imageView = findViewById(R.id.loginMainImageView);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd, HH:mm");
        Date resultdate = new Date(user.getMetadata().getCreationTimestamp());
        textView.setText("Felhasználói email: " + user.getEmail() +
                ";\n Android ID: " + Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID) + ";\n Profil létrehozása: " +
                sdf.format(resultdate));

        //remove = findViewById(R.id.login_main_remove_user_button);
        //newPassword = findViewById(R.id.login_main_change_password_button);
        //newEmail = findViewById(R.id.login_main_change_email_button);
        //editText = findViewById(R.id.main_login_edittext);
        //ok = findViewById(R.id.main_login_ok);

        //editText.setVisibility(View.INVISIBLE);
        //ok.setVisibility(View.INVISIBLE);




    }

    /**
     * Delete own user porfil
     * */

    public void clickRemove(){
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginMainActivity.this);
                alertDialogBuilder.setMessage("Biztos törli a profilt?");
                alertDialogBuilder.setPositiveButton("Igen",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                                assert user != null;
                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginMainActivity.this, "Sikeres profiltörlés!", Toast.LENGTH_LONG).show();

                                            finish();
                                            android.os.Process.killProcess(android.os.Process.myPid());
                                            LoginMainActivity.super.onDestroy();

                                            System.exit(0);
                                        } else {
                                            Toast.makeText(LoginMainActivity.this, "Nem sikerült a profiltörlés", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });

                            }
                        });

                alertDialogBuilder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(LoginMainActivity.this, "Nem törölte a profilját!", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    /**
     * Change the own email
     * */

    public void clickNewEmail(){
        newEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
                ok.setVisibility(View.VISIBLE);
                editText.setHint("új e-mail cím megadása");
                editText.setText(null);
                ok.setText(R.string.send_new_mail_main_login_a);
                ok.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (isValidEmailAddress(editText.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Nem e-mail címet adott meg!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!(user == null) && editText.getText().toString().contains("@gmail.com")) {
                            user.updateEmail(editText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginMainActivity.this, "Új email cím beállítva", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginMainActivity.this, "Nem sikerült az e-mail csere", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        } else {
                            Toast.makeText(LoginMainActivity.this, "gmail-es e-mail címet adjon!", Toast.LENGTH_SHORT).show();
                        }

                    }

                });

            }
        });
    }

    /**
     * Set new password in own profile
     * */

    public void clickNewPw(){

        newPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
                editText.setHint("Új jelszó megadása");
                editText.setText(null);

                ok.setVisibility(View.VISIBLE);
                ok.setText(R.string.send_new_pw_login_main_a);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if ((editText.getText().toString().trim().length() > 5)) {

                            assert user != null;
                            user.updatePassword(editText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                        Toast.makeText(LoginMainActivity.this, "Sikeres jelszócsere!", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginMainActivity.this, "Nem sikerült a jelszócsere", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(LoginMainActivity.this, "Legalább 6 karakter legyen a jelszó!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}


