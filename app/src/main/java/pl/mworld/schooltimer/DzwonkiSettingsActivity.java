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

public class DzwonkiSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    long actualRingChanged = 1;
    EditText editText;
    SharedPreferences.Editor mSharedEditor;
    SharedPreferences mShared;
    Spinner spinner;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dzwonki_settings);

        mShared = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedEditor = mShared.edit();

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        editText = (EditText) findViewById(R.id.editText);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        // Spinner Drop down elements
        List<String> ringList = new ArrayList<String>();
        for(int i = 1; i < 31; i++) {
            ringList.add(new Integer(i).toString());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ringList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Set actual edited ring
        actualRingChanged = ++l;

        // Set time in edittext
        //TODO Doesn`t work
        //editText.setText(mShared.getString(Integer.valueOf(actualRingChanged).toString(),"0"));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        // Put time edited into actual ring
        if(mSharedEditor.putLong(Long.valueOf(actualRingChanged).toString(), Long.valueOf(editText.getText().toString())).commit()) {
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        }
    }
}
