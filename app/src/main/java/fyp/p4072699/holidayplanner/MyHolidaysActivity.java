package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class MyHolidaysActivity extends AppController implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Button add;
    private ListView holidayLv;
    private ArrayAdapter ad;
    private ArrayList<String> holList, coords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_holidays);
        getDrawer();
        setTitle(R.string.my_holidays);
        connectDisplay();
        setupListView();
        getHolidays();
        setListeners();
    }

    protected void connectDisplay() {
        //Connect to the display
        add = findViewById(R.id.button_addholiday);
        holidayLv = findViewById(R.id.listView_holidays);
        holList = new ArrayList<String>();
        coords = new ArrayList<String>();
    }

    protected void setupListView() {
        ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, holList);
        holidayLv.setAdapter(ad);
    }

    protected void setListeners() {
        //Set the click listeners
        add.setOnClickListener(this);
        holidayLv.setOnItemClickListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot.child(getString(R.string.completed)).getValue(String.class).equals(getString(R.string.zero))) {
            String to = dataSnapshot.child(getString(R.string.to_day)).getValue(String.class) + getString(R.string.slash)
                    + dataSnapshot.child(getString(R.string.to_month)).getValue(String.class) + getString(R.string.slash)
                    + dataSnapshot.child(getString(R.string.to_year)).getValue(String.class);
            String from = dataSnapshot.child(getString(R.string.from_day)).getValue(String.class) + getString(R.string.slash)
                    + dataSnapshot.child(getString(R.string.from_month)).getValue(String.class) + getString(R.string.slash)
                    + dataSnapshot.child(getString(R.string.from_year)).getValue(String.class);
            String m = getString(R.string.location_two_dots) + dataSnapshot.child(getString(R.string.loc)).getValue(String.class) +
                    getString(R.string.new_line) + getString(R.string.date_from_two_dots) + from +
                    getString(R.string.new_line) + getString(R.string.date_to_two_dots) + to;
            String c = dataSnapshot.child(getString(R.string.lat)).getValue(double.class).toString() + getString(R.string.com) + dataSnapshot.child(getString(R.string.lon)).getValue(double.class).toString();
            coords.add(c);
            holList.add(m);
            ad.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(MyHolidaysActivity.this, AddStartDateActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(MyHolidaysActivity.this, NearHolidayChoiceActivity.class)
                .putExtra("coords", coords.get(i)));
    }
}