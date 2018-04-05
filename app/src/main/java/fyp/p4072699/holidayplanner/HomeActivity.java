package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private Button addHoliday, myHolidays, destinations;
    private FirebaseAuth auth;
    private DrawerLayout draw;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(R.string.home);

        //Connect to the display
        addHoliday = findViewById(R.id.button_addholiday);
        myHolidays = findViewById(R.id.button_viewholidays);
        destinations = findViewById(R.id.button_destinations);
        auth = FirebaseAuth.getInstance();
        draw = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_bar);

        //Add the click listeners
        destinations.setOnClickListener(this);
        addHoliday.setOnClickListener(this);
        myHolidays.setOnClickListener(this);
        nav.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_addholiday:
                startActivity(new Intent(HomeActivity.this, AddStartDateActivity.class));
                break;
            case R.id.button_viewholidays:
                startActivity(new Intent(HomeActivity.this, MyHolidaysActivity.class));
                break;
            case R.id.button_destinations:
                startActivity(new Intent(HomeActivity.this, DestinationsActivity.class));
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        draw.closeDrawers();
        String s = String.valueOf(item.getTitle());
        switch (s) {
            case "Home":

                break;
            case "My Profile":
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                break;
            case "My Holidays":
                startActivity(new Intent(HomeActivity.this, MyHolidaysActivity.class));
                break;
            case "Destinations":
                startActivity(new Intent(HomeActivity.this, DestinationsActivity.class));
                break;
            case "Sign Out":
                auth.signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
        }
        return false;
    }
}
