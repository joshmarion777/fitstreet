package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 18/7/16.
 */
public class ReqTrendingCoupons extends ReqBase {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("search")
    @Expose
    private String search;
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
     * @return The search
     */
    public String getSearch() {
        return search;
    }

    /**
     * @param search The search
     */
    public void setSearch(String search) {
        this.search = search;
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
