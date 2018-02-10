
package affle.com.fitstreet.models.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CouponData {

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
    @SerializedName("tagText")
    @Expose
    private String tagText;
    @SerializedName("extCashBack")
    @Expose
    private String extCashBack;
    @SerializedName("redeemPoint")
    @Expose
    private String redeemPoint;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("ValidUpTo")
    @Expose
    private String ValidUpTo;
    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("favourite")
    @Expose
    private int favourite;
    @SerializedName("partnerID")
    @Expose
    private int partnerId;

    /**
     * 
     * @return
     *     The coupanID
     */
    public String getCoupanID() {
        return coupanID;
    }

    /**
     * 
     * @param coupanID
     *     The coupanID
     */
    public void setCoupanID(String coupanID) {
        this.coupanID = coupanID;
    }

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The tagText
     */
    public String getTagText() {
        return tagText;
    }

    /**
     * 
     * @param tagText
     *     The tagText
     */
    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    /**
     * 
     * @return
     *     The extCashBack
     */
    public String getExtCashBack() {
        return extCashBack;
    }

    /**
     * 
     * @param extCashBack
     *     The extCashBack
     */
    public void setExtCashBack(String extCashBack) {
        this.extCashBack = extCashBack;
    }

    /**
     * 
     * @return
     *     The redeemPoint
     */
    public String getRedeemPoint() {
        return redeemPoint;
    }

    /**
     * 
     * @param redeemPoint
     *     The redeemPoint
     */
    public void setRedeemPoint(String redeemPoint) {
        this.redeemPoint = redeemPoint;
    }

    /**
     * 
     * @return
     *     The code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The ValidUpTo
     */
    public String getValidUpTo() {
        return ValidUpTo;
    }

    /**
     * 
     * @param ValidUpTo
     *     The ValidUpTo
     */
    public void setValidUpTo(String ValidUpTo) {
        this.ValidUpTo = ValidUpTo;
    }

    /**
     * 
     * @return
     *     The locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * 
     * @param locationName
     *     The locationName
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * 
     * @return
     *     The favourite
     */
    public int getFavourite() {
        return favourite;
    }

    /**
     * 
     * @param favourite
     *     The favourite
     */
    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    /**
     *
     * @return
     *     The partnerId
     */
    public int getPartnerId() {
        return partnerId;
    }
    /**
     *
     * @param partnerId
     *     The favourite
     */

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }
}
