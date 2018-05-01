package fyp.p4072699.holidayplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ButtonController extends AppCompatActivity implements View.OnClickListener {
    public void buttonNext() {
        Button next = findViewById(R.id.button_next);
        next.setOnClickListener(this);
    }

    public void buttonCancel() {
        Button cancel = findViewById(R.id.button_cancel);
        cancel.setOnClickListener(this);
    }

    public void buttonSave() {
        Button save = findViewById(R.id.button_save);
        save.setOnClickListener(this);
    }

    public void buttonReturn() {
        Button retur = findViewById(R.id.button_return);
        retur.setOnClickListener(this);
    }

    public void buttonReset() {
        Button reset = findViewById(R.id.button_reset);
        reset.setOnClickListener(this);
    }

    public void buttonAddHoliday() {
        Button addHoliday = findViewById(R.id.button_addholiday);
        addHoliday.setOnClickListener(this);
    }

    public void buttonMyHoliday() {
        Button myHoliday = findViewById(R.id.button_viewholidays);
        myHoliday.setOnClickListener(this);
    }

    public void buttonDestinations() {
        Button destinations = findViewById(R.id.button_destinations);
        destinations.setOnClickListener(this);
    }

    public void buttonLogin() {
        Button login = findViewById(R.id.button_login);
        login.setOnClickListener(this);
    }

    public void buttonSignUp() {
        Button signup = findViewById(R.id.button_signup);
        signup.setOnClickListener(this);
    }

    public void buttonForgot() {
        Button forgot = findViewById(R.id.button_forgot);
        forgot.setOnClickListener(this);
    }

    public void buttonChangeEmail() {
        Button email = findViewById(R.id.button_changeemail);
        email.setOnClickListener(this);
    }

    public void buttonChangePassword() {
        Button password = findViewById(R.id.button_changepassword);
        password.setOnClickListener(this);
    }

    public void buttonSignOut() {
        Button signOut = findViewById(R.id.button_signout);
        signOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_addholiday:
                startActivity(new Intent(getApplicationContext(), AddStartDateActivity.class));
                break;
            case R.id.button_viewholidays:
                startActivity(new Intent(getApplicationContext(), MyHolidaysActivity.class));
                break;
            case R.id.button_destinations:
                startActivity(new Intent(getApplicationContext(), DestinationsActivity.class));
                break;
        }
    }
}
