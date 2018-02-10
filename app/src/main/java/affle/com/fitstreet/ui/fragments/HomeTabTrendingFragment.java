package affle.com.fitstreet.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.request.ReqCouponsList;
import affle.com.fitstreet.models.request.ReqTrendingCoupons;
import affle.com.fitstreet.models.request.ReqTrendingProducts;
import affle.com.fitstreet.models.response.ResCouponList;
import affle.com.fitstreet.models.response.ResGetCouponList;
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
import affle.com.fitstreet.ui.activities.CouponsDetailActivity;
import affle.com.fitstreet.ui.activities.FsStoreProductDetail;
import affle.com.fitstreet.ui.activities.ProductDetailActivity;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akash on 15/6/16.
 */
public class HomeTabTrendingFragment extends BaseFragment {
    @BindView(R.id.pb_iv_big_row_first)
    ProgressBar pbIvBigRowFirst;
    @BindView(R.id.pb_iv_small_first_row_first)
    ProgressBar pbIvSmallFirstRowFirst;
    @BindView(R.id.pb_iv_small_second_row_first)
    ProgressBar pbIvSmallSecondRowFirst;
    @BindView(R.id.pb_iv_big_row_second)
    ProgressBar pbIvBigRowSecond;
    @BindView(R.id.pb_iv_small_first_row_second)
    ProgressBar pbIvSmallFirstRowSecond;
    @BindView(R.id.pb_iv_small_second_row_second)
    ProgressBar pbIvSmallSecondRowSecond;
    @BindView(R.id.iv_big_row_first)
    ImageView ivBigRowFirst;
    @BindView(R.id.iv_big_row_second)
    ImageView ivBigRowSecond;
    @BindView(R.id.iv_small_first_row_first)
    ImageView ivSmallFirstRowFirst;
    @BindView(R.id.iv_small_first_row_second)
    ImageView ivSmallFirstRowSecond;
    @BindView(R.id.iv_small_second_row_first)
    ImageView ivSmallSecondRowFirst;
    @BindView(R.id.iv_small_second_row_second)
    ImageView ivSmallSecondRowSecond;
    @BindView(R.id.v_iv_small_first_row_first)
    View vIvSmallFirstRowFirst;
    @BindView(R.id.v_iv_big_row_first)
    View vIvBigRowFirst;
    @BindView(R.id.v_iv_small_second_row_first)
    View vIvSmallSecondRowFirst;
    @BindView(R.id.v_iv_big_row_second)
    View vIvBigRowSecond;
    @BindView(R.id.v_iv_small_first_row_second)
    View vIvSmallFirstRowSecond;
    @BindView(R.id.v_iv_small_second_row_second)
    View vIvSmallSecondRowSecond;

    @BindView(R.id.tv_big_row_first)
    affle.com.fitstreet.customviews.CustomTypefaceTextView tvBigRowFirst;
    @BindView(R.id.tv_big_row_second)
    affle.com.fitstreet.customviews.CustomTypefaceTextView tvBigRowSecond;
    @BindView(R.id.tv_small_first_row_first)
    affle.com.fitstreet.customviews.CustomTypefaceTextView tvSmallFirstRowFirst;
    @BindView(R.id.tv_small_first_row_second)
    affle.com.fitstreet.customviews.CustomTypefaceTextView tvSmallFirstRowSecond;
    @BindView(R.id.tv_small_second_row_first)
    affle.com.fitstreet.customviews.CustomTypefaceTextView tvSmallSecondRowFirst;
    @BindView(R.id.tv_small_second_row_second)
    affle.com.fitstreet.customviews.CustomTypefaceTextView tvSmallSecondRowSecond;
    private ResGetTrendingProducts resGetTrendingProducts;
    private List<ResTrendingProductsData> resTrendingProductsData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_tab_recommended, container, false);
    }

    @Override
    protected void initViews() {
        if (NetworkConnection.isNetworkConnected(mActivity)) {
            pbIvSmallFirstRowFirst.setVisibility(View.VISIBLE);
            pbIvSmallSecondRowSecond.setVisibility(View.VISIBLE);
            pbIvSmallFirstRowSecond.setVisibility(View.VISIBLE);
            pbIvBigRowFirst.setVisibility(View.VISIBLE);
            pbIvBigRowSecond.setVisibility(View.VISIBLE);
            pbIvSmallSecondRowFirst.setVisibility(View.VISIBLE);
            getTrendingCouponsAndProductsForHomeTabs();
        } else {
            ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
        }

    }

    /**
     * Fetching Trending coupons and products images for home-tabs
     */
    public void getTrendingCouponsAndProductsForHomeTabs() {
        final IApiClient client = ApiClient.getApiClient();
        ReqTrendingCoupons.FilterType filterType = new ReqTrendingCoupons.FilterType();
        filterType.setPartnerID(null);
        final ReqTrendingCoupons reqTrendingCoupons = new ReqTrendingCoupons();
        reqTrendingCoupons.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqTrendingCoupons.setMethod(MethodFactory.GET_TRENDING_COUPONS.getMethod());
        reqTrendingCoupons.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, mActivity.getString(R.string.txt_skip_user_id)));
        reqTrendingCoupons.setSearch("");
        reqTrendingCoupons.setFilterType(null);


///for trending products
        final ReqTrendingProducts reqTrendingProducts = new ReqTrendingProducts();
        reqTrendingProducts.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqTrendingProducts.setMethod(MethodFactory.GET_TRENDING_PRODUCTS.getMethod());
        reqTrendingProducts.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, mActivity.getString(R.string.txt_skip_user_id)));
        reqTrendingProducts.setSearch("");
        reqTrendingProducts.setFilterType(null);

        Call<ResGetTrendingProducts> resGetTrendingProductsCall = client.getTrendingProducts(reqTrendingProducts);
        resGetTrendingProductsCall.enqueue(new Callback<ResGetTrendingProducts>() {
            @Override
            public void onResponse(Call<ResGetTrendingProducts> call, Response<ResGetTrendingProducts> response) {
                resGetTrendingProducts = response.body();
                if (resGetTrendingProducts != null) {
                    if (resGetTrendingProducts.getSuccess() == ServiceConstants.SUCCESS) {
                        resTrendingProductsData.addAll(resGetTrendingProducts.getTrendingProductData());
                        setTrendingProductImages();
                        //setProductsClickListeners();
                    } else {
                        //ToastUtils.showShortToast(mActivity, resGetTrendingProducts.getErrstr());
                        pbIvSmallFirstRowFirst.setVisibility(View.GONE);
                        pbIvSmallSecondRowSecond.setVisibility(View.GONE);
                        pbIvSmallFirstRowSecond.setVisibility(View.GONE);
                        pbIvBigRowFirst.setVisibility(View.GONE);
                        pbIvBigRowSecond.setVisibility(View.GONE);
                        pbIvSmallSecondRowFirst.setVisibility(View.GONE);
                        ivSmallFirstRowFirst.setImageResource(R.drawable.no_image_icon);
                        ivSmallSecondRowFirst.setImageResource(R.drawable.no_image_icon);
                        ivSmallFirstRowSecond.setImageResource(R.drawable.no_image_icon);
                        ivBigRowFirst.setImageResource(R.drawable.no_image_icon);
                        ivBigRowSecond.setImageResource(R.drawable.no_image_icon);
                        ivSmallSecondRowSecond.setImageResource(R.drawable.no_image_icon);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResGetTrendingProducts> call, Throwable t) {
                ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                pbIvSmallFirstRowFirst.setVisibility(View.GONE);
                pbIvSmallSecondRowSecond.setVisibility(View.GONE);
                pbIvSmallFirstRowSecond.setVisibility(View.GONE);
                pbIvBigRowFirst.setVisibility(View.GONE);
                pbIvBigRowSecond.setVisibility(View.GONE);
                pbIvSmallSecondRowFirst.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Set images in the respective image views
     */
    private void setTrendingProductImages() {
        tvBigRowFirst.setVisibility(View.VISIBLE);
        tvSmallFirstRowFirst.setVisibility(View.VISIBLE);
        tvSmallSecondRowFirst.setVisibility(View.VISIBLE);

        if (resTrendingProductsData.size() >= 1) {
            if (resTrendingProductsData.get(0).getImage() != null) {
                Glide.with(mActivity)
                        .load(resTrendingProductsData.get(0).getImage())
                        .error(R.drawable.no_image_available)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                pbIvBigRowFirst.setVisibility(View.GONE);
                                vIvBigRowFirst.setVisibility(View.VISIBLE);
                                return false;
                            }
                        })
                        .into(ivBigRowFirst);
                tvBigRowFirst.setText(resTrendingProductsData.get(0).getName());
                ivBigRowFirst.setOnClickListener(this);
            }
        } else {
            ivBigRowFirst.setImageResource(R.drawable.no_image_icon);
            pbIvBigRowFirst.setVisibility(View.GONE);
            vIvBigRowFirst.setVisibility(View.VISIBLE);

        }
        if (resTrendingProductsData.size() >= 2) {
            if (resTrendingProductsData.get(1).getImage() != null) {
                Glide.with(mActivity)
                        .load(resTrendingProductsData.get(1).getImage())
                        .error(R.drawable.no_image_available)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                pbIvSmallFirstRowFirst.setVisibility(View.GONE);
                                vIvSmallFirstRowFirst.setVisibility(View.VISIBLE);
                                return false;
                            }
                        })
                        .into(ivSmallFirstRowFirst);
                tvSmallFirstRowFirst.setText(resTrendingProductsData.get(1).getName());
                ivSmallFirstRowFirst.setOnClickListener(this);
            }
        } else {
            ivSmallFirstRowFirst.setImageResource(R.drawable.no_image_icon);
            pbIvSmallFirstRowFirst.setVisibility(View.GONE);
            vIvSmallFirstRowFirst.setVisibility(View.VISIBLE);

        }
        if (resTrendingProductsData.size() >= 3) {
            if (resTrendingProductsData.get(2).getImage() != null) {
                Glide.with(mActivity)
                        .load(resTrendingProductsData.get(2).getImage())
                        .error(R.drawable.no_image_available)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                pbIvSmallSecondRowFirst.setVisibility(View.GONE);
                                vIvSmallSecondRowFirst.setVisibility(View.VISIBLE);
                                return false;
                            }
                        })
                        .into(ivSmallSecondRowFirst);
                tvSmallSecondRowFirst.setText(resTrendingProductsData.get(2).getName());
                ivSmallSecondRowFirst.setOnClickListener(this);
            }
        } else {
            pbIvSmallSecondRowFirst.setVisibility(View.GONE);
            vIvSmallSecondRowFirst.setVisibility(View.VISIBLE);
            ivSmallSecondRowFirst.setImageResource(R.drawable.no_image_icon);

        }
        tvBigRowSecond.setVisibility(View.VISIBLE);
        tvSmallSecondRowSecond.setVisibility(View.VISIBLE);
        tvSmallFirstRowSecond.setVisibility(View.VISIBLE);

        if (resTrendingProductsData.size() >= 4) {
            if (resTrendingProductsData.get(3).getImage() != null) {
                Glide.with(mActivity)
                        .load(resTrendingProductsData.get(3).getImage())
                        .error(R.drawable.no_image_available)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                pbIvBigRowSecond.setVisibility(View.GONE);
                                vIvBigRowSecond.setVisibility(View.VISIBLE);
                                return false;

                            }
                        })
                        .into(ivBigRowSecond);
                tvBigRowSecond.setText(resTrendingProductsData.get(3).getName());
                ivBigRowSecond.setOnClickListener(this);
            }
        } else {
            pbIvBigRowSecond.setVisibility(View.GONE);
            vIvBigRowSecond.setVisibility(View.VISIBLE);
            ivBigRowSecond.setImageResource(R.drawable.no_image_icon);

        }
        if (resTrendingProductsData.size() >= 5) {
            if (resTrendingProductsData.get(4).getImage() != null) {
                Glide.with(mActivity)
                        .load(resTrendingProductsData.get(4).getImage())
                        .error(R.drawable.no_image_available)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                pbIvSmallFirstRowSecond.setVisibility(View.GONE);
                                vIvSmallFirstRowSecond.setVisibility(View.VISIBLE);
                                return false;

                            }
                        })
                        .into(ivSmallFirstRowSecond);
                tvSmallFirstRowSecond.setText(resTrendingProductsData.get(4).getName());
                ivSmallFirstRowSecond.setOnClickListener(this);
            }
        } else {
            pbIvSmallFirstRowSecond.setVisibility(View.GONE);
            vIvSmallFirstRowSecond.setVisibility(View.VISIBLE);
            ivSmallFirstRowSecond.setImageResource(R.drawable.no_image_icon);

        }
        if (resTrendingProductsData.size() >= 6) {
            if (resTrendingProductsData.get(5).getImage() != null) {
                Glide.with(mActivity)
                        .load(resTrendingProductsData.get(5).getImage())
                        .error(R.drawable.no_image_available)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                pbIvSmallSecondRowSecond.setVisibility(View.GONE);
                                vIvSmallSecondRowSecond.setVisibility(View.VISIBLE);
                                return false;

                            }
                        })
                        .into(ivSmallSecondRowSecond);
                tvSmallSecondRowSecond.setText(resTrendingProductsData.get(5).getName());
                ivSmallSecondRowSecond.setOnClickListener(this);
            }
        } else {
            pbIvSmallSecondRowSecond.setVisibility(View.GONE);
            vIvSmallSecondRowSecond.setVisibility(View.VISIBLE);
            ivSmallSecondRowSecond.setImageResource(R.drawable.no_image_icon);

        }
    }

    @Override
    protected void initVariables() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_big_row_first: {
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    openProductDetailScreen(0);
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            }
            case R.id.iv_small_first_row_first: {
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    openProductDetailScreen(1);
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            }
            case R.id.iv_small_second_row_first: {
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    openProductDetailScreen(2);
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            }
            case R.id.iv_big_row_second: {
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    openProductDetailScreen(3);
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            }
            case R.id.iv_small_first_row_second: {
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    openProductDetailScreen(4);
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            }
            case R.id.iv_small_second_row_second: {
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    openProductDetailScreen(5);
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            }

        }
    }

    /**
     * To open detail screen on clicking of individual image in home-tabs
     *
     * @param pos
     */
    private void openProductDetailScreen(int pos) {
        Intent intent;
        if (resTrendingProductsData.get(pos).getProductType().equals(ServiceConstants.FS_STORE_PRODUCT)) {
            intent = new Intent(mActivity, FsStoreProductDetail.class);
            intent.putExtra("productId", resTrendingProductsData.get(pos).getProductID());
        } else {
            intent = new Intent(mActivity, ProductDetailActivity.class);
            intent.putExtra("productId", resTrendingProductsData.get(pos).getProductID());
        }

        intent.putExtra("productName", resTrendingProductsData.get(pos).getName());
        intent.putExtra("productPosition", pos);
        startActivityForResult(intent, AppConstants.RC_PRODUCTS_DETAIL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == AppConstants.RC_COUPON_LIST && resultCode == Activity.RESULT_OK) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
