package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 6/9/16.
 */
public class ResDeliveryStatus extends ResBase {
    @SerializedName("pincodeData")
    @Expose
    private PincodeData pincodeData;
    /**
     * @return The pincodeData
     */
    public PincodeData getPincodeData() {
        return pincodeData;
    }

    /**
     * @param pincodeData The pincodeData
     */
    public void setPincodeData(PincodeData pincodeData) {
        this.pincodeData = pincodeData;
    }

    public class PincodeData {

        @SerializedName("shipID")
        @Expose
        private String shipID;
        @SerializedName("minDays")
        @Expose
        private String minDays;
        @SerializedName("maxDays")
        @Expose
        private String maxDays;
        @SerializedName("pincode")
        @Expose
        private String pincode;

        /**
         * @return The shipID
         */
        public String getShipID() {
            return shipID;
        }

        /**
         * @param shipID The shipID
         */
        public void setShipID(String shipID) {
            this.shipID = shipID;
        }

        /**
         * @return The minDays
         */
        public String getMinDays() {
            return minDays;
        }

        /**
         * @param minDays The minDays
         */
        public void setMinDays(String minDays) {
            this.minDays = minDays;
        }

        /**
         * @return The maxDays
         */
        public String getMaxDays() {
            return maxDays;
        }

        /**
         * @param maxDays The maxDays
         */
        public void setMaxDays(String maxDays) {
            this.maxDays = maxDays;
        }

        /**
         * @return The pincode
         */
        public String getPincode() {
            return pincode;
        }

        /**
         * @param pincode The pincode
         */
        public void setPincode(String pincode) {
            this.pincode = pincode;
        }
    }
}
