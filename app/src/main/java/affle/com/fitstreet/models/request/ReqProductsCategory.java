package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 14/7/16.
 */
public class ReqProductsCategory extends ReqBase {

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("search")
    @Expose
    private String search;
    @SerializedName("page")
    @Expose
    private String page;

    /**
     * @return The userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID The userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSearch() {
        return search;
    }

    /**
     * @param search The search
     */
    public void setSearch(String search) {
        this.search = search;
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
}
