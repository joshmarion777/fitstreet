package affle.com.fitstreet.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.MyCartRecycleAdapter;
import affle.com.fitstreet.customviews.CustomTypefaceButton;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.interfaces.AdapterCallback;
import affle.com.fitstreet.models.request.ReqMyCart;
import affle.com.fitstreet.models.request.ReqPlaceOrder;
import affle.com.fitstreet.models.response.ResMyCart;
import affle.com.fitstreet.models.response.ResPlaceOrder;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.activities.CheckoutActivity;
import affle.com.fitstreet.ui.activities.FavouritesActivity;
import affle.com.fitstreet.ui.activities.HomeActivity;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akash on 9/9/16.
 */
public class MyCartFragmentNew extends BaseFragment implements AdapterCallback {


    @BindView(R.id.tv_empty_cart_1)
    CustomTypefaceTextView tvEmptyCart1;
    @BindView(R.id.tv_empty_cart_2)
    CustomTypefaceTextView tvEmptyCart2;
    @BindView(R.id.tv_empty_cart_3)
    CustomTypefaceTextView tvEmptyCart3;
    @BindView(R.id.iv_empty_cart)
    ImageView ivEmptyCart;
    @BindView(R.id.btn_start_shopping)
    CustomTypefaceButton btnStartShopping;
    @BindView(R.id.btn_add_from_favourites)
    CustomTypefaceButton btnAddFromFavourites;
    @BindView(R.id.rl_my_cart_empty_layout)
    RelativeLayout rlMyCartEmptyLayout;
    @BindView(R.id.btn_place_order)
    CustomTypefaceButton btnPlaceOrder;
    @BindView(R.id.rl_btn_place_order)
    LinearLayout rlBtnPlaceOrder;
    @BindView(R.id.my_cart_items)
    CustomTypefaceTextView myCartItems;
    @BindView(R.id.my_cart_total_amount)
    CustomTypefaceTextView myCartTotalAmount;
    @BindView(R.id.rl_top_layout)
    RelativeLayout rlTopLayout;
    @BindView(R.id.rv_my_cart_products_layout)
    RecyclerView rvMyCartProductsLayout;
    @BindView(R.id.tv_add_more)
    CustomTypefaceTextView tvAddMore;
    @BindView(R.id.tv_price_detail_cart_total)
    CustomTypefaceTextView tvPriceDetailCartTotal;
    @BindView(R.id.tv_price_detail_cart_total_amount)
    CustomTypefaceTextView tvPriceDetailCartTotalAmount;
    @BindView(R.id.tv_price_detail_discount_total)
    CustomTypefaceTextView tvPriceDetailDiscountTotal;
    @BindView(R.id.tv_price_detail_discount_total_amount)
    CustomTypefaceTextView tvPriceDetailDiscountTotalAmount;
    @BindView(R.id.tv_price_detail_cart_sub_total)
    CustomTypefaceTextView tvPriceDetailCartSubTotal;
    @BindView(R.id.tv_price_detail_cart_sub_total_amount)
    CustomTypefaceTextView tvPriceDetailCartSubTotalAmount;
    @BindView(R.id.tv_price_detail_delivery)
    CustomTypefaceTextView tvPriceDetailDelivery;
    @BindView(R.id.tv_price_detail_delivery_amount)
    CustomTypefaceTextView tvPriceDetailDeliveryAmount;
    @BindView(R.id.tv_price_detail_total_payable)
    CustomTypefaceTextView tvPriceDetailTotalPayable;
    @BindView(R.id.tv_price_detail_total_payable_amount)
    CustomTypefaceTextView tvPriceDetailTotalPayableAmount;
    @BindView(R.id.scroll_cart_items)
    ScrollView scrollCartItems;
    @BindView(R.id.rl_my_cart_product_layout_parent)
    RelativeLayout rlMyCartProductLayoutParent;
    @BindView(R.id.tv_price_details)
    CustomTypefaceTextView tvPriceDetails;
    @BindView(R.id.rl_price_details)
    RelativeLayout rlPriceDetails;
    public HashMap<Integer, HashMap<String, String>> spinnerSelectedItems = new HashMap<>();
    List<ResMyCart.CartDatum> mCartList = new ArrayList<>();
    MyCartRecycleAdapter myCartRecycleAdapter;
    private ArrayList<Integer> mCartIds = new ArrayList<>();
    private float totalBalance = 0;
    private TextView tvToolbarFavouriteCount;
    private ResMyCart resMyCart;

    public void setSpinnerSelectedItems(HashMap<Integer, HashMap<String, String>> spinnerSelectedItems) {
        this.spinnerSelectedItems = spinnerSelectedItems;
    }

    @Override
    protected void initViews() {
        if (mActivity instanceof HomeActivity) {
            ((HomeActivity) mActivity).setActionBarTitle(R.string.txt_my_cart);
            ((HomeActivity) mActivity).setVisibilityBottomLayout(false);
        }
        rvMyCartProductsLayout.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        getCartDetails();
    }

    @Override
    protected void initVariables() {
        btnPlaceOrder.setOnClickListener(this);
        tvAddMore.setOnClickListener(this);
        btnAddFromFavourites.setOnClickListener(this);
        btnStartShopping.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_from_favourites:
                if (mActivity instanceof HomeActivity) {
                    ((HomeActivity) mActivity).showFavourites();
                    ((HomeActivity) mActivity).unCheckNavigationSelectedItem();
                } else {
                    Intent intent = new Intent(mActivity, FavouritesActivity.class);
                    mActivity.startActivityForResult(intent, AppConstants.RC_FROM_MY_CART_ACTIVITY_TO_FAVOURITES_ACTIVITY);


                }
                break;
            case R.id.btn_start_shopping:
                if (mActivity instanceof HomeActivity) {
                    ((HomeActivity) mActivity).startFsStoreFragment();
                    ((HomeActivity) mActivity).setVisibilityBottomLayout(true);
                } else {

                    Intent intent = new Intent(mActivity, HomeActivity.class);
                    intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_MY_CART_ACTIVITY);
                    mActivity.startActivity(intent);

                }
                break;
            case R.id.tv_add_more:
                if (mActivity instanceof HomeActivity) {
                    ((HomeActivity) mActivity).showFavourites();
                    ((HomeActivity) mActivity).unCheckNavigationSelectedItem();
                } else {
                    Intent intent = new Intent(mActivity, FavouritesActivity.class);
                    super.startActivityForResult(intent, AppConstants.RC_FROM_MY_CART_ACTIVITY_TO_FAVOURITES_ACTIVITY);

                }
                break;
            case R.id.btn_place_order:
                if (resMyCart != null && !resMyCart.getCartData().isEmpty()) {
                    if (NetworkConnection.isNetworkConnected(mActivity))
                        placeOrder();
                }
                break;
        }

    }

    public void setToolbarFavouriteCount() {
        if (!tvToolbarFavouriteCount.getText().equals(""))
            tvToolbarFavouriteCount.setText(String.valueOf(Integer.parseInt(tvToolbarFavouriteCount.getText().toString()) + 1));
    }

    public void setData(ResMyCart resMyCart) {
        this.resMyCart = null;
        this.resMyCart = resMyCart;
    }

    /**
     * Placing order (Redirecting to Checkout Activity)
     */
    private void placeOrder() {
        boolean available = false;
        for (int i = 0; i < resMyCart.getCartData().size(); i++) {
            if (Integer.parseInt(resMyCart.getCartData().get(i).getQuantity()) == 0) {
                available = false;
                break;
            } else {
                available = true;
            }
        }
        //  if (!mMyCartQuantityList.isEmpty()) {
        if (available) {
            AppDialog.showProgressDialog(mActivity, true);
            IApiClient client = ApiClient.getApiClient();
            final ReqPlaceOrder reqPlaceOrder = new ReqPlaceOrder();
            reqPlaceOrder.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
            reqPlaceOrder.setMethod(MethodFactory.PLACE_ORDER.getMethod());
            reqPlaceOrder.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
            List<ReqPlaceOrder.CartDatum> cartUpdateDataList = new ArrayList<>();
            for (int i = 0; i < resMyCart.getCartData().size(); i++) {
                ReqPlaceOrder.CartDatum cartData = new ReqPlaceOrder.CartDatum();
                cartData.setCartID(resMyCart.getCartData().get(i).getCartID());
                // cartData.setSize(resMyCart.getCartData().get(i).getSelectedSize());
                cartData.setSize(spinnerSelectedItems.get(i).get("spn_cart_size"));
                //cartData.setSize(spnCartSize.getSelectedItem().toString());
                //cartData.setQuantity("1");
                cartData.setQuantity(spinnerSelectedItems.get(i).get("spn_cart_quantity"));
                cartUpdateDataList.add(cartData);
            }
            reqPlaceOrder.setCartData(cartUpdateDataList);
            Call<ResPlaceOrder> call = client.placeOrder(reqPlaceOrder);
            call.enqueue(new Callback<ResPlaceOrder>() {
                @Override
                public void onResponse(Call<ResPlaceOrder> call, Response<ResPlaceOrder> response) {
                    AppDialog.showProgressDialog(mActivity, false);
                    ResPlaceOrder resPlaceOrder;
                    resPlaceOrder = response.body();
                    if (resPlaceOrder != null) {
                        if (resPlaceOrder.getSuccess() == ServiceConstants.SUCCESS) {
                            Intent intent = new Intent(mActivity, CheckoutActivity.class);
                            if (resPlaceOrder.getErrorCode() == 2) {
                                intent.putExtra("addressAvailability", false);
                            } else {
                                intent.putExtra("addressAvailability", true);
                                intent.putExtra("name", resPlaceOrder.getUserAddressData().get(0).getName());
                                intent.putExtra("address", resPlaceOrder.getUserAddressData().get(0).getAddress());
                                intent.putExtra("city", resPlaceOrder.getUserAddressData().get(0).getCity());
                                intent.putExtra("phone", resPlaceOrder.getUserAddressData().get(0).getPhone());
                                intent.putExtra("state", resPlaceOrder.getUserAddressData().get(0).getState());
                                intent.putExtra("pincode", resPlaceOrder.getUserAddressData().get(0).getPincode());
                                intent.putExtra("landmark", resPlaceOrder.getUserAddressData().get(0).getLandmark());
                                intent.putExtra("addId", resPlaceOrder.getUserAddressData().get(0).getUserAddID());
                            }
                            intent.putExtra("productsCount", "" + resMyCart.getCartData().size());
                            intent.putExtra("totalPayableAmount", "" + totalBalance);
                            intent.putExtra("walletAmount", resPlaceOrder.getWalletAmt());
                            for (int i = 0; i < resMyCart.getCartData().size(); i++) {
                                mCartIds.add(Integer.parseInt(resMyCart.getCartData().get(i).getCartID()));
                            }
                            intent.putIntegerArrayListExtra("cartIds", mCartIds);
                            mActivity.startActivity(intent);
                        } else {
                            ToastUtils.showShortToast(mActivity, resPlaceOrder.getErrstr());
                        }
                    } else {
                        ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                    }
                }

                @Override
                public void onFailure(Call<ResPlaceOrder> call, Throwable t) {
                    AppDialog.showProgressDialog(mActivity, false);
                    ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
                }
            });
        } else {
            ToastUtils.showShortToast(mActivity, mActivity.getString(R.string.txt_sold_out));
        }
    }

    /**
     * Getting cart details from server
     */
    private void getCartDetails() {
        AppDialog.showProgressDialog(mActivity, true);
        IApiClient client = ApiClient.getApiClient();
        ReqMyCart reqMyCart = new ReqMyCart();
        reqMyCart.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqMyCart.setMethod(MethodFactory.GET_MY_CART.getMethod());
        reqMyCart.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        Call<ResMyCart> call = client.getCartDetails(reqMyCart);
        call.enqueue(new Callback<ResMyCart>() {
            @Override
            public void onResponse(Call<ResMyCart> call, Response<ResMyCart> response) {
                AppDialog.showProgressDialog(mActivity, false);
                resMyCart = response.body();
                mCartList.clear();
                mCartList.addAll(resMyCart.getCartData());
                myCartRecycleAdapter = new MyCartRecycleAdapter(mActivity, mCartList, MyCartFragmentNew.this, MyCartFragmentNew.this);
                if (mCartList.isEmpty()) {
                    setVisibilityEmptyCart(resMyCart);
                } else {
                    tvPriceDetails.setVisibility(View.VISIBLE);
                    rvMyCartProductsLayout.setVisibility(View.VISIBLE);
                    tvAddMore.setVisibility(View.VISIBLE);
                    rlPriceDetails.setVisibility(View.VISIBLE);
                }
                rvMyCartProductsLayout.setAdapter(myCartRecycleAdapter);
                tvToolbarFavouriteCount = (TextView) mActivity.findViewById(R.id.tv_toolbar_favourite_count);
                tvToolbarFavouriteCount.setText(resMyCart.getFavCount());

            }

            @Override
            public void onFailure(Call<ResMyCart> call, Throwable t) {
                AppDialog.showProgressDialog(mActivity, false);
                ToastUtils.showShortToast(mActivity, R.string.err_network_connection);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (NetworkConnection.isNetworkConnected(mActivity)) {
            getCartDetails();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart_new, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setVisibilityEmptyCart(ResMyCart resMyCart) {
        if (resMyCart.getCartData().isEmpty()) {
            rlMyCartProductLayoutParent.setVisibility(View.GONE);
            rlMyCartEmptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMethodCallback(int size, float totalBalance, float totalAmount, float totalDiscount) {
        this.totalBalance = totalBalance;
        myCartItems.setText(String.valueOf("ITEMS: " + resMyCart.getCartData().size()));
        myCartTotalAmount.setText("TOTAL: Rs. " + String.valueOf(totalBalance));
        tvPriceDetailCartTotalAmount.setText(String.valueOf(totalAmount));
        tvPriceDetailDiscountTotalAmount.setText(String.valueOf(totalDiscount));
        tvPriceDetailCartSubTotalAmount.setText(String.valueOf(totalBalance));
        tvPriceDetailDeliveryAmount.setText("Free");
        tvPriceDetailTotalPayableAmount.setText(String.valueOf(totalBalance));
    }
}
