package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class NearbyDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button retur;
    private TextView name, rating, add1, website;
    private String baseURL, placeID, key, URL, n, r, a1, w, formAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_details);
        setTitle(R.string.place_details);
        baseURL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
        placeID = getIntent().getStringExtra("id");
        Log.d("aaaaaa", placeID);
        key = "AIzaSyDAiArIeNB9Yyqvf--VRQZQb4Vhx-37b_k";
        //https://maps.googleapis.com/maps/api/place/details/json?placeid=  PLACEID  &key= VALUE

        //Connect to the display
        retur = findViewById(R.id.button_return);
        name = findViewById(R.id.textView_name);
        add1 = findViewById(R.id.textView_address1);
        rating = findViewById(R.id.textView_rating);
        website = findViewById(R.id.textView_website);

        getDetails();

        //Set the click listener
        retur.setOnClickListener(this);
    }

    private void getDetails() {
        URL = baseURL + placeID + "&key=" + key;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject obj, res = null;
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
                    if (!res.has("website")) {
                        w = "No Website Available.";
                    } else {
                        w = res.getString("website");
                    }
                    formAddress = a1.replace(", ", "\n");
                    name.setText(n);
                    rating.setText(r);
                    add1.setText(formAddress);
                    website.setText(w);
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
                        .putExtra("coords", getIntent().getStringExtra("location")));
                break;
        }
    }
}
