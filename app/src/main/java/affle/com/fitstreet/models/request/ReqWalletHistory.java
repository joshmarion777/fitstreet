package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by apps on 10/10/16.
 */

public class ReqWalletHistory {
        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("method")
        @Expose
        private String method;
        @SerializedName("service_key")
        @Expose
        private String serviceKey;
        @SerializedName("walletStatus")
        @Expose
        private String walletStatus;

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
         * The method
         */
        public String getMethod() {
            return method;
        }

        /**
         *
         * @param method
         * The method
         */
        public void setMethod(String method) {
            this.method = method;
        }

        /**
         *
         * @return
         * The serviceKey
         */
        public String getServiceKey() {
            return serviceKey;
        }

        /**
         *
         * @param serviceKey
         * The service_key
         */
        public void setServiceKey(String serviceKey) {
            this.serviceKey = serviceKey;
        }

        /**
         *
         * @return
         * The walletStatus
         */
        public String getWalletStatus() {
            return walletStatus;
        }

        /**
         *
         * @param walletStatus
         * The walletStatus
         */
        public void setWalletStatus(String walletStatus) {
            this.walletStatus = walletStatus;
        }
}
