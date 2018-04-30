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

public class SignUpActivity extends NavController implements View.OnClickListener {
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

    //Connect to the display
    protected void connectDisplay() {
        signUp = findViewById(R.id.button_signup);
        ret = findViewById(R.id.button_return);
        name = findViewById(R.id.editText_name);
        email = findViewById(R.id.editText_e);
        confirmEmail = findViewById(R.id.editText_confirmemail);
        password = findViewById(R.id.editText_p);
        confirmPassword = findViewById(R.id.editText_confirmpassword);
    }

    //Add the click listeners
    protected void setListeners() {
        signUp.setOnClickListener(this);
        ret.setOnClickListener(this);
    }

    //Get the details from the previous screen
    protected void getFromScreen() {
        n = name.getText().toString();
        e = email.getText().toString();
        ce = confirmEmail.getText().toString();
        p = password.getText().toString();
        cp = confirmPassword.getText().toString();
    }

    //Check which button was pressed
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_signup:
                getFromScreen();
                //Check that the details are acceptable
                if (!e.equals(ce)) {
                    sendToast(getString(R.string.both_email));
                    break;
                } else if (!p.equals(cp)) {
                    sendToast(getString(R.string.both_passwords));
                    break;
                } else if (p.equals("") || cp.equals("") || e.equals("") || cp.equals("") || n.equals("")) {
                    sendToast(getString(R.string.all_fields));
                    break;
                } else if (p.length() < 6) {
                    sendToast(getString(R.string.password_length));
                    break;
                } else {
                    checkSignUp();
                    break;
                }
            case R.id.button_return:
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                break;
        }
    }

    //Check that the details were passed to firebase successfully
    protected void checkSignUp() {
        getAuth().createUserWithEmailAndPassword(e, p).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    sendToast(getString(R.string.signup_failed));
                } else {
                    createUser(n);
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }

    //Add the users name to the firebase database
    private void createUser(String name) {
        DatabaseReference mFirebaseDatabase = getDatabase().getReference(getString(R.string.users));
        String userId = null;
        if (getAuth().getCurrentUser() != null) {
            userId = getAuth().getCurrentUser().getUid();
        }
        User user = new User(name);
        mFirebaseDatabase.child(userId).setValue(user);
    }
}
