package pl.mworld.schooltimer;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by ROOT on 2017-03-23.
 */

public class Timer extends Service{
    private SharedPreferences mShared;
    private long timeSinceMidnight;
    private TextView timerView;
    private long[] ringTimes;
    private int numberOfActualRing;
    private long timeToNextRing;
    private boolean isNotificationOn;
    private boolean isWorking;

    public Timer(){
        timeSinceMidnight = new GregorianCalendar().getTimeInMillis();
        timerView = MainActivity.getTimerView();
        ringTimes = mSharedGetter();
        isNotificationOn = mShared.getBoolean("isNotificationOn", false);
        isWorking = false;

        start();
    }
    private void start(){
        isWorking = true;
        actualRing();
        timeToNextRingSetter();

    }
    private void actualRing(){
        for(int i = 0; ; i++){
            if(ringTimes[i] >= timeSinceMidnight){
                numberOfActualRing = i-1;
                break;
            }
        }
    }
    private void timeToNextRingSetter(){
        timeToNextRing = ringTimes[numberOfActualRing] - timeSinceMidnight;
    }
    private void timer(long time){
        new CountDownTimer(time, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerView.setText(String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % TimeUnit.MINUTES.toSeconds(1)));
            }

            @Override
            public void onFinish() {
                timerView.setText(R.string.ringIsRinging);
            }
        };
    }
    public void stop(){

    }

    /**
     * Gets table of ringtimes from SharedPreferances with key formula "ring<number starting 0>".
     * @return Table of ringtimes.
     */
    private long[] mSharedGetter(){
        long[] times = new long[30];
        times[0] = mShared.getLong("ring0", 0);
        times[1] = mShared.getLong("ring1", 0);
        times[2] = mShared.getLong("ring2", 0);
        times[3] = mShared.getLong("ring3", 0);
        times[4] = mShared.getLong("ring4", 0);
        times[5] = mShared.getLong("ring5", 0);
        times[6] = mShared.getLong("ring6", 0);
        times[7] = mShared.getLong("ring7", 0);
        times[8] = mShared.getLong("ring8", 0);
        times[9] = mShared.getLong("ring9", 0);
        times[10] = mShared.getLong("ring10", 0);
        times[11] = mShared.getLong("ring11", 0);
        times[12] = mShared.getLong("ring12", 0);
        times[13] = mShared.getLong("ring13", 0);
        times[14] = mShared.getLong("ring14", 0);
        times[15] = mShared.getLong("ring15", 0);
        times[16] = mShared.getLong("ring16", 0);
        times[17] = mShared.getLong("ring17", 0);
        times[18] = mShared.getLong("ring18", 0);
        times[19] = mShared.getLong("ring19", 0);
        times[20] = mShared.getLong("ring20", 0);
        times[21] = mShared.getLong("ring21", 0);
        times[22] = mShared.getLong("ring22", 0);
        times[23] = mShared.getLong("ring23", 0);
        times[24] = mShared.getLong("ring24", 0);
        times[25] = mShared.getLong("ring25", 0);
        times[26] = mShared.getLong("ring26", 0);
        times[27] = mShared.getLong("ring27", 0);
        times[28] = mShared.getLong("ring28", 0);
        times[29] = mShared.getLong("ring29", 0);
        return times;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
