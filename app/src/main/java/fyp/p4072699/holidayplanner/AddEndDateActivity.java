package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;

public class AddEndDateActivity extends NavController implements View.OnClickListener, DatePicker.OnDateChangedListener {
    private int day, month, year;
    private Button next, cancel, ret;
    private ArrayList<Integer> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_end_date);
        getDrawer();
        setTitle(R.string.add_end_date);
        getDetails();
        setupCalendar();
        connectDisplay();
        setClickListeners();
    }

    //Get the details from the previous screen
    protected void getDetails() {
        details = getIntent().getIntegerArrayListExtra(getString(R.string.details));
        day = details.get(0);
        month = details.get(1);
        year = details.get(2);
    }

    //Set the calendar to the start date
    protected void setupCalendar() {
        DatePicker endDate = findViewById(R.id.datePicker_enddate);
        endDate.init(year, month, day, this);
    }

    //Connect to the items on the display
    protected void connectDisplay() {
        next = findViewById(R.id.button_confirmenddate);
        cancel = findViewById(R.id.button_cancel);
        ret = findViewById(R.id.button_return);
    }

    //Setup the listeners for clicks
    protected void setClickListeners() {
        next.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ret.setOnClickListener(this);
    }

    //Check which button was clicked
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_confirmenddate:
                checkDateIsAcceptable();
                break;
            case R.id.button_cancel:
                startActivity(new Intent(AddEndDateActivity.this, HomeActivity.class));
                break;
            case R.id.button_return:
                startActivity(new Intent(AddEndDateActivity.this, AddStartDateActivity.class));
                break;
        }
    }

    //Make sure that the date is after the start date
    protected void checkDateIsAcceptable() {
        if (details.get(2) < year) {
            carryOn();
        } else if (checkDate(details.get(2), year) && details.get(1) < month) {
            carryOn();
        } else if (checkDate(details.get(2), year) && checkDate(details.get(1), month) && checkDate(details.get(0), day)) {
            carryOn();
        } else {
            sendToast(getString(R.string.end_after_start));
        }
    }

    //Go to the next page
    protected void carryOn() {
        details.add(day);
        details.add(month);
        details.add(year);
        startActivity(new Intent(AddEndDateActivity.this, AddLocationActivity.class)
                .putExtra(getString(R.string.details), details));
    }

    //Get the date when changed
    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        year = i;
        month = i1;
        day = i2;
    }
}
