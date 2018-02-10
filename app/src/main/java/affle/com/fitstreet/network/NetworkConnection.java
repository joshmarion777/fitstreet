package affle.com.fitstreet.network;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import affle.com.fitstreet.R;

/**
 * Created by root on 6/14/16.
 */
public class NetworkConnection {

    /**
     * This method is used to check whether internet is on or off
     *
     * @return boolean
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * This method is used to show Internet alert dialog
     *
     * @return void
     */
    public static void showInternetAlertDialog(Context context) {
        new AlertDialog.Builder(context).setMessage(R.string.err_network_connection).
                setNeutralButton(R.string.txt_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }
}
