package fyp.p4072699.holidayplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String databaseName = "HolidayPlanner.db";

    //  Holiday Table Information
    private static final String holidayTableName = "Holiday";
    private static final String colID = "_id";
    private static final String colDateFrom = "date_from";
    private static final String colDateTo = "date_to";
    private static final String colLocation = "location";

    private static String createHolidayTable = "CREATE TABLE "
            + holidayTableName
            + " (" + colID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + colDateFrom + " TEXT, "
            + colDateTo + " TEXT, "
            + colLocation + " TEXT);";

    public DatabaseHandler(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(createHolidayTable);
        Log.d("Database: ", "Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addHoliday(Holiday h) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(colLocation, h.getLocation());
        cv.put(colDateFrom, h.getDateFrom());
        cv.put(colDateTo, h.getDateTo());

        database.insert(holidayTableName, null, cv);
        database.close();
        Log.d("Database: ", "Holiday Added");
    }

    public List<Holiday> getHolidays() {
        List<Holiday> hol = new ArrayList<Holiday>();
        SQLiteDatabase d = this.getReadableDatabase();

        String query = "SELECT * FROM " + holidayTableName;
        Cursor c = d.rawQuery(query, new String[]{});

        if (c.moveToFirst()) {
            int l = c.getColumnIndex(colLocation);
            int to = c.getColumnIndex(colDateTo);
            int from = c.getColumnIndex(colDateFrom);
            do {
                Holiday h = new Holiday(
                        c.getString(l),
                        c.getString(to),
                        c.getString(from)
                );
                hol.add(h);
            } while (c.moveToNext());
        }
        return hol;
    }
}
