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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name, email, confirmEmail, password, confirmPassword;
    private Button signUp, ret;
    private String n, e, ce, p, cp;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.button_signup);
        ret = findViewById(R.id.button_return);
        name = findViewById(R.id.editText_name);
        email = findViewById(R.id.editText_e);
        confirmEmail = findViewById(R.id.editText_confirmemail);
        password = findViewById(R.id.editText_p);
        confirmPassword = findViewById(R.id.editText_confirmpassword);
        auth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(this);
        ret.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        n = name.getText().toString();
        e = email.getText().toString();
        ce = confirmEmail.getText().toString();
        p = password.getText().toString();
        cp = confirmPassword.getText().toString();

        switch (view.getId()) {
            case R.id.button_signup:
                if (!e.equals(ce)) {
                    Toast.makeText(getApplicationContext(), "Both E-Mail Addresses need to match." , Toast.LENGTH_SHORT).show();
                    break;
                } else if (!p.equals(cp)) {
                    Toast.makeText(getApplicationContext(), "Both Passwords need to match." , Toast.LENGTH_SHORT).show();
                    break;
                } else if (p.equals("") || cp.equals("") || e.equals("") || cp.equals("") || n.equals("")) {
                    Toast.makeText(getApplicationContext(), "All field need to be completed." , Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Sign Up Failed." + task.getException(), Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                            }
                        }
                    });
                }
            case R.id.button_return:
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                break;
        }
    }
}
