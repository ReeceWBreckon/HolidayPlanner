package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class NearHolidayActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener {
    private String baseURL, URL, location, radius, type, key, name, rating;
    private Button retur;
    private ListView nearbyLV;
    private ArrayList<String> nearbyList, placeID;
    private ArrayAdapter ad;
    private SeekBar distance;
    private TextView dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_holiday);
        setTitle(getIntent().getStringExtra("title"));
        connectDisplay();
        setupListView();
        setupURL();
        setListeners();
        getPlaces("");
    }

    protected void setupURL() {
        //set the url paramaters
        location = getIntent().getStringExtra("coords");
        type = getIntent().getStringExtra("type");
        radius = String.valueOf(distance.getProgress());
        key = "AIzaSyDAiArIeNB9Yyqvf--VRQZQb4Vhx-37b_k";
        baseURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    }

    protected void setupListView() {
        nearbyList = new ArrayList<>();
        placeID = new ArrayList<>();
        ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nearbyList);
        nearbyLV.setAdapter(ad);
        dis.setText(String.valueOf(distance.getProgress()));
    }

    protected void connectDisplay() {
        //Connect to the display
        retur = findViewById(R.id.button_return);
        nearbyLV = findViewById(R.id.listView_nearby);
        distance = findViewById(R.id.seekBar_distance);
        dis = findViewById(R.id.textview_distance);
    }

    protected void setListeners() {
        //Set the click listeners
        retur.setOnClickListener(this);
        nearbyLV.setOnItemClickListener(this);
        //Setup the seek bar
        distance.setOnSeekBarChangeListener(this);
    }

    protected void getPlaces(String s) {
        if (s.equals("")) {
            URL = baseURL + "location=" + location + "&radius=" + radius + "&type=" + type + "&key=" + key;
            nearbyList.clear();
        } else {
            URL = s;
        }
        final String[] pageToken = new String[1];
        pageToken[0] = "";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject obj, results = null;
                JSONArray res;
                try {
                    obj = new JSONObject(new String(responseBody));
                    if (obj.has("next_page_token")) {
                        pageToken[0] = obj.getString("next_page_token");
                    }
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
                    if (!pageToken[0].equals("")) {
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getPlaces(baseURL + "pagetoken=" + pageToken[0] + "&key=" + key);
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
                .putExtra("location", location)
                .putExtra("type", type));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        radius = String.valueOf(i);
        dis.setText(String.valueOf(distance.getProgress()));
        getPlaces("");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        dis.setText(String.valueOf(distance.getProgress()));
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
