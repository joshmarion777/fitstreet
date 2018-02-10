package affle.com.fitstreet.ui.activities;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.EndlessRecyclerOnScrollListenerGrid;
import affle.com.fitstreet.adapters.FsStoreProductsListAdapter;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.request.ReqFsStoreProductsList;
import affle.com.fitstreet.models.request.ReqSetFavouriteProduct;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResFsStoreProductsList;
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
import affle.com.fitstreet.utils.PermissionUtils;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FsStoreMenWomenActivity extends BaseActivity implements IOnItemClickListener {
    public int mGender;
    public ArrayList<Integer> mSelectedRangeForFSProducts = new ArrayList<>();
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv_fs_store_product_list)
    RecyclerView mRvFsStoreProductList;
    @BindView(R.id.tv_toolbar_favourite_count)
    TextView tvToolbarFavouriteCount;
    @BindView(R.id.rl_toolbar_filter)
    RelativeLayout rlToolbarFilter;
    @BindView(R.id.rl_toolbar_favourite)
    RelativeLayout rlToolbarFavourite;
    @BindView(R.id.rl_toolbar_mycart)
    RelativeLayout rlToolbarMyCart;
    @BindView(R.id.iv_toolbar_filter)
    ImageView ivToolbarFilter;
    @BindView(R.id.iv_toolbar_filter_active)
    ImageView ivFilterIndicator;
    @BindView(R.id.iv_cross_search)
    ImageView ivCrossSearch;
    GridLayoutManager gridLayoutManager;
    private List<String> mProductFiltersList;
    private List<String> mSelectedCategoryList = new ArrayList<>();
    private ArrayList<ResFsStoreProductsList.FsCatDatum> mCategoryList = new ArrayList<>();
    private List<Integer> mRangeList;
    private ResFsStoreProductsList mResFsStoreProductsList;
    private FsStoreProductsListAdapter mFsStoreProductsListAdapter;
    private List<ResFsStoreProductsList.FsProductDatum> mResFsStoreProductsListData = new ArrayList<>();
    private String mShareBody;
    private Bitmap mBitmap;
    private int mPageindex = 1;
    private String mMinPrice = "";
    private String mMaxPrice = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_FS_STORE_PRODUCT_DETAIL && resultCode == RESULT_OK) {
            int pos = data.getExtras().getInt("productPosition");
            String productId = data.getExtras().getString("productId");
            int favourite = data.getExtras().getInt("productPositionFavourite");
            if (mResFsStoreProductsListData != null) {
                if (mResFsStoreProductsListData.get(pos).getFavourite() != favourite) {
                    String fav = tvToolbarFavouriteCount.getText().toString().trim();
                    int favCount = Integer.parseInt(fav.isEmpty() ? "0" : fav);
                    if (favourite == ServiceConstants.FAVOURITE) {
                        favCount++;
                    } else {
                        favCount--;
                    }
                    tvToolbarFavouriteCount.setText(String.valueOf(favCount));
                    if (favCount > 0) {
                        tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                    } else {
                        tvToolbarFavouriteCount.setVisibility(View.GONE);
                    }

                }
                for (int i = 0; i < mResFsStoreProductsListData.size(); i++) {
                    if (mResFsStoreProductsListData.get(i).getProductID().equals(productId)) {
                        mResFsStoreProductsListData.get(i).setFavourite(favourite);
                    }
                }
                //mResFsStoreProductsListData.get(pos).setFavourite(favourite);
                mFsStoreProductsListAdapter.notifyDataSetChanged();
            }
        }

        //super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fs_store_men_women);
        mShareBody = getString(R.string.txt_share_body);
        super.initData();


    }

    @Override
    protected void initViews() {
        rlToolbarFilter.setVisibility(View.VISIBLE);
        //rlToolbarMyCart.setVisibility(View.VISIBLE);
        rlToolbarFavourite.setVisibility(View.VISIBLE);
        rlToolbarFavourite.setOnClickListener(this);
        ivCrossSearch.setOnClickListener(this);
        if (getIntent().getIntExtra("FsStoreGender", 1) == AppConstants.GENDER_MALE) {
            tvTitle.setText(R.string.txt_shop_men_fs_store_title);
        } else {
            tvTitle.setText(R.string.txt_shop_women_fs_store_title);
        }

        gridLayoutManager = new GridLayoutManager(FsStoreMenWomenActivity.this, 2);
        mRvFsStoreProductList.setLayoutManager(gridLayoutManager);
        mRvFsStoreProductList.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        mFsStoreProductsListAdapter = new FsStoreProductsListAdapter(FsStoreMenWomenActivity.this, mResFsStoreProductsListData);
        mFsStoreProductsListAdapter.setOnItemClickListener(this);
        setAdapter();
        if (NetworkConnection.isNetworkConnected(FsStoreMenWomenActivity.this)) {
            if (getIntent().getIntExtra("FsStoreGender", 1) == AppConstants.GENDER_MALE) {
                mGender = AppConstants.GENDER_MALE;
                getFsStoreProducts("", mGender, "", "", mPageindex, false);
            } else {
                mGender = AppConstants.GENDER_FEMALE;
                getFsStoreProducts("", mGender, "", "", mPageindex, false);
            }
        } else {
            ToastUtils.showShortToast(FsStoreMenWomenActivity.this, getString(R.string.err_network_connection));
        }


        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    AppUtilMethods.hideKeyBoard(FsStoreMenWomenActivity.this);
                    if (NetworkConnection.isNetworkConnected(FsStoreMenWomenActivity.this)) {
                        getFsStoreProducts(etSearch.getText().toString(), mGender, "", "", 1, true);
                    } else {
                        ToastUtils.showShortToast(FsStoreMenWomenActivity.this, getResources().getString(R.string.err_network_connection));
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
                //mFsStoreProductsListAdapter.getFilter().filter(charSequence.toString());
                if (charSequence.length() > 0) {
                    ivCrossSearch.setVisibility(View.VISIBLE);
                } else {
                    ivCrossSearch.setVisibility(View.GONE);
                    AppUtilMethods.hideKeyBoard(FsStoreMenWomenActivity.this);
                    mPageindex = 1;
                    mMinPrice = "";
                    mMaxPrice = "";
                    if (NetworkConnection.isNetworkConnected(FsStoreMenWomenActivity.this)) {

                        getFsStoreProducts("", mGender, "", "", 1, true);
                    } else {
                        ToastUtils.showShortToast(FsStoreMenWomenActivity.this, getResources().getString(R.string.err_network_connection));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * setting adapters and onScroll listeners for paging
     */
    private void setAdapter() {
        mRvFsStoreProductList.setAdapter(mFsStoreProductsListAdapter);
        mRvFsStoreProductList.addOnScrollListener(new EndlessRecyclerOnScrollListenerGrid(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.d("onloadmore", "Load More data, Current Page = " + current_page);
                mPageindex += 1;
                getFsStoreProducts(etSearch.getText().toString(), AppConstants.GENDER_MALE, mMaxPrice, mMinPrice, mPageindex, false);
            }
        });
    }

    /**
     * Sharing product
     *
     * @param bitmap
     * @param shareBody
     */
    public void shareProduct(Bitmap bitmap, String shareBody) {
        mBitmap = bitmap;
        if (PermissionUtils.hasStoragePermission(this)) {
            String shareSubject = getString(R.string.app_name);
            String imagePath = AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_PRODUCT_IMAGE_PATH + +System.currentTimeMillis() + AppConstants.EXTN_JPG;
            CameraAndGalleryUtils.saveImageToFile(bitmap, imagePath);
            AppUtilMethods.openImageShareDialog(this, shareSubject, mShareBody, imagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.RC_REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    shareProduct(mBitmap, mShareBody);
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
     * setting favourites/unfavourites to/from server
     *
     * @param position
     * @param methodName
     */
    public void setFavouriteUnFavourite(final int position, final String methodName) {
        AppDialog.showProgressDialog(this, true);
        IApiClient client = ApiClient.getApiClient();
        ReqSetFavouriteProduct reqSetFavouriteProduct = new ReqSetFavouriteProduct();
        reqSetFavouriteProduct.setProductID(mResFsStoreProductsListData.get(position).getProductID());
        reqSetFavouriteProduct.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqSetFavouriteProduct.setMethod(methodName);
        reqSetFavouriteProduct.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        Call<ResBase> resBaseCall = client.setFavouriteProduct(reqSetFavouriteProduct);
        resBaseCall.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                AppDialog.showProgressDialog(FsStoreMenWomenActivity.this, false);
                ResBase resBase = response.body();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        String fav = tvToolbarFavouriteCount.getText().toString().trim();
                        int favCount = Integer.parseInt(fav.isEmpty() ? "0" : fav);
                        if (methodName.equals(MethodFactory.UNFAVOURITE_PRODUCT.getMethod().toString())) {
                            ToastUtils.showShortToast(FsStoreMenWomenActivity.this, getString(R.string.txt_success_unfavourite_product));
                            mResFsStoreProductsListData.get(position).setFavourite(ServiceConstants.UNFAVOURITE);
                            mFsStoreProductsListAdapter.notifyDataSetChanged();
                            tvToolbarFavouriteCount.setText(String.valueOf(favCount - 1));
                            if ((favCount - 1) > 0)
                                tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                            else
                                tvToolbarFavouriteCount.setVisibility(View.GONE);
                        } else {
                            mResFsStoreProductsListData.get(position).setFavourite(ServiceConstants.FAVOURITE);
                            ToastUtils.showShortToast(FsStoreMenWomenActivity.this, getString(R.string.txt_success_set_favourite_product));
                            mFsStoreProductsListAdapter.notifyDataSetChanged();
                            tvToolbarFavouriteCount.setText(String.valueOf(favCount + 1));
                            if ((favCount + 1) > 0)
                                tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                            else
                                tvToolbarFavouriteCount.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtils.showShortToast(FsStoreMenWomenActivity.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(FsStoreMenWomenActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(FsStoreMenWomenActivity.this, false);
                ToastUtils.showShortToast(FsStoreMenWomenActivity.this, R.string.err_network_connection);

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

    @Override
    protected void initVariables() {
        ivBack.setOnClickListener(this);
        rlToolbarFilter.setOnClickListener(this);
        mRangeList = new ArrayList<Integer>(2);
        mProductFiltersList = Arrays.asList(getResources().getStringArray(R.array.arr_filter_fs_product));
        mRangeList.add(AppConstants.FILTER_MIN);
        mRangeList.add(AppConstants.FILTER_MAX);
    }

    /**
     * Getting Fs Store products from server
     *
     * @param searchTerm
     * @param gender
     * @param maxRange
     * @param minRange
     * @param pageindex
     * @param isFromFilters
     */
    public void getFsStoreProducts(final String searchTerm, int gender, String maxRange, String minRange, final int pageindex, final boolean isFromFilters) {
        AppDialog.showProgressDialog(FsStoreMenWomenActivity.this, true);
        IApiClient apiClient = ApiClient.getApiClient();
        ReqFsStoreProductsList reqFsStoreProductsList = new ReqFsStoreProductsList();
        reqFsStoreProductsList.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqFsStoreProductsList.setMethod(MethodFactory.GET_FS_STORE_PRODUCTS_LIST.getMethod());
        reqFsStoreProductsList.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, getString(R.string.txt_skip_user_id)));
        reqFsStoreProductsList.setGender(gender);
        reqFsStoreProductsList.setSearch(searchTerm);
        reqFsStoreProductsList.setPage(String.valueOf(pageindex));
        ReqFsStoreProductsList.FilterType filterType = new ReqFsStoreProductsList.FilterType();
        ReqFsStoreProductsList.PriceRange priceRange = new ReqFsStoreProductsList.PriceRange();
        priceRange.setMax(maxRange);
        priceRange.setMin(minRange);
        filterType.setPriceRange(priceRange);
        filterType.setProCatID(mSelectedCategoryList);
        reqFsStoreProductsList.setFilterType(filterType);
        apiClient.getFsStoreProductsList(reqFsStoreProductsList);
        Call<ResFsStoreProductsList> resFsStoreProductsListCall = apiClient.getFsStoreProductsList(reqFsStoreProductsList);
        resFsStoreProductsListCall.enqueue(new Callback<ResFsStoreProductsList>() {
            @Override
            public void onResponse(Call<ResFsStoreProductsList> call, Response<ResFsStoreProductsList> response) {
                mResFsStoreProductsList = new ResFsStoreProductsList();
                AppDialog.showProgressDialog(FsStoreMenWomenActivity.this, false);
                mResFsStoreProductsList = response.body();
                if (mResFsStoreProductsList != null) {
                    if (mResFsStoreProductsList.getSuccess() == ServiceConstants.SUCCESS) {
                        if (isFromFilters)
                            mResFsStoreProductsListData.clear();
                        mResFsStoreProductsListData.addAll(mResFsStoreProductsList.getFsProductData());
                        tvToolbarFavouriteCount.setText(mResFsStoreProductsList.getFavCount());
                        if (!TextUtils.isEmpty(searchTerm) || pageindex == 1)
                            setAdapter();
                        else
                            mFsStoreProductsListAdapter.notifyDataSetChanged();
                    } else {
                        if (isFromFilters)
                            mResFsStoreProductsListData.clear();
                        mResFsStoreProductsListData.addAll(mResFsStoreProductsList.getFsProductData());
                        mFsStoreProductsListAdapter.notifyDataSetChanged();
                        //tvToolbarFavouriteCount.setText(mResFsStoreProductsList.getFavCount());
                        //tvToolbarFavouriteCount.setVisibility(View.GONE);
                        // ToastUtils.showShortToast(FsStoreMenWomenActivity.this, mResFsStoreProductsList.getErrstr());
                    }

                } else {
                    ToastUtils.showShortToast(FsStoreMenWomenActivity.this, R.string.err_network_connection);

                }
            }

            @Override
            public void onFailure(Call<ResFsStoreProductsList> call, Throwable t) {
                AppDialog.showProgressDialog(FsStoreMenWomenActivity.this, false);
                ToastUtils.showShortToast(FsStoreMenWomenActivity.this, R.string.err_network_connection);
            }
        });


    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.rl_toolbar_filter:
                if (mCategoryList.isEmpty()) {
                    if (NetworkConnection.isNetworkConnected(this)) {
                        if (mResFsStoreProductsList != null) {
                            for (int i = 0; i < mResFsStoreProductsList.getFsCatData().size(); i++) {
                                mCategoryList.add(mResFsStoreProductsList.getFsCatData().get(i));
                            }
                            AppDialog.showFSProductFiltersDialog(this, mGender, ivToolbarFilter.getMeasuredWidth(), ivToolbarFilter.getMeasuredHeight(), etSearch.getText().toString().trim(), mProductFiltersList,
                                    mCategoryList, mSelectedCategoryList, mRangeList, mPageindex);
                        }
                    } else {
                        ToastUtils.showShortToast(this, R.string.err_network_connection);
                    }
                } else {
                    AppDialog.showFSProductFiltersDialog(this, mGender, ivToolbarFilter.getMeasuredWidth(), ivToolbarFilter.getMeasuredHeight(), etSearch.getText().toString().trim(), mProductFiltersList,
                            mCategoryList, mSelectedCategoryList, mRangeList, mPageindex);

                }
                break;
            case R.id.rl_toolbar_favourite:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        Intent intent = new Intent(this, FavouritesActivity.class);
                        intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_PRODUCTS);
                        startActivityForResult(intent, AppConstants.RC_FAVORITES);
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
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
    public void onItemClick(View view, int position, int tag) {
        Intent intent = new Intent(this, FsStoreProductDetail.class);
        ResFsStoreProductsList.FsProductDatum fsProductDatum = (ResFsStoreProductsList.FsProductDatum) view.getTag();
        intent.putExtra("productId", fsProductDatum.getProductID());
        intent.putExtra("productPosition", position);
        startActivityForResult(intent, AppConstants.RC_FS_STORE_PRODUCT_DETAIL);
    }
}
