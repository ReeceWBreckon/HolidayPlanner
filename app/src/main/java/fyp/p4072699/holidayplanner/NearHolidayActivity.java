package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NearHolidayActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener {
    private String baseURL, URL, location, radius, type, kyword, key, name, rating;
    private Button retur;
    private ListView nearbyLV;
    private ArrayList<String> nearbyList, placeID;
    private ArrayAdapter ad;
    private SeekBar distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_holiday);
        setTitle(getIntent().getStringExtra("title"));
        nearbyList = new ArrayList<>();
        placeID = new ArrayList<>();

        //Connect to the display
        retur = findViewById(R.id.button_return);
        nearbyLV = findViewById(R.id.listView_nearby);
        ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nearbyList);
        nearbyLV.setAdapter(ad);
        distance = findViewById(R.id.seekBar_distance);

        //Setup the seek bar
        distance.setOnSeekBarChangeListener(this);

        //set the url paramaters
        location = getIntent().getStringExtra("coords");
        type = getIntent().getStringExtra("type");
        radius = String.valueOf(distance.getProgress());
        key = "AIzaSyDAiArIeNB9Yyqvf--VRQZQb4Vhx-37b_k";

        baseURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=

        getPlaces();
        //20 places per search, up to 60 with next page token
        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=    "VALUE"      &key=     YOUR_API_KEY

        //Set the click listeners
        retur.setOnClickListener(this);
        nearbyLV.setOnItemClickListener(this);
    }

    private void getPlaces() {
        URL = baseURL + location + "&radius=" + radius + "&type=" + type + "&key=" + key;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject obj, results = null;
                JSONArray res;
                try {
                    obj = new JSONObject(new String(responseBody));
                    res = obj.getJSONArray("results");
                    nearbyList.clear();
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
                startActivity(new Intent(NearHolidayActivity.this, NearHolidayChoiceActivity.class).putExtra("coords", location));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(NearHolidayActivity.this, NearbyDetailsActivity.class)
                .putExtra("id", placeID.get(i))
                .putExtra("location", location));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        radius = String.valueOf(i);
        Log.d("aaaaaaaa", radius);
        getPlaces();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
