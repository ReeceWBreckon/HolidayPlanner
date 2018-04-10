package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ForgotPasswordActivity extends AppController implements View.OnClickListener {
    private Button reset, ret;
    private EditText e;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setTitle(R.string.reset_password);
        connectDisplay();
        setListeners();
    }

    protected void connectDisplay() {
        //Connect to the display
        reset = findViewById(R.id.button_reset);
        ret = findViewById(R.id.button_return);
        e = findViewById(R.id.editText_reset);
    }

    protected void setListeners() {
        //Set the click listeners
        reset.setOnClickListener(this);
        ret.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        email = e.getText().toString();
        switch (view.getId()) {
            case R.id.button_reset:
                if (email.equals("")) {
                    sendToast("Please enter an email.");
                    break;
                }
                getAuth().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            sendToast("We have sent instructions to reset your password.");
                        } else {
                            sendToast("Failed to send.");
                        }
                        startActivity(i);
                    }
                });
                break;
            case R.id.button_return:
                startActivity(i);
                break;
        }
    }
}
