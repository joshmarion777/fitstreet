package affle.com.fitstreet.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by root on 3/4/16.
 */
public class AppConstants {
    public static final String FITSTREET_DIRECTORY_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + ".fitstreet";
    public static final String FITSTREET_IMAGE_PATH = File.separator + "image.jpg";
    public static final String FITSTREET_FITNESS_IMAGE_PATH = File.separator + "fitness_image.jpg";
    //    public static final String FITSTREET_COUPON_IMAGE_PATH = File.separator + "coupon_image.jpg";
    public static final String FITSTREET_COUPON_IMAGE_PATH = File.separator + "coupon_image";
    public static final String FITSTREET_PRODUCT_IMAGE_PATH = File.separator + "product_image";
    public static final String EXTN_PNG = ".png";
    public static final String EXTN_JPG = ".jpg";
    //    public static final String FITSTREET_IMAGE_PATH = File.separator + "image.png";
//    public static final String FITSTREET_FITNESS_IMAGE_PATH = File.separator + "fitness_image.png";
    public static final int MIN_LENGTH_PASSWORD = 8;
    public static final int MAX_LENGTH_PASSWORD = 15;
    //    public static final String URL_ABOUT_US = "http://fitstreet.appstudioz.co.in/docs/AboutUs_final.docx.pdf";
    public static final String URL_ABOUT_US = "http://fitstreet.com/docs/about_us.html";
    //    public static final String URL_PRIVACY_POLICY = "http://fitstreettest.w3studioz.com/privacy-policy";
//    public static final String URL_PRIVACY_POLICY = "http://fitstreet.appstudioz.co.in/docs/PRIVACYPOLICY.docx.pdf";
    public static final String URL_PRIVACY_POLICY = "http://fitstreet.com/docs/privacypolicy.html";

    //public static final String URL_TERMS_OF_USE = "http://fitstreettest.w3studioz.com/terms-of-use";
//    public static final String URL_TERMS_OF_USE = "http://fitstreet.appstudioz.co.in/docs/TermsofService.docx.pdf";
    public static final String URL_TERMS_OF_USE = " http://fitstreet.com/docs/Termsofuse.html";

    //public static final String URL_REFUND_POLICY = "http://fitstreet.appstudioz.co.in/docs/RefundPolicy.pdf";
    public static final String URL_REFUND_POLICY = " http://fitstreet.com/docs/refund_policy.html";

    public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,15}";
    //    public static final String TYPE_CITY = "cities";
    public static final String TYPE_CITY = "geocode";
    public static final int FROM_PROFILE = 0;
    public static final int FROM_ADDRESS = 1;
    public static final int FROM_SHARE_IMAGE = 0;
    public static final int FROM_LOGIN = 1;
    public static final int FROM_RESET_IMAGE = 0;
    public static final int FROM_COUPONS = 0;
    public static final int FROM_PRODUCTS = 1;

    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;
    public static final int FROM_PLACE_ORDER = 3;


    //request codes
    public static final int RC_LOGIN_SIGN_UP = 101;
    public static final int RC_CHOOSE_LOCATION = 102;
    public static final int RC_CAMERA_INTENT = 103;
    public static final int RC_GALLERY_INTENT = 104;
    public static final int RC_EDIT_PROFILE = 105;
    public static final int RC_RECOMMENDED = 107;
    public static final int RC_SETTINGS = 108;
    public static final int RC_ADD_ADDRESS = 109;
    public static final int RC_PRODUCTS_DETAIL = 110;
    public static final int RC_CHALLENGES_FRAGMENT = 111;
    public static final int RC_FAVORITES = 112;
    public static final int RC_COUPON_LIST = 113;
    public static final int RC_SHARE_DIALOG = 114;
    public static final int RC_FS_STORE_PRODUCT_DETAIL = 115;
    public static final int RC_FS_STORE_FRAGMENT = 116;
    public static final int RC_CHECKOUT_ACTIVITY = 117;
    public static final int RC_ORDER_DETAILS = 118;
    public static final int RC_MY_ORDERS = 119;
    public static final int RC_MY_WALLET_FRAGMENT = 120;
    public static final int RC_FROM_DETAILS_TO_MY_CART_ACTIVITY = 121;
    public static final int RC_FROM_MY_CART_ACTIVITY_TO_FAVOURITES_ACTIVITY = 122;
    public static final int RC_FROM_FS_STORE_FRAGMENT = 123;
    public static final int RC_FROM_FS_STORE_MEN_WOMEN_PRODUCTS_LISTING = 124;
    //intent tags
    public static final String TAG_IMG_RESOURCE_ID = "tag_image_res_id";
    public static final String TAG_TEXT_RESOURCE_ID = "tag_txt_res_id";
    public static final String TAG_TITLE_RESOURCE_ID = "tag_title_res_id";
    public static final String TAG_SOCIAL_MODEL = "tag_social_model";
    public static final String TAG_URL = "tag_url";
    public static final String TAG_PLACE_MODEL = "tag_place_model";
    public static final String TAG_FROM_WHERE = "tag_from_where";
    public static final String TAG_ADDRESS_MODEL = "tag_address_model";
    public static final String TAG_POSITION = "tag_position";
    public static final String TAG_IMAGE_PATH = "tag_image_path";
    public static final String TAG_HEIGHT = "tag_height";
    public static final String TAG_CONTEST_ID = "tag_contestId";
    public static final String COUPON_ID = "couponId";
    public static final String FAVOURITE = "favourite";
    public static final String PAYMENT_BY_CASH_ON_DELIVERY = "1";
    public static final String PAYMENT_BY_OTHER_OPTIONS = "2";
    public static final String PAYMENT_BY_WALLET = "3";
    public static final String USE_FS_POINTS = "1";
    public static final String DO_NOT_USE_FS_POINTS = "2";
    //used when user has already joined contest
    public static final String TAG_CONTEST_VIEW_TYPE = "tag_contest_view_type";
    public static final String PARTNER_ID = "partnerFilterId";

    //for filter
    public static final int FILTER_MIN = 1;
    public static final int FILTER_MAX = 10000;

    public static final String FITBIT_CONNECTED = "1";
    public static final String RUNKEEPER_CONNECTED = "2";
    public static final String NO_APP_CONNECTED = "3";
    public static final String FACEBOOK_URL = "https://www.facebook.com/fitstreetapperel/?fref=ts";
    public static final String TWITTER_URL = "https://twitter.com/fsapperel";
    public static final String INSTAGRAM_URL = "https://www.instagram.com/fitstreetapparel/";

    //Notification Types
    public static final int NOTIFICATION_ORDER_DISPATCHED = 1;
    public static final int NOTIFICATION_NEW_CHALLENGE_CREATED = 2;
    public static final int NOTIFICATION_CHALLENGE_WINNER = 3;
    public static final int NOTIFICATION_FS_POINTS_EARNED = 4;
    public static final int NOTIFICATION_PRODUCT_ADDED = 5;

    public static final String FROM_NOTIFICATION_ACTIVITY = "1";
    public static final int FROM_PRODUCTS_DETAIL_ACTIVITY = 1001;
    public static final int FROM_FS_STORE_FRAGMENT = 1002;
    public static final String FROM_NOTIFICATION_ON_STATUS_BAR = "201";
    public static final String FROM_MY_CART_ACTIVITY = "FromMyCartActivity";


}

