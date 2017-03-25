package pl.mworld.schooltimer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static private TextView timerView;
    private SharedPreferences mShared;

    static public TextView getTimerView() {
        return timerView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerView = (TextView) findViewById(R.id.timerText);

        startService(new Intent(this, Timer.class));
    }

    @Override
    protected void onStop() {
        if (mShared.getBoolean("isNotificationOn", false) == true) {
            stopService(new Intent(this, Timer.class));
        }
        super.onStop();
    }

    void floatingSettings(View view){
        startActivity(new Intent(this, SettingsActivity.class));
    }
}