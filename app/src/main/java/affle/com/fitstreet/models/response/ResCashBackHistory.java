package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 9/10/16.
 */

public class ResCashBackHistory {

    @SerializedName("errstr")
    @Expose
    private String errstr;
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("cashBackData")
    @Expose
    private List<CashBackDatum> cashBackData = new ArrayList<CashBackDatum>();

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
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode The errorCode
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return The success
     */
    public Integer getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }

    /**
     * @return The cashBackData
     */
    public List<CashBackDatum> getCashBackData() {
        return cashBackData;
    }

    /**
     * @param cashBackData The cashBackData
     */
    public void setCashBackData(List<CashBackDatum> cashBackData) {
        this.cashBackData = cashBackData;
    }

    public class CashBackDatum {

        @SerializedName("orderID")
        @Expose
        private String orderID;
        @SerializedName("productID")
        @Expose
        private String productID;
        @SerializedName("redeemPoint")
        @Expose
        private String redeemPoint;
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
        /**
         *
         * @return
         * The createdTime
         */
        public String getCreatedTime() {
            return createdTime;
        }

        /**
         *
         * @param createdTime
         * The createdTime
         */
        /**
         *
         * @return
         * The affilateType
         */
        public String getAffilateType() {
            return affilateType;
        }

        /**
         *
         * @param affilateType
         * The affilateType
         */
        public void setAffilateType(String affilateType) {
            this.affilateType = affilateType;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
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

    }
}


