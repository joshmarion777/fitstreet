package affle.com.fitstreet.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.request.ReqForgotPassword;
import affle.com.fitstreet.models.request.ReqLogin;
import affle.com.fitstreet.models.response.ResForgotPassword;
import affle.com.fitstreet.models.response.ResLoginSignUpSocial;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_email_id)
    EditText etEmailId;
    @BindView(R.id.et_password)
    EditText etPassword;
    private String mEmailId, mPassword;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        super.initData();
    }

    @Override
    protected void initViews() {
        //set listeners
        ivBack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
    }

    /**
     * Method used to check if data entered is valid
     *
     * @return true if valid data is entered, else returns false
     */
    private boolean isValid() {
        mEmailId = etEmailId.getText().toString().trim();
        mPassword = etPassword.getText().toString().trim();
        if (mEmailId.isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_email_empty);
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mEmailId).matches()) {
            ToastUtils.showShortToast(this, R.string.err_email_invalid);
            return false;
        }
        if (mPassword.isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_password_empty);
            return false;
        }
        return true;
    }

    /**
     * This method is used to show the progress dialog
     *
     * @return void
     */
    private void showProgressDialog() {
        mProgressDialog = AppDialog.showProgressDialog(this);
        mProgressDialog.show();
    }

    /**
     * This method is used to hide the progress dialog
     *
     * @return void
     */
    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Method used to call the login web service
     */
    private void login() {
        showProgressDialog();
        AppUtilMethods.hideKeyBoard(this);
        IApiClient client = ApiClient.getApiClient();
        ReqLogin reqLogin = new ReqLogin();
        reqLogin.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqLogin.setDeviceType(ServiceConstants.DEVICE_TYPE);
        reqLogin.setMethod(MethodFactory.LOGIN.getMethod());
        reqLogin.setEmailID(mEmailId);
        reqLogin.setPassword(mPassword);
        if (mAppSharedPreference.getString(PreferenceKeys.KEY_DEVICE_TOKEN, "").equals("")) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            mAppSharedPreference.setString(PreferenceKeys.KEY_DEVICE_TOKEN, refreshedToken);
        }
        reqLogin.setDeviceToken(mAppSharedPreference.getString(PreferenceKeys.KEY_DEVICE_TOKEN, "aa"));
        Call<ResLoginSignUpSocial> resLoginSignUpSocialCall = client.login(reqLogin);
        resLoginSignUpSocialCall.enqueue(new Callback<ResLoginSignUpSocial>() {
            @Override
            public void onResponse(Call<ResLoginSignUpSocial> call, Response<ResLoginSignUpSocial> response) {
                dismissProgressDialog();
                ResLoginSignUpSocial resLoginSignUpSocial = response.body();
                if (resLoginSignUpSocial != null) {
                    if (resLoginSignUpSocial.getSuccess() == ServiceConstants.SUCCESS) {
                        ResLoginSignUpSocial.ResUserData userData = resLoginSignUpSocial.getUserData();
                        mAppSharedPreference.setString(PreferenceKeys.KEY_USER_ID, userData.getUserID());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_EMAIL_ID, userData.getEmailID());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_USERNAME, userData.getName());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_DOB, userData.getDob());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_GENDER, userData.getGender());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_IMAGE, userData.getImage());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, userData.getIsEmailVerified());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_PHONE, userData.getPhone());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_REGISTRATION_TYPE, userData.getRegistrationType());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_SERVICE_KEY, userData.getServiceKey());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_STATUS, userData.getStatus());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_HOME_IMAGE, userData.getHomeScreen());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_DISTANCE_UNIT, AppUtilMethods.getDistanceUnit(LoginActivity.this, Integer.parseInt(userData.getUnit())));
                        mAppSharedPreference.setInt(PreferenceKeys.KEY_DISTANCE_UNIT_INDEX, Integer.parseInt(userData.getUnit()));
                        mAppSharedPreference.setBoolean(PreferenceKeys.KEY_NOTIFICATION_ON, userData.getNotification().equals(ServiceConstants.TRUE));
                        mAppSharedPreference.setString(PreferenceKeys.KEY_POINTS, userData.getTotalPoints());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_CALORIES, userData.getCalories());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_DISTANCE, userData.getDistance());
                        mAppSharedPreference.setBoolean(PreferenceKeys.KEY_LOGGED_IN, true);
                        if (userData.getIsFitbitConnect().equals("1"))
                            mAppSharedPreference.setString(PreferenceKeys.KEY_CONNECTED_APP, AppConstants.FITBIT_CONNECTED);
                        else if (userData.getIsRunkeeperConnect().equals("1"))
                            mAppSharedPreference.setString(PreferenceKeys.KEY_CONNECTED_APP, AppConstants.RUNKEEPER_CONNECTED);
                        mAppSharedPreference.setBoolean(PreferenceKeys.KEY_LOGGED_IN, true);
                        Intent intent;
                        if (userData.getLoginTime().equals("1")) {
                            intent = new Intent(LoginActivity.this, AppTutorialActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            setResult(RESULT_OK);
                        } else {
                            intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            setResult(RESULT_OK);
                        }
                        finish();
                    } else {
                        if (resLoginSignUpSocial.getErrorCode() == 1) {
                            etEmailId.setText("");
                            etPassword.setText("");
                            etEmailId.requestFocus();
                        }
                        ToastUtils.showShortToast(LoginActivity.this, resLoginSignUpSocial.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(LoginActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResLoginSignUpSocial> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(LoginActivity.this, R.string.err_network_connection);
            }
        });
    }

    /**
     * Method used to call the forgot password web service
     */
    public void forgotPassword(String emailId) {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        ReqForgotPassword reqForgotPassword = new ReqForgotPassword();
        reqForgotPassword.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqForgotPassword.setMethod(MethodFactory.FORGOT_PASSWORD.getMethod());
        reqForgotPassword.setEmailID(emailId);
        Call<ResForgotPassword> resForgotPasswordCall = client.forgotPassword(reqForgotPassword);
        resForgotPasswordCall.enqueue(new Callback<ResForgotPassword>() {
            @Override
            public void onResponse(Call<ResForgotPassword> call, Response<ResForgotPassword> response) {
                dismissProgressDialog();
                ResForgotPassword resForgotPassword = response.body();
                if (resForgotPassword != null) {
                    if (resForgotPassword.getSuccess() == ServiceConstants.SUCCESS) {
                        ToastUtils.showShortToast(LoginActivity.this, resForgotPassword.getErrstr());
                    } else {
                        ToastUtils.showShortToast(LoginActivity.this, resForgotPassword.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(LoginActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResForgotPassword> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(LoginActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_login:
                if (isValid()) {
                    if (NetworkConnection.isNetworkConnected(this)) {
                        login();
                    } else {
                        ToastUtils.showShortToast(this, R.string.err_network_connection);
                    }
                }
                break;
            case R.id.tv_forgot_password:
                AppDialog.showForgotPasswordDialog(this);
                break;
        }
    }
}
