package fyp.p4072699.holidayplanner;

import android.content.Intent;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class AutocompleteController extends ButtonController implements PlaceSelectionListener {
    protected void setupListener() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
    }

    @Override
    public void onPlaceSelected(Place place) {
        double lat = place.getLatLng().latitude;
        double lng = place.getLatLng().longitude;

        startActivity(new Intent(getApplicationContext(), DestinationDetailActivity.class)
                .putExtra(getString(R.string.lat), lat)
                .putExtra(getString(R.string.lng), lng));
    }

    @Override
    public void onError(Status status) {

    }
}
