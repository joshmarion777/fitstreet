package affle.com.fitstreet.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.CancellationDetailsAdapter;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.request.ReqCancellationDetails;
import affle.com.fitstreet.models.response.ResCancellationDetails;
import affle.com.fitstreet.models.response.ResCashBackHistory;
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

public class CancellationDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    CustomTypefaceTextView tvTitle;
    @BindView(R.id.tv_total_refund)
    TextView tvTotalRefund;
    @BindView(R.id.rv_cancellation_detail)
    RecyclerView rvCancellationDetail;
    @BindView(R.id.iv_no_cancellation)
    ImageView ivNoCancellation;
    @BindView(R.id.tv_no_cancellation)
    CustomTypefaceTextView tvNoCancellation;
    @BindView(R.id.view_line)
    View viewLine;
    private CancellationDetailsAdapter mCancellationDetailsAdapter;
    private ArrayList<ResCancellationDetails.CancellationDatum> mCancellationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation_detail);
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.refund_history);
        rvCancellationDetail.setLayoutManager(new LinearLayoutManager(this));
        mCancellationDetailsAdapter = new CancellationDetailsAdapter(mCancellationList);
        rvCancellationDetail.setAdapter(mCancellationDetailsAdapter);

    }

    @Override
    protected void initVariables() {
        ivBack.setOnClickListener(this);
        getCancellationDetails();

    }

    /**
     * Calculating total refund amount when cancelling products from order
     *
     * @param cancellationList List of products to be cancelled
     * @return
     */
    private float calculateTotalAmount(ArrayList<ResCancellationDetails.CancellationDatum> cancellationList) {
        float totalRefund = 0;
        for (int i = 0; i < cancellationList.size(); i++) {
            totalRefund = totalRefund + Float.parseFloat(cancellationList.get(i).getAmount());
        }
        return totalRefund;
    }

    /**
     * Getting Cancelled products details
     */
    private void getCancellationDetails() {
        AppDialog.showProgressDialog(this, true);
        ReqCancellationDetails reqCancellation = new ReqCancellationDetails();
        reqCancellation.setMethod(MethodFactory.GET_CANCELLATION_DETAILS.getMethod());
        reqCancellation.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqCancellation.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ""));
        IApiClient client = ApiClient.getApiClient();
        Call<ResCancellationDetails> call = client.getCancellationDetails(reqCancellation);
        call.enqueue(new Callback<ResCancellationDetails>() {
            @Override
            public void onResponse(Call<ResCancellationDetails> call, Response<ResCancellationDetails> response) {
                AppDialog.showProgressDialog(CancellationDetailActivity.this, false);
                ResCancellationDetails resCancellationDetails = response.body();
                if (resCancellationDetails != null) {
                    if (resCancellationDetails.getSuccess() == ServiceConstants.SUCCESS) {
                        ivNoCancellation.setVisibility(View.GONE);
                        tvNoCancellation.setVisibility(View.GONE);
                        rvCancellationDetail.setVisibility(View.VISIBLE);
                        viewLine.setVisibility(View.VISIBLE);
                        tvTotalRefund.setVisibility(View.VISIBLE);
                        mCancellationList.addAll(resCancellationDetails.getCancellationData());
                        tvTotalRefund.setText(getString(R.string.txt_total_refund) + calculateTotalAmount(mCancellationList));
                        mCancellationDetailsAdapter.notifyDataSetChanged();
                    } else {
                        //ToastUtils.showShortToast(CancellationDetailActivity.this, resCancellationDetails.getErrstr());
                        ivNoCancellation.setVisibility(View.VISIBLE);
                        tvNoCancellation.setVisibility(View.VISIBLE);
                        rvCancellationDetail.setVisibility(View.GONE);
                        viewLine.setVisibility(View.GONE);
                        tvTotalRefund.setVisibility(View.GONE);
                    }
                } else {
                    ToastUtils.showShortToast(CancellationDetailActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResCancellationDetails> call, Throwable t) {
                AppDialog.showProgressDialog(CancellationDetailActivity.this, false);
                ToastUtils.showShortToast(CancellationDetailActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}
