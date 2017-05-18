package pl.mworld.schooltimer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ikovac.timepickerwithseconds.MyTimePickerDialog;

import java.util.ArrayList;
import java.util.List;
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
 * todo validation
 * todo what if ringtime = 0
 * todo weekends
 * todo make devicetester as library
 * todo countdownview
 */
public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private long numberOfActualRingChanged = 1;
    private SharedPreferences.Editor mSharedEditor;
    private SharedPreferences mShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Getting instances
        mShared = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedEditor = mShared.edit();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Button button = (Button) findViewById(R.id.button);

        // Setting up listeners
        spinner.setOnItemSelectedListener(this);
        button.setOnClickListener( view -> {
            List<Integer> list = formatActualRingChangedTimeIntoArrayList();
            MyTimePickerDialog mTimePicker = new MyTimePickerDialog(this, (view1, hourOfDay, minute, seconds)
                    -> mSharedEditor.putLong(Long.valueOf(numberOfActualRingChanged).toString(),
                    (long) ((hourOfDay * 3600) + (minute * 60) + seconds)).apply(),
                    list.get(0), list.get(1), list.get(2), true);
            mTimePicker.show();
            });

        // Getting Spinner Drop down elements
        List<String> ringList = new ArrayList<>();
        for(int i = 1; i < 31; i++) {
            ringList.add(Integer.toString(i));
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ringList);
        // Setting drop down layout style -> list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Setting actual edited ring
        numberOfActualRingChanged = l;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private ArrayList<Integer> formatActualRingChangedTimeIntoArrayList() {
        long l = mShared.getLong(Long.valueOf(numberOfActualRingChanged).toString(), 0);
        ArrayList<Integer> list = new ArrayList<>(3);
        long minus = l/3600;
        list.add(0, (int) (minus)); //HH
        l = l - (minus * 3600);
        minus = l/60;
        list.add(1, (int) (minus)); //mm
        l = l - (minus * 60);
        list.add(2, (int) l); //ss
        return list;
    }
}
