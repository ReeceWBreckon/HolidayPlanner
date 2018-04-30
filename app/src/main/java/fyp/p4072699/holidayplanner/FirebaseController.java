package fyp.p4072699.holidayplanner;

import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FirebaseController extends MapController implements ChildEventListener {
    private Calendar calendar = Calendar.getInstance();

    public Calendar getCalendar() {
        return calendar;
    }

    public FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance();
    }

    public FirebaseFirestore getFireDB() {
        return FirebaseFirestore.getInstance();
    }

    public FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    //Used to send messages to the user
    public void sendToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    //Saves the selected country to firebase for trending
    protected void saveCountry(final String s) {
        final Map<String, Object> hits = new HashMap<>();
        getFireDB().collection(getString(R.string.hot_country)).document(s).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Map<String, Object> data = documentSnapshot.getData();
                    Long i = (Long) data.get(getString(R.string.hits));
                    hits.clear();
                    hits.put(getString(R.string.hits), i + 1);
                    getFireDB().collection(getString(R.string.hot_country)).document(s).update(hits);
                } else {
                    hits.put(getString(R.string.hits), 0);
                    getFireDB().collection(getString(R.string.hot_country)).document(s).set(hits);
                }
            }
        });
    }


    //Sets a listener for the user holidays
    protected void getHolidays() {
        String userId = null;
        if (getAuth().getCurrentUser() != null) {
            userId = getAuth().getCurrentUser().getUid();
            getDatabase().getReference().child(getString(R.string.holidays)).child(userId).addChildEventListener(this);
        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        checkHolidayCompleted(dataSnapshot);
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

    //Checks whether a holiday is finished
    protected void checkHolidayCompleted(DataSnapshot d) {
        int year = Integer.valueOf(d.child(getString(R.string.to_year)).getValue(String.class));
        int month = Integer.valueOf(d.child(getString(R.string.to_month)).getValue(String.class));
        int day = Integer.valueOf(d.child(getString(R.string.to_day)).getValue(String.class));
        if (d.child(getString(R.string.completed)).getValue(String.class).equals(getString(R.string.zero))) {
            if (checkDate(year, calendar.get(Calendar.YEAR))
                    && checkDate(month, calendar.get(Calendar.MONTH) + 1)
                    && checkDay(day, calendar.get(Calendar.DAY_OF_MONTH))) {
                // Call the update
                updateHolidayCompleted(d.getKey());
            }
        }
    }

    //If a holiday is finished update it
    protected void updateHolidayCompleted(String s) {
        String userId = null;

        if (getAuth().getCurrentUser() != null) {
            userId = getAuth().getCurrentUser().getUid();
        }

        final Map<String, Object> completed = new HashMap<>();
        completed.put(getString(R.string.completed), "1");

        DatabaseReference fDB = getDatabase().getReference(getString(R.string.holidays)).child(userId).child(s);
        fDB.updateChildren(completed);
    }

    //Used to check dates
    protected boolean checkDate(int y, int yy) {
        return y <= yy;
    }

    protected boolean checkDay(int y, int yy) {
        return y < yy;
    }
}
