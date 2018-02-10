package affle.com.fitstreet.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceButton;
import affle.com.fitstreet.customviews.CustomTypefaceEditText;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.request.ReqSendToBank;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendToBankActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    CustomTypefaceTextView tvTitle;
    @BindView(R.id.et_account_holder_name)
    CustomTypefaceEditText etAccountHolderName;
    @BindView(R.id.et_bank_name)
    CustomTypefaceEditText etBankName;
    @BindView(R.id.et_account_number)
    CustomTypefaceEditText etAccountNumber;
    @BindView(R.id.et_ifsc_code)
    CustomTypefaceEditText etIfscCode;
    @BindView(R.id.et_amoun_send_to_bank)
    CustomTypefaceEditText etAmounSendToBank;
    @BindView(R.id.btn_send_to_bank)
    CustomTypefaceButton btnSendToBank;
    private float mWalletBalance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_bank);
        super.initData();
        Intent intent = getIntent();
        mWalletBalance = Float.parseFloat(intent.getExtras().getString("walletAmount", "0"));
    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_send_to_bank);
    }

    @Override
    protected void initVariables() {
        btnSendToBank.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_to_bank:
                if (isValidBankInfo())
                    sendDetails();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Checking valid information
     *
     * @return
     */
    private boolean isValidBankInfo() {
        if (etAccountHolderName.getText().toString().isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_account_holder_name);
            return false;
        } else if (etBankName.getText().toString().isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_bank_name);
            return false;
        } else if (etAccountNumber.getText().toString().isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_account_number);
            return false;
        } else if (etIfscCode.getText().toString().isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_ifsc_code);
            return false;
        } else if (etAmounSendToBank.getText().toString().isEmpty()) {
            ToastUtils.showShortToast(this, R.string.err_amount_send);
            return false;
        } else if (mWalletBalance < 100) {
            ToastUtils.showShortToast(this, R.string.txt_check_min_balnace_in_wallet);
            return false;
        } else if (Float.parseFloat(etAmounSendToBank.getText().toString()) > mWalletBalance) {
            ToastUtils.showShortToast(this, R.string.err_amount_value_greater_than_wallet);
            return false;
        } else if (Float.parseFloat(etAmounSendToBank.getText().toString()) < 100 || Integer.parseInt(etAmounSendToBank.getText().toString()) > 5000) {
            ToastUtils.showShortToast(this, R.string.err_amount_value);
            return false;
        }

        return true;
    }

    /**
     * Send user bank detail info to the server
     */
    private void sendDetails() {
        AppDialog.showProgressDialog(SendToBankActivity.this, true);
        ReqSendToBank reqSendToBank = new ReqSendToBank();
        reqSendToBank.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqSendToBank.setAccountHolderName(etAccountHolderName.getText().toString());
        reqSendToBank.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqSendToBank.setAccountNumber(etAccountNumber.getText().toString());
        reqSendToBank.setAmount(etAmounSendToBank.getText().toString());
        reqSendToBank.setBankName(etBankName.getText().toString());
        reqSendToBank.setIfscCode(etIfscCode.getText().toString());
        reqSendToBank.setMethod(MethodFactory.SEND_TO_BANK.getMethod());
        IApiClient client = ApiClient.getApiClient();
        Call<ResBase> call = client.sendToBank(reqSendToBank);
        call.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                AppDialog.showProgressDialog(SendToBankActivity.this, false);
                AppUtilMethods.hideKeyBoard(SendToBankActivity.this);
                ResBase resBase = response.body();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        ToastUtils.showShortToast(SendToBankActivity.this, R.string.send_to_bank_info);
                        Intent intent = new Intent();
                        intent.putExtra("amountSendToBank", etAmounSendToBank.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        ToastUtils.showShortToast(SendToBankActivity.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(SendToBankActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(SendToBankActivity.this, false);
            }
        });

    }
}
