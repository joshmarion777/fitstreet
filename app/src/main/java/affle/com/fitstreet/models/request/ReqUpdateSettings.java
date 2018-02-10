package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 6/30/16.
 */
public class ReqUpdateSettings extends ReqBase {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("homeScreen")
    @Expose
    private String homeScreen;
    @SerializedName("resetImage")
    @Expose
    private String resetImage;

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
     * @return The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return The notification
     */
    public String getNotification() {
        return notification;
    }

    /**
     * @param notification The notification
     */
    public void setNotification(String notification) {
        this.notification = notification;
    }

    /**
     * @return The homeScreen
     */
    public String getHomeScreen() {
        return homeScreen;
    }

    /**
     * @param homeScreen The homeScreen
     */
    public void setHomeScreen(String homeScreen) {
        this.homeScreen = homeScreen;
    }

    /**
     * @return The resetImage
     */
    public String getResetImage() {
        return resetImage;
    }

    /**
     * @param resetImage The resetImage
     */
    public void setResetImage(String resetImage) {
        this.resetImage = resetImage;
    }
}
