package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import affle.com.fitstreet.models.response.ResMyCart;

/**
 * Created by akash on 15/9/16.
 */
public class ReqPlaceOrder extends ReqBase {
    @SerializedName("cartData")
    @Expose
    private List<CartDatum> cartData = new ArrayList<CartDatum>();
    @SerializedName("userID")
    @Expose
    private String userID;

    /**
     *
     * @return
     * The cartData
     */
    public List<CartDatum> getCartData() {
        return cartData;
    }

    /**
     *
     * @param cartData
     * The cartData
     */
    public void setCartData(List<CartDatum> cartData) {
        this.cartData = cartData;
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


    public static class CartDatum {

        @SerializedName("cartID")
        @Expose
        private String cartID;
        @SerializedName("size")
        @Expose
        private String size;
        @SerializedName("quantity")
        @Expose
        private String quantity;

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

    }
}
