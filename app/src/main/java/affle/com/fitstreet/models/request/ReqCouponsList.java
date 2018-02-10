package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 4/7/16.
 */
public class ReqCouponsList extends ReqBase {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("catID")
    @Expose
    private String catID;
    @SerializedName("filterType")
    @Expose
    private FilterType filterType;

    /**
     * @return The userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID The userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return The catID
     */
    public String getCatID() {
        return catID;
    }

    /**
     * @param catID The catID
     */
    public void setCatID(String catID) {
        this.catID = catID;
    }

    /**
     * @return The filterType
     */
    public FilterType getFilterType() {
        return filterType;
    }

    /**
     * @param filterType The filterType
     */
    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public static class FilterType {

        @SerializedName("partnerID")
        @Expose
        private List<String> partnerID = new ArrayList<String>();

        /**
         * @return The partnerID
         */
        public List<String> getPartnerID() {
            return partnerID;
        }

        /**
         * @param partnerID The partnerID
         */
        public void setPartnerID(List<String> partnerID) {
            this.partnerID = partnerID;
        }

    }

}