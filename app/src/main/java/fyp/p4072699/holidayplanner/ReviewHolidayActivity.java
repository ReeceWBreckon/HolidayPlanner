package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ReviewHolidayActivity extends NavController implements View.OnClickListener {
    private TextView startDate, endDate, location;
    private ArrayList<Integer> details;
    private Button save, cancel;
    private String start, end, loc, toDay, toMonth, toYear, fromDay, fromMonth, fromYear;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_holiday);
        getDrawer();
        setTitle(R.string.review_holiday);
        getDetailsFromPrevious();
        connectDisplay();
        setDisplay();
        setListeners();
    }

    //Get the details from the previous screen
    protected void getDetailsFromPrevious() {
        details = getIntent().getIntegerArrayListExtra(getString(R.string.details));
        loc = getIntent().getStringExtra(getString(R.string.loc));
        lat = getIntent().getExtras().getDouble(getString(R.string.lat));
        lng = getIntent().getExtras().getDouble(getString(R.string.lng));
        toDay = String.valueOf(details.get(3));
        toMonth = String.valueOf(details.get(4) + 1);
        toYear = String.valueOf(details.get(5));
        fromDay = String.valueOf(details.get(0));
        fromMonth = String.valueOf(details.get(1) + 1);
        fromYear = String.valueOf(details.get(2));
    }

    //Set the text to show the holiday details
    protected void setDisplay() {
        end = toDay + getString(R.string.slash) + toMonth + getString(R.string.slash) + toYear;
        start = fromDay + getString(R.string.slash) + fromMonth + getString(R.string.slash) + fromYear;
        startDate.setText(start);
        endDate.setText(end);
        location.setText(loc);
    }

    //Connect to the display
    protected void connectDisplay() {
        save = findViewById(R.id.button_save);
        cancel = findViewById(R.id.button_cancel);
        startDate = findViewById(R.id.textView_start);
        endDate = findViewById(R.id.textView_enddate);
        location = findViewById(R.id.textView_location);
    }

    //Add the click listeners
    protected void setListeners() {
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    //Check which button was pressed
    @Override
    public void onClick(View view) {
        Intent i = new Intent(ReviewHolidayActivity.this, HomeActivity.class);
        switch (view.getId()) {
            case R.id.button_save:
                addHoliday();
                startActivity(i);
                break;
            case R.id.button_cancel:
                startActivity(i);
                break;
        }
    }

    //Add the holiday to the firebase database
    private void addHoliday() {
        String userId = null;

        if (getAuth().getCurrentUser() != null) {
            userId = getAuth().getCurrentUser().getUid();
        }

        DatabaseReference fDB = getDatabase().getReference(getString(R.string.holidays)).child(userId);
        DatabaseReference r = fDB.push();

        Holiday h = new Holiday(loc, lat, lng, toDay, toMonth, toYear, fromDay, fromMonth, fromYear, "0");

        r.setValue(h);
    }
}
