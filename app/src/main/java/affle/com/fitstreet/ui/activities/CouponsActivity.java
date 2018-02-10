package affle.com.fitstreet.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.CouponsRecyclerAdapter;
import affle.com.fitstreet.customviews.CustomTypefaceEditText;
import affle.com.fitstreet.models.request.ReqCoupons;
import affle.com.fitstreet.models.response.ResCouponData;
import affle.com.fitstreet.models.response.ResGetCouponData;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.GridSpacingItemDecoration;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rv_coupons)
    RecyclerView rvCoupons;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    CustomTypefaceEditText etSearch;
    private ProgressDialog mProgressDialog;
    private GridLayoutManager mGridLayoutManager;
    private List<ResCouponData> mResCouponData;
    private CouponsRecyclerAdapter mCouponsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_title_coupons);
        mGridLayoutManager = new GridLayoutManager(CouponsActivity.this, 2);
        rvCoupons.setLayoutManager(mGridLayoutManager);
        // rvCoupons.addItemDecoration(new SimpleDividerItemDecorationCoupons(this));

        mResCouponData = new ArrayList<>();
        mCouponsRecyclerAdapter = new CouponsRecyclerAdapter(CouponsActivity.this, mResCouponData);
        rvCoupons.setAdapter(mCouponsRecyclerAdapter);
        rvCoupons.addItemDecoration(new GridSpacingItemDecoration(3, 5, true));

        ivBack.setOnClickListener(this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCouponsRecyclerAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    @Override
    protected void initVariables() {
        if (NetworkConnection.isNetworkConnected(this)) {
            getCoupons();
        } else {
            ToastUtils.showShortToast(this, R.string.err_network_connection);
        }
    }

    /**
     * This method is show to hide the progress dialog
     *
     * @return void
     */
    private void showProgressDialog() {
        mProgressDialog = AppDialog.showProgressDialog(this);
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
     * getting coupons from server
     */
    private void getCoupons() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        ReqCoupons reqCoupons = new ReqCoupons();
        reqCoupons.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqCoupons.setMethod(MethodFactory.GET_ALL_CATEGORY.getMethod());
        reqCoupons.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        Call<ResGetCouponData> resGetCouponDataCall = client.getCoupons(reqCoupons);
        resGetCouponDataCall.enqueue(new Callback<ResGetCouponData>() {
            @Override
            public void onResponse(Call<ResGetCouponData> call, Response<ResGetCouponData> response) {
                dismissProgressDialog();
                ResGetCouponData resGetCouponData = response.body();
                if (resGetCouponData != null) {
                    if (resGetCouponData.getSuccess() == ServiceConstants.SUCCESS) {
                        mResCouponData.addAll(resGetCouponData.getCoupanCatData());
                        mCouponsRecyclerAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showShortToast(CouponsActivity.this, resGetCouponData.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(CouponsActivity.this, R.string.err_network_connection);
                }

            }

            @Override
            public void onFailure(Call<ResGetCouponData> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(CouponsActivity.this, R.string.err_network_connection);
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
        }
    }
}
