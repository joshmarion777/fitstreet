package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 31/8/16.
 */
public class ResFsStoreProductsList {

    @SerializedName("errstr")
    @Expose
    private String errstr;
    @SerializedName("errorCode")
    @Expose
    private int errorCode;
    @SerializedName("success")
    @Expose
    private int success;
    @SerializedName("fsProductData")
    @Expose
    private List<FsProductDatum> fsProductData = new ArrayList<FsProductDatum>();
    @SerializedName("favCount")
    @Expose
    private String favCount;
    @SerializedName("fsCatData")
    @Expose
    private List<FsCatDatum> fsCatData = new ArrayList<FsCatDatum>();

    /**
     * @return The errstr
     */
    public String getErrstr() {
        return errstr;
    }

    /**
     * @param errstr The errstr
     */
    public void setErrstr(String errstr) {
        this.errstr = errstr;
    }

    /**
     * @return The errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode The errorCode
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return The success
     */
    public int getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(int success) {
        this.success = success;
    }

    /**
     * @return The fsProductData
     */
    public List<FsProductDatum> getFsProductData() {
        return fsProductData;
    }

    /**
     * @param fsProductData The fsProductData
     */
    public void setFsProductData(List<FsProductDatum> fsProductData) {
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
     * @return The fsCatData
     */
    public List<FsCatDatum> getFsCatData() {
        return fsCatData;
    }

    /**
     * @param fsCatData The fsCatData
     */
    public void setFsCatData(List<FsCatDatum> fsCatData) {
        this.fsCatData = fsCatData;
    }

    public class FsCatDatum {

        @SerializedName("proCatID")
        @Expose
        private String proCatID;
        @SerializedName("proCatName")
        @Expose
        private String proCatName;

        /**
         * @return The proCatID
         */
        public String getProCatID() {
            return proCatID;
        }

        /**
         * @param proCatID The proCatID
         */
        public void setProCatID(String proCatID) {
            this.proCatID = proCatID;
        }

        /**
         * @return The proCatName
         */
        public String getProCatName() {
            return proCatName;
        }

        /**
         * @param proCatName The proCatName
         */
        public void setProCatName(String proCatName) {
            this.proCatName = proCatName;
        }

    }

    public class FsProductDatum {

        @SerializedName("productID")
        @Expose
        private String productID;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("name")
        @Expose
        private String name;
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
