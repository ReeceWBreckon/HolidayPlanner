package fyp.p4072699.holidayplanner;

import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapController extends AppCompatActivity implements OnMapReadyCallback {
    private float f;
    private double lat, lng;
    private GoogleMap map;
    private SupportMapFragment mapF;

    //Set the zoom
    public void setF(float f) {
        this.f = f;
        onMapReady(map);
    }

    //Set the marker position
    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    //Connect the map
    public void setupMap() {
        mapF = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_location);
        mapF.getMapAsync(this);
    }

    //Load the map and place marker, set scrolling to false
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng loc = new LatLng(lat, lng);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, f));
        map.addMarker(new MarkerOptions().position(loc));
        map.getUiSettings().setScrollGesturesEnabled(false);
    }
}
