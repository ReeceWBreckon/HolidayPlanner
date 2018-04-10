package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class AppController extends AppCompatActivity implements OnMapReadyCallback {
    private NavigationView nav;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private float f;
    private double lat, lng;
    private GoogleMap map;
    private SupportMapFragment mapF;

    public FirebaseDatabase getDatabase() {
        database = FirebaseDatabase.getInstance();
        return database;
    }

    public FirebaseAuth getAuth() {
        auth = FirebaseAuth.getInstance();
        return auth;
    }

    public FirebaseUser getUser() {

        user = auth.getCurrentUser();
        return user;
    }

    public void sendToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    public void getDrawer() {
        nav = findViewById(R.id.nav_bar);
        auth = FirebaseAuth.getInstance();
        nav.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                break;
                            case R.id.nav_profile:
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                break;
                            case R.id.nav_holidays:
                                startActivity(new Intent(getApplicationContext(), MyHolidaysActivity.class));
                                break;
                            case R.id.nav_destinations:
                                startActivity(new Intent(getApplicationContext(), DestinationsActivity.class));
                                break;
                            case R.id.nav_signout:
                                auth.signOut();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                break;
                        }
                        return true;
                    }
                });
    }

    public void setF(float f) {
        this.f = f;
        onMapReady(map);
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setupMap() {
        mapF = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_location);
        mapF.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng loc = new LatLng(lat, lng);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, f));
        map.addMarker(new MarkerOptions().position(loc));
        map.getUiSettings().setScrollGesturesEnabled(false);
    }
}
