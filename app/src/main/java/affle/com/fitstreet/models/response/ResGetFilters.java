package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 7/19/16.
 */
public class ResGetFilters extends ResBase {
    @SerializedName("partnerData")
    @Expose
    private List<ResPartnerData> partnerData = new ArrayList<ResPartnerData>();
    @SerializedName("brandData")
    @Expose
    private List<ResBrandData> brandData = new ArrayList<ResBrandData>();

    /**
     * @return The partnerData
     */
    public List<ResPartnerData> getPartnerData() {
        return partnerData;
    }

    /**
     * @param partnerData The partnerData
     */
    public void setPartnerData(List<ResPartnerData> partnerData) {
        this.partnerData = partnerData;
    }

    /**
     * @return The brandData
     */
    public List<ResBrandData> getBrandData() {
        return brandData;
    }

    /**
     * @param brandData The brandData
     */
    public void setBrandData(List<ResBrandData> brandData) {
        this.brandData = brandData;
    }

    public class ResPartnerData {

        @SerializedName("partnerID")
        @Expose
        private String partnerID;
        @SerializedName("partnerName")
        @Expose
        private String partnerName;

        /**
         * @return The partnerID
         */
        public String getPartnerID() {
            return partnerID;
        }

        /**
         * @param partnerID The partnerID
         */
        public void setPartnerID(String partnerID) {
            this.partnerID = partnerID;
        }

        /**
         * @return The partnerName
         */
        public String getPartnerName() {
            return partnerName;
        }

        /**
         * @param partnerName The partnerName
         */
        public void setPartnerName(String partnerName) {
            this.partnerName = partnerName;
        }
    }

    public class ResBrandData {

        @SerializedName("brandID")
        @Expose
        private String brandID;
        @SerializedName("brandName")
        @Expose
        private String brandName;

        /**
         * @return The brandID
         */
        public String getBrandID() {
            return brandID;
        }

        /**
         * @param brandID The brandID
         */
        public void setBrandID(String brandID) {
            this.brandID = brandID;
        }

        /**
         * @return The brandName
         */
        public String getBrandName() {
            return brandName;
        }

        /**
         * @param brandName The brandName
         */
        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }
    }
}
