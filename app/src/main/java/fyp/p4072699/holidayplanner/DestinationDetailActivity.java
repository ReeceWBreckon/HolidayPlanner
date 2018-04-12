package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class DestinationDetailActivity extends AppController implements View.OnClickListener {
    private double lat, lng;
    private Button retur;
    private String country, countryCode, URL, capital, region, currency;
    private TextView c, cap, reg, currenc;
    private ImageView flag;
    private int index = 0;

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
        setClickListeners();
    }

    protected void setClickListeners() {
        retur.setOnClickListener(this);
    }

    protected void setDisplay() {
        c.setText(country);
        int flagid = getResources().getIdentifier(countryCode.toLowerCase(), getString(R.string.drawable), getPackageName());
        flag.setImageResource(flagid);
        retur = findViewById(R.id.button_retur);
    }

    protected void geoCoder() {
        //get the country from the coordiantes
        Geocoder geo = new Geocoder(this);
        try {
            List<Address> location = geo.getFromLocation(lat, lng, 1);
            if (location != null) {
                country = location.get(index).getCountryName();
                countryCode = location.get(index).getCountryCode();
                if (countryCode.equals(getString(R.string.domRepublicCode))) {
                    countryCode = getString(R.string.domrepublicReplace); //do is a sacred word in java
                }
                saveCountry(countryCode);
                URL = getString(R.string.rest_base_url) + countryCode;
            }
        } catch (IOException e) {
        }
    }

    protected void setParameters() {
        //Retrieve long/lang from previous screen
        lat = getIntent().getExtras().getDouble(getString(R.string.lat));
        lng = getIntent().getExtras().getDouble(getString(R.string.lng));
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
                JSONObject obj, co;
                JSONArray curr;
                try {
                    obj = new JSONObject(new String(responseBody));
                    capital = obj.getString(getString(R.string.capital_lower));
                    region = obj.getString(getString(R.string.region_lower));
                    curr = obj.getJSONArray(getString(R.string.currency_lower));
                    co = curr.getJSONObject(index);
                    currency = co.getString(getString(R.string.name_lower));
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

    @Override
    public void onClick(View view) {
        startActivity(new Intent(DestinationDetailActivity.this, DestinationsActivity.class));
    }
}
