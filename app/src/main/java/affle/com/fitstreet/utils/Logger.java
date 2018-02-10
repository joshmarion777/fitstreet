package affle.com.fitstreet.utils;

import android.util.Log;

public class Logger {

    public static boolean logstatus = true;
    public static String TAG = "FitStreet";

    private Logger() {
    }

    public static void error(String s, Throwable throwable) {
        if (logstatus)
            Log.e(TAG, s, throwable);
    }

    public static void e(String s) {
        if (logstatus)
            Log.e(TAG, "" + s);
    }

    public static void w(String s) {
        if (logstatus)
            Log.w(TAG, "" + s);
    }

    public static void i(String s) {
        if (logstatus)
            Log.i(TAG, "" + s);

    }

    public static void v(String s) {
        if (logstatus)
            Log.v(TAG, "" + s);
    }
}
