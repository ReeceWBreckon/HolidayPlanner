package fyp.p4072699.holidayplanner;

import android.os.Bundle;

public class HomeActivity extends NavController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getDrawer();
        setTitle(R.string.home);
        connectDisplay();
    }

    //Connect to the display
    public void connectDisplay() {
        buttonAddHoliday();
        buttonDestinations();
        buttonMyHoliday();
    }
}
