package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addHoliday, myHolidays, signOut;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addHoliday = findViewById(R.id.button_addholiday);
        myHolidays = findViewById(R.id.button_viewholidays);
        signOut = findViewById(R.id.button_signout);
        auth = FirebaseAuth.getInstance();

        addHoliday.setOnClickListener(this);
        myHolidays.setOnClickListener(this);
        signOut.setOnClickListener(this);

        if (auth.getCurrentUser() != null) {
            Log.d("user", "logged in");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_addholiday:
                startActivity(new Intent(HomeActivity.this, AddStartDateActivity.class));
                break;
            case R.id.button_viewholidays:
                startActivity(new Intent(HomeActivity.this, MyHolidaysActivity.class));
                break;
            case R.id.button_signout:
                auth.signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
        }
    }
}
