package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ReviewHolidayActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView startDate, endDate, location;
    private ArrayList<Integer> details;
    private Intent i;
    private Button save, cancel;
    private String start, end, loc;
    private DatabaseReference fDB;
    private FirebaseDatabase fDBI;
    private FirebaseAuth auth;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_holiday);
        setTitle(R.string.review_holiday);

        //Get the details from the previous screen
        i = getIntent();
        details = i.getIntegerArrayListExtra("Details");
        loc = i.getStringExtra("Location");
        lat = getIntent().getExtras().getDouble("lat");
        lng = getIntent().getExtras().getDouble("lng");

        //Connect to the display
        save = findViewById(R.id.button_save);
        cancel = findViewById(R.id.button_cancel);
        start = (String.valueOf(details.get(3)) + " / " + String.valueOf(details.get(4)) + " / " + String.valueOf(details.get(5)));
        end = (String.valueOf(details.get(0)) + " / " + String.valueOf(details.get(1)) + " / " + String.valueOf(details.get(2)));
        startDate = findViewById(R.id.textView_start);
        endDate = findViewById(R.id.textView_enddate);
        location = findViewById(R.id.textView_location);

        //Set the text to show the holiday details
        startDate.setText(start);
        endDate.setText(end);
        location.setText(loc);

        //Firebase setup
        auth = FirebaseAuth.getInstance();
        fDBI = FirebaseDatabase.getInstance();
        fDBI.getReference("app_title").setValue("Realtime Database");

        //Add the click listeners
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                addHoliday(loc, end, start);
                startActivity(new Intent(ReviewHolidayActivity.this, HomeActivity.class));
                break;
            case R.id.button_cancel:
                startActivity(new Intent(ReviewHolidayActivity.this, HomeActivity.class));
                break;
        }
    }

    private void addHoliday(String l, String f, String t) {
        String userId = null;

        if (auth.getCurrentUser() != null) {
            userId = auth.getCurrentUser().getUid();
        }

        fDB = fDBI.getReference("holidays").child(userId);
        DatabaseReference r = fDB.push();

        Holiday h = new Holiday(l, f, t, lat, lng);

        r.setValue(h);
    }
}
