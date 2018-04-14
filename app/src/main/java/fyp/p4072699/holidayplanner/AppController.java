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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AppController extends AppCompatActivity implements OnMapReadyCallback, ChildEventListener {
    private NavigationView nav;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private FirebaseFirestore fireDB;
    private float f;
    private double lat, lng;
    private GoogleMap map;
    private SupportMapFragment mapF;
    private Calendar calendar = Calendar.getInstance();

    public Calendar getCalendar() {
        return calendar;
    }

    public FirebaseDatabase getDatabase() {
        return database = FirebaseDatabase.getInstance();
    }

    public FirebaseFirestore getFireDB() {
        return fireDB = FirebaseFirestore.getInstance();
    }

    public FirebaseAuth getAuth() {
        return auth = FirebaseAuth.getInstance();
    }

    public FirebaseUser getUser() {
        return user = auth.getCurrentUser();
    }

    public void sendToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    public void getDrawer() {
        nav = findViewById(R.id.nav_bar);
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
                                getAuth().signOut();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                break;
                        }
                        return true;
                    }
                });
    }

    protected void saveCountry(final String s) {
        getFireDB();
        final Map<String, Object> hits = new HashMap<>();
        fireDB.collection(getString(R.string.hot_country)).document(s).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Map<String, Object> data = documentSnapshot.getData();
                    Long i = (Long) data.get(getString(R.string.hits));
                    hits.clear();
                    hits.put(getString(R.string.hits), i + 1);
                    fireDB.collection(getString(R.string.hot_country)).document(s).update(hits);
                } else {
                    hits.put(getString(R.string.hits), 0);
                    fireDB.collection(getString(R.string.hot_country)).document(s).set(hits);
                }
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

    protected void getHolidays() {
        String userId = null;
        if (getAuth().getCurrentUser() != null) {
            userId = getAuth().getCurrentUser().getUid();
            getDatabase().getReference().child(getString(R.string.holidays)).child(userId).addChildEventListener(this);
        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        checkHolidayCompleted(dataSnapshot);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    protected void checkHolidayCompleted(DataSnapshot d) {
        int year = Integer.valueOf(d.child(getString(R.string.to_year)).getValue(String.class));
        int month = Integer.valueOf(d.child(getString(R.string.to_month)).getValue(String.class));
        int day = Integer.valueOf(d.child(getString(R.string.to_day)).getValue(String.class));
        if (d.child(getString(R.string.completed)).getValue(String.class).equals(getString(R.string.zero))) {
            if (checkDate(year, calendar.get(Calendar.YEAR))
                    && checkDate(month, calendar.get(Calendar.MONTH) + 1)
                    && checkDay(day, calendar.get(Calendar.DAY_OF_MONTH))) {
                // Call the update
                updateHolidayCompleted(d.getKey());
            }
        }
    }

    protected void updateHolidayCompleted(String s) {
        String userId = null;

        if (getAuth().getCurrentUser() != null) {
            userId = getAuth().getCurrentUser().getUid();
        }

        final Map<String, Object> completed = new HashMap<>();
        completed.put("completed", "1");

        DatabaseReference fDB = getDatabase().getReference(getString(R.string.holidays)).child(userId).child(s);
        fDB.updateChildren(completed);
    }

    protected boolean checkDate(int y, int yy) {
        return y <= yy;
    }

    protected boolean checkDay(int y, int yy) {
        return y < yy;
    }
}
