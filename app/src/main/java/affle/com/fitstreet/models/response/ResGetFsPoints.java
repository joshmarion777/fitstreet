package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 5/9/16.
 */
public class ResGetFsPoints extends ResBase {
    @SerializedName("totalPoints")
    @Expose
    private String totalPoints;

    /**
     * @return The totalPoints
     */
    public String getTotalPoints() {
        return totalPoints;
    }

    /**
     * @param totalPoints The totalPoints
     */
    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }
}
