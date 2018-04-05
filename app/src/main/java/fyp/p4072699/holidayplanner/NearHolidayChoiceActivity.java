package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class NearHolidayChoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_holiday_choice);
        setTitle(R.string.near_holiday);

        rest = findViewById(R.id.imageButton_rest);

        rest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(NearHolidayChoiceActivity.this, NearHolidayActivity.class);
        String c = getIntent().getStringExtra("coords");
        switch (view.getId()) {
            case R.id.imageButton_rest:
                startActivity(i.putExtra("type", "restaurant")
                        .putExtra("coords", c)
                        .putExtra("title", "Nearby Restaurants"));
                break;
        }
    }
}
