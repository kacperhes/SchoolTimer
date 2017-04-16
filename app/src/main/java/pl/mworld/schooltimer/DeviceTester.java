package pl.mworld.schooltimer;

import android.content.Context;
import android.provider.Settings;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ROOT on 2017-04-14.
 */

class DeviceTester {
    static private ArrayList<String> devList = new ArrayList<>();
    static {
        devList.add("62c0d9704e82021");
        //Add here developers
    }
    @Deprecated
    static boolean isHiosdra(Context context) {
        return getAndroidId(context).equals("62c0d9704e82021");
    }

    static boolean isDev(Context context) {
        return devList.contains(getAndroidId(context));
    }

    static String getAndroidId(Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    static void getAndroidIdInToast(Context context) {
        Toast.makeText(context, getAndroidId(context), Toast.LENGTH_SHORT).show();
    }
}
