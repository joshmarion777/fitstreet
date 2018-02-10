package affle.com.fitstreet.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import affle.com.fitstreet.R;

/**
 * Created by root on 6/27/16.
 */
public class PermissionUtils {
    public static final int RC_REQUEST_PERMISSION = 105;


    /**
     * This method used to check the storage permission.
     *
     * @param activity
     */
    public static boolean hasReadGServicesPermission(final Activity activity) {
        int hasStoragePermission = ContextCompat.checkSelfPermission(activity, "com.google.android.providers.gsf.permission.READ_GSERVICES");
        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, "com.google.android.providers.gsf.permission.READ_GSERVICES")) {
                showMessageOKCancel(activity, activity.getString(R.string.per_allow_gservices),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity, new String[]{"com.google.android.providers.gsf.permission.READ_GSERVICES"}, RC_REQUEST_PERMISSION);
                            }
                        });
                return false;
            }
            ActivityCompat.requestPermissions(activity, new String[]{"com.google.android.providers.gsf.permission.READ_GSERVICES"}, RC_REQUEST_PERMISSION);
            return false;
        }
        return true;
    }

    /**
     * This method used to check the storage permission.
     *
     * @param activity
     */
    public static boolean hasStoragePermission(final Activity activity) {
        int hasStoragePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showMessageOKCancel(activity, activity.getString(R.string.per_allow_storage),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_REQUEST_PERMISSION);
                            }
                        });
                return false;
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_REQUEST_PERMISSION);
            return false;
        }
        return true;
    }

    private static void showMessageOKCancel(Context context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(R.string.txt_ok, okListener)
                .setNegativeButton(R.string.txt_cancel, null)
                .create()
                .show();
    }
}
