package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;

public class AddLocationActivity extends AppCompatActivity implements View.OnClickListener {
    private Button next, cancel, ret;
    private ArrayList<Integer> details;
    private Intent i;
    private String loc;
    private double lo, la;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        setTitle(R.string.add_location);

        //Connect to the display
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        i = getIntent();
        next = findViewById(R.id.button_next);
        cancel = findViewById(R.id.button_cancel);
        ret = findViewById(R.id.button_return);
        details = i.getIntegerArrayListExtra("Details");

        //Set the click listeners
        next.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ret.setOnClickListener(this);

        //Get the value from the autocomplete
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i("w", "Place: " + place.getName());
                loc = place.getName().toString();
                lo = place.getLatLng().longitude;
                la = place.getLatLng().latitude;
            }

            @Override
            public void onError(Status status) {
                Log.i("w", "An error occurred: " + status);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_next:
                startActivity(new Intent(AddLocationActivity.this, ReviewHolidayActivity.class)
                        .putExtra("Details", details)
                        .putExtra("Location", loc)
                        .putExtra("lng", lo)
                        .putExtra("lat", la));
                break;
            case R.id.button_cancel:
                startActivity(new Intent(AddLocationActivity.this, HomeActivity.class));
                break;
            case R.id.button_return:
                startActivity(new Intent(AddLocationActivity.this, AddStartDateActivity.class));
                break;
        }
    }
}
