package pl.mworld.schooltimer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import cn.iwgang.countdownview.CountdownView;
// Copyright 2017 Hiosdra
//
//         Licensed under the Apache License, Version 2.0 (the "License");
//         you may not use this file except in compliance with the License.
//         You may obtain a copy of the License at
//
//         http://www.apache.org/licenses/LICENSE-2.0
//
//         Unless required by applicable law or agreed to in writing, software
//         distributed under the License is distributed on an "AS IS" BASIS,
//         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//         See the License for the specific language governing permissions and
//         limitations under the License.

/**
 * Main screen of app
 */
public class MainActivity extends AppCompatActivity {
    SharedPreferences mShared;
    TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting instances
       // timerTextView = (TextView) findViewById(R.id.timerTextView);
        mShared = PreferenceManager.getDefaultSharedPreferences(this);
        final Context context = getApplicationContext();

        // Setting up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // CountdownView
        CountdownView countdownView = (CountdownView) findViewById(R.id.CountdownView);
        countdownView.start(10000);
        countdownView.setOnCountdownEndListener(countdownView1 -> countdownView1.start(5025));

        // Starting timer in notification
       // if(!TimerService.isRunning()) startService(new Intent(this, TimerService.class));

        // Setting up FloatingActionButton for debugging
       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       //fab.hide();
        //if(!DeviceTester.isDev(this)) fab.hide();
        //else {
            fab.setOnClickListener(view -> {
                startService(new Intent(this, TimerService.class));
            });
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
