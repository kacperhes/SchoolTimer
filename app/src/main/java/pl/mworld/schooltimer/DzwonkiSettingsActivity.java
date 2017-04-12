package pl.mworld.schooltimer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class DzwonkiSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int actualRingChanged = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dzwonki_settings);

        SharedPreferences mShared = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mSharedEditor = mShared.edit();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        EditText editText = (EditText) findViewById(R.id.editText);
        //TODO sth when text is done edited

        // Spinner Drop down elements
        List<String> ringList = new ArrayList<String>();
        for(int i = 1; i < 31; i++) {
            ringList.add(new Integer(i).toString());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ringList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        actualRingChanged = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
