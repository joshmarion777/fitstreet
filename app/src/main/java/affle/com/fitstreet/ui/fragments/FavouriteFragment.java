package affle.com.fitstreet.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.EndlessRecyclerOnScrollListenerGrid;
import affle.com.fitstreet.adapters.FavouriteCouponsRecycleAdapter;
import affle.com.fitstreet.adapters.FavouriteProductsRecyclerAdapter;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.request.ReqFavouriteCoupons;
import affle.com.fitstreet.models.request.ReqFavouriteProducts;
import affle.com.fitstreet.models.response.ResFavouriteCouponsData;
import affle.com.fitstreet.models.response.ResFavouriteProductsData;
import affle.com.fitstreet.models.response.ResGetFavouriteCoupons;
import affle.com.fitstreet.models.response.ResGetFavouriteProducts;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.activities.FavouritesActivity;
import affle.com.fitstreet.ui.activities.FsStoreProductDetail;
import affle.com.fitstreet.ui.activities.HomeActivity;
import affle.com.fitstreet.ui.activities.ProductDetailActivity;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.GridSpacingItemDecoration;
import affle.com.fitstreet.utils.Logger;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends BaseFragment implements IOnItemClickListener {
    @BindView(R.id.btn_products)
    Button btnProducts;
    @BindView(R.id.btn_coupons)
    Button btnCoupons;
    @BindView(R.id.rv_favourite_products)
    RecyclerView rvFavouriteProducts;
    @BindView(R.id.rv_favourite_coupons)
    RecyclerView rvFavouriteCoupons;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_cross_search)
    ImageView ivCrossSearch;
    private FavouriteCouponsRecycleAdapter favouriteCouponsRecycleAdapter;
    public FavouriteProductsRecyclerAdapter favouriteProductsRecyclerAdapter;
    private List<ResFavouriteProductsData> resFavouriteProductsData = new ArrayList<>();
    private List<ResFavouriteCouponsData> resFavouriteCouponsData = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private GridLayoutManager gridLayoutManager;
    private CustomTypefaceTextView tvToolbarFavouriteCount;
    private ResGetFavouriteProducts resGetFavouriteProducts;
    private int fromWhere;
    private int mPageIndex = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initViews() {
        if (mActivity instanceof HomeActivity) {
            ((HomeActivity) mActivity).setVisibilityBottomLayout(false);
            ((HomeActivity) mActivity).setActionBarTitle(R.string.txt_favourite);
            fromWhere = ((HomeActivity) mActivity).getFromWhere();
        } else if (mActivity instanceof FavouritesActivity) {
            fromWhere = ((FavouritesActivity) mActivity).getFromWhere();
        }
        switch (fromWhere) {
            case AppConstants.FROM_PRODUCTS:
                btnProducts.setSelected(true);
                break;
            case AppConstants.FROM_COUPONS:
                btnCoupons.setSelected(true);
                break;
        }

        resFavouriteCouponsData = new ArrayList<ResFavouriteCouponsData>();
        resFavouriteProductsData = new ArrayList<ResFavouriteProductsData>();
        favouriteCouponsRecycleAdapter = new FavouriteCouponsRecycleAdapter(mActivity, resFavouriteCouponsData, FavouriteFragment.this);
        favouriteProductsRecyclerAdapter = new FavouriteProductsRecyclerAdapter(mActivity, resFavouriteProductsData, FavouriteFragment.this);
        rvFavouriteCoupons.setAdapter(favouriteCouponsRecycleAdapter);
        gridLayoutManager = new GridLayoutManager(mActivity, 2);
        rvFavouriteProducts.setLayoutManager(gridLayoutManager);
        setFavoriteAdapter();

        //set listeners
        favouriteProductsRecyclerAdapter.setOnItemClickListener(this);
        btnCoupons.setOnClickListener(this);
        btnProducts.setOnClickListener(this);
        ivCrossSearch.setOnClickListener(this);


        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    AppUtilMethods.hideKeyBoard(mActivity);
                    if (NetworkConnection.isNetworkConnected(mActivity)) {
                        getFavouriteProducts(etSearch.getText().toString(), 0, 1, true);
                    } else {
                        ToastUtils.showShortToast(mActivity, getResources().getString(R.string.err_network_connection));
                    }
                }
                return false;
            }
        });
        rvFavouriteProducts.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        tvToolbarFavouriteCount = (CustomTypefaceTextView) mActivity.findViewById(R.id.tv_toolbar_favourite_count);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (btnCoupons.isSelected()) {
//                    favouriteCouponsRecycleAdapter.getFilter().filter(s.toString());
//                } else {
//                    favouriteProductsRecyclerAdapter.getFilter().filter(s.toString());
//                }
                if (s.length() > 0) {
                    ivCrossSearch.setVisibility(View.VISIBLE);
                } else {
                    ivCrossSearch.setVisibility(View.GONE);
                    AppUtilMethods.hideKeyBoard(mActivity);
                    mPageIndex = 1;
                    if (NetworkConnection.isNetworkConnected(mActivity)) {
                        getFavouriteProducts("", 0, 1, true);
                    } else {
                        ToastUtils.showShortToast(mActivity, getResources().getString(R.string.err_network_connection));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * Setting adapter and Scroll listener for paging
     */
    private void setFavoriteAdapter() {
        rvFavouriteProducts.setAdapter(favouriteProductsRecyclerAdapter);
        rvFavouriteProducts.addOnScrollListener(new EndlessRecyclerOnScrollListenerGrid(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.d("onloadmore", "Load More data, Current Page = " + current_page);
                mPageIndex += 1;
                getFavouriteProducts(etSearch.getText().toString(), 0, mPageIndex, false);
            }
        });
    }

    @Override
    protected void initVariables() {
        switch (fromWhere) {
            case AppConstants.FROM_PRODUCTS:
                if (NetworkConnection.isNetworkConnected(mActivity)) {
                    rvFavouriteCoupons.setVisibility(View.GONE);
                    rvFavouriteProducts.setVisibility(View.VISIBLE);
                    getFavouriteProducts(etSearch.getText().toString(), 0, 1, false);
                } else {
                    ToastUtils.showShortToast(mActivity, getContext().getResources().getString(R.string.err_network_connection));
                }
                break;
            case AppConstants.FROM_COUPONS:
                if (NetworkConnection.isNetworkConnected(mActivity)) {
                    rvFavouriteCoupons.setVisibility(View.VISIBLE);
                    rvFavouriteProducts.setVisibility(View.GONE);
                    getFavouriteCoupons(etSearch.getText().toString(), 0);
                } else {
                    ToastUtils.showShortToast(mActivity, getContext().getResources().getString(R.string.err_network_connection));
                }
                break;
        }
    }

    /**
     * This method is used to show the progress dialog
     *
     * @return void
     */
    public void showProgressDialog() {
        mProgressDialog = AppDialog.showProgressDialog(mActivity);
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

    /**
     * Get favourite coupons list from server
     *
     * @param searchTerm
     * @param from
     */
    private void getFavouriteCoupons(String searchTerm, int from) {
        if (from == 0)
            AppDialog.showProgressDialog(mActivity, true);
        rvFavouriteProducts.setVisibility(View.GONE);
        rvFavouriteCoupons.setVisibility(View.VISIBLE);
        gridLayoutManager = new GridLayoutManager(mActivity, 1);
        rvFavouriteCoupons.setLayoutManager(gridLayoutManager);
        //       rvFavouriteCoupons.addItemDecoration(new ItemDecorationAlbumColumns(mActivity));
        IApiClient client = ApiClient.getApiClient();
        final ReqFavouriteCoupons reqFavouriteCoupons = new ReqFavouriteCoupons();
        reqFavouriteCoupons.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqFavouriteCoupons.setMethod(MethodFactory.GET_FAVOURITE_COUPONS.getMethod());
        reqFavouriteCoupons.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqFavouriteCoupons.setSearch(searchTerm);
        Call<ResGetFavouriteCoupons> resGetFavouriteCouponsCall = client.getFavouriteCoupons(reqFavouriteCoupons);
        resGetFavouriteCouponsCall.enqueue(new Callback<ResGetFavouriteCoupons>() {
            @Override
            public void onResponse(Call<ResGetFavouriteCoupons> call, Response<ResGetFavouriteCoupons> response) {
                AppDialog.showProgressDialog(mActivity, false);
                ResGetFavouriteCoupons resGetFavouriteCoupons = response.body();
                if (resGetFavouriteCoupons != null) {
                    if (resGetFavouriteCoupons.getSuccess() == ServiceConstants.SUCCESS) {
                        resFavouriteCouponsData.clear();
                        resFavouriteCouponsData.addAll(resGetFavouriteCoupons.getFavCoupanData());
                        favouriteCouponsRecycleAdapter.notifyDataSetChanged();
                        tvToolbarFavouriteCount.setText("" + resFavouriteCouponsData.size());
                        if (resFavouriteCouponsData.size() > 0)
                            tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                        else
                            tvToolbarFavouriteCount.setVisibility(View.GONE);
                    } else {
                        resFavouriteCouponsData.clear();
                        favouriteCouponsRecycleAdapter.notifyDataSetChanged();
                        tvToolbarFavouriteCount.setText("0");
                        tvToolbarFavouriteCount.setVisibility(View.GONE);
//                        ToastUtils.showShortToast(mActivity, resGetFavouriteCoupons.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResGetFavouriteCoupons> call, Throwable t) {
                AppDialog.showProgressDialog(mActivity, false);
                ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
            }
        });


    }

    /**
     * Get favourite products list from server
     *
     * @param searchTerm
     * @param from
     */
    private void getFavouriteProducts(final String searchTerm, int from, final int pageindex, final boolean isFromFilter) {
        if (from == 0)
            AppDialog.showProgressDialog(mActivity, true);
        rvFavouriteCoupons.setVisibility(View.GONE);
        rvFavouriteProducts.setVisibility(View.VISIBLE);
        IApiClient client = ApiClient.getApiClient();
        final ReqFavouriteProducts reqFavouriteProducts = new ReqFavouriteProducts();
        reqFavouriteProducts.setPage(String.valueOf(pageindex));
        reqFavouriteProducts.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqFavouriteProducts.setMethod(MethodFactory.GET_FAVOURITE_PRODUCTS.getMethod());
        reqFavouriteProducts.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqFavouriteProducts.setSearch(searchTerm);
        Call<ResGetFavouriteProducts> resGetFavouriteProductsCall = client.getFavouriteProducts(reqFavouriteProducts);
        resGetFavouriteProductsCall.enqueue(new Callback<ResGetFavouriteProducts>() {
            @Override
            public void onResponse(Call<ResGetFavouriteProducts> call, Response<ResGetFavouriteProducts> response) {
                AppDialog.showProgressDialog(mActivity, false);
                if (isAdded()) {
                    resGetFavouriteProducts = response.body();
                    if (resGetFavouriteProducts != null) {
                        if (resGetFavouriteProducts.getSuccess() == ServiceConstants.SUCCESS) {
                            if (isFromFilter)
                                resFavouriteProductsData.clear();
                            resFavouriteProductsData.addAll(resGetFavouriteProducts.getFavProductData());
                            if (!TextUtils.isEmpty(searchTerm) || pageindex == 1)
                                setFavoriteAdapter();
                            else
                                favouriteProductsRecyclerAdapter.notifyDataSetChanged();
                            tvToolbarFavouriteCount.setText(String.valueOf(resGetFavouriteProducts.getTotalRecords()));
                            if (resFavouriteProductsData.size() > 0)
                                tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                            else
                                tvToolbarFavouriteCount.setVisibility(View.GONE);
                        } else {
                            if (isFromFilter)
                                resFavouriteProductsData.clear();
                            resFavouriteProductsData.addAll(resGetFavouriteProducts.getFavProductData());
                            favouriteProductsRecyclerAdapter.notifyDataSetChanged();
                        }
                    } else {
                        ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResGetFavouriteProducts> call, Throwable t) {
                AppDialog.showProgressDialog(mActivity, false);
                ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
            }
        });


    }

    /**
     * To notify adapters when any tab is selected
     */
    public void notifyData() {
        if (btnCoupons.isSelected()) {
            tvToolbarFavouriteCount.setText("" + resFavouriteCouponsData.size());
            favouriteCouponsRecycleAdapter.notifyDataSetChanged();
        } else {
            resGetFavouriteProducts.setTotalRecords(resGetFavouriteProducts.getTotalRecords() - 1);
            tvToolbarFavouriteCount.setText("" + (resGetFavouriteProducts.getTotalRecords()));
            favouriteProductsRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        Logger.e("on click ll product");
        Intent intent;
        ResFavouriteProductsData favouriteProduct = (ResFavouriteProductsData) view.getTag();
        if (favouriteProduct.getProductType().equals(ServiceConstants.FS_STORE_PRODUCT)) {
            intent = new Intent(mActivity, FsStoreProductDetail.class);
            intent.putExtra("productId", favouriteProduct.getProductID().toString());
        } else {
            intent = new Intent(mActivity, ProductDetailActivity.class);
            intent.putExtra("productId", favouriteProduct.getProductID().toString());
        }
        intent.putExtra("productName", favouriteProduct.getName().toString());
        intent.putExtra("productPosition", position);
        startActivityForResult(intent, AppConstants.RC_PRODUCTS_DETAIL);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_products:
                if (!btnProducts.isSelected()) {
                    setFrom(getActivity(), AppConstants.FROM_PRODUCTS);
                    btnProducts.setSelected(true);
                    btnCoupons.setSelected(false);
                    rvFavouriteProducts.setVisibility(View.VISIBLE);
                    rvFavouriteCoupons.setVisibility(View.GONE);
                    tvToolbarFavouriteCount.setText(String.valueOf(resGetFavouriteProducts.getTotalRecords()));
                    if (resFavouriteProductsData.size() > 0)
                        tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                    else
                        tvToolbarFavouriteCount.setVisibility(View.GONE);
                    if (resFavouriteProductsData.size() == 0) {
                        getFavouriteProducts(etSearch.getText().toString(), 0, mPageIndex, false);
                    }
                }
                break;
            case R.id.btn_coupons:
                if (!btnCoupons.isSelected()) {
                    setFrom(getActivity(), AppConstants.FROM_COUPONS);
                    btnCoupons.setSelected(true);
                    btnProducts.setSelected(false);
                    rvFavouriteCoupons.setVisibility(View.VISIBLE);
                    rvFavouriteProducts.setVisibility(View.GONE);
                    tvToolbarFavouriteCount.setText(String.valueOf(resFavouriteCouponsData.size()));
                    if (resFavouriteCouponsData.size() > 0)
                        tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                    else
                        tvToolbarFavouriteCount.setVisibility(View.GONE);
                    if (resFavouriteCouponsData.size() == 0) {
                        getFavouriteCoupons(etSearch.getText().toString(), 0);

                    }

                }
                break;
            case R.id.iv_cross_search:
                etSearch.setText("");
                ivCrossSearch.setVisibility(View.GONE);
                AppUtilMethods.hideKeyBoard(mActivity);
                break;
        }
    }

    private void setFrom(Activity activity, int from) {
        if (activity instanceof HomeActivity)
            ((HomeActivity) activity).setFromWhere(from);

        if (activity instanceof FavouritesActivity)
            ((FavouritesActivity) activity).setFromWhere(from);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_PRODUCTS_DETAIL && resultCode == Activity.RESULT_OK) {
            int favourite = data.getExtras().getInt("productPositionFavourite");
            String productId = data.getExtras().getString("productId");
            if (favourite == ServiceConstants.UNFAVOURITE) {
                for (int i = 0; i < resFavouriteProductsData.size(); i++) {
                    if (resFavouriteProductsData.get(i).getProductID().equals(productId))
                        resFavouriteProductsData.remove(i);
                }
                for (int j = 0; j < favouriteProductsRecyclerAdapter.favouriteProductsData.size(); j++) {
                    if (favouriteProductsRecyclerAdapter.favouriteProductsData.get(j).getProductID().equals(productId))
                        favouriteProductsRecyclerAdapter.favouriteProductsData.remove(j);
                }

                tvToolbarFavouriteCount.setText(String.valueOf(resGetFavouriteProducts.getTotalRecords()));
                favouriteProductsRecyclerAdapter.notifyDataSetChanged();
                if (resFavouriteProductsData.size() > 0) {
                    tvToolbarFavouriteCount.setVisibility(View.VISIBLE);
                } else {
                    tvToolbarFavouriteCount.setVisibility(View.GONE);
                }
            }
        } else if (requestCode == AppConstants.RC_COUPON_LIST && resultCode == Activity.RESULT_OK) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}




