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
import com.google.firebase.database.DatabaseReference;

public class SignUpActivity extends DrawerNavigation implements View.OnClickListener {
    private EditText name, email, confirmEmail, password, confirmPassword;
    private Button signUp, ret;
    private String n, e, ce, p, cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle(R.string.Sign_up);
        connectDisplay();
        setListeners();
    }

    protected void connectDisplay() {
        //Connect to the display
        signUp = findViewById(R.id.button_signup);
        ret = findViewById(R.id.button_return);
        name = findViewById(R.id.editText_name);
        email = findViewById(R.id.editText_e);
        confirmEmail = findViewById(R.id.editText_confirmemail);
        password = findViewById(R.id.editText_p);
        confirmPassword = findViewById(R.id.editText_confirmpassword);
    }

    protected void setListeners() {
        //Add the click listeners
        signUp.setOnClickListener(this);
        ret.setOnClickListener(this);
    }

    protected void getFromScreen() {
        n = name.getText().toString();
        e = email.getText().toString();
        ce = confirmEmail.getText().toString();
        p = password.getText().toString();
        cp = confirmPassword.getText().toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_signup:
                getFromScreen();
                if (!e.equals(ce)) {
                    sendToast("Both E-Mail Addresses need to match.");
                    break;
                } else if (!p.equals(cp)) {
                    sendToast("Both Passwords need to match.");
                    break;
                } else if (p.equals("") || cp.equals("") || e.equals("") || cp.equals("") || n.equals("")) {
                    sendToast("All field need to be completed.");
                    break;
                } else if (p.length() < 6) {
                    sendToast("Password needs to be 6 characters or longer.");
                    break;
                } else {
                    getAuth().createUserWithEmailAndPassword(e, p).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                sendToast("Sign Up Failed.");
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
        DatabaseReference mFirebaseDatabase = getDatabase().getReference("users");
        String userId = null;
        if (getAuth().getCurrentUser() != null) {
            userId = getAuth().getCurrentUser().getUid();
        }
        User user = new User(name);
        mFirebaseDatabase.child(userId).setValue(user);
    }
}
