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

public class ShippingAndOrderFAQActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_fitstreet_shipping_policy_header)
    CustomTypefaceTextView tvFitstreetShippingPolicyHeader;
    @BindView(R.id.tv_fitstreet_shipping_policy)
    CustomTypefaceTextView tvFitstreetShippingPolicy;
    @BindView(R.id.tv_fitstreet_time_period_header)
    CustomTypefaceTextView tvFitstreetTimePeriodHeader;
    @BindView(R.id.tv_fitstreet_time_period)
    CustomTypefaceTextView tvFitstreetTimePeriod;
    @BindView(R.id.tv_fitstreet_track_order_header)
    CustomTypefaceTextView tvFitstreetTrackOrderHeader;
    @BindView(R.id.tv_fitstreet_track_order)
    CustomTypefaceTextView tvFitstreetTrackOrder;
    @BindView(R.id.tv_fitstreet_shipping_charges_header)
    CustomTypefaceTextView tvFitstreetShippingChargesHeader;
    @BindView(R.id.tv_fitstreet_shipping_charges)
    CustomTypefaceTextView tvFitstreetShippingCharges;
    private Boolean mIsShowing = false;
    private int mPrevious = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_and_order_faq);
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
    @OnClick({R.id.tv_fitstreet_shipping_policy_header, R.id.tv_fitstreet_shipping_policy, R.id.tv_fitstreet_time_period_header, R.id.tv_fitstreet_time_period, R.id.tv_fitstreet_track_order_header, R.id.tv_fitstreet_track_order, R.id.tv_fitstreet_shipping_charges_header, R.id.tv_fitstreet_shipping_charges})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_fitstreet_shipping_policy_header:
                if (mIsShowing) {
                    if (mPrevious == 1) {
                        tvFitstreetShippingPolicy.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetShippingPolicy.setVisibility(View.VISIBLE);
                        mPrevious = 1;
                        mIsShowing = true;
                    }
                } else {
                    tvFitstreetShippingPolicy.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 1;
                }
                break;
            case R.id.tv_fitstreet_shipping_policy:
                break;
            case R.id.tv_fitstreet_time_period_header:
                if (mIsShowing) {
                    if (mPrevious == 2) {
                        tvFitstreetTimePeriod.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetTimePeriod.setVisibility(View.VISIBLE);
                        mPrevious = 2;
                        mIsShowing = true;
                    }
                } else {
                    tvFitstreetTimePeriod.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 2;
                }
                break;
            case R.id.tv_fitstreet_time_period:
                break;
            case R.id.tv_fitstreet_track_order_header:
                if (mIsShowing) {
                    if (mPrevious == 3) {
                        tvFitstreetTrackOrder.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetTrackOrder.setVisibility(View.VISIBLE);
                        mPrevious = 3;
                        mIsShowing = true;
                    }
                } else {
                    tvFitstreetTrackOrder.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 3;
                }
                break;
            case R.id.tv_fitstreet_track_order:
                break;
            case R.id.tv_fitstreet_shipping_charges_header:
                if (mIsShowing) {
                    if (mPrevious == 4) {
                        tvFitstreetShippingCharges.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetShippingCharges.setVisibility(View.VISIBLE);
                        mPrevious = 4;
                        mIsShowing = true;
                    }
                } else {
                    tvFitstreetShippingCharges.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 4;
                }
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
                tvFitstreetShippingPolicy.setVisibility(View.GONE);
                break;
            case 2:
                tvFitstreetTimePeriod.setVisibility(View.GONE);
                break;
            case 3:
                tvFitstreetTrackOrder.setVisibility(View.GONE);
                break;
            case 4:
                tvFitstreetShippingCharges.setVisibility(View.GONE);
                break;
        }
    }


}
