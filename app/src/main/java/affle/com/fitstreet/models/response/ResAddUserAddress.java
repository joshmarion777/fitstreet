package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 6/14/16.
 */
public class ResAddUserAddress extends ResBase {
    @SerializedName("userAddress")
    @Expose
    private ResUserAddress userAddress;

    /**
     * @return The userAddress
     */
    public ResUserAddress getUserAddress() {
        return userAddress;
    }

    /**
     * @param userAddress The userAddress
     */
    public void setUserAddress(ResUserAddress userAddress) {
        this.userAddress = userAddress;
    }
}
