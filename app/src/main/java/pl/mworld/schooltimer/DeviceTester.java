package pl.mworld.schooltimer;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by ROOT on 2017-04-14.
 */

final class DeviceTester {
    static boolean isHiosdra(Context context) {
        if(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID).equals("62c0d9704e82021")) return true;
        else return false;
    }
}
