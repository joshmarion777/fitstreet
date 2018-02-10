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

public class FAQDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_fitstreet_all_about_header)
    CustomTypefaceTextView tvFitstreetAllAboutHeader;
    @BindView(R.id.tv_fitstreet_all_about)
    CustomTypefaceTextView tvFitstreetAllAbout;
    @BindView(R.id.tv_fitstreet_money_header)
    CustomTypefaceTextView tvFitstreetMoneyHeader;
    @BindView(R.id.tv_fitstreet_money)
    CustomTypefaceTextView tvFitstreetMoney;
    @BindView(R.id.tv_fitstreet_reward_points_header)
    CustomTypefaceTextView tvFitstreetRewardPointsHeader;
    @BindView(R.id.tv_fitstreet_reward_points)
    CustomTypefaceTextView tvFitstreetRewardPoints;
    @BindView(R.id.tv_fitstreet_workout_header)
    CustomTypefaceTextView tvFitstreetWorkoutHeader;
    @BindView(R.id.tv_fitstreet_workout)
    CustomTypefaceTextView tvFitstreetWorkout;
    @BindView(R.id.tv_fitstreet_online_shopping_header)
    CustomTypefaceTextView tvFitstreetOnlineShoppingHeader;
    @BindView(R.id.tv_fitstreet_online_shopping)
    CustomTypefaceTextView tvFitstreetOnlineShopping;
    @BindView(R.id.tv_fitstreet_purchase_inform_header)
    CustomTypefaceTextView tvFitstreetPurchaseInformHeader;
    @BindView(R.id.tv_fitstreet_purchase_inform)
    CustomTypefaceTextView tvFitstreetPurchaseInform;
    @BindView(R.id.tv_fitstreet_tracking_header)
    CustomTypefaceTextView tvFitstreetTrackingHeader;
    @BindView(R.id.tv_fitstreet_tracking)
    CustomTypefaceTextView tvFitstreetTracking;
    @BindView(R.id.tv_fitstreet_long_sync_header)
    CustomTypefaceTextView tvFitstreetLongSyncHeader;
    @BindView(R.id.tv_fitstreet_long_sync)
    CustomTypefaceTextView tvFitstreetLongSync;
    @BindView(R.id.tv_fitstreet_redemption_header)
    CustomTypefaceTextView tvFitstreetRedemptionHeader;
    @BindView(R.id.tv_fitstreet_redemption)
    CustomTypefaceTextView tvFitstreetRedemption;
    @BindView(R.id.tv_fitstreet_earnings_header)
    CustomTypefaceTextView tvFitstreetEarningsHeader;
    @BindView(R.id.tv_fitstreet_earnings)
    CustomTypefaceTextView tvFitstreetEarnings;
    @BindView(R.id.tv_fitstreet_exclusive_header)
    CustomTypefaceTextView tvFitstreetExclusiveHeader;
    @BindView(R.id.tv_fitstreet_exclusive)
    CustomTypefaceTextView tvFitstreetExclusive;
    @BindView(R.id.tv_fitstreet_customer_support_header)
    CustomTypefaceTextView tvFitstreetCustomerSupportHeader;
    @BindView(R.id.tv_fitstreet_customer_support)
    CustomTypefaceTextView tvFitstreetCustomerSupport;
    private Boolean mIsShowing = false;
    private int mPrevious = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_detail);
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(getIntent().getStringExtra("titleFaq"));
        tvFitstreetAllAbout.setText(R.string.fitstreet_is_all_about);


    }

    protected void initVariables() {
        ivBack.setOnClickListener(this);
    }

    @Override
    @OnClick({R.id.tv_fitstreet_all_about_header, R.id.tv_fitstreet_all_about, R.id.tv_fitstreet_money_header, R.id.tv_fitstreet_money, R.id.tv_fitstreet_reward_points_header, R.id.tv_fitstreet_reward_points, R.id.tv_fitstreet_workout_header, R.id.tv_fitstreet_workout, R.id.tv_fitstreet_online_shopping_header, R.id.tv_fitstreet_online_shopping, R.id.tv_fitstreet_purchase_inform_header, R.id.tv_fitstreet_purchase_inform, R.id.tv_fitstreet_tracking_header, R.id.tv_fitstreet_tracking, R.id.tv_fitstreet_long_sync_header, R.id.tv_fitstreet_long_sync, R.id.tv_fitstreet_redemption_header, R.id.tv_fitstreet_redemption, R.id.tv_fitstreet_earnings_header, R.id.tv_fitstreet_earnings, R.id.tv_fitstreet_exclusive_header, R.id.tv_fitstreet_exclusive, R.id.tv_fitstreet_customer_support_header, R.id.tv_fitstreet_customer_support})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_fitstreet_all_about_header:
                if (mIsShowing) {
                    if (mPrevious == 1) {
                        tvFitstreetAllAbout.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetAllAbout.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 1;
                    }
                } else {
                    tvFitstreetAllAbout.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 1;
                }
                break;
            case R.id.tv_fitstreet_all_about:
                break;
            case R.id.tv_fitstreet_money_header:
                if (mIsShowing) {
                    if (mPrevious == 2) {
                        tvFitstreetMoney.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetMoney.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 2;
                    }
                } else {
                    tvFitstreetMoney.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 2;
                }
                break;
            case R.id.tv_fitstreet_money:
                break;
            case R.id.tv_fitstreet_reward_points_header:
                if (mIsShowing) {
                    if (mPrevious == 3) {
                        tvFitstreetRewardPoints.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetRewardPoints.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 3;
                    }
                } else {
                    tvFitstreetRewardPoints.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 3;
                }
                break;
            case R.id.tv_fitstreet_reward_points:
                break;
            case R.id.tv_fitstreet_workout_header:
                if (mIsShowing) {
                    if (mPrevious == 4) {
                        tvFitstreetWorkout.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetWorkout.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 4;
                    }
                } else {
                    tvFitstreetWorkout.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 4;
                }
                break;
            case R.id.tv_fitstreet_workout:
                break;
            case R.id.tv_fitstreet_online_shopping_header:
                if (mIsShowing) {
                    if (mPrevious == 5) {
                        tvFitstreetOnlineShopping.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetOnlineShopping.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 5;
                    }
                } else {
                    tvFitstreetOnlineShopping.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 5;
                }
                break;
            case R.id.tv_fitstreet_online_shopping:
                break;
            case R.id.tv_fitstreet_purchase_inform_header:
                if (mIsShowing) {
                    if (mPrevious == 6) {
                        tvFitstreetPurchaseInform.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetPurchaseInform.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 6;
                    }
                } else {
                    tvFitstreetPurchaseInform.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 6;
                }
                break;
            case R.id.tv_fitstreet_purchase_inform:
                break;
            case R.id.tv_fitstreet_tracking_header:
                if (mIsShowing) {
                    if (mPrevious == 7) {
                        tvFitstreetTracking.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetTracking.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 7;
                    }
                } else {
                    tvFitstreetTracking.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 7;
                }
                break;
            case R.id.tv_fitstreet_tracking:
                break;
            case R.id.tv_fitstreet_long_sync_header:
                if (mIsShowing) {
                    if (mPrevious == 8) {
                        tvFitstreetLongSync.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetLongSync.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 8;
                    }
                } else {
                    tvFitstreetLongSync.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 8;
                }
                break;
            case R.id.tv_fitstreet_long_sync:
                break;
            case R.id.tv_fitstreet_redemption_header:
                if (mIsShowing) {
                    if (mPrevious == 9) {
                        tvFitstreetRedemption.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetRedemption.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 9;
                    }
                } else {
                    tvFitstreetRedemption.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 9;
                }
                break;
            case R.id.tv_fitstreet_redemption:
                break;
            case R.id.tv_fitstreet_earnings_header:
                if (mIsShowing) {
                    if (mPrevious == 10) {
                        tvFitstreetEarnings.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetEarnings.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 10;
                    }
                } else {
                    tvFitstreetEarnings.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 10;
                }
                break;
            case R.id.tv_fitstreet_earnings:
                break;
            case R.id.tv_fitstreet_exclusive_header:
                if (mIsShowing) {
                    if (mPrevious == 11) {
                        tvFitstreetExclusive.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetExclusive.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 11;
                    }
                } else {
                    tvFitstreetExclusive.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 11;
                }
                break;
            case R.id.tv_fitstreet_exclusive:
                break;
            case R.id.tv_fitstreet_customer_support_header:
                if (mIsShowing) {
                    if (mPrevious == 12) {
                        tvFitstreetCustomerSupport.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetCustomerSupport.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 12;
                    }
                } else {
                    tvFitstreetCustomerSupport.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 12;
                }
                break;
            case R.id.tv_fitstreet_customer_support:
                break;
        }
    }

    /**
     * Handling visibility of Text-views
     *
     * @param previousView
     */
    private void handleVisibility(int previousView) {
        switch (previousView) {
            case 1:
                tvFitstreetAllAbout.setVisibility(View.GONE);
                break;
            case 2:
                tvFitstreetMoney.setVisibility(View.GONE);
                break;
            case 3:
                tvFitstreetRewardPoints.setVisibility(View.GONE);
                break;
            case 4:
                tvFitstreetWorkout.setVisibility(View.GONE);
                break;
            case 5:
                tvFitstreetOnlineShopping.setVisibility(View.GONE);
                break;
            case 6:
                tvFitstreetPurchaseInform.setVisibility(View.GONE);
                break;
            case 7:
                tvFitstreetTracking.setVisibility(View.GONE);
                break;
            case 8:
                tvFitstreetLongSync.setVisibility(View.GONE);
                break;
            case 9:
                tvFitstreetRedemption.setVisibility(View.GONE);
                break;
            case 10:
                tvFitstreetEarnings.setVisibility(View.GONE);
                break;
            case 11:
                tvFitstreetExclusive.setVisibility(View.GONE);
                break;
            case 12:
                tvFitstreetCustomerSupport.setVisibility(View.GONE);
                break;


        }
    }
}
