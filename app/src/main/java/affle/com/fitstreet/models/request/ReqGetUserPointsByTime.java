package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 11/11/16.
 */
public class ReqGetUserPointsByTime extends ReqBase{

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("pointTime")
    @Expose
    private String pointTime;

    /**
     *
     * @return
     * The userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     * The userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }


    /**
     *
     * @return
     * The pointTime
     */
    public String getPointTime() {
        return pointTime;
    }

    /**
     *
     * @param pointTime
     * The pointTime
     */
    public void setPointTime(String pointTime) {
        this.pointTime = pointTime;
    }

}
