package affle.com.fitstreet.models.request;

/**
 * Created by akash on 27/6/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqCoupons extends ReqBase {


    @SerializedName("userID")
    @Expose
    private String userID;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * @return The userID
     */
    @SerializedName("search")
    private String search;


    public String getUserID() {
        return userID;
    }

    /**
     * @param userID The userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }


}