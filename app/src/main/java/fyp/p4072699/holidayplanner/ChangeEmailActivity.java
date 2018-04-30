package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ChangeEmailActivity extends NavController implements View.OnClickListener {
    private Button save, cancel;
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
        setListeners();
    }

    //Connect to the display
    protected void connectDisplay() {
        save = findViewById(R.id.button_save);
        cancel = findViewById(R.id.button_cancel);
        newEmail = findViewById(R.id.editText_newEmail);
        confEmail = findViewById(R.id.editText_confirmEmail);
    }

    //Set the click listeners
    protected void setListeners() {
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    //Check which button was pressed
    @Override
    public void onClick(View view) {
        em = newEmail.getText().toString();
        cem = confEmail.getText().toString();
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
