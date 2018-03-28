package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AddLocationActivity extends AppCompatActivity implements View.OnClickListener {
    private Button next, cancel, ret;
    private ArrayList<Integer> details;
    private Intent i;
    private String loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        setTitle(R.string.add_location);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        i = getIntent();
        next = findViewById(R.id.button_next);
        cancel = findViewById(R.id.button_cancel);
        ret = findViewById(R.id.button_return);
        details = i.getIntegerArrayListExtra("Details");

        next.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ret.setOnClickListener(this);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i("w", "Place: " + place.getName());
                loc = place.getName().toString();
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
                        .putExtra("Location", loc));
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
