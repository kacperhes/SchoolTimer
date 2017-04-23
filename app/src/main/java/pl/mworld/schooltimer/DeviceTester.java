package pl.mworld.schooltimer;

import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ROOT on 2017-04-14.
 * Identifies developer devices
 */

class DeviceTester {
    static private ArrayList<String> devList = new ArrayList<>();
    static {
        devList.add("62c0d9704e82021");
        //Add here developers
    }

    static boolean isDev(@NonNull Context context) {
        return devList.contains(getAndroidId(context));
    }

    static String getAndroidId(@NonNull Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    static void getAndroidIdInToast(@NonNull Context context) {
        Toast.makeText(context, getAndroidId(context), Toast.LENGTH_SHORT).show();
    }
}
