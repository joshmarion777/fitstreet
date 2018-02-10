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

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.request.ReqCouponsList;
import affle.com.fitstreet.models.response.ResCouponList;
import affle.com.fitstreet.models.response.ResGetCouponList;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.activities.CouponsDetailActivity;
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
public class HomeTabCouponsFragment extends BaseFragment {
    @BindView(R.id.ll_tabs_container)
    LinearLayout llTabsContainer;
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
    private ResGetCouponList resGetCouponList;
    private List<ResCouponList> resCouponLists = new ArrayList<>();


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
        } else {
            ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
        }
    }

    /**
     * get 6 coupons for home-tabs from server
     */
    public void getCouponsForHomeTabs() {
        IApiClient client = ApiClient.getApiClient();
        final ReqCouponsList reqCouponsList = new ReqCouponsList();
        ReqCouponsList.FilterType filterType = new ReqCouponsList.FilterType();
        filterType.setPartnerID(null);
        reqCouponsList.setCatID("");
        reqCouponsList.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqCouponsList.setMethod(MethodFactory.GET_ALL_COUPONS_LIST.getMethod());
        reqCouponsList.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqCouponsList.setFilterType(null);
        Call<ResGetCouponList> resGetCouponListCall = client.getCouponsList(reqCouponsList);
        resGetCouponListCall.enqueue(new Callback<ResGetCouponList>() {
            @Override
            public void onResponse(Call<ResGetCouponList> call, Response<ResGetCouponList> response) {
//                AppDialog.showProgressDialog(mActivity,false);
                //pbTabsProgress.setVisibility(View.GONE);
                llTabsContainer.setVisibility(View.VISIBLE);
                resGetCouponList = response.body();
                if (resGetCouponList != null) {
                    if (resGetCouponList.getSuccess() == ServiceConstants.SUCCESS) {
                        resCouponLists.clear();
                        resCouponLists.addAll(resGetCouponList.getCouponsList());
                        //setCouponImages();
                        setClickListeners();

                    } else {
                        ToastUtils.showShortToast(mActivity, resGetCouponList.getErrstr());
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
            public void onFailure(Call<ResGetCouponList> call, Throwable t) {
                ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
            }
        });
    }

    /**
     * Setting coupons images into their respective image-views
     */
    private void setCouponImages() {
        Glide.with(mActivity)
                .load(resCouponLists.get(0).getImage())
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
        Glide.with(mActivity)
                .load(resCouponLists.get(1).getImage())
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
        Glide.with(mActivity)
                .load(resCouponLists.get(2).getImage())
                .error(R.drawable.no_image_available)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pbIvSmallFirstRowSecond.setVisibility(View.GONE);
                        vIvSmallSecondRowFirst.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(ivSmallSecondRowFirst);
        Glide.with(mActivity)
                .load(resCouponLists.get(3).getImage())
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
        Glide.with(mActivity)
                .load(resCouponLists.get(4).getImage())
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
        Glide.with(mActivity)
                .load(resCouponLists.get(5).getImage())
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
                .error(R.drawable.no_image_available)
                .into(ivSmallSecondRowSecond);
        tvBigRowFirst.setVisibility(View.VISIBLE);
        tvSmallFirstRowFirst.setVisibility(View.VISIBLE);
        tvSmallSecondRowFirst.setVisibility(View.VISIBLE);
        tvBigRowSecond.setVisibility(View.VISIBLE);
        tvSmallFirstRowSecond.setVisibility(View.VISIBLE);
        tvSmallSecondRowSecond.setVisibility(View.VISIBLE);
        tvBigRowFirst.setText(resCouponLists.get(0).getName());
        tvSmallFirstRowFirst.setText(resCouponLists.get(1).getName());
        tvSmallSecondRowFirst.setText(resCouponLists.get(2).getName());
        tvBigRowSecond.setText(resCouponLists.get(3).getName());
        tvSmallFirstRowSecond.setText(resCouponLists.get(4).getName());
        tvSmallSecondRowSecond.setText(resCouponLists.get(5).getName());

    }

    private void setClickListeners() {
        ivBigRowFirst.setOnClickListener(this);
        ivSmallFirstRowFirst.setOnClickListener(this);
        ivSmallSecondRowFirst.setOnClickListener(this);
        ivBigRowSecond.setOnClickListener(this);
        ivSmallFirstRowSecond.setOnClickListener(this);
        ivSmallSecondRowSecond.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_big_row_first: {
                openDetailScreen(0);
                break;
            }
            case R.id.iv_small_first_row_first: {
                openDetailScreen(1);
                break;
            }
            case R.id.iv_small_second_row_first: {
                openDetailScreen(2);
                break;
            }
            case R.id.iv_big_row_second: {
                openDetailScreen(3);
                break;
            }
            case R.id.iv_small_first_row_second: {
                openDetailScreen(4);
                break;
            }
            case R.id.iv_small_second_row_second: {
                openDetailScreen(5);
                break;
            }

        }

    }

    /**
     * To open detail screen on clicking of individual image in home-tabs
     *
     * @param pos
     */
    private void openDetailScreen(int pos) {
        Intent intent = new Intent(mActivity, CouponsDetailActivity.class);
        intent.putExtra(AppConstants.COUPON_ID, resCouponLists.get(pos).getCoupanID());
        intent.putExtra(AppConstants.FAVOURITE, resCouponLists.get(pos).getFavourite() + "");
        this.startActivityForResult(intent, AppConstants.RC_COUPON_LIST);
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
