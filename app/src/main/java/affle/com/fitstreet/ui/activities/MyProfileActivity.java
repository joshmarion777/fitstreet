package affle.com.fitstreet.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.request.ReqGetUserDetails;
import affle.com.fitstreet.models.response.ResGetUserDetails;
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
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_profile_pic)
    ImageView ivProfilePic;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_mobile_no)
    TextView tvMobileNo;
    @BindView(R.id.tv_dob)
    TextView tvDob;
    @BindView(R.id.tv_email_id)
    TextView tvEmailId;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_my_profile);

        //set listeners
        ivBack.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        if (NetworkConnection.isNetworkConnected(this)) {
            getUserDetails();
        } else {
            setProfileData();
//            ToastUtils.showShortToast(this, R.string.err_network_connection);
        }
    }

    /**
     * Method used to set profile data on the UI
     */
    private void setProfileData() {
        tvName.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_USERNAME, ""));
        tvGender.setText(AppUtilMethods.getGender(MyProfileActivity.this, Integer.parseInt(mAppSharedPreference.getString(PreferenceKeys.KEY_GENDER, "0"))));
        tvMobileNo.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_PHONE, ""));
        tvDob.setText(AppUtilMethods.getProfileDate(mAppSharedPreference.getString(PreferenceKeys.KEY_DOB, "")));
        tvEmailId.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_EMAIL_ID, ""));
        tvLocation.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_LOCATION, ""));
        if (!mAppSharedPreference.getString(PreferenceKeys.KEY_HEIGHT, "").isEmpty()) {
            int heightTotal = Integer.parseInt(mAppSharedPreference.getString(PreferenceKeys.KEY_HEIGHT, "0"));
            if (heightTotal > 0) {
                int heightFt = heightTotal / 12;
                int heightIn = heightTotal % 12;
                tvHeight.setText(String.valueOf(heightFt) + "'" + String.valueOf(heightIn) + "\"");
            }
        }
        if (!mAppSharedPreference.getString(PreferenceKeys.KEY_WEIGHT, "").isEmpty()) {
            int weight = Integer.parseInt(mAppSharedPreference.getString(PreferenceKeys.KEY_WEIGHT, "0"));
            tvWeight.setText(weight > 0 ? String.valueOf(weight) : "");
        }
        Glide.with(MyProfileActivity.this)
                .load(mAppSharedPreference.getString(PreferenceKeys.KEY_IMAGE, ""))
                .centerCrop()
                .placeholder(R.drawable.default_pic)
                .bitmapTransform(new CropCircleTransformation(MyProfileActivity.this))
                .into(ivProfilePic);
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
    private void getUserDetails() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        ReqGetUserDetails reqGetUserDetails = new ReqGetUserDetails();
        reqGetUserDetails.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqGetUserDetails.setMethod(MethodFactory.GET_USER_DETAILS.getMethod());
        reqGetUserDetails.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        Call<ResGetUserDetails> resGetUserDetailsCall = client.getUserDetails(reqGetUserDetails);
        resGetUserDetailsCall.enqueue(new Callback<ResGetUserDetails>() {
            @Override
            public void onResponse(Call<ResGetUserDetails> call, Response<ResGetUserDetails> response) {
                dismissProgressDialog();
                ResGetUserDetails resGetUserDetails = response.body();
                if (resGetUserDetails != null) {
                    if (resGetUserDetails.getSuccess() == ServiceConstants.SUCCESS) {
                        ResGetUserDetails.ResUserData userData = resGetUserDetails.getUserData();
                        mAppSharedPreference.setString(PreferenceKeys.KEY_USER_ID, userData.getUserID());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, userData.getIsEmailVerified());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_REGISTRATION_TYPE, userData.getRegistrationType());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_STATUS, userData.getStatus());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_IMAGE, userData.getImage());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_USERNAME, userData.getName());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_GENDER, userData.getGender());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_PHONE, userData.getPhone());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_DOB, userData.getDob());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_EMAIL_ID, userData.getEmailID());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_LOCATION, userData.getLocationName());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_HEIGHT, userData.getHeight());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_WEIGHT, userData.getWeight());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_HOME_IMAGE, userData.getHomeScreen());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_DISTANCE_UNIT, AppUtilMethods.getDistanceUnit(MyProfileActivity.this, Integer.parseInt(userData.getUnit())));
                        mAppSharedPreference.setInt(PreferenceKeys.KEY_DISTANCE_UNIT_INDEX, Integer.parseInt(userData.getUnit()));
                        mAppSharedPreference.setBoolean(PreferenceKeys.KEY_NOTIFICATION_ON, userData.getNotification().equals(ServiceConstants.TRUE));
                        mAppSharedPreference.setString(PreferenceKeys.KEY_POINTS, userData.getTotalPoints());
                        setProfileData();
                    } else {
                        ToastUtils.showShortToast(MyProfileActivity.this, resGetUserDetails.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(MyProfileActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResGetUserDetails> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(MyProfileActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                startActivityForResult(new Intent(MyProfileActivity.this, EditProfileActivity.class), AppConstants.RC_EDIT_PROFILE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_EDIT_PROFILE && resultCode == RESULT_OK) {
            setProfileData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
