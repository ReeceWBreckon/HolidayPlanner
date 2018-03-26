package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyHolidaysActivity extends AppCompatActivity implements View.OnClickListener {
    private Button home, add;
    private ListView holidayLv;
    private List<Holiday> holidays;
    private DatabaseHandler dbh;
    private ArrayAdapter ad;
    private String[] hol = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_holidays);

        home = findViewById(R.id.button_returnhome);
        add = findViewById(R.id.button_addholiday);
        holidayLv = findViewById(R.id.listView_holidays);
        dbh = new DatabaseHandler(this);
        holidays = new ArrayList<Holiday>();
        holidays = dbh.getHolidays();

        for (Holiday h : holidays) {
            hol = Arrays.copyOf(hol, hol.length + 1);
            hol[hol.length - 1] = "Location: " + h.getLocation()
                    + "\nDate From: " + h.getDateFrom()
                    + "\nDate To: " + h.getDateTo();
        }

        ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hol);
        holidayLv.setAdapter(ad);

        home.setOnClickListener(this);
        add.setOnClickListener(this);
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
}
