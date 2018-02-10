package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 7/15/16.
 */
public class ResGetUserAddresses extends ResBase {

    @SerializedName("userAddressData")
    @Expose
    private List<ResUserAddress> userAddressList = new ArrayList<ResUserAddress>();

    /**
     * @return The userAddressData
     */
    public List<ResUserAddress> getUserAddressList() {
        return userAddressList;
    }

    /**
     * @param resUserAddress The userAddressData
     */
    public void setUserAddressList(List<ResUserAddress> resUserAddress) {
        this.userAddressList = resUserAddress;
    }
}