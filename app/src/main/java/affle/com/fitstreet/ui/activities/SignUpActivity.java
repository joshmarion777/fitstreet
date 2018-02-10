package affle.com.fitstreet.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import affle.com.fitstreet.R;
import affle.com.fitstreet.interfaces.IOnDateSet;
import affle.com.fitstreet.models.SocialModel;
import affle.com.fitstreet.models.request.ReqSignUp;
import affle.com.fitstreet.models.response.ResLoginSignUpSocial;
import affle.com.fitstreet.models.response.ResSignUp;
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
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity implements IOnDateSet {
    @BindView(R.id.tv_receive_updates)
    TextView tvReceiveUpdates;
    @BindView(R.id.tv_text_terms_conditions)
    TextView tvTextTermsConditions;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_female)
    Button btnFemale;
    @BindView(R.id.btn_male)
    Button btnMale;
    @BindView(R.id.btn_others)
    Button btnOthers;
    @BindView(R.id.btn_create_account)
    Button btnCreateAccount;
    @BindView(R.id.et_email_id)
    EditText etEmailId;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_dob)
    EditText etDob;
    private String mEmailId, mPassword, mMobile, mName, mDob;
    private int mGender, mEmailUpdate;
    private SocialModel mSocialModel;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        super.initData();
    }

    @Override
    protected void initViews() {
        btnFemale.setSelected(false);
        btnMale.setSelected(false);
        btnOthers.setSelected(false);
        if (getIntent().getParcelableExtra(AppConstants.TAG_SOCIAL_MODEL) != null) {
            mSocialModel = getIntent().getParcelableExtra(AppConstants.TAG_SOCIAL_MODEL);
            etName.setText(mSocialModel.getName());
            etDob.setText(mSocialModel.getDob());
            if (mSocialModel.getGender() != 0) {
                if (mSocialModel.getGender() == ServiceConstants.GENDER_MALE) {
                    btnMale.setSelected(true);
                    mGender = ServiceConstants.GENDER_MALE;
                } else if (mSocialModel.getGender() == ServiceConstants.GENDER_FEMALE) {
                    btnFemale.setSelected(true);
                    mGender = ServiceConstants.GENDER_FEMALE;
                } else if (mSocialModel.getGender() == ServiceConstants.GENDER_OTHERS) {
                    btnOthers.setSelected(true);
                    mGender = ServiceConstants.GENDER_OTHERS;
                }
            }
        }
        String terms = getString(R.string.txt_terms_of_use);
        String privacy = getString(R.string.txt_privacy_policy);
        String termsPrivacy = getString(R.string.txt_terms_privacy_policy);
        SpannableStringBuilder builder = new SpannableStringBuilder(termsPrivacy);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, WebViewActivity.class);
                intent.putExtra(AppConstants.TAG_TITLE_RESOURCE_ID, R.string.txt_terms_of_use);
                intent.putExtra(AppConstants.TAG_URL, AppConstants.URL_TERMS_OF_USE);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(ContextCompat.getColor(SignUpActivity.this, R.color.c_blue_btns));
            }

        }, termsPrivacy.indexOf(terms), termsPrivacy.indexOf(terms) + terms.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, WebViewActivity.class);
                intent.putExtra(AppConstants.TAG_TITLE_RESOURCE_ID, R.string.txt_privacy_policy);
                intent.putExtra(AppConstants.TAG_URL, AppConstants.URL_PRIVACY_POLICY);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(ContextCompat.getColor(SignUpActivity.this, R.color.c_pink_outline));
            }
        }, termsPrivacy.indexOf(privacy), termsPrivacy.indexOf(privacy) + privacy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvTextTermsConditions.setText(builder);
        tvTextTermsConditions.setMovementMethod(LinkMovementMethod.getInstance());

        //set listeners
        tvReceiveUpdates.setOnClickListener(this);
        tvReceiveUpdates.setSelected(false);
        ivBack.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);
        btnFemale.setOnClickListener(this);
        btnMale.setOnClickListener(this);
        btnOthers.setOnClickListener(this);
        etDob.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mEmailUpdate = 0;
    }

    /**
     * Checking for valid email info
     *
     * @return
     */
    private boolean isValid() {
        mEmailId = etEmailId.getText().toString().trim();
        mPassword = etPassword.getText().toString().trim();
        mName = etName.getText().toString().trim();
        mMobile = etMobile.getText().toString().trim();
        mDob = etDob.getText().toString().trim();
        if (mName.isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_name_empty);
            return false;
        }
//        if (mMobile.isEmpty()) {
//            ToastUtils.showShortToast(this, R.string.err_mobile_empty);
//            return false;
//        }
        if (!mMobile.isEmpty() && mMobile.length() < 10) {
            ToastUtils.showShortToast(this, R.string.err_mobile_invalid);
            return false;
        }
//        if (mDob.isEmpty()) {
//            ToastUtils.showShortToast(this, R.string.err_dob_empty);
//            return false;
//        }
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
        if (mPassword.length() < AppConstants.MIN_LENGTH_PASSWORD || mPassword.length() > AppConstants.MAX_LENGTH_PASSWORD) {
            ToastUtils.showLongToast(this, String.format(getString(R.string.err_password_length), AppConstants.MIN_LENGTH_PASSWORD, AppConstants.MAX_LENGTH_PASSWORD));
            return false;
        }
//        if (!mPassword.matches(AppConstants.REGEX_PASSWORD)) {
//            ToastUtils.showLongToast(this, String.format(getString(R.string.err_password_invalid), AppConstants.MIN_LENGTH_PASSWORD, AppConstants.MAX_LENGTH_PASSWORD));
//            return false;
//        }
        if (mGender == 0) {
            ToastUtils.showShortToast(this, R.string.err_gender_empty);
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
     * Method used to call the sign up web service
     */
    private void signUp() {
        showProgressDialog();
        AppUtilMethods.hideKeyBoard(this);
        IApiClient client = ApiClient.getApiClient();
        ReqSignUp reqSignUp = new ReqSignUp();
        reqSignUp.setEmailID(mEmailId);
        reqSignUp.setPassword(mPassword);
        reqSignUp.setDeviceType(ServiceConstants.DEVICE_TYPE);
        reqSignUp.setName(mName);
        reqSignUp.setGender(String.valueOf(mGender));
        reqSignUp.setMethod(MethodFactory.SIGN_UP.getMethod());
        reqSignUp.setPhone(mMobile);
        reqSignUp.setRegistrationType(ServiceConstants.LOGIN_MANUAL);
        reqSignUp.setImage("");
        reqSignUp.setFacebookId("");
        if (mAppSharedPreference.getString(PreferenceKeys.KEY_DEVICE_TOKEN, "").equals("")) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            mAppSharedPreference.setString(PreferenceKeys.KEY_DEVICE_TOKEN, refreshedToken);
        }
        reqSignUp.setDeviceToken(mAppSharedPreference.getString(PreferenceKeys.KEY_DEVICE_TOKEN, "aaaaaa"));
        //reqSignUp.setDeviceToken(mAppSharedPreference.getString(PreferenceKeys.KEY_DEVICE_TOKEN,""));
        reqSignUp.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqSignUp.setDob(AppUtilMethods.getServerDate(mDob));
        reqSignUp.setEmailUpdate(String.valueOf(mEmailUpdate));
        Call<ResSignUp> resSignUp = client.signup(reqSignUp);
        resSignUp.enqueue(new Callback<ResSignUp>() {
            @Override
            public void onResponse(Call<ResSignUp> call, Response<ResSignUp> response) {
                dismissProgressDialog();
                ResSignUp resSignUp = response.body();
                if (resSignUp != null) {
                    if (resSignUp.getSuccess() == ServiceConstants.SUCCESS) {
//                        mAppSharedPreference.setString(PreferenceKeys.KEY_USER_ID, resSignUp.getUserID());
//                        mAppSharedPreference.setString(PreferenceKeys.KEY_EMAIL_ID, mEmailId);
//                        mAppSharedPreference.setString(PreferenceKeys.KEY_USERNAME, mName);
//                        mAppSharedPreference.setString(PreferenceKeys.KEY_DOB, mDob);
//                        mAppSharedPreference.setString(PreferenceKeys.KEY_GENDER, String.valueOf(mGender));
//                        mAppSharedPreference.setString(PreferenceKeys.KEY_IMAGE, "");
//                        mAppSharedPreference.setString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0");
//                        mAppSharedPreference.setString(PreferenceKeys.KEY_PHONE, mMobile);
//                        mAppSharedPreference.setString(PreferenceKeys.KEY_REGISTRATION_TYPE, mSocialModel == null ? ServiceConstants.LOGIN_MANUAL : ServiceConstants.LOGIN_FB);
//                        mAppSharedPreference.setBoolean(PreferenceKeys.KEY_LOGGED_IN, true);
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        ToastUtils.showShortToast(SignUpActivity.this, resSignUp.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(SignUpActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResSignUp> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(SignUpActivity.this, R.string.err_network_connection);
            }
        });
    }

    /**
     * Method used to call the sign up web service if user has logged in with Facebook
     */
    private void signUpSocial() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        ReqSignUp reqSignUp = new ReqSignUp();
        reqSignUp.setEmailID(mEmailId);
        reqSignUp.setPassword(mPassword);
        reqSignUp.setDeviceType(ServiceConstants.DEVICE_TYPE);
        reqSignUp.setName(mName);
        reqSignUp.setGender(String.valueOf(mGender));
        reqSignUp.setMethod(MethodFactory.SIGN_UP.getMethod());
        reqSignUp.setPhone(mMobile);
        reqSignUp.setRegistrationType(ServiceConstants.LOGIN_FB);
        reqSignUp.setImage(mSocialModel == null ? "" : mSocialModel.getPicture());
        reqSignUp.setFacebookId(mSocialModel.getId());
        if (mAppSharedPreference.getString(PreferenceKeys.KEY_DEVICE_TOKEN, "").equals("")) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            mAppSharedPreference.setString(PreferenceKeys.KEY_DEVICE_TOKEN, refreshedToken);
        }
        reqSignUp.setDeviceToken(mAppSharedPreference.getString(PreferenceKeys.KEY_DEVICE_TOKEN, "aaaaaa"));
        //reqSignUp.setDeviceToken("e7e540c80678dd8cac61848dc2fc8a");
        reqSignUp.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqSignUp.setDob(AppUtilMethods.getServerDate(mDob));
        reqSignUp.setEmailUpdate(String.valueOf(mEmailUpdate));
        Call<ResLoginSignUpSocial> resSignUp = client.signupFb(reqSignUp);
        resSignUp.enqueue(new Callback<ResLoginSignUpSocial>() {
            @Override
            public void onResponse(Call<ResLoginSignUpSocial> call, Response<ResLoginSignUpSocial> response) {
                dismissProgressDialog();
                ResLoginSignUpSocial resSignUp = response.body();
                if (resSignUp != null) {
                    if (resSignUp.getSuccess() == ServiceConstants.SUCCESS) {
                        ResLoginSignUpSocial.ResUserData userData = resSignUp.getUserData();
                        Intent intent;
                        if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equals("1")) {
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
                            mAppSharedPreference.setString(PreferenceKeys.KEY_DISTANCE_UNIT, AppUtilMethods.getDistanceUnit(SignUpActivity.this, Integer.parseInt(userData.getUnit())));
                            mAppSharedPreference.setInt(PreferenceKeys.KEY_DISTANCE_UNIT_INDEX, Integer.parseInt(userData.getUnit()));
                            mAppSharedPreference.setBoolean(PreferenceKeys.KEY_NOTIFICATION_ON, userData.getNotification().equals(ServiceConstants.TRUE));
                            mAppSharedPreference.setString(PreferenceKeys.KEY_POINTS, userData.getTotalPoints());
                            mAppSharedPreference.setBoolean(PreferenceKeys.KEY_LOGGED_IN, true);
                            intent = new Intent(SignUpActivity.this, HomeActivity.class);
                        } else {
                            intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        }
                        startActivity(intent);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        ToastUtils.showShortToast(SignUpActivity.this, resSignUp.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(SignUpActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResLoginSignUpSocial> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(SignUpActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onDateSet(int yearSelected, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, yearSelected);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = simpleDateFormat.format(calendar.getTime());
        etDob.setText(date);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_receive_updates:
                if (tvReceiveUpdates.isSelected()) {
                    tvReceiveUpdates.setSelected(false);
                    mEmailUpdate = 0;
                } else {
                    tvReceiveUpdates.setSelected(true);
                    mEmailUpdate = 1;
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_male:
                btnMale.setSelected(true);
                btnFemale.setSelected(false);
                btnOthers.setSelected(false);
                mGender = ServiceConstants.GENDER_MALE;
                break;
            case R.id.btn_female:
                btnFemale.setSelected(true);
                btnMale.setSelected(false);
                btnOthers.setSelected(false);
                mGender = ServiceConstants.GENDER_FEMALE;
                break;
            case R.id.btn_others:
                btnOthers.setSelected(true);
                btnFemale.setSelected(false);
                btnMale.setSelected(false);
                mGender = ServiceConstants.GENDER_OTHERS;
                break;
            case R.id.btn_create_account:
                if (isValid()) {
                    if (NetworkConnection.isNetworkConnected(this)) {
                        if (mSocialModel == null) {
                            signUp();
                        } else {
                            signUpSocial();
                        }
                    } else {
                        ToastUtils.showShortToast(this, R.string.err_network_connection);
                    }
                }
                break;
            case R.id.et_dob:
                AppDialog.showDatePickerDialog(this, this, false);
                break;
        }
    }
}
