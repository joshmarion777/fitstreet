package affle.com.fitstreet.ui.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceButton;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.request.ReqGetOrderDetail;
import affle.com.fitstreet.models.request.ReqRepeatOrder;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResGetOrderDetail;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    CustomTypefaceTextView tvTitle;
    @BindView(R.id.tv_no_of_items_txt)
    CustomTypefaceTextView tvNoOfItemsTxt;
    @BindView(R.id.tv_grand_total_txt)
    CustomTypefaceTextView tvGrandTotalTxt;
    @BindView(R.id.tv_delievered_on_txt)
    CustomTypefaceTextView tvDelieveredOnTxt;
    @BindView(R.id.tv_paymeny_by_txt)
    CustomTypefaceTextView tvPaymenyByTxt;
    @BindView(R.id.tv_order_number)
    CustomTypefaceTextView tvOrderNumber;
    @BindView(R.id.ll_order_detail_layout)
    LinearLayout llOrderDetailLayout;
    @BindView(R.id.btn_repeat_order)
    CustomTypefaceButton btnRepeatOrder;
    @BindView(R.id.rl_repeat_order)
    LinearLayout rlBtnBuy;
    @BindView(R.id.tv_number_of_items)
    TextView tvNumberOfItems;
    @BindView(R.id.tv_ordered_on)
    TextView tvOrderedOn;
    @BindView(R.id.tv_delivered_on)
    TextView tvDeliveredOn;
    @BindView(R.id.tv_grand_total)
    TextView tvGrandTotal;
    @BindView(R.id.iv_order_status)
    ImageView ivOrderStatus;
    @BindView(R.id.tv_paymeny_by)
    CustomTypefaceTextView tvPaymenyBy;
    @BindView(R.id.tv_address_name)
    CustomTypefaceTextView tvAddressName;
    @BindView(R.id.tv_address_location)
    CustomTypefaceTextView tvAddressLocation;
    @BindView(R.id.tv_address_city)
    CustomTypefaceTextView tvAddressCity;
    @BindView(R.id.tv_address_state)
    CustomTypefaceTextView tvAddressState;
    @BindView(R.id.card_saved_address)
    CardView cardSavedAddress;
    private ResGetOrderDetail mResGetOrderDetail;
    private LayoutInflater mInflater;
    String mOrderId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Intent intent = getIntent();
        mOrderId = intent.getStringExtra("orderId");
        super.initData();

    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_title_order_details);
    }

    @Override
    protected void initVariables() {
        ivBack.setOnClickListener(this);
        getOrderDetail();
        btnRepeatOrder.setOnClickListener(this);

    }

    /**
     * Getting order details from server
     */
    private void getOrderDetail() {
        AppDialog.showProgressDialog(OrderDetailsActivity.this, true);
        IApiClient client = ApiClient.getApiClient();
        final ReqGetOrderDetail reqGetOrderDetail = new ReqGetOrderDetail();
        reqGetOrderDetail.setOrderID(mOrderId);
        reqGetOrderDetail.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqGetOrderDetail.setMethod(MethodFactory.GET_ORDERS_DETAIL.getMethod());
        mInflater = (LayoutInflater) getSystemService(OrderDetailsActivity.LAYOUT_INFLATER_SERVICE);
        reqGetOrderDetail.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        Call<ResGetOrderDetail> call = client.getOrderDetail(reqGetOrderDetail);
        call.enqueue(new Callback<ResGetOrderDetail>() {
            @Override
            public void onResponse(Call<ResGetOrderDetail> call, Response<ResGetOrderDetail> response) {
                mResGetOrderDetail = response.body();
                int count = 0;
                float total = 0;
                if (mResGetOrderDetail != null) {
                    if (mResGetOrderDetail.getSuccess() == ServiceConstants.SUCCESS) {
                        for (int i = 0; i < mResGetOrderDetail.getOrderDetails().size(); i++) {
                            count++;
                            View orderView = mInflater.inflate(R.layout.row_order_detail_recycler, null);
                            orderView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            ImageView imageView = (ImageView) orderView.findViewById(R.id.iv_contest_image);
                            CustomTypefaceTextView tvProductName = (CustomTypefaceTextView) orderView.findViewById(R.id.ordered_product_name);
                            CustomTypefaceTextView tvSize = (CustomTypefaceTextView) orderView.findViewById(R.id.tv_size);
                            CustomTypefaceTextView tvColor = (CustomTypefaceTextView) orderView.findViewById(R.id.tv_color);
                            CustomTypefaceTextView tvDiscount = (CustomTypefaceTextView) orderView.findViewById(R.id.tv_discount);
                            CustomTypefaceTextView tvActualPrice = (CustomTypefaceTextView) orderView.findViewById(R.id.tv_actual_price);
                            CustomTypefaceTextView tvDiscountedPrice = (CustomTypefaceTextView) orderView.findViewById(R.id.tv_discounted_price);
                            CustomTypefaceTextView tvProductSerial = (CustomTypefaceTextView) orderView.findViewById(R.id.tv_product_serial);
                            CustomTypefaceTextView tvOrderCancelled = (CustomTypefaceTextView) orderView.findViewById(R.id.tv_order_cancelled);
                            View view = (View) orderView.findViewById(R.id.view_selected_overlay);
                            CustomTypefaceTextView tvQuantity = (CustomTypefaceTextView) orderView.findViewById(R.id.tv_quantity);
                            if (mResGetOrderDetail.getOrderDetails().get(i).getDiscount().equals("0") || mResGetOrderDetail.getOrderDetails().get(i).getDiscount().equals("0")) {
                                tvActualPrice.setVisibility(View.GONE);
                            }
                            tvProductName.setText(mResGetOrderDetail.getOrderDetails().get(i).getName());
                            tvSize.setText(mResGetOrderDetail.getOrderDetails().get(i).getSize());
                            tvColor.setText(mResGetOrderDetail.getOrderDetails().get(i).getColor());
                            tvProductSerial.setText("Product : " + String.valueOf(i + 1));
                            tvQuantity.setText(mResGetOrderDetail.getOrderDetails().get(i).getQuantity());
                            tvActualPrice.setText("Rs. " + mResGetOrderDetail.getOrderDetails().get(i).getPrice());
                            tvActualPrice.setPaintFlags(tvActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            float discountedPrice = calculateDiscountPrice(Float.parseFloat(mResGetOrderDetail.getOrderDetails().get(i).getPrice()), Float.parseFloat(mResGetOrderDetail.getOrderDetails().get(i).getDiscount()));
                            Glide.with(OrderDetailsActivity.this)
                                    .load(mResGetOrderDetail.getOrderDetails().get(i).getImage())
                                    .placeholder(R.drawable.no_image_available)
                                    .into(imageView);
                            tvDiscount.setText(mResGetOrderDetail.getOrderDetails().get(i).getDiscount() + "%");
                            tvDiscountedPrice.setText("Rs. " + String.valueOf(discountedPrice));
                            total = total + (discountedPrice * (Integer.parseInt(mResGetOrderDetail.getOrderDetails().get(i).getQuantity()) == 0 ? 1 : Integer.parseInt(mResGetOrderDetail.getOrderDetails().get(i).getQuantity())));
                            if (Integer.parseInt(mResGetOrderDetail.getOrderDetails().get(i).getStatus()) == ServiceConstants.ORDER_INACTIVE ||
                                    Integer.parseInt(mResGetOrderDetail.getOrderDetails().get(i).getStatus()) == ServiceConstants.ORDER_INACTIVE_BY_ADMIN) {
                                tvOrderCancelled.setVisibility(View.VISIBLE);
                                view.setVisibility(View.VISIBLE);
                            }
                            llOrderDetailLayout.addView(orderView);
                        }
                        tvOrderNumber.setText(getString(R.string.txt_order_num_) + mOrderId);
                        tvNumberOfItems.setText(String.valueOf(count));
                        if (Integer.parseInt(mResGetOrderDetail.getOrderStatus()) == ServiceConstants.ORDER_DELIVERED) {
                            try {
                                Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(mResGetOrderDetail.getDeliveredTime());
                                SimpleDateFormat dtFormat = new SimpleDateFormat("MMM dd, yyyy");
                                String date = dtFormat.format(simpleDateFormat);
                                tvDeliveredOn.setText(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else
                            tvDeliveredOn.setText(R.string.txt_not_delivered);

                        if (Integer.parseInt(mResGetOrderDetail.getOrderStatus()) == ServiceConstants.ORDER_CANCELLED)
                            tvDeliveredOn.setText(R.string.order_cancelled);
                        tvGrandTotal.setText(String.valueOf(total));

                        if (Integer.parseInt(mResGetOrderDetail.getOrderStatus()) == ServiceConstants.ORDER_CONFIRMED || Integer.parseInt(mResGetOrderDetail.getOrderStatus()) == ServiceConstants.ORDER_CANCELLED) {
                            ivOrderStatus.setImageResource(R.drawable.confirm);
                        } else if (Integer.parseInt(mResGetOrderDetail.getOrderStatus()) == ServiceConstants.ORDER_PROCESSING) {
                            ivOrderStatus.setImageResource(R.drawable.packed);
                        } else if (Integer.parseInt(mResGetOrderDetail.getOrderStatus()) == ServiceConstants.ORDER_DISPATCHED) {
                            ivOrderStatus.setImageResource(R.drawable.shipped);
                        } else if (Integer.parseInt(mResGetOrderDetail.getOrderStatus()) == ServiceConstants.ORDER_DELIVERED) {
                            ivOrderStatus.setImageResource(R.drawable.delivered);
                        }


                        try {
                            Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(mResGetOrderDetail.getCreatedTime());
                            SimpleDateFormat dtFormat = new SimpleDateFormat("MMM dd, yyyy");
                            String date = dtFormat.format(simpleDateFormat);
                            tvOrderedOn.setText(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (mResGetOrderDetail.getPaymentBy().equals(AppConstants.PAYMENT_BY_OTHER_OPTIONS)) {
                            tvPaymenyBy.setText(R.string.txt_payment_online);
                        } else if (mResGetOrderDetail.getPaymentBy().equals(AppConstants.PAYMENT_BY_CASH_ON_DELIVERY))
                            tvPaymenyBy.setText(R.string.txt_payment_by_cod);

                        else if (mResGetOrderDetail.getPaymentBy().equals(AppConstants.PAYMENT_BY_WALLET))
                            tvPaymenyBy.setText(R.string.txt_payment_by_wallet);

                        tvAddressName.setText(mResGetOrderDetail.getUserAddressData().getName());
                        tvAddressLocation.setText(mResGetOrderDetail.getUserAddressData().getAddress());
                        tvAddressCity.setText((mResGetOrderDetail.getUserAddressData().getLandmark().equals("") ? "" : mResGetOrderDetail.getUserAddressData().getLandmark() + ", ") + mResGetOrderDetail.getUserAddressData().getCity() + (mResGetOrderDetail.getUserAddressData().getPincode().equals("") ? "" : " - " + mResGetOrderDetail.getUserAddressData().getPincode()));
                        tvAddressState.setText(mResGetOrderDetail.getUserAddressData().getState());


                    } else {
                        ToastUtils.showShortToast(OrderDetailsActivity.this, mResGetOrderDetail.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(OrderDetailsActivity.this, R.string.err_network_connection);
                }
                AppDialog.showProgressDialog(OrderDetailsActivity.this, false);
            }

            @Override
            public void onFailure(Call<ResGetOrderDetail> call, Throwable t) {
                AppDialog.showProgressDialog(OrderDetailsActivity.this, false);
                ToastUtils.showShortToast(OrderDetailsActivity.this, R.string.err_network_connection);
            }
        });

    }

    /**
     * Calculating discounted price by applying discount on actualPrice
     *
     * @param actualPrice
     * @param discount
     * @return
     */
    public float calculateDiscountPrice(float actualPrice, float discount) {
        float discountValue = (actualPrice * discount) / 100;
        return actualPrice - discountValue;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_repeat_order:
                RepeatOrder();
                break;
        }

    }

    /**
     * Repeat order (Adding all the products of the order to MyCart)
     */
    private void RepeatOrder() {
        AppDialog.showProgressDialog(OrderDetailsActivity.this, true);
        ReqRepeatOrder reqRepeatOrder = new ReqRepeatOrder();
        reqRepeatOrder.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqRepeatOrder.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqRepeatOrder.setOrderID(mOrderId);
        reqRepeatOrder.setMethod(MethodFactory.REPEAT_ORDER.getMethod());
        IApiClient client = ApiClient.getApiClient();
        Call<ResBase> call = client.repeatOrder(reqRepeatOrder);
        call.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                AppDialog.showProgressDialog(OrderDetailsActivity.this, false);
                ResBase resBase = response.body();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        ToastUtils.showShortToast(OrderDetailsActivity.this, resBase.getErrstr());
                    } else {
                        ToastUtils.showShortToast(OrderDetailsActivity.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(OrderDetailsActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(OrderDetailsActivity.this, false);
                ToastUtils.showShortToast(OrderDetailsActivity.this, R.string.err_network_connection);
            }
        });
    }
}
