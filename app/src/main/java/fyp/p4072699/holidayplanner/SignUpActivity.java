package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name, email, confirmEmail, password, confirmPassword;
    private Button signUp, ret;
    private String n, e, ce, p, cp;
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle(R.string.Sign_up);

        //Connect to the display
        signUp = findViewById(R.id.button_signup);
        ret = findViewById(R.id.button_return);
        name = findViewById(R.id.editText_name);
        email = findViewById(R.id.editText_e);
        confirmEmail = findViewById(R.id.editText_confirmemail);
        password = findViewById(R.id.editText_p);
        confirmPassword = findViewById(R.id.editText_confirmpassword);

        //Firebase setup
        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        //Add the click listeners
        signUp.setOnClickListener(this);
        ret.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_signup:
                n = name.getText().toString();
                e = email.getText().toString();
                ce = confirmEmail.getText().toString();
                p = password.getText().toString();
                cp = confirmPassword.getText().toString();
                if (!e.equals(ce)) {
                    Toast.makeText(getApplicationContext(), "Both E-Mail Addresses need to match.", Toast.LENGTH_SHORT).show();
                    break;
                } else if (!p.equals(cp)) {
                    Toast.makeText(getApplicationContext(), "Both Passwords need to match.", Toast.LENGTH_SHORT).show();
                    break;
                } else if (p.equals("") || cp.equals("") || e.equals("") || cp.equals("") || n.equals("")) {
                    Toast.makeText(getApplicationContext(), "All field need to be completed.", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Sign Up Failed." + task.getException(), Toast.LENGTH_SHORT).show();
                            } else {
                                createUser(n);
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                            }
                        }
                    });
                    break;
                }
            case R.id.button_return:
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                break;
        }
    }

    private void createUser(String name) {
        String userId = null;
        if (auth.getCurrentUser() != null) {
            userId = auth.getCurrentUser().getUid();
        }
        User user = new User(name);
        mFirebaseDatabase.child(userId).setValue(user);
    }
}
