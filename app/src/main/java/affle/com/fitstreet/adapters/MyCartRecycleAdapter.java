package affle.com.fitstreet.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceButton;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.interfaces.AdapterCallback;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.request.ReqMyCart;
import affle.com.fitstreet.models.request.ReqRemoveCart;
import affle.com.fitstreet.models.request.ReqSetFavouriteProduct;
import affle.com.fitstreet.models.request.ReqUpdateCart;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResMyCart;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.AppSharedPreference;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.activities.HomeActivity;
import affle.com.fitstreet.ui.fragments.MyCartFragmentNew;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by akash on 28/9/16.
 */
public class MyCartRecycleAdapter extends RecyclerView.Adapter<MyCartRecycleAdapter.ViewHolder> implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnTouchListener {
    List<ResMyCart.CartDatum> list;
    Activity mContext;
    MyCartFragmentNew myCartFragmentNew;
    AdapterCallback mAdapterCallback;
    public HashMap<Integer, HashMap<String, String>> spinnerSelectedItems = new HashMap<>();
    private List<String> mMyCartSizeList;
    private List<String> mMyCartQuantityList;
    private HashMap<String, String> spinnerMap;
    float TotalDiscountNew = 0;
    float TotalAmountNew = 0;
    float TotalPayableNew = 0;
    float DiscountRowWise = 0;
    boolean userSelect = false;

    public MyCartRecycleAdapter(Context context, List<ResMyCart.CartDatum> list, AdapterCallback adapterCallback, MyCartFragmentNew myCartFragmentNew) {
        this.mAdapterCallback = adapterCallback;
        this.list = list;
        this.mContext = (Activity) context;
        this.myCartFragmentNew = myCartFragmentNew;
        if (list.size() > 0)
            getTotalAmount();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_cart, null);
        return new ViewHolder(layoutView);

    }

    private void getTotalAmount() {

        for (int i = 0; i < list.size(); i++) {
            int quantity = Integer.parseInt(list.get(i).getSelectedQuantity());
            TotalAmountNew = TotalAmountNew + ((Float.parseFloat(list.get(i).getPrice())) * quantity);
            DiscountRowWise = (Float.parseFloat(list.get(i).getPrice()) - calculateDiscountPrice(Float.parseFloat(list.get(i).getPrice()), Float.parseFloat(list.get(i).getDiscount()))) * quantity;
            TotalDiscountNew = TotalDiscountNew + DiscountRowWise;
        }
        TotalPayableNew = TotalAmountNew - TotalDiscountNew;
        mAdapterCallback.onMethodCallback(list.size(), TotalPayableNew, TotalAmountNew, TotalDiscountNew);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        spinnerMap = new HashMap<>();
        holder.myCartProductName.setText(list.get(position).getName());
        holder.tvProductActualPrice.setText("Rs. " + list.get(position).getPrice());
        holder.tvProductActualPrice.setPaintFlags(holder.tvProductActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        mMyCartSizeList = list.get(position).getSize();
        CartSizeQuantityAdapter cartSizeAdapter = new CartSizeQuantityAdapter(mContext, R.id.tv_distance_unit, mMyCartSizeList);
        holder.spnCartSize.setAdapter(cartSizeAdapter);
        int quantity = Integer.parseInt(list.get(position).getQuantity());
        mMyCartQuantityList = new ArrayList<>();
        if (quantity > 0) {
            if (quantity > 5) {
                for (int k = 1; k <= 5; k++)
                    mMyCartQuantityList.add(String.valueOf(k));
                CartSizeQuantityAdapter cartQuantityAdapter = new CartSizeQuantityAdapter(mContext, R.id.tv_distance_unit, mMyCartQuantityList);
                holder.spnCartQuantity.setAdapter(cartQuantityAdapter);
                holder.tvQuantity.setVisibility(View.GONE);
            } else {
                for (int k = 1; k <= quantity; k++)
                    mMyCartQuantityList.add(String.valueOf(k));
                CartSizeQuantityAdapter cartQuantityAdapter = new CartSizeQuantityAdapter(mContext, R.id.tv_distance_unit, mMyCartQuantityList);
                holder.spnCartQuantity.setAdapter(cartQuantityAdapter);
                holder.tvQuantity.setVisibility(View.GONE);
            }
        } else {
            holder.spnCartQuantity.setVisibility(View.GONE);
            holder.tvQuantity.setVisibility(View.VISIBLE);
        }
        holder.tvProductDiscount.setText(list.get(position).getDiscount() + "% off");
        float discountedPrice = calculateDiscountPrice(Float.parseFloat(list.get(position).getPrice()), Float.parseFloat(list.get(position).getDiscount()));
//        totalBalance = totalBalance + discountedPrice;
//        totalDiscount = totalDiscount + (Float.parseFloat(list.get(position).getPrice()) - discountedPrice);
//        totalAmount = totalAmount + Float.parseFloat(list.get(position).getPrice());
        holder.tvDiscountedPrice.setText("Rs. " + String.valueOf(discountedPrice));
        if (!list.get(position).getSelectedSize().toUpperCase().equals("") && !list.get(position).getSelectedSize().toUpperCase().equals("0")) {
            holder.spnCartSize.setSelection((mMyCartSizeList.indexOf(list.get(position).getSelectedSize())));
            spinnerMap.put("spn_cart_size", list.get(position).getSelectedSize());
        } else {
            holder.spnCartSize.setSelection(0);
            spinnerMap.put("spn_cart_size", "M");
        }
        if (!list.get(position).getSelectedQuantity().toUpperCase().equals("") && !list.get(position).getSelectedQuantity().toUpperCase().equals("0")) {
            holder.spnCartQuantity.setSelection((mMyCartQuantityList.indexOf(list.get(position).getSelectedQuantity())));
            spinnerMap.put("spn_cart_quantity", list.get(position).getSelectedSize());
        } else {
            holder.spnCartQuantity.setSelection(0);
            spinnerMap.put("spn_cart_quantity", "1");
        }

        if (mMyCartSizeList.size() == 0) {
            holder.spnCartSize.setVisibility(View.INVISIBLE);
        } else {
            holder.spnCartSize.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext)
                .load(list.get(position).getImage())
                .placeholder(R.drawable.no_image_available)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.ivProductImageCart.setImageResource(R.drawable.no_image_available);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.ivProductImageCart);

        holder.btnRemoveCart.setOnClickListener(this);
        holder.btnMoveToFavourites.setOnClickListener(this);
        holder.btnRemoveCart.setTag(position);
        holder.btnMoveToFavourites.setTag(position);
        holder.spnCartQuantity.setTag(R.id.tag_position_cart, position);
        holder.spnCartSize.setTag(R.id.tag_position_cart, position);
        holder.spnCartQuantity.setTag(R.id.tag_cart_quantity, Integer.parseInt(list.get(position).getSelectedQuantity()));
        spinnerSelectedItems.put(position, spinnerMap);
        myCartFragmentNew.setSpinnerSelectedItems(spinnerSelectedItems);
        holder.spnCartQuantity.setOnItemSelectedListener(this);
        holder.spnCartSize.setOnItemSelectedListener(this);
        holder.spnCartQuantity.setOnTouchListener(this);
        holder.spnCartSize.setOnTouchListener(this);
//        mAdapterCallback.onMethodCallback(list.size(), totalBalance, totalAmount, totalDiscount);

        if (list.get(position).getDiscount().equals("0") || list.get(position).getDiscount().equals("0")) {
            holder.tvProductActualPrice.setVisibility(View.GONE);
        }

    }

    public float calculateDiscountPrice(float actualPrice, float discount) {
        float discountValue = (actualPrice * discount) / 100;
        return actualPrice - discountValue;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_remove_cart:
                removeFromCart((int) view.getTag(), false);
                break;
            case R.id.btn_move_to_favourites:
                moveToFavourites((int) view.getTag());
                break;
        }
    }

    private void moveToFavourites(final int pos) {
        AppDialog.showProgressDialog(mContext, true);
        final IApiClient client = ApiClient.getApiClient();
        final ReqSetFavouriteProduct reqSetFavouriteProduct = new ReqSetFavouriteProduct();
        reqSetFavouriteProduct.setProductID(list.get(pos).getProductID());
        reqSetFavouriteProduct.setUserID(AppSharedPreference.getInstance(mContext).getString(PreferenceKeys.KEY_USER_ID, ""));
        reqSetFavouriteProduct.setMethod(MethodFactory.FAVOURITE_PRODUCT.getMethod());
        reqSetFavouriteProduct.setServiceKey(AppSharedPreference.getInstance(mContext).getString(PreferenceKeys.KEY_SERVICE_KEY, ""));
        Call<ResBase> resBaseCall = client.setFavouriteProduct(reqSetFavouriteProduct);
        resBaseCall.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                ResBase resBase = response.body();
                AppDialog.showProgressDialog(mContext, false);
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        ToastUtils.showShortToast(mContext, resBase.getErrstr());
                        removeFromCart(pos, true);
                        myCartFragmentNew.setToolbarFavouriteCount();
                    } else {
                        ToastUtils.showShortToast(mContext, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(mContext, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(mContext, false);
                ToastUtils.showShortToast(mContext, R.string.err_network_connection);
            }
        });
    }

    private void removeFromCart(final int pos, final boolean isFromFavourite) {
        if (!isFromFavourite)
            AppDialog.showProgressDialog(mContext, true);
        final IApiClient client = ApiClient.getApiClient();
        ReqRemoveCart reqRemoveCart = new ReqRemoveCart();
        reqRemoveCart.setUserID(AppSharedPreference.getInstance(mContext).getString(PreferenceKeys.KEY_USER_ID, ""));
        reqRemoveCart.setServiceKey(AppSharedPreference.getInstance(mContext).getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqRemoveCart.setCartID((String.valueOf(list.get(pos).getCartID())));
        reqRemoveCart.setMethod(MethodFactory.REMOVE_CART.getMethod());
        Call<ResBase> call = client.removeCart(reqRemoveCart);
        call.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                AppDialog.showProgressDialog(mContext, false);
                ResBase resBase = response.body();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        if (!isFromFavourite)
                            ToastUtils.showShortToast(mContext, R.string.cart_remove_successfully);
                        ReqMyCart reqMyCart = new ReqMyCart();
                        reqMyCart.setServiceKey(AppSharedPreference.getInstance(mContext).getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
                        reqMyCart.setMethod(MethodFactory.GET_MY_CART.getMethod());
                        reqMyCart.setUserID(AppSharedPreference.getInstance(mContext).getString(PreferenceKeys.KEY_USER_ID, ""));
                        Call<ResMyCart> cartCall = client.getCartDetails(reqMyCart);
                        cartCall.enqueue(new Callback<ResMyCart>() {
                            @Override
                            public void onResponse(Call<ResMyCart> call, Response<ResMyCart> response) {
                                ResMyCart resMyCart = response.body();
                                list.clear();
                                list.addAll(resMyCart.getCartData());
                                TotalPayableNew = 0;
                                TotalAmountNew = 0;
                                TotalDiscountNew = 0;
                                notifyDataSetChanged();
                                getTotalAmount();
                                spinnerSelectedItems.remove(pos);
                                myCartFragmentNew.setData(resMyCart);
                                myCartFragmentNew.setVisibilityEmptyCart(resMyCart);
                                myCartFragmentNew.setSpinnerSelectedItems(spinnerSelectedItems);
                            }

                            @Override
                            public void onFailure(Call<ResMyCart> call, Throwable t) {
                                AppDialog.showProgressDialog(mContext, false);
                                ToastUtils.showShortToast(mContext, R.string.err_network_connection);
                            }
                        });

                    } else {
                        ToastUtils.showShortToast(mContext, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(mContext, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(mContext, false);
                ToastUtils.showShortToast(mContext, R.string.err_network_connection);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spinner = (Spinner) adapterView;
        int pos = (int) spinner.getTag(R.id.tag_position_cart);
        String updatedQuantity = "", updatedSize = "";
        if (spinner.getId() == R.id.spn_my_cart_quantity) {
            ((CartSizeQuantityAdapter) spinner.getAdapter()).setCheckedPos(i);
            spinnerSelectedItems.get(pos).put("spn_cart_quantity", "" + spinner.getSelectedItem());
            int quantity = (int) spinner.getTag(R.id.tag_cart_quantity);
            if (userSelect) {
                float price = Float.parseFloat(list.get(pos).getPrice());
                float discount = Float.parseFloat(list.get(pos).getDiscount());
                float discountAmount = price - calculateDiscountPrice(price, discount);
                float discountedPrice = calculateDiscountPrice(price, discount);
                int selectedQuantity = Integer.parseInt("" + spinner.getSelectedItem());
                spinner.setTag(R.id.tag_cart_quantity, selectedQuantity);
                int dif = (selectedQuantity - quantity);
                price = price * dif;
                discountedPrice = discountedPrice * dif;
                discountAmount = discountAmount * dif;
                TotalPayableNew = TotalPayableNew + discountedPrice;
                TotalAmountNew = TotalAmountNew + price;
                TotalDiscountNew = TotalDiscountNew + discountAmount;
            }
//                mAdapterCallback.onMethodCallback(list.size(), TotalPayableNew, TotalAmountNew, TotalDiscountNew);
//            totalBalance = totalBalance + discountedPrice;
//            totalAmount = totalAmount + price;
//            totalDiscount = totalDiscount + discountAmount;
            updatedQuantity = (String) spinner.getSelectedItem();
//            mAdapterCallback.onMethodCallback(list.size(), totalBalance, totalAmount, totalDiscount);
            mAdapterCallback.onMethodCallback(list.size(), TotalPayableNew, TotalAmountNew, TotalDiscountNew);

        } else if (spinner.getId() == R.id.spn_my_cart_size) {
            ((CartSizeQuantityAdapter) spinner.getAdapter()).setCheckedPos(i);
            spinnerSelectedItems.get(pos).put("spn_cart_size", "" + spinner.getSelectedItem());
            updatedSize = (String) spinner.getSelectedItem();

        }
        // if (!isSpinnerMyCartQuantityInitialized && !isSpinnerMyCartSizeInitialized)
        if (userSelect) {
            UpdateCartDetails(list.get(pos).getCartID(), updatedQuantity, updatedSize);
            userSelect = false;
        }
        myCartFragmentNew.setSpinnerSelectedItems(spinnerSelectedItems);


    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        userSelect = true;
        return false;
    }

    private void UpdateCartDetails(String cartId, String quantity, String size) {
        //AppDialog.showProgressDialog(mContext,true);
        ReqUpdateCart reqUpdateCart = new ReqUpdateCart();
        reqUpdateCart.setCartID(cartId);
        if (!quantity.equals(""))
            reqUpdateCart.setQuantity(quantity);
        if (!size.equals(""))
            reqUpdateCart.setSize(size);
        reqUpdateCart.setMethod(MethodFactory.UPDATE_CART_DETAILS.getMethod());
        reqUpdateCart.setServiceKey(AppSharedPreference.getInstance(mContext).getString(PreferenceKeys.KEY_SERVICE_KEY, PreferenceKeys.KEY_SERVICE_KEY));
        reqUpdateCart.setUserID(AppSharedPreference.getInstance(mContext).getString(PreferenceKeys.KEY_USER_ID, ""));
        IApiClient client = ApiClient.getApiClient();
        Call<ResBase> call = client.updateCartDetails(reqUpdateCart);
        call.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                //AppDialog.showProgressDialog(mContext,false);
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(mContext, false);
            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private Spinner spnCartSize;
        private Spinner spnCartQuantity;
        private CustomTypefaceTextView tvDiscountedPrice;
        private CustomTypefaceTextView tvProductActualPrice;
        private CustomTypefaceTextView tvProductDiscount;
        private CustomTypefaceTextView myCartProductName;
        private ImageView ivProductImageCart;
        private CustomTypefaceTextView tvQuantity;
        private CustomTypefaceButton btnRemoveCart;
        private CustomTypefaceButton btnMoveToFavourites;

        public ViewHolder(View itemView) {
            super(itemView);
            spnCartSize = (Spinner) itemView.findViewById(R.id.spn_my_cart_size);
            spnCartQuantity = (Spinner) itemView.findViewById(R.id.spn_my_cart_quantity);
            tvDiscountedPrice = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_discounted_price);
            tvProductActualPrice = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_product_actual_price);
            tvProductDiscount = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_product_discount);
            myCartProductName = (CustomTypefaceTextView) itemView.findViewById(R.id.my_cart_product_name);
            ivProductImageCart = (ImageView) itemView.findViewById(R.id.iv_product_image_cart);
            tvQuantity = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_my_cart_quantity);
            btnRemoveCart = (CustomTypefaceButton) itemView.findViewById(R.id.btn_remove_cart);
            btnMoveToFavourites = (CustomTypefaceButton) itemView.findViewById(R.id.btn_move_to_favourites);

        }
    }
}
