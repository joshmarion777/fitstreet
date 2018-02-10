package affle.com.fitstreet.appsanddevices.runkeeper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.request.ReqRunkeeperToken;
import affle.com.fitstreet.models.response.ResRunkeeperData;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.AppSharedPreference;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.fragments.ConnectAppsAndDevicesFragment;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akash on 8/8/16.
 */
public class ManagerRunKeeper {
    //
//    private static final String CLIENT_ID = "1c2b0aacde3a44a8b29b2bf26ff5a096";
//    private static final String CLIENT_SECRET = "b8f8b0781c9748fb923e597d2834c07b";
//    private static final String AUTHORIZATION_URL = "https://runkeeper.com/apps/authorize";
//    private static final String DE_AUTHORIZATION_URL = "https://runkeeper.com/apps/de-authorize";
//    private static final String ACCESS_TOKEN_URL = "https://runkeeper.com/apps/token";
    private static final String CALLBACK_URL = "com.example.runkeeperapi://RunKeeperIsCallingBack";


    private static final String CLIENT_ID = "4c173852c75d43ba850f110262289fa1";
    private static final String CLIENT_SECRET = "91ff8b447b49469081030fac1ad405d0";

    private String accessToken = "";

    private Activity activity;
    private WebView webView;
    private static ManagerRunKeeper managerRunKeeper;
    private Dialog dialog;
    private ProgressDialog progressDialog;


    private ManagerRunKeeper(Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialog(activity, R.style.ProgressDialogTheme);
        this.webView = new WebView(activity);
    }

    public static ManagerRunKeeper getInstance(Activity activity) {
        if (managerRunKeeper == null)
            return managerRunKeeper = new ManagerRunKeeper(activity);
        else
            return managerRunKeeper;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setting() {

        webView.setVisibility(View.VISIBLE);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        webView.getSettings().setJavaScriptEnabled(true);

    }

    private void reset() {

        webView.clearCache(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    public void getAuthorizationCode(final Activity activity, final ConnectAppsAndDevicesFragment ConnectAppsDevicesFragment) {
        setting();
        String authorizationUrl = "https://runkeeper.com/apps/authorize?response_type=code&client_id=%s&redirect_uri=%s";
        authorizationUrl = String.format(authorizationUrl, CLIENT_ID, CALLBACK_URL);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(CALLBACK_URL)) {
                    final String authCode = Uri.parse(url).getQueryParameter("code");
                    webView.setVisibility(View.GONE);
                    dialog.dismiss();
                    AppDialog.showProgressDialog(activity, false);
                    getAccessToken(authCode, activity,ConnectAppsDevicesFragment);
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                AppDialog.showProgressDialog(activity, false);
                super.onPageFinished(view, url);
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                AppDialog.showProgressDialog(activity, true);
                super.onPageStarted(view, url, favicon);
            }


        });

        webView.loadUrl(authorizationUrl);
    }


    private void getAccessToken(String authCode, final Activity activity, final ConnectAppsAndDevicesFragment ConnectAppsDevicesFragment) {
        AppDialog.showProgressDialog(activity, false);
//        String accessTokenUrl = "https://runkeeper.com/apps/token?grant_type=authorization_code&code=%s&client_id=%s&client_secret=%s&redirect_uri=%s";
//        final String finalUrl = String.format(accessTokenUrl, authCode, CLIENT_ID, CLIENT_SECRET, CALLBACK_URL);
        final IApiClient client = ApiClient.getApiClient();
//        RequestBody body = RequestBody.create(MediaType.parse("*/*"), String.format("grant_type=authorization_code&code=%s&client_id=%s&client_secret=%s&redirect_uri=%s",
//                authCode, CLIENT_ID, CLIENT_SECRET, CALLBACK_URL));

        Call<ResponseBody> call = client.getAccessToken("authorization_code", authCode, CLIENT_ID, CLIENT_SECRET, CALLBACK_URL);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
//                    ToastUtils.showShortToast(activity, response.body().string());
                    accessToken = (new JSONObject(response.body().string()).getString("access_token"));
                    //getTotalDistance(accessToken);


                } catch (JSONException e) {
                    ToastUtils.showShortToast(activity, e.getMessage());
                    AppDialog.showProgressDialog(activity, false);

                } catch (IOException e) {
//                    ToastUtils.showShortToast(activity, e.getMessage());
                    AppDialog.showProgressDialog(activity, false);
                }
                ReqRunkeeperToken reqRunkeeperToken = new ReqRunkeeperToken();
                reqRunkeeperToken.setRunkeeperAccessToken(accessToken);
                reqRunkeeperToken.setIsRunkeeperConnect("1");
                reqRunkeeperToken.setMethod(MethodFactory.GET_RUNKEEPER_DATA.getMethod());
                reqRunkeeperToken.setUserID(AppSharedPreference.getInstance(activity).getString(PreferenceKeys.KEY_USER_ID, ""));
                reqRunkeeperToken.setServiceKey(AppSharedPreference.getInstance(activity).getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));

                Call<ResRunkeeperData> call1 = client.getRunkeeperData(reqRunkeeperToken);
                call1.enqueue(new Callback<ResRunkeeperData>() {
                    @Override
                    public void onResponse(Call<ResRunkeeperData> call, Response<ResRunkeeperData> response) {
                        AppDialog.showProgressDialog(activity, false);
                        ResRunkeeperData resRunkeeperData = response.body();
                        if (resRunkeeperData != null) {
                            if (resRunkeeperData.getSuccess() == ServiceConstants.SUCCESS) {
                                resRunkeeperData.getActivityData();
                                if (resRunkeeperData.getActivityData().getCalories() != null) {
                                    AppSharedPreference.getInstance(activity).setString(PreferenceKeys.KEY_DISTANCE, String.format(Locale.getDefault(), "%.1f", Float.parseFloat(resRunkeeperData.getActivityData().getDistance())));
//                                    AppSharedPreference.getInstance(activity).setString(PreferenceKeys.KEY_DISTANCE, String.format(Locale.getDefault(),"%.1f", Float.parseFloat(resRunkeeperData.getActivityData().getDistance())));
                                    AppSharedPreference.getInstance(activity).setString(PreferenceKeys.KEY_CALORIES, resRunkeeperData.getActivityData().getCalories());
                                }

                                ToastUtils.showShortToast(activity, R.string.runkeeper_data_success);
                                AppSharedPreference.getInstance(activity).setString(PreferenceKeys.KEY_CONNECTED_APP, AppConstants.RUNKEEPER_CONNECTED);
                                ConnectAppsDevicesFragment.setData();

                            } else {
                                ToastUtils.showShortToast(activity, resRunkeeperData.getErrstr());
                            }
                        } else {
                            ToastUtils.showShortToast(activity, R.string.err_network_connection);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResRunkeeperData> call, Throwable t) {
                        AppDialog.showProgressDialog(activity, false);
                        ToastUtils.showShortToast(activity, R.string.err_network_connection);
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ToastUtils.showShortToast(activity, t.getMessage());
                AppDialog.showProgressDialog(activity, false);

            }
        });
    }


//    private void getTotalDistance(String accessToken) {
//        showProgressBar();
//        IApiClient client = ApiClient.getApiClient();
//        Call<ResponseBody> call = client.getTotalDistance("Bearer " + accessToken, "*/*");
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Log.i("fish", "getTotalDistance onResponse: " + response.body().toString());
////                    ToastUtils.showShortToast(activity, response.body().contentType().type());
//                    findTotalWalkingDistance(new JSONArray(response.body().string()));
//                } catch (JSONException e) {
////                    ToastUtils.showShortToast(activity, e.getMessage());
//                    Log.e("fish", "getTotalDistance onResponse: " + e.getMessage());
//                    stopProgressBar();
//                } catch (IOException e) {
////                    ToastUtils.showShortToast(activity, e.getMessage());
//                    Log.e("fish", "getTotalDistance onResponse: " + e.getMessage());
//                    stopProgressBar();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                ToastUtils.showShortToast(activity, t.getMessage());
//                Log.i("fish", "getTotalDistance onFailure: " + t.getMessage());
//                stopProgressBar();
//            }
//        });
////        String value = response.body().string();
////        try {
////            HttpClient client = new DefaultHttpClient();
////            HttpGet get = new HttpGet("http://api.runkeeper.com/records");
////
////            get.addHeader("Authorization", "Bearer " + accessToken);
////            get.addHeader("Accept", "*/*");
////
////            HttpResponse response = client.execute(get);
////
////            String jsonString = EntityUtils.toString(response.getEntity());
////            JSONArray jsonArray = new JSONArray(jsonString);
////            findTotalWalkingDistance(jsonArray);
////
////        } catch (Exception e) {
////            displayToast("Exception occured:(");
////            e.printStackTrace();
////            resetUi();
////        }
//    }
//
//
//    private void findTotalWalkingDistance(JSONArray arrayOfRecords) {
//        try {
//            //Each record has activity_type and array of statistics. Traverse to  activity_type = Walking
//            for (int ii = 0; ii < arrayOfRecords.length(); ii++) {
//                JSONObject statObject = (JSONObject) arrayOfRecords.get(ii);
//                if ("Walking".equalsIgnoreCase(statObject.getString("activity_type"))) {
//                    //Each activity_type has array of stats, navigate to "Overall" statistic to find the total distance walked.
//                    JSONArray walkingStats = statObject.getJSONArray("stats");
//                    for (int jj = 0; jj < walkingStats.length(); jj++) {
//                        JSONObject iWalkingStat = (JSONObject) walkingStats.get(jj);
//                        if ("Overall".equalsIgnoreCase(iWalkingStat.getString("stat_type"))) {
//                            long totalWalkingDistanceMeters = iWalkingStat.getLong("value");
//                            double totalWalkingDistanceMiles = totalWalkingDistanceMeters * 0.00062137;
//                            displayTotalWalkingDistance(totalWalkingDistanceMiles);
//                            return;
//                        }
//                    }
//                }
//            }
//            ToastUtils.showShortToast(activity, "Something went wrong!!!");
//            stopProgressBar();
//            dialog.dismiss();
//        } catch (JSONException e) {
//            ToastUtils.showShortToast(activity, "Exception occured:(");
//            stopProgressBar();
//            dialog.dismiss();
//            e.printStackTrace();
//
//        }
//    }
//
//
//    private void displayTotalWalkingDistance(double totalWalkingDistanceMiles) {
//
//        dialog.dismiss();
//        stopProgressBar();
//        final String milesWalkedMessage = (totalWalkingDistanceMiles < 1) ? "0 miles?, You get no respect, Start walking already!!!" :
//                String.format("Cool, You have walked %.2f miles so far.", totalWalkingDistanceMiles);
//        ToastUtils.showShortToast(activity, milesWalkedMessage);
//
//    }


    public void connectRunKeeper(final Activity activity, final ConnectAppsAndDevicesFragment ConnectAppsDevicesFragment) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final RelativeLayout layout = new RelativeLayout(activity);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout
                .LayoutParams.MATCH_PARENT));
        layout.setGravity(RelativeLayout.CENTER_IN_PARENT);
        if (webView.getParent() != null)
            ((RelativeLayout) webView.getParent()).removeAllViews();
        ;
        webView.setLayoutParams(layout.getLayoutParams());
        layout.addView(webView);
        dialog.setContentView(layout);
        dialog.getWindow().setBackgroundDrawableResource(R.color.c_transparent);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);


        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                layout.removeAllViews();
                reset();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                layout.removeAllViews();
                reset();
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                getAuthorizationCode(activity,ConnectAppsDevicesFragment);
            }
        });
        if (!activity.isFinishing())
            dialog.show();
    }
}
