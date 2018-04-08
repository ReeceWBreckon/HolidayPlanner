package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ChangeEmailActivity extends DrawerNavigation implements View.OnClickListener {
    private Button save, cancel;
    private EditText newEmail, confEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        setTitle(R.string.change_email);
        connectDisplay();
        setListeners();
    }

    protected void connectDisplay() {
        //Connect to the display
        save = findViewById(R.id.button_save);
        cancel = findViewById(R.id.button_cancel);
        newEmail = findViewById(R.id.editText_newEmail);
        confEmail = findViewById(R.id.editText_confirmEmail);
    }

    protected void setListeners() {
        //Set the click listeners
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String em = newEmail.getText().toString();
        String cem = confEmail.getText().toString();
        Intent i = new Intent(ChangeEmailActivity.this, ProfileActivity.class);
        switch (view.getId()) {
            case R.id.button_save:
                if (em.equals(cem)) {
                    getUser().updateEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                sendToast("Email Updated.");
                            } else {
                                sendToast("Could not update email.");
                            }
                        }
                    });
                    startActivity(i);
                } else {
                    sendToast("Emails do not match.");
                }
                break;
            case R.id.button_cancel:
                startActivity(i);
                break;
        }
    }
}
