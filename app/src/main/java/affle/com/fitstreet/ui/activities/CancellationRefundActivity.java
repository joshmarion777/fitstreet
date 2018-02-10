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

public class CancellationRefundActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_fitstreet_return_policy_header)
    CustomTypefaceTextView tvFitstreetReturnPolicyHeader;
    @BindView(R.id.tv_fitstreet_return_policy)
    CustomTypefaceTextView tvFitstreetReturnPolicy;
    @BindView(R.id.tv_fitstreet_return_my_product_header)
    CustomTypefaceTextView tvFitstreetReturnMyProductHeader;
    @BindView(R.id.tv_fitstreet_return_my_product)
    CustomTypefaceTextView tvFitstreetReturnMyProduct;
    @BindView(R.id.tv_fitstreet_cancel_order_header)
    CustomTypefaceTextView tvFitstreetCancelOrderHeader;
    @BindView(R.id.tv_fitstreet_cancel_order)
    CustomTypefaceTextView tvFitstreetCancelOrder;
    @BindView(R.id.tv_fitstreet_terms_and_conditions_header)
    CustomTypefaceTextView tvFitstreetTermsAndConditionsHeader;
    @BindView(R.id.tv_fitstreet_terms_and_conditions)
    CustomTypefaceTextView tvFitstreetTermsAndConditions;
    @BindView(R.id.tv_fitstreet_exchanges_allowed_header)
    CustomTypefaceTextView tvFitstreetExchangesAllowedHeader;
    @BindView(R.id.tv_fitstreet_exchanges_allowed)
    CustomTypefaceTextView tvFitstreetExchangesAllowed;
    private boolean mIsShowing = false;
    private int mPreviousSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation_refund);
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(getIntent().getStringExtra("titleFaq"));
    }

    @Override
    protected void initVariables() {
        //setting listeners
        ivBack.setOnClickListener(this);
        tvFitstreetCancelOrder.setOnClickListener(this);
        tvFitstreetCancelOrderHeader.setOnClickListener(this);
        tvFitstreetExchangesAllowed.setOnClickListener(this);
        tvFitstreetExchangesAllowedHeader.setOnClickListener(this);
        tvFitstreetReturnMyProduct.setOnClickListener(this);
        tvFitstreetReturnMyProductHeader.setOnClickListener(this);
        tvFitstreetReturnPolicy.setOnClickListener(this);
        tvFitstreetReturnPolicyHeader.setOnClickListener(this);
        tvFitstreetTermsAndConditions.setOnClickListener(this);
        tvFitstreetTermsAndConditionsHeader.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_fitstreet_return_policy_header:
                if (mIsShowing) {
                    if (mPreviousSelected == 1) {
                        tvFitstreetReturnPolicy.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPreviousSelected = 0;
                    } else {
                        handleVisibility(mPreviousSelected);
                        tvFitstreetReturnPolicy.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPreviousSelected = 1;
                    }
                } else {
                    tvFitstreetReturnPolicy.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPreviousSelected = 1;
                }
                break;
            case R.id.tv_fitstreet_return_policy:
                break;
            case R.id.tv_fitstreet_return_my_product_header:
                if (mIsShowing) {
                    if (mPreviousSelected == 2) {
                        tvFitstreetReturnMyProduct.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPreviousSelected = 0;
                    } else {
                        handleVisibility(mPreviousSelected);
                        tvFitstreetReturnMyProduct.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPreviousSelected = 2;
                    }
                } else {
                    tvFitstreetReturnMyProduct.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPreviousSelected = 2;
                }
                break;
            case R.id.tv_fitstreet_return_my_product:
                break;
            case R.id.tv_fitstreet_cancel_order_header:
                if (mIsShowing) {
                    if (mPreviousSelected == 3) {
                        tvFitstreetCancelOrder.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPreviousSelected = 0;
                    } else {
                        handleVisibility(mPreviousSelected);
                        tvFitstreetCancelOrder.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPreviousSelected = 3;
                    }
                } else {
                    tvFitstreetCancelOrder.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPreviousSelected = 3;
                }
                break;
            case R.id.tv_fitstreet_cancel_order:
                break;
            case R.id.tv_fitstreet_terms_and_conditions_header:
                if (mIsShowing) {
                    if (mPreviousSelected == 4) {
                        tvFitstreetTermsAndConditions.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPreviousSelected = 0;
                    } else {
                        handleVisibility(mPreviousSelected);
                        tvFitstreetTermsAndConditions.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPreviousSelected = 4;
                    }
                } else {
                    tvFitstreetTermsAndConditions.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPreviousSelected = 4;
                }
                break;
            case R.id.tv_fitstreet_terms_and_conditions:
                break;
            case R.id.tv_fitstreet_exchanges_allowed_header:
                if (mIsShowing) {
                    if (mPreviousSelected == 5) {
                        tvFitstreetExchangesAllowed.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPreviousSelected = 0;
                    } else {
                        handleVisibility(mPreviousSelected);
                        tvFitstreetExchangesAllowed.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPreviousSelected = 5;
                    }
                } else {
                    tvFitstreetExchangesAllowed.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPreviousSelected = 5;
                }
                break;
            case R.id.tv_fitstreet_exchanges_allowed:
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
                tvFitstreetReturnPolicy.setVisibility(View.GONE);
                break;
            case 2:
                tvFitstreetReturnMyProduct.setVisibility(View.GONE);
                break;
            case 3:
                tvFitstreetCancelOrder.setVisibility(View.GONE);
                break;
            case 4:
                tvFitstreetTermsAndConditions.setVisibility(View.GONE);
                break;
            case 5:
                tvFitstreetExchangesAllowed.setVisibility(View.GONE);
                break;
        }
    }

}
