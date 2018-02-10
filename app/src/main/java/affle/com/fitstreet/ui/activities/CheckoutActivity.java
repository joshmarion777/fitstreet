package affle.com.fitstreet.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceButton;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.request.ReqProceed;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResUserAddress;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.AppSharedPreference;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends BaseActivity implements PaymentResultListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_title)
    CustomTypefaceTextView tvTitle;
    @BindView(R.id.iv_toolbar_favourite)
    ImageView ivToolbarFavourite;
    @BindView(R.id.tv_toolbar_favourite_count)
    CustomTypefaceTextView tvToolbarFavouriteCount;
    @BindView(R.id.rl_toolbar_favourite)
    RelativeLayout rlToolbarFavourite;
    @BindView(R.id.iv_toolbar_filter)
    ImageView ivToolbarFilter;
    @BindView(R.id.iv_toolbar_filter_active)
    ImageView ivToolbarFilterActive;
    @BindView(R.id.rl_toolbar_filter)
    RelativeLayout rlToolbarFilter;
    @BindView(R.id.iv_toolbar_mycart)
    ImageView ivToolbarMycart;
    @BindView(R.id.tv_toolbar_cart_count)
    CustomTypefaceTextView tvToolbarCartCount;
    @BindView(R.id.rl_toolbar_mycart)
    RelativeLayout rlToolbarMycart;
    @BindView(R.id.btn_proceed)
    CustomTypefaceButton btnProceed;
    @BindView(R.id.tv_select_address)
    CustomTypefaceTextView tvSelectAddress;
    @BindView(R.id.tv_address_name)
    CustomTypefaceTextView tvAddressName;
    @BindView(R.id.tv_address_location)
    CustomTypefaceTextView tvAddressLocation;
    @BindView(R.id.tv_address_city)
    CustomTypefaceTextView tvAddressCity;
    @BindView(R.id.tv_address_edit)
    CustomTypefaceTextView tvAddressEdit;
    @BindView(R.id.tv_address_state)
    CustomTypefaceTextView tvAddressState;
    @BindView(R.id.tv_address_mobile)
    CustomTypefaceTextView tvAddressMobile;
    @BindView(R.id.card_saved_address)
    CardView cardSavedAddress;
    @BindView(R.id.select_add_address)
    CardView selectAddAddress;
    @BindView(R.id.tv_wallet_balance)
    CustomTypefaceTextView tvWalletBalance;
    @BindView(R.id.card_pay_by_wallet)
    CardView cardPayByWallet;
    @BindView(R.id.tv_order_summary)
    CustomTypefaceTextView tvOrderSummary;
    @BindView(R.id.card_order_summary)
    CardView cardOrderSummary;
    @BindView(R.id.tv_number_of_products)
    CustomTypefaceTextView tvNumberOfProducts;
    @BindView(R.id.tv_total_payable_amount)
    CustomTypefaceTextView tvTotalPayableAmount;
    @BindView(R.id.tv_amount_to_be_paid)
    CustomTypefaceTextView tvAmountToBePaid;
    @BindView(R.id.paid_from_wallet_balance)
    CustomTypefaceTextView paidFromWalletBalance;
    @BindView(R.id.tv_pay_by_wallet)
    CustomTypefaceTextView tvPayByWallet;
    @BindView(R.id.btn_pay_by_other_options)
    Button btnPayByOtherOptions;
    @BindView(R.id.tv_email_id)
    TextView tvEmailId;
    private float mTotalAmount;
    private int mTotalProducts;
    private float mWalletAmount;
    private Intent mIntent;
    private boolean mPayByWallet = false;
    private float amountToBePaid = 0;
    private ArrayList<Integer> mCartIds = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);
        mIntent = getIntent();
        if (mIntent != null) {
            mTotalAmount = Float.parseFloat(mIntent.getStringExtra("totalPayableAmount"));
            mTotalProducts = Integer.parseInt(mIntent.getStringExtra("productsCount").equals("") ? "0" : mIntent.getStringExtra("productsCount"));
            //tertiary operator
            mWalletAmount = Float.parseFloat((mIntent.getStringExtra("walletAmount").equals("") ? "0" : mIntent.getStringExtra("walletAmount")));
            tvNumberOfProducts.setText(String.valueOf(mTotalProducts));
            tvTotalPayableAmount.setText(String.valueOf(mTotalAmount));
            if (mPayByWallet) {
                if (mWalletAmount > 0) {
                    if (mWalletAmount < mTotalAmount) {
                        paidFromWalletBalance.setText(String.valueOf(mWalletAmount));
                    } else {
                        paidFromWalletBalance.setText(String.valueOf(mTotalAmount));
                    }
                } else {
                    paidFromWalletBalance.setText(String.valueOf(mWalletAmount));
                }
            } else {
                paidFromWalletBalance.setText("0.0");
            }

            mCartIds.addAll(mIntent.getIntegerArrayListExtra("cartIds"));
            if (mIntent.getBooleanExtra("addressAvailability", false)) {
                cardSavedAddress.setVisibility(View.VISIBLE);
                tvAddressName.setText(mIntent.getStringExtra("name"));
                tvAddressLocation.setText(mIntent.getStringExtra("address") + ",");
                tvAddressMobile.setText(mIntent.getStringExtra("phone"));
                tvAddressCity.setText(((mIntent.getStringExtra("landmark") == null || mIntent.getStringExtra("landmark").equals("") ? "" : mIntent.getStringExtra("landmark") + ", ")) + mIntent.getStringExtra("city") + " - " + mIntent.getStringExtra("pincode"));
                tvAddressState.setText(mIntent.getStringExtra("state"));
            } else {
                cardSavedAddress.setVisibility(View.GONE);
            }
            if (mPayByWallet)
                amountToBePaid = mTotalAmount - mWalletAmount;
            else
                amountToBePaid = mTotalAmount;
            if (amountToBePaid > 0) {
                tvAmountToBePaid.setText("Rs. " + String.valueOf(amountToBePaid));
            } else {
                tvAmountToBePaid.setText("Rs. 0");
            }
            tvWalletBalance.setText(String.valueOf(mWalletAmount));
            tvEmailId.setText(AppSharedPreference.getInstance(this).getString(PreferenceKeys.KEY_EMAIL_ID, ""));
        }
        super.initData();

    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_checkout);
        ivBack.setOnClickListener(this);
        selectAddAddress.setOnClickListener(this);
        tvAddressEdit.setOnClickListener(this);
        btnProceed.setOnClickListener(this);
        tvPayByWallet.setOnClickListener(this);
        btnPayByOtherOptions.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_CHECKOUT_ACTIVITY && resultCode == RESULT_OK) {
            cardSavedAddress.setVisibility(View.VISIBLE);
            mIntent.putExtra("addressAvailability", true);
            tvAddressName.setText(data.getExtras().getString("name"));
            tvAddressCity.setText(((data.getExtras().getString("landmark") == null || data.getStringExtra("landmark").equals("") ? "" : data.getExtras().getString("landmark") + ", ") + data.getExtras().getString("city") + " - " + data.getExtras().getString("pincode") + ","));
            tvAddressLocation.setText(data.getExtras().getString("address") + ",");
            tvAddressState.setText(data.getExtras().getString("state"));
            tvAddressMobile.setText("Mobile - " + data.getExtras().getString("mobile"));
            mIntent.putExtra("city", data.getExtras().getString("city"));
            mIntent.putExtra("name", data.getExtras().getString("name"));
            mIntent.putExtra("phone", data.getExtras().getString("mobile"));
            mIntent.putExtra("address", data.getExtras().getString("address"));
            mIntent.putExtra("pincode", data.getExtras().getString("pincode"));
            mIntent.putExtra("state", data.getExtras().getString("state"));
            mIntent.putExtra("landmark", data.getExtras().getString("landmark"));
            mIntent.putExtra("addId", data.getExtras().getString("addId"));
        }
        if (requestCode == AppConstants.RC_ADD_ADDRESS && resultCode == RESULT_OK) {
            AppDialog.showProgressDialog(this, false);
            if (data != null) {
                ResUserAddress address = data.getParcelableExtra(AppConstants.TAG_ADDRESS_MODEL);
                tvAddressName.setText(address.getName());
                tvAddressCity.setText((address.getLandmark().equals("") || address.getLandmark() == null ? "" : address.getLandmark() + ", ") + address.getCity() + " - " + address.getPincode() + ",");
                tvAddressLocation.setText(address.getAddress() + ",");
                tvAddressState.setText(address.getState());
                tvAddressMobile.setText(address.getPhone());
                mIntent.putExtra("city", address.getCity());
                mIntent.putExtra("name", address.getName());
                mIntent.putExtra("phone", address.getPhone());
                mIntent.putExtra("address", address.getAddress());
                mIntent.putExtra("pincode", address.getPincode());
                mIntent.putExtra("state", address.getState());
                mIntent.putExtra("landmark", address.getLandmark());
                mIntent.putExtra("addId", address.getUserAddID());
            }

//            int position = data.getIntExtra(AppConstants.TAG_POSITION, -1);
//            if (position >= 0)
//                mAddressList.set(position, address);
//            else
            // mAddressList.add(address);
            // mAddressesAdapter.notifyDataSetChanged();
//            tvAddressCount.setText(String.format(getResources().getQuantityString(R.plurals.plural_address, mAddressList.size()), mAddressList.size()));
//            tvAddressCount.setVisibility(View.VISIBLE);
//            rvAddresses.setVisibility(View.VISIBLE);
//            tvNoAddresses.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_proceed:
                if (mIntent.getBooleanExtra("addressAvailability", false))
                    proceedOrder(AppConstants.PAYMENT_BY_CASH_ON_DELIVERY, "");
                else
                    ToastUtils.showShortToast(CheckoutActivity.this, R.string.pls_select_address);
                break;
            case R.id.btn_pay_by_other_options:
                if (NetworkConnection.isNetworkConnected(this)) {
                    if (mWalletAmount >= mTotalAmount && mPayByWallet) {
                        if (mIntent.getBooleanExtra("addressAvailability", false))
                            proceedOrder(AppConstants.PAYMENT_BY_OTHER_OPTIONS, "");
                        else
                            ToastUtils.showShortToast(CheckoutActivity.this, R.string.pls_select_address);
                    } else {
                        if (mIntent.getBooleanExtra("addressAvailability", false)) {
                            AppDialog.showProgressDialog(this, true);
                            ToastUtils.showShortToast(this, getString(R.string.txt_wait_for_payemnt));
                            startPayment();
                        } else
                            ToastUtils.showShortToast(CheckoutActivity.this, R.string.pls_select_address);
                    }
                } else {
                    ToastUtils.showShortToast(CheckoutActivity.this, R.string.err_network_connection);
                }
                break;
            case R.id.iv_share:
                break;
            case R.id.tv_title:
                break;
            case R.id.iv_toolbar_favourite:
                break;
            case R.id.tv_toolbar_favourite_count:
                break;
            case R.id.rl_toolbar_favourite:
                break;
            case R.id.iv_toolbar_filter:
                break;
            case R.id.iv_toolbar_filter_active:
                break;
            case R.id.rl_toolbar_filter:
                break;
            case R.id.iv_toolbar_mycart:
                break;
            case R.id.tv_toolbar_cart_count:
                break;
            case R.id.rl_toolbar_mycart:
                break;
            case R.id.tv_select_address:
                break;
            case R.id.tv_address_name:
                break;
            case R.id.tv_address_location:
                break;
            case R.id.tv_address_city:
                break;
            case R.id.tv_address_edit:
                ResUserAddress address = new ResUserAddress();
                address.setCity(mIntent.getStringExtra("city"));
                address.setName(mIntent.getStringExtra("name"));
                address.setPhone(mIntent.getStringExtra("phone"));
                address.setAddress(mIntent.getStringExtra("address"));
                address.setPincode(mIntent.getStringExtra("pincode"));
                address.setState(mIntent.getStringExtra("state"));
                address.setLandmark(mIntent.getStringExtra("landmark"));
                address.setUserAddID(mIntent.getStringExtra("addId"));
                Intent intent1 = new Intent(this, AddAddressActivity.class);
                intent1.putExtra(AppConstants.TAG_ADDRESS_MODEL, address);
                intent1.putExtra(AppConstants.TAG_POSITION, "");
                intent1.putExtra(AppConstants.TAG_FROM_WHERE, "fromCheckoutActivity");
                AppDialog.showProgressDialog(this, true);
                startActivityForResult(intent1, AppConstants.RC_ADD_ADDRESS);
                break;
            case R.id.card_saved_address:
                break;
            case R.id.select_add_address:
                Intent intent = new Intent(CheckoutActivity.this, MyAddressesActivity.class);
                intent.putExtra(AppConstants.TAG_FROM_WHERE, true);
                startActivityForResult(intent, AppConstants.RC_CHECKOUT_ACTIVITY);

                break;
            case R.id.tv_wallet_details:
                break;
            case R.id.card_pay_by_wallet:
                break;
            case R.id.tv_order_summary:
                break;
            case R.id.card_order_summary:
                break;
            case R.id.tv_pay_by_wallet:
                if (!mPayByWallet) {
                    //tvPayByWallet.setCompoundDrawables(getResources().getDrawable(R.drawable.tick_icon),null,null,null);
                    mPayByWallet = true;
                    amountToBePaid = mTotalAmount - mWalletAmount;
                    Drawable image = getResources().getDrawable(R.drawable.tick);
                    int h = image.getIntrinsicHeight();
                    int w = image.getIntrinsicWidth();
                    image.setBounds(0, 0, w, h);
                    tvPayByWallet.setCompoundDrawables(image, null, null, null);


                    if (mWalletAmount > 0) {
                        if (mWalletAmount < mTotalAmount) {
                            paidFromWalletBalance.setText(String.valueOf(mWalletAmount));
                        } else {
                            paidFromWalletBalance.setText(String.valueOf(mTotalAmount));
                        }
                    } else {
                        paidFromWalletBalance.setText(String.valueOf(mWalletAmount));
                    }
                } else {
                    //tvPayByWallet.setDrawa(getResources().getDrawable(R.drawable.emptybutton),null,null,null);
                    mPayByWallet = false;
                    amountToBePaid = mTotalAmount;
                    Drawable image = getResources().getDrawable(R.drawable.empty);
                    int h = image.getIntrinsicHeight();
                    int w = image.getIntrinsicWidth();
                    image.setBounds(0, 0, w, h);
                    tvPayByWallet.setCompoundDrawables(image, null, null, null);

                    paidFromWalletBalance.setText("0.0");

                }
                if (amountToBePaid > 0) {
                    tvAmountToBePaid.setText("Rs. " + String.valueOf(amountToBePaid));
                } else {
                    tvAmountToBePaid.setText("Rs. 0.0");
                }
                break;

        }
    }

    /**
     * Initiating payment process
     */
    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;
        final Checkout co = new Checkout();
        co.setImage(R.drawable.logo);
        //co.setPublicKey("rzp_test_qvRjONGpwIid6y");
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Fitstreet");
            options.put("description", "");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", R.drawable.fs_logo);
            options.put("currency", "INR");
            options.put("amount", amountToBePaid * 100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", AppSharedPreference.getInstance(this).getString(PreferenceKeys.KEY_EMAIL_ID, ""));
            preFill.put("contact", AppSharedPreference.getInstance(this).getString(PreferenceKeys.KEY_PHONE, ""));

            options.put("prefill", preFill);

            co.open(activity, options);
            AppDialog.showProgressDialog(this, false);
        } catch (Exception e) {
            AppDialog.showProgressDialog(this, false);
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * Creating/Proceeding order after Payment or in case of COD
     *
     * @param paymentMethod type of payment
     * @param paymentId     paymentId when payment made through RazorPay
     */
    private void proceedOrder(String paymentMethod, String paymentId) {
        AppDialog.showProgressDialog(CheckoutActivity.this, true);
        ReqProceed reqProceed = new ReqProceed();
        reqProceed.setUserID(AppSharedPreference.getInstance(this).getString(PreferenceKeys.KEY_USER_ID, ""));
        reqProceed.setServiceKey(AppSharedPreference.getInstance(this).getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqProceed.setMethod(MethodFactory.PROCEED_ORDER.getMethod());
        reqProceed.setPaymentID(paymentId);
        ReqProceed.UserAddressData userAddress = new ReqProceed.UserAddressData();
        userAddress.setName(tvAddressName.getText().toString());

        String fullAddress[] = tvAddressCity.getText().toString().split(",|-");
        if (fullAddress.length > 0) {
            if (fullAddress.length >= 1)
                userAddress.setCity(fullAddress[1].trim());
            else
                userAddress.setCity("");
            if (fullAddress.length >= 2)
                userAddress.setLandmark(fullAddress[0].trim());
            else
                userAddress.setLandmark("");
            if (fullAddress.length >= 3)
                userAddress.setPincode(fullAddress[2].trim());
            else
                userAddress.setPincode("");
        }
        userAddress.setAddress(tvAddressLocation.getText().toString());
        userAddress.setPhone(tvAddressMobile.getText().toString());
        userAddress.setState(tvAddressState.getText().toString());
        userAddress.setEmailID(AppSharedPreference.getInstance(this).getString(PreferenceKeys.KEY_EMAIL_ID, ""));
        reqProceed.setUserAddressData(userAddress);
        reqProceed.setAmount(tvTotalPayableAmount.getText().toString());
        reqProceed.setCartArray(mCartIds);
        reqProceed.setPaymentBy(paymentMethod);
        if (mPayByWallet) {
            if (mWalletAmount > 0) {
                if (mWalletAmount < mTotalAmount) {
                    reqProceed.setWalletAmt(String.valueOf(mWalletAmount));
                } else {
                    reqProceed.setWalletAmt(String.valueOf(mTotalAmount));
                }
            } else {
                reqProceed.setWalletAmt(String.valueOf(mWalletAmount));
            }
        } else
            reqProceed.setWalletAmt(String.valueOf(0));
        IApiClient client = ApiClient.getApiClient();
        Call<ResBase> call = client.proceedOrder(reqProceed);
        call.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                AppDialog.showProgressDialog(CheckoutActivity.this, false);
                ResBase resBase = response.body();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        //Showing dialog after order completed
                        AppDialog.showPlaceOrderDialog(CheckoutActivity.this);
                    } else {
                        ToastUtils.showShortToast(CheckoutActivity.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(CheckoutActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(CheckoutActivity.this, false);
                ToastUtils.showShortToast(CheckoutActivity.this, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onPaymentSuccess(String paymentId) {
        try {
            //Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            if (NetworkConnection.isNetworkConnected(this))
                proceedOrder(AppConstants.PAYMENT_BY_OTHER_OPTIONS, paymentId);
            else
                ToastUtils.showShortToast(CheckoutActivity.this, R.string.err_network_connection);
        } catch (Exception e) {
            Log.e("com.merchant", e.getMessage(), e);
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            if (!response.equals("Payment Cancelled")) {
//                ToastUtils.showShortToast(CheckoutActivity.this, R.string.err_network_connection);
                Toast.makeText(this, "Payment failed: " + Integer.toString(code) + " " + response, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("com.merchant", e.getMessage(), e);
        }

    }
}
