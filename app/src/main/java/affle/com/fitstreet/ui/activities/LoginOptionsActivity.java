package affle.com.fitstreet.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sromku.simple.fb.SimpleFacebook;

import affle.com.fitstreet.R;
import affle.com.fitstreet.interfaces.IOnSocialDataFetched;
import affle.com.fitstreet.models.SocialModel;
import affle.com.fitstreet.models.request.ReqSignUp;
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
import affle.com.fitstreet.utils.Logger;
import affle.com.fitstreet.utils.SocialNetworkUtils;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginOptionsActivity extends BaseActivity implements IOnSocialDataFetched {
    @BindView(R.id.tv_login_with_fb)
    TextView tvLoginWithFb;
    @BindView(R.id.tv_login_with_email)
    TextView tvLoginWithEmail;
    @BindView(R.id.tv_skip)
    TextView tvSkip;
    @BindView(R.id.iv_back_login_options)
    ImageView ivBackLoginOptions;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);
        super.initData();
    }

    @Override
    protected void initViews() {
        tvLoginWithFb.setOnClickListener(this);
        tvLoginWithEmail.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
        ivBackLoginOptions.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
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
     * Sign up through Facebook
     *
     * @param socialModel
     */
    private void signUpSocial(final SocialModel socialModel) {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        ReqSignUp reqSignUp = new ReqSignUp();
        reqSignUp.setFacebookId(socialModel.getId());
        reqSignUp.setEmailID(socialModel.getEmail());
        reqSignUp.setPassword("");
        reqSignUp.setDeviceType(ServiceConstants.DEVICE_TYPE);
        reqSignUp.setName(socialModel.getName());
        reqSignUp.setGender(socialModel.getGender() != 0 ? String.valueOf(socialModel.getGender()) : "");
        reqSignUp.setMethod(MethodFactory.SIGN_UP.getMethod());
        reqSignUp.setPhone("");
        reqSignUp.setRegistrationType(ServiceConstants.LOGIN_FB);
        if (mAppSharedPreference.getString(PreferenceKeys.KEY_DEVICE_TOKEN, "").equals("")) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            mAppSharedPreference.setString(PreferenceKeys.KEY_DEVICE_TOKEN, refreshedToken);
        }
        reqSignUp.setDeviceToken(mAppSharedPreference.getString(PreferenceKeys.KEY_DEVICE_TOKEN, "aaaaaa"));
        //reqSignUp.setDeviceToken("e7e540c80678dd8cac61848dc2fc8a");
        reqSignUp.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqSignUp.setDob(socialModel.getDob());
        reqSignUp.setEmailUpdate(String.valueOf(0));
        reqSignUp.setImage(socialModel.getPicture());
        Call<ResLoginSignUpSocial> resSignUp = client.signupFb(reqSignUp);
        resSignUp.enqueue(new Callback<ResLoginSignUpSocial>() {
            @Override
            public void onResponse(Call<ResLoginSignUpSocial> call, Response<ResLoginSignUpSocial> response) {
                dismissProgressDialog();
                ResLoginSignUpSocial resSignUp = response.body();
                if (resSignUp != null) {
                    if (resSignUp.getSuccess() == ServiceConstants.SUCCESS) {
                        ResLoginSignUpSocial.ResUserData userData = resSignUp.getUserData();
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
                        mAppSharedPreference.setString(PreferenceKeys.KEY_DISTANCE_UNIT, AppUtilMethods.getDistanceUnit(LoginOptionsActivity.this, Integer.parseInt(userData.getUnit())));
                        mAppSharedPreference.setInt(PreferenceKeys.KEY_DISTANCE_UNIT_INDEX, Integer.parseInt(userData.getUnit()));
                        mAppSharedPreference.setBoolean(PreferenceKeys.KEY_NOTIFICATION_ON, userData.getNotification().equals(ServiceConstants.TRUE));
                        mAppSharedPreference.setString(PreferenceKeys.KEY_POINTS, userData.getTotalPoints());
                        if (userData.getIsFitbitConnect().equals("1"))
                            mAppSharedPreference.setString(PreferenceKeys.KEY_CONNECTED_APP, AppConstants.FITBIT_CONNECTED);
                        else if (userData.getIsRunkeeperConnect().equals("1"))
                            mAppSharedPreference.setString(PreferenceKeys.KEY_CONNECTED_APP, AppConstants.RUNKEEPER_CONNECTED);
                        mAppSharedPreference.setBoolean(PreferenceKeys.KEY_LOGGED_IN, true);
                        if (socialModel.getEmail() != null && !socialModel.getEmail().isEmpty()) {
                            mAppSharedPreference.setBoolean(PreferenceKeys.KEY_CHANGE_PWD, false);
                        }
                        startActivity(new Intent(LoginOptionsActivity.this, HomeActivity.class));
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        if (resSignUp.getErrorCode() == 2) {
                            Intent intent = new Intent(LoginOptionsActivity.this, SignUpActivity.class);
                            intent.putExtra(AppConstants.TAG_SOCIAL_MODEL, socialModel);
                            startActivityForResult(intent, AppConstants.RC_LOGIN_SIGN_UP);
                        } else {
                            ToastUtils.showShortToast(LoginOptionsActivity.this, resSignUp.getErrstr());
                        }
                    }
                } else {
                    ToastUtils.showShortToast(LoginOptionsActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResLoginSignUpSocial> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(LoginOptionsActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    protected void onResume() {
//        tvSkip.setOnClickListener(this);
//        tvLoginWithEmail.setOnClickListener(this);
        super.onResume();
    }

    @Override
    public void onSocialDataFetched(SocialModel socialModel) {
        Logger.e("Fb ID " + socialModel.getId());
        signUpSocial(socialModel);
        tvSkip.setOnClickListener(this);
        tvLoginWithEmail.setOnClickListener(this);
        /*if (socialModel.getEmail() != null && !socialModel.getEmail().isEmpty()) {
            signUpSocial(socialModel);
        } else {
            Intent mIntent = new Intent(LoginOptionsActivity.this, SignUpActivity.class);
            mIntent.putExtra(AppConstants.TAG_SOCIAL_MODEL, socialModel);
            startActivityForResult(mIntent, AppConstants.RC_LOGIN_SIGN_UP);
        }*/
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_login_with_email:
                intent = new Intent(LoginOptionsActivity.this, LoginActivity.class);
                startActivityForResult(intent, AppConstants.RC_LOGIN_SIGN_UP);
                break;
            case R.id.tv_login_with_fb:
                if (NetworkConnection.isNetworkConnected(this)) {
                    SocialNetworkUtils.getInstance(this).loginWithFb(this, SimpleFacebook.getInstance(this));
                    tvSkip.setOnClickListener(null);
                    tvLoginWithEmail.setOnClickListener(null);
                } else
                    ToastUtils.showShortToast(this, R.string.err_network_connection);
                break;
            case R.id.tv_skip:
                //AppDialog.showProgressDialog(this,true);
                intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(AppConstants.TAG_FROM_WHERE, "FromLoginOptionsSkipActivity");
                startActivity(intent);
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.iv_back_login_options:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_LOGIN_SIGN_UP && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        } else {
            SimpleFacebook.getInstance(this).onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
