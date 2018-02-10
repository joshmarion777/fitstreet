package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 13/9/16.
 */
public class ReqAddToCart extends ReqBase {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("pointToRedeem")
    @Expose
    private String pointToRedeem;

    public ReqAddToCart() {
    }

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
     * The size
     */
    public String getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(String size) {
        this.size = size;
    }

    public String getPointToRedeem() {
        return pointToRedeem;
    }

    public void setPointToRedeem(String pointToRedeem) {
        this.pointToRedeem = pointToRedeem;
    }
}
