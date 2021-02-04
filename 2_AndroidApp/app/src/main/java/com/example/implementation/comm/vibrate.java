package com.example.implementation.comm;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Vibrator;

import androidx.core.app.ActivityCompat;
/*
 * @Author: Qiushi Wang
 * Description:
 * function to vibrate
 * */
public class vibrate {

    public static void vib(Activity activity){
        Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(300);
    }

    public static void checkVibrationPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.VIBRATE},1);
        }
    }
}
