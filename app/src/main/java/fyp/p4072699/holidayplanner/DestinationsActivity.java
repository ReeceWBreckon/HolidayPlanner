package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DestinationsActivity extends NavController implements AdapterView.OnItemClickListener {
    private ListView destinationLV;
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
        setupListener();
        getHotCountry();
    }

    //Set the list view and adapter
    protected void setupListView() {
        ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hotList);
        destinationLV.setAdapter(ad);
    }

    //Connect to the display
    protected void connectDisplay() {
        destinationLV = (ListView) findViewById(R.id.listview_destinations);
        hotList = new ArrayList<>();
    }

    //Set the click listeners
    protected void setListeners() {
        destinationLV.setOnItemClickListener(this);
    }

    //Get the list of trending countries from firebase
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

    //When an item in the list view is clicked get the coordinates
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Geocoder geo = new Geocoder(this);
        try {
            List<Address> location = geo.getFromLocationName(hotList.get(i), 1);
            startActivity(new Intent(DestinationsActivity.this, DestinationDetailActivity.class)
                    .putExtra(getString(R.string.lat), location.get(0).getLatitude())
                    .putExtra(getString(R.string.lng), location.get(0).getLongitude()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
