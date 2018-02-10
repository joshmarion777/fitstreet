package affle.com.fitstreet.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceButton;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.models.request.ReqProductDetail;
import affle.com.fitstreet.models.request.ReqRedeemPointsAffiliate;
import affle.com.fitstreet.models.request.ReqSetFavouriteProduct;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResGetProductDetail;
import affle.com.fitstreet.models.response.ResProductDetailData;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.NetworkConnection;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
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

public class ProductDetailActivity extends BaseActivity {
    @BindView(R.id.btn_know_more)
    Button btnKnowMore;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_product_info)
    TextView tvProductInfo;
    @BindView(R.id.iv_product_image)
    ImageView ivProductImage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_selling_price)
    TextView tvSellingPrice;
    @BindView(R.id.tv_redeem_points)
    TextView tvRedeemPoints;
    @BindView(R.id.tv_discount_value)
    TextView tvDiscountValue;
    @BindView(R.id.tv_product_desc)
    TextView tvProductDesc;
    @BindView(R.id.iv_set_favourite)
    ImageView ivSetFavourite;
    @BindView(R.id.iv_share_product)
    ImageView ivShareProduct;
    @BindView(R.id.tv_discounted_price)
    CustomTypefaceTextView tvDiscountedPrice;
    @BindView(R.id.tv_discount_value_affiliate)
    CustomTypefaceTextView tvDiscountValueAffiliate;
    @BindView(R.id.btn_buy_now)
    CustomTypefaceButton btnBuyNow;
    private String mProductId = "";
    private String mProductName = "";
    private int mProductPosition;
    private ResProductDetailData mProductDetailData;
    private ProgressDialog mProgressDialog;
    private Bitmap mBitmap;
    private String mShareBody;
    private int mFsPoints = 0;
    private boolean mIsImageLoadCompleted=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShareBody = getString(R.string.txt_share_body);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        if (intent != null) {
            mProductId = intent.getExtras().getString("productId");
            mProductName = intent.getExtras().getString("productName");
            mProductPosition = intent.getExtras().getInt("productPosition");
        }
        super.initData();
    }

    @Override
    protected void initViews() {
        tvTitle.setText(mProductName);
        //set listeners
        ivBack.setOnClickListener(this);
        btnKnowMore.setOnClickListener(this);
        ivSetFavourite.setOnClickListener(this);
        ivShareProduct.setOnClickListener(this);
        btnBuyNow.setOnClickListener(this);

    }

    @Override
    protected void initVariables() {
        if (NetworkConnection.isNetworkConnected(ProductDetailActivity.this)) {
            getProductDetail(mProductId);
        } else {
            ToastUtils.showShortToast(ProductDetailActivity.this, getResources().getString(R.string.err_network_connection));
        }
    }

    public void showProgressDialog() {
        mProgressDialog = AppDialog.showProgressDialog(ProductDetailActivity.this);
        mProgressDialog.show();
    }

    /**
     * This method is used to hide the progress dialog
     *
     * @return void
     */
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Getting products detail data from server using productId
     *
     * @param productId
     */
    public void getProductDetail(final String productId) {
        showProgressDialog();
        IApiClient client = ApiClient.getApiClient();
        ReqProductDetail reqProductDetail = new ReqProductDetail();
        reqProductDetail.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqProductDetail.setProductID(productId);
        reqProductDetail.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqProductDetail.setMethod(MethodFactory.GET_PRODUCT_DETAIL.getMethod());
        Call<ResGetProductDetail> resGetProductDetailCall = client.getProductDetail(reqProductDetail);
        resGetProductDetailCall.enqueue(new Callback<ResGetProductDetail>() {
            @Override
            public void onResponse(Call<ResGetProductDetail> call, Response<ResGetProductDetail> response) {
                dismissProgressDialog();
                progressBar.setVisibility(View.VISIBLE);
                ResGetProductDetail resGetProductDetail = response.body();
                if (resGetProductDetail != null) {
                    if (resGetProductDetail.getSuccess() == ServiceConstants.SUCCESS) {
                        mProductDetailData = resGetProductDetail.getProductData();
                        if (mProductDetailData != null) {
                            tvProductName.setText(mProductDetailData.getName());
                            if (!mProductDetailData.getPackOf().equals("0") || mProductDetailData.getPackOf().equals(""))
                                tvProductInfo.setText(mProductDetailData.getColor() + " Pack of " + mProductDetailData.getPackOf());
                            Glide.with(ProductDetailActivity.this)
                                    .load(mProductDetailData.getImage())
                                    .placeholder(R.drawable.no_image_icon)
                                    .listener(new RequestListener<String, GlideDrawable>() {
                                        @Override
                                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                            progressBar.setVisibility(View.GONE);
                                            ivProductImage.setVisibility(View.VISIBLE);
                                            ivProductImage.setImageResource(R.drawable.no_image_available);
                                            mIsImageLoadCompleted=false;
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                            progressBar.setVisibility(View.GONE);
                                            ivProductImage.setVisibility(View.VISIBLE);
                                            mIsImageLoadCompleted=true;
                                            return false;
                                        }

                                    })
                                    .into(ivProductImage);

                            float discountedPrice = calculateDiscountPrice(Float.parseFloat(mProductDetailData.getPrice()), Float.parseFloat(mProductDetailData.getDiscount()));
                            tvSellingPrice.setText("Rs. " + mProductDetailData.getPrice());
                            if (Integer.parseInt(mProductDetailData.getDiscount()) > 0) {
                                tvSellingPrice.setPaintFlags(tvSellingPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                tvDiscountedPrice.setText("Rs. " + String.valueOf((int) discountedPrice));
                                tvDiscountedPrice.setVisibility(View.VISIBLE);
                            } else {
                                tvDiscountedPrice.setVisibility(View.GONE);
                            }
                            tvRedeemPoints.setText(mProductDetailData.getRedeemPoint());
                            tvDiscountValueAffiliate.setText(mProductDetailData.getDiscount() + "%");

                            tvDiscountValue.setText("Rs. " + mProductDetailData.getExtCashBack());
                            tvProductDesc.setText(mProductDetailData.getDescription());
                            if (tvProductDesc.getLineCount() >= 4) {
                                btnKnowMore.setVisibility(View.VISIBLE);
                            } else {
                                btnKnowMore.setVisibility(View.GONE);
                            }
                            if (mProductDetailData.getFavourite() == ServiceConstants.FAVOURITE) {
                                ivSetFavourite.setImageResource(R.drawable.heart);
                            } else {
                                ivSetFavourite.setImageResource(R.drawable.empty_heart);
                            }
                            //rbProduct.setRating(mProductDetailData.getRating());

                        }


                    } else {
                        ToastUtils.showShortToast(ProductDetailActivity.this, resGetProductDetail.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(ProductDetailActivity.this, R.string.err_network_connection);
                }

            }

            @Override
            public void onFailure(Call<ResGetProductDetail> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(ProductDetailActivity.this, R.string.err_network_connection);
            }
        });
    }

    /**
     * Calculating discounted price using actualPrice and discount
     *
     * @param actualPrice
     * @param discount
     * @return
     */
    private float calculateDiscountPrice(float actualPrice, float discount) {
        float discountValue = (actualPrice * discount) / 100;
        return actualPrice - discountValue;
    }

    /**
     * Setting favourite/unfavourite the product
     */
    public void setFavouriteUnFavourite() {
        showProgressDialog();
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
                dismissProgressDialog();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        if (reqSetFavouriteProduct.getMethod().equals(MethodFactory.FAVOURITE_PRODUCT.getMethod())) {
                            ToastUtils.showShortToast(ProductDetailActivity.this, getString(R.string.txt_success_set_favourite_product));
                            mProductDetailData.setFavourite(ServiceConstants.FAVOURITE);
                            ivSetFavourite.setImageResource(R.drawable.heart);
                        } else {
                            mProductDetailData.setFavourite(ServiceConstants.UNFAVOURITE);
                            ToastUtils.showShortToast(ProductDetailActivity.this, getString(R.string.txt_success_unfavourite_product));
                            ivSetFavourite.setImageResource(R.drawable.empty_heart);
                        }
                    } else {
                        ToastUtils.showShortToast(ProductDetailActivity.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(ProductDetailActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                dismissProgressDialog();
                ToastUtils.showShortToast(ProductDetailActivity.this, R.string.err_network_connection);

            }
        });
    }

    /**
     * Dialog for Redeeming points confirmation
     */
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    if (NetworkConnection.isNetworkConnected(ProductDetailActivity.this)) {
                        if (mFsPoints >= Integer.parseInt(mProductDetailData.getRedeemPoint())) {
                            redeemFsPoints();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
                            builder.setMessage(R.string.txt_not_having_sufficient_fs_points)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            buyNow();
                                        }
                                    })
                                    .show();
                        }
                    }
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    if (NetworkConnection.isNetworkConnected(ProductDetailActivity.this))
                        buyNow();
                    break;
            }
        }
    };

    /**
     * Opening respective product detail pages in Browser
     */
    private void buyNow() {
        String url = mProductDetailData.getAffilateUrl();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        if (URLUtil.isValidUrl(url)) {
            startActivity(i);
        } else {
            ToastUtils.showShortToast(ProductDetailActivity.this, getString(R.string.txt_invalid_url));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_know_more:
                tvProductDesc.setMaxLines(Integer.MAX_VALUE);
                tvProductDesc.setEllipsize(null);
                btnKnowMore.setVisibility(View.GONE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tvProductDesc.setLayoutParams(layoutParams);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_set_favourite:
                setFavouriteUnFavourite();
                break;
            case R.id.iv_share_product:
                if(mIsImageLoadCompleted) {
                    String shareBody = getString(R.string.txt_product_share_message);
                    ivProductImage.setDrawingCacheEnabled(true);
                    Bitmap bitmap = ivProductImage.getDrawingCache();
                    shareProduct(bitmap, shareBody);
                }
                break;
            case R.id.btn_buy_now:
                if (mAppSharedPreference.getBoolean(PreferenceKeys.KEY_LOGGED_IN, false)) {
                    if (mAppSharedPreference.getString(PreferenceKeys.KEY_IS_EMAIL_VERIFIED, "0").equalsIgnoreCase("1")) {
                        mFsPoints = Integer.parseInt(mAppSharedPreference.getString(PreferenceKeys.KEY_POINTS, "0"));
                        if (mFsPoints > 0 && Integer.parseInt(mProductDetailData.getRedeemPoint()) > 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage("Do You Want To Redeem FS Points?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();
                        } else {
                            buyNow();
                        }
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
     * saving whether redeeming points or not on server
     */
    private void redeemFsPoints() {
        AppDialog.showProgressDialog(this, true);
        IApiClient client = ApiClient.getApiClient();
        ReqRedeemPointsAffiliate reqRedeemPointsAffiliate = new ReqRedeemPointsAffiliate();
        reqRedeemPointsAffiliate.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqRedeemPointsAffiliate.setProductID(mProductDetailData.getProductID());
        reqRedeemPointsAffiliate.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqRedeemPointsAffiliate.setMethod(MethodFactory.REDEEM_POINTS_AFFLIATE.getMethod());
        Call<ResBase> call = client.redeemPointsAffiliate(reqRedeemPointsAffiliate);
        call.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                AppDialog.showProgressDialog(ProductDetailActivity.this, false);
                ResBase resBase = response.body();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {
                        buyNow();
                    } else {
                        ToastUtils.showShortToast(ProductDetailActivity.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(ProductDetailActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                AppDialog.showProgressDialog(ProductDetailActivity.this, false);
                ToastUtils.showShortToast(ProductDetailActivity.this, R.string.err_network_connection);
            }
        });
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
            CameraAndGalleryUtils.saveImageToFile(bitmap, imagePath);
            AppUtilMethods.openImageShareDialog(this, shareSubject, shareBody, imagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.RC_REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    shareProduct(mBitmap, mShareBody);
                } else {
                    // Permission Denied
                    ToastUtils.showShortToast(this, getString(R.string.err_permission_denied));
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("productPosition", mProductPosition);
        intent.putExtra("productId", mProductId);
        if (mProductDetailData != null)
            intent.putExtra("productPositionFavourite", mProductDetailData.getFavourite());
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
