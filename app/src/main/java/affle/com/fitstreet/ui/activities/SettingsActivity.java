package affle.com.fitstreet.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.sromku.simple.fb.SimpleFacebook;

import java.util.Arrays;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.DistanceUnitAdapter;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.request.ReqChangePassword;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResUpdateProfile;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.SocialNetworkUtils;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 6/16/16.
 */
public class SettingsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.tv_change_password)
    TextView tvChangePassword;
    @BindView(R.id.spn_distance)
    Spinner spnDistance;
    @BindView(R.id.switch_notifications)
    Switch switchNotifications;
    @BindView(R.id.ll_change_pwd)
    LinearLayout llChangePassword;
    @BindView(R.id.tv_about_us)
    TextView tvAboutUs;
    @BindView(R.id.tv_privacy_policy)
    TextView tvPrivacyPolicy;
    @BindView(R.id.tv_terms_of_use)
    TextView tvTermsOfUse;
    @BindView(R.id.iv_hide_show_new_password)
    ImageView ivHideShowNewPassword;
    @BindView(R.id.iv_hide_show_old_password)
    ImageView ivHideShowOldPassword;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_done)
    Button btnDone;
    @BindView(R.id.tv_refund_policy)
    CustomTypefaceTextView tvRefundPolicy;
    private DistanceUnitAdapter mDistanceUnitAdapter;
    private List<String> mDistanceUnitList;
    private int mCheckedPos;
    private String mOldPassword, mNewPassword;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_settings);
        switchNotifications.setChecked(mAppSharedPreference.getBoolean(PreferenceKeys.KEY_NOTIFICATION_ON, false));

        //set listeners
        switchNotifications.setOnClickListener(this);
        tvChangePassword.setOnClickListener(this);
        tvAboutUs.setOnClickListener(this);
        tvRefundPolicy.setOnClickListener(this);
        tvPrivacyPolicy.setOnClickListener(this);
        tvTermsOfUse.setOnClickListener(this);
        ivHideShowNewPassword.setOnClickListener(this);
        ivHideShowOldPassword.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnDone.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mCheckedPos = mAppSharedPreference.getInt(PreferenceKeys.KEY_DISTANCE_UNIT_INDEX, 0);
        mDistanceUnitList = Arrays.asList(getResources().getStringArray(R.array.arr_distance));
        mDistanceUnitAdapter = new DistanceUnitAdapter(this, R.id.tv_distance_unit, mDistanceUnitList, mCheckedPos);
        spnDistance.setAdapter(mDistanceUnitAdapter);
        spnDistance.setSelection(mCheckedPos);
        spnDistance.setOnItemSelectedListener(this);
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
     * Checking valid information
     *
     * @return
     */
    private boolean isValid() {
        mOldPassword = etOldPassword.getText().toString().trim();
        mNewPassword = etNewPassword.getText().toString().trim();
        if (mOldPassword.isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_old_pwd_empty);
            return false;
        }
        if (mOldPassword.length() < AppConstants.MIN_LENGTH_PASSWORD || mOldPassword.length() > AppConstants.MAX_LENGTH_PASSWORD) {
            ToastUtils.showLongToast(this, String.format(getString(R.string.err_old_password_length), AppConstants.MIN_LENGTH_PASSWORD, AppConstants.MAX_LENGTH_PASSWORD));
            return false;
        }
        if (mNewPassword.isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_new_pwd_empty);
            return false;
        }
        if (mNewPassword.length() < AppConstants.MIN_LENGTH_PASSWORD || mNewPassword.length() > AppConstants.MAX_LENGTH_PASSWORD) {
            ToastUtils.showLongToast(this, String.format(getString(R.string.err_new_password_length), AppConstants.MIN_LENGTH_PASSWORD, AppConstants.MAX_LENGTH_PASSWORD));
            return false;
        }
        if (mOldPassword.equals(mNewPassword)) {
            ToastUtils.showLongToast(this, R.string.err_passwords_same);
            return false;
        }
        return true;
    }

    /**
     * Method used to call the change password web service
     */
    public void changePassword() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        ReqChangePassword reqChangePassword = new ReqChangePassword();
        reqChangePassword.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqChangePassword.setMethod(MethodFactory.CHANGE_PASSWORD.getMethod());
        reqChangePassword.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqChangePassword.setOldPassword(mOldPassword);
        reqChangePassword.setNewPassword(mNewPassword);
        Call<ResBase> resChangePasswordCall = client.changePassword(reqChangePassword);
        resChangePasswordCall.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                dismissProgressDialog();
                ResBase resBase = response.body();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        ToastUtils.showShortToast(SettingsActivity.this, resBase.getErrstr());
                        etNewPassword.setText("");
                        etOldPassword.setText("");
                        tvChangePassword.setSelected(false);
                        llChangePassword.setVisibility(View.GONE);
                        SocialNetworkUtils.getInstance(SettingsActivity.this).logoutFromFb(SettingsActivity.this, SimpleFacebook.getInstance(SettingsActivity.this));
                        mAppSharedPreference.clearEditor();
                        Intent intent = new Intent(SettingsActivity.this, TutorialActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtils.showShortToast(SettingsActivity.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(SettingsActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(SettingsActivity.this, R.string.err_network_connection);
            }
        });
    }

    /**
     * Method used to call the update distance unit
     */
    private void updateDistanceUnit(final int position) {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        Call<ResUpdateProfile> resUpdateProfileCall = client.updateSettingsWithoutImage(RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), MethodFactory.UPDATE_SETTINGS.getMethod()),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY)),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, "")),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), String.valueOf(position)),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""));
        resUpdateProfileCall.enqueue(new Callback<ResUpdateProfile>() {
            @Override
            public void onResponse(Call<ResUpdateProfile> call, Response<ResUpdateProfile> response) {
                dismissProgressDialog();
                ResUpdateProfile resUpdateProfile = response.body();
                if (resUpdateProfile != null) {
                    if (resUpdateProfile.getSuccess() == ServiceConstants.SUCCESS) {
                        mCheckedPos = position;
                        mAppSharedPreference.setString(PreferenceKeys.KEY_DISTANCE_UNIT, mDistanceUnitList.get(position));
                        mAppSharedPreference.setInt(PreferenceKeys.KEY_DISTANCE_UNIT_INDEX, position);
                        mDistanceUnitAdapter.setCheckedPos(mCheckedPos);
                        mDistanceUnitAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showShortToast(SettingsActivity.this, resUpdateProfile.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(SettingsActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResUpdateProfile> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(SettingsActivity.this, R.string.err_network_connection);
            }
        });
    }

    /**
     * Method used to call the update notification status
     */
    private void updateNotificationStatus(final boolean isNotificationOn) {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        Call<ResUpdateProfile> resUpdateProfileCall = client.updateSettingsWithoutImage(RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), MethodFactory.UPDATE_SETTINGS.getMethod()),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY)),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, "")),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), isNotificationOn ? "1" : "0"),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""));
        resUpdateProfileCall.enqueue(new Callback<ResUpdateProfile>() {
            @Override
            public void onResponse(Call<ResUpdateProfile> call, Response<ResUpdateProfile> response) {
                dismissProgressDialog();
                ResUpdateProfile resUpdateProfile = response.body();
                if (resUpdateProfile != null) {
                    if (resUpdateProfile.getSuccess() == ServiceConstants.SUCCESS) {
                        mAppSharedPreference.setBoolean(PreferenceKeys.KEY_NOTIFICATION_ON, isNotificationOn);
                    } else {
                        ToastUtils.showShortToast(SettingsActivity.this, resUpdateProfile.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(SettingsActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResUpdateProfile> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(SettingsActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (NetworkConnection.isNetworkConnected(this)) {
            if (mCheckedPos != position) {
                updateDistanceUnit(position);
            }
        } else {
            spnDistance.setSelection(mCheckedPos);
            ToastUtils.showShortToast(this, R.string.err_network_connection);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_change_password:
                if (!mAppSharedPreference.getBoolean(PreferenceKeys.KEY_CHANGE_PWD, true)) {
                    AppDialog.showCannotChangePasswordAlertDialog(this);
                } else {
                    if (tvChangePassword.isSelected()) {
                        tvChangePassword.setSelected(false);
                        llChangePassword.setVisibility(View.GONE);
                    } else {
                        tvChangePassword.setSelected(true);
                        llChangePassword.setVisibility(View.VISIBLE);
                    }
                }

                break;
            case R.id.switch_notifications:
                if (NetworkConnection.isNetworkConnected(this)) {
                    updateNotificationStatus(switchNotifications.isChecked());
                } else {
                    switchNotifications.setChecked(!switchNotifications.isChecked());
                    ToastUtils.showShortToast(this, R.string.err_network_connection);
                }
                break;
            case R.id.iv_hide_show_new_password:
                if (ivHideShowNewPassword.isSelected()) {
                    ivHideShowNewPassword.setSelected(false);
                    etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etNewPassword.setSelection(etNewPassword.getText().toString().length());
                } else {
                    ivHideShowNewPassword.setSelected(true);
                    etNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etNewPassword.setSelection(etNewPassword.getText().toString().length());
                }
                break;
            case R.id.iv_hide_show_old_password:
                if (ivHideShowOldPassword.isSelected()) {
                    ivHideShowOldPassword.setSelected(false);
                    etOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etOldPassword.setSelection(etOldPassword.getText().toString().length());
                } else {
                    ivHideShowOldPassword.setSelected(true);
                    etOldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etOldPassword.setSelection(etOldPassword.getText().toString().length());
                }
                break;
            case R.id.iv_back:
                AppUtilMethods.hideKeyBoard(this);
                AppUtilMethods.hideKeyBoard(this, view);
                onBackPressed();
                break;
            case R.id.tv_about_us:
                intent = new Intent(SettingsActivity.this, WebViewActivity.class);
                intent.putExtra(AppConstants.TAG_TITLE_RESOURCE_ID, R.string.txt_about_us);
                intent.putExtra(AppConstants.TAG_URL, AppConstants.URL_ABOUT_US);
                startActivity(intent);
                break;
            case R.id.tv_privacy_policy:
                intent = new Intent(SettingsActivity.this, WebViewActivity.class);
                intent.putExtra(AppConstants.TAG_TITLE_RESOURCE_ID, R.string.txt_privacy_policy);
                intent.putExtra(AppConstants.TAG_URL, AppConstants.URL_PRIVACY_POLICY);
                startActivity(intent);
                break;
            case R.id.tv_terms_of_use:
                intent = new Intent(SettingsActivity.this, WebViewActivity.class);
                intent.putExtra(AppConstants.TAG_TITLE_RESOURCE_ID, R.string.txt_terms_of_use);
                intent.putExtra(AppConstants.TAG_URL, AppConstants.URL_TERMS_OF_USE);
                startActivity(intent);
                break;
            case R.id.tv_refund_policy:
                intent = new Intent(SettingsActivity.this, WebViewActivity.class);
                intent.putExtra(AppConstants.TAG_TITLE_RESOURCE_ID, R.string.txt_refund_policy);
                intent.putExtra(AppConstants.TAG_URL, AppConstants.URL_REFUND_POLICY);
                startActivity(intent);
                break;
            case R.id.btn_done:
                if (isValid()) {
                    if (NetworkConnection.isNetworkConnected(this)) {
                        changePassword();
                    } else {
                        ToastUtils.showShortToast(this, R.string.err_network_connection);
                    }
                }
                break;
        }
    }
}
