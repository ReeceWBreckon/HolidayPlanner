package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;

public class AddEndDateActivity extends AppCompatActivity implements View.OnClickListener, DatePicker.OnDateChangedListener {
    private DatePicker endDate;
    private int day, month, year;
    private Button next, cancel, ret;
    private ArrayList<Integer> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_end_date);
        setTitle(R.string.add_end_date);
        getDetails();
        setupCalendar();
        connectDisplay();
        setClickListeners();
    }

    protected void getDetails() {
        details = getIntent().getIntegerArrayListExtra("Details");
        day = details.get(0);
        month = details.get(1);
        year = details.get(2);
    }

    protected void setupCalendar() {
        endDate = findViewById(R.id.datePicker_enddate);
        endDate.init(year, month, day, this);
    }

    protected void connectDisplay() {
        next = findViewById(R.id.button_confirmenddate);
        cancel = findViewById(R.id.button_cancel);
        ret = findViewById(R.id.button_return);
    }

    protected void setClickListeners() {
        next.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ret.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_confirmenddate:
                details.add(day);
                details.add(month);
                details.add(year);
                startActivity(new Intent(AddEndDateActivity.this, AddLocationActivity.class)
                        .putExtra("Details", details));
                break;
            case R.id.button_cancel:
                startActivity(new Intent(AddEndDateActivity.this, HomeActivity.class));
                break;
            case R.id.button_return:
                startActivity(new Intent(AddEndDateActivity.this, AddStartDateActivity.class));
                break;
        }
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        year = i;
        month = i1;
        day = i2;
    }
}
