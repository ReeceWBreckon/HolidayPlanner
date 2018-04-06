package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class DrawerNavigation extends AppCompatActivity {
    private NavigationView nav;
    private FirebaseAuth auth;

    public void getDrawer() {
        nav = findViewById(R.id.nav_bar);
        auth = FirebaseAuth.getInstance();
        nav.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
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
}
