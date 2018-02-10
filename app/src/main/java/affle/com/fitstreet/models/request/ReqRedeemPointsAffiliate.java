package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 15/11/16.
 */
public class ReqRedeemPointsAffiliate extends ReqBase {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("pointToRedeem")
    @Expose
    private String pointToRedeem;

    /**
     *
     * @return
     * The userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     * The userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     *
     * @return
     * The productID
     */
    public String getProductID() {
        return productID;
    }

    /**
     *
     * @param productID
     * The productID
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     *
     * @return
     * The pointToRedeem
     */
    public String getPointToRedeem() {
        return pointToRedeem;
    }

    /**
     *
     * @param pointToRedeem
     * The pointToRedeem
     */
    public void setPointToRedeem(String pointToRedeem) {
        this.pointToRedeem = pointToRedeem;
    }

}
