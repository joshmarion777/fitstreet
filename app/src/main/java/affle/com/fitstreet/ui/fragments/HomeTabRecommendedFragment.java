package affle.com.fitstreet.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.request.ReqProductsList;
import affle.com.fitstreet.models.response.ResGetProductsList;
import affle.com.fitstreet.models.response.ResProductsListData;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
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
public class HomeTabRecommendedFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.ll_tabs_container)
    LinearLayout llTabsContainer;
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
    ResGetProductsList resGetProductsList;
    List<ResProductsListData> productsListData = new ArrayList<>();

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
            if (productsListData.size() > 0) {
                setImages();
            } else {
                getProductsListForHomeTabs();
            }
        } else {
            ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
        }
    }

    /**
     * Getting 6 product images for home-tabs
     */
    public void getProductsListForHomeTabs() {
        IApiClient client = ApiClient.getApiClient();
        ReqProductsList reqProductsList = new ReqProductsList();
        reqProductsList.setSearch("");
        reqProductsList.setProCatID("");
        reqProductsList.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, mActivity.getString(R.string.txt_skip_user_id)));
        reqProductsList.setMethod(MethodFactory.GET_PRODUCTS_LIST.getMethod());
        reqProductsList.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqProductsList.setFilterType(null);
        Call<ResGetProductsList> resGetProductsListCall = client.getProductsList(reqProductsList);
        resGetProductsListCall.enqueue(new Callback<ResGetProductsList>() {
            @Override
            public void onResponse(Call<ResGetProductsList> call, Response<ResGetProductsList> response) {
                llTabsContainer.setVisibility(View.VISIBLE);
                resGetProductsList = response.body();
                if (resGetProductsList != null) {
                    if (resGetProductsList.getSuccess() == ServiceConstants.SUCCESS) {
                        productsListData.addAll(resGetProductsList.getCatProductData());
                        //setClickListeners();
                        setImages();

                    } else {
                        ToastUtils.showShortToast(mActivity, resGetProductsList.getErrstr());
                        pbIvSmallFirstRowFirst.setVisibility(View.GONE);
                        pbIvSmallSecondRowSecond.setVisibility(View.GONE);
                        pbIvSmallFirstRowSecond.setVisibility(View.GONE);
                        pbIvBigRowFirst.setVisibility(View.GONE);
                        pbIvBigRowSecond.setVisibility(View.GONE);
                        pbIvSmallSecondRowFirst.setVisibility(View.GONE);
                    }
                } else {
                    ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                    pbIvSmallFirstRowFirst.setVisibility(View.GONE);
                    pbIvSmallSecondRowSecond.setVisibility(View.GONE);
                    pbIvSmallFirstRowSecond.setVisibility(View.GONE);
                    pbIvBigRowFirst.setVisibility(View.GONE);
                    pbIvBigRowSecond.setVisibility(View.GONE);
                    pbIvSmallSecondRowFirst.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResGetProductsList> call, Throwable t) {
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

    @Override
    protected void initVariables() {
    }

    /**
     * Set images in the respective image views
     */
    public void setImages() {
        tvBigRowFirst.setVisibility(View.VISIBLE);
        tvSmallFirstRowFirst.setVisibility(View.VISIBLE);
        tvSmallSecondRowFirst.setVisibility(View.VISIBLE);
        tvBigRowSecond.setVisibility(View.VISIBLE);
        tvSmallFirstRowSecond.setVisibility(View.VISIBLE);
        tvSmallSecondRowSecond.setVisibility(View.VISIBLE);


        if (productsListData.size() >= 1) {
            if (productsListData.get(0).getImage() != null) {
                Glide.with(mActivity)
                        .load(productsListData.get(0).getImage())
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
                tvBigRowFirst.setText(productsListData.get(0).getName());
                ivBigRowFirst.setOnClickListener(this);
            }
        } else {
            ivBigRowFirst.setImageResource(R.drawable.no_image_icon);
            pbIvBigRowFirst.setVisibility(View.GONE);
            vIvBigRowFirst.setVisibility(View.VISIBLE);

        }
        if (productsListData.size() >= 2) {
            if (productsListData.get(1).getImage() != null) {
                Glide.with(mActivity)
                        .load(productsListData.get(1).getImage())
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
                tvSmallFirstRowFirst.setText(productsListData.get(1).getName());
                ivSmallFirstRowFirst.setOnClickListener(this);
            } else {
                ivSmallFirstRowFirst.setImageResource(R.drawable.no_image_icon);
                pbIvSmallFirstRowFirst.setVisibility(View.GONE);
                vIvSmallFirstRowFirst.setVisibility(View.VISIBLE);

            }
        }
        if (productsListData.size() >= 3) {
            if (productsListData.get(2).getImage() != null) {
                Glide.with(mActivity)
                        .load(productsListData.get(2).getImage())
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
                tvSmallSecondRowFirst.setText(productsListData.get(2).getName());
                ivSmallSecondRowFirst.setOnClickListener(this);
            } else {
                ivSmallSecondRowFirst.setImageResource(R.drawable.no_image_icon);
                pbIvSmallSecondRowFirst.setVisibility(View.GONE);
                vIvSmallSecondRowFirst.setVisibility(View.VISIBLE);

            }
        }

        if (productsListData.size() >= 4) {
            if (productsListData.get(3).getImage() != null) {
                Glide.with(mActivity)
                        .load(productsListData.get(3).getImage())
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
                tvBigRowSecond.setText(productsListData.get(3).getName());
                ivBigRowSecond.setOnClickListener(this);
            } else {
                ivBigRowSecond.setImageResource(R.drawable.no_image_icon);
                pbIvBigRowSecond.setVisibility(View.GONE);
                vIvBigRowSecond.setVisibility(View.VISIBLE);

            }
        }
        if (productsListData.size() >= 5) {
            if (productsListData.get(4).getImage() != null) {
                Glide.with(mActivity)
                        .load(productsListData.get(4).getImage())
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
                tvSmallFirstRowSecond.setText(productsListData.get(4).getName());
                ivSmallFirstRowSecond.setOnClickListener(this);
            } else {
                ivSmallFirstRowSecond.setImageResource(R.drawable.no_image_icon);
                pbIvSmallFirstRowSecond.setVisibility(View.GONE);
                vIvSmallFirstRowSecond.setVisibility(View.VISIBLE);

            }
        }
        if (productsListData.size() >= 6) {
            if (productsListData.get(5).getImage() != null) {
                Glide.with(mActivity)
                        .load(productsListData.get(5).getImage())
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
                tvSmallSecondRowSecond.setText(productsListData.get(5).getName());
                ivSmallSecondRowSecond.setOnClickListener(this);
            } else {
                ivSmallSecondRowSecond.setImageResource(R.drawable.no_image_icon);
                pbIvSmallSecondRowSecond.setVisibility(View.GONE);
                vIvSmallSecondRowSecond.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_big_row_first: {
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    openDetailScreen(0);
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            }
            case R.id.iv_small_first_row_first: {
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    openDetailScreen(1);
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            }
            case R.id.iv_small_second_row_first: {
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    openDetailScreen(2);
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            }
            case R.id.iv_big_row_second: {
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    openDetailScreen(3);
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            }
            case R.id.iv_small_first_row_second: {
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    openDetailScreen(4);
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            }
            case R.id.iv_small_second_row_second: {
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    openDetailScreen(5);
                } else {
                    AppDialog.showLoginAlertDialog(mActivity);
                }
                break;
            }

        }

    }

    /**
     * Open Detail page of the product on clicking of individual image
     *
     * @param pos position of the product
     */
    private void openDetailScreen(int pos) {
        Intent intent = new Intent(mActivity, ProductDetailActivity.class);
        intent.putExtra("productId", productsListData.get(pos).getProductID().toString());
        intent.putExtra("productName", productsListData.get(pos).getName().toString());
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
