package affle.com.fitstreet.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import affle.com.fitstreet.R;
import affle.com.fitstreet.adapters.FSProductDetailViewPagerAdapter;
import affle.com.fitstreet.customviews.CustomTypefaceButton;
import affle.com.fitstreet.customviews.CustomTypefaceEditText;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.request.ReqAddToCart;
import affle.com.fitstreet.models.request.ReqDeliveryStatus;
import affle.com.fitstreet.models.request.ReqProductDetail;
import affle.com.fitstreet.models.request.ReqSetFavouriteProduct;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResDeliveryStatus;
import affle.com.fitstreet.models.response.ResGetProductDetail;
import affle.com.fitstreet.models.response.ResProductDetailData;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.AppSharedPreference;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.ui.fragments.SizeChartDialogFragement;
import affle.com.fitstreet.ui.fragments.ViewPagerDialog;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.CameraAndGalleryUtils;
import affle.com.fitstreet.utils.PermissionUtils;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FsStoreProductDetail extends BaseActivity implements ViewPager.OnPageChangeListener, IOnItemClickListener {
    @BindView(R.id.vp_fs_store_product)
    ViewPager vpFsStoreProduct;
    @BindView(R.id.tv_sold_out)
    CustomTypefaceTextView tvSoldOut;
    @BindView(R.id.tv_title)
    CustomTypefaceTextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_pager_indicator)
    LinearLayout llPagerIndicator;
    @BindView(R.id.btn_S)
    CustomTypefaceButton btnS;
    @BindView(R.id.btn_M)
    CustomTypefaceButton btnM;
    @BindView(R.id.btn_L)
    CustomTypefaceButton btnL;
    @BindView(R.id.btn_XL)
    CustomTypefaceButton btnXL;
    @BindView(R.id.btn_XXL)
    CustomTypefaceButton btnXXL;
    @BindView(R.id.tv_discount_value)
    CustomTypefaceTextView tvDiscountCashback;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_toolbar_filter)
    ImageView ivToolbarFilter;
    @BindView(R.id.iv_toolbar_filter_active)
    ImageView ivToolbarFilterActive;
    @BindView(R.id.rl_toolbar_filter)
    RelativeLayout rlToolbarFilter;
    @BindView(R.id.iv_toolbar_favourite)
    ImageView ivToolbarFavourite;
    @BindView(R.id.tv_toolbar_favourite_count)
    CustomTypefaceTextView tvToolbarFavouriteCount;
    @BindView(R.id.rl_toolbar_favourite)
    RelativeLayout rlToolbarFavourite;
    @BindView(R.id.iv_share_product)
    ImageView ivShareProduct;
    @BindView(R.id.iv_set_favourite)
    ImageView ivSetFavourite;
    @BindView(R.id.tv_product_name)
    CustomTypefaceTextView tvProductName;
    @BindView(R.id.tv_product_actual_price)
    CustomTypefaceTextView tvProductActualPrice;
    @BindView(R.id.tv_product_discount)
    CustomTypefaceTextView tvProductDiscount;
    @BindView(R.id.tv_discounted_price)
    CustomTypefaceTextView tvDiscountedPrice;
    @BindView(R.id.ll_product_name_layout)
    LinearLayout llProductNameLayout;
    @BindView(R.id.tv_select_size)
    CustomTypefaceTextView tvSelectSize;
    @BindView(R.id.tv_size_chart)
    CustomTypefaceTextView tvSizeChart;
    @BindView(R.id.tv_txt_points_to_redeem)
    CustomTypefaceTextView tvTxtPointsToRedeem;
    @BindView(R.id.tv_redeem_points)
    CustomTypefaceTextView tvRedeemPoints;
    @BindView(R.id.tv_product_desc)
    CustomTypefaceTextView tvProductDesc;
    @BindView(R.id.btn_know_more)
    CustomTypefaceButton btnKnowMore;
    @BindView(R.id.SCROLLER_ID)
    ScrollView SCROLLERID;
    @BindView(R.id.tv_material)
    CustomTypefaceTextView tvMaterial;
    @BindView(R.id.tv_sleeve_text)
    CustomTypefaceTextView tvSleeveText;
    @BindView(R.id.tv_sleeve_value)
    CustomTypefaceTextView tvSleeveValue;
    @BindView(R.id.tv_color_text)
    CustomTypefaceTextView tvColorText;
    @BindView(R.id.tv_color_value)
    CustomTypefaceTextView tvColorValue;
    @BindView(R.id.tv_care_for_text)
    CustomTypefaceTextView tvCareForText;
    @BindView(R.id.tv_care_for_value)
    CustomTypefaceTextView tvCareForValue;
    @BindView(R.id.tv_fabric_text)
    CustomTypefaceTextView tvFabricText;
    @BindView(R.id.tv_fabric_value)
    CustomTypefaceTextView tvFabricValue;
    @BindView(R.id.tv_fit_text)
    CustomTypefaceTextView tvFitText;
    @BindView(R.id.tv_fit_value)
    CustomTypefaceTextView tvFitValue;
    @BindView(R.id.tv_suitable_for_text)
    CustomTypefaceTextView tvSuitableForText;
    @BindView(R.id.tv_suitable_for_value)
    CustomTypefaceTextView tvSuitableForValue;
    @BindView(R.id.tv_delivery_text)
    CustomTypefaceTextView tvDeliveryText;
    @BindView(R.id.tv_check_pin_code_text)
    CustomTypefaceTextView tvCheckPinCodeText;
    @BindView(R.id.tv_delivery_value)
    CustomTypefaceTextView tvDeliveryValue;
    @BindView(R.id.et_pin_code)
    CustomTypefaceEditText etPinCode;
    @BindView(R.id.ll_check_pin_code)
    RelativeLayout llCheckPinCode;
    @BindView(R.id.tv_return_value_days)
    TextView tvReturnValueDays;
    @BindView(R.id.btn_buy_now)
    CustomTypefaceButton btnBuyNow;
    @BindView(R.id.rl_btn_buy)
    LinearLayout rlBtnBuy;
    @BindView(R.id.tv_Check_pin_code)
    CustomTypefaceTextView tvCheckPinCode;
    @BindView(R.id.iv_toolbar_mycart)
    ImageView ivToolbarMycart;
    @BindView(R.id.tv_toolbar_cart_count)
    CustomTypefaceTextView tvToolbarCartCount;
    @BindView(R.id.rl_toolbar_mycart)
    RelativeLayout rlToolbarMycart;
    private boolean mBtnSelectedSize = false;
    private ResDeliveryStatus mResDeliveryStatus;
    private Bitmap mBitmap;
    private String mShareBody;
    ResGetProductDetail mResGetProductDetail;
    private List<ImageView> mImageViewsList;
    private String mProductId;
    private FSProductDetailViewPagerAdapter mFsProductDetailViewPagerAdapter;
    private ArrayList<String> mProductImageList = new ArrayList();
    private ResProductDetailData mProductDetailData;
    private int mProductPosition;
    private int mFsPoints = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fs_store_product_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            mProductId = intent.getExtras().getString("productId");
            mProductPosition = intent.getExtras().getInt("productPosition");
        }
        super.initData();

    }

    @Override
    protected void initViews() {
        tvTitle.setText(R.string.txt_title_fs_store_product_detail);
        vpFsStoreProduct.setOffscreenPageLimit(4);
        mImageViewsList = new ArrayList<>();
        mFsProductDetailViewPagerAdapter.setOnItemClickListener(this);
        rlToolbarMycart.setVisibility(View.VISIBLE);


    }

    @Override
    protected void initVariables() {
        vpFsStoreProduct.setOnPageChangeListener(this);
        ivBack.setOnClickListener(this);
        btnS.setOnClickListener(this);
        btnL.setOnClickListener(this);
        btnM.setOnClickListener(this);
        btnXXL.setOnClickListener(this);
        btnXL.setOnClickListener(this);
        tvSizeChart.setOnClickListener(this);
        btnKnowMore.setOnClickListener(this);
        tvCheckPinCode.setOnClickListener(this);
        ivSetFavourite.setOnClickListener(this);
        ivShareProduct.setOnClickListener(this);
        btnBuyNow.setOnClickListener(this);
        rlToolbarMycart.setOnClickListener(this);
        if (NetworkConnection.isNetworkConnected(FsStoreProductDetail.this)) {
            getFsStoreProductDetail();

        } else {
            ToastUtils.showShortToast(FsStoreProductDetail.this, getString(R.string.err_network_connection));
        }
    }

    /**
     * Getting Fs Store product details from server
     */
    private void getFsStoreProductDetail() {
        AppDialog.showProgressDialog(FsStoreProductDetail.this, true);
        IApiClient apiClient = ApiClient.getApiClient();
        ReqProductDetail reqProductDetail = new ReqProductDetail();
        reqProductDetail.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, getString(R.string.txt_skip_user_id)));
        reqProductDetail.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqProductDetail.setMethod(MethodFactory.GET_PRODUCT_DETAIL.getMethod());
        reqProductDetail.setProductID(mProductId);
        Call<ResGetProductDetail> resGetProductDetailCall = apiClient.getProductDetail(reqProductDetail);
        resGetProductDetailCall.enqueue(new Callback<ResGetProductDetail>() {
            @Override
            public void onResponse(Call<ResGetProductDetail> call, Response<ResGetProductDetail> response) {
                AppDialog.showProgressDialog(FsStoreProductDetail.this, false);
                mResGetProductDetail = response.body();
                if (mResGetProductDetail != null) {
                    if (mResGetProductDetail.getSuccess() == ServiceConstants.SUCCESS) {
                        mProductDetailData = mResGetProductDetail.getProductData();
                        if (mProductDetailData != null) {
                            int count = getImageAndCount(mProductDetailData);
                            mFsProductDetailViewPagerAdapter = new FSProductDetailViewPagerAdapter(FsStoreProductDetail.this, mProductImageList);
                            vpFsStoreProduct.setAdapter(mFsProductDetailViewPagerAdapter);
                            showViewPagerIndicator(count);
                            tvProductName.setText(mProductDetailData.getName());
                            tvToolbarCartCount.setText(mResGetProductDetail.getCartCount());
                            tvProductActualPrice.setText("Rs. " + mProductDetailData.getPrice());
                            tvProductActualPrice.setPaintFlags(tvProductActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            float discountedPrice = calculateDiscountPrice(Float.parseFloat(mProductDetailData.getPrice()), Float.parseFloat(mProductDetailData.getDiscount()));
                            tvDiscountedPrice.setText(String.valueOf("Rs. " + discountedPrice));
                            tvProductDesc.setText(mProductDetailData.getDescription());
                            tvFabricValue.setText(mProductDetailData.getFabric());
                            tvFitValue.setText(mProductDetailData.getFit());
                            if (!(mProductDetailData.getDiscount().equals("0") || mProductDetailData.getDiscount().equals("0"))) {
                                tvProductDiscount.setText(mProductDetailData.getDiscount() + "% off");
                            } else {
                                tvProductDiscount.setVisibility(View.GONE);
                                tvProductActualPrice.setVisibility(View.GONE);
                            }
                            tvSuitableForValue.setText(mProductDetailData.getSuitableFor());
                            tvSleeveValue.setText(mProductDetailData.getSleeve());
                            tvColorValue.setText(mProductDetailData.getColor());
                            tvDiscountCashback.setText("Rs. " + mProductDetailData.getExtCashBack());
                            tvCareForValue.setText(mProductDetailData.getCare());
                            if (mProductDetailData.getQuantity().equals("0") || mProductDetailData.getQuantity().equals("")) {
                                tvSoldOut.setVisibility(View.VISIBLE);
                            } else {
                                tvSoldOut.setVisibility(View.GONE);
                            }
                            tvRedeemPoints.setText(mProductDetailData.getRedeemPoint());
                            if (mProductDetailData.getFavourite() == ServiceConstants.FAVOURITE) {
                                ivSetFavourite.setImageResource(R.drawable.heart);
                            } else {
                                ivSetFavourite.setImageResource(R.drawable.empty_heart);
                            }
                            for (int i = 0; i < mProductDetailData.getSize().size(); i++) {
                                switch (mProductDetailData.getSize().get(i).toUpperCase()) {
                                    case "S":
                                        btnS.setVisibility(View.VISIBLE);
                                        if (!mBtnSelectedSize) {
                                            btnS.setSelected(true);
                                            mBtnSelectedSize = true;
                                        }
                                        break;
                                    case "M":
                                        btnM.setVisibility(View.VISIBLE);
                                        if (!mBtnSelectedSize) {
                                            btnS.setSelected(true);
                                            mBtnSelectedSize = true;
                                        }
                                        break;
                                    case "L":
                                        btnL.setVisibility(View.VISIBLE);
                                        if (!mBtnSelectedSize) {
                                            btnS.setSelected(true);
                                            mBtnSelectedSize = true;
                                        }
                                        break;
                                    case "XL":
                                        btnXL.setVisibility(View.VISIBLE);
                                        if (!mBtnSelectedSize) {
                                            btnS.setSelected(true);
                                            mBtnSelectedSize = true;
                                        }
                                        break;
                                    case "XXL":
                                        btnXXL.setVisibility(View.VISIBLE);
                                        if (!mBtnSelectedSize) {
                                            btnS.setSelected(true);
                                            mBtnSelectedSize = true;
                                        }
                                        break;
                                }
                            }
                            if (tvProductDesc.getLineCount() >= 4) {
                                btnKnowMore.setVisibility(View.VISIBLE);
                            } else {
                                btnKnowMore.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        ToastUtils.showShortToast(FsStoreProductDetail.this, mResGetProductDetail.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(FsStoreProductDetail.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResGetProductDetail> call, Throwable t) {
                AppDialog.showProgressDialog(FsStoreProductDetail.this, false);
                ToastUtils.showShortToast(FsStoreProductDetail.this, R.string.err_network_connection);
            }
        });

    }

    /**
     * Calculating discounted price by applying discount
     *
     * @param actualPrice
     * @param discount
     * @return
     */
    public float calculateDiscountPrice(float actualPrice, float discount) {
        float discountValue = (actualPrice * discount) / 100;
        return actualPrice - discountValue;
    }

    /**
     * Showing View pager dots indicator
     *
     * @param count
     */
    private void showViewPagerIndicator(int count) {
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(FsStoreProductDetail.this);
            imageView.setTag(R.id.tag_position, i);
            imageView.setImageResource(R.drawable.x_sd_page_indicator);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = (int) getResources().getDimension(R.dimen.d_pad_pager_indicator);
            params.rightMargin = (int) getResources().getDimension(R.dimen.d_pad_pager_indicator);
            imageView.setLayoutParams(params);
            imageView.setOnClickListener(this);
            llPagerIndicator.addView(imageView);
            mImageViewsList.add(imageView);
        }
        mImageViewsList.get(0).setSelected(true);
    }

    /**
     * Getting product images count and saving images in image-List from server
     *
     * @param mProductDetailData
     * @return
     */
    private int getImageAndCount(ResProductDetailData mProductDetailData) {
        int count = 0;
        if (!mProductDetailData.getImage().equals("")) {
            count++;
            mProductImageList.add(mProductDetailData.getImage());
        }
        if (!mProductDetailData.getImage1().equals("")) {
            count++;
            mProductImageList.add(mProductDetailData.getImage1());
        }
        if (!mProductDetailData.getImage2().equals("")) {
            count++;
            mProductImageList.add(mProductDetailData.getImage2());
        }
        if (!mProductDetailData.getImage3().equals("")) {
            count++;
            mProductImageList.add(mProductDetailData.getImage3());
        }
        return count;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == AppConstants.RC_FROM_DETAILS_TO_MY_CART_ACTIVITY) {
            if (NetworkConnection.isNetworkConnected(FsStoreProductDetail.this)) {
                getFsStoreProductDetail();

            } else {
                ToastUtils.showShortToast(FsStoreProductDetail.this, getString(R.string.err_network_connection));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("productPosition", mProductPosition);
        intent.putExtra("productId", mProductId);
        if (mProductDetailData != null) {
            intent.putExtra("productPositionFavourite", mProductDetailData.getFavourite());
            setResult(RESULT_OK, intent);
        } else
            setResult(RESULT_CANCELED, intent);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_S:
                btnS.setSelected(true);
                // btnS.setTextColor(getResources().getColor(R.color.c_white));
                btnM.setSelected(false);
                btnL.setSelected(false);
                btnXL.setSelected(false);
                btnXXL.setSelected(false);
                break;
            case R.id.btn_M:
                btnS.setSelected(false);
                btnM.setSelected(true);
                //btnM.setTextColor(getResources().getColor(R.color.c_white));
                btnL.setSelected(false);
                btnXL.setSelected(false);
                btnXXL.setSelected(false);
                break;
            case R.id.btn_L:
                btnS.setSelected(false);
                btnM.setSelected(false);
                btnL.setSelected(true);
                btnXXL.setSelected(false);
                //btnL.setTextColor(getResources().getColor(R.color.c_white));
                btnXL.setSelected(false);
                break;
            case R.id.btn_XL:
                btnS.setSelected(false);
                btnM.setSelected(false);
                btnL.setSelected(false);
                btnXL.setSelected(true);
                btnXXL.setSelected(false);
                // btnXL.setTextColor(getResources().getColor(R.color.c_white));
                break;
            case R.id.btn_XXL:
                btnXXL.setSelected(true);
                btnS.setSelected(false);
                btnM.setSelected(false);
                btnL.setSelected(false);
                btnXL.setSelected(false);
            case R.id.iv_toolbar_filter:
                break;
            case R.id.iv_toolbar_filter_active:
                break;
            case R.id.rl_toolbar_filter:
                break;
            case R.id.iv_toolbar_favourite:
                break;
            case R.id.btn_know_more:
                tvProductDesc.setMaxLines(Integer.MAX_VALUE);
                tvProductDesc.setEllipsize(null);
                btnKnowMore.setVisibility(View.GONE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tvProductDesc.setLayoutParams(layoutParams);
                break;
            case R.id.tv_Check_pin_code:
                if (!etPinCode.getText().toString().equals("")) {
                    getDeliveryStatus(etPinCode.getText().toString());
                }
                break;
            case R.id.iv_set_favourite:
                if (AppSharedPreference.getInstance(this).getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (AppSharedPreference.getInstance(this).getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        setFavouriteUnFavourite();
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                break;
            case R.id.iv_share_product:
                if (AppSharedPreference.getInstance(this).getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (AppSharedPreference.getInstance(this).getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
//                        new AsyncTask<Void, Void, Void>() {
//                            @Override
//                            protected Void doInBackground(Void... params) {
//                                Looper.prepare();
//                                try {
//                                    mBitmap = Glide.
//                                            with(FsStoreProductDetail.this).
//                                            load(mProductDetailData.getImage()).
//                                            asBitmap().
//                                            into(-1, -1).
//                                            get();
//                                } catch (final ExecutionException e) {
//                                    Log.e("", e.getMessage());
//                                } catch (final InterruptedException e) {
//                                    Log.e("", e.getMessage());
//                                }
//                                return null;
//                            }
//
//                            @Override
//                            protected void onPostExecute(Void dummy) {
//                                if (null != mBitmap) {
//                                    String shareBody = getString(R.string.txt_fs_store_share);
//                                    shareProduct(mBitmap, shareBody);
//                                }
//                            }
//                        }.execute();
                        String shareBody = getString(R.string.txt_fs_store_share);
                        shareProduct(mBitmap, shareBody);
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }

                break;
            case R.id.tv_size_chart:
                SizeChartDialogFragement sizeChartDialogFragement = new SizeChartDialogFragement();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog_fragment");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.replace(android.R.id.content, sizeChartDialogFragement, "dialog_fragment")
                        .addToBackStack(null).commit();
                break;

            case R.id.btn_buy_now:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        mFsPoints = Integer.parseInt(mAppSharedPreference.getString(PreferenceKeys.KEY_POINTS, "0"));
                        if (mFsPoints > 0 && Integer.parseInt(mProductDetailData.getRedeemPoint()) > 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage(getString(R.string.txt_want_redeem_fs_points)).setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();
                        } else {
                            AddToCart(false);
                        }
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                break;

            case R.id.rl_toolbar_mycart:
                Intent intent;
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        intent = new Intent(this, MyCartActivity.class);
                        intent.putExtra(AppConstants.TAG_FROM_WHERE, AppConstants.FROM_PRODUCTS_DETAIL_ACTIVITY);
                        startActivityForResult(intent, AppConstants.RC_FROM_DETAILS_TO_MY_CART_ACTIVITY);
                    } else {
                        AppDialog.showVerifyEmailAlertDialog(this);
                    }
                } else {
                    AppDialog.showLoginAlertDialog(this);
                }
                break;
        }
    }

    /**
     * Dialog for Redeeming FS Points confirmation
     */
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    if (mFsPoints >= Integer.parseInt(mProductDetailData.getRedeemPoint())) {
                        AddToCart(true);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FsStoreProductDetail.this);
                        builder.setMessage(R.string.txt_not_having_sufficient_fs_points)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        AddToCart(false);
                                    }
                                })
                                .show();

                    }
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    AddToCart(false);
                    break;
            }
        }
    };

    /**
     * Adding the product into the cart
     *
     * @param redeemFsPoints whether redeeming Fs points or not
     */
    private void AddToCart(boolean redeemFsPoints) {
        if (!(mProductDetailData.getQuantity().equals("0") || mProductDetailData.getQuantity().equals(""))) {
            AppDialog.showProgressDialog(this, true);
            IApiClient apiClient = ApiClient.getApiClient();
            ReqAddToCart reqAddToCart = new ReqAddToCart();
            reqAddToCart.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
            reqAddToCart.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
            reqAddToCart.setProductID(mProductDetailData.getProductID());
            reqAddToCart.setMethod(MethodFactory.ADD_CART.getMethod());
            reqAddToCart.setSize(setSelectedSize());
            if (redeemFsPoints)
                reqAddToCart.setPointToRedeem(AppConstants.USE_FS_POINTS);
            else
                reqAddToCart.setPointToRedeem(AppConstants.DO_NOT_USE_FS_POINTS);
            Call<ResBase> call = apiClient.addCart(reqAddToCart);
            call.enqueue(new Callback<ResBase>() {
                @Override
                public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                    AppDialog.showProgressDialog(FsStoreProductDetail.this, false);
                    ResBase resBase = response.body();
                    if (resBase != null) {
                        if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                            ToastUtils.showShortToast(FsStoreProductDetail.this, resBase.getErrstr());
                            tvToolbarCartCount.setText(String.valueOf(Integer.parseInt(mResGetProductDetail.getCartCount()) + 1));
                        } else {
                            ToastUtils.showShortToast(FsStoreProductDetail.this, resBase.getErrstr());
                        }
                    } else {
                        ToastUtils.showShortToast(FsStoreProductDetail.this, R.string.err_network_connection);
                    }
                }

                @Override
                public void onFailure(Call<ResBase> call, Throwable t) {
                    AppDialog.showProgressDialog(FsStoreProductDetail.this, false);
                    ToastUtils.showShortToast(FsStoreProductDetail.this, R.string.err_network_connection);
                }
            });
        } else {
            ToastUtils.showShortToast(FsStoreProductDetail.this, R.string.product_sold_out);
        }
    }

    /**
     * Sharing product
     *
     * @param bitmap
     * @param shareBody
     */
    public void shareProduct(Bitmap bitmap, String shareBody) {
        mBitmap = bitmap;
        mShareBody = shareBody;
        if (PermissionUtils.hasStoragePermission(this)) {
            String shareSubject = getString(R.string.app_name);
            String imagePath = AppConstants.FITSTREET_DIRECTORY_PATH + AppConstants.FITSTREET_PRODUCT_IMAGE_PATH + +System.currentTimeMillis() + AppConstants.EXTN_JPG;
            //CameraAndGalleryUtils.saveImageToFile(bitmap, imagePath);
            AppUtilMethods.openImageShareDialog(this, shareSubject, shareBody, imagePath);
        }
    }

    /**
     * Setting/Saving selected size of the product
     *
     * @return
     */
    private String setSelectedSize() {
        if (btnS.isSelected())
            return "S";
        else if (btnM.isSelected())
            return "M";
        else if (btnL.isSelected())
            return "L";
        else if (btnXL.isSelected())
            return "XL";
        else if (btnXXL.isSelected())
            return "XXL";
        else
            return "";
    }

    /**
     * Setting favourites/unfavourites product to/from server
     */
    public void setFavouriteUnFavourite() {
        AppDialog.showProgressDialog(this, true);
        IApiClient client = ApiClient.getApiClient();
        final ReqSetFavouriteProduct reqSetFavouriteProduct = new ReqSetFavouriteProduct();
        reqSetFavouriteProduct.setProductID(mProductDetailData.getProductID());
        reqSetFavouriteProduct.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        if (mProductDetailData.getFavourite() == ServiceConstants.FAVOURITE) {
            reqSetFavouriteProduct.setMethod(MethodFactory.UNFAVOURITE_PRODUCT.getMethod());
        } else {
            reqSetFavouriteProduct.setMethod(MethodFactory.FAVOURITE_PRODUCT.getMethod());
        }
        reqSetFavouriteProduct.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ""));
        Call<ResBase> resBaseCall = client.setFavouriteProduct(reqSetFavouriteProduct);
        resBaseCall.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                ResBase resBase = response.body();
                AppDialog.showProgressDialog(FsStoreProductDetail.this, false);
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        if (reqSetFavouriteProduct.getMethod().equals(MethodFactory.FAVOURITE_PRODUCT.getMethod())) {
                            ToastUtils.showShortToast(FsStoreProductDetail.this, getString(R.string.txt_success_set_favourite_product));
                            mProductDetailData.setFavourite(ServiceConstants.FAVOURITE);
                            ivSetFavourite.setImageResource(R.drawable.heart);
                        } else {
                            mProductDetailData.setFavourite(ServiceConstants.UNFAVOURITE);
                            ToastUtils.showShortToast(FsStoreProductDetail.this, getString(R.string.txt_success_unfavourite_product));
                            ivSetFavourite.setImageResource(R.drawable.empty_heart);
                        }
                    } else {
                        ToastUtils.showShortToast(FsStoreProductDetail.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(FsStoreProductDetail.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(FsStoreProductDetail.this, false);
                ToastUtils.showShortToast(FsStoreProductDetail.this, R.string.err_network_connection);

            }
        });
    }

    /**
     * Getting Delivery status/time by using pincode from server
     *
     * @param pinCode
     */
    private void getDeliveryStatus(String pinCode) {
        AppDialog.showProgressDialog(this, true);
        IApiClient client = ApiClient.getApiClient();
        ReqDeliveryStatus reqDeliveryStatus = new ReqDeliveryStatus();
        reqDeliveryStatus.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, getString(R.string.txt_skip_user_id)));
        reqDeliveryStatus.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqDeliveryStatus.setMethod(MethodFactory.GET_DELIVERY_STATUS.getMethod());
        reqDeliveryStatus.setPincode(pinCode);
        final Call<ResDeliveryStatus> resGetDeliveryStatus = client.getDeliveryStatus(reqDeliveryStatus);
        resGetDeliveryStatus.enqueue(new Callback<ResDeliveryStatus>() {
            @Override
            public void onResponse(Call<ResDeliveryStatus> call, Response<ResDeliveryStatus> response) {
                AppDialog.showProgressDialog(FsStoreProductDetail.this, false);
                mResDeliveryStatus = response.body();
                if (mResDeliveryStatus != null) {
                    if (mResDeliveryStatus.getSuccess() == ServiceConstants.SUCCESS) {
                        if (!mResDeliveryStatus.getPincodeData().getMinDays().equals(""))
                            tvDeliveryValue.setText(mResDeliveryStatus.getPincodeData().getMinDays() + " to " + mResDeliveryStatus.getPincodeData().getMaxDays() + " Days");
                        else
                            tvDeliveryValue.setText(R.string.txt_delivery_not_available);
                    } else {
                        ToastUtils.showShortToast(FsStoreProductDetail.this, mResDeliveryStatus.getErrstr());
                        tvDeliveryValue.setText("");
                    }
                } else {
                    ToastUtils.showShortToast(FsStoreProductDetail.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResDeliveryStatus> call, Throwable t) {
                AppDialog.showProgressDialog(FsStoreProductDetail.this, false);
                ToastUtils.showShortToast(FsStoreProductDetail.this, R.string.err_network_connection);
            }
        });


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        for (ImageView imageView : mImageViewsList) {
            imageView.setSelected(false);
        }
        mImageViewsList.get(position).setSelected(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        ViewPagerDialog newFragment = new ViewPagerDialog(mProductImageList, mProductImageList.size());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog_fragment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.replace(android.R.id.content, newFragment, "dialog_fragment")
                .addToBackStack(null).commitAllowingStateLoss();


    }
}
