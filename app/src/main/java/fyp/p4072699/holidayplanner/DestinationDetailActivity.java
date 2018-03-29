package fyp.p4072699.holidayplanner;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class DestinationDetailActivity extends AppCompatActivity {
    private double lat, lng;
    private String country, countryCode, baseURL, URL;
    private List<Address> location;
    private TextView c;
    private ImageView flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);
        setTitle(R.string.destination_details);
        baseURL = "https://restcountries.eu/rest/v2/alpha/"; //https://restcountries.eu/rest/v2/alpha/col countryCode at the end

        //Connect to the display
        c = findViewById(R.id.textView_country);
        flag = findViewById(R.id.imageView_flag);

        //Retrieve long/lang from previous screen
        lat = getIntent().getExtras().getDouble("lat");
        lng = getIntent().getExtras().getDouble("lng");

        //get the country from the coordiantes
        Geocoder geo = new Geocoder(this);
        try {
            location = geo.getFromLocation(lat, lng, 1);

            if (location != null) {
                country = location.get(0).getCountryName();
                countryCode = location.get(0).getCountryCode();
                if (countryCode.equals("do")) {
                    countryCode = "doo";
                }
                URL = baseURL + countryCode;

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(URL, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String s = "";
                        try {
                            s = new String(responseBody, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Log.d("details", s); //All detials, pain to get individual
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        } catch (IOException e) {
        }
        c.setText(country);
        int flagid = getResources().getIdentifier(countryCode.toLowerCase(), "drawable", getPackageName());
        flag.setImageResource(flagid);

        Log.d("aaaaaaa", String.valueOf(flagid));
    }
}
