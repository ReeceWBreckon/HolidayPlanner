package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ChangePasswordActivity extends AppController implements View.OnClickListener {
    private Button save, cancel;
    private EditText newPass, confPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle(R.string.change_password);
        connectDisplay();
        setListeners();
    }

    protected void connectDisplay() {
        //Connect to the display
        save = findViewById(R.id.button_save);
        cancel = findViewById(R.id.button_cancel);
        newPass = findViewById(R.id.editText_newPassword);
        confPass = findViewById(R.id.editText_confirmpassword);
    }

    protected void setListeners() {
        //Set the click listeners
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
        String p = newPass.getText().toString();
        String cp = confPass.getText().toString();
        switch (view.getId()) {
            case R.id.button_save:
                if (p.equals(cp)) {
                    getUser().updatePassword(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                sendToast("Password Updated.");
                            } else {
                                sendToast("Unable to update password.");
                            }
                        }
                    });
                    startActivity(i);
                } else {
                    sendToast("Password do not match.");
                }
                break;
            case R.id.button_cancel:
                startActivity(i);
                break;
        }
    }
}
