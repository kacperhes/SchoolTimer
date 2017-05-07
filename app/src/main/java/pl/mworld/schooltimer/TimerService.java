package pl.mworld.schooltimer;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
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

//todo extend -> stop notification and progress bar
//todo what if weekend
public class TimerService extends Service {
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mManager;
    private List<Long> filteredRingList;
    static private boolean isRunning = false;
    public TimerService() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mManager.cancel(1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();

        // Getting list of rings
        SharedPreferences mShared = PreferenceManager.getDefaultSharedPreferences(context);
        List<Long> ringlist = new ArrayList<>(20);
        filteredRingList = new ArrayList<>();
        for(long l = 1; l < 21; l++) {
            ringlist.add(mShared.getLong(Long.toString(l), 0));
        }
        for (long l : ringlist) {
            if (l != 0) filteredRingList.add(l);
        }

        // Setting up notification
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_notify)
                .setContentTitle(getString(R.string.remaining_time_to_ring))
                .setContentText("")
                .setAutoCancel(false)
                .setOngoing(true)
                .setWhen(0);
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        runTimer();
    }

    void runTimer() {
        // Getting what is the time since midnight
        Calendar thatMoment = GregorianCalendar.getInstance();
        long sinceMidnight = thatMoment.get(Calendar.SECOND) +
                (thatMoment.get(Calendar.MINUTE) * 60) + (thatMoment.get(Calendar.HOUR_OF_DAY) * 3600);

        // Calculation what ring is next
        int actualRing = 0;
        long toNextRing;
        while(true) {
            if(filteredRingList.size() > actualRing) {
                if (filteredRingList.get(actualRing) >= sinceMidnight) {
                    toNextRing = filteredRingList.get(actualRing) - sinceMidnight;
                    break;
                }
                else actualRing++;
            }
            else {
                toNextRing = filteredRingList.get(0) + (86400 - sinceMidnight);
                break;
            }
        }

        // Running timer
        isRunning = true;
        new CountDownTimer(toNextRing*1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mBuilder.setContentText(format(millisUntilFinished));
                mManager.notify(1, mBuilder.build());
            }

            @Override
            public void onFinish() {
                mBuilder.setContentText(getString(R.string.ring_is_ringing));
                mManager.notify(1, mBuilder.build());
                isRunning = false;
                restartTimer();
            }
        }.start();
    }

    /**
     * @param l Time to calculate
     * @return Time in preset "10h 15min 54sec"
     */
    String format(long l) {
        l = l/1000;
        String s;
        s = String.valueOf(l/3600) + "h ";//HH
        Long temp = l/3600;
        temp = temp * 3600;
        l = l - temp;
        s = s + String.valueOf(l/60) + "min ";//mm
        temp = l/60;
        temp = temp * 60;
        l = l - temp;
        s = s + String.valueOf(l) + "sec";//ss
        return s;
    }

    /**
     * Restarts timer
     */
    void restartTimer() {
        runTimer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    static public boolean isRunning() {
        return isRunning;
    }
}
