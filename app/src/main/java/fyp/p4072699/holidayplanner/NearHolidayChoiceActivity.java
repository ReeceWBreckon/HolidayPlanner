package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class NearHolidayChoiceActivity extends NavController implements View.OnClickListener {
    private ImageButton rest, amuse, aquarium, casino, museum, bar, zoo, shops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_holiday_choice);
        getDrawer();
        setTitle(R.string.near_holiday);
        connectDisplay();
        setListeners();
    }

    //Connect to the display
    protected void connectDisplay() {
        buttonReturn();
        rest = findViewById(R.id.imageButton_rest);
        amuse = findViewById(R.id.imageButton_amusement);
        aquarium = findViewById(R.id.imageButton_aquarium);
        casino = findViewById(R.id.imageButton_casino);
        museum = findViewById(R.id.imageButton_museum);
        bar = findViewById(R.id.imageButton_bar);
        zoo = findViewById(R.id.imageButton_zoo);
        shops = findViewById(R.id.imageButton_shopping);
    }

    //Set the click listeners
    protected void setListeners() {
        rest.setOnClickListener(this);
        amuse.setOnClickListener(this);
        aquarium.setOnClickListener(this);
        casino.setOnClickListener(this);
        museum.setOnClickListener(this);
        bar.setOnClickListener(this);
        zoo.setOnClickListener(this);
        shops.setOnClickListener(this);
    }

    //Check which image button was pressed
    @Override
    public void onClick(View view) {
        String type = null, title = null;
        if (view.getId() != R.id.button_return) {
            switch (view.getId()) {
                case R.id.imageButton_rest:
                    type = getString(R.string.type_resteraunt);
                    title = getString(R.string.title_resteraunt);
                    break;
                case R.id.imageButton_amusement:
                    type = getString(R.string.type_amusement);
                    title = getString(R.string.title_amusement);
                    break;
                case R.id.imageButton_aquarium:
                    type = getString(R.string.type_aquarium);
                    title = getString(R.string.title_aquarium);
                    break;
                case R.id.imageButton_casino:
                    type = getString(R.string.type_casino);
                    title = getString(R.string.title_casino);
                    break;
                case R.id.imageButton_museum:
                    type = getString(R.string.type_museum);
                    title = getString(R.string.title_museum);
                    break;
                case R.id.imageButton_bar:
                    type = getString(R.string.type_bar);
                    title = getString(R.string.title_bar);
                    break;
                case R.id.imageButton_zoo:
                    type = getString(R.string.type_zoo);
                    title = getString(R.string.title_zoo);
                    break;
                case R.id.imageButton_shopping:
                    type = getString(R.string.type_shopping);
                    title = getString(R.string.title_shopping);
                    break;
            }
            startAct(type, title);
        } else {
            startActivity(new Intent(NearHolidayChoiceActivity.this, MyHolidaysActivity.class));
        }
    }

    //To start the image button activity, removes repeated code
    public void startAct(String t, String title) {
        String c = getIntent().getStringExtra(getString(R.string.coords));
        Intent i = new Intent(NearHolidayChoiceActivity.this, NearHolidayActivity.class);
        startActivity(i.putExtra(getString(R.string.type), t)
                .putExtra(getString(R.string.coords), c)
                .putExtra(getString(R.string.title), title));
    }
}
