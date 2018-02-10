package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 12/7/16.
 */
public class ResGetFavouriteCoupons extends ResBase {
    @SerializedName("favCoupanData")
    @Expose
    private List<ResFavouriteCouponsData> favCoupanData = new ArrayList<ResFavouriteCouponsData>();

    /**
     * @return The favCoupanData
     */
    public List<ResFavouriteCouponsData> getFavCoupanData() {
        return favCoupanData;
    }

    /**
     * @param favCoupanData The favCoupanData
     */
    public void setFavCoupanData(List<ResFavouriteCouponsData> favCoupanData) {
        this.favCoupanData = favCoupanData;
    }

}
