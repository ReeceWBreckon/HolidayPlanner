package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class DestinationsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button home;
    private ListView destinationLV;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);
        setTitle(R.string.destinations);

        //Connect to the display
        home = findViewById(R.id.button_home);
        destinationLV = (ListView) findViewById(R.id.listview_destinations);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        //Set the click listener
        home.setOnClickListener(this);

        //Get the data from the autocomplete
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;

                startActivity(new Intent(DestinationsActivity.this, DestinationDetailActivity.class)
                        .putExtra("lat", lat)
                        .putExtra("lng", lng));
            }

            @Override
            public void onError(Status status) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(DestinationsActivity.this, HomeActivity.class));
    }
}
