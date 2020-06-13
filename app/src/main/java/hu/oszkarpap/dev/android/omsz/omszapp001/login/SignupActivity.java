package hu.oszkarpap.dev.android.omsz.omszapp001.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;
import hu.oszkarpap.dev.android.omsz.omszapp001.main.MainActivity;


/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the sign up activity
 */

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputRepassword;
    private Button btnSignUp;
    private Button btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        Button btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputRepassword = findViewById(R.id.repassword);
        progressBar = findViewById(R.id.progressBar);
        btnResetPassword = findViewById(R.id.btn_reset_password);


        clickResetPwActivity();


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clickSignUp();

    }

    /**
     * click sign up button
     */
    public void clickSignUp() {

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
                if (isValidEmailAddress(email)) {
                    Toast.makeText(SignupActivity.this, "Nem e-mail címet adott meg!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.endsWith("@gmail.com")) {
                    inputEmail.setError("csak gmail.com valid!");


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
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "Új felhasználó!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
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


    /**
     * click reset password button
     */
    public void clickResetPwActivity() {


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });
    }


    /**
     * check email validate
     */
    public boolean isValidEmailAddress(String email) {
        String ePattern = getString(R.string.validate_email_string);
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
