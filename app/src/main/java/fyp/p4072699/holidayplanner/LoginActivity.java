package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login, signup, forgot;
    private String email, password;
    private EditText em, pa;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.button_login);
        signup = findViewById(R.id.button_signup);
        em = (EditText) findViewById(R.id.editText_e);
        pa = (EditText) findViewById(R.id.editText_p);
        forgot = findViewById(R.id.button_forgot);
        auth = FirebaseAuth.getInstance();

        forgot.setOnClickListener(this);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        email = em.getText().toString();
        password = pa.getText().toString();
        switch (view.getId()) {
            case R.id.button_login:
                if (email.equals("")) {
                    Toast.makeText(this, "Please enter an email.", Toast.LENGTH_SHORT).show();
                    break;
                } else if (password.equals("")) {
                    Toast.makeText(this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                if (password.length() < 6) {
                                    Toast.makeText(LoginActivity.this, "Password is too short.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                        }
                    });
                    break;
                }
            case R.id.button_signup:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
            case R.id.button_forgot:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;
        }
    }
}
