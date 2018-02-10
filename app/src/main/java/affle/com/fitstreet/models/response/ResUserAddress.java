package affle.com.fitstreet.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 7/26/16.
 */
public class ResUserAddress implements Parcelable {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("userAddID")
    @Expose
    private String userAddID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pincode")
    @Expose
    private String pincode;

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
     * @return The userAddID
     */
    public String getUserAddID() {
        return userAddID;
    }

    /**
     * @param userAddID The userAddID
     */
    public void setUserAddID(String userAddID) {
        this.userAddID = userAddID;
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
     * @return The landmark
     */
    public String getLandmark() {
        return landmark;
    }

    /**
     * @param landmark The landmark
     */
    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return The pincode
     */
    public String getPincode() {
        return pincode;
    }

    /**
     * @param pincode The pincode
     */
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userID);
        dest.writeString(this.userAddID);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.landmark);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.address);
        dest.writeString(this.pincode);
    }

    public ResUserAddress() {
    }

    protected ResUserAddress(Parcel in) {
        this.userID = in.readString();
        this.userAddID = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.landmark = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.address = in.readString();
        this.pincode = in.readString();
    }

    public static final Parcelable.Creator<ResUserAddress> CREATOR = new Parcelable.Creator<ResUserAddress>() {
        @Override
        public ResUserAddress createFromParcel(Parcel source) {
            return new ResUserAddress(source);
        }

        @Override
        public ResUserAddress[] newArray(int size) {
            return new ResUserAddress[size];
        }
    };
}