package affle.com.fitstreet.models.response;

/**
 * Created by akash on 27/6/16.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResCouponData {


    @SerializedName("catID")
    @Expose
    private String catID;
    @SerializedName("catImage")
    @Expose
    private String catImage;
    @SerializedName("catIcon")
    @Expose
    private String catIcon;
    @SerializedName("catName")
    @Expose
    private String catName;

    /**
     *
     * @return
     * The catID
     */
    public String getCatID() {
        return catID;
    }

    /**
     *
     * @param catID
     * The catID
     */
    public void setCatID(String catID) {
        this.catID = catID;
    }

    /**
     *
     * @return
     * The catImage
     */
    public String getCatImage() {
        return catImage;
    }

    /**
     *
     * @param catImage
     * The catImage
     */
    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    /**
     *
     * @return
     * The catIcon
     */
    public String getCatIcon() {
        return catIcon;
    }

    /**
     *
     * @param catIcon
     * The catIcon
     */
    public void setCatIcon(String catIcon) {
        this.catIcon = catIcon;
    }

    /**
     *
     * @return
     * The catName
     */
    public String getCatName() {
        return catName;
    }

    /**
     *
     * @param catName
     * The catName
     */
    public void setCatName(String catName) {
        this.catName = catName;
    }

}