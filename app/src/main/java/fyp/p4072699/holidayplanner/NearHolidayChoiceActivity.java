package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class NearHolidayChoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton rest, amuse, aquarium, casino, museum, bar, zoo;
    private Button retur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_holiday_choice);
        setTitle(R.string.near_holiday);

        //Connect to the display
        rest = findViewById(R.id.imageButton_rest);
        amuse = findViewById(R.id.imageButton_amusement);
        aquarium = findViewById(R.id.imageButton_aquarium);
        casino = findViewById(R.id.imageButton_casino);
        museum = findViewById(R.id.imageButton_museum);
        bar = findViewById(R.id.imageButton_bar);
        zoo = findViewById(R.id.imageButton_zoo);
        retur = findViewById(R.id.button_return);

        //Set the click listeners
        rest.setOnClickListener(this);
        amuse.setOnClickListener(this);
        aquarium.setOnClickListener(this);
        casino.setOnClickListener(this);
        museum.setOnClickListener(this);
        bar.setOnClickListener(this);
        zoo.setOnClickListener(this);
        retur.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String type = null, title = null;
        if (view.getId() != R.id.button_return) {
            switch (view.getId()) {
                case R.id.imageButton_rest:
                    type = "restaurant";
                    title = "Nearby Restaurants";
                    break;
                case R.id.imageButton_amusement:
                    type = "amusement_park";
                    title = "Nearby Amusement Parks";
                    break;
                case R.id.imageButton_aquarium:
                    type = "aquarium";
                    title = "Nearby Aquariums";
                    break;
                case R.id.imageButton_casino:
                    type = "casino";
                    title = "Nearby Casinos";
                    break;
                case R.id.imageButton_museum:
                    type = "museum";
                    title = "Nearby Museums";
                    break;
                case R.id.imageButton_bar:
                    type = "bar";
                    title = "Nearby Bars";
                    break;
                case R.id.imageButton_zoo:
                    type = "zoo";
                    title = "Nearby Zoos";
                    break;
            }
            startAct(type, title);
        } else {
            startActivity(new Intent(NearHolidayChoiceActivity.this, MyHolidaysActivity.class));
        }
    }

    public void startAct(String t, String title) {
        String c = getIntent().getStringExtra("coords");
        Intent i = new Intent(NearHolidayChoiceActivity.this, NearHolidayActivity.class);
        startActivity(i.putExtra("type", t)
                .putExtra("coords", c)
                .putExtra("title", title));
    }
}
