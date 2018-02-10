package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 7/18/16.
 */
public class ReqDeleteUserAddresses extends ReqBase {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("userAddID")
    @Expose
    private String userAddID;

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
     * @return The userAddID
     */
    public String getUserAddID() {
        return userAddID;
    }

    /**
     * @param userAddID The userAddID
     */
    public void setUserAddID(String userAddID) {
        this.userAddID = userAddID;
    }

}
