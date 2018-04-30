package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends NavController implements View.OnClickListener {
    private Button addHoliday, myHolidays, destinations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getDrawer();
        setTitle(R.string.home);
        connectDisplay();
        clickListeners();
    }

    //Connect to the display
    public void connectDisplay() {
        addHoliday = findViewById(R.id.button_addholiday);
        myHolidays = findViewById(R.id.button_viewholidays);
        destinations = findViewById(R.id.button_destinations);
    }

    //Add the click listeners
    public void clickListeners() {
        destinations.setOnClickListener(this);
        addHoliday.setOnClickListener(this);
        myHolidays.setOnClickListener(this);
    }

    //Check which button was clicked
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_addholiday:
                startActivity(new Intent(HomeActivity.this, AddStartDateActivity.class));
                break;
            case R.id.button_viewholidays:
                startActivity(new Intent(HomeActivity.this, MyHolidaysActivity.class));
                break;
            case R.id.button_destinations:
                startActivity(new Intent(HomeActivity.this, DestinationsActivity.class));
                break;
        }
    }
}
