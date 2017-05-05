package pl.mworld.schooltimer;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

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
        Calendar thatMoment = GregorianCalendar.getInstance();
        long sinceMidnight = thatMoment.get(Calendar.SECOND)
                + (thatMoment.get(Calendar.MINUTE) * 60) + (thatMoment.get(Calendar.HOUR_OF_DAY) * 3600);//todo test
        //todo probably good
        //Log.e("TAG", String.valueOf(sinceMidnight) + String.valueOf(thatMoment.get(Calendar.SECOND)) + String.valueOf(thatMoment.get(Calendar.MINUTE)) + String.valueOf(thatMoment.get(Calendar.HOUR_OF_DAY)));
        //Log.e("TAG", String.valueOf(sinceMidnight));

        int actualRing = 0;
        while(true) {
            if(filteredRingList.size() >= actualRing) {
                if (filteredRingList.get(actualRing) >= sinceMidnight) {
                    break;
                }
                else {
                    actualRing++;
                    }
            }
            else {
                //todo what if there isn`t any ring today anymore
                break;
            }
        }

        mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.drawable.ic_notify)
                .setContentTitle(getString(R.string.remaining_time_to_ring))
                .setContentText("")
                .setAutoCancel(false)
                .setOngoing(true)
                .setWhen(0);
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        new CountDownTimer((filteredRingList.get(actualRing) - sinceMidnight)*1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mBuilder.setContentText(format(millisUntilFinished));
                mManager.notify(1, mBuilder.build());
            }

            @Override
            public void onFinish() {
                mBuilder.setContentText(String.valueOf(getString(R.string.ring_is_ringing)));//todo string
                mManager.notify(1, mBuilder.build());
            }
        }.start();

    }

    String format(long l) {
        l = l/1000;
        String s;
        s = String.valueOf(l/3600) + ":";//HH
        Long temp = l/3600;
        temp = temp * 3600;
        l = l - temp;
        s = s + String.valueOf(l/60) + ":";//mm
        temp = l/60;
        temp = temp * 60;
        l = l - temp;
        s = s + String.valueOf(l);//ss
        return s;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
