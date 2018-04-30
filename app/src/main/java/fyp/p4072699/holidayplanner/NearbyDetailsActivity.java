package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class NearbyDetailsActivity extends NavController implements View.OnClickListener, OnMapReadyCallback {
    private Button retur, web;
    private TextView name, rating, add1;
    private String n, r, a1, w, formAddress, placeID;
    private Float zoom = 15.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_details);
        getDrawer();
        setTitle(R.string.place_details);
        setupUrl();
        connectDisplay();
        setupMap();
        getDetails();
        setListeners();
    }

    //get the id from previous screen
    protected void setupUrl() {
        placeID = getIntent().getStringExtra(getString(R.string.id));
    }

    //Connect to the display
    protected void connectDisplay() {
        retur = findViewById(R.id.button_return);
        name = findViewById(R.id.textView_name);
        add1 = findViewById(R.id.textView_address1);
        rating = findViewById(R.id.textView_rating);
        web = findViewById(R.id.button_website);
    }

    //Set the click listener
    protected void setListeners() {
        retur.setOnClickListener(this);
        web.setOnClickListener(this);
    }

    //Get the details about the place
    private void getDetails() {
        String URL = getString(R.string.google_place_base_url) + placeID + getString(R.string.and_key) + getString(R.string.key);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject obj, res, loca;
                try {
                    obj = new JSONObject(new String(responseBody));
                    res = obj.getJSONObject(getString(R.string.result));
                    n = res.getString(getString(R.string.name_lower));
                    if (!res.has(getString(R.string.rating_lower))) {
                        r = getString(R.string.no_ratings);
                    } else {
                        r = res.getString(getString(R.string.rating_lower)) + getString(R.string.out_of_five);
                    }
                    a1 = res.getString(getString(R.string.formatted_address));
                    if (res.has(getString(R.string.website))) {
                        w = res.getString(getString(R.string.website));
                        web.setVisibility(View.VISIBLE);
                    }
                    loca = res.getJSONObject(getString(R.string.geometry)).getJSONObject(getString(R.string.loc));
                    setLat(loca.getDouble(getString(R.string.lat)));
                    setLng(loca.getDouble(getString(R.string.lng)));
                    setF(zoom);
                    formAddress = a1.replace(", ", getString(R.string.new_line));
                    name.setText(n);
                    rating.setText(r);
                    add1.setText(formAddress);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    //Check which button was clicked
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_return:
                startActivity(new Intent(NearbyDetailsActivity.this, NearHolidayActivity.class)
                        .putExtra(getString(R.string.coords), getIntent().getStringExtra(getString(R.string.loc)))
                        .putExtra(getString(R.string.type), getIntent().getStringExtra(getString(R.string.type))));
                break;
            case R.id.button_website:
                Uri site = Uri.parse(w);
                Intent launchWeb = new Intent(Intent.ACTION_VIEW, site);
                startActivity(launchWeb);
                break;
        }
    }
}
