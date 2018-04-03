package fyp.p4072699.holidayplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class NearHolidayActivity extends AppCompatActivity {
    private String baseURL, location, radius, type, kyword, key;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_holiday);
        setTitle(R.string.near_holiday);

        key = "AIzaSyDAiArIeNB9Yyqvf--VRQZQb4Vhx-37b_k";

        //Retrieve long/lang from previous screen
        lat = getIntent().getExtras().getDouble("lat"); //will be passed through from the holiday screen
        lng = getIntent().getExtras().getDouble("lng");

        location = String.valueOf(lat) + String.valueOf(lng);

        baseURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="; //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=
    }
}
