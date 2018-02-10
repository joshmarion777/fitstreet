package affle.com.fitstreet.network;

/**
 * Created by root on 6/14/16.
 */
public enum MethodFactory {
    SIGN_UP("signup"),
    LOGIN("login"),
    FORGOT_PASSWORD("forgetPassword"),
    GET_USER_DETAILS("getUserDetails"),
    ADD_USER_ADDRESS("addUserAddress"),
    GET_USER_ADDRESS("getUserAddress"),
    CHANGE_PASSWORD("changePassword"),
    UPDATE_PROFILE("updateProfile"),
    UPDATE_SETTINGS("updateSetting"),
    GET_ALL_CATEGORY("getAllCategory"),
    GET_FAVOURITE_PRODUCTS("getFavouriteProducts"),
    GET_TRENDING_PRODUCTS("getTrendingProducts"),
    GET_FAVOURITE_COUPONS("getFavouriteCoupons"),
    GET_TRENDING_COUPONS("getTrendingCoupans"),
    GET_ALL_COUPONS_LIST("getCategoryCoupans"),
    FAVOURITE_COUPON("addFavouriteCoupons"),
    FAVOURITE_PRODUCT("addFavouriteProducts"),
    UNFAVOURITE_COUPON("deleteFavouriteCoupons"),
    UNFAVOURITE_PRODUCT("deleteFavouriteProducts"),
    GET_PRODUCT_CATEGORIES("getAllProdcutCategory"),
    GET_PRODUCT_DETAIL("getProductDetails"),
    GET_PRODUCTS_LIST("getCategoryProducts"),
    DELETE_USER_ADDRESS("deleteUserAddress"),
    GET_FILTERS("getPartnerWithBrand"),
    GET_COUPONS_DETAIL("getCouponDetails"),
    SHARE_AND_INVITE("shareAndInviteFriend"),
    GET_CONTESTS("getContests"),
    JOIN_CHALLENGE("joinContests"),
    LEAVE_CHALLENGE("leaveContests"),
    GET_CONTEST_DETAILS("getContestsDetails"),
    GET_FS_STORE("getFsProdcutsBanner"),
    GET_FS_STORE_PRODUCTS_LIST("getFsProductList"),
    GET_FS_STORE_POINTS("getUserPoints"),
    GET_USER_CALORIE_DISTANCE("getUserCaloriesDistance"),
    GET_DELIVERY_STATUS("getPincodeExist"),
    GET_MY_CART("getCartDetails"),
    REMOVE_CART("deleteCartDetails"),
    ADD_CART("addCartDetails"),
    PLACE_ORDER("placeOrder"),
    GET_ORDERS("getOrderList"),
    GET_ORDERS_DETAIL("getOrderDetails"),
    CANCEL_ORDER("cancelOrder"),
    PROCEED_ORDER("proceedOrder"),
    REPEAT_ORDER("repeatOrder"),
    CONTACT_US("getContactDetails"),
    MY_WALLET("getWalletDetails"),
    SEND_TO_BANK("addBankDetails"),
    GET_CASHBACK_HISTORY("getCashbackHistory"),
    GET_WALLET_HISTORY("getWalletHistory"),
    GET_CANCELLATION_DETAILS("getCancellationHistory"),
    GET_PRIZE_MONEY_DETAILS("getPrizeDetails"),
    GET_RUNKEEPER_DATA("runkeeperLogin"),
    GET_FITBIT_DATA("fitbitLogin"),
    GET_PUSH_NOTIFICATION("getPushNotification"),
    GET_FS_POINTS_BY_TIME("getUserPoints"),
    DELETE_ALL_NOTIFICATIONS("deletePushNotification"),
    CONNECT_DISCONNECT_APP("disconnectAppsDevices"),
    REDEEM_POINTS_AFFLIATE("addAffilateReddemPoint"),
    UPDATE_CART_DETAILS("updateCartDetails");

    private String method;

    MethodFactory(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }
}