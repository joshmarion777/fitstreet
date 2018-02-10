package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 15/9/16.
 */
public class ResPlaceOrder extends ResBase {
    @SerializedName("userAddressData")
    @Expose
    private List<UserAddressDatum> userAddressData = new ArrayList<UserAddressDatum>();
    @SerializedName("walletAmt")
    @Expose
    private String walletAmt;



    /**
     *
     * @return
     * The userAddressData
     */
    public List<UserAddressDatum> getUserAddressData() {
        return userAddressData;
    }

    /**
     *
     * @param userAddressData
     * The userAddressData
     */
    public void setUserAddressData(List<UserAddressDatum> userAddressData) {
        this.userAddressData = userAddressData;
    }

    /**
     *
     * @return
     * The walletAmt
     */
    public String getWalletAmt() {
        return walletAmt;
    }

    /**
     *
     * @param walletAmt
     * The walletAmt
     */
    public void setWalletAmt(String walletAmt) {
        this.walletAmt = walletAmt;
    }

public class UserAddressDatum {

    @SerializedName("userAddID")
    @Expose
    private String userAddID;
    @SerializedName("userID")
    @Expose
    private String userID;
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
     *
     * @return
     * The userAddID
     */
    public String getUserAddID() {
        return userAddID;
    }

    /**
     *
     * @param userAddID
     * The userAddID
     */
    public void setUserAddID(String userAddID) {
        this.userAddID = userAddID;
    }

    /**
     *
     * @return
     * The userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     * The userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
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
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The landmark
     */
    public String getLandmark() {
        return landmark;
    }

    /**
     *
     * @param landmark
     * The landmark
     */
    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The pincode
     */
    public String getPincode() {
        return pincode;
    }

    /**
     *
     * @param pincode
     * The pincode
     */
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

}
}

