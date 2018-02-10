package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 26/9/16.
 */
public class ReqRepeatOrder extends ReqBase {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("orderID")
    @Expose
    private String orderID;

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
     * @return The orderID
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * @param orderID The orderID
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

}
