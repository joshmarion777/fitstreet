package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 14/7/16.
 */
public class ResProductsCategoryData {
    @SerializedName("proCatID")
    @Expose
    private String proCatID;
    @SerializedName("proCatName")
    @Expose
    private String proCatName;
    @SerializedName("proCatImage")
    @Expose
    private String proCatImage;

    /**
     * @return The proCatID
     */
    public String getProCatID() {
        return proCatID;
    }

    /**
     * @param proCatID The proCatID
     */
    public void setProCatID(String proCatID) {
        this.proCatID = proCatID;
    }

    /**
     * @return The proCatName
     */
    public String getProCatName() {
        return proCatName;
    }

    /**
     * @param proCatName The proCatName
     */
    public void setProCatName(String proCatName) {
        this.proCatName = proCatName;
    }

    /**
     * @return The proCatImage
     */
    public String getProCatImage() {
        return proCatImage;
    }

    /**
     * @param proCatImage The proCatImage
     */
    public void setProCatImage(String proCatImage) {
        this.proCatImage = proCatImage;
    }


}
