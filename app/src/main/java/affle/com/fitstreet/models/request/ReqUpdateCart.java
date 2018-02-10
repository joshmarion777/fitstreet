package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by apps on 19/11/16.
 */
public class ReqUpdateCart extends ReqBase {
    @SerializedName("cartID")
    @Expose
    private String cartID;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("userID")
    @Expose
    private String userID;

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

    /**
     *
     * @return
     * The quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     * The quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
}
