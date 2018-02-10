package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 14/7/16.
 */
public class ResGetProductsList extends ResBase {
    @SerializedName("catProductData")
    @Expose
    private List<ResProductsListData> catProductData = new ArrayList<>();
    @SerializedName("favCount")
    @Expose
    private String favCount;

    /**
     * @return The catProductData
     */
    public List<ResProductsListData> getCatProductData() {
        return catProductData;
    }

    /**
     * @param catProductData The catProductData
     */
    public void setCatProductData(List<ResProductsListData> catProductData) {
        this.catProductData = catProductData;
    }


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
}
