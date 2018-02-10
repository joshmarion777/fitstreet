package affle.com.fitstreet.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.CashbackHistoryAdapterCompleted;
import affle.com.fitstreet.adapters.CashbackHistoryAdapterPending;
import affle.com.fitstreet.customviews.CustomTypefaceButton;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.request.ReqCashbackHistory;
import affle.com.fitstreet.models.response.ResCashBackHistory;
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

public class CashbackHistoryActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    CustomTypefaceTextView tvTitle;
    @BindView(R.id.btn_completed_cashback)
    CustomTypefaceButton btnCompletedCashback;
    @BindView(R.id.btn_pending_cashback)
    CustomTypefaceButton btnPendingCashback;
    @BindView(R.id.tv_total_cashback)
    CustomTypefaceTextView tvTotalCashback;
    @BindView(R.id.rv_cashback_history_completed)
    RecyclerView rvCashbackHistoryCompleted;
    @BindView(R.id.rv_cashback_history_pending)
    RecyclerView rvCashbackHistoryPending;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_no_cancellation)
    ImageView ivNoCancellation;
    @BindView(R.id.tv_no_cancellation)
    CustomTypefaceTextView tvNoCancellation;
    @BindView(R.id.ll_completed_pending_buttons)
    LinearLayout llCompletedPendingButtons;
    @BindView(R.id.view_line)
    View viewLine;
    private CashbackHistoryAdapterCompleted mCashbackHistoryAdapterCompleted;
    private CashbackHistoryAdapterPending mCashbackHistoryAdapterPending;
    private ResCashBackHistory mResCashBackHistoryCompleted;
    private ResCashBackHistory mResCashBackHistoryPending;
    private ArrayList<ResCashBackHistory.CashBackDatum> mCashBackCompletedList = new ArrayList<>();
    private ArrayList<ResCashBackHistory.CashBackDatum> mCashBackPendingList = new ArrayList<>();
    private boolean fromMyAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashback_history);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getBoolean("fromMyAccount")) {
                fromMyAccount = true;
                llCompletedPendingButtons.setVisibility(View.GONE);
                tvTotalCashback.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
            } else {
                fromMyAccount = false;
            }
        }
        super.initData();

    }

    @Override
    protected void initViews() {
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getBoolean("fromMyAccount")) {
                tvTitle.setText(R.string.txt_redeem_history);
            }
        } else {
            tvTitle.setText(R.string.txt_cashback_history);

        }
        btnCompletedCashback.setSelected(true);
        rvCashbackHistoryCompleted.setVisibility(View.VISIBLE);
        rvCashbackHistoryPending.setVisibility(View.GONE);
        getCashbackHistory(ServiceConstants.CASHBACK_COMPLETED);
    }

    @Override
    protected void initVariables() {
        btnPendingCashback.setOnClickListener(this);
        btnCompletedCashback.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rvCashbackHistoryPending.setLayoutManager(new LinearLayoutManager(this));
        mCashbackHistoryAdapterPending = new CashbackHistoryAdapterPending(this, mCashBackPendingList);
        rvCashbackHistoryPending.setAdapter(mCashbackHistoryAdapterPending);
        rvCashbackHistoryCompleted.setLayoutManager(new LinearLayoutManager(this));
        mCashbackHistoryAdapterCompleted = new CashbackHistoryAdapterCompleted(this, mCashBackCompletedList);
        rvCashbackHistoryCompleted.setAdapter(mCashbackHistoryAdapterCompleted);

    }

    /**
     * Getting cashback history data from API
     *
     * @param cashbackStatus
     */
    private void getCashbackHistory(final String cashbackStatus) {
        AppDialog.showProgressDialog(this, true);
        IApiClient client = ApiClient.getApiClient();
        ReqCashbackHistory reqCashbackHistory = new ReqCashbackHistory();
        reqCashbackHistory.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqCashbackHistory.setMethod(MethodFactory.GET_CASHBACK_HISTORY.getMethod());
        if (fromMyAccount) {
            reqCashbackHistory.setCashbackStatus("");
        } else {
            reqCashbackHistory.setCashbackStatus(cashbackStatus);
        }
        reqCashbackHistory.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        Call<ResCashBackHistory> call = client.getCashbackHistory(reqCashbackHistory);
        call.enqueue(new Callback<ResCashBackHistory>() {
                         @Override
                         public void onResponse(Call<ResCashBackHistory> call, Response<ResCashBackHistory> response) {
                             AppDialog.showProgressDialog(CashbackHistoryActivity.this, false);
                             if (cashbackStatus.equals(ServiceConstants.CASHBACK_COMPLETED)) {
                                 mResCashBackHistoryCompleted = response.body();
                                 if (mResCashBackHistoryCompleted != null) {
                                     if (mResCashBackHistoryCompleted.getSuccess() == ServiceConstants.SUCCESS) {
                                         rvCashbackHistoryCompleted.setVisibility(View.VISIBLE);
                                         tvNoCancellation.setVisibility(View.GONE);
                                         ivNoCancellation.setVisibility(View.GONE);
                                         if (!fromMyAccount) {
                                             viewLine.setVisibility(View.VISIBLE);
                                             tvTotalCashback.setVisibility(View.VISIBLE);
                                         }
                                         mCashBackCompletedList.addAll(mResCashBackHistoryCompleted.getCashBackData());
                                         tvTotalCashback.setText(getString(R.string.txt_total_cashback) + String.valueOf(calculateTotalAmount(mCashBackCompletedList)));
                                         mCashbackHistoryAdapterCompleted.notifyDataSetChanged();

                                     } else {
                                         if (fromMyAccount) {
                                             tvNoCancellation.setText(R.string.txt_no_redeem_history);
                                         } else
                                             tvNoCancellation.setText(R.string.you_got_no_cashback);
                                         tvNoCancellation.setVisibility(View.VISIBLE);
                                         ivNoCancellation.setVisibility(View.VISIBLE);

                                         //ToastUtils.showShortToast(CashbackHistoryActivity.this, mResCashBackHistoryCompleted.getErrstr());
                                     }
                                 } else {
                                     ToastUtils.showShortToast(CashbackHistoryActivity.this, R.string.err_network_connection);
                                 }
                             } else {
                                 mResCashBackHistoryPending = response.body();
                                 if (mResCashBackHistoryPending != null) {
                                     if (mResCashBackHistoryPending.getSuccess() == ServiceConstants.SUCCESS) {
                                         rvCashbackHistoryPending.setVisibility(View.VISIBLE);
                                         tvNoCancellation.setVisibility(View.GONE);
                                         ivNoCancellation.setVisibility(View.GONE);
                                         if (!fromMyAccount) {
                                             tvTotalCashback.setVisibility(View.VISIBLE);
                                             viewLine.setVisibility(View.VISIBLE);
                                         }
                                         mCashBackPendingList.addAll(mResCashBackHistoryPending.getCashBackData());
                                         tvTotalCashback.setText(getString(R.string.txt_total_cashback) + String.valueOf(calculateTotalAmount(mCashBackPendingList)));
                                         mCashbackHistoryAdapterPending.notifyDataSetChanged();
                                     } else {
                                         tvNoCancellation.setVisibility(View.VISIBLE);
                                         ivNoCancellation.setVisibility(View.VISIBLE);
                                         tvNoCancellation.setText(R.string.txt_no_pending_cashback);
                                         //ToastUtils.showShortToast(CashbackHistoryActivity.this, mResCashBackHistoryPending.getErrstr());
                                     }
                                 } else {
                                     ToastUtils.showShortToast(CashbackHistoryActivity.this, R.string.err_network_connection);
                                 }
                             }
                         }

                         @Override
                         public void onFailure(Call<ResCashBackHistory> call, Throwable t) {
                             AppDialog.showProgressDialog(CashbackHistoryActivity.this, false);
                             ToastUtils.showShortToast(CashbackHistoryActivity.this, R.string.err_network_connection);
                         }
                     }

        );

    }

    /**
     * Calculating total cashback amount from the cashback list
     *
     * @param cashBackList
     * @return
     */
    private float calculateTotalAmount(ArrayList<ResCashBackHistory.CashBackDatum> cashBackList) {
        float totalCashback = 0;
        for (int i = 0; i < cashBackList.size(); i++) {
            totalCashback = totalCashback + Float.parseFloat(cashBackList.get(i).getAmount());
        }
        return totalCashback;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_completed_cashback:
                btnCompletedCashback.setSelected(true);
                btnPendingCashback.setSelected(false);
                rvCashbackHistoryCompleted.setVisibility(View.VISIBLE);
                rvCashbackHistoryPending.setVisibility(View.GONE);
                tvTotalCashback.setText("Total Cashback : " + calculateTotalAmount(mCashBackCompletedList));
                if (mCashBackCompletedList.isEmpty())
                    getCashbackHistory(ServiceConstants.CASHBACK_COMPLETED);
                else if (mCashBackCompletedList.size() > 0) {
                    ivNoCancellation.setVisibility(View.GONE);
                    tvNoCancellation.setVisibility(View.GONE);

                }
                break;
            case R.id.btn_pending_cashback:
                btnCompletedCashback.setSelected(false);
                btnPendingCashback.setSelected(true);
                rvCashbackHistoryCompleted.setVisibility(View.GONE);
                rvCashbackHistoryPending.setVisibility(View.VISIBLE);
                tvTotalCashback.setText("Total Cashback : " + calculateTotalAmount(mCashBackPendingList));
                if (mCashBackPendingList.isEmpty())
                    getCashbackHistory(ServiceConstants.CASHBACK_PENDING);
                else if (mCashBackPendingList.size() > 0) {
                    ivNoCancellation.setVisibility(View.GONE);
                    tvNoCancellation.setVisibility(View.GONE);

                }
                break;
        }
    }
}
