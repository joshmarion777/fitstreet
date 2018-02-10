package affle.com.fitstreet.appsanddevices.foursquare;

import android.app.Activity;
import android.content.Intent;

import com.foursquare.android.nativeoauth.FoursquareCancelException;
import com.foursquare.android.nativeoauth.FoursquareDenyException;
import com.foursquare.android.nativeoauth.FoursquareInvalidRequestException;
import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.FoursquareOAuthException;
import com.foursquare.android.nativeoauth.FoursquareUnsupportedVersionException;
import com.foursquare.android.nativeoauth.model.AccessTokenResponse;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;

import affle.com.fitstreet.ui.fragments.ConnectAppsAndDevicesFragment;
import affle.com.fitstreet.utils.ToastUtils;

/**
 * Created by guruchetan on 8/8/16.
 */
public class ManagerFourSquare {

    private static final String CLIENT_ID = "F41MWYYITRQJOPSJGB12IV4IV15LSJMVDXUSYGC2SMT3YAAK";
    private static final String CLIENT_SECRET = "XOULWSRXDWKBFFWWYEORBHIIANAZY14Q04Q53QTRG23WHUOY";
    private static final int REQUEST_CODE_FSQ_CONNECT = 1313;
    private static final int REQUEST_CODE_FSQ_TOKEN_EXCHANGE = 1212;
    private static ManagerFourSquare managerFourSquare;
    private final Activity activity;
    private final ConnectAppsAndDevicesFragment connectAppsAndDevicesFragment;

    private ManagerFourSquare(Activity activity, ConnectAppsAndDevicesFragment connectAppsAndDevicesFragment) {
        this.activity = activity;
        this.connectAppsAndDevicesFragment=connectAppsAndDevicesFragment;
    }

    public void connectFourSquare() {
//        TextUtils.isEmpty(token)
        // Start the native auth flow.
        Intent intent = FoursquareOAuth.getConnectIntent(connectAppsAndDevicesFragment.getActivity(), CLIENT_ID);

        // If the device does not have the Foursquare app installed, we'd
        // get an intent back that would open the Play Store for download.
        // Otherwise we start the auth flow.
        if (FoursquareOAuth.isPlayStoreIntent(intent)) {
            connectAppsAndDevicesFragment.startActivity(intent);
        } else {
            connectAppsAndDevicesFragment.startActivityForResult(intent, REQUEST_CODE_FSQ_CONNECT);
        }
    }

    public void onCompleteConnect(int resultCode, Intent data) {
        AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);
        Exception exception = codeResponse.getException();

        if (exception == null) {
            // Success.
            String code = codeResponse.getCode();
            performTokenExchange( code);

        } else {
            if (exception instanceof FoursquareCancelException) {
                // Cancel.
                ToastUtils.showShortToast(activity, "Canceled");

            } else if (exception instanceof FoursquareDenyException) {
                // Deny.
                ToastUtils.showShortToast(activity, "Denied");

            } else if (exception instanceof FoursquareOAuthException) {
                // OAuth error.
                String errorMessage = exception.getMessage();
                String errorCode = ((FoursquareOAuthException) exception).getErrorCode();
                ToastUtils.showShortToast(activity, errorMessage + " [" + errorCode + "]");

            } else if (exception instanceof FoursquareUnsupportedVersionException) {
                // Unsupported Fourquare app version on the device.
                ToastUtils.showShortToast(activity, exception.getMessage());

            } else if (exception instanceof FoursquareInvalidRequestException) {
                // Invalid request.
                ToastUtils.showShortToast(activity, exception.getMessage());

            } else {
                // Error.
                ToastUtils.showShortToast(activity, exception.getMessage());
            }
        }
    }

    /**
     * Exchange a code for an OAuth Token. Note that we do not recommend you
     * do this in your app, rather do the exchange on your server. Added here
     * for demo purposes.
     *
     * @param code The auth code returned from the native auth flow.
     */
    public void performTokenExchange(String code) {
        Intent intent = FoursquareOAuth.getTokenExchangeIntent(activity, CLIENT_ID, CLIENT_SECRET, code);
        connectAppsAndDevicesFragment.startActivityForResult(intent, REQUEST_CODE_FSQ_TOKEN_EXCHANGE);
    }

    public void onCompleteTokenExchange(int resultCode, Intent data) {
        AccessTokenResponse tokenResponse = FoursquareOAuth.getTokenFromResult(resultCode, data);
        Exception exception = tokenResponse.getException();

        if (exception == null) {
            String accessToken = tokenResponse.getAccessToken();
            // Success.
            ToastUtils.showShortToast(activity, "Access token: " + accessToken);

        } else {
            if (exception instanceof FoursquareOAuthException) {
                // OAuth error.
                String errorMessage = ((FoursquareOAuthException) exception).getMessage();
                String errorCode = ((FoursquareOAuthException) exception).getErrorCode();
                ToastUtils.showShortToast(activity, errorMessage + " [" + errorCode + "]");

            } else {
                // Other exception type.
                ToastUtils.showShortToast(activity, exception.getMessage());
            }
        }
    }


    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean flag = false;
        switch (requestCode) {
            case REQUEST_CODE_FSQ_CONNECT:
                onCompleteConnect(resultCode, data);
                flag = true;
                break;
            case REQUEST_CODE_FSQ_TOKEN_EXCHANGE:
                onCompleteTokenExchange(resultCode, data);
                flag = true;
                break;
            default:
                flag = false;

        }
        return flag;
    }


    public static ManagerFourSquare getInstance(Activity activity, ConnectAppsAndDevicesFragment connectAppsAndDevicesFragment) {
        if(managerFourSquare==null)
            return managerFourSquare=new ManagerFourSquare(activity,connectAppsAndDevicesFragment);
        else
            return managerFourSquare;
    }
}
