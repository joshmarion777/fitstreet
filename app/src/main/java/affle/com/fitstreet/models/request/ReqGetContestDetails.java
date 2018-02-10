package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 22/7/16.
 */
public class ReqGetContestDetails extends ReqBase {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("contestID")
    @Expose
    private String contestID;

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
     * @return The contestID
     */
    public String getContestID() {
        return contestID;
    }

    /**
     * @param contestID The contestID
     */
    public void setContestID(String contestID) {
        this.contestID = contestID;
    }

}

