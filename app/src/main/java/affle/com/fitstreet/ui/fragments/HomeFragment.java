package affle.com.fitstreet.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.Locale;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.TabPagerAdapter;
import affle.com.fitstreet.customviews.CustomTypefaceButton;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.imagechooser.ImageChooser;
import affle.com.fitstreet.models.request.ReqFsPoints;
import affle.com.fitstreet.models.request.ReqUserCalorieDistance;
import affle.com.fitstreet.models.response.ResGetFsPoints;
import affle.com.fitstreet.models.response.ResUpdateProfile;
import affle.com.fitstreet.models.response.ResUserCalorieDistance;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.activities.CouponsActivity;
import affle.com.fitstreet.ui.activities.HomeActivity;
import affle.com.fitstreet.ui.activities.NotificationActivity;
import affle.com.fitstreet.ui.activities.RecommendedActivity;
import affle.com.fitstreet.ui.activities.ShareActivity;
import affle.com.fitstreet.ui.activities.TrendingActivity;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.CameraAndGalleryUtils;
import affle.com.fitstreet.utils.PermissionUtils;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akash on 17/6/16.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.iv_image_at_top)
    ImageView ivImageAtTop;
    @BindView(R.id.iv_navigation_menu)
    ImageView ivNavigationSlideMenu;
    @BindView(R.id.toolbar)
    Toolbar customToolbar;
    @BindView(R.id.iv_upload)
    ImageView ivUpload;
    @BindView(R.id.btn_view_all)
    CustomTypefaceButton btnViewAll;
    @BindView(R.id.iv_notification)
    ImageView ivNotification;
    @BindView(R.id.tv_distance_unit)
    TextView tvDistanceUnit;
    @BindView(R.id.rl_fitness_info)
    RelativeLayout rlFitnessInfo;
    @BindView(R.id.tv_points)
    TextView tvPoints;
    @BindView(R.id.tv_distance)
    CustomTypefaceTextView tvDistance;
    @BindView(R.id.tv_calories)
    CustomTypefaceTextView tvCalories;
    @BindView(R.id.tv_title)
    CustomTypefaceTextView tvTitle;
    @BindView(R.id.tv_notification_count)
    CustomTypefaceTextView tvNotificationCount;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.ToDoList)
    NestedScrollView ToDoList;
    private int COUPONS_ACTIVITY = 3001;
    private int RECOMMENDED_ACTIVITY = 3002;
    private int TRENDING_ACTIVITY = 3003;
    private int width;
    private int height;
    private ProgressDialog mProgressDialog;
    private String mImagePath;
    private String mCurrentTab;
    private ResUserCalorieDistance mResGetFsPoints;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getInt(AppConstants.TAG_FROM_WHERE, 0) == 1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        openShareActivity();
                    }
                }, 100);
            }
        }
    }

    @Override
    protected void initViews() {
        ivNavigationSlideMenu.setOnClickListener(this);
        ((HomeActivity) mActivity).setVisibilityBottomLayout(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(customToolbar);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.c_hint_et_login_sign_up), ContextCompat.getColor(mActivity, R.color.c_dark_grey));
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        ivImageAtTop.getLayoutParams().height = (int) (height / (2.5));

        btnViewAll.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        tabLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        int statusBarHeight = getStatusBarHeight();
        int topImageHeight = (int) (height / (2.5));
        int bottomTabsHeight = (int) getResources().getDimension(R.dimen.d_bottom_layout_height);
        int viewAllHeight = btnViewAll.getMeasuredHeight();
        int tabLayoutHeight = tabLayout.getMeasuredHeight();
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int viewPagerHeight = screenHeight - (statusBarHeight + topImageHeight + bottomTabsHeight + viewAllHeight + tabLayoutHeight);
        LinearLayout.LayoutParams pagerParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
        pagerParams.height = viewPagerHeight;
        mCurrentTab = (viewPager.getAdapter().getPageTitle(viewPager.getCurrentItem())).toString();

        if (!mAppSharedPreference.getString(PreferenceKeys.KEY_HOME_IMAGE, "").isEmpty()) {
            Glide.with(mActivity)
                    .load(mAppSharedPreference.getString(PreferenceKeys.KEY_HOME_IMAGE, ""))
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .into(ivImageAtTop);
        }
        mCurrentTab = (viewPager.getAdapter().getPageTitle(viewPager.getCurrentItem())).toString();
        AppUtilMethods.overrideFonts(mActivity, tabLayout);

        //set listeners
        tabLayout.setOnTabSelectedListener(this);
        btnViewAll.setOnClickListener(this);
        ivUpload.setOnClickListener(this);
        tabLayout.setOnTabSelectedListener(this);
        ivNotification.setOnClickListener(this);
        tvNotificationCount.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        updateUserData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initVariables() {
        if (NetworkConnection.isNetworkConnected(mActivity))
            getFSPoints();
    }

    /**
     * Updating user distance,fs-points and calories
     */
    public void updateUserData() {
        tvDistanceUnit.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_DISTANCE_UNIT, ""));
        tvPoints.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_POINTS, "0"));
        tvCalories.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_CALORIES, "0"));
        tvDistance.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_DISTANCE, "0.0"));
    }

    /**
     * Getting user distance,fs-points and calories from server
     */
    private void getFSPoints() {
        IApiClient client = ApiClient.getApiClient();
        ReqUserCalorieDistance reqUserCalorieDistance = new ReqUserCalorieDistance();
        reqUserCalorieDistance.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqUserCalorieDistance.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqUserCalorieDistance.setMethod(MethodFactory.GET_USER_CALORIE_DISTANCE.getMethod());
        Call<ResUserCalorieDistance> resGetFsPoints = client.getUserCalorieDistance(reqUserCalorieDistance);
        if (mResGetFsPoints == null) {
            resGetFsPoints.enqueue(new Callback<ResUserCalorieDistance>() {
                @Override
                public void onResponse(Call<ResUserCalorieDistance> call, Response<ResUserCalorieDistance> response) {
                    mResGetFsPoints = response.body();
                    if (mResGetFsPoints != null) {
                        if (mResGetFsPoints.getSuccess() == ServiceConstants.SUCCESS) {
                            if (mResGetFsPoints.getTotalPoints().equals("")) {
                                mAppSharedPreference.setString(PreferenceKeys.KEY_POINTS, "0");
                            } else {
                                mAppSharedPreference.setString(PreferenceKeys.KEY_POINTS, mResGetFsPoints.getTotalPoints());
                            }
                            if (mResGetFsPoints.getCalories().equals("")) {
                                mAppSharedPreference.setString(PreferenceKeys.KEY_CALORIES, "0");
                            } else {
                                mAppSharedPreference.setString(PreferenceKeys.KEY_CALORIES, mResGetFsPoints.getCalories());
                            }
                            if (mResGetFsPoints.getDistance().equals("")) {
                                mAppSharedPreference.setString(PreferenceKeys.KEY_DISTANCE, "0");
                            } else {
                                mAppSharedPreference.setString(PreferenceKeys.KEY_DISTANCE, String.format(Locale.getDefault(), "%.1f", Float.parseFloat(mResGetFsPoints.getDistance())));
                            }
                            if (mResGetFsPoints.getNotificationCount().equals("")) {
                                mAppSharedPreference.setString(PreferenceKeys.KEY_POINTS, "0");
                            } else {
                                tvNotificationCount.setText(mResGetFsPoints.getNotificationCount());
                            }
                            if (mResGetFsPoints.getIsFitbitConnect().equals("1")) {
                                mAppSharedPreference.setString(PreferenceKeys.KEY_CONNECTED_APP, AppConstants.FITBIT_CONNECTED);
                            }
                            if (mResGetFsPoints.getIsRunkeeperConnect().equals("1")) {
                                mAppSharedPreference.setString(PreferenceKeys.KEY_CONNECTED_APP, AppConstants.RUNKEEPER_CONNECTED);
                            }
                            mAppSharedPreference.setString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, mResGetFsPoints.getEmailStatus());
                        }
                    }
                    updateUserData();
                }

                @Override
                public void onFailure(Call<ResUserCalorieDistance> call, Throwable t) {
                    AppDialog.showProgressDialog(mActivity, false);
                    ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction;
        switch (v.getId()) {
            case R.id.iv_navigation_menu:
                ((HomeActivity) mActivity).showDrawer();
                break;
            case R.id.iv_fs_store_bottom:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {

                        //rlNavigationToolbarFavourite.setVisibility(View.VISIBLE);
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        ChallengesFragment challengesFragment = new ChallengesFragment();
                        //mCurrentFragment = favouriteFragment;
                        fragmentTransaction.replace(R.id.container_body, challengesFragment);
                        fragmentTransaction.commit();
                        ((HomeActivity) getActivity()).mCurrentFragment = challengesFragment;
                        //llToolbar.setVisibility(View.VISIBLE);
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(mActivity);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            case R.id.iv_notification:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    Intent intent;
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        intent = new Intent(mActivity, NotificationActivity.class);
                        startActivity(intent);
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(mActivity);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            case R.id.tv_notification_count:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    Intent intent;
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        intent = new Intent(mActivity, NotificationActivity.class);
                        startActivity(intent);
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(mActivity);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            case R.id.iv_upload:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        ((HomeActivity) mActivity).setmFromWhere(AppConstants.FROM_RESET_IMAGE);
                        if (PermissionUtils.hasStoragePermission(mActivity))
                            new ImageChooser(mActivity, this).openMediaSelector();
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(mActivity);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            case R.id.btn_view_all:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        if (mCurrentTab.equals(getString(R.string.txt_tabs_title_coupons))) {
                            Intent intent1 = new Intent(getActivity(), CouponsActivity.class);
                            startActivityForResult(intent1, COUPONS_ACTIVITY);
                            break;
                        } else if (mCurrentTab.equals(getString(R.string.txt_tabs_title_recommended))) {
                            Intent intent1 = new Intent(getActivity(), RecommendedActivity.class);
                            startActivityForResult(intent1, RECOMMENDED_ACTIVITY);
                            break;
                        } else if (mCurrentTab.equals(getString(R.string.txt_tabs_title_trending))) {
                            Intent intent1 = new Intent(getActivity(), TrendingActivity.class);
                            startActivityForResult(intent1, TRENDING_ACTIVITY);
                            break;
                        }
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(mActivity);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        mCurrentTab = (viewPager.getAdapter().getPageTitle(viewPager.getCurrentItem())).toString();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    /**
     * This method is used to open the camera
     *
     * @param intent
     * @return void
     */
    public void getImageFromCamera(Intent intent) {
        CameraAndGalleryUtils.getImageFromCamera(intent, mActivity);
    }

    /**
     * This method is used to open the gallery
     *
     * @param intent
     * @return void
     */
    public void getImageFromGallery(Intent intent) {
        CameraAndGalleryUtils.getImageFromGallery(intent, mActivity);
    }

    /**
     * Method used to reset home image
     */
    public void resetImage() {
        if (NetworkConnection.isNetworkConnected(mActivity)) {
            resetHomeScreenImage();
        } else {
            ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
        }
    }

    /**
     * This method is used to set the profile image
     *
     * @return void
     */
    public void setProfileImage(String imagePath) {
        mImagePath = imagePath;
        if (NetworkConnection.isNetworkConnected(mActivity)) {
            Glide.with(mActivity)
                    .load(mImagePath)
                    .centerCrop()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.img_placeholder)
                    .into(ivImageAtTop);
            updateHomeScreenImage();
        } else {
            ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
        }
    }

    /**
     * Method used to choose image
     */
    public void selectImage() {
        new ImageChooser(mActivity, this).openMediaSelector();
    }

    /**
     * Method used to open share activity to share image on Facebook
     */
    public void openShareActivity() {
        rlFitnessInfo.setDrawingCacheEnabled(true);
        rlFitnessInfo.buildDrawingCache(true);
        rlFitnessInfo.setDrawingCacheQuality(RelativeLayout.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(rlFitnessInfo.getDrawingCache());

        if (bitmap != null) {
            String imagePath = AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_FITNESS_IMAGE_PATH;
            CameraAndGalleryUtils.saveImageToFile(bitmap, imagePath);
            Intent intent = new Intent(mActivity, ShareActivity.class);
            intent.putExtra(AppConstants.TAG_IMAGE_PATH, imagePath);
            intent.putExtra(AppConstants.TAG_HEIGHT, (int) (height / (2.5)));
            startActivity(intent);
        }
        rlFitnessInfo.setDrawingCacheEnabled(false);
    }

    /**
     * This method is used to show the progress dialog
     *
     * @return void
     */
    private void showProgressDialog() {
        mProgressDialog = AppDialog.showProgressDialog(getActivity());
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
     * Method used to call the update profile web service
     */
    private void updateHomeScreenImage() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        File imageFile = new File(mImagePath);
        Call<ResUpdateProfile> resUpdateProfileCall = client.updateSettings(RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), MethodFactory.UPDATE_SETTINGS.getMethod()),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY)),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, "")),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""),
                MultipartBody.Part.createFormData("homeScreen", imageFile.getName(), RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), imageFile)));
        resUpdateProfileCall.enqueue(new Callback<ResUpdateProfile>() {
            @Override
            public void onResponse(Call<ResUpdateProfile> call, Response<ResUpdateProfile> response) {
                dismissProgressDialog();
                ResUpdateProfile resUpdateProfile = response.body();
                if (resUpdateProfile != null) {
                    if (resUpdateProfile.getSuccess() == ServiceConstants.SUCCESS) {
                        mAppSharedPreference.setString(PreferenceKeys.KEY_HOME_IMAGE, resUpdateProfile.getImgUrl());
                        if (!mAppSharedPreference.getString(PreferenceKeys.KEY_HOME_IMAGE, "").isEmpty()) {
                            Glide.with(mActivity)
                                    .load(mAppSharedPreference.getString(PreferenceKeys.KEY_HOME_IMAGE, ""))
                                    .centerCrop()
                                    .placeholder(R.drawable.img_placeholder)
                                    .into(ivImageAtTop);
                        }
                    } else {
                        ToastUtils.showShortToast(mActivity, resUpdateProfile.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResUpdateProfile> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
            }
        });
    }

    /**
     * Method used to call the reset home screen image service
     */
    private void resetHomeScreenImage() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        Call<ResUpdateProfile> resUpdateProfileCall = client.updateSettingsWithoutImage(RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), MethodFactory.UPDATE_SETTINGS.getMethod()),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY)),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, "")),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), "1"),
                RequestBody.create(MediaType.parse(ServiceConstants.TYPE_MULTIPART), ""));
        resUpdateProfileCall.enqueue(new Callback<ResUpdateProfile>() {
            @Override
            public void onResponse(Call<ResUpdateProfile> call, Response<ResUpdateProfile> response) {
                dismissProgressDialog();
                ResUpdateProfile resUpdateProfile = response.body();
                if (resUpdateProfile != null) {
                    if (resUpdateProfile.getSuccess() == ServiceConstants.SUCCESS) {
                        mAppSharedPreference.setString(PreferenceKeys.KEY_HOME_IMAGE, resUpdateProfile.getImgUrl());
                        if (!mAppSharedPreference.getString(PreferenceKeys.KEY_HOME_IMAGE, "").isEmpty()) {
                            Glide.with(mActivity)
                                    .load(mAppSharedPreference.getString(PreferenceKeys.KEY_HOME_IMAGE, ""))
                                    .centerCrop()
                                    .placeholder(R.drawable.img_placeholder)
                                    .into(ivImageAtTop);
                        }
                    } else {
                        ToastUtils.showShortToast(mActivity, resUpdateProfile.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResUpdateProfile> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
