package affle.com.fitstreet.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.request.ReqBase;
import affle.com.fitstreet.models.response.ResContactUs;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_phone)
    CustomTypefaceTextView tvPhone;
    @BindView(R.id.tv_timings)
    CustomTypefaceTextView tvTimings;
    @BindView(R.id.tv_customer_support_mail)
    CustomTypefaceTextView tvCustomerSupportMail;
    @BindView(R.id.tv_business_enquiry_mail)
    CustomTypefaceTextView tvBusinessEnquiryMail;
    @BindView(R.id.tv_address)
    CustomTypefaceTextView tvAddress;
    @BindView(R.id.tv_city)
    CustomTypefaceTextView tvCity;
    @BindView(R.id.tv_facebook)
    CustomTypefaceTextView tvFacebook;
    @BindView(R.id.tv_twitter)
    CustomTypefaceTextView tvTwitter;
    @BindView(R.id.tv_insta)
    CustomTypefaceTextView tvInsta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        super.initData();
        if(NetworkConnection.isNetworkConnected(this))
        getContactDetails();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_contact_us);
    }

    @Override
    protected void initVariables() {
        ivBack.setOnClickListener(this);

    }

    /**
     * Getting contact details from server
     */
    private void getContactDetails() {
        AppDialog.showProgressDialog(ContactUsActivity.this, true);
        final ReqBase reqBase = new ReqBase();
        reqBase.setMethod(MethodFactory.CONTACT_US.getMethod());
        reqBase.setServiceKey(ServiceConstants.SERVICE_KEY);
        IApiClient client = ApiClient.getApiClient();
        Call<ResContactUs> call = client.conatctUs(reqBase);
        call.enqueue(new Callback<ResContactUs>() {
            @Override
            public void onResponse(Call<ResContactUs> call, Response<ResContactUs> response) {
                AppDialog.showProgressDialog(ContactUsActivity.this, false);
                ResContactUs resContactUs = response.body();
                if (resContactUs != null) {
                    if (resContactUs.getSuccess() == ServiceConstants.SUCCESS) {
                        tvPhone.setText("+91- " + resContactUs.getContactData().getPhone());
                        tvTimings.setText("Timings : " + resContactUs.getContactData().getStartTime() + " AM to " + resContactUs.getContactData().getEndTime()
                                + " PM (" + resContactUs.getContactData().getTimeStsing() + ")");
                        tvCustomerSupportMail.setText("Customer Support : " + resContactUs.getContactData().getCsEmail());
                        tvBusinessEnquiryMail.setText("Business Inquiry : " + resContactUs.getContactData().getBsEmail());
                        tvAddress.setText(resContactUs.getContactData().getAddress());
                        tvCity.setText(resContactUs.getContactData().getCity() + " - " + resContactUs.getContactData().getPincode());

                    } else {
                        ToastUtils.showShortToast(ContactUsActivity.this, resContactUs.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(ContactUsActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResContactUs> call, Throwable t) {
                AppDialog.showProgressDialog(ContactUsActivity.this, false);
                ToastUtils.showShortToast(ContactUsActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    @OnClick({R.id.tv_facebook, R.id.tv_twitter, R.id.tv_insta})
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_facebook:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(AppConstants.FACEBOOK_URL));
                if (URLUtil.isValidUrl(AppConstants.FACEBOOK_URL)) {
                    startActivity(intent);
                } else {
                    ToastUtils.showShortToast(ContactUsActivity.this, getString(R.string.txt_invalid_url));
                }
                break;
            case R.id.tv_twitter:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(AppConstants.TWITTER_URL));
                if (URLUtil.isValidUrl(AppConstants.TWITTER_URL)) {
                    startActivity(intent);
                } else {
                    ToastUtils.showShortToast(ContactUsActivity.this, getString(R.string.txt_invalid_url));
                }
                break;
            case R.id.tv_insta:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(AppConstants.INSTAGRAM_URL));
                if (URLUtil.isValidUrl(AppConstants.INSTAGRAM_URL)) {
                    startActivity(intent);
                } else {
                    ToastUtils.showShortToast(ContactUsActivity.this, getString(R.string.txt_invalid_url));
                }
                break;
        }
    }
}
