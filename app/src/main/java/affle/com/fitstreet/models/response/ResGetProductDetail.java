package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 18/7/16.
 */
public class ResGetProductDetail extends ResBase {
    @SerializedName("productData")
    @Expose
    private ResProductDetailData productData;
    @SerializedName("cartCount")
    @Expose
    private String cartCount;


    /**
     * @return The productData
     */
    public ResProductDetailData getProductData() {
        return productData;
    }

    /**
     * @param productData The productData
     */
    public void setProductData(ResProductDetailData productData) {
        this.productData = productData;
    }

    public String getCartCount() {
        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }
}
