package hu.oszkarpap.dev.android.omsz.omszapp001.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MainActivity;


public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputRepassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    /*created by
     * Oszkar Pap
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputRepassword = (EditText) findViewById(R.id.repassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Kérem olyan e-mail címet adjon meg, melyet használ, hogy későbbiekben megkapja az applikáció frissített verzióját!");
        alertDialogBuilder.setNegativeButton("Rendben", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        inputEmail.setError("csak gmail.com valid!");

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();


                String password = inputPassword.getText().toString().trim();
                String repassword = inputRepassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "E-mail cím!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.endsWith("@gmail.com")) {
                    Toast.makeText(getApplicationContext(), "Gmail-es e-mail címet írjon!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
                    Toast.makeText(getApplicationContext(), "Jelszó!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6 || repassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), "legalább 6 karakter legyen!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(repassword)) {
                    Toast.makeText(getApplicationContext(), "Nem egyezik a jelszó!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "Új felhasználó!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Sikertelen!" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
