package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 11/7/16.
 */
public class ResGetFavouriteProducts extends ResBase {
    @SerializedName("favProductData")
    @Expose
    private List<ResFavouriteProductsData> favProductData = new ArrayList<ResFavouriteProductsData>();
    @SerializedName("totalPage")
    @Expose
    private Integer totalPage;
    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("totalRecords")
    @Expose
    private Integer totalRecords;

    /**
     * @return The favProductData
     */
    public List<ResFavouriteProductsData> getFavProductData() {
        return favProductData;
    }

    /**
     * @param favProductData The favProductData
     */
    public void setFavProductData(List<ResFavouriteProductsData> favProductData) {
        this.favProductData = favProductData;
    }

    /**
     *
     * @return
     * The totalPage
     */
    public Integer getTotalPage() {
        return totalPage;
    }

    /**
     *
     * @param totalPage
     * The totalPage
     */
    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    /**
     *
     * @return
     * The page
     */
    public String getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The totalRecords
     */
    public Integer getTotalRecords() {
        return totalRecords;
    }

    /**
     *
     * @param totalRecords
     * The totalRecords
     */
    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

}