package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 6/27/16.
 */
public class ReqUpdateProfile extends ReqBase {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("mGender")
    @Expose
    private String gender;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("weight")
    @Expose
    private String weight;

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
     * @return The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return The dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @param dob The dob
     */
    public void setDob(String dob) {
        this.dob = dob;
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

    /**
     * @return The mGender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender The mGender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return The height
     */
    public String getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * @return The weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * @param weight The weight
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

}
