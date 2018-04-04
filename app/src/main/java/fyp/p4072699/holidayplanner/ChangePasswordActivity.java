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

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Button save, cancel;
    private EditText newPass, confPass;
    private FirebaseAuth auth;
    private FirebaseUser u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle(R.string.change_password);

        //Setup firebase
        auth = FirebaseAuth.getInstance();
        u = auth.getCurrentUser();

        //Connect to the display
        save = findViewById(R.id.button_save);
        cancel = findViewById(R.id.button_cancel);
        newPass = findViewById(R.id.editText_newPassword);
        confPass = findViewById(R.id.editText_confirmpassword);

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
                    u.updatePassword(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangePasswordActivity.this, "Password Updated.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "Unable to update password.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    startActivity(i);
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Password do not match.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_cancel:
                startActivity(i);
                break;
        }
    }
}
