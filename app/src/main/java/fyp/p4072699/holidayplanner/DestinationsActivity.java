package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class DestinationsActivity extends DrawerNavigation implements View.OnClickListener, PlaceSelectionListener {
    private Button home;
    private ListView destinationLV;
    private PlaceAutocompleteFragment autocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);
        getDrawer();
        setTitle(R.string.destinations);
        connectDisplay();
        setListeners();
    }

    protected void connectDisplay() {
        //Connect to the display
        home = findViewById(R.id.button_home);
        destinationLV = (ListView) findViewById(R.id.listview_destinations);
        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
    }

    protected void setListeners() {
        //Set the click listener
        home.setOnClickListener(this);

        //Get the data from the autocomplete
        autocompleteFragment.setOnPlaceSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(DestinationsActivity.this, HomeActivity.class));
    }

    @Override
    public void onPlaceSelected(Place place) {
        double lat = place.getLatLng().latitude;
        double lng = place.getLatLng().longitude;

        startActivity(new Intent(DestinationsActivity.this, DestinationDetailActivity.class)
                .putExtra("lat", lat)
                .putExtra("lng", lng));
    }

    @Override
    public void onError(Status status) {

    }
}
