package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 6/14/16.
 */
public class ResForgotPassword extends ResBase {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("emailID")
    @Expose
    private String emailID;

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
     * @return The emailID
     */
    public String getEmailID() {
        return emailID;
    }

    /**
     * @param emailID The emailID
     */
    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
}
