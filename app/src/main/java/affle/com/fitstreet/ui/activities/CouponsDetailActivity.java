package affle.com.fitstreet.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import affle.com.fitstreet.R;
import affle.com.fitstreet.models.request.ReqCouponsDetail;
import affle.com.fitstreet.models.response.CouponData;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResCouponsDetail;
import affle.com.fitstreet.network.ApiClient;
import affle.com.fitstreet.network.IApiClient;
import affle.com.fitstreet.network.MethodFactory;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.preference.PreferenceKeys;
import affle.com.fitstreet.utils.AppConstants;
import affle.com.fitstreet.utils.AppDialog;
import affle.com.fitstreet.utils.AppUtilMethods;
import affle.com.fitstreet.utils.ToastUtils;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponsDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_display_image)
    ImageView ivDisplayImage;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_view_all_offer)
    TextView tvViewAllOffer;
    @BindView(R.id.ll_expand)
    LinearLayout llExpand;
    @BindView(R.id.ib_heart)
    ImageButton ibHeart;
    @BindView(R.id.tv_coupon_code)
    TextView tvCouponCode;
    @BindView(R.id.btn_start_shopping)
    Button btnStartShopping;
    @BindView(R.id.tv_title_coupon)
    TextView tvTitleCoupon;
    @BindView(R.id.tv_description_below_tittle)
    TextView tvDescriptionBelowTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_validity)
    TextView tvValidity;
    @BindView(R.id.tv_redeem_points)
    TextView tvRedeemPoints;
    @BindView(R.id.tv_extra_cashback)
    TextView tvExtraCashBack;
    @BindView(R.id.tv_avail_cashback_steps)
    TextView tvAvailCashBackSteps;
    @BindView(R.id.nestedScroll)
    NestedScrollView nestedScroll;
    private String mCouponId = "";
    private boolean mFavourite = false;
    private CouponData mCouponData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_detail);
        super.initData();
        if (getIntent() != null) {
            Intent intent = getIntent();
            String couponId = intent.getStringExtra(AppConstants.COUPON_ID);
            String fav = intent.getStringExtra(AppConstants.FAVOURITE);

            if (couponId != null)
                this.mCouponId = couponId;

            if (fav != null) {
                if (fav.equals("1")) {
                    mFavourite = true;
                } else
                    mFavourite = false;
            }
        }
        setResult(RESULT_OK, null);

    }

    @Override
    protected void initViews() {
        setFavouriteIcon(mFavourite);
        getCouponDetail();
        tvTitle.setText("Offer Details");
        tvAvailCashBackSteps.setText("1. Start with an empty cart.\n" +
                "2. Finish your purchase in this season.\n" +
                "3. Don't visit other coupon, cashback or price \n" +
                "    comparison sites.");
        ibHeart.setOnClickListener(this);
        tvViewAllOffer.setOnClickListener(this);
        btnStartShopping.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_view_all_offer:
                Intent intent = new Intent();
                intent.putExtra(AppConstants.PARTNER_ID, mCouponData.getPartnerId() + "");
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_start_shopping:
                ToastUtils.showShortToast(this, "Start Shopping");
                break;
            case R.id.ib_heart:

                String tag = (String) ibHeart.getTag();
                if (!mFavourite)
                    setmFavourite(MethodFactory.FAVOURITE_COUPON);
                else
                    setmFavourite(MethodFactory.UNFAVOURITE_COUPON);

                break;

        }
    }

    /**
     * Setting favourite coupon to server
     *
     * @param favouriteCoupon
     */
    private void setmFavourite(final MethodFactory favouriteCoupon) {
        AppDialog.showProgressDialog(this, true);
        IApiClient client = ApiClient.getApiClient();
        ReqCouponsDetail reqCouponsDetail = new ReqCouponsDetail();
        reqCouponsDetail.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqCouponsDetail.setMethod(favouriteCoupon.getMethod());
        reqCouponsDetail.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqCouponsDetail.setCoupanID(mCouponId);
        Call<ResBase> resBaseCall = client.setFavouriteCouponDetail(reqCouponsDetail);
        resBaseCall.enqueue(new Callback<ResBase>() {
            @Override
            public void onResponse(Call<ResBase> call, Response<ResBase> response) {
                AppDialog.showProgressDialog(CouponsDetailActivity.this, false);
                ResBase resBase = response.body();
                if (resBase != null) {
                    if (resBase.getSuccess() == ServiceConstants.SUCCESS) {

                        ToastUtils.showShortToast(CouponsDetailActivity.this, resBase.getErrstr());
                        setFavouriteIcon(favouriteCoupon);

                    } else {

                        ToastUtils.showShortToast(CouponsDetailActivity.this, resBase.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(CouponsDetailActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResBase> call, Throwable t) {
                ToastUtils.showShortToast(CouponsDetailActivity.this, R.string.err_network_connection);
            }
        });
    }

    /**
     * Setting favourite/unfavourite icons
     *
     * @param favourite
     */
    private void setFavouriteIcon(boolean favourite) {
        if (favourite) {
            ibHeart.setBackgroundResource(R.drawable.heart);
        } else {
            ibHeart.setBackgroundResource(R.drawable.empty_heart);
        }

    }

    /**
     * setting coupons favourite/unfavourite
     *
     * @param favouriteCoupon
     */
    private void setFavouriteIcon(MethodFactory favouriteCoupon) {
        if (favouriteCoupon.getMethod().equalsIgnoreCase(MethodFactory.FAVOURITE_COUPON.getMethod()))
            setFavouriteIcon(mFavourite = true);
        else
            setFavouriteIcon(mFavourite = false);
    }

    /**
     * Getting coupons details data from server
     */
    private void getCouponDetail() {
        AppDialog.showProgressDialog(this, true);
        IApiClient client = ApiClient.getApiClient();
        ReqCouponsDetail reqCouponsDetail = new ReqCouponsDetail();
        reqCouponsDetail.setServiceKey(mAppSharedPreference.getString(PreferenceKeys.KEY_SERVICE_KEY, ServiceConstants.SERVICE_KEY));
        reqCouponsDetail.setMethod(MethodFactory.GET_COUPONS_DETAIL.getMethod());
        reqCouponsDetail.setUserID(mAppSharedPreference.getString(PreferenceKeys.KEY_USER_ID, ""));
        reqCouponsDetail.setCoupanID(mCouponId);
        Call<ResCouponsDetail> resCouponsDetailCall = client.getCouponDetail(reqCouponsDetail);
        resCouponsDetailCall.enqueue(new Callback<ResCouponsDetail>() {
            @Override
            public void onResponse(Call<ResCouponsDetail> call, Response<ResCouponsDetail> response) {
                AppDialog.showProgressDialog(CouponsDetailActivity.this, false);
                ResCouponsDetail resCouponsDetail = response.body();
                if (resCouponsDetail != null) {
                    if (resCouponsDetail.getSuccess() == ServiceConstants.SUCCESS) {
                        setData(mCouponData = resCouponsDetail.getCouponData());

                    } else {
                        ToastUtils.showShortToast(CouponsDetailActivity.this, resCouponsDetail.getErrstr());
                    }
                } else {
                    ToastUtils.showShortToast(CouponsDetailActivity.this, R.string.err_network_connection);
                }
            }

            @Override
            public void onFailure(Call<ResCouponsDetail> call, Throwable t) {
                ToastUtils.showShortToast(CouponsDetailActivity.this, R.string.err_network_connection);
            }
        });
    }

    /**
     * Setting coupon details data into UI
     *
     * @param couponData
     */
    private void setData(CouponData couponData) {
        tvTitleCoupon.setText(couponData.getName());
        tvLocation.setText(couponData.getLocationName());
        tvRedeemPoints.setText(couponData.getRedeemPoint());
        tvDescriptionBelowTitle.setText(couponData.getTagText());
        tvValidity.setText(couponData.getValidUpTo());
        tvExtraCashBack.setText(couponData.getExtCashBack());
        tvDescription.setText(couponData.getDescription());
        tvCouponCode.setText(couponData.getCode());
        tvCouponCode.setTextIsSelectable(true);
        nestedScroll.smoothScrollTo(0, 0);
        //setting display image
        AppUtilMethods.setImageShowingLoader(this, ivDisplayImage, couponData.getImage());
    }


}
