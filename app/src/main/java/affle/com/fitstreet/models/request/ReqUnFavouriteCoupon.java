package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 13/7/16.
 */
public class ReqUnFavouriteCoupon extends ReqBase {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("coupanID")
    @Expose
    private String coupanID;

    /**
     * @return The userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID The userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return The coupanID
     */
    public String getCoupanID() {
        return coupanID;
    }

    /**
     * @param coupanID The coupanID
     */
    public void setCoupanID(String coupanID) {
        this.coupanID = coupanID;
    }

}
