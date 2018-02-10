package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 6/14/16.
 */
public class ReqLogin extends ReqBase {

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("deviceType")
    @Expose
    private String deviceType;
    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * @return The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
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

    /**
     * @return The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName The userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return The deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType The deviceType
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return The deviceToken
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * @param deviceToken The deviceToken
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    /**
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
