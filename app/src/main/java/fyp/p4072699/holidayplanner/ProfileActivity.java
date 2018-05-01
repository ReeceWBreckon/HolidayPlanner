package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends NavController implements ValueEventListener {
    private TextView name, email;
    private String userID, e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getDrawer();
        setTitle(R.string.my_profile);
        setupDatabase();
        connectDisplay();
    }

    protected void setupDatabase() {
        if (getAuth().getCurrentUser() != null) {
            e = getAuth().getCurrentUser().getEmail();
            userID = getAuth().getCurrentUser().getUid();
        }

        getDatabase().getReference().child(getString(R.string.users)).child(userID).addListenerForSingleValueEvent(this);
    }

    //Connect to the display
    protected void connectDisplay() {
        buttonChangeEmail();
        buttonChangePassword();
        buttonSignOut();
        name = findViewById(R.id.textView_name);
        email = findViewById(R.id.textView_email);
    }

    //Check which button was pressed
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_changeemail:
                startActivity(new Intent(ProfileActivity.this, ChangeEmailActivity.class));
                break;
            case R.id.button_changepassword:
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
                break;
            case R.id.button_signout:
                getAuth().signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                break;
        }
    }

    //Gets the users name from firebase, set the name and email on screen
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String n = dataSnapshot.child(getString(R.string.name_lower)).getValue(String.class);
        email.setText(e);
        name.setText(n);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
