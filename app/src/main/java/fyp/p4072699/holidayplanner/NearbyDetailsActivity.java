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

public class NearbyDetailsActivity extends AppController implements View.OnClickListener, OnMapReadyCallback {
    private Button retur, web;
    private TextView name, rating, add1;
    private String baseURL, placeID, key, URL, n, r, a1, w, formAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_details);
        setTitle(R.string.place_details);
        setupUrl();
        connectDisplay();
        setupMap();
        getDetails();
        setListeners();
    }

    protected void setupUrl() {
        baseURL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
        placeID = getIntent().getStringExtra("id");
        key = "AIzaSyDAiArIeNB9Yyqvf--VRQZQb4Vhx-37b_k";
    }

    protected void connectDisplay() {
        //Connect to the display
        retur = findViewById(R.id.button_return);
        name = findViewById(R.id.textView_name);
        add1 = findViewById(R.id.textView_address1);
        rating = findViewById(R.id.textView_rating);
        web = findViewById(R.id.button_website);
    }

    protected void setListeners() {
        //Set the click listener
        retur.setOnClickListener(this);
        web.setOnClickListener(this);
    }

    private void getDetails() {
        URL = baseURL + placeID + "&key=" + key;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject obj, res, loca;
                try {
                    obj = new JSONObject(new String(responseBody));
                    res = obj.getJSONObject("result");
                    n = res.getString("name");
                    if (!res.has("rating")) {
                        r = "No Ratings Available.";
                    } else {
                        r = res.getString("rating") + "/5";
                    }
                    a1 = res.getString("formatted_address");
                    if (res.has("website")) {
                        w = res.getString("website");
                        web.setVisibility(View.VISIBLE);
                    }
                    loca = res.getJSONObject("geometry").getJSONObject("location");
                    setLat(loca.getDouble("lat"));
                    setLng(loca.getDouble("lng"));
                    setF(15.0f);
                    formAddress = a1.replace(", ", "\n");
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_return:
                startActivity(new Intent(NearbyDetailsActivity.this, NearHolidayActivity.class)
                        .putExtra("coords", getIntent().getStringExtra("location"))
                        .putExtra("type", getIntent().getStringExtra("type")));
                break;
            case R.id.button_website:
                Uri site = Uri.parse(w);
                Intent launchWeb = new Intent(Intent.ACTION_VIEW, site);
                startActivity(launchWeb);
                break;
        }
    }
}
