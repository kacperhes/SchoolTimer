package pl.mworld.schooltimer;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

//todo end this
//todo extend -> stop notification and progress bar
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

        mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.drawable.ic_notify)
                .setContentTitle(getString(R.string.remaining_time_to_ring))
                .setContentText("")
                .setAutoCancel(false)
                .setOngoing(true)
                .setWhen(0);
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Calendar thatMoment = GregorianCalendar.getInstance();
        long sinceMidnight = thatMoment.get(Calendar.SECOND) +
                (thatMoment.get(Calendar.MINUTE) * 60) + (thatMoment.get(Calendar.HOUR_OF_DAY) * 3600);

        boolean canRun = false;
        int actualRing = 0;
        while(true) {
            if(filteredRingList.size() > actualRing) {
                if (filteredRingList.get(actualRing) >= sinceMidnight) {
                    canRun = true;
                    break;
                }
                else {
                    actualRing++;
                    }
            }
            else {
//                new Thread(() -> {
//                    mBuilder.setContentText("There will be no more rings today"); //todo make this string static
//                    mManager.notify(1, mBuilder.build());
//                    try {
//                        wait(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    mManager.cancel(1);
//                }).run();
                break;
            }
        }

        //todo put into if statement above
        if(canRun) {
            new CountDownTimer((filteredRingList.get(actualRing) - sinceMidnight)*1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    mBuilder.setContentText(format(millisUntilFinished));
                    mManager.notify(1, mBuilder.build());
                }

                @Override
                public void onFinish() {
                    mBuilder.setContentText(getString(R.string.ring_is_ringing));
                    mManager.notify(1, mBuilder.build());
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            /*try {
                                wait(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }*/
                            //this.wait(3000);
                            startService(new Intent(getApplicationContext(), TimerServiceRestarterService.class));
                            return null;
                        }
                    }.execute();
                }
            }.start();
        }


    }

    String format(long l) {
        l = l/1000;
        String s;
        s = String.valueOf(l/3600) + getString(R.string.hour_notification);//HH
        Long temp = l/3600;
        temp = temp * 3600;
        l = l - temp;
        s = s + String.valueOf(l/60) + getString(R.string.minute_notification);//mm
        temp = l/60;
        temp = temp * 60;
        l = l - temp;
        s = s + String.valueOf(l) + getString(R.string.seconds_notification);//ss
        return s;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
