package affle.com.fitstreet.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import affle.com.fitstreet.R;
import affle.com.fitstreet.appsanddevices.fitbit.ManagerFitbit;
import affle.com.fitstreet.customviews.arclayout.ArcLayout;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.fragments.ChallengesFragment;
import affle.com.fitstreet.ui.fragments.ConnectAppsAndDevicesFragment;
import affle.com.fitstreet.ui.fragments.FavouriteFragment;
import affle.com.fitstreet.ui.fragments.FsStoreFragment;
import affle.com.fitstreet.ui.fragments.HelpAndSupportFragment;
import affle.com.fitstreet.ui.fragments.HomeFragment;
import affle.com.fitstreet.ui.fragments.MyAccountFragment;
import affle.com.fitstreet.ui.fragments.MyCartFragmentNew;
import affle.com.fitstreet.ui.fragments.MyWalletFragment;
import affle.com.fitstreet.ui.fragments.RedeemFsPointsFragment;
import affle.com.fitstreet.utils.AnimatorUtils;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.CameraAndGalleryUtils;
import affle.com.fitstreet.utils.CustomTypefaceSpanNavigationDrawer;
import affle.com.fitstreet.utils.Logger;
import affle.com.fitstreet.utils.PermissionUtils;
import affle.com.fitstreet.utils.ServiceUtils;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {
    public Fragment mCurrentFragment;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.custom_toolbar)
    Toolbar customToolbar;
    @BindView(R.id.iv_navigation_menu)
    ImageView ivNavigationMenu;
    @BindView(R.id.iv_toolbar_favourite)
    ImageView ivNavigationToolbarFavourite;
    @BindView(R.id.rl_toolbar_favourite)
    RelativeLayout rlNavigationToolbarFavourite;
    @BindView(R.id.rl_parent_layout_arc_menu)
    RelativeLayout rlParentLayoutArcMenu;
    @BindView(R.id.tv_toolbar_cart_count)
    affle.com.fitstreet.customviews.CustomTypefaceTextView tvToolbarCartCount;
    @BindView(R.id.fab)
    ImageView fab;
    @BindView(R.id.tv_toolbar_favourite_count)
    affle.com.fitstreet.customviews.CustomTypefaceTextView tvToolbarCount;
    @BindView(R.id.iv_toolbar_share)
    ImageView ivToolbarShare;
    @BindView(R.id.arc_layout)
    ArcLayout arcLayout;
    @BindView(R.id.menu_layout)
    View menuLayout;
    @BindView(R.id.tv_workout)
    TextView tvWorkout;
    @BindView(R.id.tv_share_activities)
    TextView tvShareActivities;
    @BindView(R.id.tv_invite_friends)
    TextView tvInviteFriends;
    @BindView(R.id.ll_bottom_layout)
    LinearLayout llBottomLayout;
    @BindView(R.id.iv_home_bottom)
    ImageView ivHomeBottom;
    @BindView(R.id.iv_trophy_bottom)
    ImageView ivTrophyBottom;
    @BindView(R.id.iv_fs_store_bottom)
    ImageView ivFsStoreBottom;
    @BindView(R.id.iv_wallet_bottom)
    ImageView ivWalletBottom;
    @BindView(R.id.ll_view_all)
    LinearLayout llViewAll;
    @BindView(R.id.rl_toolbar_mycart)
    RelativeLayout rlToolbarMycart;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    private Stack<Fragment> mFragmentStack;
    private ImageView ivProfilePicBlur;
    private ImageView mIvProfilePic;
    private TextView mTvName;
    private TextView mTvLocation;
    private LinearLayout mLlLoginSignUp;
    private TextView mTvLogin;
    private TextView mTvSignUp;
    private int mFromWhere = -1;
    private int fromWhere;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.initData();
    }

    @Override
    protected void initViews() {
        android.support.v4.app.FragmentTransaction fragmentTransaction;
        ivHomeBottom.setSelected(true);
        mFragmentStack = new Stack<>();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        setSupportActionBar(customToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(
                this, drawer, customToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        navigationView.setItemIconTintList(null);
        HomeFragment homeFragment = new HomeFragment();
        mCurrentFragment = homeFragment;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, homeFragment);
        //mFragmentStack.push(homeFragment);
        navigationView.getMenu().getItem(0).setChecked(true);
        fragmentTransaction.commit();
        customToolbar.setVisibility(View.GONE);
        View headerLayout = navigationView.getHeaderView(0); // 0-index header
        ivProfilePicBlur = (ImageView) headerLayout.findViewById(R.id.iv_blur_profile_image);
        mIvProfilePic = (ImageView) headerLayout.findViewById(R.id.iv_profile);
        mTvName = (TextView) headerLayout.findViewById(R.id.tv_name);
        mTvLocation = (TextView) headerLayout.findViewById(R.id.tv_location);
        mTvLogin = (TextView) headerLayout.findViewById(R.id.tv_login);
        mTvSignUp = (TextView) headerLayout.findViewById(R.id.tv_sign_up);
        mLlLoginSignUp = (LinearLayout) headerLayout.findViewById(R.id.ll_login_sign_up);
        tvWorkout.setOnClickListener(this);
        ivFsStoreBottom.setOnClickListener(this);
        ivHomeBottom.setOnClickListener(this);
        ivTrophyBottom.setOnClickListener(this);
        ivWalletBottom.setOnClickListener(this);
        tvShareActivities.setOnClickListener(this);
        rlToolbarMycart.setOnClickListener(this);
        tvInviteFriends.setOnClickListener(this);
        rlNavigationToolbarFavourite.setOnClickListener(this);
        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(this);
        }
        menuLayout.setOnClickListener(this);
        fab.setOnClickListener(this);
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            //for applying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            //the method we have created in activity
            applyFontToMenuItem(mi);
        }

        //set navigation header view
        setProfileData();

        //set listeners
        ivNavigationMenu.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        headerLayout.findViewById(R.id.iv_edit).setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        ivToolbarShare.setOnClickListener(this);
        mTvSignUp.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.getExtras().getString(AppConstants.TAG_FROM_WHERE, "") != null && intent.getExtras().getString(AppConstants.TAG_FROM_WHERE, "").equals(AppConstants.FROM_NOTIFICATION_ACTIVITY)) {
                showChallengesFragment();
            } else if (intent.getExtras().getString(AppConstants.TAG_FROM_WHERE, "").equals(AppConstants.FROM_MY_CART_ACTIVITY)) {
                startFsStoreFragment();
            }
        }


    }

    /**
     * Setting font of Navigation Drawer items
     *
     * @param mi
     */
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpanNavigationDrawer("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    protected void initVariables() {
        mAppSharedPreference.getSharedPreferences().registerOnSharedPreferenceChangeListener(HomeActivity.this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //use of scheme and host from FitBit Application and getting AcccessToken and UserId of Fitbit User
        if (intent != null)
            if (Intent.ACTION_VIEW.equals(intent.getAction())) {
                Uri uri = intent.getData();
                String ss = uri.toString().replace('#', '?');
                String accessToken = Uri.parse(ss).getQueryParameter("access_token");
                String userId = Uri.parse(ss).getQueryParameter("user_id");
                launchFragmentConnectAppsAndDevices(accessToken, userId);
            }
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppSharedPreference.getSharedPreferences().unregisterOnSharedPreferenceChangeListener(HomeActivity.this);
    }

    public void setmFromWhere(int mFromWhere) {
        this.mFromWhere = mFromWhere;
    }

    /**
     * Method used to set the profile data on the UI
     */
    public void setProfileData() {
        Glide.with(this)
                .load(mAppSharedPreference.getString(PreferenceKeys.KEY_IMAGE, ""))
                .centerCrop()
                .placeholder(R.drawable.default_pic)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(mIvProfilePic);
        mTvName.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_USERNAME, ""));
        mTvLocation.setText(mAppSharedPreference.getString(PreferenceKeys.KEY_EMAIL_ID, ""));

        if (mAppSharedPreference.getString(PreferenceKeys.KEY_EMAIL_ID, "").isEmpty()) {
            mLlLoginSignUp.setVisibility(View.VISIBLE);

        } else {
            mLlLoginSignUp.setVisibility(View.GONE);
        }
    }

    /**
     * set action bar title from fragments
     *
     * @param title Title text
     */
    public void setActionBarTitle(int title) {
        tvToolbar.setText(title);
    }

    /**
     * Navigation drawer open or close from the custom toolbar of Home fragment
     */
    public void showDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        setProfileData();
        if (mCurrentFragment instanceof HomeFragment) {
            ((HomeFragment) mCurrentFragment).updateUserData();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        AppUtilMethods.hideKeyBoard(this);
        hideMenu();
        rlParentLayoutArcMenu.setBackgroundColor(0x00000000);
        fab.setImageResource(R.drawable.center_menu);
        fab.setSelected(false);
        switch (item.getItemId()) {
            case R.id.nav_home:
                rlToolbarMycart.setVisibility(View.GONE);
                rlNavigationToolbarFavourite.setVisibility(View.GONE);
                item.setChecked(true);
                ivWalletBottom.setSelected(false);
                ivTrophyBottom.setSelected(false);
                ivHomeBottom.setSelected(true);
                ivFsStoreBottom.setSelected(false);
                HomeFragment homeFragment = new HomeFragment();
                mCurrentFragment = homeFragment;
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_body, homeFragment);
                fragmentTransaction.commit();
                customToolbar.setVisibility(View.GONE);
                ivToolbarShare.setVisibility(View.GONE);
                break;
            case R.id.nav_settings:
                rlToolbarMycart.setVisibility(View.GONE);
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivityForResult(intent, AppConstants.RC_SETTINGS);
                        item.setChecked(true);
                        customToolbar.setVisibility(View.VISIBLE);
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                break;
            case R.id.nav_my_account:
                rlToolbarMycart.setVisibility(View.GONE);
                rlNavigationToolbarFavourite.setVisibility(View.GONE);
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        customToolbar.setVisibility(View.VISIBLE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        MyAccountFragment myAccount = new MyAccountFragment();
                        mCurrentFragment = myAccount;
                        fragmentTransaction.replace(R.id.container_body, myAccount);
                        fragmentTransaction.commit();
                        customToolbar.setVisibility(View.VISIBLE);
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                ivToolbarShare.setVisibility(View.GONE);
                break;
            case R.id.nav_favourite:
                showFavourites();
                break;

            case R.id.nav_invite:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        String shareBody = "This is the text that will be shared.";
                        String shareSubject = getString(R.string.app_name);
                        AppUtilMethods.openTextShareDialog(this, shareSubject, shareBody);
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                break;
            case R.id.nav_connect:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        setVisibilityBottomLayout(false);
                        launchFragmentConnectAppsAndDevices("", "");
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                break;

            case R.id.nav_redeem:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        customToolbar.setVisibility(View.VISIBLE);
                        rlToolbarMycart.setVisibility(View.GONE);
                        ivToolbarShare.setVisibility(View.GONE);
                        setVisibilityBottomLayout(false);
                        rlNavigationToolbarFavourite.setVisibility(View.GONE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        RedeemFsPointsFragment redeemFsPointsFragment = new RedeemFsPointsFragment();
                        mCurrentFragment = redeemFsPointsFragment;
                        fragmentTransaction.replace(R.id.container_body, redeemFsPointsFragment);
                        fragmentTransaction.commit();
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                break;

            case R.id.nav_help:
                // if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                //   if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                customToolbar.setVisibility(View.VISIBLE);
                rlToolbarMycart.setVisibility(View.GONE);
                ivToolbarShare.setVisibility(View.GONE);
                setVisibilityBottomLayout(false);
                rlNavigationToolbarFavourite.setVisibility(View.GONE);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                HelpAndSupportFragment helpAndSupportFragment = new HelpAndSupportFragment();
                mCurrentFragment = helpAndSupportFragment;
                fragmentTransaction.replace(R.id.container_body, helpAndSupportFragment);
                fragmentTransaction.commit();
//                    } else {
//                        AppDialog.showVerifyEmailAlertDialog(this);
//                    }
//                } else {
//                    AppDialog.showLoginAlertDialog(this);
//                }
                break;

            case R.id.nav_my_cart:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        showMyCart();
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Displaying MyCart Fragment
     */
    public void showMyCart() {
        customToolbar.setVisibility(View.VISIBLE);
        rlToolbarMycart.setVisibility(View.GONE);
        ivToolbarShare.setVisibility(View.GONE);
        setVisibilityBottomLayout(false);
        navigationView.getMenu().getItem(3).setChecked(true);
        rlNavigationToolbarFavourite.setVisibility(View.VISIBLE);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MyCartFragmentNew myCartFragment = new MyCartFragmentNew();
        mCurrentFragment = myCartFragment;
        fragmentTransaction.replace(R.id.container_body, myCartFragment);
        fragmentTransaction.commit();
    }

    /**
     * Displaying Favourites Fragment
     */
    public void showFavourites() {
        if (mCurrentFragment instanceof MyCartFragmentNew) {
            if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                    Intent intent = new Intent(this, FavouritesActivity.class);
                    startActivity(intent);
                } else {
                    AppDialog.showVerifyEmailAlertDialog(this);
                }
            } else {
                AppDialog.showLoginAlertDialog(this);
            }
        } else {
            tvToolbarCount.setVisibility(View.VISIBLE);
            ivNavigationToolbarFavourite.setVisibility(View.VISIBLE);
            rlToolbarMycart.setVisibility(View.GONE);
            if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                    customToolbar.setVisibility(View.VISIBLE);
                    navigationView.getMenu().getItem(6).setChecked(true);
                    rlNavigationToolbarFavourite.setVisibility(View.VISIBLE);
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FavouriteFragment favouriteFragment = new FavouriteFragment();
                    mCurrentFragment = favouriteFragment;
                    fragmentTransaction.replace(R.id.container_body, favouriteFragment);
                    fromWhere = AppConstants.FROM_PRODUCTS;
                    fragmentTransaction.commit();
                    customToolbar.setVisibility(View.VISIBLE);
                    ivToolbarShare.setVisibility(View.GONE);
                } else {
                    AppDialog.showVerifyEmailAlertDialog(this);
                }
            } else {
                AppDialog.showLoginAlertDialog(this);
            }
            ivToolbarShare.setVisibility(View.GONE);

        }
    }

    /**
     * Displaying Connect Apps & Devices Fragment
     *
     * @param accessToken  Empty when not login through Fitbit
     * @param fitbitUserId Empty when not login through Fitbit
     */
    private void launchFragmentConnectAppsAndDevices(String accessToken, String fitbitUserId) {
        rlToolbarMycart.setVisibility(View.GONE);
        ivToolbarShare.setVisibility(View.GONE);
        rlNavigationToolbarFavourite.setVisibility(View.GONE);
        customToolbar.setVisibility(View.VISIBLE);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        setVisibilityBottomLayout(false);
        ConnectAppsAndDevicesFragment connectAppsAndDevicesFragment = new ConnectAppsAndDevicesFragment();
        mCurrentFragment = connectAppsAndDevicesFragment;
        if (accessToken != null && fitbitUserId != null) {
            if (!accessToken.equals("") && !fitbitUserId.equals("")) {
                Bundle bundle = new Bundle();
                bundle.putString("accessToken", accessToken);
                bundle.putString("userId", fitbitUserId);
                connectAppsAndDevicesFragment.setArguments(bundle);
            }
        }
        fragmentTransaction.replace(R.id.container_body, connectAppsAndDevicesFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * Displaying FsStore Fragment
     */
    public void startFsStoreFragment() {
        ivWalletBottom.setSelected(false);
        ivTrophyBottom.setSelected(false);
        ivHomeBottom.setSelected(false);
        ivFsStoreBottom.setSelected(true);
        rlNavigationToolbarFavourite.setVisibility(View.GONE);
        //if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
        //    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
        unCheckNavigationSelectedItem();
        rlToolbarMycart.setVisibility(View.VISIBLE);
        tvToolbarCartCount.setText("0");
        FsStoreFragment fsStoreFragment = new FsStoreFragment();
        mCurrentFragment = fsStoreFragment;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fsStoreFragment);
        fragmentTransaction.commit();
        customToolbar.setVisibility(View.VISIBLE);
        ivToolbarShare.setVisibility(View.GONE);
//            } else {
//                AppDialog.showVerifyEmailAlertDialog(this);
//            }
//        } else {
//            AppDialog.showLoginAlertDialog(HomeActivity.this);
//        }
    }

    @Override
    public void onBackPressed() {
        //close drawer on backpressed
        AppUtilMethods.hideKeyBoard(HomeActivity.this);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //back to home fragment on backpressed
        else {
            if (!(mCurrentFragment instanceof HomeFragment)) {
                HomeFragment homeFragment = new HomeFragment();
                mCurrentFragment = homeFragment;
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_body, homeFragment);
                fragmentTransaction.commit();
                navigationView.getMenu().getItem(0).setChecked(true);
                customToolbar.setVisibility(View.GONE);
                ivWalletBottom.setSelected(false);
                ivTrophyBottom.setSelected(false);
                ivHomeBottom.setSelected(true);
                ivFsStoreBottom.setSelected(false);
                AppUtilMethods.hideKeyBoard(this);
            } else {
                AppDialog.showExitAlertDialog(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_navigation_menu:
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    AppUtilMethods.hideKeyBoard(this);
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    AppUtilMethods.hideKeyBoard(this);
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.iv_edit:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        intent = new Intent(HomeActivity.this, EditProfileActivity.class);
                        startActivity(intent);
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                break;
            case R.id.tv_login:
                intent = new Intent(HomeActivity.this, LoginOptionsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_sign_up:
                intent = new Intent(HomeActivity.this, SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_wallet_bottom:
                ivWalletBottom.setSelected(true);
                ivTrophyBottom.setSelected(false);
                ivHomeBottom.setSelected(false);
                ivFsStoreBottom.setSelected(false);
                rlToolbarMycart.setVisibility(View.GONE);
                showWalletFragment();
                break;
            case R.id.iv_fs_store_bottom:
                startFsStoreFragment();
                break;
            case R.id.iv_home_bottom:
                rlToolbarMycart.setVisibility(View.GONE);
                ivWalletBottom.setSelected(false);
                ivTrophyBottom.setSelected(false);
                ivHomeBottom.setSelected(true);
                ivFsStoreBottom.setSelected(false);
                ivToolbarShare.setVisibility(View.GONE);
                rlNavigationToolbarFavourite.setVisibility(View.GONE);
                HomeFragment homeFragment = new HomeFragment();
                mCurrentFragment = homeFragment;
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_body, homeFragment);
                fragmentTransaction.commit();
                customToolbar.setVisibility(View.GONE);
                navigationView.getMenu().getItem(0).setChecked(true);
                break;
            case R.id.iv_trophy_bottom:
                showChallengesFragment();
                break;
            case R.id.fab:
                onFabClick(v);
                break;
            case R.id.menu_layout:
                onFabClick(null);
                break;
            case R.id.tv_share_activities:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        mFromWhere = AppConstants.FROM_SHARE_IMAGE;
                        if (PermissionUtils.hasStoragePermission(this)) {
                            if (mCurrentFragment instanceof HomeFragment) {
                                ((HomeFragment) mCurrentFragment).openShareActivity();
                            } else {
                                HomeFragment fragment = new HomeFragment();
                                mCurrentFragment = fragment;
                                Bundle bundle = new Bundle();
                                bundle.putInt(AppConstants.TAG_FROM_WHERE, 1);
                                fragment.setArguments(bundle);
                                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.container_body, fragment);
                                fragmentTransaction.commit();
                                navigationView.getMenu().getItem(0).setChecked(true);
                                customToolbar.setVisibility(View.GONE);
                                ivWalletBottom.setSelected(false);
                                ivTrophyBottom.setSelected(false);
                                ivHomeBottom.setSelected(true);
                                ivFsStoreBottom.setSelected(false);
                            }
                        }

                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                if (fab.isSelected()) {
                    onFabClick(v);
                }
                break;
            case R.id.rl_toolbar_favourite:
                if (!(mCurrentFragment instanceof FavouriteFragment)) {
                    showFavourites();
                }
                break;
            case R.id.rl_toolbar_mycart:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        if (mCurrentFragment instanceof FsStoreFragment) {
                            intent = new Intent(this, MyCartActivity.class);
                            intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_FS_STORE_FRAGMENT);
                            this.startActivityForResult(intent, AppConstants.RC_FROM_FS_STORE_FRAGMENT);
                        } else {
                            showMyCart();
                        }
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                break;
            case R.id.iv_toolbar_share:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        mFromWhere = AppConstants.FROM_SHARE_IMAGE;
                        if (mCurrentFragment instanceof ChallengesFragment) {
                            String shareBody = getString(R.string.txt_contest_share);
                            String shareSubject = getString(R.string.app_name);
                            AppUtilMethods.openTextShareDialog(this, shareSubject, shareBody);
                        }
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                break;
            case R.id.tv_workout:
                Intent i;
                PackageManager manager = getPackageManager();
                try {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_CONNECTED_APP, AppConstants.NO_APP_CONNECTED).equals(AppConstants.FITBIT_CONNECTED))
                        i = manager.getLaunchIntentForPackage(getString(R.string.txt_fitbit_package_name));
                    else if (mAppSharedPreference.getString(PreferenceKeys.KEY_CONNECTED_APP, AppConstants.NO_APP_CONNECTED).equals(AppConstants.RUNKEEPER_CONNECTED))
                        i = manager.getLaunchIntentForPackage(getString(R.string.txt_runkeeper_pacakge_name));
                    else {
                        i = null;
                    }
                    if (i == null) {
                        if (mAppSharedPreference.getString(PreferenceKeys.KEY_CONNECTED_APP, AppConstants.NO_APP_CONNECTED).equals(AppConstants.NO_APP_CONNECTED) || mAppSharedPreference.getString(PreferenceKeys.KEY_CONNECTED_APP, "").equals(""))
                            ToastUtils.showShortToast(this, getString(R.string.txt_err_app_not_connected));
                        else {
                            ToastUtils.showShortToast(this, getString(R.string.txt_err_app_not_installed));
                        }
                        throw new PackageManager.NameNotFoundException();

                    }
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(i);

                } catch (PackageManager.NameNotFoundException e) {
                    if (!mAppSharedPreference.getString(PreferenceKeys.KEY_CONNECTED_APP, "").equals(AppConstants.NO_APP_CONNECTED) || mAppSharedPreference.getString(PreferenceKeys.KEY_CONNECTED_APP, "").equals("")) {
                        if (mAppSharedPreference.getString(PreferenceKeys.KEY_CONNECTED_APP, "").equals(AppConstants.NO_APP_CONNECTED))
                            ToastUtils.showShortToast(this, getString(R.string.txt_err_app_not_installed));
                    }
                }
                if (fab.isSelected()) {
                    onFabClick(v);
                }

                break;
            case R.id.tv_invite_friends:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        String shareBody = getString(R.string.txt_invite_friend_text);
                        String shareSubject = getString(R.string.app_name);
                        AppUtilMethods.openTextShareDialog(this, shareSubject, shareBody);

                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                if (fab.isSelected()) {
                    onFabClick(v);
                }
                break;
        }
    }

    /**
     * Displaying Wallet Fragment
     */
    public void showWalletFragment() {
        if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
            if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                unCheckNavigationSelectedItem();
                rlNavigationToolbarFavourite.setVisibility(View.GONE);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                MyWalletFragment walletFragment = new MyWalletFragment();
                getSupportFragmentManager().executePendingTransactions();
                fragmentTransaction.replace(R.id.container_body, walletFragment);
                fragmentTransaction.commit();
                mCurrentFragment = walletFragment;
                customToolbar.setVisibility(View.VISIBLE);
            } else {
                AppDialog.showVerifyEmailAlertDialog(HomeActivity.this);
            }
        } else {
            AppDialog.showLoginAlertDialog(HomeActivity.this);
        }
    }

    /**
     * Displaying Challenges Fragment
     */
    private void showChallengesFragment() {
        rlToolbarMycart.setVisibility(View.GONE);
        if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
            if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                ivWalletBottom.setSelected(false);
                ivTrophyBottom.setSelected(true);
                ivHomeBottom.setSelected(false);
                ivFsStoreBottom.setSelected(false);
                unCheckNavigationSelectedItem();
                rlNavigationToolbarFavourite.setVisibility(View.GONE);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                ChallengesFragment challengesFragment = new ChallengesFragment();
                fragmentTransaction.replace(R.id.container_body, challengesFragment);
                fragmentTransaction.commit();
                mCurrentFragment = challengesFragment;
                customToolbar.setVisibility(View.VISIBLE);
            } else {
                AppDialog.showVerifyEmailAlertDialog(HomeActivity.this);
            }
        } else {
            AppDialog.showLoginAlertDialog(HomeActivity.this);
        }
    }

    /**
     * Maintaining Arc Menu Buttons
     *
     * @param v
     */
    private void onFabClick(View v) {
        if (fab.isSelected()) {
            hideMenu();
            rlParentLayoutArcMenu.setBackgroundColor(0x00000000);
            fab.setImageResource(R.drawable.center_menu);
            fab.setSelected(false);
        } else {
            showMenu();
            rlParentLayoutArcMenu.setBackgroundColor(getResources().getColor(R.color.c_black_overlay));
            fab.setImageResource(R.drawable.close_circle);
            fab.setSelected(true);
        }
        if (v == null) {
            hideMenu();
            rlParentLayoutArcMenu.setBackgroundColor(0x00000000);
            fab.setImageResource(R.drawable.center_menu);
            fab.setSelected(false);
        }
        //fab.setSelected(!fab.isSelected());
    }

    /**
     * setting visibility of bottom layout
     *
     * @param visibility
     */
    public void setVisibilityBottomLayout(Boolean visibility) {
        if (visibility) {
            llViewAll.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
        } else {
            llViewAll.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        }
    }

    /**
     * Showing Arc Menu
     */
    private void showMenu() {
        menuLayout.setVisibility(View.VISIBLE);
        List<Animator> animList = new ArrayList<>();
        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(300);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    /**
     * Hiding Arc Menu
     */
    @SuppressWarnings("NewApi")
    private void hideMenu() {
        List<Animator> animList = new ArrayList<>();
        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(300);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menuLayout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();

    }

    /**
     * Showing Animation in Arc Menu
     *
     * @param item
     * @return
     */
    private Animator createShowItemAnimator(View item) {

        float dx = fab.getX() - item.getX();
        float dy = fab.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                //AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        return anim;
    }

    /**
     * Hiding Animation in Arc Menu
     *
     * @param item
     * @return
     */
    private Animator createHideItemAnimator(final View item) {
        float dx = fab.getX() - item.getX();
        float dy = fab.getY() - item.getY();
        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                //AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_GALLERY_INTENT && resultCode == RESULT_OK) {
            Uri mImageCaptureUri = data.getData();
            Cursor cursor = getContentResolver().query(mImageCaptureUri, new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String imagePath = cursor.getString(0);
                Bitmap bitmap = CameraAndGalleryUtils.getImageBitmap(imagePath, CameraAndGalleryUtils.getRotationMatrix(CameraAndGalleryUtils.getOrientation(imagePath)), 300, 300);
                CameraAndGalleryUtils.saveImageToFile(bitmap, AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_IMAGE_PATH);
            }
            if (mCurrentFragment instanceof HomeFragment) {
                ((HomeFragment) mCurrentFragment).setProfileImage(AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_IMAGE_PATH);
            }
        } else if (requestCode == AppConstants.RC_CAMERA_INTENT && resultCode == RESULT_OK) {
            String imagePath = AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_IMAGE_PATH;
            Bitmap bitmap = CameraAndGalleryUtils.getImageBitmap(imagePath, CameraAndGalleryUtils.getRotationMatrix(CameraAndGalleryUtils.getOrientation(imagePath)), 300, 300);
            CameraAndGalleryUtils.saveImageToFile(bitmap, imagePath);
            Logger.e("Path" + imagePath);
            if (mCurrentFragment instanceof HomeFragment) {
                ((HomeFragment) mCurrentFragment).setProfileImage(imagePath);
            }
        } else if (requestCode == AppConstants.RC_SETTINGS && resultCode == RESULT_OK) {
            navigationView.getMenu().getItem(0).setChecked(true);
            customToolbar.setVisibility(View.GONE);
            if (!(mCurrentFragment instanceof HomeFragment)) {
                HomeFragment homeFragment = new HomeFragment();
                mCurrentFragment = homeFragment;
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_body, homeFragment);
                fragmentTransaction.commitAllowingStateLoss();
                ivWalletBottom.setSelected(false);
                ivTrophyBottom.setSelected(false);
                ivHomeBottom.setSelected(true);
                ivFsStoreBottom.setSelected(false);
            }
        } else if (requestCode == AppConstants.RC_SHARE_DIALOG && resultCode == RESULT_OK) {
            ServiceUtils.shareAndInvite(this, new ProgressDialog(this), mAppSharedPreference, ServiceConstants.TYPE_SHARE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.RC_REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (mFromWhere == AppConstants.FROM_RESET_IMAGE) {
                        if (mCurrentFragment instanceof HomeFragment) {
                            ((HomeFragment) mCurrentFragment).selectImage();
                        }
                    } else if (mFromWhere == AppConstants.FROM_SHARE_IMAGE) {
                        tvShareActivities.performClick();
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

    /**
     * Unchecking Navigation Drawer items when using bottom buttons
     */
    public void unCheckNavigationSelectedItem() {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.isChecked()) {
                menuItem.setChecked(false);
                break;
            }
        }
    }

    public int getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(int from) {
        fromWhere = from;
    }
}
