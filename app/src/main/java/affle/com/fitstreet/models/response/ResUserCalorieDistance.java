package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 10/11/16.
 */
public class ResUserCalorieDistance extends ResBase {
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("calories")
    @Expose
    private String calories;
    @SerializedName("totalPoints")
    @Expose
    private String totalPoints;
    @SerializedName("notificationCount")
    @Expose
    private String notificationCount;
    @SerializedName("isFitbitConnect")
    @Expose
    private String isFitbitConnect;
    @SerializedName("isRunkeeperConnect")
    @Expose
    private String isRunkeeperConnect;
    @SerializedName("emailStatus")
    @Expose
    private String emailStatus;

    /**
     *
     * @return
     * The distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     * The distance
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     *
     * @return
     * The calories
     */
    public String getCalories() {
        return calories;
    }

    /**
     *
     * @param calories
     * The calories
     */
    public void setCalories(String calories) {
        this.calories = calories;
    }

    /**
     *
     * @return
     * The totalPoints
     */
    public String getTotalPoints() {
        return totalPoints;
    }

    /**
     *
     * @param totalPoints
     * The totalPoints
     */
    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(String notificationCount) {
        this.notificationCount = notificationCount;
    }

    public String getIsFitbitConnect() {
        return isFitbitConnect;
    }

    public void setIsFitbitConnect(String isFitbitConnect) {
        this.isFitbitConnect = isFitbitConnect;
    }

    public String getIsRunkeeperConnect() {
        return isRunkeeperConnect;
    }

    public void setIsRunkeeperConnect(String isRunkeeperConnect) {
        this.isRunkeeperConnect = isRunkeeperConnect;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }
}
