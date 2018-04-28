package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class DestinationsActivity extends AppController implements PlaceSelectionListener {
    private ListView destinationLV;
    private PlaceAutocompleteFragment autocompleteFragment;
    private ArrayAdapter ad;
    private ArrayList<String> hotList;
    private int limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);
        getDrawer();
        setTitle(R.string.destinations);
        connectDisplay();
        setupListView();
        setListeners();
        getHotCountry();
    }

    protected void setupListView() {
        ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hotList);
        destinationLV.setAdapter(ad);
    }

    protected void connectDisplay() {
        //Connect to the display
        destinationLV = (ListView) findViewById(R.id.listview_destinations);
        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        hotList = new ArrayList<>();
    }

    protected void setListeners() {
        //Get the data from the autocomplete
        autocompleteFragment.setOnPlaceSelectedListener(this);
    }

    @Override
    public void onPlaceSelected(Place place) {
        double lat = place.getLatLng().latitude;
        double lng = place.getLatLng().longitude;

        startActivity(new Intent(DestinationsActivity.this, DestinationDetailActivity.class)
                .putExtra(getString(R.string.lat), lat)
                .putExtra(getString(R.string.lng), lng));
    }

    @Override
    public void onError(Status status) {

    }

    protected void getHotCountry() {
        getFireDB().collection(getString(R.string.hot_country)).orderBy(getString(R.string.hits), Query.Direction.DESCENDING).limit(limit).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot s : queryDocumentSnapshots) {
                    Locale name = new Locale("", s.getId());
                    hotList.add(name.getDisplayCountry());
                    ad.notifyDataSetChanged();
                }
            }
        });
    }
}
