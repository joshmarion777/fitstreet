package affle.com.fitstreet.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FAQActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_toolbar_filter)
    ImageView ivToolbarFilter;
    @BindView(R.id.iv_toolbar_filter_active)
    ImageView ivToolbarFilterActive;
    @BindView(R.id.rl_toolbar_filter)
    RelativeLayout rlToolbarFilter;
    @BindView(R.id.iv_toolbar_favourite)
    ImageView ivToolbarFavourite;
    @BindView(R.id.tv_toolbar_favourite_count)
    CustomTypefaceTextView tvToolbarFavouriteCount;
    @BindView(R.id.rl_toolbar_favourite)
    RelativeLayout rlToolbarFavourite;
    @BindView(R.id.tv_option_general)
    CustomTypefaceTextView tvOptionGeneral;
    @BindView(R.id.tv_option_cashback)
    CustomTypefaceTextView tvOptionCashback;
    @BindView(R.id.tv_option_challenges)
    CustomTypefaceTextView tvOptionChallenges;
    @BindView(R.id.tv_option_shipping_order)
    CustomTypefaceTextView tvOptionShippingOrder;
    @BindView(R.id.tv_option_cancellation)
    CustomTypefaceTextView tvOptionCancellation;
    @BindView(R.id.tv_option_payment)
    CustomTypefaceTextView tvOptionPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_faq);
    }

    @Override
    protected void initVariables() {
        ivBack.setOnClickListener(this);
        tvOptionCashback.setOnClickListener(this);
        tvOptionShippingOrder.setOnClickListener(this);
        tvOptionPayment.setOnClickListener(this);
        tvOptionChallenges.setOnClickListener(this);
        tvOptionCancellation.setOnClickListener(this);
        tvOptionGeneral.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_option_general:
                intent = new Intent(this, FAQDetailActivity.class);
                intent.putExtra("titleFaq", tvOptionGeneral.getText().toString() + " FAQ");
                startActivity(intent);
                break;
            case R.id.tv_option_cancellation:
                intent = new Intent(this, CancellationRefundActivity.class);
                intent.putExtra("titleFaq", tvOptionCancellation.getText().toString() + " FAQ");
                startActivity(intent);
                break;
            case R.id.tv_option_cashback:
                intent = new Intent(this, CashbackFaqActivity.class);
                intent.putExtra("titleFaq", tvOptionCashback.getText().toString() + " FAQ");
                startActivity(intent);
                break;
            case R.id.tv_option_challenges:
                intent = new Intent(this, ChallengesFAQActivity.class);
                intent.putExtra("titleFaq", tvOptionChallenges.getText().toString() + " FAQ");
                startActivity(intent);
                break;
            case R.id.tv_option_payment:
                intent = new Intent(this, PaymentFaqActivity.class);
                intent.putExtra("titleFaq", tvOptionPayment.getText().toString() + " FAQ");
                startActivity(intent);
                break;
            case R.id.tv_option_shipping_order:
                intent = new Intent(this, ShippingAndOrderFAQActivity.class);
                intent.putExtra("titleFaq", tvOptionShippingOrder.getText().toString() + " FAQ");
                startActivity(intent);
                break;
        }
    }
}
