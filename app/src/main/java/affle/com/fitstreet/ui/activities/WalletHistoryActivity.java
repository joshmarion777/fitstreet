package affle.com.fitstreet.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.WalletHistoryPaidAdapter;
import affle.com.fitstreet.adapters.WalletHistoryReceivedAdapter;
import affle.com.fitstreet.customviews.CustomTypefaceButton;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.request.ReqWalletHistory;
import affle.com.fitstreet.models.response.ResWalletHistoryPaid;
import affle.com.fitstreet.models.response.ResWalletHistoryReceived;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletHistoryActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_wallet_received)
    CustomTypefaceButton btnWalletReceived;
    @BindView(R.id.btn_wallet_paid)
    CustomTypefaceButton btnWalletPaid;
    @BindView(R.id.rv_wallet_history_paid)
    RecyclerView rvWalletHistoryPaid;
    @BindView(R.id.rv_wallet_history_received)
    RecyclerView rvWalletHistoryReceived;
    @BindView(R.id.tv_title)
    CustomTypefaceTextView tvTitle;
    @BindView(R.id.iv_no_cashback)
    ImageView ivNoCashback;
    @BindView(R.id.tv_no_cashback)
    CustomTypefaceTextView tvNoCashback;
    private WalletHistoryReceivedAdapter mWalletHistoryReceivedAdapter;
    private WalletHistoryPaidAdapter mWalletHistoryPaidAdapter;
    ArrayList<ResWalletHistoryPaid.WalletDatum> mWalletHistoryPaidList = new ArrayList<>();
    ArrayList<ResWalletHistoryReceived.WalletDatum> mWalletHistoryReceivedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_history);
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_wallet_history);
        btnWalletReceived.setSelected(true);
        rvWalletHistoryReceived.setLayoutManager(new LinearLayoutManager(this));
        rvWalletHistoryPaid.setLayoutManager(new LinearLayoutManager(this));
        rvWalletHistoryReceived.setVisibility(View.VISIBLE);
        mWalletHistoryReceivedAdapter = new WalletHistoryReceivedAdapter(this, mWalletHistoryReceivedList);
        mWalletHistoryPaidAdapter = new WalletHistoryPaidAdapter(this, mWalletHistoryPaidList);
        rvWalletHistoryPaid.setAdapter(mWalletHistoryPaidAdapter);
        rvWalletHistoryReceived.setAdapter(mWalletHistoryReceivedAdapter);

    }

    /**
     * Getting wallet history from server
     *
     * @param walletStatus Paid or Received
     */
    private void getWalletHistory(final String walletStatus) {
        AppDialog.showProgressDialog(this, true);
        final IApiClient client = ApiClient.getApiClient();
        final ReqWalletHistory reqWalletHistory = new ReqWalletHistory();
        reqWalletHistory.setMethod(MethodFactory.GET_WALLET_HISTORY.getMethod());
        reqWalletHistory.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, PreferenceKeys.KEY_SERVICE_KEY));
        reqWalletHistory.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqWalletHistory.setWalletStatus(walletStatus);
        if (walletStatus.equals(ServiceConstants.WALLET_RECEIVED)) {
            Call<ResWalletHistoryReceived> call = client.getWalletHistoryReceived(reqWalletHistory);
            call.enqueue(new Callback<ResWalletHistoryReceived>() {
                @Override
                public void onResponse(Call<ResWalletHistoryReceived> call, Response<ResWalletHistoryReceived> response) {
                    AppDialog.showProgressDialog(WalletHistoryActivity.this, false);
                    ResWalletHistoryReceived resWalletHistoryReceived = response.body();
                    if (resWalletHistoryReceived != null) {
                        if (resWalletHistoryReceived.getSuccess() == ServiceConstants.SUCCESS) {
                            mWalletHistoryReceivedList.clear();
                            ivNoCashback.setVisibility(View.GONE);
                            tvNoCashback.setVisibility(View.GONE);
                            mWalletHistoryReceivedList.addAll(resWalletHistoryReceived.getWalletData());
                            mWalletHistoryReceivedAdapter.notifyDataSetChanged();
                        } else {
                            ivNoCashback.setImageResource(R.drawable.nothing_received);
                            ivNoCashback.setVisibility(View.VISIBLE);
                            tvNoCashback.setText(R.string.txt_not_received_amount);
                            tvNoCashback.setVisibility(View.VISIBLE);
                            //ToastUtils.showShortToast(WalletHistoryActivity.this, resWalletHistoryReceived.getErrstr());
                        }
                    } else {
                        ToastUtils.showShortToast(WalletHistoryActivity.this, R.string.err_network_connection);
                    }

                }

                @Override
                public void onFailure(Call<ResWalletHistoryReceived> call, Throwable t) {
                    AppDialog.showProgressDialog(WalletHistoryActivity.this, false);
                    ToastUtils.showShortToast(WalletHistoryActivity.this, getString(R.string.err_network_connection));
                }
            });
        } else if (walletStatus.equals(ServiceConstants.WALLET_PAID)) {
            Call<ResWalletHistoryPaid> call = client.getWalletHistoryPaid(reqWalletHistory);

            call.enqueue(new Callback<ResWalletHistoryPaid>() {
                @Override
                public void onResponse(Call<ResWalletHistoryPaid> call, Response<ResWalletHistoryPaid> response) {
                    AppDialog.showProgressDialog(WalletHistoryActivity.this, false);
                    ResWalletHistoryPaid resWalletHistoryPaid = response.body();
                    if (resWalletHistoryPaid != null) {
                        if (resWalletHistoryPaid.getSuccess() == ServiceConstants.SUCCESS) {
                            mWalletHistoryPaidList.clear();
                            ivNoCashback.setVisibility(View.GONE);
                            tvNoCashback.setVisibility(View.GONE);
                            mWalletHistoryPaidList.addAll(resWalletHistoryPaid.getWalletData());
                            mWalletHistoryPaidAdapter.notifyDataSetChanged();
                        } else {
                            ivNoCashback.setVisibility(View.VISIBLE);
                            ivNoCashback.setImageResource(R.drawable.nothing_paid);
                            tvNoCashback.setVisibility(View.VISIBLE);
                            tvNoCashback.setText(R.string.txt_not_paid_amount);
                            //ToastUtils.showShortToast(WalletHistoryActivity.this, resWalletHistoryPaid.getErrstr());
                        }
                    } else {
                        ToastUtils.showShortToast(WalletHistoryActivity.this, R.string.err_network_connection);
                    }

                }

                @Override
                public void onFailure(Call<ResWalletHistoryPaid> call, Throwable t) {
                    AppDialog.showProgressDialog(WalletHistoryActivity.this, false);
                    ToastUtils.showShortToast(WalletHistoryActivity.this, R.string.err_network_connection);
                }
            });
        }


    }

    @Override
    protected void initVariables() {
        ivBack.setOnClickListener(this);
        getWalletHistory(ServiceConstants.WALLET_RECEIVED);
        rvWalletHistoryReceived.setVisibility(View.VISIBLE);
        btnWalletReceived.setOnClickListener(this);
        btnWalletPaid.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_wallet_received:
                btnWalletPaid.setSelected(false);
                btnWalletReceived.setSelected(true);
                rvWalletHistoryReceived.setVisibility(View.VISIBLE);
                rvWalletHistoryPaid.setVisibility(View.GONE);
                if (mWalletHistoryReceivedList.isEmpty()) {

                    getWalletHistory(ServiceConstants.WALLET_RECEIVED);

                } else {
                    ivNoCashback.setVisibility(View.GONE);
                    tvNoCashback.setVisibility(View.GONE);
                }

                break;
            case R.id.btn_wallet_paid:
                rvWalletHistoryPaid.setVisibility(View.VISIBLE);
                rvWalletHistoryReceived.setVisibility(View.GONE);
                btnWalletPaid.setSelected(true);
                btnWalletReceived.setSelected(false);
                if (mWalletHistoryPaidList.isEmpty())
                    getWalletHistory(ServiceConstants.WALLET_PAID);
                else {
                    ivNoCashback.setVisibility(View.GONE);
                    tvNoCashback.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
