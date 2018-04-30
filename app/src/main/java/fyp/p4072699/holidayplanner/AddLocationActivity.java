package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;

public class AddLocationActivity extends AppController implements View.OnClickListener, PlaceSelectionListener, OnMapReadyCallback {
    private Button next, cancel, ret;
    private ArrayList<Integer> details;
    private String loc = "";
    private double lo, la;
    private PlaceAutocompleteFragment autocompleteFragment;
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
        setListeners();
    }

    protected void hideMap() {
        m.setVisibility(View.GONE);
    }

    protected void showMap() {
        m.setVisibility(View.VISIBLE);
    }

    protected void connectDisplay() {
        //Connect to the display
        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        next = findViewById(R.id.button_next);
        cancel = findViewById(R.id.button_cancel);
        ret = findViewById(R.id.button_return);
        details = getIntent().getIntegerArrayListExtra(getString(R.string.details));
        m = findViewById(R.id.map_location);
    }

    protected void setListeners() {
        //Set the click listeners
        next.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ret.setOnClickListener(this);

        //Get the value from the autocomplete
        autocompleteFragment.setOnPlaceSelectedListener(this);
    }

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

    @Override
    public void onError(Status status) {

    }
}
