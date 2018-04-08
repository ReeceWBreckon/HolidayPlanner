package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends DrawerNavigation implements View.OnClickListener {
    private Button login, signup, forgot;
    private String email, password;
    private EditText em, pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.Login);
        connectDisplay();
        setListeners();
    }

    protected void connectDisplay() {
        //Connect to the display
        login = findViewById(R.id.button_login);
        signup = findViewById(R.id.button_signup);
        em = (EditText) findViewById(R.id.editText_e);
        pa = (EditText) findViewById(R.id.editText_p);
        forgot = findViewById(R.id.button_forgot);
    }

    protected void setListeners() {
        //Set the click listeners
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
                    sendToast("Please enter an email.");
                    break;
                } else if (password.equals("")) {
                    sendToast("Please enter a password.");
                    break;
                } else {
                    getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                if (password.length() < 6) {
                                    sendToast("Password is too short.");
                                } else {
                                    sendToast("Authentication Failed.");
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
