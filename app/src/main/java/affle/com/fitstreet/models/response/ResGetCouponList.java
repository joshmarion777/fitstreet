package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash on 5/7/16.
 */
public class ResGetCouponList extends ResBase {
    @SerializedName("catCoupanData")
    @Expose
    private List<ResCouponList> couponsList = new ArrayList<>();
    @SerializedName("favCount")
    @Expose
    private String favCount;

    /**
     * @return The favCount
     */
    public String getFavCount() {
        return favCount;
    }

    /**
     * @param favCount The favCount
     */
    public void setFavCount(String favCount) {
        this.favCount = favCount;
    }

    /**
     *
     * @return
     * The couponsList
     */
    public List<ResCouponList> getCouponsList() {
        return couponsList;
    }

    /**
     *
     * @param couponsList
     * The couponsList
     */
    public void setCouponsList(List<ResCouponList> couponsList) {
        this.couponsList = couponsList;
    }

}

