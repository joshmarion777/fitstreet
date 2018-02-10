package affle.com.fitstreet.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.PrizeMoneyDetailsAdapter;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.request.ReqPrizeMoneyDetails;
import affle.com.fitstreet.models.response.ResPrizeMoneyDetails;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrizeMoneyDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_no_prize_money)
    ImageView ivNoPrizeMoney;
    @BindView(R.id.tv_iv_no_prize_money)
    CustomTypefaceTextView tvIvNoPrizeMoney;
    @BindView(R.id.tv_total_prize_money)
    CustomTypefaceTextView tvTotalPrizeMoney;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.rv_prize_money_detail)
    RecyclerView rvPrizeMoneyDetail;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    CustomTypefaceTextView tvTitle;
    private PrizeMoneyDetailsAdapter mPrizeMoneyDetailsAdapter;
    private ArrayList<ResPrizeMoneyDetails.PrizeDatum> mPrizeMoneyDetailsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize_money_details);
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_prize_money);
        rvPrizeMoneyDetail.setLayoutManager(new LinearLayoutManager(this));
        mPrizeMoneyDetailsAdapter = new PrizeMoneyDetailsAdapter(this, mPrizeMoneyDetailsList);
        rvPrizeMoneyDetail.setAdapter(mPrizeMoneyDetailsAdapter);
    }

    @Override
    protected void initVariables() {
        ivBack.setOnClickListener(this);
        getPrizeMoneyDetails();
    }

    /**
     * Getting prize money history details from server
     */
    private void getPrizeMoneyDetails() {
        AppDialog.showProgressDialog(this, true);
        final ReqPrizeMoneyDetails reqPrizeMoney = new ReqPrizeMoneyDetails();
        reqPrizeMoney.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqPrizeMoney.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqPrizeMoney.setMethod(MethodFactory.GET_PRIZE_MONEY_DETAILS.getMethod());
        IApiClient client = ApiClient.getApiClient();
        Call<ResPrizeMoneyDetails> call = client.getPrizeMoneyDetails(reqPrizeMoney);
        call.enqueue(new Callback<ResPrizeMoneyDetails>() {
            @Override
            public void onResponse(Call<ResPrizeMoneyDetails> call, Response<ResPrizeMoneyDetails> response) {
                AppDialog.showProgressDialog(PrizeMoneyDetailsActivity.this, false);
                ResPrizeMoneyDetails resPrizeMoneyDetails = response.body();
                if (resPrizeMoneyDetails != null) {
                    if (resPrizeMoneyDetails.getSuccess() == ServiceConstants.SUCCESS) {
                        mPrizeMoneyDetailsList.clear();
                        mPrizeMoneyDetailsList.addAll(resPrizeMoneyDetails.getPrizeData());
                        mPrizeMoneyDetailsAdapter.notifyDataSetChanged();
                        rvPrizeMoneyDetail.setVisibility(View.VISIBLE);
                        ivNoPrizeMoney.setVisibility(View.GONE);
                        tvIvNoPrizeMoney.setVisibility(View.GONE);
                    } else {
                        ivNoPrizeMoney.setVisibility(View.VISIBLE);
                        tvIvNoPrizeMoney.setVisibility(View.VISIBLE);
//                        ToastUtils.showShortToast(PrizeMoneyDetailsActivity.this, resPrizeMoneyDetails.getErrstr());
                    }
                } else {
//                    ToastUtils.showShortToast(PrizeMoneyDetailsActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResPrizeMoneyDetails> call, Throwable t) {
                AppDialog.showProgressDialog(PrizeMoneyDetailsActivity.this, false);
                ToastUtils.showShortToast(PrizeMoneyDetailsActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }
    }
}
