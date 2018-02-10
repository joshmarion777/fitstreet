package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apps on 10/10/16.
 */

public class ResWalletHistoryPaid extends ResBase{
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
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("accountHolderName")
        @Expose
        private String accountHolderName;
        @SerializedName("accountNumber")
        @Expose
        private String accountNumber;
        @SerializedName("bankName")
        @Expose
        private String bankName;
        @SerializedName("ifscCode")
        @Expose
        private String ifscCode;
        @SerializedName("createdTime")
        @Expose
        private String createdTime;
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
         * @return The amount
         */
        public Integer getAmount() {
            return amount;
        }

        /**
         * @param amount The amount
         */
        public void setAmount(Integer amount) {
            this.amount = amount;
        }
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
        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
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
         * @return The accountHolderName
         */
        public String getAccountHolderName() {
            return accountHolderName;
        }

        /**
         * @param accountHolderName The accountHolderName
         */
        public void setAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
        }

        /**
         * @return The accountNumber
         */
        public String getAccountNumber() {
            return accountNumber;
        }

        /**
         * @param accountNumber The accountNumber
         */
        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        /**
         * @return The bankName
         */
        public String getBankName() {
            return bankName;
        }

        /**
         * @param bankName The bankName
         */
        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        /**
         * @return The ifscCode
         */
        public String getIfscCode() {
            return ifscCode;
        }

        /**
         * @param ifscCode The ifscCode
         */
        public void setIfscCode(String ifscCode) {
            this.ifscCode = ifscCode;
        }

    }

}
