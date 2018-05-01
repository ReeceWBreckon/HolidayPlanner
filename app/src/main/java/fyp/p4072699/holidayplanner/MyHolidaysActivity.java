package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class MyHolidaysActivity extends NavController implements AdapterView.OnItemClickListener {
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

    //Connect to the display
    protected void connectDisplay() {
        buttonAddHoliday();
        holidayLv = findViewById(R.id.listView_holidays);
        holList = new ArrayList<String>();
        coords = new ArrayList<String>();
    }

    //Setup the list view and adapter
    protected void setupListView() {
        ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, holList);
        holidayLv.setAdapter(ad);
    }

    //Set the click listeners
    protected void setListeners() {
        holidayLv.setOnItemClickListener(this);
    }

    //When a holiday is loaded from firebase add to the holiday list and put coordinates in the coords list
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot.child(getString(R.string.completed)).getValue(String.class).equals(getString(R.string.zero))) {
            String to = dataSnapshot.child(getString(R.string.to_day)).getValue(String.class) + getString(R.string.slash)
                    + dataSnapshot.child(getString(R.string.to_month)).getValue(String.class) + getString(R.string.slash)
                    + dataSnapshot.child(getString(R.string.to_year)).getValue(String.class);
            String from = dataSnapshot.child(getString(R.string.from_day)).getValue(String.class) + getString(R.string.slash)
                    + dataSnapshot.child(getString(R.string.from_month)).getValue(String.class) + getString(R.string.slash)
                    + dataSnapshot.child(getString(R.string.from_year)).getValue(String.class);
            String m = getString(R.string.location_two_dots) + " " + dataSnapshot.child(getString(R.string.loc)).getValue(String.class) +
                    getString(R.string.new_line) + getString(R.string.date_from_two_dots) + " " + from +
                    getString(R.string.new_line) + getString(R.string.date_to_two_dots) + " " + to;
            String c = dataSnapshot.child(getString(R.string.lat)).getValue(double.class).toString() + getString(R.string.com) + dataSnapshot.child(getString(R.string.lon)).getValue(double.class).toString();
            coords.add(c);
            holList.add(m);
            ad.notifyDataSetChanged();
        }
    }

    //When a holiday is clicked load the choices
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(MyHolidaysActivity.this, NearHolidayChoiceActivity.class)
                .putExtra("coords", coords.get(i)));
    }
}