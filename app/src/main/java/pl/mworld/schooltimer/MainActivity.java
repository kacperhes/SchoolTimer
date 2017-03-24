package pl.mworld.schooltimer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences mShared;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView timerView = (TextView) findViewById(R.id.timerText);

        timer = new Timer(timerView);
    }

    @Override
    protected void onStop() {
        if(mShared.getBoolean("isNotificationOn", false) == true){
            timer.stop();
        }
        super.onStop();
    }
}