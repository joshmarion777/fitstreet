package affle.com.fitstreet.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by root on 6/9/16.
 * Class used to show Toast message
 */
public class ToastUtils {

    /**
     * Method to show long toast
     *
     * @param context  context to show toast
     * @param strResId resource id of the string to display
     */
    public static void showLongToast(Context context, int strResId) {
        Toast.makeText(context, strResId, Toast.LENGTH_LONG).show();
    }

    /**
     * Method to show short toast
     *
     * @param context  context to show toast
     * @param strResId resource id of the string to display
     */
    public static void showShortToast(Context context, int strResId) {
        Toast.makeText(context, strResId, Toast.LENGTH_LONG).show();
    }

    /**
     * Method to show long toast
     *
     * @param context context to show toast
     * @param str     string to display
     */
    public static void showLongToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    /**
     * Method to show short toast
     *
     * @param context context to show toast
     * @param str     string to display
     */
    public static void showShortToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
}
