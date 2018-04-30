package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

public class NavController extends FirebaseController {
    private NavigationView nav;

    //Allow the navigation bar to be clickable
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
}
