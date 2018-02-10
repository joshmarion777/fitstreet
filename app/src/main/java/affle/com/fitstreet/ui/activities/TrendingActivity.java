package affle.com.fitstreet.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.EndlessRecyclerOnScrollListenerGrid;
import affle.com.fitstreet.adapters.TrendingCouponsRecycleAdapter;
import affle.com.fitstreet.adapters.TrendingProductsRecyclerAdapter;
import affle.com.fitstreet.customviews.CustomTypefaceEditText;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.request.ReqGetFilters;
import affle.com.fitstreet.models.request.ReqSetFavouriteProduct;
import affle.com.fitstreet.models.request.ReqTrendingCoupons;
import affle.com.fitstreet.models.request.ReqTrendingProducts;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResGetFilters;
import affle.com.fitstreet.models.response.ResGetTrendingCoupons;
import affle.com.fitstreet.models.response.ResGetTrendingProducts;
import affle.com.fitstreet.models.response.ResTrendingCouponsData;
import affle.com.fitstreet.models.response.ResTrendingProductsData;
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
import affle.com.fitstreet.utils.GridSpacingItemDecoration;
import affle.com.fitstreet.utils.Logger;
import affle.com.fitstreet.utils.PermissionUtils;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingActivity extends BaseActivity implements IOnItemClickListener {
    @BindView(R.id.rv_trending_coupons)
    RecyclerView rvTrendingCoupon;
    @BindView(R.id.rv_trending_products)
    RecyclerView rvTrendingProduct;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    CustomTypefaceEditText etSearch;
    @BindView(R.id.btn_products)
    Button btnProducts;
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
    @BindView(R.id.iv_cross_search)
    ImageView ivCrossSearch;
    ReqTrendingProducts.FilterType filterType = new ReqTrendingProducts.FilterType();
    String mGender = "";
    String mMinPrice = "";
    String mMaxPrice = "";
    private ResGetTrendingProducts mResGetTrendingProducts;
    private ResGetTrendingCoupons mResGetTrendingCoupons;
    private TrendingProductsRecyclerAdapter mTrendingProductsRecyclerAdapter;
    private TrendingCouponsRecycleAdapter mTrendingCouponsRecycleAdapter;
    private List<ResTrendingProductsData> mResTrendingProductsData = new ArrayList<>();
    private List<ResTrendingCouponsData> mResTrendingCouponsData = new ArrayList<>();
    private Call<ResBase> mResBaseCall;
    private String mFavProductCount;
    private String mFavCouponCount;
    private GridLayoutManager mGridLayoutManager;
    private List<ResGetFilters.ResPartnerData> mPartnerDataList;
    private List<ResGetFilters.ResBrandData> mBrandDataList;
    private List<String> mSelectedProductPartnersList, mSelectedCouponPartnersList;
    private List<String> mSelectedBrandsList;
    private List<String> mProductFiltersList;
    private List<Integer> mRangeList;
    private List<Boolean> mSelectedGenderList;
    private boolean mIsProductFilter, mIsCouponFilter;
    private Bitmap mBitmapCoupon;
    private Bitmap mBitmapProduct;
    private String mShareBodyCoupon;
    private String mShareBodyProduct;
    private int mPageindex = 1;
    public ArrayList<Integer> mSelectedRangeForTrendingProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        super.initData();
    }

    @Override
    protected void initViews() {
        mShareBodyProduct = getString(R.string.txt_share_body);
        mShareBodyCoupon = getString(R.string.txt_share_body);
        tvTitle.setText(R.string.txt_trending_title);
        rlToolBarFilter.setVisibility(View.VISIBLE);
        rlToolBarFavourite.setVisibility(View.VISIBLE);
        btnProducts.setSelected(true);
        rlToolBarFilter.setVisibility(View.VISIBLE);
        ivFilter.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        ivCrossSearch.setOnClickListener(this);
        mResTrendingCouponsData = new ArrayList<ResTrendingCouponsData>();
        mResTrendingProductsData = new ArrayList<ResTrendingProductsData>();
        mTrendingCouponsRecycleAdapter = new TrendingCouponsRecycleAdapter(TrendingActivity.this, mResTrendingCouponsData);
        mTrendingProductsRecyclerAdapter = new TrendingProductsRecyclerAdapter(TrendingActivity.this, mResTrendingProductsData);
        rvTrendingCoupon.setAdapter(mTrendingCouponsRecycleAdapter);
        mGridLayoutManager = new GridLayoutManager(TrendingActivity.this, 2);
        rvTrendingProduct.setLayoutManager(mGridLayoutManager);
        setAdapter();

        rvTrendingProduct.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));

        //set listeners
        mTrendingProductsRecyclerAdapter.setOnItemClickListener(this);
        ivBack.setOnClickListener(this);
        ivFilter.setOnClickListener(this);
        btnProducts.setOnClickListener(this);
        rlToolBarFavourite.setOnClickListener(this);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    AppUtilMethods.hideKeyBoard(TrendingActivity.this);
                    if (NetworkConnection.isNetworkConnected(TrendingActivity.this)) {
                        getTrendingProducts(etSearch.getText().toString(), "", "", "", 0, 1, true);
                    } else {
                        ToastUtils.showShortToast(TrendingActivity.this, getResources().getString(R.string.err_network_connection));
                    }
                }
                return false;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    ivCrossSearch.setVisibility(View.VISIBLE);
                } else {
                    ivCrossSearch.setVisibility(View.GONE);
                    AppUtilMethods.hideKeyBoard(TrendingActivity.this);
                    mPageindex = 1;
                    if (NetworkConnection.isNetworkConnected(TrendingActivity.this)) {
                        getTrendingProducts("", "", "", "", 0, 1, true);
                    } else {
                        ToastUtils.showShortToast(TrendingActivity.this, getResources().getString(R.string.err_network_connection));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * Setting adapters and onScroll listeners for paging
     */
    private void setAdapter() {
        rvTrendingProduct.setAdapter(mTrendingProductsRecyclerAdapter);
        rvTrendingProduct.addOnScrollListener(new EndlessRecyclerOnScrollListenerGrid(mGridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.d("onloadmore", "Load More data, Current Page = " + current_page);
                mPageindex += 1;
                getTrendingProducts(etSearch.getText().toString(), mGender, mMinPrice, mMaxPrice, 0, mPageindex, false);
            }
        });
    }

    @Override
    protected void initVariables() {
        mProductFiltersList = Arrays.asList(getResources().getStringArray(R.array.arr_filter_product));
        mPartnerDataList = new ArrayList<>();
        mBrandDataList = new ArrayList<>();
        mSelectedProductPartnersList = new ArrayList<>();
        mSelectedCouponPartnersList = new ArrayList<>();
        mSelectedBrandsList = new ArrayList<>();
        mRangeList = new ArrayList<>(2);
        mRangeList.add(AppConstants.FILTER_MIN);
        mRangeList.add(AppConstants.FILTER_MAX);
        mSelectedGenderList = new ArrayList<>(2);
        mSelectedGenderList.add(false);
        mSelectedGenderList.add(false);
        mFavCouponCount = "0";
        mFavProductCount = "0";

        if (NetworkConnection.isNetworkConnected(TrendingActivity.this)) {
            rvTrendingProduct.setVisibility(View.GONE);
            rvTrendingCoupon.setVisibility(View.VISIBLE);
            getTrendingProducts(etSearch.getText().toString(), "", "", "", 0, mPageindex, false);
        } else {
            ToastUtils.showShortToast(TrendingActivity.this, getResources().getString(R.string.err_network_connection));
        }
    }

    /**
     * Method used to handle visibility of filter indicator
     *
     * @param visibility
     */
    public void setProductFilterIndicatorVisibility(int visibility, boolean isFilter) {
        ivFilterIndicator.setVisibility(visibility);
        mIsProductFilter = isFilter;
    }

    /**
     * Method used to handle visibility of filter indicator
     *
     * @param visibility
     */
    public void setCouponFilterIndicatorVisibility(int visibility, boolean isFilter) {
        ivFilterIndicator.setVisibility(visibility);
        mIsCouponFilter = isFilter;
    }

    /**
     * Method used to hit get partners and brand service
     */
    private void getPartnersAndBrands() {
        AppDialog.showProgressDialog(this, true);
        IApiClient client = ApiClient.getApiClient();
        ReqGetFilters reqGetFilters = new ReqGetFilters();
        reqGetFilters.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqGetFilters.setMethod(MethodFactory.GET_FILTERS.getMethod());
        reqGetFilters.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqGetFilters.setFilterBy("");
        Call<ResGetFilters> resGetFiltersCall = client.getFilters(reqGetFilters);
        resGetFiltersCall.enqueue(new Callback<ResGetFilters>() {
            @Override
            public void onResponse(Call<ResGetFilters> call, Response<ResGetFilters> response) {
                AppDialog.showProgressDialog(TrendingActivity.this, false);
                ResGetFilters resGetFilters = response.body();
                if (resGetFilters != null) {
                    if (resGetFilters.getSuccess() == ServiceConstants.SUCCESS) {
                        mPartnerDataList.addAll(resGetFilters.getPartnerData());
                        mBrandDataList.addAll(resGetFilters.getBrandData());
                        ivFilter.performClick();
                    } else {
                        ToastUtils.showShortToast(TrendingActivity.this, resGetFilters.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(TrendingActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResGetFilters> call, Throwable t) {
                AppDialog.showProgressDialog(TrendingActivity.this, false);
                ToastUtils.showShortToast(TrendingActivity.this, R.string.err_network_connection);
            }
        });
    }

    /**
     * Setting Favourite/Unfavourite products
     *
     * @param position
     * @param methodName
     */
    public void setFavouriteUnFavourite(final int position, final String methodName) {
        AppDialog.showProgressDialog(TrendingActivity.this, true);
        IApiClient client = ApiClient.getApiClient();
        if (btnProducts.isSelected()) {
            ReqSetFavouriteProduct reqSetFavouriteProduct = new ReqSetFavouriteProduct();
            reqSetFavouriteProduct.setProductID(mResTrendingProductsData.get(position).getProductID());
            reqSetFavouriteProduct.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
            reqSetFavouriteProduct.setMethod(methodName);
            reqSetFavouriteProduct.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ""));
            mResBaseCall = client.setFavouriteProduct(reqSetFavouriteProduct);
        }
        mResBaseCall.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                ResBase resBase = response.body();
                AppDialog.showProgressDialog(TrendingActivity.this, false);
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        String fav = tvToolbarFavouriteCount.getText().toString().trim();
                        int favCount = Integer.parseInt(fav.isEmpty() ? "0" : fav);
                        if (methodName.equals(MethodFactory.UNFAVOURITE_PRODUCT.getMethod().toString())) {
                            ToastUtils.showShortToast(TrendingActivity.this, getString(R.string.txt_success_unfavourite_product));
                            mResTrendingProductsData.get(position).setFavourite(ServiceConstants.UNFAVOURITE);
                            mTrendingProductsRecyclerAdapter.notifyDataSetChanged();
                            mFavProductCount = String.valueOf(favCount - 1);
                            tvToolbarFavouriteCount.setText(mFavProductCount);
                            if ((favCount - 1) > 0)
                                tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                            else
                                tvToolbarFavouriteCount.setVisibility(View.GONE);
                        } else {
                            ToastUtils.showShortToast(TrendingActivity.this, getString(R.string.txt_success_set_favourite_product));
                            mResTrendingProductsData.get(position).setFavourite(ServiceConstants.FAVOURITE);
                            mTrendingProductsRecyclerAdapter.notifyDataSetChanged();
                            mFavProductCount = String.valueOf(favCount + 1);
                            tvToolbarFavouriteCount.setText(mFavProductCount);
                            if ((favCount + 1) > 0)
                                tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                            else
                                tvToolbarFavouriteCount.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtils.showShortToast(TrendingActivity.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(TrendingActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(TrendingActivity.this, false);
                ToastUtils.showShortToast(TrendingActivity.this, R.string.err_network_connection);

            }
        });
    }

    /**
     * Sharing coupons
     *
     * @param bitmap
     * @param shareBody
     */
    public void shareCoupon(Bitmap bitmap, String shareBody) {
        mBitmapCoupon = bitmap;
        mShareBodyCoupon = shareBody;
        if (PermissionUtils.hasStoragePermission(this)) {
            String shareSubject = getString(R.string.app_name);
            String imagePath = AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_COUPON_IMAGE_PATH + System.currentTimeMillis() + AppConstants.EXTN_JPG;
            CameraAndGalleryUtils.saveImageToFile(bitmap, imagePath);
            AppUtilMethods.openImageShareDialog(this, shareSubject, shareBody, imagePath);
        }
    }

    /**
     * Sharing products
     *
     * @param bitmap
     * @param shareBody
     */
    public void shareProduct(Bitmap bitmap, String shareBody) {
        mBitmapProduct = bitmap;
//        mShareBodyProduct = shareBody;
        if (PermissionUtils.hasStoragePermission(this)) {
            String shareSubject = getString(R.string.app_name);
            String imagePath = AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_PRODUCT_IMAGE_PATH + +System.currentTimeMillis() + AppConstants.EXTN_JPG;
            //CameraAndGalleryUtils.saveImageToFile(bitmap, imagePath);
            AppUtilMethods.openImageShareDialog(this, shareSubject, mShareBodyProduct, imagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.RC_REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    shareProduct(mBitmapProduct, mShareBodyProduct);

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
     * Getting trending products data from server
     *
     * @param searchTerm
     * @param gender
     * @param minPrice
     * @param maxPrice
     * @param from
     * @param pageindex
     * @param isFromFilters
     */
    public void getTrendingProducts(final String searchTerm, final String gender, final String minPrice, final String maxPrice, int from, final int pageindex, final boolean isFromFilters) {
        if (from == 0)
            AppDialog.showProgressDialog(TrendingActivity.this, true);
        mMaxPrice = maxPrice;
        mMinPrice = minPrice;
        mGender = gender;
        rvTrendingCoupon.setVisibility(View.GONE);
        rvTrendingProduct.setVisibility(View.VISIBLE);
        mPageindex = pageindex;
        //       rvFavouriteProducts.addItemDecoration(new ItemDecorationAlbumColumns(mActivity));
        IApiClient client = ApiClient.getApiClient();

        ReqTrendingProducts.FilterType filterType = new ReqTrendingProducts.FilterType();
        filterType.setBrandID(mSelectedBrandsList);
        filterType.setPartnerID(mSelectedProductPartnersList);
        filterType.setGender(gender);
        ReqTrendingProducts.PriceRange priceRange = new ReqTrendingProducts.PriceRange();
        priceRange.setMin(minPrice);
        priceRange.setMax(maxPrice);
        filterType.setPriceRange(priceRange);

        final ReqTrendingProducts reqTrendingProducts = new ReqTrendingProducts();
        reqTrendingProducts.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqTrendingProducts.setMethod(MethodFactory.GET_TRENDING_PRODUCTS.getMethod());
        reqTrendingProducts.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqTrendingProducts.setSearch(searchTerm);
        reqTrendingProducts.setPage(String.valueOf(pageindex));
        reqTrendingProducts.setFilterType(filterType);
        final Call<ResGetTrendingProducts> resGetTrendingProductsCall = client.getTrendingProducts(reqTrendingProducts);
        resGetTrendingProductsCall.enqueue(new Callback<ResGetTrendingProducts>() {
            @Override
            public void onResponse(Call<ResGetTrendingProducts> call, Response<ResGetTrendingProducts> response) {
                AppDialog.showProgressDialog(TrendingActivity.this, false);
                mResGetTrendingProducts = response.body();
                if (mResGetTrendingProducts != null) {
                    if (mResGetTrendingProducts.getSuccess() == ServiceConstants.SUCCESS) {
                        if (isFromFilters)
                            mResTrendingProductsData.clear();
                        mResTrendingProductsData.addAll(mResGetTrendingProducts.getTrendingProductData());
                        if (!TextUtils.isEmpty(searchTerm) || pageindex == 1)
                            setAdapter();
                        else
                            mTrendingProductsRecyclerAdapter.notifyDataSetChanged();
                        // mTrendingProductsRecyclerAdapter.notifyDataSetChanged();
                        mFavProductCount = mResGetTrendingProducts.getFavCount();
                        tvToolbarFavouriteCount.setText(String.valueOf(mResGetTrendingProducts.getFavCount()));
                        if (Integer.parseInt(mResGetTrendingProducts.getFavCount()) > 0)
                            tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                        else
                            tvToolbarFavouriteCount.setVisibility(View.GONE);
                    } else {
                        //ToastUtils.showShortToast(TrendingActivity.this, mResGetTrendingProducts.getErrstr());
                        if (isFromFilters) {
                            mResTrendingProductsData.clear();
                        }
                        mTrendingProductsRecyclerAdapter.notifyDataSetChanged();
                        //tvToolbarFavouriteCount.setText("0");
                        //tvToolbarFavouriteCount.setVisibility(View.GONE);
                    }
                } else {
                    ToastUtils.showShortToast(TrendingActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResGetTrendingProducts> call, Throwable t) {
                AppDialog.showProgressDialog(TrendingActivity.this, false);
                ToastUtils.showShortToast(TrendingActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        Intent intent;
        ResTrendingProductsData trendingProduct = mResTrendingProductsData.get(position);
        if (trendingProduct.getProductType().equals(ServiceConstants.FS_STORE_PRODUCT)) {
            intent = new Intent(this, FsStoreProductDetail.class);
            intent.putExtra("productId", trendingProduct.getProductID());
        } else {
            intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("productId", trendingProduct.getProductID());
        }

        intent.putExtra("productName", trendingProduct.getName());
        intent.putExtra("productPosition", position);
        startActivityForResult(intent, AppConstants.RC_PRODUCTS_DETAIL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                AppUtilMethods.hideKeyBoard(this, v);
                AppUtilMethods.hideKeyBoard(this);
                AppUtilMethods.hideKeyBoard(this, etSearch);
                finish();
                break;
            case R.id.btn_products:
                if (!btnProducts.isSelected()) {
                    btnProducts.setSelected(true);
                    //btnCoupons.setSelected(false);
                    if (mResTrendingProductsData.size() == 0)
                        getTrendingProducts(etSearch.getText().toString(), "", "", "", 0, mPageindex, false);
                    rvTrendingProduct.setVisibility(View.VISIBLE);
                    rvTrendingCoupon.setVisibility(View.GONE);
                    tvToolbarFavouriteCount.setText(mFavProductCount);
                    int visibility = mIsProductFilter ? View.VISIBLE : View.GONE;
                    setProductFilterIndicatorVisibility(visibility, mIsProductFilter);
                }
                break;
            case R.id.rl_toolbar_favourite:
                Intent intent = new Intent(this, FavouritesActivity.class);
                if (btnProducts.isSelected())
                    intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_PRODUCTS);
//                else if (btnCoupons.isSelected())
//                    intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_COUPONS);
                startActivityForResult(intent, AppConstants.RC_FAVORITES);
                break;
            case R.id.iv_toolbar_filter:
                if (mPartnerDataList.isEmpty()) {
                    if (NetworkConnection.isNetworkConnected(this)) {
                        getPartnersAndBrands();
                    } else {
                        ToastUtils.showShortToast(this, R.string.err_network_connection);
                    }
                } else {
                    AppDialog.showProductFiltersDialog(this, ivFilter.getMeasuredWidth(), ivFilter.getMeasuredHeight(), etSearch.getText().toString().trim(), mProductFiltersList,
                            mPartnerDataList, mSelectedProductPartnersList, mBrandDataList, mSelectedBrandsList, mRangeList, mSelectedGenderList, 1);
                }
                break;
            case R.id.iv_cross_search:
                etSearch.setText("");
                ivCrossSearch.setVisibility(View.GONE);
                AppUtilMethods.hideKeyBoard(this);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.RC_PRODUCTS_DETAIL && resultCode == RESULT_OK) {
            int pos = data.getExtras().getInt("productPosition");
            int favourite = data.getExtras().getInt("productPositionFavourite");
            if (mResTrendingProductsData.get(pos).getFavourite() != favourite) {
                String fav = mFavProductCount;
                int favCount = Integer.parseInt(fav.isEmpty() ? "0" : fav);
                if (favourite == ServiceConstants.FAVOURITE)
                    favCount++;
                else
                    favCount--;
                mFavProductCount = String.valueOf(favCount);
                tvToolbarFavouriteCount.setText(mFavProductCount);
                if (favCount > 0)
                    tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                else
                    tvToolbarFavouriteCount.setVisibility(View.GONE);
            }
            mResTrendingProductsData.get(pos).setFavourite(favourite);
            mTrendingProductsRecyclerAdapter.notifyDataSetChanged();
        } else if (requestCode == AppConstants.RC_FAVORITES && resultCode == RESULT_OK) {
            if (NetworkConnection.isNetworkConnected(TrendingActivity.this)) {
                if (btnProducts.isSelected()) {
                    boolean isMaleSelected = mSelectedGenderList.get(0);
                    boolean isFemaleSelected = mSelectedGenderList.get(1);
                    String gender = ((isMaleSelected && isFemaleSelected) || (!isMaleSelected && !isFemaleSelected)) ?
                            "" :
                            (isMaleSelected ? String.valueOf(ServiceConstants.GENDER_MALE) : String.valueOf(ServiceConstants.GENDER_FEMALE));
                    int minPrice = mRangeList.get(0);
                    int maxPrice = mRangeList.get(1);
                    String minRange;
                    String maxRange;
                    if (minPrice == AppConstants.FILTER_MIN && maxPrice == AppConstants.FILTER_MAX) {
                        minRange = "";
                        maxRange = "";
                    } else {
                        minRange = String.valueOf(minPrice);
                        maxRange = String.valueOf(maxPrice);
                    }
                    getTrendingProducts(etSearch.getText().toString(), gender, minRange, maxRange, 0, mPageindex, false);
                }
//                else if (btnCoupons.isSelected())
//                    getTrendingCoupons(etSearch.getText().toString(), 0);
            } else {
                ToastUtils.showShortToast(TrendingActivity.this, getResources().getString(R.string.err_network_connection));
            }
        } else if (requestCode == AppConstants.RC_COUPON_LIST && resultCode == RESULT_OK) {
            //// TODO: 16/8/16 need to know flow 
        }
    }
}
