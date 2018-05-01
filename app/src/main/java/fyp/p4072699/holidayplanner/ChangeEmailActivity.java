package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ChangeEmailActivity extends NavController {
    private EditText newEmail, confEmail;
    private String em, cem;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        getDrawer();
        setTitle(R.string.change_email);
        connectDisplay();
    }

    //Connect to the display
    protected void connectDisplay() {
        buttonCancel();
        buttonSave();
        newEmail = findViewById(R.id.editText_newEmail);
        confEmail = findViewById(R.id.editText_confirmEmail);
    }

    //Check which button was pressed
    @Override
    public void onClick(View view) {
        i = new Intent(ChangeEmailActivity.this, ProfileActivity.class);
        switch (view.getId()) {
            case R.id.button_save:
                updateEmail();
                break;
            case R.id.button_cancel:
                startActivity(i);
                break;
        }
    }

    //Update the email stored on firebase
    protected void updateEmail() {
        em = newEmail.getText().toString();
        cem = confEmail.getText().toString();
        if (em.equals(cem)) {
            getUser().updateEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendToast(getString(R.string.email_updated));
                    } else {
                        sendToast(getString(R.string.email_not_updated));
                    }
                }
            });
            startActivity(i);
        } else {
            sendToast(getString(R.string.both_email));
        }
    }
}
