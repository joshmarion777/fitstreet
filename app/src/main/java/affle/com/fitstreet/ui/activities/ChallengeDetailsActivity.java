package affle.com.fitstreet.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.LeaderboardAdapter;
import affle.com.fitstreet.customviews.DividerItemDecorationLeaderboard;
import affle.com.fitstreet.interfaces.IViewType;
import affle.com.fitstreet.models.request.ReqGetContestDetails;
import affle.com.fitstreet.models.request.ReqJoinChallenge;
import affle.com.fitstreet.models.request.ReqLeaveChallenge;
import affle.com.fitstreet.models.response.ResGetContestDetails;
import affle.com.fitstreet.models.response.ResLeaveContest;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.AppSharedPreference;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeDetailsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_display_image)
    ImageView ivDisplayImage;
    @BindView(R.id.btn_join)
    Button btnJoin;
    @BindView(R.id.btn_detail)
    Button btnDetail;
    @BindView(R.id.btn_leave_challenge)
    Button btnLeaveChallenge;
    @BindView(R.id.btn_leaderbord)
    Button btnLeaderboard;
    @BindView(R.id.ll_info_container)
    LinearLayout llInfoContainer;
    @BindView(R.id.ll_info_leaderboard_container)
    LinearLayout llInfoLeaderboardContainer;
    @BindView(R.id.ll_distance_calories)
    LinearLayout llDistanceCalories;
    @BindView(R.id.tv_challenge_title)
    TextView tvChallengeTittle;
    @BindView(R.id.tv_challenge_description)
    TextView tvChallengeDescription;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_total_distance)
    TextView tvTotalDistance;
    @BindView(R.id.tv_calories)
    TextView tvCalories;
    @BindView(R.id.tv_rank_participant)
    TextView tvRankParticipant;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.tv_additional_info)
    TextView tvAdditionalInfo;
    @BindView(R.id.rv_leaderboard_list)
    RecyclerView rvLeaderboardList;
    @BindView(R.id.tv_rank_participant_tittle)
    TextView tvRankParticipantTittle;
    @BindView(R.id.rl_toolbar_mycart)
    RelativeLayout rlToolbarMyCart;
    private String mContestId;
    private ProgressDialog mProgressDialog;
    private ResGetContestDetails mResGetContestDetails;
    private boolean mIsAlreadyJoined = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_details);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            Intent intent = getIntent();
            mContestId = intent.getStringExtra(AppConstants.TAG_CONTEST_ID);
            String data = getIntent().getStringExtra(AppConstants.TAG_CONTEST_VIEW_TYPE);
            if (data != null) {
                if (intent.getStringExtra(AppConstants.TAG_CONTEST_VIEW_TYPE).equals(IViewType.VIEW_TYPE_MY_CONTEST + "")) {
                    mIsAlreadyJoined = true;
                } else
                    mIsAlreadyJoined = false;
            }
        }
        super.initData();
    }

    /**
     * Getting Contest detail from server
     */
    public void getContestDetails() {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        final ReqGetContestDetails reqGetContestDetails = new ReqGetContestDetails();
        reqGetContestDetails.setContestID(mContestId);
        reqGetContestDetails.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqGetContestDetails.setMethod(MethodFactory.GET_CONTEST_DETAILS.getMethod());
        reqGetContestDetails.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        Call<ResGetContestDetails> call = client.getContestDetails(reqGetContestDetails);
        call.enqueue(new Callback<ResGetContestDetails>() {
            @Override
            public void onResponse(Call<ResGetContestDetails> call, Response<ResGetContestDetails> response) {
                dismissProgressDialog();
                ResGetContestDetails resGetContestDetails = response.body();
                if (resGetContestDetails != null) {
                    if (resGetContestDetails.getSuccess() == ServiceConstants.SUCCESS) {
                        setData(ChallengeDetailsActivity.this.mResGetContestDetails = resGetContestDetails, mIsAlreadyJoined);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResGetContestDetails> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(ChallengeDetailsActivity.this, R.string.err_network_connection);
            }
        });

    }

    /**
     * Setting detail data into Text-views
     *
     * @param resLeaveContest
     * @param isAlreadyJoined
     */
    private void setData(ResLeaveContest resLeaveContest, boolean isAlreadyJoined) {
        ResLeaveContest.CurrentContestData contestData = resLeaveContest.getCurrentContestData();
        tvCalories.setText("Calories: " + contestData.getCalories());
        tvDistance.setText("Distance: " + contestData.getDistance() + " " + mAppSharedPreference.getString(PreferenceKeys.KEY_DISTANCE_UNIT, " km"));
        tvTotalDistance.setText(contestData.getTotalDistance() + "");
        tvLeft.setText(String.valueOf(AppUtilMethods.calculateDays(contestData.getEndDate())) + getResources().getQuantityString(R.plurals.txt_days_left, (int) AppUtilMethods.calculateDays(contestData.getEndDate())));
        //tvLeft.setText(AppUtilMethods.calculateDays(contestData.getEndDate()) + " Days");
        if (AppUtilMethods.calculateDays(contestData.getEndDate()) >= 1)
            tvLeft.setText(String.valueOf(AppUtilMethods.calculateDays(contestData.getEndDate())) + getResources().getQuantityString(R.plurals.txt_days_left, (int) AppUtilMethods.calculateDays(contestData.getEndDate())));
        else if (AppUtilMethods.calculateDays(contestData.getEndDate()) == 0) {
            tvLeft.setText(R.string.txt_last_day);
        } else if (AppUtilMethods.calculateDays(contestData.getEndDate()) < 1) {
            tvLeft.setText(R.string.txt_ended);
        }
        tvChallengeTittle.setText(contestData.getName());
        tvChallengeDescription.setText(contestData.getTagText());
        tvOverview.setText(contestData.getDescription());
        tvAdditionalInfo.setText(contestData.getAddInfo());
        List<ResLeaveContest.LeaderBoard> leaderBoardList = resLeaveContest.getLeaderBoard();
        rvLeaderboardList.setAdapter(new LeaderboardAdapter(this, leaderBoardList, null));

        if (isAlreadyJoined) {
            tvRankParticipantTittle.setText(R.string.txt_rank);
            tvRankParticipant.setText(contestData.getRank() + " of " + contestData.getParticpants());
            clickJoin(btnJoin);
        } else {
            tvRankParticipantTittle.setText(R.string.txt_participants);
            tvRankParticipant.setText(contestData.getParticpants());
        }

        //setting display image
        AppUtilMethods.setImageShowingLoader(this, ivDisplayImage, mResGetContestDetails.getContestData().getImage());
    }

    /**
     * Setting detail data into Text-views
     *
     * @param resGetContestDetails
     * @param isAlreadyJoined
     */
    private void setData(ResGetContestDetails resGetContestDetails, boolean isAlreadyJoined) {
        ResGetContestDetails.ContestData contestData = resGetContestDetails.getContestData();
        tvCalories.setText("Calories: " + contestData.getCalories());
        tvDistance.setText("Distance: " + contestData.getDistance() + " " + mAppSharedPreference.getString(PreferenceKeys.KEY_DISTANCE_UNIT, " km"));
        tvTotalDistance.setText(contestData.getTotalDistance() + "");
        tvLeft.setText(String.valueOf(AppUtilMethods.calculateDays(contestData.getEndDate())) + getResources().getQuantityString(R.plurals.txt_days_left, (int) AppUtilMethods.calculateDays(contestData.getEndDate())));
        //tvLeft.setText(AppUtilMethods.calculateDays(contestData.getEndDate()) + " Days");

        if (AppUtilMethods.calculateDays(contestData.getEndDate()) >= 1)
            tvLeft.setText(String.valueOf(AppUtilMethods.calculateDays(contestData.getEndDate())) + getResources().getQuantityString(R.plurals.txt_days_left, (int) AppUtilMethods.calculateDays(contestData.getEndDate())));
        else if (AppUtilMethods.calculateDays(contestData.getEndDate()) == 0) {
            tvLeft.setText(R.string.txt_last_day);
        } else if (AppUtilMethods.calculateDays(contestData.getEndDate()) < 1) {
            tvLeft.setText(R.string.txt_ended);
        }
        tvChallengeTittle.setText(contestData.getName());
        tvChallengeDescription.setText(contestData.getTagText());
        tvOverview.setText(contestData.getDescription());
        tvAdditionalInfo.setText(contestData.getAddInfo());
        List<ResGetContestDetails.LeaderBoard> leaderBoardList = resGetContestDetails.getLeaderBoard();
        rvLeaderboardList.setAdapter(new LeaderboardAdapter(this, null, leaderBoardList));

        if (isAlreadyJoined) {
            tvRankParticipantTittle.setText(R.string.txt_rank);
            tvRankParticipant.setText(contestData.getRank() + " of " + contestData.getParticpants());
            clickJoin(btnJoin);
        } else {
            tvRankParticipantTittle.setText(R.string.txt_participants);
            tvRankParticipant.setText(contestData.getParticpants());
        }

        //setting display image
        AppUtilMethods.setImageShowingLoader(this, ivDisplayImage, resGetContestDetails.getContestData().getImage());
    }

    @Override
    protected void initViews() {
        rlToolbarMyCart.setVisibility(View.GONE);
        tvTitle.setText(R.string.txt_title_challenge_details);
        ivShare.setVisibility(ImageView.VISIBLE);
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        btnLeaveChallenge.setOnClickListener(this);
        btnDetail.setOnClickListener(this);
        btnJoin.setOnClickListener(this);
        btnLeaderboard.setOnClickListener(this);
        rvLeaderboardList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvLeaderboardList.setHasFixedSize(true);
        rvLeaderboardList.addItemDecoration(new DividerItemDecorationLeaderboard(this));

    }

    @Override
    protected void initVariables() {
        if (NetworkConnection.isNetworkConnected(ChallengeDetailsActivity.this)) {
            getContestDetails();
        } else {
            ToastUtils.showShortToast(ChallengeDetailsActivity.this, getResources().getString(R.string.err_network_connection));
        }

    }

    /**
     * This method is used to show the progress dialog
     *
     * @return void
     */
    private void showProgressDialog() {
        mProgressDialog = AppDialog.showProgressDialog(this);
        mProgressDialog.show();
    }

    /**
     * This method is used to hide the progress dialog
     *
     * @return void
     */
    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void setVisible(View view) {
        if (view.getVisibility() == view.VISIBLE)
            view.setVisibility(view.GONE);
        else
            view.setVisibility(view.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_share:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        String sharedMessage1 = getString(R.string.txt_challenge_shared_msg_one);
                        String sharedMessage2 = getString(R.string.txt_challenge_shared_msg_two);
                        String shareBody = sharedMessage1 + mResGetContestDetails.getContestData().getName() + sharedMessage2;
                        String shareSubject = getString(R.string.app_name);
                        AppUtilMethods.openTextShareDialog(this, shareSubject, shareBody);
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_join:
                if (NetworkConnection.isNetworkConnected(this)) {
                    if (AppUtilMethods.calculateDays(mResGetContestDetails.getContestData().getEndDate()) < 0) {
                        new AlertDialog.Builder(this)
                                .setTitle("Alert")
                                .setMessage(getString(R.string.txt_contest_ended))
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    } else
                        joinChallenge(mContestId);
                }
                break;

            case R.id.btn_leave_challenge:
                if (NetworkConnection.isNetworkConnected(this)) {
                    setVisible(v);
                    mIsAlreadyJoined = false;
//                btnLeaderboard.setEnabled(false);
                    btnLeaderboard.setVisibility(View.VISIBLE);
                    btnJoin.setVisibility(Button.VISIBLE);
                    btnDetail.setVisibility(View.GONE);
                    //llDistanceCalories.setVisibility(View.INVISIBLE);
                    llInfoContainer.setVisibility(View.VISIBLE);
                    llInfoLeaderboardContainer.setVisibility(View.GONE);
//                btnLeaderboard.setBackgroundResource(R.drawable.button_border_leaderboard);
//                btnLeaderboard.setTextColor(getResources().getColor(R.color.c_gray_text));
                    btnDetail.setTextColor(getResources().getColor(R.color.c_gray_text));
                    leaveChallenge(mContestId);
                }

                break;
            case R.id.btn_detail:
                setVisible(v);
                btnLeaderboard.setVisibility(View.VISIBLE);
                llInfoContainer.setVisibility(View.VISIBLE);
                llInfoLeaderboardContainer.setVisibility(View.GONE);
                break;
            case R.id.btn_leaderbord:
                setVisible(v);
                v.setBackgroundResource(R.drawable.button_border_leaderboard_green);
                llInfoContainer.setVisibility(View.GONE);
                llInfoLeaderboardContainer.setVisibility(View.VISIBLE);
                btnDetail.setVisibility(View.VISIBLE);
                btnDetail.setTextColor(getResources().getColor(R.color.c_join));
                break;
        }
    }

    /**
     * click on Join
     *
     * @param v
     */
    private void clickJoin(View v) {
        setVisible(v);
        mIsAlreadyJoined = true;
        btnLeaveChallenge.setVisibility(Button.VISIBLE);
        btnLeaderboard.setEnabled(true);
        btnLeaderboard.setBackgroundResource(R.drawable.button_border_leaderboard_green);
        btnLeaderboard.setTextColor(getResources().getColor(R.color.c_join));
        llDistanceCalories.setVisibility(View.VISIBLE);
    }

    /**
     * Join challenges
     *
     * @param contestId
     */
    public void joinChallenge(String contestId) {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        ReqJoinChallenge reqJoinChallenge = new ReqJoinChallenge();
        reqJoinChallenge.setMethod(MethodFactory.JOIN_CHALLENGE.getMethod());
        reqJoinChallenge.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqJoinChallenge.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqJoinChallenge.setContestID(contestId);
        Call<ResLeaveContest> resLeaveContestDataCall = client.joinChallengeFromDetail(reqJoinChallenge);
        resLeaveContestDataCall.enqueue(new Callback<ResLeaveContest>() {
            @Override
            public void onResponse(Call<ResLeaveContest> call, Response<ResLeaveContest> response) {
                dismissProgressDialog();
                ResLeaveContest resLeaveContest = response.body();
                if (resLeaveContest != null) {
                    if (resLeaveContest.getSuccess() == ServiceConstants.SUCCESS) {
                        ToastUtils.showShortToast(ChallengeDetailsActivity.this, "Join Successfully");
                        mIsAlreadyJoined = true;
                        ResLeaveContest.CurrentContestData currentContestData = resLeaveContest.getCurrentContestData();
                        setData(resLeaveContest, mIsAlreadyJoined);

                    } else {
                        ToastUtils.showShortToast(ChallengeDetailsActivity.this, resLeaveContest.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(ChallengeDetailsActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResLeaveContest> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(ChallengeDetailsActivity.this, R.string.err_network_connection);
            }
        });
    }

    /**
     * Leave challenges
     *
     * @param contestId
     */
    public void leaveChallenge(String contestId) {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        ReqLeaveChallenge reqLeaveChallenge = new ReqLeaveChallenge();
        reqLeaveChallenge.setMethod(MethodFactory.LEAVE_CHALLENGE.getMethod());
        reqLeaveChallenge.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqLeaveChallenge.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqLeaveChallenge.setContestID(contestId);
        Call<ResLeaveContest> resLeaveContestDataCall = client.leaveChallenge(reqLeaveChallenge);
        resLeaveContestDataCall.enqueue(new Callback<ResLeaveContest>() {
            @Override
            public void onResponse(Call<ResLeaveContest> call, Response<ResLeaveContest> response) {
                dismissProgressDialog();
                ResLeaveContest resLeaveContest = response.body();
                if (resLeaveContest != null) {
                    if (resLeaveContest.getSuccess() == ServiceConstants.SUCCESS) {
                        ToastUtils.showShortToast(ChallengeDetailsActivity.this, "Leave Successfully");
                        mIsAlreadyJoined = false;
                        setData(resLeaveContest, mIsAlreadyJoined);
                    } else {
                        ToastUtils.showShortToast(ChallengeDetailsActivity.this, resLeaveContest.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(ChallengeDetailsActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResLeaveContest> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(ChallengeDetailsActivity.this, R.string.err_network_connection);
            }
        });

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
