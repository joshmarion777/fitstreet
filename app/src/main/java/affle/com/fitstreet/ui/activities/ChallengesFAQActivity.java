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

public class ChallengesFAQActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_fitstreet_organized_contests_header)
    CustomTypefaceTextView tvFitstreetOrganizedContestsHeader;
    @BindView(R.id.tv_fitstreet_organized_contests)
    CustomTypefaceTextView tvFitstreetOrganizedContests;
    @BindView(R.id.tv_fitstreet_participate_contests_header)
    CustomTypefaceTextView tvFitstreetParticipateContestsHeader;
    @BindView(R.id.tv_fitstreet_participate_contests)
    CustomTypefaceTextView tvFitstreetParticipateContests;
    @BindView(R.id.tv_fitstreet_contests_prize_money_header)
    CustomTypefaceTextView tvFitstreetContestsPrizeMoneyHeader;
    @BindView(R.id.tv_fitstreet_contests_prize_money)
    CustomTypefaceTextView tvFitstreetContestsPrizeMoney;
    private Boolean mIsShowing = false;
    private int mPrevious = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_faq);
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
    @OnClick({R.id.tv_fitstreet_organized_contests_header, R.id.tv_fitstreet_organized_contests, R.id.tv_fitstreet_participate_contests_header, R.id.tv_fitstreet_participate_contests, R.id.tv_fitstreet_contests_prize_money_header, R.id.tv_fitstreet_contests_prize_money})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_fitstreet_organized_contests_header:
                if (mIsShowing) {
                    if (mPrevious == 1) {
                        tvFitstreetOrganizedContests.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetOrganizedContests.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 1;
                    }
                } else {
                    tvFitstreetOrganizedContests.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 1;
                }
                break;
            case R.id.tv_fitstreet_organized_contests:
                break;
            case R.id.tv_fitstreet_participate_contests_header:
                if (mIsShowing) {
                    if (mPrevious == 2) {
                        tvFitstreetParticipateContests.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetParticipateContests.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 2;
                    }
                } else {
                    tvFitstreetParticipateContests.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 2;
                }
                break;
            case R.id.tv_fitstreet_participate_contests:
                break;
            case R.id.tv_fitstreet_contests_prize_money_header:
                if (mIsShowing) {
                    if (mPrevious == 3) {
                        tvFitstreetContestsPrizeMoney.setVisibility(View.GONE);
                        mIsShowing = false;
                        mPrevious = 0;
                    } else {
                        handleVisibility(mPrevious);
                        tvFitstreetContestsPrizeMoney.setVisibility(View.VISIBLE);
                        mIsShowing = true;
                        mPrevious = 3;
                    }
                } else {
                    tvFitstreetContestsPrizeMoney.setVisibility(View.VISIBLE);
                    mIsShowing = true;
                    mPrevious = 3;
                }
                break;

            case R.id.tv_fitstreet_contests_prize_money:
                break;
        }
    }

    /**
     * Handling visibility of TextViews
     *
     * @param previousView previous visible view
     */
    private void handleVisibility(int previousView) {
        switch (previousView) {
            case 1:
                tvFitstreetOrganizedContests.setVisibility(View.GONE);
                break;
            case 2:
                tvFitstreetParticipateContests.setVisibility(View.GONE);
                break;
            case 3:
                tvFitstreetContestsPrizeMoney.setVisibility(View.GONE);
                break;
        }
    }
}
