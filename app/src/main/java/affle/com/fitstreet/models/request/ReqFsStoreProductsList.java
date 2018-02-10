package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 31/8/16.
 */
public class ReqFsStoreProductsList {
    @SerializedName("userID")
    @Expose
    private String userID;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @SerializedName("gender")
    @Expose
    private int gender;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("service_key")
    @Expose
    private String serviceKey;
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
     * @return The method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method The method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return The serviceKey
     */
    public String getServiceKey() {
        return serviceKey;
    }

    /**
     * @param serviceKey The service_key
     */
    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
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

    public static class FilterType {

        @SerializedName("proCatID")
        @Expose
        private List<String> proCatID = new ArrayList<String>();
        @SerializedName("price_range")
        @Expose
        private PriceRange priceRange;

        /**
         *
         * @return
         * The proCatID
         */
        public List<String> getProCatID() {
            return proCatID;
        }

        /**
         *
         * @param proCatID
         * The proCatID
         */
        public void setProCatID(List<String> proCatID) {
            this.proCatID = proCatID;
        }

        /**
         *
         * @return
         * The priceRange
         */
        public PriceRange getPriceRange() {
            return priceRange;
        }

        /**
         *
         * @param priceRange
         * The price_range
         */
        public void setPriceRange(PriceRange priceRange) {
            this.priceRange = priceRange;
        }

    }
}
