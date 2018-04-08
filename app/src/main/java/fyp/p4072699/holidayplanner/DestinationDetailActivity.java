package fyp.p4072699.holidayplanner;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class DestinationDetailActivity extends AppCompatActivity {
    private double lat, lng;
    private String country, countryCode, baseURL, URL, capital, region, currency;
    private List<Address> location;
    private TextView c, cap, reg, currenc;
    private ImageView flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);
        setTitle(R.string.destination_details);
        connectDisplay();
        setParameters();
        geoCoder();
        getDetails();
        setDisplay();
    }

    protected void setDisplay() {
        c.setText(country);
        int flagid = getResources().getIdentifier(countryCode.toLowerCase(), "drawable", getPackageName());
        flag.setImageResource(flagid);
    }

    protected void geoCoder() {
        //get the country from the coordiantes
        Geocoder geo = new Geocoder(this);
        try {
            location = geo.getFromLocation(lat, lng, 1);

            if (location != null) {
                country = location.get(0).getCountryName();
                countryCode = location.get(0).getCountryCode();
                if (countryCode.equals("do")) {
                    countryCode = "doo"; //do is a sacred word in java
                }
                URL = baseURL + countryCode;
            }
        } catch (IOException e) {
        }
    }

    protected void setParameters() {
        //Retrieve long/lang from previous screen
        lat = getIntent().getExtras().getDouble("lat");
        lng = getIntent().getExtras().getDouble("lng");
        baseURL = "https://restcountries.eu/rest/v2/alpha/";
    }

    protected void connectDisplay() {
        //Connect to the display
        c = findViewById(R.id.textView_country);
        flag = findViewById(R.id.imageView_flag);
        cap = findViewById(R.id.textView_capital);
        reg = findViewById(R.id.textView_subregion);
        currenc = findViewById(R.id.textView_currency);
    }

    private void getDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject obj, co = null;
                JSONArray curr;
                try {
                    obj = new JSONObject(new String(responseBody));
                    capital = obj.getString("capital");
                    region = obj.getString("region");
                    curr = obj.getJSONArray("currencies");
                    co = curr.getJSONObject(0);
                    currency = co.getString("name");
                    Log.d("currecnt", currency);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cap.setText(capital);
                reg.setText(region);
                currenc.setText(currency);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
