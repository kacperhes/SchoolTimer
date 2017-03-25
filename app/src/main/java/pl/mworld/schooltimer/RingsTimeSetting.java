package pl.mworld.schooltimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RingsTimeSetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rings_time_setting);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearRingsTime);

        for(int i = 1; i < 31; i++){
            linearLayout.addView(createTextView("Dzwonek: " + i, i));
        }

    }

    private TextView createTextView(String text, int i){
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        //TODO onClick into specifying times
        return textView;
    }
}
