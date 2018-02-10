package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 6/14/16.
 */
public class ReqSignUp extends ReqBase {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("deviceType")
    @Expose
    private String deviceType;
    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;
    @SerializedName("registrationType")
    @Expose
    private String registrationType;
    @SerializedName("facebookId")
    @Expose
    private String facebookId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("emailUpdate")
    @Expose
    private String emailUpdate;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

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
     * @return The registrationType
     */
    public String getRegistrationType() {
        return registrationType;
    }

    /**
     * @param registrationType The registrationType
     */
    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    /**
     * @return The facebookId
     */
    public String getFacebookId() {
        return facebookId;
    }

    /**
     * @param facebookId The facebookId
     */
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
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

    /**
     * @return The mGender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender The mGender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return The dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @param dob The dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmailUpdate() {
        return emailUpdate;
    }

    public void setEmailUpdate(String emailUpdate) {
        this.emailUpdate = emailUpdate;
    }
}
