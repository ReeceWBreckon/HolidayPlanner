package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class AddStartDateActivity extends AppController implements View.OnClickListener, DatePicker.OnDateChangedListener {
    private int day, month, year;
    private Button next, cancel;
    private ArrayList<Integer> details;
    private Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_start_date);
        setTitle(R.string.add_start_date);
        connectDisplay();
        setupCalendar();
        setListeners();
    }

    protected void connectDisplay() {
        //Connect to the display
        details = new ArrayList<>();
        next = findViewById(R.id.button_confirmstartdate);
        cancel = findViewById(R.id.button_cancel);
    }

    protected void setupCalendar() {
        //Set the calendar to todays date
        c = getCalendar();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        DatePicker startDate = findViewById(R.id.datePicker_startdate);
        startDate.init(year, month, day, this);
    }

    protected void setListeners() {
        //Add the click listeners
        next.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_confirmstartdate:
                checkDateIsAcceptable();
                break;
            case R.id.button_cancel:
                startActivity(new Intent(AddStartDateActivity.this, HomeActivity.class));
                break;
        }
    }

    protected void checkDateIsAcceptable() {
        if (checkDate(c.get(Calendar.YEAR), year) && checkDate(c.get(Calendar.MONTH), month) && checkDate(c.get(Calendar.DAY_OF_MONTH), day)) {
            details.add(day);
            details.add(month);
            details.add(year);
            startActivity(new Intent(AddStartDateActivity.this, AddEndDateActivity.class)
                    .putExtra(getString(R.string.details), details));
        } else {
            sendToast(getString(R.string.after_current_date));
        }
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        year = i;
        month = i1;
        day = i2;
    }
}
