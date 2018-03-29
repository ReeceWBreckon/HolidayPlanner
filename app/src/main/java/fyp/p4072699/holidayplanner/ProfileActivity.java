package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Button changeEmail, changePassword, signOut;
    private TextView name, email;
    private FirebaseAuth auth;
    private FirebaseDatabase fdb;
    private String userID, n, e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(R.string.my_profile);
        auth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();

        //Connect to the display
        changeEmail = findViewById(R.id.button_changeemail);
        changePassword = findViewById(R.id.button_changepassword);
        signOut = findViewById(R.id.button_signout);
        name = findViewById(R.id.textView_name);
        email = findViewById(R.id.textView_email);

        if (auth.getCurrentUser() != null) {
            e = auth.getCurrentUser().getEmail();
            userID = auth.getCurrentUser().getUid();
        }

        fdb.getReference().child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                n = dataSnapshot.child("name").getValue(String.class);
                email.setText(e);
                name.setText(n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Set the click listeners
        changePassword.setOnClickListener(this);
        changeEmail.setOnClickListener(this);
        signOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_changeemail:
                //create screen to change email
                break;
            case R.id.button_changepassword:
                //create screen to change password
                break;
            case R.id.button_signout:
                auth.signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                break;
        }
    }
}
