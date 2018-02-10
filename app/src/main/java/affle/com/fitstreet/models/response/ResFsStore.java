package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 5/8/16.
 */
public class ResFsStore extends ResBase {
    @SerializedName("productBanner")
    @Expose
    private ArrayList<ProductBanner> productBanner = new ArrayList<ProductBanner>();
    @SerializedName("shopManUrl")
    @Expose
    private String shopManUrl;
    @SerializedName("shopWomanUrl")
    @Expose
    private String shopWomanUrl;
    private List<ProductBanner.FsProductData> fsProductData = new ArrayList<ProductBanner.FsProductData>();
    private String favCount;
    @SerializedName("cartCount")
    @Expose
    private String cartCount;

    /**
     * @return The productBanner
     */
    public ArrayList<ProductBanner> getProductBanner() {
        return productBanner;
    }


    public List<ProductBanner.FsProductData> getFsProductData() {
        return fsProductData;
    }



    /**
     *
     * @param fsProductData
     * The fsProductData
     */
    public void setFsProductData(List<ProductBanner.FsProductData> fsProductData) {
        this.fsProductData = fsProductData;
    }

    /**
     * @return The favCount
     */
    public String getFavCount() {
        return favCount;
    }

    /**
     * @param favCount The favCount
     */
    public void setFavCount(String favCount) {
        this.favCount = favCount;
    }




    /**
     *
     * @return
     * The cartCount
     */
    public String getCartCount() {
        return cartCount;
    }

    /**
     *
     * @param cartCount
     * The cartCount
     */
    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }


    /**
     * @param productBanner The productBanner
     */
    public void setProductBanner(ArrayList<ProductBanner> productBanner) {
        this.productBanner = productBanner;
    }

    /**
     * @return The shopManUrl
     */
    public String getShopManUrl() {
        return shopManUrl;
    }

    /**
     * @param shopManUrl The shopManUrl
     */
    public void setShopManUrl(String shopManUrl) {
        this.shopManUrl = shopManUrl;
    }

    /**
     * @return The shopWomanUrl
     */
    public String getShopWomanUrl() {
        return shopWomanUrl;
    }

    /**
     * @param shopWomanUrl The shopWomanUrl
     */
    public void setShopWomanUrl(String shopWomanUrl) {
        this.shopWomanUrl = shopWomanUrl;
    }


    public class ProductBanner {

        @SerializedName("bannerID")
        @Expose
        private String bannerID;
        @SerializedName("bannerImage")
        @Expose
        private String bannerImage;

        /**
         * @return The bannerID
         */
        public String getBannerID() {
            return bannerID;
        }

        /**
         * @param bannerID The bannerID
         */
        public void setBannerID(String bannerID) {
            this.bannerID = bannerID;
        }

        /**
         * @return The bannerImage
         */
        public String getBannerImage() {
            return bannerImage;
        }

        /**
         * @param bannerImage The bannerImage
         */
        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }
        public class FsProductData {

            @SerializedName("productID")
            @Expose
            private String productID;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("mGender")
            @Expose
            private String gender;
            @SerializedName("discount")
            @Expose
            private String discount;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("favourite")
            @Expose
            private int favourite;

            /**
             * @return The productID
             */
            public String getProductID() {
                return productID;
            }

            /**
             * @param productID The productID
             */
            public void setProductID(String productID) {
                this.productID = productID;
            }

            /**
             * @return The image
             */
            public String getImage() {
                return image;
            }

            /**
             * @param image The image
             */
            public void setImage(String image) {
                this.image = image;
            }

            /**
             * @return The name
             */
            public String getName() {
                return name;
            }

            /**
             * @param name The name
             */
            public void setName(String name) {
                this.name = name;
            }

            /**
             * @return The mGender
             */
            public String getGender() {
                return gender;
            }

            /**
             * @param gender The mGender
             */
            public void setGender(String gender) {
                this.gender = gender;
            }

            /**
             * @return The discount
             */
            public String getDiscount() {
                return discount;
            }

            /**
             * @param discount The discount
             */
            public void setDiscount(String discount) {
                this.discount = discount;
            }

            /**
             * @return The price
             */
            public String getPrice() {
                return price;
            }

            /**
             * @param price The price
             */
            public void setPrice(String price) {
                this.price = price;
            }

            /**
             * @return The favourite
             */
            public int getFavourite() {
                return favourite;
            }

            /**
             * @param favourite The favourite
             */
            public void setFavourite(int favourite) {
                this.favourite = favourite;
            }

        }

    }
}

