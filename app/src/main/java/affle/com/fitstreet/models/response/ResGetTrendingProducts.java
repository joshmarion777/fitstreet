package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 18/7/16.
 */
public class ResGetTrendingProducts extends ResBase {
    @SerializedName("trendingProductData")
    @Expose
    private List<ResTrendingProductsData> trendingProductData = new ArrayList<ResTrendingProductsData>();
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
     * @return The trendingProductData
     */
    public List<ResTrendingProductsData> getTrendingProductData() {
        return trendingProductData;
    }

    /**
     * @param trendingProductData The trendingProductData
     */
    public void setTrendingProductData(List<ResTrendingProductsData> trendingProductData) {
        this.trendingProductData = trendingProductData;
    }

}
