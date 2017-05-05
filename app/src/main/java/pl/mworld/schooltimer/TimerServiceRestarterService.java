package pl.mworld.schooltimer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TimerServiceRestarterService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();

        stopService(new Intent(getApplicationContext(), TimerService.class));
        startService(new Intent(getApplicationContext(), TimerService.class));
    }

    public TimerServiceRestarterService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
