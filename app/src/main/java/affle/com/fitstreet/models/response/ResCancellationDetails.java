package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 12/10/16.
 */
public class ResCancellationDetails extends ResBase {
    @SerializedName("cancellationData")
    @Expose
    private List<CancellationDatum> cancellationData = new ArrayList<CancellationDatum>();

    /**
     * @return The cancellationData
     */
    public List<CancellationDatum> getCancellationData() {
        return cancellationData;
    }

    /**
     * @param cancellationData The cancellationData
     */
    public void setCancellationData(List<CancellationDatum> cancellationData) {
        this.cancellationData = cancellationData;
    }

    public class CancellationDatum {

        @SerializedName("orderID")
        @Expose
        private String orderID;
        @SerializedName("productID")
        @Expose
        private String productID;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("createdTime")
        @Expose
        private String createdTime;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("productType")
        @Expose
        private String productType;
        @SerializedName("affilateType")
        @Expose
        private String affilateType;
        @SerializedName("status")
        @Expose
        private String status;

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
         * @return The productType
         */
        public String getProductType() {
            return productType;
        }

        /**
         * @param productType The productType
         */
        public void setProductType(String productType) {
            this.productType = productType;
        }

        /**
         * @return The affilateType
         */
        public String getAffilateType() {
            return affilateType;
        }

        /**
         * @param affilateType The affilateType
         */
        public void setAffilateType(String affilateType) {
            this.affilateType = affilateType;
        }

        /**
         * @return The status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status The status
         */
        public void setStatus(String status) {
            this.status = status;
        }
    }
}
