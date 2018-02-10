package affle.com.fitstreet.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.AnimatingLinearLayout;
import affle.com.fitstreet.customviews.WheelView;
import affle.com.fitstreet.imagechooser.ImageChooser;
import affle.com.fitstreet.interfaces.IOnDateSet;
import affle.com.fitstreet.models.PlaceModel;
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
import affle.com.fitstreet.utils.CameraAndGalleryUtils;
import affle.com.fitstreet.utils.Logger;
import affle.com.fitstreet.utils.PermissionUtils;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends BaseActivity implements IOnDateSet {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_profile_pic)
    ImageView ivProfilePic;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.et_mobile_no)
    EditText etMobileNo;
    @BindView(R.id.et_dob)
    EditText etDob;
    @BindView(R.id.tv_email_id)
    TextView tvEmailId;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.et_height_ft)
    EditText etHeightFt;
    @BindView(R.id.et_height_in)
    EditText etHeightIn;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.all_feet)
    AnimatingLinearLayout allFeet;
    @BindView(R.id.all_inches)
    AnimatingLinearLayout allInches;
    @BindView(R.id.wv_feet)
    WheelView wvFeet;
    @BindView(R.id.wv_inches)
    WheelView wvInches;
    @BindView(R.id.tv_set_feet)
    TextView tvSetFeet;
    @BindView(R.id.tv_set_inches)
    TextView tvSetInches;
    @BindView(R.id.tv_cancel_feet)
    TextView tvCancelFeet;
    @BindView(R.id.tv_cancel_inches)
    TextView tvCancelInches;
    @BindView(R.id.sv_edit_profile)
    ScrollView svEditProfile;
    @BindView(R.id.rl_wheel_ft)
    RelativeLayout rlWheelHeight;
    private String mName, mMobileNo, mDob, mLocation, mHeightFt, mHeightIn, mWeight, mImagePath;
    private int mHeight;
    private ProgressDialog mProgressDialog;
    private int mHeightPopupHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        super.initData();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_my_profile);
        etName.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_USERNAME, ""));
        tvGender.setText(AppUtilMethods.getGender(EditProfileActivity.this, Integer.parseInt(mAppSharedPreference.getString(PreferenceKeys.KEY_GENDER, "0"))));
        etMobileNo.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_PHONE, ""));
        etDob.setText(AppUtilMethods.getProfileDate(mAppSharedPreference.getString(PreferenceKeys.KEY_DOB, "")));
        tvEmailId.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_EMAIL_ID, ""));
        etLocation.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_LOCATION, ""));
        if (!mAppSharedPreference.getString(PreferenceKeys.KEY_HEIGHT, "").isEmpty()) {
            int heightTotal = Integer.parseInt(mAppSharedPreference.getString(PreferenceKeys.KEY_HEIGHT, "0"));
            if (heightTotal > 0) {
                mHeightFt = String.valueOf(heightTotal / 12);
                mHeightIn = String.valueOf(heightTotal % 12);
                etHeightFt.setText(mHeightFt + "'");
                etHeightIn.setText(mHeightIn + "\"");
            }
        }
        if (!mAppSharedPreference.getString(PreferenceKeys.KEY_WEIGHT, "").isEmpty()) {
            int weight = Integer.parseInt(mAppSharedPreference.getString(PreferenceKeys.KEY_WEIGHT, "0"));
            etWeight.setText(weight > 0 ? String.valueOf(weight) : "");
        }
        Glide.with(EditProfileActivity.this)
                .load(mAppSharedPreference.getString(PreferenceKeys.KEY_IMAGE, ""))
                .centerCrop()
                .placeholder(R.drawable.default_pic)
                .bitmapTransform(new CropCircleTransformation(EditProfileActivity.this))
                .into(ivProfilePic);
        wvFeet.setOffset(1);
        wvFeet.setItems(Arrays.asList(getResources().getStringArray(R.array.arr_feet)));
        wvInches.setOffset(1);
        wvInches.setItems(Arrays.asList(getResources().getStringArray(R.array.arr_inches)));

        rlWheelHeight.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mHeightPopupHeight = rlWheelHeight.getMeasuredHeight();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                allFeet.setVisibility(View.GONE);
                allInches.setVisibility(View.GONE);
            }
        }, 500);

        //set listeners
        tvSave.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        etLocation.setOnClickListener(this);
        ivProfilePic.setOnClickListener(this);
        etDob.setOnClickListener(this);
        etHeightFt.setOnClickListener(this);
        etHeightIn.setOnClickListener(this);
        tvSetFeet.setOnClickListener(this);
        tvSetInches.setOnClickListener(this);
        tvCancelFeet.setOnClickListener(this);
        tvCancelInches.setOnClickListener(this);
        wvFeet.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Logger.e("selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });
        wvInches.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Logger.e("selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });
    }

    @Override
    protected void initVariables() {
        mImagePath = mAppSharedPreference.getString(PreferenceKeys.KEY_IMAGE, "");
    }

    /**
     * This method is used to open the camera
     *
     * @param intent
     * @return void
     */
    public void getImageFromCamera(Intent intent) {
        CameraAndGalleryUtils.getImageFromCamera(intent, this);
    }

    /**
     * This method is used to open the gallery
     *
     * @param intent
     * @return void
     */
    public void getImageFromGallery(Intent intent) {
        CameraAndGalleryUtils.getImageFromGallery(intent, this);
    }

    /**
     * This method is used to set the profile image
     *
     * @return void
     */
    private void setProfileImage() {
        Glide.with(EditProfileActivity.this)
                .load(mImagePath)
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.default_pic)
                .bitmapTransform(new CropCircleTransformation(EditProfileActivity.this))
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
     * Checking validity of data
     *
     * @return
     */
    private boolean isValid() {
        mName = etName.getText().toString().trim();
        mMobileNo = etMobileNo.getText().toString().trim();
        mDob = etDob.getText().toString().trim();
        mLocation = etLocation.getText().toString().trim();
        mHeightFt = etHeightFt.getText().toString().trim().replace("'", "");
        mHeightIn = etHeightIn.getText().toString().trim().replace("\"", "");
        Logger.e("Feet -----> " + mHeightFt + "            Inches -----> " + mHeightIn);
        mWeight = etWeight.getText().toString().trim();
//        if (mImagePath == null || mImagePath.isEmpty()) {
//            ToastUtils.showShortToast(this, R.string.err_image_empty);
//            return false;
//        }
        if (mName.isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_name_empty);
            return false;
        }
//        if (mMobileNo.isEmpty()) {
//            ToastUtils.showShortToast(this, R.string.err_mobile_empty);
//            return false;
//        }
        if (!mMobileNo.isEmpty() && mMobileNo.length() < 10) {
            ToastUtils.showShortToast(this, R.string.err_mobile_invalid);
            return false;
        }
//        if (mDob.isEmpty()) {
//            ToastUtils.showShortToast(this, R.string.err_dob_empty);
//            return false;
//        }
//        if (mLocation.isEmpty()) {
//            ToastUtils.showShortToast(this, R.string.err_loc_empty);
//            return false;
//        }
        /*if (mHeightFt.isEmpty()){
            ToastUtils.showShortToast(this, R.string.err_height_ft_empty);
            return false;
        }
        if (mHeightIn.isEmpty()){
            ToastUtils.showShortToast(this, R.string.err_height_in_empty);
            return false;
        }*/
//        if (mHeightFt.isEmpty() || mHeightIn.isEmpty()) {
//            ToastUtils.showShortToast(this, R.string.err_height_empty);
//            return false;
//        } else {
//            mHeight = (Integer.parseInt(mHeightFt) * 12) + Integer.parseInt(mHeightIn);
//        }
        if (!mHeightFt.isEmpty() && !mHeightIn.isEmpty()) {
            mHeight = (Integer.parseInt(mHeightFt) * 12) + Integer.parseInt(mHeightIn);
        }
//        if (mWeight.isEmpty()) {
//            ToastUtils.showShortToast(this, R.string.err_weight_empty);
//            return false;
//        }
        return true;
    }

    /**
     * Method used to call the update profile web service
     */
    private void updateProfile() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        File imageFile = new File(mImagePath);
        Call<ResUpdateProfile> resUpdateProfileCall = client.updateProfile(RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), MethodFactory.UPDATE_PROFILE.getMethod()),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY)),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, "")),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mName),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mMobileNo),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), AppUtilMethods.getServerDate(mDob)),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mLocation),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), String.valueOf(mHeight)),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mWeight),
                MultipartBody.Part.createFormData("image", imageFile.getName(), RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), imageFile)));
        resUpdateProfileCall.enqueue(new Callback<ResUpdateProfile>() {
            @Override
            public void onResponse(Call<ResUpdateProfile> call, Response<ResUpdateProfile> response) {
                dismissProgressDialog();
                ResUpdateProfile resUpdateProfile = response.body();
                if (resUpdateProfile != null) {
                    if (resUpdateProfile.getSuccess() == ServiceConstants.SUCCESS) {
                        mAppSharedPreference.setString(PreferenceKeys.KEY_IMAGE, resUpdateProfile.getImgUrl());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_USERNAME, mName);
                        mAppSharedPreference.setString(PreferenceKeys.KEY_PHONE, mMobileNo);
                        mAppSharedPreference.setString(PreferenceKeys.KEY_DOB, AppUtilMethods.getServerDate(mDob));
                        mAppSharedPreference.setString(PreferenceKeys.KEY_LOCATION, mLocation);
                        mAppSharedPreference.setString(PreferenceKeys.KEY_HEIGHT, String.valueOf(mHeight));
                        mAppSharedPreference.setString(PreferenceKeys.KEY_WEIGHT, mWeight);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        ToastUtils.showShortToast(EditProfileActivity.this, resUpdateProfile.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(EditProfileActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResUpdateProfile> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(EditProfileActivity.this, R.string.err_network_connection);
            }
        });
    }

    /**
     * Method used to call the update profile web service if image is not updated
     */
    private void updateProfileWithoutImage() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        Call<ResUpdateProfile> resUpdateProfileCall = client.updateProfileWithoutImage(RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), MethodFactory.UPDATE_PROFILE.getMethod()),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY)),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, "")),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mName),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mMobileNo),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), AppUtilMethods.getServerDate(mDob)),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mLocation),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), String.valueOf(mHeight)),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mWeight),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""));
        resUpdateProfileCall.enqueue(new Callback<ResUpdateProfile>() {
            @Override
            public void onResponse(Call<ResUpdateProfile> call, Response<ResUpdateProfile> response) {
                dismissProgressDialog();
                ResUpdateProfile resUpdateProfile = response.body();
                if (resUpdateProfile != null) {
                    if (resUpdateProfile.getSuccess() == ServiceConstants.SUCCESS) {
//                        mAppSharedPreference.setString(PreferenceKeys.KEY_IMAGE, resUpdateProfile.getImgUrl());
                        mAppSharedPreference.setString(PreferenceKeys.KEY_USERNAME, mName);
                        mAppSharedPreference.setString(PreferenceKeys.KEY_PHONE, mMobileNo);
                        mAppSharedPreference.setString(PreferenceKeys.KEY_DOB, AppUtilMethods.getServerDate(mDob));
                        mAppSharedPreference.setString(PreferenceKeys.KEY_LOCATION, mLocation);
                        mAppSharedPreference.setString(PreferenceKeys.KEY_HEIGHT, String.valueOf(mHeight));
                        mAppSharedPreference.setString(PreferenceKeys.KEY_WEIGHT, mWeight);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        ToastUtils.showShortToast(EditProfileActivity.this, resUpdateProfile.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(EditProfileActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResUpdateProfile> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(EditProfileActivity.this, R.string.err_network_connection);
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
        AppUtilMethods.hideKeyBoard(this);
        AppUtilMethods.hideKeyBoard(this, view);
        boolean animate = true;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.et_location:
                if (NetworkConnection.isNetworkConnected(this)) {
                    if (PermissionUtils.hasReadGServicesPermission(this)) {
                        Intent intent = new Intent(EditProfileActivity.this, ChooseLocationActivity.class);
                        intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_PROFILE);
                        startActivityForResult(intent, AppConstants.RC_CHOOSE_LOCATION);
                    }
                }
                break;
            case R.id.iv_profile_pic:
                if (PermissionUtils.hasStoragePermission(this))
                    new ImageChooser(this, null).openMediaSelector();
                break;
            case R.id.tv_save:
                if (isValid()) {
                    if (NetworkConnection.isNetworkConnected(this)) {
                        if (mImagePath.startsWith("http") || mImagePath.isEmpty())
                            updateProfileWithoutImage();
                        else
                            updateProfile();
                    } else {
                        ToastUtils.showShortToast(this, R.string.err_network_connection);
                    }
                }
                break;
            case R.id.et_dob:
                AppDialog.showDatePickerDialog(this, this, false);
                break;
            case R.id.et_height_ft:
                if (allInches.isSelected()) {
                    allInches.hide();
                    allInches.setSelected(false);
                    animate = false;
                }
                if (!allFeet.isSelected()) {
                    allFeet.show(true, svEditProfile, animate ? mHeightPopupHeight : 0);
                    allFeet.setSelected(true);
                }
                break;
            case R.id.et_height_in:
                if (allFeet.isSelected()) {
                    allFeet.hide();
                    allFeet.setSelected(false);
                    animate = false;
                }
                if (!allInches.isSelected()) {
                    allInches.show(true, svEditProfile, animate ? mHeightPopupHeight : 0);
                    allInches.setSelected(true);
                }
                break;
            case R.id.tv_set_feet:
                etHeightFt.setText(wvFeet.getSeletedItem() + "'");
                if (allFeet.isSelected()) {
                    allFeet.hide(true, null, svEditProfile, mHeightPopupHeight);
                }
                break;
            case R.id.tv_cancel_feet:
                if (allFeet.isSelected()) {
                    allFeet.hide(true, null, svEditProfile, mHeightPopupHeight);
                }
                break;
            case R.id.tv_set_inches:
                etHeightIn.setText(wvInches.getSeletedItem() + "\"");
                if (allInches.isSelected()) {
                    allInches.hide(true, null, svEditProfile, mHeightPopupHeight);
                }
                break;
            case R.id.tv_cancel_inches:
                if (allInches.isSelected()) {
                    allInches.hide(true, null, svEditProfile, mHeightPopupHeight);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.RC_REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (permissions.length > 0) {
                        if (permissions[0].equals("com.google.android.providers.gsf.permission.READ_GSERVICES")) {
                            Intent intent = new Intent(EditProfileActivity.this, ChooseLocationActivity.class);
                            intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_PROFILE);
                            startActivityForResult(intent, AppConstants.RC_CHOOSE_LOCATION);
                        } else if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            new ImageChooser(this, null).openMediaSelector();
                        }
                    }
                } else {
                    // Permission Denied
                    ToastUtils.showShortToast(this, getString(R.string.err_permission_denied));
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_CHOOSE_LOCATION && resultCode == RESULT_OK) {
            PlaceModel placeModel = data.getParcelableExtra(AppConstants.TAG_PLACE_MODEL);
            etLocation.setText(placeModel.getName());
        } else if (requestCode == AppConstants.RC_GALLERY_INTENT && resultCode == RESULT_OK) {
            Uri mImageCaptureUri = data.getData();
            Cursor cursor = getContentResolver().query(mImageCaptureUri, new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(0);
            mImagePath = AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_IMAGE_PATH;
            Bitmap bitmap = CameraAndGalleryUtils.getImageBitmap(imagePath, CameraAndGalleryUtils.getRotationMatrix(CameraAndGalleryUtils.getOrientation(imagePath)), 300, 300);
            CameraAndGalleryUtils.saveImageToFile(bitmap, mImagePath);
            setProfileImage();
        } else if (requestCode == AppConstants.RC_CAMERA_INTENT && resultCode == RESULT_OK) {
            mImagePath = AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_IMAGE_PATH;
            Bitmap bitmap = CameraAndGalleryUtils.getImageBitmap(mImagePath, CameraAndGalleryUtils.getRotationMatrix(CameraAndGalleryUtils.getOrientation(mImagePath)), 300, 300);
            CameraAndGalleryUtils.saveImageToFile(bitmap, mImagePath);
            Logger.e("Path" + mImagePath);
            setProfileImage();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
