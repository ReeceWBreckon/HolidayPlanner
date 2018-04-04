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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button save, cancel;
    private EditText newEmail, confEmail;
    private FirebaseAuth auth;
    private FirebaseUser u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        setTitle(R.string.change_email);

        //Setup firebase
        auth = FirebaseAuth.getInstance();
        u = auth.getCurrentUser();

        //Connect to the display
        save = findViewById(R.id.button_save);
        cancel = findViewById(R.id.button_cancel);
        newEmail = findViewById(R.id.editText_newEmail);
        confEmail = findViewById(R.id.editText_confirmEmail);

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
                    u.updateEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangeEmailActivity.this, "Email Updated.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangeEmailActivity.this, "Could not update email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    startActivity(i);
                } else {
                    Toast.makeText(ChangeEmailActivity.this, "Emails do not match.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_cancel:
                startActivity(i);
                break;
        }
    }
}
