package affle.com.fitstreet.appsanddevices.fitbit;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import java.util.Locale;
import affle.com.fitstreet.R;
import affle.com.fitstreet.models.request.ReqFitbitToken;
import affle.com.fitstreet.models.response.ResRunkeeperData;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.AppSharedPreference;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.activities.HomeActivity;
import affle.com.fitstreet.ui.fragments.ConnectAppsAndDevicesFragment;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akash on 20/10/16.
 */
public class ManagerFitbit {
    private static final String CALLBACK_URL = "affle.com.fitstreet.appsanddevices.fitbit://FitBitIsCallingBack";
    private static final String CLIENT_ID = "2282YX";
    private static final String CLIENT_SECRET = "2a0ff33bb8a321f932e533f11753e845";
    private Activity activity;
    private static ManagerFitbit managerFitbit;


    //Chrome tabs example
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    private CustomTabsIntent mCustomTabsIntent;
    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";

    private ManagerFitbit(HomeActivity activity) {
        this.activity = activity;

    }

    public static ManagerFitbit getInstance(HomeActivity activity) {
        if (managerFitbit == null)
            return managerFitbit = new ManagerFitbit(activity);
        else
            return managerFitbit;
    }

    public void connectFitbit(final Activity activity) {
        getAuthorizationCode(activity);
    }


    public void getAuthorizationCode(final Activity activity) {
        String authorizationUrl = "https://www.fitbit.com/oauth2/authorize?response_type=token&client_id=%s&redirect_uri=%s&expires_in=31536000&scope=activity&prompt=login";
        authorizationUrl = String.format(authorizationUrl, CLIENT_ID, CALLBACK_URL);


        //using Chrome tabs
        mCustomTabsServiceConnection= new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        CustomTabsClient.bindCustomTabsService(activity, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);

        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(true)
                .build();
        mCustomTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        mCustomTabsIntent.launchUrl(activity, Uri.parse(authorizationUrl));



    }


    /**
     * Saving accessToken and Userid to server and getting data from Fitbit server
     *
     * @param accessToken AccessToken from Fitbit
     * @param userId      Userid of Fitbit user
     */
    public  void saveTokenToServer(String accessToken, String userId, final ConnectAppsAndDevicesFragment ConnectAppsDevicesFragment) {
        //AppDialog.showProgressDialog(activity, true);
        IApiClient client = ApiClient.getApiClient();
        ReqFitbitToken reqFitbitToken = new ReqFitbitToken();
        reqFitbitToken.setUserID(AppSharedPreference.getInstance(activity).getString(PreferenceKeys.KEY_USER_ID, ""));
        reqFitbitToken.setServiceKey(AppSharedPreference.getInstance(activity).getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqFitbitToken.setFitbitAccessToken(accessToken);
        reqFitbitToken.setIsFitbitConnect("1");
        reqFitbitToken.setFitbitUserId(userId);
        reqFitbitToken.setMethod(MethodFactory.GET_FITBIT_DATA.getMethod());
        Call<ResRunkeeperData> call = client.getFitbitData(reqFitbitToken);
        call.enqueue(new Callback<ResRunkeeperData>() {
            @Override
            public void onResponse(Call<ResRunkeeperData> call, Response<ResRunkeeperData> response) {
                //AppDialog.showProgressDialog(activity, false);
                ResRunkeeperData runkeeperData = response.body();
                if (runkeeperData != null) {
                    if (runkeeperData.getSuccess() == ServiceConstants.SUCCESS) {
                        runkeeperData.getActivityData();
                        if (runkeeperData.getActivityData().getCalories() != null) {
                            AppSharedPreference.getInstance(activity).setString(PreferenceKeys.KEY_DISTANCE, String.format(Locale.getDefault(), "%.1f", Float.parseFloat(runkeeperData.getActivityData().getDistance())));
                            //AppSharedPreference.getInstance(activity).setString(PreferenceKeys.KEY_DISTANCE,  String.format(Locale.getDefault(),"%.1f", Float.parseFloat(runkeeperData.getActivityData().getDistance())));

                            AppSharedPreference.getInstance(activity).setString(PreferenceKeys.KEY_CALORIES, runkeeperData.getActivityData().getCalories());
                        }
                        ToastUtils.showShortToast(activity, R.string.fitbit_data_success);
                        AppSharedPreference.getInstance(activity).setString(PreferenceKeys.KEY_CONNECTED_APP, AppConstants.FITBIT_CONNECTED);
                        ConnectAppsDevicesFragment.setData();
                    } else {
                        ToastUtils.showShortToast(activity, runkeeperData.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(activity, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResRunkeeperData> call, Throwable t) {
                //AppDialog.showProgressDialog(activity, false);
                ToastUtils.showShortToast(activity, R.string.err_network_connection);
            }
        });

    }
}
