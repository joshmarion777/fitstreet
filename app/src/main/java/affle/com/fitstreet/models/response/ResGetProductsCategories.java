package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 14/7/16.
 */
public class ResGetProductsCategories extends ResBase {
    @SerializedName("productCatData")
    @Expose
    private List<ResProductsCategoryData> productCatData = new ArrayList<ResProductsCategoryData>();

    /**
     * @return The productCatData
     */
    public List<ResProductsCategoryData> getProductCatData() {
        return productCatData;
    }

    /**
     * @param productCatData The productCatData
     */
    public void setProductCatData(List<ResProductsCategoryData> productCatData) {
        this.productCatData = productCatData;
    }

}
