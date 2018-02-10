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
import android.view.MotionEvent;
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
import affle.com.fitstreet.adapters.ProductsListRecycleAdapter;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.request.ReqConnectDisconnectApps;
import affle.com.fitstreet.models.request.ReqGetFilters;
import affle.com.fitstreet.models.request.ReqProductsList;
import affle.com.fitstreet.models.request.ReqRedeemPointsAffiliate;
import affle.com.fitstreet.models.request.ReqSetFavouriteProduct;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResGetFilters;
import affle.com.fitstreet.models.response.ResGetProductsList;
import affle.com.fitstreet.models.response.ResProductsListData;
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
import butterknife.internal.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsListActivity extends BaseActivity implements IOnItemClickListener {
    @BindView(R.id.rv_products_list)
    RecyclerView rvProductsList;
    @BindView(R.id.et_search)
    EditText etSearch;
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
    @BindView(R.id.iv_cross_search)
    ImageView ivCrossSearch;
    private String mProductCatId = "";
    private String mProductCatName = "";
    private String mGender;
    private String mMinPrice;
    private String mMaxPrice;
    private ResGetProductsList mResGetProductsList;
    private List<ResProductsListData> mProductsListData = new ArrayList<>();
    private ProductsListRecycleAdapter mProductsListRecycleAdapter;
    private GridLayoutManager mGridLayoutManager;
    private List<ResGetFilters.ResPartnerData> mPartnerDataList;
    private List<ResGetFilters.ResBrandData> mBrandDataList;
    private List<String> mSelectedPartnersList;
    private List<String> mSelectedBrandsList;
    private List<String> mProductFiltersList;
    private List<Integer> mRangeList;
    private List<Boolean> mSelectedGenderList;
    private Bitmap mBitmap;
    private String mShareBody;
    private int mPageindex = 1;
    public ArrayList<Integer> mSelectedRangeForAffiliateProducts = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPageindex = 1;
        rvProductsList.setOnScrollListener(new EndlessRecyclerOnScrollListenerGrid(mGridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.d("onloadmore", "Load More data, Current Page = " + current_page);
                mPageindex += 1;
                getProductsList(etSearch.getText().toString(), "", "", "", 0, mPageindex, false);
            }
        });
        if (requestCode == AppConstants.RC_PRODUCTS_DETAIL && resultCode == RESULT_OK) {
            int pos = data.getExtras().getInt("productPosition");
            int favourite = data.getExtras().getInt("productPositionFavourite");
            if (mProductsListData != null) {
                if (mProductsListData.get(pos).getFavourite() != favourite) {
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
                mProductsListData.get(pos).setFavourite(favourite);
                mProductsListRecycleAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == AppConstants.RC_FAVORITES && resultCode == RESULT_OK) {
            if (NetworkConnection.isNetworkConnected(ProductsListActivity.this)) {
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
                getProductsList(etSearch.getText().toString(), gender, minRange, maxRange, 0, mPageindex, true);
            } else {
                ToastUtils.showShortToast(ProductsListActivity.this, getString(R.string.err_network_connection));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShareBody = getString(R.string.txt_share_body);
        setContentView(R.layout.activity_products_list);
        Intent intent = getIntent();
        if (intent != null) {
            mProductCatId = intent.getExtras().getString("productCatId");
            mProductCatName = intent.getExtras().getString("productCatName");
        }
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(mProductCatName.toUpperCase());
        rlToolBarFavourite.setVisibility(View.VISIBLE);
        rlToolBarFilter.setVisibility(View.VISIBLE);
        ivFilter.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mPageindex = 1;
        rvProductsList.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        mProductsListData = new ArrayList<ResProductsListData>();
        mProductsListRecycleAdapter = new ProductsListRecycleAdapter(ProductsListActivity.this, mProductsListData);

        mGridLayoutManager = new GridLayoutManager(ProductsListActivity.this, 2);
        rvProductsList.setLayoutManager(mGridLayoutManager);
        ivCrossSearch.setOnClickListener(this);
        setAdapter();


        //set listeners
        mProductsListRecycleAdapter.setOnItemClickListener(this);
        ivFilter.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rlToolBarFavourite.setOnClickListener(this);


        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    AppUtilMethods.hideKeyBoard(ProductsListActivity.this);
                    if (NetworkConnection.isNetworkConnected(ProductsListActivity.this)) {
                        getProductsList(etSearch.getText().toString(), "", "", "", 0, 1, true);
                    } else {
                        ToastUtils.showShortToast(ProductsListActivity.this, getResources().getString(R.string.err_network_connection));
                    }
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //mProductsListRecycleAdapter.getFilter().filter(s.toString());
                if (s.length() > 0) {
                    ivCrossSearch.setVisibility(View.VISIBLE);
                } else {
                    ivCrossSearch.setVisibility(View.GONE);
                    AppUtilMethods.hideKeyBoard(ProductsListActivity.this);
                    mPageindex = 1;
                    if (NetworkConnection.isNetworkConnected(ProductsListActivity.this)) {
                        mMinPrice = "";
                        mGender = "";
                        mMinPrice = "";
                        getProductsList("", "", "", "", 0, 1, true);
                    } else {
                        ToastUtils.showShortToast(ProductsListActivity.this, getResources().getString(R.string.err_network_connection));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setAdapter() {
        rvProductsList.setAdapter(mProductsListRecycleAdapter);
        rvProductsList.setOnScrollListener(new EndlessRecyclerOnScrollListenerGrid(mGridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.d("onloadmore", "Load More data, Current Page = " + current_page);
                mPageindex += 1;
                getProductsList(etSearch.getText().toString(), mGender, mMinPrice, mMaxPrice, 0, mPageindex, false);
            }
        });
    }

    @Override
    protected void initVariables() {
        mProductFiltersList = Arrays.asList(getResources().getStringArray(R.array.arr_filter_product));
        mPartnerDataList = new ArrayList<ResGetFilters.ResPartnerData>();
        mBrandDataList = new ArrayList<ResGetFilters.ResBrandData>();
        mSelectedPartnersList = new ArrayList<String>();
        mSelectedBrandsList = new ArrayList<String>();
        mRangeList = new ArrayList<Integer>(2);
        mRangeList.add(AppConstants.FILTER_MIN);
        mRangeList.add(AppConstants.FILTER_MAX);
        mSelectedGenderList = new ArrayList<Boolean>(2);
        mSelectedGenderList.add(false);
        mSelectedGenderList.add(false);

        if (NetworkConnection.isNetworkConnected(ProductsListActivity.this)) {
            getProductsList(etSearch.getText().toString(), "", "", "", 0, mPageindex, false);
        } else {
            ToastUtils.showShortToast(ProductsListActivity.this, getString(R.string.err_network_connection));
        }
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
     * Method used to hit get partners and brand service
     */
    private void getPartnersAndBrands() {
        AppDialog.showProgressDialog(ProductsListActivity.this, true);
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
                AppDialog.showProgressDialog(ProductsListActivity.this, false);
                ResGetFilters resGetFilters = response.body();
                if (resGetFilters != null) {
                    if (resGetFilters.getSuccess() == ServiceConstants.SUCCESS) {
                        mPartnerDataList.addAll(resGetFilters.getPartnerData());
                        mBrandDataList.addAll(resGetFilters.getBrandData());
                        ivFilter.performClick();
                    } else {
                        ToastUtils.showShortToast(ProductsListActivity.this, resGetFilters.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(ProductsListActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResGetFilters> call, Throwable t) {
                AppDialog.showProgressDialog(ProductsListActivity.this, false);
                ToastUtils.showShortToast(ProductsListActivity.this, R.string.err_network_connection);
            }
        });
    }

    public void setFavouriteUnFavourite(final int position, final String methodName) {
        AppDialog.showProgressDialog(ProductsListActivity.this, true);
        IApiClient client = ApiClient.getApiClient();
        ReqSetFavouriteProduct reqSetFavouriteProduct = new ReqSetFavouriteProduct();
        reqSetFavouriteProduct.setProductID(mProductsListData.get(position).getProductID());
        reqSetFavouriteProduct.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqSetFavouriteProduct.setMethod(methodName);
        reqSetFavouriteProduct.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        Call<ResBase> resBaseCall = client.setFavouriteProduct(reqSetFavouriteProduct);
        resBaseCall.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                ResBase resBase = response.body();
                AppDialog.showProgressDialog(ProductsListActivity.this, false);
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        String fav = tvToolbarFavouriteCount.getText().toString().trim();
                        int favCount = Integer.parseInt(fav.isEmpty() ? "0" : fav);
                        if (methodName.equals(MethodFactory.UNFAVOURITE_PRODUCT.getMethod())) {
                            ToastUtils.showShortToast(ProductsListActivity.this, getString(R.string.txt_success_unfavourite_product));
                            mProductsListData.get(position).setFavourite(ServiceConstants.UNFAVOURITE);
                            mProductsListRecycleAdapter.notifyDataSetChanged();
                            tvToolbarFavouriteCount.setText(String.valueOf(favCount - 1));
                            if ((favCount - 1) > 0)
                                tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                            else
                                tvToolbarFavouriteCount.setVisibility(View.GONE);
                        } else {
                            mProductsListData.get(position).setFavourite(ServiceConstants.FAVOURITE);
                            ToastUtils.showShortToast(ProductsListActivity.this, getString(R.string.txt_success_set_favourite_product));
                            mProductsListRecycleAdapter.notifyDataSetChanged();
                            tvToolbarFavouriteCount.setText(String.valueOf(favCount + 1));
                            if ((favCount + 1) > 0)
                                tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                            else
                                tvToolbarFavouriteCount.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtils.showShortToast(ProductsListActivity.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(ProductsListActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(ProductsListActivity.this, false);
                ToastUtils.showShortToast(ProductsListActivity.this, R.string.err_network_connection);

            }
        });
    }

    public void getProductsList(final String searchTerm, final String gender, final String minPrice, final String maxPrice, int from, final int pageIndex, final boolean isFromFilters) {
        if (from == 0) {
            AppDialog.showProgressDialog(ProductsListActivity.this, true);
        }
        mPageindex = pageIndex;
        //showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        ReqProductsList.FilterType filterType = new ReqProductsList.FilterType();
        filterType.setBrandID(mSelectedBrandsList);
        filterType.setPartnerID(mSelectedPartnersList);
        filterType.setGender(gender);
        ReqProductsList.PriceRange priceRange = new ReqProductsList.PriceRange();
        priceRange.setMin(minPrice);
        priceRange.setMax(maxPrice);
        filterType.setPriceRange(priceRange);
        mMaxPrice = maxPrice;
        mMinPrice = minPrice;
        mGender = gender;
        ReqProductsList reqProductsList = new ReqProductsList();
        reqProductsList.setSearch(searchTerm);
        reqProductsList.setPage(String.valueOf(pageIndex));
        reqProductsList.setProCatID(mProductCatId);
        reqProductsList.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqProductsList.setMethod(MethodFactory.GET_PRODUCTS_LIST.getMethod());
        reqProductsList.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqProductsList.setFilterType(filterType);
        Call<ResGetProductsList> resGetProductsListCall = client.getProductsList(reqProductsList);
        resGetProductsListCall.enqueue(new Callback<ResGetProductsList>() {
            @Override
            public void onResponse(Call<ResGetProductsList> call, Response<ResGetProductsList> response) {
                AppDialog.showProgressDialog(ProductsListActivity.this, false);
                mResGetProductsList = response.body();
                if (mResGetProductsList != null) {
                    if (mResGetProductsList.getSuccess() == ServiceConstants.SUCCESS) {
                        if (isFromFilters)
                            mProductsListData.clear();
                        mProductsListData.addAll(mResGetProductsList.getCatProductData());
                        if (!TextUtils.isEmpty(searchTerm) || pageIndex == 1)
                            setAdapter();
                        else
                            mProductsListRecycleAdapter.notifyDataSetChanged();
                        tvToolbarFavouriteCount.setText(mResGetProductsList.getFavCount());
                        if (Integer.parseInt(mResGetProductsList.getFavCount()) > 0)
                            tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                        else
                            tvToolbarFavouriteCount.setVisibility(View.GONE);
                    } else {
                        if (isFromFilters) {
                            mProductsListData.clear();
//                            rvProductsList.setOnScrollListener(new EndlessRecyclerOnScrollListenerGrid(mGridLayoutManager) {
//                                @Override
//                                public void onLoadMore(int current_page) {
//                                    Log.d("onloadmore", "Load More data, Current Page = " + current_page);
//                                    mPageindex += 1;
//                                    getProductsList(etSearch.getText().toString(), gender, minPrice, maxPrice, 0, mPageindex, false);
//                                }
//                            });
                        }
                        mProductsListData.addAll(mResGetProductsList.getCatProductData());
                        mProductsListRecycleAdapter.notifyDataSetChanged();
                        //tvToolbarFavouriteCount.setText("0");
                        // tvToolbarFavouriteCount.setVisibility(View.GONE);
                        // ToastUtils.showShortToast(ProductsListActivity.this, mResGetProductsList.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(ProductsListActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResGetProductsList> call, Throwable t) {
                AppDialog.showProgressDialog(ProductsListActivity.this, false);
                ToastUtils.showShortToast(ProductsListActivity.this, R.string.err_network_connection);
            }
        });
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
            case R.id.iv_toolbar_filter:
                if (mPartnerDataList.isEmpty()) {
                    if (NetworkConnection.isNetworkConnected(this)) {
                        getPartnersAndBrands();
                    } else {
                        ToastUtils.showShortToast(this, R.string.err_network_connection);
                    }
                } else {
                    AppDialog.showProductFiltersDialog(this, ivFilter.getMeasuredWidth(), ivFilter.getMeasuredHeight(), etSearch.getText().toString().trim(), mProductFiltersList,
                            mPartnerDataList, mSelectedPartnersList, mBrandDataList, mSelectedBrandsList, mRangeList, mSelectedGenderList, mPageindex);
                }
                break;
            case R.id.rl_toolbar_favourite:
                Intent intent = new Intent(this, FavouritesActivity.class);
                intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_PRODUCTS);
                startActivityForResult(intent, AppConstants.RC_FAVORITES);
                break;
            case R.id.iv_cross_search:
                etSearch.setText("");
                ivCrossSearch.setVisibility(View.GONE);
                AppUtilMethods.hideKeyBoard(this);
                break;
        }
    }


    public void shareProduct(Bitmap bitmap, String shareBody) {
        mBitmap = bitmap;
        if (PermissionUtils.hasStoragePermission(this)) {
            String shareSubject = getString(R.string.app_name);
            String imagePath = AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_PRODUCT_IMAGE_PATH + +System.currentTimeMillis() + AppConstants.EXTN_JPG;
            CameraAndGalleryUtils.saveImageToFile(bitmap, imagePath);
            AppUtilMethods.openImageShareDialog(this, shareSubject, mShareBody, imagePath);
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

    @Override
    public void onItemClick(View view, int position, int tag) {
        Logger.e("on click ll product");
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("productId", mProductsListData.get(position).getProductID());
        intent.putExtra("productName", mProductsListData.get(position).getName());
        intent.putExtra("productPosition", position);
        startActivityForResult(intent, AppConstants.RC_PRODUCTS_DETAIL);
    }
}
