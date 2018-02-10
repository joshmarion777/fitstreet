package affle.com.fitstreet.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.CouponsListRecyclerAdapter;
import affle.com.fitstreet.models.request.ReqCouponsList;
import affle.com.fitstreet.models.request.ReqGetFilters;
import affle.com.fitstreet.models.request.ReqSetFavouriteCoupon;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResCouponList;
import affle.com.fitstreet.models.response.ResGetCouponList;
import affle.com.fitstreet.models.response.ResGetFilters;
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
import affle.com.fitstreet.utils.PermissionUtils;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponsListActivity extends BaseActivity {
    @BindView(R.id.rv_coupons_list)
    RecyclerView rvCouponsList;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_toolbar_favourite)
    RelativeLayout rlToolBarFavourite;
    @BindView(R.id.tv_toolbar_favourite_count)
    TextView tvToolbarFavouriteCount;
    @BindView(R.id.rl_toolbar_filter)
    RelativeLayout rlToolBarFilter;
    @BindView(R.id.iv_toolbar_filter)
    ImageView ivFilter;
    @BindView(R.id.iv_toolbar_filter_active)
    ImageView ivFilterIndicator;
    private String mCatId = "";
    private String mCatName = "";
    private ResGetCouponList mResGetCouponList;
    private ProgressDialog mProgressDialog;
    private List<ResCouponList> mResCouponLists;
    private CouponsListRecyclerAdapter mCouponsListRecyclerAdapter;
    private List<ResGetFilters.ResPartnerData> mPartnerDataList;
    private List<String> mSelectedPartnersList;
    private Bitmap mBitmap;
    private String mShareBody;
    private String mPartnerFilterId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            mCatId = intent.getExtras().getString("mCatId");
            mCatName = intent.getExtras().getString("mCatName");
        }
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(mCatName);
        rlToolBarFavourite.setVisibility(View.VISIBLE);
        rlToolBarFilter.setVisibility(View.VISIBLE);
        rvCouponsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ivFilter.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        //set listeners
        ivBack.setOnClickListener(this);
        ivFilter.setOnClickListener(this);
        rlToolBarFavourite.setOnClickListener(this);
    }

    /**
     * This method is used to show the progress dialog
     *
     * @return void
     */
    public void showProgressDialog() {
        mProgressDialog = AppDialog.showProgressDialog(this);
        mProgressDialog.show();
    }

    /**
     * This method is used to hide the progress dialog
     *
     * @return void
     */
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void initVariables() {
        mResCouponLists = new ArrayList<>();
        mCouponsListRecyclerAdapter = new CouponsListRecyclerAdapter(CouponsListActivity.this, mResCouponLists);
        rvCouponsList.setAdapter(mCouponsListRecyclerAdapter);
        mPartnerDataList = new ArrayList<>();
        mSelectedPartnersList = new ArrayList<>();
        if (NetworkConnection.isNetworkConnected(this)) {
            getCoupons();
        } else {
            ToastUtils.showShortToast(this, R.string.err_network_connection);
        }
    }

    /**
     * Set favourites/unfavourites to/from server
     *
     * @param position
     * @param methodName
     */
    public void setFavouriteUnFavourite(final int position, final String methodName) {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        ReqSetFavouriteCoupon reqSetFavouriteCoupon = new ReqSetFavouriteCoupon();
        reqSetFavouriteCoupon.setCoupanID(mResCouponLists.get(position).getCoupanID());
        reqSetFavouriteCoupon.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqSetFavouriteCoupon.setMethod(methodName);
        reqSetFavouriteCoupon.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ""));
        Call<ResBase> resBaseCall = client.setFavouriteCoupon(reqSetFavouriteCoupon);
        resBaseCall.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                ResBase resBase = response.body();
                dismissProgressDialog();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        if (methodName.equals(MethodFactory.UNFAVOURITE_COUPON.getMethod().toString())) {
                            ToastUtils.showShortToast(CouponsListActivity.this, getString(R.string.txt_success_unfavourite_coupon));
                            mResCouponLists.get(position).setFavourite(ServiceConstants.UNFAVOURITE);
                            mCouponsListRecyclerAdapter.notifyDataSetChanged();
                            int favcount = Integer.parseInt(mResGetCouponList.getFavCount()) - 1;
                            mResGetCouponList.setFavCount(String.valueOf(favcount));
                            tvToolbarFavouriteCount.setText("" + mResGetCouponList.getFavCount());
                            if (favcount > 0)
                                tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                            else
                                tvToolbarFavouriteCount.setVisibility(View.GONE);
                        } else {
                            mResCouponLists.get(position).setFavourite(ServiceConstants.FAVOURITE);
                            ToastUtils.showShortToast(CouponsListActivity.this, getString(R.string.txt_success_set_favourite_coupon));
                            mCouponsListRecyclerAdapter.notifyDataSetChanged();
                            int favcount = Integer.parseInt(mResGetCouponList.getFavCount()) + 1;
                            mResGetCouponList.setFavCount(String.valueOf(favcount));
                            tvToolbarFavouriteCount.setText("" + mResGetCouponList.getFavCount());
                            if (favcount > 0)
                                tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                            else
                                tvToolbarFavouriteCount.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtils.showShortToast(CouponsListActivity.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(CouponsListActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(CouponsListActivity.this, R.string.err_network_connection);

            }
        });
    }

    /**
     * Method used to handle visibility of filter indicator
     *
     * @param visibility
     */
    public void setFilterIndicatorVisibility(int visibility) {
        ivFilterIndicator.setVisibility(visibility);
    }

    /**
     * Method used to hit get coupons service
     */
    public void getCoupons() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        final ReqCouponsList reqCouponsList = new ReqCouponsList();
        ReqCouponsList.FilterType filterType = new ReqCouponsList.FilterType();
        filterType.setPartnerID(mSelectedPartnersList);
        reqCouponsList.setCatID(mCatId);
        reqCouponsList.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqCouponsList.setMethod(MethodFactory.GET_ALL_COUPONS_LIST.getMethod());
        reqCouponsList.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqCouponsList.setFilterType(filterType);
        Call<ResGetCouponList> resGetCouponListCall = client.getCouponsList(reqCouponsList);
        resGetCouponListCall.enqueue(new Callback<ResGetCouponList>() {
            @Override
            public void onResponse(Call<ResGetCouponList> call, Response<ResGetCouponList> response) {
                dismissProgressDialog();
                mResGetCouponList = response.body();
                if (mResGetCouponList != null) {
                    if (mResGetCouponList.getSuccess() == ServiceConstants.SUCCESS) {
                        mResCouponLists.clear();
                        mResCouponLists.addAll(mResGetCouponList.getCouponsList());
                        mCouponsListRecyclerAdapter.notifyDataSetChanged();
                        tvToolbarFavouriteCount.setText("" + mResGetCouponList.getFavCount());
                        if (Integer.parseInt(mResGetCouponList.getFavCount()) > 0)
                            tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                        else
                            tvToolbarFavouriteCount.setVisibility(View.GONE);

                    } else {
                        ToastUtils.showShortToast(CouponsListActivity.this, mResGetCouponList.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(CouponsListActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResGetCouponList> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(CouponsListActivity.this, R.string.err_network_connection);
            }
        });
    }

    /**
     * Method used to hit get partners service
     */
    private void getPartners() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        ReqGetFilters reqGetFilters = new ReqGetFilters();
        reqGetFilters.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqGetFilters.setMethod(MethodFactory.GET_FILTERS.getMethod());
        reqGetFilters.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqGetFilters.setFilterBy(ServiceConstants.FILTER_BY_PARTNER);
        Call<ResGetFilters> resGetFiltersCall = client.getFilters(reqGetFilters);
        resGetFiltersCall.enqueue(new Callback<ResGetFilters>() {
            @Override
            public void onResponse(Call<ResGetFilters> call, Response<ResGetFilters> response) {
                dismissProgressDialog();
                ResGetFilters resGetFilters = response.body();
                if (resGetFilters != null) {
                    if (resGetFilters.getSuccess() == ServiceConstants.SUCCESS) {
                        mPartnerDataList.addAll(resGetFilters.getPartnerData());
                        ivFilter.performClick();
                    } else {
                        ToastUtils.showShortToast(CouponsListActivity.this, resGetFilters.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(CouponsListActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResGetFilters> call, Throwable t) {
                ToastUtils.showShortToast(CouponsListActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_toolbar_filter:
//                isOnActivityResult = false;
                if (mPartnerDataList.isEmpty()) {
                    if (NetworkConnection.isNetworkConnected(this)) {
                        getPartners();
                    } else {
                        ToastUtils.showShortToast(this, R.string.err_network_connection);
                    }
                } else {
                    //AppDialog.showCouponFiltersDialog(CouponsListActivity.this, ivFilter.getMeasuredWidth(), ivFilter.getMeasuredHeight(), "", mPartnerDataList, mSelectedPartnersList);
                }
                break;
            case R.id.rl_toolbar_favourite:
                Intent intent = new Intent(this, FavouritesActivity.class);
                intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_COUPONS);
                startActivityForResult(intent, AppConstants.RC_FAVORITES);
                break;
        }
    }

    /**
     * Sharing coupons
     *
     * @param bitmap
     * @param shareBody
     */
    public void shareCoupon(Bitmap bitmap, String shareBody) {
        mBitmap = bitmap;
        mShareBody = getString(R.string.txt_share_body);
        ;
        if (PermissionUtils.hasStoragePermission(this)) {
            String shareSubject = getString(R.string.app_name);
            String imagePath = AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_COUPON_IMAGE_PATH + System.currentTimeMillis() + AppConstants.EXTN_JPG;
            CameraAndGalleryUtils.saveImageToFile(bitmap, imagePath);
            AppUtilMethods.openImageShareDialog(this, shareSubject, shareBody, imagePath);
//            mBitmap = null;
//            mShareBody = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.RC_REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    shareCoupon(mBitmap, mShareBody);
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
        if (requestCode == AppConstants.RC_FAVORITES && resultCode == RESULT_OK) {
            if (NetworkConnection.isNetworkConnected(this)) {
                getCoupons();
            } else {
                ToastUtils.showShortToast(this, R.string.err_network_connection);
            }
        } else if (requestCode == AppConstants.RC_COUPON_LIST && resultCode == RESULT_OK) {

            mSelectedPartnersList.clear();
            if (data != null)
                if ((mPartnerFilterId = data.getStringExtra(AppConstants.PARTNER_ID)) != null)
                    mSelectedPartnersList.add(mPartnerFilterId);
            getCoupons();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
