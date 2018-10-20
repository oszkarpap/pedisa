package hu.oszkarpap.dev.android.omsz.omszapp001.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import hu.oszkarpap.dev.android.omsz.omszapp001.R;


/**
 * @author Oszkar Pap
 * @version 1.0
 * This is the forgot password and send new password to own email
 */

public class ResetPasswordActivity extends SignupActivity {


    private EditText inputEmail;
    private Button btnReset;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputEmail =  findViewById(R.id.email);
        btnReset =  findViewById(R.id.btn_reset_password);

        progressBar =  findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        clickResetPw();

    }

    /**
     * This method send change password
     * */

    public void clickResetPw(){

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), R.string.email_resetpw_a, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isValidEmailAddress(inputEmail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), R.string.thisisnotemail_reserpw_a, Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPasswordActivity.this, R.string.sendind_email_reserpw_a, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetPasswordActivity.this, R.string.unsuccesful_experiment_resetpw_a, Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

}