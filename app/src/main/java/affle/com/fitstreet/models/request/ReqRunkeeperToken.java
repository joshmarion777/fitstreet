package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 18/10/16.
 */
public class ReqRunkeeperToken extends ReqBase {
    @SerializedName("runkeeperAccessToken")
    @Expose
    private String runkeeperAccessToken;
    @SerializedName("isRunkeeperConnect")
    @Expose
    private String isRunkeeperConnect;
    @SerializedName("userID")
    @Expose
    private String userID;
    /**
     *
     * @return
     * The runkeeperAccessToken
     */
    public String getRunkeeperAccessToken() {
        return runkeeperAccessToken;
    }

    /**
     *
     * @param runkeeperAccessToken
     * The runkeeperAccessToken
     */
    public void setRunkeeperAccessToken(String runkeeperAccessToken) {
        this.runkeeperAccessToken = runkeeperAccessToken;
    }

    /**
     *
     * @return
     * The isRunkeeperConnect
     */
    public String getIsRunkeeperConnect() {
        return isRunkeeperConnect;
    }

    /**
     *
     * @param isRunkeeperConnect
     * The isRunkeeperConnect
     */
    public void setIsRunkeeperConnect(String isRunkeeperConnect) {
        this.isRunkeeperConnect = isRunkeeperConnect;
    }

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

}
