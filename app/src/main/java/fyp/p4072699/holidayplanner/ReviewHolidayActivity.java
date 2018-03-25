package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewHolidayActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView startDate, endDate, location;
    private ArrayList<Integer> details;
    private Intent i;
    private DatabaseHandler dbh;
    private Button save, cancel;
    private String start, end, loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_holiday);

        dbh = new DatabaseHandler(this);
        i = getIntent();
        details = i.getIntegerArrayListExtra("Details");
        save = findViewById(R.id.button_save);
        cancel = findViewById(R.id.button_cancel);
        start = (String.valueOf(details.get(3)) + " / " + String.valueOf(details.get(4)) + " / " + String.valueOf(details.get(5)));
        end = (String.valueOf(details.get(0)) + " / " + String.valueOf(details.get(1)) + " / " + String.valueOf(details.get(2)));
        loc = i.getStringExtra("Location");

        startDate = findViewById(R.id.textView_start);
        endDate = findViewById(R.id.textView_enddate);
        location = findViewById(R.id.textView_location);

        startDate.setText(start);
        endDate.setText(end);
        location.setText(loc);

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                dbh.addHoliday(new Holiday(loc, start, end));
                startActivity(new Intent(ReviewHolidayActivity.this, HomeActivity.class));
                break;
            case R.id.button_cancel:
                startActivity(new Intent(ReviewHolidayActivity.this, HomeActivity.class));
                break;
        }
    }
}
