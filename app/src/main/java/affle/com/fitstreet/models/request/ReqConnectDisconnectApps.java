package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 15/11/16.
 */
public class ReqConnectDisconnectApps extends ReqBase{
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("isFitbitConnect")
    @Expose
    private String isFitbitConnect;
    @SerializedName("isRunkeeperConnect")
    @Expose
    private String isRunkeeperConnect;

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
     * The isFitbitConnect
     */
    public String getIsFitbitConnect() {
        return isFitbitConnect;
    }

    /**
     *
     * @param isFitbitConnect
     * The isFitbitConnect
     */
    public void setIsFitbitConnect(String isFitbitConnect) {
        this.isFitbitConnect = isFitbitConnect;
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

}
