package pl.mworld.schooltimer;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

public class DzwonkiSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dzwonki_settings);
        SharedPreferences mShared = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mSharedEditor = mShared.edit();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

    }

    private void textSpinnerSetter(Spinner spinner){
        TextView textView = new TextView(this);
        textView.setText("Poniedziałek");
        spinner.addView(textView);
    }
}
