package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 21/9/16.
 */
public class ReqCancelOrder extends ReqBase {
        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("orderID")
        @Expose
        private String orderID;
        @SerializedName("proData")
        @Expose
        private List<ProDatum> proData = new ArrayList<ProDatum>();



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
         * The orderID
         */
        public String getOrderID() {
            return orderID;
        }

        /**
         *
         * @param orderID
         * The orderID
         */
        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        /**
         *
         * @return
         * The proData
         */
        public List<ProDatum> getProData() {
            return proData;
        }

        /**
         *
         * @param proData
         * The proData
         */
        public void setProData(List<ProDatum> proData) {
            this.proData = proData;
        }

    public static class ProDatum {

        @SerializedName("ordProMapID")
        @Expose
        private String ordProMapID;

        /**
         *
         * @return
         * The ordProMapID
         */
        public String getOrdProMapID() {
            return ordProMapID;
        }

        /**
         *
         * @param ordProMapID
         * The ordProMapID
         */
        public void setOrdProMapID(String ordProMapID) {
            this.ordProMapID = ordProMapID;
        }

    }
}
