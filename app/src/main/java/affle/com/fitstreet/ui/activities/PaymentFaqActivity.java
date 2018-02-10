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

public class PaymentFaqActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_fitstreet_payment_policy_header)
    CustomTypefaceTextView tvFitstreetPaymentPolicyHeader;
    @BindView(R.id.tv_fitstreet_payment_policy)
    CustomTypefaceTextView tvFitstreetPaymentPolicy;
    @BindView(R.id.tv_fitstreet_payment_methods_header)
    CustomTypefaceTextView tvFitstreetPaymentMethodsHeader;
    @BindView(R.id.tv_fitstreet_payment_methods)
    CustomTypefaceTextView tvFitstreetPaymentMethods;
    @BindView(R.id.tv_fitstreet_payment_fails_header)
    CustomTypefaceTextView tvFitstreetPaymentFailsHeader;
    @BindView(R.id.tv_fitstreet_payment_fails)
    CustomTypefaceTextView tvFitstreetPaymentFails;
    private Boolean mIsShowing = false;
    private int mPrevious = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_faq);
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
    @OnClick({R.id.tv_fitstreet_payment_policy_header, R.id.tv_fitstreet_payment_policy, R.id.tv_fitstreet_payment_methods_header, R.id.tv_fitstreet_payment_methods, R.id.tv_fitstreet_payment_fails_header, R.id.tv_fitstreet_payment_fails})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_fitstreet_payment_policy_header:
                if (mIsShowing) {
                    if (mPrevious == 1) {
                        tvFitstreetPaymentPolicy.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetPaymentPolicy.setVisibility(View.VISIBLE);
                        mPrevious = 1;
                        mIsShowing = true;
                    }
                } else {
                    tvFitstreetPaymentPolicy.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 1;
                }
                break;
            case R.id.tv_fitstreet_payment_policy:
                break;
            case R.id.tv_fitstreet_payment_methods_header:
                if (mIsShowing) {
                    if (mPrevious == 2) {
                        tvFitstreetPaymentMethods.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetPaymentMethods.setVisibility(View.VISIBLE);
                        mPrevious = 2;
                        mIsShowing = true;
                    }
                } else {
                    tvFitstreetPaymentMethods.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 2;
                }
                break;
            case R.id.tv_fitstreet_payment_methods:
                break;
            case R.id.tv_fitstreet_payment_fails_header:
                if (mIsShowing) {
                    if (mPrevious == 3) {
                        tvFitstreetPaymentFails.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetPaymentFails.setVisibility(View.VISIBLE);
                        mPrevious = 3;
                        mIsShowing = true;
                    }
                } else {
                    tvFitstreetPaymentFails.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 3;
                }
                break;
            case R.id.tv_fitstreet_payment_fails:
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
                tvFitstreetPaymentPolicy.setVisibility(View.GONE);
                break;
            case 2:
                tvFitstreetPaymentMethods.setVisibility(View.GONE);
                break;
            case 3:
                tvFitstreetPaymentFails.setVisibility(View.GONE);
                break;
        }
    }

}
