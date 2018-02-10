package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 11/11/16.
 */
public class ResGetUserPointsByTime extends  ResBase {
    @SerializedName("totalPoints")
    @Expose
    private String totalPoints;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("calories")
    @Expose
    private String calories;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
