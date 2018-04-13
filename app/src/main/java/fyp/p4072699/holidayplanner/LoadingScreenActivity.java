package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

public class LoadingScreenActivity extends AppController {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        getHolidays();

        try {
            TimeUnit.SECONDS.sleep(2);
            startActivity(new Intent(LoadingScreenActivity.this, HomeActivity.class));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
