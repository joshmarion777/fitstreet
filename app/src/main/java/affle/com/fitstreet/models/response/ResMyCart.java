package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 12/9/16.
 */
public class ResMyCart extends ResBase {
    @SerializedName("cartData")
    @Expose
    private List<CartDatum> cartData = new ArrayList<CartDatum>();
    @SerializedName("favCount")
    @Expose
    private String favCount;

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
     * The favCount
     */
    public String getFavCount() {
        return favCount;
    }

    /**
     *
     * @param favCount
     * The favCount
     */
    public void setFavCount(String favCount) {
        this.favCount = favCount;
    }

    public class CartDatum{
        @SerializedName("cartID")
        @Expose
        private String cartID;
        @SerializedName("productID")
        @Expose
        private String productID;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("selectedSize")
        @Expose
        private String selectedSize;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("size")
        @Expose
        private List<String> size = new ArrayList<String>();
        @SerializedName("selectedQuantity")
        @Expose
        private String selectedQuantity;
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
         * The selectedSize
         */
        public String getSelectedSize() {
            return selectedSize;
        }

        /**
         *
         * @param selectedSize
         * The selectedSize
         */
        public void setSelectedSize(String selectedSize) {
            this.selectedSize = selectedSize;
        }

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         *
         * @return
         * The price
         */
        public String getPrice() {
            return price;
        }

        /**
         *
         * @param price
         * The price
         */
        public void setPrice(String price) {
            this.price = price;
        }

        /**
         *
         * @return
         * The discount
         */
        public String getDiscount() {
            return discount;
        }

        /**
         *
         * @param discount
         * The discount
         */
        public void setDiscount(String discount) {
            this.discount = discount;
        }

        /**
         *
         * @return
         * The image
         */
        public String getImage() {
            return image;
        }

        /**
         *
         * @param image
         * The image
         */
        public void setImage(String image) {
            this.image = image;
        }

        /**
         *
         * @return
         * The size
         */
        public List<String> getSize() {
            return size;
        }

        /**
         *
         * @param size
         * The size
         */
        public void setSize(List<String> size) {
            this.size = size;
        }

        public String getSelectedQuantity() {
            return selectedQuantity;
        }

        public void setSelectedQuantity(String selectedQuantity) {
            this.selectedQuantity = selectedQuantity;
        }
    }}
