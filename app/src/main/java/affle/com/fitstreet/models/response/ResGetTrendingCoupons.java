package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 18/7/16.
 */
public class ResGetTrendingCoupons extends ResBase {
    @SerializedName("trendingCoupanData")
    @Expose
    private List<ResTrendingCouponsData> trendingCoupanData = new ArrayList<>();
    @SerializedName("favCount")
    @Expose
    private String favCount;

    /**
     * @return The favCount
     */
    public String getFavCount() {
        return favCount;
    }

    /**
     * @param favCount The favCount
     */
    public void setFavCount(String favCount) {
        this.favCount = favCount;
    }
    /**
     * @return The trendingCoupanData
     */
    public List<ResTrendingCouponsData> getTrendingCoupanData() {
        return trendingCoupanData;
    }

    /**
     * @param trendingCoupanData The trendingCoupanData
     */
    public void setTrendingCoupanData(List<ResTrendingCouponsData> trendingCoupanData) {
        this.trendingCoupanData = trendingCoupanData;
    }

}
