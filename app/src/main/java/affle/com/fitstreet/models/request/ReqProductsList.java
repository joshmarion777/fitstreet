package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 14/7/16.
 */
public class ReqProductsList extends ReqBase {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("proCatID")
    @Expose
    private String proCatID;
    @SerializedName("search")
    @Expose
    private String search;
    @SerializedName("filterType")
    @Expose
    private FilterType filterType;
    @SerializedName("page")
    @Expose
    private String page;

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
     * @return The proCatID
     */
    public String getProCatID() {
        return proCatID;
    }

    /**
     * @param proCatID The proCatID
     */
    public void setProCatID(String proCatID) {
        this.proCatID = proCatID;
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
     *
     * @return
     * The page
     */
    public String getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(String page) {
        this.page = page;
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

        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("brandID")
        @Expose
        private List<String> brandID = new ArrayList<String>();
        @SerializedName("partnerID")
        @Expose
        private List<String> partnerID = new ArrayList<String>();
        @SerializedName("price_range")
        @Expose
        private PriceRange priceRange;

        /**
         * @return The mGender
         */
        public String getGender() {
            return gender;
        }

        /**
         * @param gender The mGender
         */
        public void setGender(String gender) {
            this.gender = gender;
        }

        /**
         * @return The brandID
         */
        public List<String> getBrandID() {
            return brandID;
        }

        /**
         * @param brandID The brandID
         */
        public void setBrandID(List<String> brandID) {
            this.brandID = brandID;
        }

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

        /**
         * @return The priceRange
         */
        public PriceRange getPriceRange() {
            return priceRange;
        }

        /**
         * @param priceRange The price_range
         */
        public void setPriceRange(PriceRange priceRange) {
            this.priceRange = priceRange;
        }

    }

    public static class PriceRange {

        @SerializedName("min")
        @Expose
        private String min;
        @SerializedName("max")
        @Expose
        private String max;

        /**
         * @return The min
         */
        public String getMin() {
            return min;
        }

        /**
         * @param min The min
         */
        public void setMin(String min) {
            this.min = min;
        }

        /**
         * @return The max
         */
        public String getMax() {
            return max;
        }

        /**
         * @param max The max
         */
        public void setMax(String max) {
            this.max = max;
        }

    }

}
