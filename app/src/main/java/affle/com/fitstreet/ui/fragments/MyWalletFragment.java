package affle.com.fitstreet.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceButton;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.request.ReqMyWallet;
import affle.com.fitstreet.models.response.ResMyWallet;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.activities.AppTutorialActivity;
import affle.com.fitstreet.ui.activities.CancellationDetailActivity;
import affle.com.fitstreet.ui.activities.CashbackHistoryActivity;
import affle.com.fitstreet.ui.activities.HomeActivity;
import affle.com.fitstreet.ui.activities.PrizeMoneyDetailsActivity;
import affle.com.fitstreet.ui.activities.SendToBankActivity;
import affle.com.fitstreet.ui.activities.WalletHistoryActivity;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akash on 3/10/16.
 */
public class MyWalletFragment extends BaseFragment {
    @BindView(R.id.tv_wallet_balance)
    CustomTypefaceTextView tvWalletBalance;
    @BindView(R.id.tv_cashback)
    CustomTypefaceTextView tvCashback;
    @BindView(R.id.tv_cancellation)
    CustomTypefaceTextView tvCancellation;
    @BindView(R.id.tv_prize_money)
    CustomTypefaceTextView tvPrizeMoney;
    @BindView(R.id.btn_send_to_bank)
    CustomTypefaceButton btnSendToBank;
    @BindView(R.id.ll_cashback_history)
    LinearLayout llCashbackHistory;
    @BindView(R.id.ll_cancellation_history)
    LinearLayout llCancellationHistory;
    @BindView(R.id.ll_prize_money_history)
    LinearLayout llPrizeMoneyHistory;
    @BindView(R.id.btn_wallet_history)
    CustomTypefaceButton btnWalletHistory;
    private ResMyWallet mResMyWallet;

    @Override
    protected void initViews() {
        ((HomeActivity) mActivity).setActionBarTitle(R.string.txt_my_wallet);
    }

    @Override
    protected void initVariables() {
        llCashbackHistory.setOnClickListener(this);
        llCancellationHistory.setOnClickListener(this);
        btnSendToBank.setOnClickListener(this);
        btnWalletHistory.setOnClickListener(this);
        llPrizeMoneyHistory.setOnClickListener(this);
        getMyWallet();

    }

    /**
     * Getting wallet details from server
     */
    private void getMyWallet() {
        AppDialog.showProgressDialog(mActivity, true);
        IApiClient client = ApiClient.getApiClient();
        ReqMyWallet reqWallet = new ReqMyWallet();
        reqWallet.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqWallet.setMethod(MethodFactory.MY_WALLET.getMethod());
        reqWallet.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        Call<ResMyWallet> call = client.myWallet(reqWallet);
        call.enqueue(new Callback<ResMyWallet>() {
            @Override
            public void onResponse(Call<ResMyWallet> call, Response<ResMyWallet> response) {
                AppDialog.showProgressDialog(mActivity, false);
                if (isAdded()) {
                    mResMyWallet = response.body();
                    if (mResMyWallet != null) {
                        if (mResMyWallet.getSuccess() == ServiceConstants.SUCCESS) {
//                            if (mResMyWallet.getRefundAmt().isEmpty())
//                                tvCancellation.setText(mActivity.getString(R.string.rs) + "0");
//                            else
//                                tvCancellation.setText(mActivity.getString(R.string.rs) + mResMyWallet.getRefundAmt());
//                            if (mResMyWallet.getRefundAmt().isEmpty())
//                                tvCashback.setText(mActivity.getString(R.string.rs) + "0");
//                            else
//                                tvCashback.setText(mActivity.getString(R.string.rs) + mResMyWallet.getCashBackAmt());
//                            if (mResMyWallet.getRefundAmt().isEmpty())
//                                tvPrizeMoney.setText(mActivity.getString(R.string.rs) + "0");
//                            else
//                                tvPrizeMoney.setText(mActivity.getString(R.string.rs) + mResMyWallet.getPrizeAmt());
                            if (mResMyWallet.getTotalAmt().isEmpty()) {
                                tvWalletBalance.setText(mActivity.getString(R.string.rs) + "0");
                            } else {
                                tvWalletBalance.setText(mActivity.getString(R.string.rs) + mResMyWallet.getTotalAmt());
                            }

                        } else {
                            ToastUtils.showShortToast(mActivity, mResMyWallet.getErrstr());
                        }
                    } else {
                        ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResMyWallet> call, Throwable t) {
                AppDialog.showProgressDialog(mActivity, false);
                ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_MY_WALLET_FRAGMENT && resultCode == mActivity.RESULT_OK) {
//            float amountSendToBank = Float.parseFloat(data.getExtras().getString("amountSendToBank"));
//            tvWalletBalance.setText(mActivity.getString(R.string.rs)+String.valueOf(Float.parseFloat(mResMyWallet.getTotalAmt()) - amountSendToBank));
//            super.onActivityResult(requestCode, resultCode, data);
            ((HomeActivity) mActivity).showWalletFragment();
            ((HomeActivity) mActivity).unCheckNavigationSelectedItem();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_cancellation_history:
                intent = new Intent(mActivity, CancellationDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_cashback_history:
                intent = new Intent(mActivity, CashbackHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_prize_money_history:
                intent = new Intent(mActivity, PrizeMoneyDetailsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_send_to_bank:
                intent = new Intent(mActivity, SendToBankActivity.class);
                if (mResMyWallet != null) {
                    //if (!mResMyWallet.getTotalAmt().isEmpty()) {
                    //if (Float.parseFloat(mResMyWallet.getTotalAmt()) > 100) {
                    intent.putExtra("walletAmount", mResMyWallet.getTotalAmt());
                    startActivityForResult(intent, AppConstants.RC_MY_WALLET_FRAGMENT);
//                        } else {
//                            ToastUtils.showShortToast(mActivity, getString(R.string.txt_check_min_balnace_in_wallet));
//                        }
                    // }
                } else {
                    ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                }
                break;
            case R.id.btn_wallet_history:
                intent = new Intent(mActivity, WalletHistoryActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_wallet, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
