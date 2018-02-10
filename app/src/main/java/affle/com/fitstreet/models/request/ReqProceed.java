package affle.com.fitstreet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 22/9/16.
 */
public class ReqProceed extends ReqBase{
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("walletAmt")
    @Expose
    private String walletAmt;
    @SerializedName("paymentBy")
    @Expose
    private String paymentBy;
    @SerializedName("paymentID")
    @Expose
    private String paymentID;
    @SerializedName("cartArray")
    @Expose
    private List<Integer> cartArray = new ArrayList<Integer>();
    @SerializedName("userAddressData")
    @Expose
    private UserAddressData userAddressData;

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
     * The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
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

    /**
     *
     * @return
     * The paymentBy
     */
    public String getPaymentBy() {
        return paymentBy;
    }

    /**
     *
     * @param paymentBy
     * The paymentBy
     */
    public void setPaymentBy(String paymentBy) {
        this.paymentBy = paymentBy;
    }

    /**
     *
     * @return
     * The cartArray
     */
    public List<Integer> getCartArray() {
        return cartArray;
    }

    /**
     *
     * @param cartArray
     * The cartArray
     */
    public void setCartArray(List<Integer> cartArray) {
        this.cartArray = cartArray;
    }

    /**
     *
     * @return
     * The userAddressData
     */
    public UserAddressData getUserAddressData() {
        return userAddressData;
    }

    /**
     *
     * @param userAddressData
     * The userAddressData
     */
    public void setUserAddressData(UserAddressData userAddressData) {
        this.userAddressData = userAddressData;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public static class UserAddressData {

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
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("pincode")
    @Expose
    private String pincode;

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

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
}
}
