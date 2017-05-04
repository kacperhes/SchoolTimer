package pl.mworld.schooltimer;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

//todo end this
//todo extend -> stop notification
public class TimerService extends Service {
    NotificationCompat.Builder mBuilder;
    NotificationManager mManager;
    public TimerService() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mManager.cancel(1);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences mShared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        List<Long> ringlist = new ArrayList<>(20);
        List<Long> filteredRingList = new ArrayList<>();
        for(long l = 1; l < 21; l++) {
            ringlist.add(mShared.getLong(Long.toString(l), 0));
        }
        for (long l : ringlist) {
            if (l != 0) filteredRingList.add(l);
        }
        long sinceMidnight = GregorianCalendar.getInstance().get(Calendar.SECOND);//todo test
        Log.e("TAG", String.valueOf(sinceMidnight));

        int actualRing = 0;
        while(true) {
            if (filteredRingList.get(actualRing) >= sinceMidnight) {
                break;
            }
            else {
                actualRing++;
            }
        }

        mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.drawable.ic_notify)
                .setContentTitle("Remaining time")
                .setContentText("")
                .setAutoCancel(false)
                .setOngoing(true)
                .setWhen(0);

        new CountDownTimer(filteredRingList.get(actualRing) - sinceMidnight, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                push(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                push(0);
            }
        }.start();

    }

    void push(long l){
        mBuilder.setContentText(String.valueOf(l));
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mManager.notify(1, mBuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
