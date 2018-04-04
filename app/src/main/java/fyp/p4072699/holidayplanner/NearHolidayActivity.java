package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NearHolidayActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private String baseURL, URL, location, radius, type, kyword, key, name, rating;
    private Button filter, sortBy, retur;
    private ListView nearbyLV;
    private ArrayList<String> nearbyList, placeID;
    private ArrayAdapter ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_holiday);
        setTitle(R.string.near_holiday);
        nearbyList = new ArrayList<>();
        placeID = new ArrayList<>();

        //Connect to the display
        filter = findViewById(R.id.button_filter);
        sortBy = findViewById(R.id.button_sortBy);
        retur = findViewById(R.id.button_return);
        nearbyLV = findViewById(R.id.listView_nearby);
        ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nearbyList);
        nearbyLV.setAdapter(ad);

        //set the url paramaters
        location = getIntent().getStringExtra("coords");
        radius = "500";
        key = "AIzaSyDAiArIeNB9Yyqvf--VRQZQb4Vhx-37b_k";

        baseURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=

        getPlaces();
        //20 places per search, up to 60 with next page token
        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=    "VALUE"      &key=     YOUR_API_KEY

        //Set the click listeners
        filter.setOnClickListener(this);
        sortBy.setOnClickListener(this);
        retur.setOnClickListener(this);
        nearbyLV.setOnItemClickListener(this);
    }

    private void getPlaces() {
        URL = baseURL + location + "&radius=" + radius + "&key=" + key;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject obj, results = null;
                JSONArray res;
                try {
                    obj = new JSONObject(new String(responseBody));
                    res = obj.getJSONArray("results");
                    for (int count = 0; count < res.length(); count++) {
                        results = res.getJSONObject(count);
                        name = results.getString("name");
                        if (!results.has("rating")) {
                            rating = "Not Yet Rated";
                        } else {
                            rating = results.getString("rating") + "/5";
                        }
                        nearbyList.add("Name: " + name + "\nRating: " + rating);
                        placeID.add(results.getString("place_id"));
                        ad.notifyDataSetChanged();
                    }
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
                startActivity(new Intent(NearHolidayActivity.this, MyHolidaysActivity.class));
                break;
            case R.id.button_filter:

                break;
            case R.id.button_sortBy:

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(NearHolidayActivity.this, NearbyDetailsActivity.class)
                .putExtra("id", placeID.get(i))
                .putExtra("location", location));
    }
}
