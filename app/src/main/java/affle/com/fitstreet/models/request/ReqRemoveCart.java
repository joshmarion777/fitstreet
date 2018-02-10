package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 13/9/16.
 */
public class ReqRemoveCart extends ReqBase {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("cartID")
    @Expose
    private String cartID;

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
     * The cartID
     */
    public String getCartID() {
        return cartID;
    }

    /**
     *
     * @param cartID
     * The cartID
     */
    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

}

