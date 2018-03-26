package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class AddStartDateActivity extends AppCompatActivity implements View.OnClickListener {
    private DatePicker startDate;
    private Calendar calendar;
    private int day, month, year;
    private Button next, cancel;
    private ArrayList<Integer> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_start_date);

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        details = new ArrayList<>();

        startDate = findViewById(R.id.datePicker_startdate);
        startDate.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1;
                day = i2;
            }
        });

        next = findViewById(R.id.button_confirmstartdate);
        cancel = findViewById(R.id.button_cancel);

        next.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_confirmstartdate:

                details.add(day);
                details.add(month);
                details.add(year);
                startActivity(new Intent(AddStartDateActivity.this, AddEndDateActivity.class)
                        .putExtra("Details", details));
                break;
            case R.id.button_cancel:
                startActivity(new Intent(AddStartDateActivity.this, HomeActivity.class));
                break;
        }
    }
}
