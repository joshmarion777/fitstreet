package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 18/7/16.
 */
public class ResProductDetailData {
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("redeemPoint")
    @Expose
    private String redeemPoint;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("image1")
    @Expose
    private String image1;
    @SerializedName("image2")
    @Expose
    private String image2;
    @SerializedName("image3")
    @Expose
    private String image3;
    @SerializedName("image4")
    @Expose
    private String image4;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("packOf")
    @Expose
    private String packOf;
    @SerializedName("fabric")
    @Expose
    private String fabric;
    @SerializedName("fit")
    @Expose
    private String fit;
    @SerializedName("suitableFor")
    @Expose
    private String suitableFor;
    @SerializedName("sleeve")
    @Expose
    private String sleeve;
    @SerializedName("care")
    @Expose
    private String care;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("extCashBack")
    @Expose
    private String extCashBack;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("size")
    @Expose
    private List<String> size = new ArrayList<String>();
    @SerializedName("favourite")
    @Expose
    private int favourite;
    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("affilateUrl")
    @Expose
    private String affilateUrl;


    /**
     *
     * @return
     * The productID
     */
    public String getProductID() {
        return productID;
    }

    /**
     *
     * @param productID
     * The productID
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     *
     * @return
     * The redeemPoint
     */
    public String getRedeemPoint() {
        return redeemPoint;
    }

    /**
     *
     * @param redeemPoint
     * The redeemPoint
     */
    public void setRedeemPoint(String redeemPoint) {
        this.redeemPoint = redeemPoint;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @return
     * The affilateUrl
     */
    public String getAffilateUrl() {
        return affilateUrl;
    }

    /**
     *
     * @param affilateUrl
     * The affilateUrl
     */
    public void setAffilateUrl(String affilateUrl) {
        this.affilateUrl = affilateUrl;
    }
    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The image1
     */
    public String getImage1() {
        return image1;
    }

    /**
     *
     * @param image1
     * The image1
     */
    public void setImage1(String image1) {
        this.image1 = image1;
    }

    /**
     *
     * @return
     * The image2
     */
    public String getImage2() {
        return image2;
    }

    /**
     *
     * @param image2
     * The image2
     */
    public void setImage2(String image2) {
        this.image2 = image2;
    }

    /**
     *
     * @return
     * The image3
     */
    public String getImage3() {
        return image3;
    }

    /**
     *
     * @param image3
     * The image3
     */
    public void setImage3(String image3) {
        this.image3 = image3;
    }

    /**
     *
     * @return
     * The image4
     */
    public String getImage4() {
        return image4;
    }

    /**
     *
     * @param image4
     * The image4
     */
    public void setImage4(String image4) {
        this.image4 = image4;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The price
     */
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The discount
     */
    public String getDiscount() {
        return discount;
    }

    /**
     *
     * @param discount
     * The discount
     */
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    /**
     *
     * @return
     * The color
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color
     * The color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *
     * @return
     * The packOf
     */
    public String getPackOf() {
        return packOf;
    }

    /**
     *
     * @param packOf
     * The packOf
     */
    public void setPackOf(String packOf) {
        this.packOf = packOf;
    }

    /**
     *
     * @return
     * The fabric
     */
    public String getFabric() {
        return fabric;
    }

    /**
     *
     * @param fabric
     * The fabric
     */
    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    /**
     *
     * @return
     * The fit
     */
    public String getFit() {
        return fit;
    }

    /**
     *
     * @param fit
     * The fit
     */
    public void setFit(String fit) {
        this.fit = fit;
    }

    /**
     *
     * @return
     * The suitableFor
     */
    public String getSuitableFor() {
        return suitableFor;
    }

    /**
     *
     * @param suitableFor
     * The suitableFor
     */
    public void setSuitableFor(String suitableFor) {
        this.suitableFor = suitableFor;
    }

    /**
     *
     * @return
     * The sleeve
     */
    public String getSleeve() {
        return sleeve;
    }

    /**
     *
     * @param sleeve
     * The sleeve
     */
    public void setSleeve(String sleeve) {
        this.sleeve = sleeve;
    }

    /**
     *
     * @return
     * The care
     */
    public String getCare() {
        return care;
    }

    /**
     *
     * @param care
     * The care
     */
    public void setCare(String care) {
        this.care = care;
    }

    /**
     *
     * @return
     * The extCashBack
     */
    public String getExtCashBack() {
        return extCashBack;
    }

    /**
     *
     * @param extCashBack
     * The extCashBack
     */
    public void setExtCashBack(String extCashBack) {
        this.extCashBack = extCashBack;
    }

    /**
     *
     * @return
     * The quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     * The quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     * The size
     */
    public List<String> getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(List<String> size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The favourite
     */
    public int getFavourite() {
        return favourite;
    }

    /**
     *
     * @param favourite
     * The favourite
     */
    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    /**
     *
     * @return
     * The rating
     */
    public int getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}
