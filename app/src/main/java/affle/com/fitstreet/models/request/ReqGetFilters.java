package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 7/19/16.
 */
public class ReqGetFilters extends ReqBase {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("filterBy")
    @Expose
    private String filterBy;

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
     * @return The filterBy
     */
    public String getFilterBy() {
        return filterBy;
    }

    /**
     * @param filterBy The filterBy
     */
    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }
}
