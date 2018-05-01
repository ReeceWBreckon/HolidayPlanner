package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ChangePasswordActivity extends NavController {
    private Button save, cancel;
    private EditText newPass, confPass;
    private Intent i;
    private String p, cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getDrawer();
        setTitle(R.string.change_password);
        connectDisplay();
    }

    //Connect to the display
    protected void connectDisplay() {
        buttonCancel();
        buttonSave();
        newPass = findViewById(R.id.editText_newPassword);
        confPass = findViewById(R.id.editText_confirmpassword);
    }

    //Check which button was clicked
    @Override
    public void onClick(View view) {
        i = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
        switch (view.getId()) {
            case R.id.button_save:
                updatePassword();
                break;
            case R.id.button_cancel:
                startActivity(i);
                break;
        }
    }

    //Update the password on firebase
    protected void updatePassword() {
        p = newPass.getText().toString();
        cp = confPass.getText().toString();
        if (p.equals(cp)) {
            getUser().updatePassword(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendToast(getString(R.string.password_updated));
                    } else {
                        sendToast(getString(R.string.password_not_updated));
                    }
                }
            });
            startActivity(i);
        } else {
            sendToast(getString(R.string.both_passwords));
        }
    }
}
