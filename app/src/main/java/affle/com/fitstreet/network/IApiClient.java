package affle.com.fitstreet.network;

import affle.com.fitstreet.models.request.ReqAddToCart;
import affle.com.fitstreet.models.request.ReqAddUserAddress;
import affle.com.fitstreet.models.request.ReqBase;
import affle.com.fitstreet.models.request.ReqCancelOrder;
import affle.com.fitstreet.models.request.ReqCancellationDetails;
import affle.com.fitstreet.models.request.ReqCashbackHistory;
import affle.com.fitstreet.models.request.ReqChangePassword;
import affle.com.fitstreet.models.request.ReqConnectDisconnectApps;
import affle.com.fitstreet.models.request.ReqContests;
import affle.com.fitstreet.models.request.ReqCoupons;
import affle.com.fitstreet.models.request.ReqCouponsDetail;
import affle.com.fitstreet.models.request.ReqCouponsList;
import affle.com.fitstreet.models.request.ReqDeleteAllNotification;
import affle.com.fitstreet.models.request.ReqDeleteUserAddresses;
import affle.com.fitstreet.models.request.ReqDeliveryStatus;
import affle.com.fitstreet.models.request.ReqFavouriteCoupons;
import affle.com.fitstreet.models.request.ReqFavouriteProducts;
import affle.com.fitstreet.models.request.ReqFitbitToken;
import affle.com.fitstreet.models.request.ReqForgotPassword;
import affle.com.fitstreet.models.request.ReqFsPoints;
import affle.com.fitstreet.models.request.ReqFsStore;
import affle.com.fitstreet.models.request.ReqFsStoreProductsList;
import affle.com.fitstreet.models.request.ReqGetContestDetails;
import affle.com.fitstreet.models.request.ReqGetFilters;
import affle.com.fitstreet.models.request.ReqGetOrderDetail;
import affle.com.fitstreet.models.request.ReqGetPushNotification;
import affle.com.fitstreet.models.request.ReqGetUserDetails;
import affle.com.fitstreet.models.request.ReqGetUserPointsByTime;
import affle.com.fitstreet.models.request.ReqJoinChallenge;
import affle.com.fitstreet.models.request.ReqLeaveChallenge;
import affle.com.fitstreet.models.request.ReqLogin;
import affle.com.fitstreet.models.request.ReqMyCart;
import affle.com.fitstreet.models.request.ReqMyOrders;
import affle.com.fitstreet.models.request.ReqMyWallet;
import affle.com.fitstreet.models.request.ReqPlaceOrder;
import affle.com.fitstreet.models.request.ReqPrizeMoneyDetails;
import affle.com.fitstreet.models.request.ReqProceed;
import affle.com.fitstreet.models.request.ReqProductDetail;
import affle.com.fitstreet.models.request.ReqProductsCategory;
import affle.com.fitstreet.models.request.ReqProductsList;
import affle.com.fitstreet.models.request.ReqRedeemPointsAffiliate;
import affle.com.fitstreet.models.request.ReqRemoveCart;
import affle.com.fitstreet.models.request.ReqRepeatOrder;
import affle.com.fitstreet.models.request.ReqRunkeeperToken;
import affle.com.fitstreet.models.request.ReqSendToBank;
import affle.com.fitstreet.models.request.ReqSetFavouriteCoupon;
import affle.com.fitstreet.models.request.ReqSetFavouriteProduct;
import affle.com.fitstreet.models.request.ReqShareAndInvite;
import affle.com.fitstreet.models.request.ReqSignUp;
import affle.com.fitstreet.models.request.ReqTrendingCoupons;
import affle.com.fitstreet.models.request.ReqTrendingProducts;
import affle.com.fitstreet.models.request.ReqUnFavouriteCoupon;
import affle.com.fitstreet.models.request.ReqUnFavouriteProduct;
import affle.com.fitstreet.models.request.ReqUpdateCart;
import affle.com.fitstreet.models.request.ReqUserCalorieDistance;
import affle.com.fitstreet.models.request.ReqWalletHistory;
import affle.com.fitstreet.models.response.ResAddUserAddress;
import affle.com.fitstreet.models.response.ResBase;
import affle.com.fitstreet.models.response.ResCancellationDetails;
import affle.com.fitstreet.models.response.ResCashBackHistory;
import affle.com.fitstreet.models.response.ResContactUs;
import affle.com.fitstreet.models.response.ResCouponsDetail;
import affle.com.fitstreet.models.response.ResDeliveryStatus;
import affle.com.fitstreet.models.response.ResForgotPassword;
import affle.com.fitstreet.models.response.ResFsStore;
import affle.com.fitstreet.models.response.ResFsStoreProductsList;
import affle.com.fitstreet.models.response.ResGetContestDetails;
import affle.com.fitstreet.models.response.ResGetContests;
import affle.com.fitstreet.models.response.ResGetCouponData;
import affle.com.fitstreet.models.response.ResGetCouponList;
import affle.com.fitstreet.models.response.ResGetFavouriteCoupons;
import affle.com.fitstreet.models.response.ResGetFavouriteProducts;
import affle.com.fitstreet.models.response.ResGetFilters;
import affle.com.fitstreet.models.response.ResGetFsPoints;
import affle.com.fitstreet.models.response.ResGetOrderDetail;
import affle.com.fitstreet.models.response.ResGetProductDetail;
import affle.com.fitstreet.models.response.ResGetProductsCategories;
import affle.com.fitstreet.models.response.ResGetProductsList;
import affle.com.fitstreet.models.response.ResGetTrendingCoupons;
import affle.com.fitstreet.models.response.ResGetTrendingProducts;
import affle.com.fitstreet.models.response.ResGetUserAddresses;
import affle.com.fitstreet.models.response.ResGetUserDetails;
import affle.com.fitstreet.models.response.ResGetUserPointsByTime;
import affle.com.fitstreet.models.response.ResLeaveContest;
import affle.com.fitstreet.models.response.ResLoginSignUpSocial;
import affle.com.fitstreet.models.response.ResMyCart;
import affle.com.fitstreet.models.response.ResMyOrders;
import affle.com.fitstreet.models.response.ResMyWallet;
import affle.com.fitstreet.models.response.ResPlaceOrder;
import affle.com.fitstreet.models.response.ResPrizeMoneyDetails;
import affle.com.fitstreet.models.response.ResPushNotification;
import affle.com.fitstreet.models.response.ResRunkeeperData;
import affle.com.fitstreet.models.response.ResShareAndInvite;
import affle.com.fitstreet.models.response.ResSignUp;
import affle.com.fitstreet.models.response.ResUpdateProfile;
import affle.com.fitstreet.models.response.ResUserCalorieDistance;
import affle.com.fitstreet.models.response.ResWalletHistoryPaid;
import affle.com.fitstreet.models.response.ResWalletHistoryReceived;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by root on 6/14/16.
 */
public interface IApiClient {

    @POST("indexm")
    Call<ResSignUp> signup(@Body ReqSignUp reqSignUp);

    @POST("indexm")
    Call<ResLoginSignUpSocial> signupFb(@Body ReqSignUp reqSignUp);

    @POST("indexm")
    Call<ResLoginSignUpSocial> login(@Body ReqLogin reqLogin);

    @POST("indexm")
    Call<ResForgotPassword> forgotPassword(@Body ReqForgotPassword reqForgotPassword);

    @POST("indexm")
    Call<ResGetUserDetails> getUserDetails(@Body ReqGetUserDetails reqGetUserDetails);

    @POST("indexm")
    Call<ResAddUserAddress> addUserAddress(@Body ReqAddUserAddress reqAddUserAddress);

    @POST("indexm")
    Call<ResBase> changePassword(@Body ReqChangePassword reqChangePassword);

    @POST("indexm")
    Call<ResGetCouponData> getCoupons(@Body ReqCoupons reqCoupons);

    @POST("indexm")
    Call<ResGetFavouriteProducts> getFavouriteProducts(@Body ReqFavouriteProducts reqFavouriteProducts);

    @POST("indexm")
    Call<ResGetUserAddresses> getUserAddresses(@Body ReqGetUserDetails reqAddress);

    @POST("indexm")
    Call<ResBase> deleteUserAddresses(@Body ReqDeleteUserAddresses reqDeleteUserAddresses);

    @POST("indexm")
    Call<ResGetFavouriteCoupons> getFavouriteCoupons(@Body ReqFavouriteCoupons reqFavouriteCoupons);

    @POST("indexm")
    Call<ResBase> setFavouriteCoupon(@Body ReqSetFavouriteCoupon reqSetFavouriteCoupon);

    @POST("indexm")
    Call<ResBase> setFavouriteProduct(@Body ReqSetFavouriteProduct reqSetFavouriteProduct);

    @POST("indexm")
    Call<ResBase> unFavouriteCoupon(@Body ReqUnFavouriteCoupon reqUnFavouriteCoupon);

    @POST("indexm")
    Call<ResBase> unFavouriteProduct(@Body ReqUnFavouriteProduct reqUnFavouriteProduct);

    @POST("indexm")
    Call<ResGetCouponList> getCouponsList(@Body ReqCouponsList reqCoupons);

    @POST("indexm")
    Call<ResGetProductsCategories> getProductsCategory(@Body ReqProductsCategory reqProductsCategory);

    @POST("indexm")
    Call<ResGetTrendingProducts> getTrendingProducts(@Body ReqTrendingProducts reqTrendingProducts);

    @POST("indexm")
    Call<ResGetTrendingCoupons> getTrendingCoupons(@Body ReqTrendingCoupons reqTrendingCoupons);

    @POST("indexm")
    Call<ResGetProductsList> getProductsList(@Body ReqProductsList reqProductsList);

    @POST("indexm")
    Call<ResGetProductDetail> getProductDetail(@Body ReqProductDetail reqProductDetail);

    @POST("indexm")
    Call<ResGetFilters> getFilters(@Body ReqGetFilters reqGetFilters);

    @POST("indexm")
    Call<ResCouponsDetail> getCouponDetail(@Body ReqCouponsDetail ReqCouponsDetail);

    @POST("indexm")
    Call<ResShareAndInvite> shareAndInvite(@Body ReqShareAndInvite reqShareAndInvite);

    @POST("indexm")
    Call<ResGetContests> getContests(@Body ReqContests reqContests);

    @POST("indexm")
    Call<ResGetContests> joinChallenge(@Body ReqJoinChallenge reqJoinChallenge);

    @POST("indexm")
    Call<ResLeaveContest> joinChallengeFromDetail(@Body ReqJoinChallenge reqJoinChallenge);

    @POST("indexm")
    Call<ResLeaveContest> leaveChallenge(@Body ReqLeaveChallenge reqLeaveChallenge);

    @POST("indexm")
    Call<ResGetContestDetails> getContestDetails(@Body ReqGetContestDetails reqGetContestDetails);

    @POST("indexm")
    Call<ResFsStoreProductsList> getFsStoreProductsList(@Body ReqFsStoreProductsList reqFsStoreProductsList);

    @POST("indexm")
    Call<ResFsStore> getFsStore(@Body ReqFsStore reqFsStore);

    @POST("indexm")
    Call<ResGetFsPoints> getFsStorePoints(@Body ReqFsPoints reqFsPoints);

    @POST("indexm")
    Call<ResUserCalorieDistance> getUserCalorieDistance(@Body ReqUserCalorieDistance reqUserCalorieDistance);

    @POST("indexm")
    Call<ResDeliveryStatus> getDeliveryStatus(@Body ReqDeliveryStatus reqDeliveryStatus);

    @POST("indexm")
    Call<ResMyCart> getCartDetails(@Body ReqMyCart reqMyCart);

    @POST("indexm")
    Call<ResBase> removeCart(@Body ReqRemoveCart reqRemoveCart);

    @POST("indexm")
    Call<ResBase> addCart(@Body ReqAddToCart reqAddToCart);

    @POST("indexm")
    Call<ResPlaceOrder> placeOrder(@Body ReqPlaceOrder reqPlaceOrder);

    @POST("indexm")
    Call<ResMyOrders> getMyOrders(@Body ReqMyOrders reqMyOrders);

    @POST("indexm")
    Call<ResGetOrderDetail> getOrderDetail(@Body ReqGetOrderDetail reqGetOrderDetail);

    @POST("indexm")
    Call<ResBase> cancelOrder(@Body ReqCancelOrder reqCancelOrder);

    @POST("indexm")
    Call<ResBase> proceedOrder(@Body ReqProceed reqProceed);

    @POST("indexm")
    Call<ResBase> repeatOrder(@Body ReqRepeatOrder reqRepeatOrder);

    @POST("indexm")
    Call<ResContactUs> conatctUs(@Body ReqBase reqBase);

    @POST("indexm")
    Call<ResMyWallet> myWallet(@Body ReqMyWallet reqMyWallet);

    @POST("indexm")
    Call<ResBase> sendToBank(@Body ReqSendToBank reqSendToBank);

    @POST("indexm")
    Call<ResWalletHistoryReceived> getWalletHistoryReceived(@Body ReqWalletHistory reqWalletHistory);

    @POST("indexm")
    Call<ResWalletHistoryPaid> getWalletHistoryPaid(@Body ReqWalletHistory reqWalletHistory);

    @POST("indexm")
    Call<ResCashBackHistory> getCashbackHistory(@Body ReqCashbackHistory reqCashbackHistory);

    @POST("indexm")
    Call<ResCancellationDetails> getCancellationDetails(@Body ReqCancellationDetails reqCancellationDetails);

    @POST("indexm")
    Call<ResPrizeMoneyDetails> getPrizeMoneyDetails(@Body ReqPrizeMoneyDetails reqPrizeMoneyDetails);

    @POST("indexm")
    Call<ResRunkeeperData> getRunkeeperData(@Body ReqRunkeeperToken reqRunkeeperToken);

    @POST("indexm")
    Call<ResRunkeeperData> getFitbitData(@Body ReqFitbitToken reqFitbitToken);

    @POST("indexm")
    Call<ResPushNotification> getPushNotification(@Body ReqGetPushNotification reqGetPushNotification);

    @POST("indexm")
    Call<ResGetUserPointsByTime> getUserPointsByTime(@Body ReqGetUserPointsByTime reqGetUserPointsByTime);

    @POST("indexm")
    Call<ResBase> deleteAllNotification(@Body ReqDeleteAllNotification reqDeleteAllNotification);

    @POST("indexm")
    Call<ResBase> connectDisconnectApp(@Body ReqConnectDisconnectApps reqConnectDisconnectApps);

    @POST("indexm")
    Call<ResBase> redeemPointsAffiliate(@Body ReqRedeemPointsAffiliate reqRedeemPointsAffiliate);

    @POST("indexm")
    Call<ResBase> updateCartDetails(@Body ReqUpdateCart reqUpdateCart);

    @Multipart
    @POST("indexm")
    Call<ResUpdateProfile> updateProfile(@Part("method") RequestBody method,
                                         @Part("service_key") RequestBody serviceKey,
                                         @Part("userID") RequestBody userID,
                                         @Part("name") RequestBody name,
                                         @Part("phone") RequestBody phone,
                                         @Part("dob") RequestBody dob,
                                         @Part("locationName") RequestBody locationName,
                                         @Part("height") RequestBody height,
                                         @Part("weight") RequestBody weight,
                                         @Part MultipartBody.Part image);

    @Multipart
    @POST("indexm")
    Call<ResUpdateProfile> updateProfileWithoutImage(@Part("method") RequestBody method,
                                                     @Part("service_key") RequestBody serviceKey,
                                                     @Part("userID") RequestBody userID,
                                                     @Part("name") RequestBody name,
                                                     @Part("phone") RequestBody phone,
                                                     @Part("dob") RequestBody dob,
                                                     @Part("locationName") RequestBody locationName,
                                                     @Part("height") RequestBody height,
                                                     @Part("weight") RequestBody weight,
                                                     @Part("image") RequestBody image);

    @Multipart
    @POST("indexm")
    Call<ResUpdateProfile> updateSettings(@Part("method") RequestBody method,
                                          @Part("service_key") RequestBody serviceKey,
                                          @Part("userID") RequestBody userID,
                                          @Part("unit") RequestBody unit,
                                          @Part("notification") RequestBody notification,
                                          @Part("resetImage") RequestBody resetImage,
                                          @Part MultipartBody.Part homeScreen);

    @Multipart
    @POST("indexm")
    Call<ResUpdateProfile> updateSettingsWithoutImage(@Part("method") RequestBody method,
                                                      @Part("service_key") RequestBody serviceKey,
                                                      @Part("userID") RequestBody userID,
                                                      @Part("unit") RequestBody unit,
                                                      @Part("notification") RequestBody notification,
                                                      @Part("resetImage") RequestBody resetImage,
                                                      @Part("homeScreen") RequestBody homeScreen);

    @POST("indexm")
    Call<ResBase> setFavouriteCouponDetail(@Body ReqCouponsDetail reqCouponsDetail);

    @GET("http://api.runkeeper.com/records")
    Call<ResponseBody> getTotalDistance(@Header("Authorization") String authorization,
                                        @Header("Accept") String accept);

    @POST("https://runkeeper.com/apps/token")
    Call<ResponseBody> getAccessToken(@Query("grant_type") String grantKey,
                                      @Query("code") String code,
                                      @Query("client_id") String clientId,
                                      @Query("client_secret") String clientSecret,
                                      @Query("redirect_uri") String redirectUri);
}
