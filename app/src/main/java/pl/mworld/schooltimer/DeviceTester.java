package pl.mworld.schooltimer;

import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;
// Copyright 2017 Hiosdra
//
//         Licensed under the Apache License, Version 2.0 (the "License");
//         you may not use this file except in compliance with the License.
//         You may obtain a copy of the License at
//
//         http://www.apache.org/licenses/LICENSE-2.0
//
//         Unless required by applicable law or agreed to in writing, software
//         distributed under the License is distributed on an "AS IS" BASIS,
//         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//         See the License for the specific language governing permissions and
//         limitations under the License.

/**
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
