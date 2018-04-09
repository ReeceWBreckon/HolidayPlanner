package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ReviewHolidayActivity extends DrawerNavigation implements View.OnClickListener {
    private TextView startDate, endDate, location;
    private ArrayList<Integer> details;
    private Button save, cancel;
    private String start, end, loc;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_holiday);
        setTitle(R.string.review_holiday);
        getDetailsFromPrevious();
        connectDisplay();
        setDisplay();
        setListeners();
    }

    protected void getDetailsFromPrevious() {
        //Get the details from the previous screen
        details = getIntent().getIntegerArrayListExtra("Details");
        loc = getIntent().getStringExtra("Location");
        lat = getIntent().getExtras().getDouble("lat");
        lng = getIntent().getExtras().getDouble("lng");
    }

    protected void setDisplay() {
        //Set the text to show the holiday details
        end = (String.valueOf(details.get(3)) + " / " + String.valueOf(details.get(4) + 1) + " / " + String.valueOf(details.get(5)));
        start = (String.valueOf(details.get(0)) + " / " + String.valueOf(details.get(1) + 1) + " / " + String.valueOf(details.get(2)));
        startDate.setText(start);
        endDate.setText(end);
        location.setText(loc);
    }

    protected void connectDisplay() {
        //Connect to the display
        save = findViewById(R.id.button_save);
        cancel = findViewById(R.id.button_cancel);
        startDate = findViewById(R.id.textView_start);
        endDate = findViewById(R.id.textView_enddate);
        location = findViewById(R.id.textView_location);
    }

    protected void setListeners() {
        //Add the click listeners
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(ReviewHolidayActivity.this, HomeActivity.class);
        switch (view.getId()) {
            case R.id.button_save:
                addHoliday(loc, end, start);
                startActivity(i);
                break;
            case R.id.button_cancel:
                startActivity(i);
                break;
        }
    }

    private void addHoliday(String l, String f, String t) {
        String userId = null;

        if (getAuth().getCurrentUser() != null) {
            userId = getAuth().getCurrentUser().getUid();
        }

        DatabaseReference fDB = getDatabase().getReference("holidays").child(userId);
        DatabaseReference r = fDB.push();

        Holiday h = new Holiday(l, f, t, lat, lng, "0");

        r.setValue(h);
    }
}
