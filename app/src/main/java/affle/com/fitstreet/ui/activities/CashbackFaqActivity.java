package affle.com.fitstreet.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CashbackFaqActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_fitstreet_redeem_reward_header)
    CustomTypefaceTextView tvFitstreetRedeemRewardHeader;
    @BindView(R.id.tv_fitstreet_redeem_reward)
    CustomTypefaceTextView tvFitstreetRedeemReward;
    @BindView(R.id.tv_fitstreet_transaction_header)
    CustomTypefaceTextView tvFitstreetTransactionHeader;
    @BindView(R.id.tv_fitstreet_transaction)
    CustomTypefaceTextView tvFitstreetTransaction;
    @BindView(R.id.tv_fitstreet_cashback_pending_header)
    CustomTypefaceTextView tvFitstreetCashbackPendingHeader;
    @BindView(R.id.tv_fitstreet_cashback_pending)
    CustomTypefaceTextView tvFitstreetCashbackPending;
    @BindView(R.id.tv_fitstreet_cashback_confirmed_header)
    CustomTypefaceTextView tvFitstreetCashbackConfirmedHeader;
    @BindView(R.id.tv_fitstreet_cashback_confirmed)
    CustomTypefaceTextView tvFitstreetCashbackConfirmed;
    @BindView(R.id.tv_fitstreet_cashback_transaction_header)
    CustomTypefaceTextView tvFitstreetCashbackTransactionHeader;
    @BindView(R.id.tv_fitstreet_cashback_transaction)
    CustomTypefaceTextView tvFitstreetCashbackTransaction;
    @BindView(R.id.tv_fitstreet_receive_cashback_header)
    CustomTypefaceTextView tvFitstreetReceiveCashbackHeader;
    @BindView(R.id.tv_fitstreet_receive_cashback)
    CustomTypefaceTextView tvFitstreetReceiveCashback;
    @BindView(R.id.tv_fitstreet_withdraw_header)
    CustomTypefaceTextView tvFitstreetWithdrawHeader;
    @BindView(R.id.tv_fitstreet_withdraw)
    CustomTypefaceTextView tvFitstreetWithdraw;
    @BindView(R.id.tv_fitstreet_minimum_value_money_header)
    CustomTypefaceTextView tvFitstreetMinimumValueMoneyHeader;
    @BindView(R.id.tv_fitstreet_minimum_value_money)
    CustomTypefaceTextView tvFitstreetMinimumValueMoney;
    @BindView(R.id.tv_fitstreet_request_withdrawal_header)
    CustomTypefaceTextView tvFitstreetRequestWithdrawalHeader;
    @BindView(R.id.tv_fitstreet_request_withdrawal)
    CustomTypefaceTextView tvFitstreetRequestWithdrawal;
    @BindView(R.id.tv_fitstreet_receive_neft_header)
    CustomTypefaceTextView tvFitstreetReceiveNeftHeader;
    @BindView(R.id.tv_fitstreet_receive_neft)
    CustomTypefaceTextView tvFitstreetReceiveNeft;
    @BindView(R.id.tv_fitstreet_cash_in_wallet_header)
    CustomTypefaceTextView tvFitstreetCashInWalletHeader;
    @BindView(R.id.tv_fitstreet_cash_in_wallet)
    CustomTypefaceTextView tvFitstreetCashInWallet;
    private Boolean mIsShowing = false;
    private int mPrevious = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashback_faq);
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(getIntent().getStringExtra("titleFaq"));
    }

    @Override
    protected void initVariables() {
        ivBack.setOnClickListener(this);
    }

    @Override
    @OnClick({R.id.tv_fitstreet_redeem_reward_header, R.id.tv_fitstreet_redeem_reward, R.id.tv_fitstreet_transaction_header, R.id.tv_fitstreet_transaction, R.id.tv_fitstreet_cashback_pending_header, R.id.tv_fitstreet_cashback_pending, R.id.tv_fitstreet_cashback_confirmed_header, R.id.tv_fitstreet_cashback_confirmed, R.id.tv_fitstreet_cashback_transaction_header, R.id.tv_fitstreet_cashback_transaction, R.id.tv_fitstreet_receive_cashback_header, R.id.tv_fitstreet_receive_cashback, R.id.tv_fitstreet_withdraw_header, R.id.tv_fitstreet_withdraw, R.id.tv_fitstreet_minimum_value_money_header, R.id.tv_fitstreet_minimum_value_money, R.id.tv_fitstreet_request_withdrawal_header, R.id.tv_fitstreet_request_withdrawal, R.id.tv_fitstreet_receive_neft_header, R.id.tv_fitstreet_receive_neft, R.id.tv_fitstreet_cash_in_wallet_header, R.id.tv_fitstreet_cash_in_wallet})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_fitstreet_redeem_reward_header:
                if (mIsShowing) {
                    if (mPrevious == 1) {
                        tvFitstreetRedeemReward.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetRedeemReward.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 1;
                    }
                } else {
                    tvFitstreetRedeemReward.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 1;
                }
                break;
            case R.id.tv_fitstreet_redeem_reward:
                break;
            case R.id.tv_fitstreet_transaction_header:
                if (mIsShowing) {
                    if (mPrevious == 2) {
                        tvFitstreetTransaction.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetTransaction.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 2;
                    }
                } else {
                    tvFitstreetTransaction.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 2;
                }
                break;
            case R.id.tv_fitstreet_transaction:
                break;
            case R.id.tv_fitstreet_cashback_pending_header:
                if (mIsShowing) {
                    if (mPrevious == 3) {
                        tvFitstreetCashbackPending.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetCashbackPending.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 3;
                    }
                } else {
                    tvFitstreetCashbackPending.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 3;
                }
                break;
            case R.id.tv_fitstreet_cashback_pending:
                break;
            case R.id.tv_fitstreet_cashback_confirmed_header:
                if (mIsShowing) {
                    if (mPrevious == 4) {
                        tvFitstreetCashbackConfirmed.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetCashbackConfirmed.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 4;
                    }
                } else {
                    tvFitstreetCashbackConfirmed.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 4;
                }
                break;
            case R.id.tv_fitstreet_cashback_confirmed:
                break;
            case R.id.tv_fitstreet_cashback_transaction_header:
                if (mIsShowing) {
                    if (mPrevious == 5) {
                        tvFitstreetCashbackTransaction.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetCashbackTransaction.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 5;
                    }
                } else {
                    tvFitstreetCashbackTransaction.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 5;
                }
                break;
            case R.id.tv_fitstreet_cashback_transaction:
                break;
            case R.id.tv_fitstreet_receive_cashback_header:
                if (mIsShowing) {
                    if (mPrevious == 6) {
                        tvFitstreetReceiveCashback.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetReceiveCashback.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 6;
                    }
                } else {
                    tvFitstreetReceiveCashback.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 6;
                }
                break;
            case R.id.tv_fitstreet_receive_cashback:
                break;
            case R.id.tv_fitstreet_withdraw_header:
                if (mIsShowing) {
                    if (mPrevious == 7) {
                        tvFitstreetWithdraw.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetWithdraw.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 7;
                    }
                } else {
                    tvFitstreetWithdraw.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 7;
                }
                break;
            case R.id.tv_fitstreet_withdraw:
                break;
            case R.id.tv_fitstreet_minimum_value_money_header:
                if (mIsShowing) {
                    if (mPrevious == 8) {
                        tvFitstreetMinimumValueMoney.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetMinimumValueMoney.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 8;
                    }
                } else {
                    tvFitstreetMinimumValueMoney.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 8;
                }
                break;
            case R.id.tv_fitstreet_minimum_value_money:
                break;
            case R.id.tv_fitstreet_request_withdrawal_header:
                if (mIsShowing) {
                    if (mPrevious == 9) {
                        tvFitstreetRequestWithdrawal.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetRequestWithdrawal.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 9;
                    }
                } else {
                    tvFitstreetRequestWithdrawal.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 9;
                }
                break;
            case R.id.tv_fitstreet_request_withdrawal:
                break;
            case R.id.tv_fitstreet_receive_neft_header:
                if (mIsShowing) {
                    if (mPrevious == 10) {
                        tvFitstreetReceiveNeft.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetReceiveNeft.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 10;
                    }
                } else {
                    tvFitstreetReceiveNeft.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 10;
                }
                break;
            case R.id.tv_fitstreet_receive_neft:
                break;
            case R.id.tv_fitstreet_cash_in_wallet_header:
                if (mIsShowing) {
                    if (mPrevious == 11) {
                        tvFitstreetCashInWallet.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetCashInWallet.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 11;
                    }
                } else {
                    tvFitstreetCashInWallet.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 11;
                }
                break;
            case R.id.tv_fitstreet_cash_in_wallet:
                break;

        }
    }

    /**
     * Setting visibility of Text-views
     *
     * @param previousView previous visible view
     */
    private void handleVisibility(int previousView) {
        switch (previousView) {
            case 1:
                tvFitstreetRedeemReward.setVisibility(View.GONE);
                break;
            case 2:
                tvFitstreetTransaction.setVisibility(View.GONE);
                break;
            case 3:
                tvFitstreetCashbackPending.setVisibility(View.GONE);
                break;
            case 4:
                tvFitstreetCashbackConfirmed.setVisibility(View.GONE);
                break;
            case 5:
                tvFitstreetCashbackTransaction.setVisibility(View.GONE);
                break;
            case 6:
                tvFitstreetReceiveCashback.setVisibility(View.GONE);
                break;
            case 7:
                tvFitstreetWithdraw.setVisibility(View.GONE);
                break;
            case 8:
                tvFitstreetMinimumValueMoney.setVisibility(View.GONE);
                break;
            case 9:
                tvFitstreetRequestWithdrawal.setVisibility(View.GONE);
                break;
            case 10:
                tvFitstreetReceiveNeft.setVisibility(View.GONE);
                break;
            case 11:
                tvFitstreetCashInWallet.setVisibility(View.GONE);
                break;
        }
    }
}
