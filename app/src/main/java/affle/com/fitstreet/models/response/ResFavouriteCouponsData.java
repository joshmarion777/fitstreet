package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 12/7/16.
 */
public class ResFavouriteCouponsData {
    @SerializedName("coupanID")
    @Expose
    private String coupanID;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("ValidUpTo")
    @Expose
    private String validUpTo;
    @SerializedName("tagText")
    @Expose
    private String tagText;


    /**
     * @return The tagText
     */
    public String getTagText() {
        return tagText;
    }

    /**
     * @param tagText The tagText
     */
    public void setTagText(String tagText) {
        this.tagText = tagText;
    }
    /**
     * @return The coupanID
     */
    public String getCoupanID() {
        return coupanID;
    }

    /**
     * @param coupanID The coupanID
     */
    public void setCoupanID(String coupanID) {
        this.coupanID = coupanID;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * @param locationName The locationName
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * @return The validUpTo
     */
    public String getValidUpTo() {
        return validUpTo;
    }

    /**
     * @param validUpTo The ValidUpTo
     */
    public void setValidUpTo(String validUpTo) {
        this.validUpTo = validUpTo;
    }

}
