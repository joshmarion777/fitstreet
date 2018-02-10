package affle.com.fitstreet.ui.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.activities.CashbackHistoryActivity;
import affle.com.fitstreet.ui.activities.EditProfileActivity;
import affle.com.fitstreet.ui.activities.HomeActivity;
import affle.com.fitstreet.ui.activities.MyAddressesActivity;
import affle.com.fitstreet.ui.activities.MyOrdersActivity;
import affle.com.fitstreet.ui.activities.MyProfileActivity;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.CustomBlurTransForm;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by akash on 17/6/16.
 */
public class MyAccountFragment extends BaseFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @BindView(R.id.tv_profile)
    TextView tvProfile;
    @BindView(R.id.tv_addresses)
    TextView tvAddresses;
    @BindView(R.id.iv_blur_profile_image)
    ImageView ivProfilePicBlur;
    @BindView(R.id.iv_profile)
    ImageView ivProfilePic;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_redeem_history)
    CustomTypefaceTextView tvRedeemHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initViews() {
        ((HomeActivity) mActivity).setActionBarTitle(R.string.txt_my_account);
        setProfileData();

        //set listener
        ((HomeActivity) mActivity).setVisibilityBottomLayout(false);
        tvProfile.setOnClickListener(this);
        tvAddresses.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        ivProfilePic.setOnClickListener(this);
        tvRedeemHistory.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mAppSharedPreference.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAppSharedPreference.getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setProfileData() {
        Glide.with(this)
                .load(mAppSharedPreference.getString(PreferenceKeys.KEY_IMAGE, ""))
                .centerCrop()
                .bitmapTransform(new CustomBlurTransForm(mActivity))
                .into(ivProfilePicBlur);

        Glide.with(this)
                .load(mAppSharedPreference.getString(PreferenceKeys.KEY_IMAGE, ""))
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .bitmapTransform(new CropCircleTransformation(mActivity))
                .into(ivProfilePic);

        tvName.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_USERNAME, ""));
//        tvLocation.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_EMAIL_ID, ""));
        tvLocation.setText("");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        setProfileData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_profile:
                startActivity(new Intent(mActivity, MyProfileActivity.class));
                break;
            case R.id.tv_addresses:
                startActivity(new Intent(mActivity, MyAddressesActivity.class));
                break;
            case R.id.tv_redeem_history:
                Intent intent = new Intent(mActivity, CashbackHistoryActivity.class);
                intent.putExtra("fromMyAccount", true);
                startActivity(intent);
                break;
            case R.id.btn_logout:
                AppDialog.showLogoutAlertDialog(mActivity);
                break;
            case R.id.iv_profile:
                startActivityForResult(new Intent(mActivity, EditProfileActivity.class), AppConstants.RC_EDIT_PROFILE);
                break;
            case R.id.tv_order:
                startActivity(new Intent(mActivity, MyOrdersActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_EDIT_PROFILE && resultCode == mActivity.RESULT_OK) {
            setProfileData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}