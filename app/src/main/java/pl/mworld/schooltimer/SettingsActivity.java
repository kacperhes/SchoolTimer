package pl.mworld.schooltimer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
 * TODO Add actual time
 * todo logo
 */
public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private long numberOfActualRingChanged = 1;
    private EditText editRingTimeEditText;
    private SharedPreferences.Editor mSharedEditor;
    private SharedPreferences mShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Getting SharedPreferences instance & editor
        mShared = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedEditor = mShared.edit();

        // Getting Spinner instance of ring number spinner and setting listener
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        // Getting EditText instance of ring time editor
        editRingTimeEditText = (EditText) findViewById(R.id.editText);

        // Getting Button instance of acceptation editing ringtime and setting listener
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener( view -> {
            // Validation
            if (!Long.valueOf(editRingTimeEditText.getText().toString()).equals(1) && editRingTimeEditText.getText().toString() != null) {
                if(mShared.getLong(Long.toString(numberOfActualRingChanged - 1), 0) < Long.valueOf(editRingTimeEditText.getText().toString())) {
                    pushEdittextChanges();
                }
                else {
                    Toast.makeText(this, R.string.validate_ring_text, Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(this, R.string.validate_ring_text, Toast.LENGTH_LONG).show();
            }
            });

        // Getting Spinner Drop down elements
        List<String> ringList = new ArrayList<>();
        for(int i = 1; i < 31; i++) {
            ringList.add(Integer.toString(i));
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ringList);

        // Setting drop down layout style -> list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Setting actual edited ring
        numberOfActualRingChanged = l + 1;

        setEdittextText();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        setEdittextText();
    }

    /**
     * Sets Edittext(editing ringtime) into actually edited ringtime
     */
    private void setEdittextText() {
        // Get string of actual changed ring -> set text of edittext
        String actualRingChangedString = Long.valueOf(mShared.getLong(Long.valueOf(numberOfActualRingChanged).toString(), 0)).toString();

        // Set edittext text of actual ring
        editRingTimeEditText.setText(actualRingChangedString);
    }

    /**
     * Updates mShared with changes, shows toast with "applied" massage, updates edittext
     */
    private void pushEdittextChanges () {
        // Put time edited into actual ring
        mSharedEditor.putLong(Long.valueOf(numberOfActualRingChanged).toString(), Long.valueOf(editRingTimeEditText.getText().toString())).apply();

        // Toast with done
        Toast.makeText(this, R.string.applied, Toast.LENGTH_SHORT).show();

        setEdittextText();
    }
}
