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

/**
 * @author Hiosdra
 * TODO Edittext is too short
 * TODO Validation of ringtimes(next ring time must be greater than previous)
 * TODO Add actual time
 */
public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private long actualRingChanged = 1; // Number of actually changed ring(number in R.id.spinner)
    private EditText editText; // Ring time edittext
    private SharedPreferences.Editor mSharedEditor; // Editor of SharedPreferences
    private SharedPreferences mShared; // Instance of SharedPreferences

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
        editText = (EditText) findViewById(R.id.editText);

        // Getting Button instance of acceptation editing ringtime and setting listener
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener( view -> {
            // Put time edited into actual ring
            mSharedEditor.putLong(Long.valueOf(actualRingChanged).toString(), Long.valueOf(editText.getText().toString())).apply();

            // Toast with done
            Toast.makeText(this, R.string.applied, Toast.LENGTH_SHORT).show();

            setEdittextText();});

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
        actualRingChanged = l + 1;

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
        String actualRingChangedString = Long.valueOf(mShared.getLong(Long.valueOf(actualRingChanged).toString(), 0)).toString();

        // Set edittext text of actual ring
        editText.setText(actualRingChangedString);
    }
}
