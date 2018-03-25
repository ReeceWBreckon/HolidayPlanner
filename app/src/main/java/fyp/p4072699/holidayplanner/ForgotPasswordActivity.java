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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Button reset, ret;
    private EditText e;
    private String email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        reset = findViewById(R.id.button_reset);
        ret = findViewById(R.id.button_return);
        e = findViewById(R.id.editText_reset);
        auth = FirebaseAuth.getInstance();

        reset.setOnClickListener(this);
        ret.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        email = e.getText().toString();
        switch (view.getId()) {
            case R.id.button_reset:
                if (email.equals("")) {
                    Toast.makeText(this, "Please enter an email.", Toast.LENGTH_SHORT).show();
                    break;
                }
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "We have sent instructions to reset your password.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Failed to send.", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                    }
                });
                break;
            case R.id.button_return:
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                break;
        }
    }
}
