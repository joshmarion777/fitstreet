package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 21/10/16.
 */
public class ReqFitbitToken extends ReqBase {
    @SerializedName("fitbitUserId")
    @Expose
    private String fitbitUserId;
    @SerializedName("fitbitAccessToken")
    @Expose
    private String fitbitAccessToken;
    @SerializedName("isFitbitConnect")
    @Expose
    private String isFitbitConnect;
    @SerializedName("userID")
    @Expose
    private String userID;
    /**
     *
     * @return
     * The fitbitUserId
     */
    public String getFitbitUserId() {
        return fitbitUserId;
    }

    /**
     *
     * @param fitbitUserId
     * The fitbitUserId
     */
    public void setFitbitUserId(String fitbitUserId) {
        this.fitbitUserId = fitbitUserId;
    }

    /**
     *
     * @return
     * The fitbitAccessToken
     */
    public String getFitbitAccessToken() {
        return fitbitAccessToken;
    }

    /**
     *
     * @param fitbitAccessToken
     * The fitbitAccessToken
     */
    public void setFitbitAccessToken(String fitbitAccessToken) {
        this.fitbitAccessToken = fitbitAccessToken;
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
