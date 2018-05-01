package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

public class AddLocationActivity extends NavController {
    private ArrayList<Integer> details;
    private String loc = "";
    private double lo, la;
    private View m;
    private Float zoom = 11.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        getDrawer();
        setTitle(R.string.add_location);
        connectDisplay();
        setupMap();
        hideMap();
        setupListener();
    }

    protected void hideMap() {
        m.setVisibility(View.GONE);
    }

    protected void showMap() {
        m.setVisibility(View.VISIBLE);
    }

    //Connect to the display
    protected void connectDisplay() {
        buttonNext();
        buttonCancel();
        buttonReturn();
        details = getIntent().getIntegerArrayListExtra(getString(R.string.details));
        m = findViewById(R.id.map_location);
    }

    //Used to see which button was pressed
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_next:
                checkLocation();
                break;
            case R.id.button_cancel:
                startActivity(new Intent(AddLocationActivity.this, HomeActivity.class));
                break;
            case R.id.button_return:
                startActivity(new Intent(AddLocationActivity.this, AddEndDateActivity.class)
                        .putExtra(getString(R.string.details), details));
                break;
        }
    }

    //Check that the user has selected a location
    protected void checkLocation() {
        if (loc.equals("")) {
            sendToast(getString(R.string.must_have_location));
        } else {
            startActivity(new Intent(AddLocationActivity.this, ReviewHolidayActivity.class)
                    .putExtra(getString(R.string.details), details)
                    .putExtra(getString(R.string.loc), loc)
                    .putExtra(getString(R.string.lng), lo)
                    .putExtra(getString(R.string.lat), la));
        }
    }

    //Get the details from the selected location
    @Override
    public void onPlaceSelected(Place place) {
        loc = place.getName().toString();
        lo = place.getLatLng().longitude;
        la = place.getLatLng().latitude;
        setLat(la);
        setLng(lo);
        setF(zoom);
        showMap();
    }
}
