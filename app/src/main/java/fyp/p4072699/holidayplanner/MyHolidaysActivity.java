package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class MyHolidaysActivity extends DrawerNavigation implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Button home, add;
    private ListView holidayLv;
    private ArrayAdapter ad;
    private String userId;
    private ArrayList<String> holList, coords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_holidays);
        getDrawer();
        setTitle(R.string.my_holidays);
        connectDisplay();
        setupListView();
        getHolidays("0");
        setListeners();
    }

    protected void connectDisplay() {
        //Connect to the display
        home = findViewById(R.id.button_returnhome);
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
        home.setOnClickListener(this);
        add.setOnClickListener(this);
        holidayLv.setOnItemClickListener(this);
    }

    public void getHolidays(final String si) {
        if (getAuth().getCurrentUser() != null) {
            userId = getAuth().getCurrentUser().getUid();
        }
        getDatabase().getReference().child("holidays").child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.child("completed").getValue(String.class).equals(si)) {
                    String m = "Location: " + dataSnapshot.child("location").getValue(String.class) +
                            "\nDate From: " + dataSnapshot.child("dateFrom").getValue(String.class) +
                            "\nDate To: " + dataSnapshot.child("dateTo").getValue(String.class);
                    String c = dataSnapshot.child("lat").getValue(double.class).toString() + "," + dataSnapshot.child("lon").getValue(double.class).toString();
                    coords.add(c);
                    holList.add(m);
                    ad.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_returnhome:
                startActivity(new Intent(MyHolidaysActivity.this, HomeActivity.class));
                break;
            case R.id.button_addholiday:
                startActivity(new Intent(MyHolidaysActivity.this, AddStartDateActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(MyHolidaysActivity.this, NearHolidayChoiceActivity.class)
                .putExtra("coords", coords.get(i)));
    }
}
