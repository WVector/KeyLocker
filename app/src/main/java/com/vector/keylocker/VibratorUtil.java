package com.vector.keylocker;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Vector on 2016/7/28 0028.
 */
public class VibratorUtil {
    private static volatile Vibrator sVibrator;

    public static void vibrateDot(Context context) {
        getVibrator(context).vibrate(100);
    }

    public static void vibrate(Context context, long milliseconds) {
        getVibrator(context).vibrate(milliseconds);
    }

    public static void vibrate(Context context, long[] pattern, int repeat) {
        getVibrator(context).vibrate(pattern, repeat);
    }

    public static void vibratePattern(Context context) {
        long[] pattern = {100, 200, 100, 200};
        getVibrator(context).vibrate(pattern, -1);
    }

    private static Vibrator getVibrator(Context context) {

        Vibrator tmp = sVibrator;


        if (tmp == null) {

            synchronized (VibratorUtil.class) {

                tmp = sVibrator;

                if (tmp == null) {

                    tmp = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

                    sVibrator = tmp;
                }

            }
        }
        return tmp;
    }
}
