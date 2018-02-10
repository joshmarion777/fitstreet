package affle.com.fitstreet.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.ContestsRecyclerAdapter;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.interfaces.IViewType;
import affle.com.fitstreet.models.ChallengeHeaderModel;
import affle.com.fitstreet.models.ChallengeNoDataModel;
import affle.com.fitstreet.models.request.ReqContests;
import affle.com.fitstreet.models.request.ReqJoinChallenge;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResGetContests;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.activities.ChallengeDetailsActivity;
import affle.com.fitstreet.ui.activities.HomeActivity;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akash on 20/7/16.
 */
public class ChallengesFragment extends BaseFragment implements IOnItemClickListener {
    @BindView(R.id.rv_challenge)
    RecyclerView rvChallenges;
    private List<ResGetContests.MyContestData> myContestData;
    private List<ResGetContests.UpContestData> upContestData;
    private List<IViewType> mChallengesList;
    private ContestsRecyclerAdapter mContestsRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);
        return view;
    }

    @Override
    protected void initViews() {
        ((HomeActivity) mActivity).setActionBarTitle(R.string.txt_challenges);
        rvChallenges.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    protected void initVariables() {
        mChallengesList = new ArrayList<IViewType>();
        mContestsRecyclerAdapter = new ContestsRecyclerAdapter(mChallengesList, this);
        rvChallenges.setAdapter(mContestsRecyclerAdapter);
        myContestData = new ArrayList<ResGetContests.MyContestData>();
        upContestData = new ArrayList<ResGetContests.UpContestData>();
        mContestsRecyclerAdapter.setOnItemClickListener(this);
        serverCall();
    }

    /**
     * Calling web service
     */
    private void serverCall() {
        if (NetworkConnection.isNetworkConnected(mActivity)) {
            if (isAdded())
                getContests();
        } else {
            ToastUtils.showShortToast(mActivity, getString(R.string.err_network_connection));
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        IViewType challenge = mChallengesList.get(position);
        Intent intent;
        intent = new Intent(mActivity, ChallengeDetailsActivity.class);
        intent.putExtra(AppConstants.TAG_FROM_WHERE, challenge.getViewType());
        switch (challenge.getViewType()) {
            case IViewType.VIEW_TYPE_MY_CONTEST:
                ResGetContests.MyContestData myContestData = (ResGetContests.MyContestData) challenge;
                intent.putExtra(AppConstants.TAG_CONTEST_ID, myContestData.getContestID());
                intent.putExtra(AppConstants.TAG_CONTEST_VIEW_TYPE, IViewType.VIEW_TYPE_MY_CONTEST + "");
                startActivityForResult(intent, AppConstants.RC_CHALLENGES_FRAGMENT);
                break;

            case IViewType.VIEW_TYPE_UPCOMING_CONTEST:
                ResGetContests.UpContestData upContestData = (ResGetContests.UpContestData) challenge;
                intent.putExtra(AppConstants.TAG_CONTEST_ID, upContestData.getContestID());
                startActivityForResult(intent, AppConstants.RC_CHALLENGES_FRAGMENT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_CHALLENGES_FRAGMENT && resultCode == Activity.RESULT_OK) {
            serverCall();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * Joining challenges
     *
     * @param contestId id of contest to be join
     */
    public void joinChallenge(String contestId) {
        AppDialog.showProgressDialog(mActivity, true);
        IApiClient client = ApiClient.getApiClient();
        ReqJoinChallenge reqJoinChallenge = new ReqJoinChallenge();
        reqJoinChallenge.setMethod(MethodFactory.JOIN_CHALLENGE.getMethod());
        reqJoinChallenge.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqJoinChallenge.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqJoinChallenge.setContestID(contestId);
        Call<ResGetContests> resGetContestsCall = client.joinChallenge(reqJoinChallenge);
        resGetContestsCall.enqueue(new Callback<ResGetContests>() {
            @Override
            public void onResponse(Call<ResGetContests> call, Response<ResGetContests> response) {
                AppDialog.showProgressDialog(mActivity, false);
                ResBase resBase = response.body();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        ResGetContests resGetContests = response.body();
                        upContestData.clear();
                        myContestData.clear();
                        upContestData.addAll(resGetContests.getUpContestData());
                        myContestData.addAll(resGetContests.getMyContestData());
                        mChallengesList.clear();
                        if (myContestData.size() > 0)
                            mChallengesList.add(new ChallengeHeaderModel(getString(R.string.txt_my_challenges)));
                        mChallengesList.addAll(myContestData);
                        mChallengesList.add(new ChallengeHeaderModel(getString(R.string.txt_ongoing_challenges)));
                        mChallengesList.addAll(upContestData);
                        if (upContestData.size() == 0)
                            mChallengesList.add(new ChallengeNoDataModel(getString(R.string.txt_no_ongoing_challenges)));
                        mContestsRecyclerAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showShortToast(mActivity, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResGetContests> call, Throwable t) {
                AppDialog.showProgressDialog(mActivity, false);
                ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
            }
        });
    }

    /**
     * Get contests list from server
     */
    public void getContests() {
        AppDialog.showProgressDialog(mActivity, true);
        IApiClient apiClient = ApiClient.getApiClient();
        final ReqContests reqContests = new ReqContests();
        reqContests.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqContests.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqContests.setMethod(MethodFactory.GET_CONTESTS.getMethod());
        Call<ResGetContests> resGetContestsCall = apiClient.getContests(reqContests);
        resGetContestsCall.enqueue(new Callback<ResGetContests>() {
            @Override
            public void onResponse(Call<ResGetContests> call, Response<ResGetContests> response) {
                AppDialog.showProgressDialog(mActivity, false);
                if (isAdded()) {
                    ResGetContests resGetContests = response.body();
                    if (resGetContests != null) {
                        if (resGetContests.getSuccess() == ServiceConstants.SUCCESS) {
                            myContestData.clear();
                            upContestData.clear();
                            myContestData.addAll(resGetContests.getMyContestData());
                            upContestData.addAll(resGetContests.getUpContestData());
                            mChallengesList.clear();
                            if (myContestData.size() > 0)
                                mChallengesList.add(new ChallengeHeaderModel(getString(R.string.txt_my_challenges)));
//                            mChallengesList.add(new ChallengeNoDataModel(getString(R.string.txt_no_joined_challenges)));
                            mChallengesList.addAll(myContestData);
                            mChallengesList.add(new ChallengeHeaderModel(getString(R.string.txt_ongoing_challenges)));
                            mChallengesList.addAll(upContestData);
                            if (upContestData.size() == 0)
                                mChallengesList.add(new ChallengeNoDataModel(getString(R.string.txt_no_ongoing_challenges)));
                            mContestsRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.showShortToast(mActivity, resGetContests.getErrstr());
                        }
                    } else {
                        ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResGetContests> call, Throwable t) {
                AppDialog.showProgressDialog(mActivity, false);
                ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
            }
        });
    }

}
