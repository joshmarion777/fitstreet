package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 6/14/16.
 */
public class ReqForgotPassword extends ReqBase {
    @SerializedName("emailID")
    @Expose
    private String emailID;

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
