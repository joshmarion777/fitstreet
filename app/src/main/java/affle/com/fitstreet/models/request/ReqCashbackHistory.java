package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by apps on 9/10/16.
 */

public class ReqCashbackHistory extends ReqBase {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("cashbackStatus")
    @Expose
    private String cashbackStatus;

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
     * @return The cashbackStatus
     */
    public String getCashbackStatus() {
        return cashbackStatus;
    }

    /**
     * @param cashbackStatus The cashbackStatus
     */
    public void setCashbackStatus(String cashbackStatus) {
        this.cashbackStatus = cashbackStatus;
    }
}