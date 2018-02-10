package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apps on 10/10/16.
 */

public class ResWalletHistoryReceived extends ResBase {
    @SerializedName("walletData")
    @Expose
    private List<WalletDatum> walletData = new ArrayList<WalletDatum>();


    /**
     * @return The walletData
     */
    public List<WalletDatum> getWalletData() {
        return walletData;
    }

    /**
     * @param walletData The walletData
     */
    public void setWalletData(List<WalletDatum> walletData) {
        this.walletData = walletData;
    }

    public class WalletDatum {

        @SerializedName("orderID")
        @Expose
        private String orderID;
        @SerializedName("productID")
        @Expose
        private String productID;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("redeemPoint")
        @Expose
        private String redeemPoint;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("contestID")
        @Expose
        private String contestID;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("rank")
        @Expose
        private String rank;
        @SerializedName("createdTime")
        @Expose
        private String createdTime;
        @SerializedName("name")
        @Expose
        private Object name;
        @SerializedName("productType")
        @Expose
        private Object productType;
        @SerializedName("affilateType")
        @Expose
        private Object affilateType;
        @SerializedName("status")
        @Expose
        private Object status;
        @SerializedName("contestName")
        @Expose
        private String contestName;
        @SerializedName("image")
        @Expose
        private String image;

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
         * @return The type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return The redeemPoint
         */
        public String getRedeemPoint() {
            return redeemPoint;
        }

        /**
         * @param redeemPoint The redeemPoint
         */
        public void setRedeemPoint(String redeemPoint) {
            this.redeemPoint = redeemPoint;
        }

        /**
         * @return The amount
         */
        public String getAmount() {
            return amount;
        }

        /**
         * @param amount The amount
         */
        public void setAmount(String amount) {
            this.amount = amount;
        }

        /**
         * @return The contestID
         */
        public String getContestID() {
            return contestID;
        }

        /**
         * @param contestID The contestID
         */
        public void setContestID(String contestID) {
            this.contestID = contestID;
        }

        /**
         * @return The distance
         */
        public String getDistance() {
            return distance;
        }

        /**
         * @param distance The distance
         */
        public void setDistance(String distance) {
            this.distance = distance;
        }

        /**
         * @return The rank
         */
        public String getRank() {
            return rank;
        }

        /**
         * @param rank The rank
         */
        public void setRank(String rank) {
            this.rank = rank;
        }

        /**
         * @return The createdTime
         */
        public String getCreatedTime() {
            return createdTime;
        }

        /**
         * @param createdTime The createdTime
         */
        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        /**
         * @return The name
         */
        public Object getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(Object name) {
            this.name = name;
        }

        /**
         * @return The productType
         */
        public Object getProductType() {
            return productType;
        }

        /**
         * @param productType The productType
         */
        public void setProductType(Object productType) {
            this.productType = productType;
        }

        /**
         * @return The affilateType
         */
        public Object getAffilateType() {
            return affilateType;
        }

        /**
         * @param affilateType The affilateType
         */
        public void setAffilateType(Object affilateType) {
            this.affilateType = affilateType;
        }

        /**
         * @return The status
         */
        public Object getStatus() {
            return status;
        }

        /**
         * @param status The status
         */
        public void setStatus(Object status) {
            this.status = status;
        }

        /**
         * @return The contestName
         */
        public String getContestName() {
            return contestName;
        }

        /**
         * @param contestName The contestName
         */
        public void setContestName(String contestName) {
            this.contestName = contestName;
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
    }
}
